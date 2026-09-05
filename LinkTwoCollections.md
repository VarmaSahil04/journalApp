# MongoDB Relationships

## Linking Two Collections: User and JournalEntry

In this section, I learned how to create a relationship between two MongoDB collections using Spring Boot.

The two collections are:

* `users`
* `journal_entries`

### Relationship

One **User** can have multiple **Journal Entries**.

```text
User
 │
 ├── Journal Entry 1
 ├── Journal Entry 2
 └── Journal Entry 3
```

This represents a **One-to-Many Relationship**.

---

## User Entity

The relationship is created using `@DBRef`.

```java
@Document(collection = "users")
@Data
public class User {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String userName;

    @NonNull
    private String password;

    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
}
```

### `@DBRef`

```java
@DBRef
private List<JournalEntry> journalEntries;
```

`@DBRef` stores references to the journal entries associated with a particular user.

---

## Username Indexing

```java
@Indexed(unique = true)
private String userName;
```

The username is marked as unique to prevent duplicate usernames.

To enable automatic index creation:

```properties
spring.data.mongodb.auto-index-creation=true
```

---

## Workflow for Creating a Journal Entry

When creating a journal entry for a particular user:

```text
JournalEntryController
        │
        ▼
JournalEntryService
        │
        ├── Find User using userName
        │
        ▼
UserService
        │
        ▼
UserRepository
        │
        ▼
MongoDB
        │
        ▼
Save JournalEntry
        │
        ▼
Add JournalEntry to User's journalEntries List
        │
        ▼
Save Updated User
```

---

## Saving Journal Entry for a User

The service method performs the following operations:

1. Find the user using `userName`.
2. Save the `JournalEntry`.
3. Add the saved journal entry to the user's journal entry list.
4. Save the updated user.

```java
public void saveEntry(JournalEntry journalEntry, String userName) {

    User user = userService.findByUserName(userName);

    journalEntry.setDate(LocalDateTime.now());

    JournalEntry saved = journalEntryRepository.save(journalEntry);

    user.getJournalEntries().add(saved);

    userService.saveUser(user);
}
```

---

## Collections Relationship

```text
users Collection
       │
       │ @DBRef
       ▼
journalEntries Collection
```

A user document contains references to the journal entries created by that user.

---

## Concepts Learned

* MongoDB Relationships
* One-to-Many Relationship
* `@DBRef`
* `@Indexed`
* Unique Index
* `UserRepository`
* `UserService`
* Connecting User and JournalEntry Collections
