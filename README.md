# Favourite Recipe App based on Microservices architecture

The solution uses Microservices architecture for building the Recipe App !

The implementation consists of one microservice 'recipe-app' implemented in Java 17 and Spring Boot 3 :

- `Ingredients API`: Ingredients API with below endpoints:

    - `POST /api/v1/ingredients Endpoint`: To create a new ingredient.
    - `GET /api/v1/ingredients?page=0&size=10 Endpoint`: To list All ingredients.
    - `DELETE /api/v1/ingredients/{id} Endpoint`: To delete an ingredient.
    - `GET  /api/v1/ingredients/{id}`: To retrieve an ingredient by ID.
  
    
- `Recipes API`: Recipe API with below endpoints:

  - `POST /api/v1/recipes Endpoint`: To create a new recipe.
  - `GET /api/v1/recipes?page=0&size=10 Endpoint`: To list All recipes.
  - `DELETE /api/v1/recipes/{id} Endpoint`: To delete an recipe.
  - `GET  /api/v1/recipes/{id}`: To retrieve a recipe by ID.
  - `PATCH  /api/v1/recipes`: To update an existing recipe .
  - `POST  /api/v1/recipes/search?page=0&size=10&sortBy=name`: To search for recipes.

    


Technologies
------------
- `Java 17`
- `Spring Boot 3`
- `H2 DB`
- `Open API 3`
- `Maven 3`


Swagger API Documentation
------------------------
This API is documented using Swagger. To view the documentation, navigate to /swagger-ui.html once the application is running on port 8090.


How To Run
----------
`./mvnw spring-boot:run`

Test Coverage
----------

the source code is covered by integration and unit tests under `src\test\java`

I have implemented around 80 unit and integration test cases that cover as much as cases
to be production ready.

Sample Request for recipe search
-------
`curl -X 'POST' \'http://localhost:8090/api/v1/recipes/search?page=0&size=10&sortBy=name' \
-H 'Content-Type: application/json' \
-d` 
```JSON'{
"criteria": [
{
"filterKey": "name",
"value": "Pasta",
"operation": "CN"
}
],
"dataOption": "ALL"
}
````


Sample Response
-------
```json
[
  {
    "id": 1,
    "name": "Pasta",
    "type": "VEGETARIAN",
    "numberOfServings": 5,
    "ingredients": [
      {
        "id": 1,
        "name": "tomato",
        "createdAt": "2023-05-09 09:51:15",
        "updatedAt": "2023-05-09 09:51:15"
      },
      {
        "id": 2,
        "name": "onion",
        "createdAt": "2023-05-09 09:51:28",
        "updatedAt": "2023-05-09 09:51:28"
      }
    ],
    "instructions": "Chop the tomato, stir and fry, boil and serve",
    "createdAt": "2023-05-09 09:52:48",
    "updatedAt": "2023-05-09 09:57:41"
  },
  {
    "id": 2,
    "name": "Pasta",
    "type": "OTHER",
    "numberOfServings": 4,
    "ingredients": [
      {
        "id": 3,
        "name": "meat",
        "createdAt": "2023-05-09 09:51:33",
        "updatedAt": "2023-05-09 09:51:33"
      }
    ],
    "instructions": "Cut the potato, add tomato and then bake it in the oven",
    "createdAt": "2023-05-09 09:53:13",
    "updatedAt": "2023-05-09 09:53:13"
  }
]
```


