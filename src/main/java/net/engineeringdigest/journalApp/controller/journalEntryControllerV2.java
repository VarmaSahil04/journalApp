package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class journalEntryControllerV2 {

    @Autowired
    private JournalEntryService  journalEntryService;


    @GetMapping
    public List<JournalEntry> getAllJournalEntries(){
      return journalEntryService.getAll();
    }

    @PostMapping()
    public boolean createJournalEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now()); // Date Automatically set
        journalEntryService.saveEntry(myEntry);
        return true;
    }

     @GetMapping("id/{myId}")
     public JournalEntry findJournalEntryById(@PathVariable  ObjectId myId){
        return journalEntryService.findById(myId).orElse(null);
     }

     @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId myId){
         journalEntryService.deleteById(myId);
         return true;
     }

     @PutMapping("id/{id}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId id , @RequestBody JournalEntry newEntry){
         JournalEntry old = journalEntryService.findById(id).orElse(null);
         if(old != null){
              old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());

              old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
         }

         journalEntryService.saveEntry(old);
        return old;
     }


}
