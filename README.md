

[![Code Size](https://img.shields.io/github/languages/code-size/karthikbanjan/Web-Quiz-Engine?label=Code%20Size)]() 
[![Java](https://img.shields.io/github/languages/top/karthikbanjan/Web-Quiz-Engine)]()


# Web Quiz Engine

A backend server designed using REST principles for 
online quizzes. It supports a variety of operations.



## API Reference

#### Register a user

```http
  GET /api/register
```

| Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `email, password` | `JSON` | **Required**. Example: { "email": "test@gmail.com", "password": "secret"} | 


### For all the below apis, an authorization header with email and password is required.


#### Create a new quiz

```http
  POST /api/quizzes
```

| Body | Type     | Description                    |
| :-------- | :------- | :-------------------------------- |
| `title, text, options, answer` | `JSON` | **Required**. Example: {"title": "Coffee drinks", "text": "Select only coffee drinks.", "options": ["Americano","Tea","Cappuccino","Sprite"], "answer": [0,2]} |


#### Get quiz with id

```http
  GET /api/quizzes/{id}
```

| Path Variable | Description                   |
| :-------- | :-------------------------------- |
| `id`  | **Required.** `Example: GET /api/quizzes/1` |


#### Solving a quiz

```http
  POST /api/quizzes/{id}/solve
```

| Path Variable | Description                   |
| :-------- | :-------------------------------- |
| `id`  | **Required.** `Example: POST /api/quizzes/1/solve` |


#### Deleting a quiz

The Quiz will only be deleted if the user is the author.

```http
  DELETE /api/quizzes/{id}
```

| Path Variable | Description                   |
| :-------- | :-------------------------------- |
| `id`  | **Required.** `Example: DELETE /api/quizzes/1` |


#### Get all quizzes with paging

```http
  GET /api/quizzes
```

| Parameter | Type | Description                   |
| :-------- | :-----| :-------------------------------- |
| `page` | Natural Number | **Optional.** `Example: GET /api/quizzes?page=1` |



#### Get all completions of quizzes with paging

```http
  GET /api/quizzes/completed
```

| Parameter | Type | Description                   |
| :-------- | :-----| :-------------------------------- |
| `page` | Natural Number | **Optional.** `Example: GET /api/quizzes/completed?page=1` |



  
  
  






## Tech Stack

**Server:** Java (Spring Boot)


## Lessons Learned

- I learnt how simple it is to develop a server using Spring Boot.
- Creating models for all required data.
- Creating repository for all required data.
- Storing relevant data in a database.
- Retreive data from database using id/paging.
- Directing GET/POST/DELETE requests.
- Enabling to the Spring Security Module for authorization of users.
- Building projects with gradle.





## Acknowledgements

 - Hyperskill for the awesome in-depth literature on languages and project outlines.
 - Katherine Oelsner for the handy readme helper.

