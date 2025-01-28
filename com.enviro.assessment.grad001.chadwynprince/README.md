# Environmental Assessment Application

## Description
The Environmental Assessment Application is designed to provide guidelines and tips for waste disposal and recycling. It aims to enhance environmental awareness and promote sustainable practices.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Dependencies](#dependencies)
- [Features](#features)
- [Acknowledgements](#acknowledgements)
- [Contact Information](#contact-information)

## Installation

### Prerequisites
- Java Development Kit (JDK) 17 or higher
- Maven 3.6 or higher

### Steps
1. Clone the repository:
    ```bash
    git clone git@github.com:Just-Chad21/com.enviro.assessment.grad001.chadwynprince.git
    ```
2. Navigate to the project directory:
    ```bash
    cd com.enviro.assessment.grad001.chadwynprince/com.enviro.assessment.grad001.chadwynprince
    ```
3. Build the project using Maven:
    ```bash
    mvn clean install
    ```

## Usage
1. To start the application, run the following command:
    ```bash
    mvn spring-boot:run
    ```

2. To run the tests, use the following command:
    ```bash
    mvn test
    ```

## API Usage

### Base URL
-http://localhost:8080/api

### Endpoints

#### 1. **GET All Waste Categories**
- **Endpoint:** `/waste-categories`
- **Method:** `GET`
- **Description:** Retrieve all waste categories.
- **Example Request:**
    ```bash
    curl -X GET http://localhost:8080/api/waste-categories
    ```
- **Response Example:**
    ```json
    [
        {
            "id": 1,
            "name": "Plastic"
        },
        {
            "id": 2,
            "name": "Glass"
        }
    ]
    ```

#### 2. **GET Waste Category by ID**
- **Endpoint:** `/waste-categories/{id}`
- **Method:** `GET`
- **Description:** Retrieve details for a specific waste category by its ID.
- **Example Request:**
    ```bash
    curl -X GET http://localhost:8080/api/waste-categories/1
    ```
- **Response Example:**
    ```json
    {
        "id": 1,
        "name": "Plastic"
    }
    ```

#### 3. **POST Create a New Waste Category**
- **Endpoint:** `/waste-categories`
- **Method:** `POST`
- **Description:** Add a new waste category.
- **Request Body Example:**
    ```json
    {
        "name": "Metal"
    }
    ```
- **Example Request:**
    ```bash
    curl -X POST http://localhost:8080/api/waste-categories \
    -H "Content-Type: application/json" \
    -d '{"name":"Metal"}'
    ```
- **Response Example:**
    ```json
    {
        "id": 3,
        "name": "Metal"
    }
    ```

#### 4. **PUT Update an Existing Waste Category**
- **Endpoint:** `/waste-categories/{id}`
- **Method:** `PUT`
- **Description:** Update the name of a specific waste category.
- **Request Body Example:**
    ```json
    {
        "name": "Updated Category Name"
    }
    ```
- **Example Request:**
    ```bash
    curl -X PUT http://localhost:8080/api/waste-categories/1 \
    -H "Content-Type: application/json" \
    -d '{"name":"Updated Category Name"}'
    ```
- **Response Example:**
    ```json
    {
        "id": 1,
        "name": "Updated Category Name"
    }
    ```

#### 5. **DELETE Remove a Waste Category**
- **Endpoint:** `/waste-categories/{id}`
- **Method:** `DELETE`
- **Description:** Remove a waste category by its ID.
- **Example Request:**
    ```bash
    curl -X DELETE http://localhost:8080/api/waste-categories/1
    ```
- **Response Status:** `204 No Content`

### Recycling Tips Endpoints

#### 1. **GET All Recycling Tips**
- **Endpoint:** `/recycling-tips`
- **Method:** `GET`
- **Description:** Retrieve all recycling tips.
- **Example Request:**
    ```bash
    curl -X GET http://localhost:8080/api/recycling-tips
    ```
- **Response Example:**
    ```json
    [
        {
            "id": 1,
            "tip": "Separate plastics from organics."
        },
        {
            "id": 2,
            "tip": "Avoid single-use items."
        }
    ]
    ```

#### 2. **POST Create a New Recycling Tip**

1. **GET All Recycling Tips**
   - **Endpoint:** `/recycling-tips`
   - **Method:** `GET`
   - **Description:** Retrieve all recycling tips.
   - **Response Example:**
     ```json
     [
         {
             "id": 1,
             "tip": "Separate plastics from organics."
         },
         {
             "id": 2,
             "tip": "Avoid single-use items."
         }
     ]
     ```

2. **GET Recycling Tip by ID**
   - **Endpoint:** `/recycling-tips/{id}`
   - **Method:** `GET`
   - **Description:** Retrieve a specific recycling tip by ID.
   - **Response Example:**
     ```json
     {
         "id": 1,
         "tip": "Separate plastics from organics."
     }
     ```

3. **POST Create a New Recycling Tip**
   - **Endpoint:** `/recycling-tips`
   - **Method:** `POST`
   - **Request Body Example:**
     ```json
     {
         "tip": "Use reusable bottles."
     }
     ```
   - **Response Example:**
     ```json
     {
         "id": 3,
         "tip": "Use reusable bottles."
     }
     ```

4. **PUT Update a Recycling Tip**
   - **Endpoint:** `/recycling-tips/{id}`
   - **Method:** `PUT`
   - **Request Body Example:**
     ```json
     {
         "tip": "Recycle electronics properly."
     }
     ```
   - **Response Example:**
     ```json
     {
         "id": 1,
         "tip": "Recycle electronics properly."
     }
     ```

5. **DELETE Remove a Recycling Tip**
   - **Endpoint:** `/recycling-tips/{id}`
   - **Method:** `DELETE`
   - **Response Status:** `204 No Content`

#### **Disposal Guidelines**

1. **GET All Disposal Guidelines**
    - **Endpoint:** `/disposal-guidelines`
    - **Method:** `GET`
    - **Description:** Retrieve all disposal guidelines.
    - **Response Example:**
      ```json
      [
          {
              "id": 1,
              "disposalGuideline": "Rinse and crush plastic bottles before disposal."
          },
          {
              "id": 2,
              "disposalGuideline": "Remove labels and rinse glass jars before recycling."
          }
      ]
      ```

2. **GET Disposal Guideline by ID**
    - **Endpoint:** `/disposal-guidelines/{id}`
    - **Method:** `GET`
    - **Description:** Retrieve a specific disposal guideline by its ID.
    - **Response Example:**
      ```json
      {
          "id": 1,
          "disposalGuideline": "Rinse and crush plastic bottles before disposal."
      }
      ```

3. **POST Create a New Disposal Guideline**
    - **Endpoint:** `/disposal-guidelines`
    - **Method:** `POST`
    - **Request Body Example:**
      ```json
      {
          "disposalGuideline": "Remove food residue from cans and flatten before recycling."
      }
      ```
    - **Response Example:**
      ```json
      {
          "id": 3,
          "disposalGuideline": "Remove food residue from cans and flatten before recycling."
      }
      ```

4. **PUT Update a Disposal Guideline**
    - **Endpoint:** `/disposal-guidelines/{id}`
    - **Method:** `PUT`
    - **Request Body Example:**
      ```json
      {
          "category": "Glass",
          "guideline": "Separate clear and colored glass before disposal."
      }
      ```
    - **Response Example:**
      ```json
      {
          "id": 2,
          "category": "Glass",
          "guideline": "Separate clear and colored glass before disposal."
      }
      ```

5. **DELETE Remove a Disposal Guideline**
    - **Endpoint:** `/disposal-guidelines/{id}`
    - **Method:** `DELETE`
    - **Response Status:** `204 No Content`

---

## Dependencies
- Spring Boot: Framework used to create stand-alone, production-grade Spring-based applications.
- Maven: Build automation tool used primarily for Java projects.

## Features
- Provides guidelines for waste disposal.
- Offers recycling tips.
- Categorizes different types of waste.


## Acknowledgements
- **Spring Boot** - Used as the framework for developing the application.
- **Maven** - Used for building and managing the project's dependencies.

## Contact Information
**Email** - cprince023@student.wethinkcode.co.za
