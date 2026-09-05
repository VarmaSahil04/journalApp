package net.engineeringdigest.journalApp.entity;


import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.*;

@Document(collection = "users")
@Data
public class User {

    @Id
    private ObjectId id; // Primary Key
    @Indexed(unique=true)
    @NonNull
    private String userName; // Unique
    @NonNull
    private String password;
       // UserName aur Password hoga tabhi tho login kar payega

    @DBRef // Created Parent-Child RelationShip
    private List<JournalEntry> journalEntries = new ArrayList<>();

}
