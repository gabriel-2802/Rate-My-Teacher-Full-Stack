## Authentication
Only endpoints under `/api/admin/**` require authentication with a **Bearer token** in the header:

```
Authorization: Bearer <token>
```

---

## Test Users

| Username | Password | Role       | Token                                   |
|----------|----------|------------|-----------------------------------------|
| gabriel  | gabriel  | ROLE_ADMIN | eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnYWJyaWVsIiwicm9sZXMiOiJST0xFX0FETUlOIiwiaWF0IjoxNzQ2MDg3Mzg3LCJleHAiOjE3NDY2OTIxODd9.pr9SnF5FbyA6cwvCCrI6k9WxjZtb0eb4fqOUlRcJk5ZomaHpLtYQ67Ej1oMKoshTN8MwdSdfxrD3vVSytxfZFg |
| mario    | mario    | ROLE_USER  | eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYXJpbyIsInJvbGVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzQ2MDg3MzYyLCJleHAiOjE3NDY2OTIxNjJ9.NA76RJJYi707AP6BPOCPmc-AX4ykl6HcTIkzh0xaI-_I2fUFWhERfhqdeoV4kyBAqsPtWpqduCKak4_JX2uF4g                                        |

---

##  API Base URL
```
http://localhost:8080
```

---

##  API Endpoints

---

###  1. Login

**POST** `/api/auth/login`

**Body:**
```json
{
  "username": "gabriel",
  "password": "gabriel"
}
```

**Response:**
```json
{
  "access_token": "eyJhbGciOi...",
  "tokenType": "Bearer"
}
```

---

### 2. Register

**POST** `/api/auth/register`

**Body:**
```json
{
  "username": "mario",
  "password": "mario"
}
```

**Response (success):** HTTP 201 Created

**Response (failure):**
```json
"message_as_string"
```
**Status Code:** 409 Conflict

---

### 3. Get All Users

**GET** `/api/admin/users`  
**(Requires ADMIN token)**

**Response:**
```json
[
  {
    "id": 9,
    "username": "gabriel",
    "email": "gabriel@gabriel.com",
    "reviews": [],
    "roles": [{ "authority": "ROLE_ADMIN" }],
    "profilePicture": null
  },
  {
    "id": 10,
    "username": "mario",
    "email": "mario@mario.com",
    "reviews": [],
    "roles": [{ "authority": "ROLE_USER" }],
    "profilePicture": null
  },
  ...
]
```

---

###  4. Get User by Username

**GET** `/api/admin/users/{username}`  
**Example:** `/api/admin/users/mario`  
**(Requires ADMIN token)**

**Response:**
```json
{
  "id": 10,
  "username": "mario",
  "email": "mario@mario.com",
  "reviews": [],
  "roles": [{ "authority": "ROLE_USER" }],
  "profilePicture": null
}
```

---

### 5. Homepage

**GET** `/`

**Response:**
```
"Welcome to Rate My Teacher app!"
```

---

### 6. Universities

**Base Endpoint** `/api/universities`

#### 6.1 Get All Universities
**GET** `/api/universities`

**No authentication required**
**Response:**
```json
[
    {
        "id": 4,
        "name": "Unibuc",
        "rating": 6.5,
        "city": "Bucharest",
        "profilePicture": null,
        "teachers": [
            {
                "id": 5,
                "name": "Dr. Andrei Popescu",
                "rating": 0.0,
                "universityId": 4,
                "teacherPicture": null
            },
            {
                "id": 7,
                "name": "Dr. Andrei Popescu",
                "rating": 7.0,
                "universityId": 4,
                "teacherPicture": null
            },
            {
                "id": 8,
                "name": "Dr. Popescu",
                "rating": 0.0,
                "universityId": 4,
                "teacherPicture": null
            }
        ]
    }
  ...
]
```

#### 6.2 Get University by ID
**GET** `/api/universities/{id}`

**Example:** `/api/universities/4`

**No authentication required**

**Response:**
```json
{
    "id": 4,
    "name": "Unibuc",
    "rating": 6.5,
    "city": "Bucharest",
    "profilePicture": null,
    "teachers": [
        {
            "id": 5,
            "name": "Dr. Andrei Popescu",
            "rating": 0.0,
            "universityId": 4,
            "teacherPicture": null
        },
        {
            "id": 7,
            "name": "Dr. Andrei Popescu",
            "rating": 7.0,
            "universityId": 4,
            "teacherPicture": null
        },
        {
            "id": 8,
            "name": "Dr. Popescu",
            "rating": 0.0,
            "universityId": 4,
            "teacherPicture": null
        }
    ]
}
```

#### 6.3 Add Review for University
**POST** `/api/universities/{id}/reviews`

**Example:** `/api/universities/4/reviews`

**(Requires USER or ADMIN token)**

**Body:**

```json
{
  "content": "Great university!",
  "rating": 8.5
}
```

**Response:**
```json
{
  "id": null,
  "content": "Great university!",
  "rating": 8.5,
  "createdAt": "2025-05-14T18:28:32.491+00:00",
  "authorUsername": "MarioBoss",
  "subjectId": "4"
}
```

#### 6.4 Create University
**POST** `/api/universities`

**(Requires ADMIN token)**

**Body:**
```json
{
  "name": "Unibuc",
  "city": "Bucharest",
  "profilePicture": null
}
```

**Response:**
```json
{
  "id": 9,
  "name": "ASE",
  "rating": null,
  "city": "Bucharest",
  "profilePicture": null,
  "teachers": null
}
```

#### 6.5 Update University
**PUT** `/api/universities/{id}`

**Example:** `/api/universities/4`

**(Requires ADMIN token)**

**Body:**
```json
{
  "name": "ASE",
  "city": "Buzau",
  "profilePicture": null
}
```

**Response:**
```json
{
  "id": 9,
  "name": "ASE",
  "rating": null,
  "city": "Buzau",
  "profilePicture": null,
  "teachers": []
}
```

#### 6.6 Delete University
**DELETE** `/api/universities/{id}`

**Example:** `/api/universities/9`

**(Requires ADMIN token)**

**Response:** HTTP 200 OK

---

### 7. Teachers

**Base Endpoint** `/api/teachers`

#### 7.1 Get All Teachers

**GET** `/api/teachers`

**No authentication required**

**Response:**
```json
[
    {
        "id": 5,
        "name": "Dr. Andrei Popescu",
        "rating": 0.0,
        "universityId": 4,
        "teacherPicture": null
    },
    {
        "id": 7,
        "name": "Dr. Andrei Popescu",
        "rating": 7.0,
        "universityId": 4,
        "teacherPicture": null
    },
    {
        "id": 8,
        "name": "Dr. Popescu",
        "rating": 0.0,
        "universityId": 4,
        "teacherPicture": null
    }
]
```

#### 7.2 Get Teacher by ID
**GET** `/api/teachers/{id}`

**Example:** `/api/teachers/5`

**No authentication required**

**Response:**

```json
{
    "id": 5,
    "name": "Dr. Andrei Popescu",
    "rating": 0.0,
    "universityId": 4,
    "teacherPicture": null
}
```

#### 7.3 Add Review for Teacher

**POST** `/api/teachers/{id}/reviews`

**Example:** `/api/teachers/7/reviews`

**(Requires USER or ADMIN token)**

**Body:**
```json
{
  "content": "Great teacher!",
  "rating": 8.5
}
```

**Response:**
```json
{
  "id": null,
  "content": "Great teacher!",
  "rating": 8.5,
  "createdAt": "2025-05-14T18:30:59.389+00:00",
  "authorUsername": "MarioUser",
  "subjectId": "7"
}
```

#### 7.4 Create Teacher
**POST** `/api/teachers`

**(Requires ADMIN token)**

**Body:**
```json
{
  "name": "Dr. Mario",
  "universityId": 4,
  "teacherPicture": null
}
```

**Response:**
```json
{
  "id": 10,
  "name": "Dr. Mario",
  "rating": null,
  "universityId": 4,
  "teacherPicture": null
}
```

#### 7.5 Update Teacher
**PUT** `/api/teachers/{id}`

**(Requires ADMIN token)**

#### 7.6 Delete Teacher
**DELETE** `/api/teachers/{id}`

**Example:** `/api/teachers/10`

**(Requires ADMIN token)**

**Response:** HTTP 200 OK or HTTP 404 Not Found

---

### 8. Reviews
**Base Endpoint** `/api/reviews`

#### 8.1 Get All Given By a User

**GET** `/api/reviews/user/{userId}`

**(Requires USER or ADMIN token)**

**Example:** `/api/reviews/user/6`

**Response:**
```json
[
  {
    "id": 1,
    "content": "This teacher is very knowledgeable and explains concepts clearly. The assignments were helpful for understanding the material.",
    "rating": 4.5,
    "createdAt": "2025-05-05T09:01:25.767+00:00",
    "authorUsername": "gabriel",
    "subjectId": "7"
  }
]
```

#### 8.2 Get All Reviews for a Teacher

**GET** `/api/reviews/teacher/{teacherId}`

**(Requires USER or ADMIN token)**

**Example:** `/api/reviews/teacher/4`

**Response:**
```json
[
  {
    "id": 3,
    "content": "The campus facilities are excellent and professors are very helpful. The library is well-stocked with resources, and the student support services are exceptional. The cafeteria could use some improvement though.",
    "rating": 4.5,
    "createdAt": null,
    "authorUsername": "MarioBoss",
    "subjectId": "4"
  },
  {
    "id": 4,
    "content": "The campus facilities are excellent and professors are very helpful. The library is well-stocked with resources, and the student support services are exceptional. The cafeteria could use some improvement though.",
    "rating": 8.5,
    "createdAt": "2025-05-13T16:48:48.099+00:00",
    "authorUsername": "MarioBoss",
    "subjectId": "4"
  }
]
```

#### 8.3 Get All Reviews for a University

**GET** `/api/reviews/university/{universityId}`

**(Requires USER or ADMIN token)**

**Example:** `/api/reviews/university/4`

**Response:**
```json
[
  {
    "id": 3,
    "content": "The campus facilities are excellent and professors are very helpful. The library is well-stocked with resources, and the student support services are exceptional. The cafeteria could use some improvement though.",
    "rating": 4.5,
    "createdAt": null,
    "authorUsername": "MarioBoss",
    "subjectId": "4"
  },
  {
    "id": 4,
    "content": "The campus facilities are excellent and professors are very helpful. The library is well-stocked with resources, and the student support services are exceptional. The cafeteria could use some improvement though.",
    "rating": 8.5,
    "createdAt": "2025-05-13T16:48:48.099+00:00",
    "authorUsername": "MarioBoss",
    "subjectId": "4"
  }
]
```

### 8.4 Get Pending Reviews
**GET** `/api/reviews/pending`

**(Requires ADMIN token)**

**Response:**
```json
[
  {
    "id": 1,
    "content": "This teacher is very knowledgeable and explains concepts clearly. The assignments were helpful for understanding the material.",
    "rating": 4.5,
    "createdAt": "2025-05-05T09:01:25.767+00:00",
    "authorUsername": "gabriel",
    "subjectId": "7"
  }
]
```

#### 8.5 Approve Review
**PUT** `/api/reviews/{id}/approve`

**Example:** `/api/reviews/1/approve`

**(Requires ADMIN token)**

**Response:**
```json
{
  "id": 1,
  "content": "This teacher is very knowledgeable and explains concepts clearly. The assignments were helpful for understanding the material.",
  "rating": 4.5,
  "createdAt": "2025-05-05T09:01:25.767+00:00",
  "authorUsername": "gabriel",
  "subjectId": "7"
}
```

#### 8.6 Get all reviews

**GET** `/api/reviews`

**(Requires ADMIN token)**

**Response:**
```json
[
  {
    "id": 1,
    "content": "This teacher is very knowledgeable and explains concepts clearly. The assignments were helpful for understanding the material.",
    "rating": 4.5,
    "createdAt": "2025-05-05T09:01:25.767+00:00",
    "authorUsername": "gabriel",
    "subjectId": "7"
  }
]
```

#### 8.7 Refuse Review
**DELETE** `/api/reviews/{id}`

**Example:** `/api/reviews/1`

**(Requires ADMIN token)**

**Response:** HTTP 200 OK or HTTP 404 Not Found
