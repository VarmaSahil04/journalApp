# ResponseEntity and HTTP Status Codes in Spring Boot

## What is ResponseEntity?

`ResponseEntity` is a class provided by Spring Boot that represents the **complete HTTP response**.

It allows us to control:

* Response Body
* HTTP Status Code
* HTTP Headers

### Syntax

```java
ResponseEntity<T>
```

Here, `T` represents the type of data returned in the response.

For example:

```java
ResponseEntity<JournalEntry>
```

This means the response body contains a `JournalEntry` object.

---

# Why Use ResponseEntity?

Without `ResponseEntity`, Spring Boot automatically decides the HTTP status code.

Using `ResponseEntity`, we can explicitly control the response.

Example:

```java
return new ResponseEntity<>(journalEntry, HttpStatus.OK);
```

This returns:

* Response Body → `journalEntry`
* HTTP Status Code → `200 OK`

---

# ResponseEntity Syntax

```java
new ResponseEntity<>(body, status);
```

Example:

```java
return new ResponseEntity<>(all, HttpStatus.OK);
```

Where:

* `all` → Response Body
* `HttpStatus.OK` → HTTP Status Code

---

# HTTP Status Codes Used

## 1. 200 OK

```java
HttpStatus.OK
```

Status Code:

```text
200 OK
```

It means the request was successfully completed.

### Example

```java
return new ResponseEntity<>(all, HttpStatus.OK);
```

Used when:

* Data is successfully fetched.
* Journal entry is successfully updated.

---

## 2. 201 CREATED

```java
HttpStatus.CREATED
```

Status Code:

```text
201 CREATED
```

It means a new resource was successfully created.

### Example

```java
return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
```

Used when:

* A new journal entry is successfully created.

---

## 3. 204 NO CONTENT

```java
HttpStatus.NO_CONTENT
```

Status Code:

```text
204 NO CONTENT
```

It means the request was successfully completed, but there is no response body.

### Example

```java
return new ResponseEntity<>(HttpStatus.NO_CONTENT);
```

Used when:

* A journal entry is successfully deleted.

---

## 4. 400 BAD REQUEST

```java
HttpStatus.BAD_REQUEST
```

Status Code:

```text
400 BAD REQUEST
```

It means the server cannot process the request because the request is invalid.

### Example

```java
catch (Exception e) {
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
}
```

Used when:

* Invalid request data is sent.
* An error occurs while processing the request.

---

## 5. 404 NOT FOUND

```java
HttpStatus.NOT_FOUND
```

Status Code:

```text
404 NOT FOUND
```

It means the requested resource was not found.

### Example

```java
return new ResponseEntity<>(HttpStatus.NOT_FOUND);
```

Used when:

* No journal entries are found.
* A journal entry with the given ID does not exist.

---

# ResponseEntity in JournalEntryController

## Get All Journal Entries

```java
@GetMapping
public ResponseEntity<?> getAllJournalEntries() {

    List<JournalEntry> all = journalEntryService.getAll();

    if (all != null && !all.isEmpty()) {
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
```

### Possible Responses

| Condition                | Status Code     |
| ------------------------ | --------------- |
| Journal entries found    | `200 OK`        |
| No journal entries found | `404 NOT FOUND` |

---

# Create Journal Entry

```java
@PostMapping
public ResponseEntity<JournalEntry> createJournalEntry(
        @RequestBody JournalEntry myEntry) {

    try {
        myEntry.setDate(LocalDateTime.now());

        journalEntryService.saveEntry(myEntry);

        return new ResponseEntity<>(
                myEntry,
                HttpStatus.CREATED
        );

    } catch (Exception e) {
        return new ResponseEntity<>(
                HttpStatus.BAD_REQUEST
        );
    }
}
```

### Possible Responses

| Condition                          | Status Code       |
| ---------------------------------- | ----------------- |
| Journal entry created successfully | `201 CREATED`     |
| Invalid request or error           | `400 BAD REQUEST` |

---

# Find Journal Entry by ID

```java
@GetMapping("id/{myId}")
public ResponseEntity<?> findJournalEntryById(
        @PathVariable ObjectId myId) {

    Optional<JournalEntry> journalEntry =
            journalEntryService.findById(myId);

    if (journalEntry.isPresent()) {
        return new ResponseEntity<>(
                journalEntry.get(),
                HttpStatus.OK
        );
    }

    return new ResponseEntity<>(
            HttpStatus.NOT_FOUND
    );
}
```

### Possible Responses

| Condition               | Status Code     |
| ----------------------- | --------------- |
| Journal entry found     | `200 OK`        |
| Journal entry not found | `404 NOT FOUND` |

---

# Delete Journal Entry by ID

```java
@DeleteMapping("id/{myId}")
public ResponseEntity<?> deleteJournalEntryById(
        @PathVariable ObjectId myId) {

    journalEntryService.deleteById(myId);

    return new ResponseEntity<>(
            HttpStatus.NO_CONTENT
    );
}
```

### Response

| Condition                          | Status Code      |
| ---------------------------------- | ---------------- |
| Journal entry deleted successfully | `204 NO CONTENT` |

---

# Update Journal Entry by ID

```java
@PutMapping("id/{id}")
public ResponseEntity<?> updateJournalEntryById(
        @PathVariable ObjectId id,
        @RequestBody JournalEntry newEntry) {

    JournalEntry old =
            journalEntryService.findById(id).orElse(null);

    if (old != null) {

        old.setTitle(
                newEntry.getTitle() != null &&
                !newEntry.getTitle().equals("")
                        ? newEntry.getTitle()
                        : old.getTitle()
        );

        old.setContent(
                newEntry.getContent() != null &&
                !newEntry.getContent().equals("")
                        ? newEntry.getContent()
                        : old.getContent()
        );

        journalEntryService.saveEntry(old);

        return new ResponseEntity<>(
                old,
                HttpStatus.OK
        );
    }

    return new ResponseEntity<>(
            HttpStatus.NOT_FOUND
    );
}
```

### Possible Responses

| Condition                          | Status Code     |
| ---------------------------------- | --------------- |
| Journal entry updated successfully | `200 OK`        |
| Journal entry not found            | `404 NOT FOUND` |

---

# What Does `ResponseEntity<?>` Mean?

```java
ResponseEntity<?>
```

The `?` is called a **Wildcard**.

It means the response can contain **different types of objects**.

For example:

```java
public ResponseEntity<?> getAllJournalEntries()
```

This method can return:

```java
List<JournalEntry>
```

or it can return:

```java
No Response Body
```

Therefore, `ResponseEntity<?>` provides flexibility when different responses are possible.

---

# Common HTTP Status Codes

| Status Code | HttpStatus                         | Meaning                                  |
| ----------- | ---------------------------------- | ---------------------------------------- |
| 200         | `HttpStatus.OK`                    | Request successful                       |
| 201         | `HttpStatus.CREATED`               | Resource successfully created            |
| 204         | `HttpStatus.NO_CONTENT`            | Successful request with no response body |
| 400         | `HttpStatus.BAD_REQUEST`           | Invalid request                          |
| 404         | `HttpStatus.NOT_FOUND`             | Resource not found                       |
| 500         | `HttpStatus.INTERNAL_SERVER_ERROR` | Server error                             |

---

# Important Takeaway

`ResponseEntity` gives us control over the HTTP response.

It allows us to send:

```text
Response Body + HTTP Status Code
```

Example:

```java
return new ResponseEntity<>(
        journalEntry,
        HttpStatus.OK
);
```

---

# What I Learned

* What `ResponseEntity` is in Spring Boot.
* How to return a response body with an HTTP status code.
* How to use `HttpStatus`.
* The meaning of `200 OK`.
* The meaning of `201 CREATED`.
* The meaning of `204 NO CONTENT`.
* The meaning of `400 BAD REQUEST`.
* The meaning of `404 NOT FOUND`.
* How `ResponseEntity<?>` uses a wildcard for flexible response types.
* How to return appropriate HTTP status codes in REST APIs.
