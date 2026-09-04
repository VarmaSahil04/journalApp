package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class journalEntryController {

    private Map<Long , JournalEntry> journalEntries = new HashMap<>();



    @GetMapping
    public List<JournalEntry> getAll(){

        return new ArrayList<>(journalEntries.values());
    }


    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
       // journalEntries.put(my,myEntry);
       return true;
    }


    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntrybyId(@PathVariable Long myId){
        return journalEntries.get(myId);
    }


    @DeleteMapping("id/{myId}")
    public JournalEntry deleteJournalEntrybyId(@PathVariable Long myId){

        return journalEntries.remove(myId);

    }

    @PutMapping("id/{id}")
    public JournalEntry updateJournalbyId(@PathVariable Long id , @RequestBody JournalEntry myEntry){

       return journalEntries.put(id,myEntry);

    }




}
