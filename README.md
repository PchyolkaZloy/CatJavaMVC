# Cat and Owner Management Service

A comprehensive service to manage cats and their owners (masters), built with a layered architecture, Spring framework,
and secured with Spring Security.

## Features

1. Manage information about cats and their owners (masters).
2. REST API to retrieve specific and filtered information about cats and owners.
3. Secured access to the API with role-based authorization.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Endpoints](#api-endpoints)
  - [User](#user)
  - [Admin](#admin)
- [Testing](#testing)
- [Folder Structure](#folder-structure)
- [License](#license)

## Prerequisites

- JDK 8 or higher
- Gradle
- PostgreSQL
- Docker  (optional, for easier setup of the PostgreSQL database)

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/PchyolkaZloy/CatJavaMVC.git
    cd CatJavaMVC
    ```

2. Build the project:

    ```bash
    ./gradlew build
    ```

3. Set up the PostgreSQL database:

    - You can use Docker to quickly build a PostgreSQL instance.
    - Use docker-compose.yml already written
    ```bash
   docker-compose up --build -d
   ```

4. Apply SQL scripts located in `dao/src/main/resources/cats.sql` to set up the necessary tables and data.

## Configuration

1. Update the `application.yml` file located in `dao/src/main/resources/cats.sql`:

    ```yaml
    spring:
      datasource:
        url: jdbc:postgresql://localhost:5432/db
        username: username
        password: password
      jpa:
        open-in-view: off
        hibernate:
          ddl-auto: update
    ```

## Usage

1. Run the application:

    ```bash
    ./gradlew bootRun
    ```

2. Access the REST API at `http://localhost:8080`.

## API Endpoints

## User

These endpoints provide a comprehensive API for managing cats and their friendships, with security measures to ensure
that only authorized users can access and manipulate the data.

### Get Cat by ID

```http
GET /user/cat/{id}
```

Retrieve information about a specific cat by its ID.

#### Example

```http
GET /user/cat/1
```

```json
{
  "id": 1,
  "name": "Whiskers",
  "birthDate": "2020-01-01",
  "breed": "Siamese",
  "color": "Brown",
  "catMasterId": 1
}
```

### Create Cat

```http
POST /user/cat/create
```

Create a new cat and associate it with the current user's cat master account.

#### Request Body

- `name`: Name of the cat.
- `birthdate`: Birthdate of the cat.
- `breed`: Breed of the cat.
- `color`: Color of the cat.

### Add Friendship

```http
PATCH /user/cat/registerFriendship
```

Register a friendship between two cats.

- `firstId` (query parameter): The ID of the first cat.
- `secondId` (query parameter): The ID of the second cat.

#### Example

```http
PATCH /user/cat/registerFriendship?firstId=1&secondId=2
```

### Get All Friends of a Cat

```http
GET /user/cat/getAllCatFriends
```

Retrieve all friends of a specific cat by its ID.

- `id` (query parameter): The ID of the cat whose friends are to be retrieved.

#### Example

```http
GET /user/cat/getAllCatFriends?id=1
```

```json
[
  {
    "id": 2,
    "name": "Tom",
    "birthDate": "2019-05-01",
    "breed": "Maine Coon",
    "color": "Black",
    "catMasterId": 1
  },
  {
    "id": 3,
    "name": "Jerry",
    "birthDate": "2020-07-01",
    "breed": "Persian",
    "color": "White",
    "catMasterId": 1
  }
]
```

### Get All Cats of the Current User

```http
GET /user/cat/getCats
```

Retrieve all cats associated with the current user.

#### Example

```http
GET /user/cat/getCats
```

```json
[
  {
    "id": 1,
    "name": "Whiskers",
    "birthDate": "2020-01-01",
    "breed": "Siamese",
    "color": "Brown",
    "catMasterId": 1
  },
  {
    "id": 2,
    "name": "Tom",
    "birthDate": "2019-05-01",
    "breed": "Maine Coon",
    "color": "Black",
    "catMasterId": 1
  }
]
```

### Get Cats by Color

```http
GET /user/cat/getCatsByColor
```

Retrieve all cats of a specific color associated with the current user.

- `color` (query parameter): The color of the cats to retrieve.

#### Example

```http
GET /user/cat/getCatsByColor?color=Brown
```

```json
[
  {
    "id": 1,
    "name": "Whiskers",
    "birthDate": "2020-01-01",
    "breed": "Siamese",
    "color": "Brown",
    "catMasterId": 1
  }
]
```

### Get Cats by Breed

```http
GET /user/cat/getCatsByBreed
```

Retrieve all cats of a specific breed associated with the current user.

- `breed` (query parameter): The breed of the cats to retrieve.

#### Example

```http
GET /user/cat/getCatsByBreed?breed=Siamese
```

```json
[
  {
    "id": 1,
    "name": "Whiskers",
    "birthDate": "2020-01-01",
    "breed": "Siamese",
    "color": "Brown",
    "catMasterId": 1
  }
]
```

### Delete Cat by ID

```http
DELETE /user/cat/{id}
```

Delete a specific cat by its ID.

- `id` (path parameter): The ID of the cat to delete.

## Admin
### Find Cat by ID

```http
GET /admin/catMenu/{id}
```
Retrieve information about a specific cat by its ID.

#### Example
```http
GET /admin/catMenu/1
```
```json
{
  "id": 1,
  "name": "Whiskers",
  "birthDate": "2021-01-01",
  "breed": "Siamese",
  "color": "Brown",
  "catMasterId": 2
}
```

### Create Cat
```http
POST /admin/catMenu/create
```
Creates a new cat.

#### Example
```http
POST /admin/catMenu/create
```

#### Request Body
```json
{
  "name": "Whiskers",
  "birthdate": "2021-01-01",
  "breed": "Siamese",
  "color": "Brown"
}
```

### Register Cat Friendship
```http
PATCH /admin/catMenu/registerFriendship
```
Registers a friendship between two cats.

#### Example
```http
PATCH /admin/catMenu/registerFriendship?firstId=1&secondId=2
```

### Find All Cats
```http
GET /admin/catMenu/getCats
```
Fetches all cats.

#### Example
```http
GET /admin/catMenu/getCats
```
```json
[
  {
    "id": 1,
    "name": "Whiskers",
    "birthDate": "2021-01-01",
    "breed": "Siamese",
    "color": "Brown",
    "catMasterId": 2
  },
  {
    "id": 2,
    "name": "Paws",
    "birthDate": "2020-06-15",
    "breed": "Persian",
    "color": "White",
    "catMasterId": 3
  }
]
```

### Find All Friends of a Cat
```http
GET /admin/catMenu/getAllCatFriends
```
Fetches all friends of a specific cat.

#### Example
```http
GET /admin/catMenu/getAllCatFriends?id=1
```
```json
[
  {
    "id": 2,
    "name": "Paws",
    "birthDate": "2020-06-15",
    "breed": "Persian",
    "color": "White",
    "catMasterId": 3
  }
]
```

### Find All Cats by Color
```http
GET /admin/catMenu/getCatsByColor
```
Fetches all cats by color.

#### Example
```http
GET /admin/catMenu/getCatsByColor?color=Brown
```
```json
[
  {
    "id": 1,
    "name": "Whiskers",
    "birthDate": "2021-01-01",
    "breed": "Siamese",
    "color": "Brown",
    "catMasterId": 2
  }
]
```

### Find All Cats by Breed
```http
GET /admin/catMenu/getCatsByBreed
```
Fetches all cats by breed.

#### Example
```http
GET /admin/catMenu/getCatsByBreed?breed=Siamese
```
```json
[
  {
    "id": 1,
    "name": "Whiskers",
    "birthDate": "2021-01-01",
    "breed": "Siamese",
    "color": "Brown",
    "catMasterId": 2
  }
]
```

### Delete Cat by ID
```http
DELETE /admin/catMenu/{id}
```
Deletes a cat by its ID.

#### Example
```http
DELETE /admin/catMenu/1
```

### Find Cat Master by ID

```http
GET /admin/catMasterMenu/{id}
```
Retrieve information about a specific cat master by their ID.

#### Example
```http
GET /admin/catMasterMenu/1
```
```json
{
  "id": 1,
  "name": "John Doe",
  "birthDate": "1980-05-15",
  "cats": [
    {
      "id": 1,
      "name": "Whiskers",
      "birthDate": "2021-01-01",
      "breed": "Siamese",
      "color": "Brown",
      "catMasterId": 1
    }
  ]
}
```

### Create Cat Master
```http
POST /admin/catMasterMenu/create
```
Creates a new cat master.

#### Example
```http
POST /admin/catMasterMenu/create
```
#### Request Body
```json
{
  "name": "John Doe",
  "birthdate": "1980-05-15"
}
```

### Add Cat to Cat Master
```http
PATCH /admin/catMasterMenu/addCatToCatMaster
```
Adds a cat to a cat master.

#### Example
```http
PATCH /admin/catMasterMenu/addCatToCatMaster?catMasterId=1&catId=1
```

### Find All Cat Masters
```http
GET /admin/catMasterMenu/getCatMasters
```
Fetches all cat masters.

#### Example
```http
GET /admin/catMasterMenu/getCatMasters
```
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "birthDate": "1980-05-15",
    "cats": [
      {
        "id": 1,
        "name": "Whiskers",
        "birthDate": "2021-01-01",
        "breed": "Siamese",
        "color": "Brown",
        "catMasterId": 1
      }
    ]
  }
]
```

### Find All Cats by Cat Master
```http
GET /admin/catMasterMenu/getCats
```
Fetches all cats for a specific cat master.

#### Example
```http
GET /admin/catMasterMenu/getCats?catMasterId=1
```
```json
[
  {
    "id": 1,
    "name": "Whiskers",
    "birthDate": "2021-01-01",
    "breed": "Siamese",
    "color": "Brown",
    "catMasterId": 1
  }
]
```

### Find All Cats by Color for Cat Master
```http
GET /admin/catMasterMenu/getCatsByColor
```
Fetches all cats by color for a specific cat master.

#### Example
```http
GET /admin/catMasterMenu/getCatsByColor?catMasterId=1&color=Brown
```
```json
[
  {
    "id": 1,
    "name": "Whiskers",
    "birthDate": "2021-01-01",
    "breed": "Siamese",
    "color": "Brown",
    "catMasterId": 1
  }
]
```

### Find All Cats by Breed for Cat Master
```http
GET /admin/catMasterMenu/getCatsByBreed
```
Fetches all cats by breed for a specific cat master.

#### Example
```http
GET /admin/catMasterMenu/getCatsByBreed?catMasterId=1&breed=Siamese
```
```json
[
  {
    "id": 1,
    "name": "Whiskers",
    "birthDate": "2021-01-01",
    "breed": "Siamese",
    "color": "Brown",
    "catMasterId": 1
  }
]
```

### Delete Cat Master by ID
```http
DELETE /admin/catMasterMenu/{id}
```
Deletes a cat master by their ID.

#### Example
```http
DELETE /admin/catMasterMenu/1
```

### Find User by ID

```http
GET /admin/userMenu/{id}
```
Retrieve information about a specific user by their ID.

#### Example
```http
GET /admin/userMenu/1
```
```json
{
  "id": 1,
  "username": "admin",
  "password": "{bcrypt}$2a$10$e0MYzXyjpJS7Pd0RVvHwHe",
  "role": "ADMIN",
  "catMasterId": 1
}
```

### Create User
```http
POST /admin/userMenu/create
```
Creates a new user.

#### Example
```http
POST /admin/userMenu/create
```
#### Request Body
```json
{
  "username": "admin",
  "password": "password",
  "role": "ADMIN",
  "catMasterId": 1
}
```

### Delete User by ID
```http
DELETE /admin/userMenu/{id}
```
Deletes a user by their ID.

#### Example
```http
DELETE /admin/userMenu/1
```

## Testing

- The project uses JUnit for testing. To run the tests:

    ```bash
    ./gradlew test
    ```

- Mockito is used to mock dependencies and avoid connecting to a real database during tests.
- Each layer has its own tests (unit and integration tests).
- The dao layer tests in docker container

## Folder Structure

- `controller`: REST controllers. Security configuration.
- `service`: Service layer.
- `dao`: Data Access Objects (DAOs). Entity and DTO classes.

- `controller/src/main/java/en/pchz/ApplicationRunner`: Contains the start main method code.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
