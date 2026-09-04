package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.journalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class journalEntryController {

    private Map<Long , journalEntry> journalEntries = new HashMap<>();



    @GetMapping
    public List<journalEntry> getAll(){

        return new ArrayList<>(journalEntries.values());
    }


    @PostMapping
    public boolean createEntry(@RequestBody journalEntry myEntry){
       journalEntries.put(myEntry.getId(),myEntry);
       return true;
    }


    @GetMapping("id/{myId}")
    public journalEntry getJournalEntrybyId(@PathVariable Long myId){
        return journalEntries.get(myId);
    }


    @DeleteMapping("id/{myId}")
    public journalEntry deleteJournalEntrybyId(@PathVariable Long myId){

        return journalEntries.remove(myId);

    }

    @PutMapping("id/{id}")
    public journalEntry updateJournalbyId(@PathVariable Long id , @RequestBody journalEntry myEntry){

       return journalEntries.put(id,myEntry);

    }




}
