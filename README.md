# Journal App

A RESTful Journal Application built while learning Spring Boot through the **Engineering Digest Spring Boot playlist**.

The project started with in-memory CRUD operations using `HashMap` and has now been extended with **MongoDB integration using Spring Data MongoDB**.

## Tech Stack

* Java
* Spring Boot
* Spring Web
* Spring Data MongoDB
* MongoDB
* Maven
* Postman

## Features Implemented

* Health Check API
* Create Journal Entry
* Get All Journal Entries
* Get Journal Entry by ID
* Update Journal Entry
* Delete Journal Entry
* MongoDB Database Integration
* Spring Data MongoDB Repository
* Automatic `ObjectId` generation
* Automatic date/time for journal entries

---

# REST API Endpoints

## Health Check

**GET**

```text
/health-check
```

Response:

```text
Ok
```

---

## Get All Journal Entries

**GET**

```text
/journal
```

Returns all journal entries stored in MongoDB.

---

## Create Journal Entry

**POST**

```text
/journal
```

Example Request Body:

```json
{
    "title": "My First Journal",
    "content": "Learning Spring Boot and MongoDB"
}
```

The `date` is automatically generated using:

```java
myEntry.setDate(LocalDateTime.now());
```

MongoDB automatically generates the `_id` using `ObjectId`.

---

## Get Journal Entry by ID

**GET**

```text
/journal/id/{myId}
```

Example:

```text
/journal/id/6a9ad1ce684d805fb067e122
```

---

## Update Journal Entry

**PUT**

```text
/journal/id/{id}
```

Example Request Body:

```json
{
    "title": "Updated Journal",
    "content": "Learning Spring Boot MongoDB Integration"
}
```

The existing journal entry is first retrieved from MongoDB and then updated.

---

## Delete Journal Entry

**DELETE**

```text
/journal/id/{myId}
```

Example:

```text
/journal/id/6a9ad1ce684d805fb067e122
```

---

# MongoDB Integration

MongoDB is configured in:

```text
src/main/resources/application.properties
```

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=journaldb
```

The application connects to:

```text
MongoDB
   ↓
localhost:27017
   ↓
journaldb
   ↓
journal_entries
```

---

# Entity – JournalEntry

The `JournalEntry` class is mapped to the MongoDB collection using `@Document`.

```java
package net.engineeringdigest.journalApp.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
public class JournalEntry {

    @Id
    private ObjectId id;

    private String title;

    private String content;

    private LocalDateTime date;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
```

### Important Annotations

### `@Document`

```java
@Document(collection = "journal_entries")
```

Maps the Java class to the MongoDB collection:

```text
journal_entries
```

### `@Id`

```java
@Id
private ObjectId id;
```

Maps the Java `id` field to MongoDB's `_id` field.

---

# Repository Layer

Spring Data MongoDB provides the `MongoRepository` interface for database operations.

```java
package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository
        extends MongoRepository<JournalEntry, ObjectId> {

}
```

By extending `MongoRepository`, we get built-in CRUD methods such as:

```java
save()
findAll()
findById()
deleteById()
existsById()
count()
```

No manual MongoDB queries are required for these basic operations.

---

# Service Layer

The service layer communicates with the repository.

```java
@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }
}
```

---

# Application Architecture

The application follows a layered architecture:

```text
Client / Postman
       ↓
   Controller
       ↓
    Service
       ↓
   Repository
       ↓
    MongoDB
```

### Flow

```text
POST /journal
      ↓
Controller
      ↓
JournalEntryService
      ↓
JournalEntryRepository
      ↓
MongoDB
      ↓
journaldb
      ↓
journal_entries
```

This keeps the responsibilities separated between the different layers.

---

# MongoDB Data

The application uses:

**Database**

```text
journaldb
```

**Collection**

```text
journal_entries
```

Example MongoDB document:

```json
{
    "_id": "ObjectId(...)",
    "title": "Morning",
    "content": "Went to gym",
    "date": "2026-09-04T14:12:30.702Z",
    "_class": "net.engineeringdigest.journalApp.entity.JournalEntry"
}
```

Another example:

```json
{
    "_id": "ObjectId(...)",
    "title": "Evening",
    "content": "I was Sad",
    "date": "2026-09-04T18:49:38.341Z",
    "_class": "net.engineeringdigest.journalApp.entity.JournalEntry"
}
```

---

# MongoDB Commands

Select the database:

```javascript
use journaldb
```

View collections:

```javascript
show collections
```

View journal entries:

```javascript
db.journal_entries.find()
```

---

# Project Structure

```text
src/main/java/net/engineeringdigest/journalApp
│
├── JournalApplication.java
│
├── controller
│   ├── HealthCheck.java
│   ├── journalEntryController.java
│   └── journalEntryControllerV2.java
│
├── entity
│   └── JournalEntry.java
│
├── repository
│   └── JournalEntryRepository.java
│
└── service
    └── JournalEntryService.java

src/main/resources
└── application.properties
```

---

# Previous Implementation

Initially, journal entries were stored temporarily using an in-memory `HashMap`.

```java
Map<Long, journalEntry> journalEntries = new HashMap<>();
```

With this implementation, all data was lost whenever the application was restarted.

The application has now been upgraded to use MongoDB for persistent storage.

### Earlier

```text
Controller
    ↓
HashMap
```

### Current

```text
Controller
    ↓
Service
    ↓
MongoRepository
    ↓
MongoDB
```

---

# Learning Progress

* [x] Maven Basics
* [x] Spring Boot Project Setup
* [x] Creating REST APIs
* [x] Health Check Endpoint
* [x] CRUD Operations using HashMap
* [x] MongoDB Integration
* [x] Spring Data MongoDB
* [x] MongoRepository
* [x] MongoDB Documents and Collections
* [x] ObjectId
* [x] Repository Layer
* [x] Service Layer
* [ ] Spring Security

---

# Key Concepts Learned

Through this stage of the project, I learned:

* MongoDB integration with Spring Boot
* Spring Data MongoDB
* `@Document`
* `@Id`
* `MongoRepository`
* `ObjectId`
* MongoDB collections and documents
* CRUD operations using Spring Data
* Repository and Service layers
* Dependency Injection using `@Autowired`
* Mapping Java objects to MongoDB documents
* Using `LocalDateTime`
* Connecting Spring Boot with a local MongoDB database

---

# Learning Resource

This project is being developed while following the **Engineering Digest Spring Boot playlist** and implementing the concepts practically.

---

# Future Improvements

Planned improvements include:

* Spring Security
* User authentication and authorization
* User-specific journal entries
* Validation
* Exception handling
* Improved API responses
* More advanced MongoDB queries
* Custom repository methods
