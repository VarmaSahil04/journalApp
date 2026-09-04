# Journal App

A RESTful Journal Application built while learning Spring Boot through the Engineering Digest Spring Boot playlist.

## Tech Stack

* Java
* Spring Boot
* Spring Web
* Maven

## Features Implemented

* Health Check API
* Create Journal Entry
* Get All Journal Entries
* Get Journal Entry by ID
* Update Journal Entry
* Delete Journal Entry

---

## REST API Endpoints

### Health Check

**GET**

```text
/health-check
```

Response:

```text
Ok
```

---

### Get All Journal Entries

**GET**

```text
/journal
```

Returns all journal entries.

---

### Create Journal Entry

**POST**

```text
/journal
```

Example Request Body:

```json
{
    "id": 1,
    "title": "My First Journal",
    "content": "Learning Spring Boot REST APIs"
}
```

---

### Get Journal Entry by ID

**GET**

```text
/journal/id/{myId}
```

Example:

```text
/journal/id/1
```

---

### Update Journal Entry

**PUT**

```text
/journal/id/{id}
```

Example Request Body:

```json
{
    "id": 1,
    "title": "Updated Journal",
    "content": "Updated content"
}
```

---

### Delete Journal Entry

**DELETE**

```text
/journal/id/{myId}
```

Example:

```text
/journal/id/1
```

---

## Project Structure

```text
src/main/java/net/engineeringdigest/journalApp
│
├── JournalApplication.java
│
├── controller
│   ├── HealthCheck.java
│   └── journalEntryController.java
│
└── entity
    └── journalEntry.java
```

---

## Current Implementation

Currently, journal entries are stored temporarily using an in-memory `HashMap`.

```java
Map<Long, journalEntry> journalEntries = new HashMap<>();
```

⚠️ Data will be lost whenever the application is restarted.

Database integration will be added in future stages of the project.

---

## Learning Progress

* [x] Maven Basics
* [x] Spring Boot Project Setup
* [x] Creating REST APIs
* [x] Health Check Endpoint
* [x] CRUD Operations using HashMap
* [ ] Database Integration
* [ ] MongoDB
* [ ] Spring Data MongoDB
* [ ] Spring Security

