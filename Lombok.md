# Lombok in Spring Boot

## What is Lombok?

**Project Lombok** is a Java library that helps reduce boilerplate code.

Instead of manually writing:

* Getters
* Setters
* Constructors
* `toString()`
* `equals()`
* `hashCode()`

Lombok automatically generates them during compilation using annotations.

---

# Adding Lombok Dependency

Add the following dependency to the `pom.xml` file:

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.38</version>
    <scope>provided</scope>
</dependency>
```

### Why is `scope` set to `provided`?

The Lombok library is required during **compilation** to generate the required code, but it is not required at runtime.

---

# Common Lombok Annotations

## `@Getter`

Generates getter methods for the fields.

```java
@Getter
public class User {

    private String name;
}
```

Lombok generates:

```java
public String getName() {
    return name;
}
```

---

## `@Setter`

Generates setter methods for the fields.

```java
@Setter
public class User {

    private String name;
}
```

Lombok generates:

```java
public void setName(String name) {
    this.name = name;
}
```

---

## `@Data`

`@Data` is one of the most commonly used Lombok annotations.

```java
@Data
public class User {

    private String name;
}
```

It automatically generates:

* Getters
* Setters
* `toString()`
* `equals()`
* `hashCode()`
* Required constructor

> **Note:** `@Data` does not automatically mean `@NoArgsConstructor`. A no-argument constructor should be added explicitly using `@NoArgsConstructor` when required.

---

# Example: JournalEntry Entity

```java
package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
@Data
public class JournalEntry {

    @Id
    private ObjectId id;

    private String title;

    private String content;

    private LocalDateTime date;
}
```

---

# Explanation of Annotations

### `@Document`

```java
@Document(collection = "journal_entries")
```

This annotation tells **Spring Data MongoDB** that this class represents a MongoDB document.

The data will be stored in the following collection:

```text
journal_entries
```

---

### `@Id`

```java
@Id
private ObjectId id;
```

This field represents the unique identifier of the MongoDB document.

MongoDB automatically generates an `ObjectId` when a new document is created.

---

### `@Data`

```java
@Data
```

Lombok automatically generates getter and setter methods for all fields during compilation.

For example:

```java
journalEntry.setTitle("My Journal");

String title = journalEntry.getTitle();
```

These methods work even though we have not manually written them.

---

# Before Using Lombok

Without Lombok, we would need to manually write:

```java
public String getTitle() {
    return title;
}

public void setTitle(String title) {
    this.title = title;
}

public String getContent() {
    return content;
}

public void setContent(String content) {
    this.content = content;
}
```

---

# With Lombok

Using:

```java
@Data
```

Lombok automatically generates all the required getter and setter methods.

This makes the code:

* Cleaner
* Shorter
* Easier to maintain
* Less repetitive

---

# Important Note

Lombok generates code during the **compilation process**.

The generated getter and setter methods are not manually visible in the source code, but they can still be used normally.

```java
journalEntry.setTitle("Learning Spring Boot");

System.out.println(journalEntry.getTitle());
```

---

# Dependencies and Technologies Used

* Java
* Spring Boot
* Maven
* Lombok
* MongoDB
* Spring Data MongoDB

---

## What I Learned

* What Lombok is and why it is used.
* How to add Lombok dependency in Maven.
* How `@Getter` works.
* How `@Setter` works.
* How `@Data` reduces boilerplate code.
* How Lombok generates code during compilation.
* How Lombok can be used inside Spring Boot entity classes.
* The difference between manually writing getters/setters and using Lombok annotations.
