package com.favourite.recipe.exception;

import com.favourite.recipe.api.response.GenericResponse;
import com.favourite.recipe.config.MessageProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@ControllerAdvice
@Slf4j
public class RecipeExceptionHandler {

	private final MessageProvider messageProvider;

	@ExceptionHandler(NotFoundException.class)
	@ResponseBody
	public ResponseEntity<GenericResponse> handleNotFoundException(NotFoundException ex) {
		return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({
			IllegalArgumentException.class,
			InvalidDataAccessApiUsageException.class
	})
	@ResponseBody
	public ResponseEntity<GenericResponse> handleArgumentException(Exception ex) {
		return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseBody
	public ResponseEntity<GenericResponse> handleDataIntegrityViolationException(Exception ex) {
		if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
			String message = messageProvider.getMessage("item.unableToDelete");
			return buildResponse(message, HttpStatus.BAD_REQUEST);
		}

		return handleGlobalException(ex);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<GenericResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

		String errorMessage = fieldErrors.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.joining(", "));

		return buildResponse(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public final ResponseEntity<GenericResponse> handleConstraintViolation(ConstraintViolationException ex) {
		String message = ex.getConstraintViolations()
				.stream()
				.map(ConstraintViolation::getMessage)
				.collect(Collectors.joining(", "));

		return buildResponse(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public ResponseEntity<GenericResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		String message = messageProvider.getMessage("json.invalidFormat");
		return buildResponse(message, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<GenericResponse> handleGlobalException(Exception ex) {
		log.error("Internal Server error ", ex);
		String message = messageProvider.getMessage("error.internalServerError");
		return buildResponse(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<GenericResponse> buildResponse(String message, HttpStatus status) {
		return new ResponseEntity<>(new GenericResponse(message), status);
	}
}
