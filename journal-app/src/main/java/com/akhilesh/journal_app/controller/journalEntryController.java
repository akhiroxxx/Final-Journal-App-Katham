package com.akhilesh.journal_app.controller;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akhilesh.journal_app.entity.JournalEntry;
import com.akhilesh.journal_app.service.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class journalEntryController {
  

  @Autowired
  private JournalEntryService journalEntryService;


  @GetMapping
  public ResponseEntity<?> getAll(){
   List<JournalEntry> all =  journalEntryService.getAll();
   if(all!=null && !all.isEmpty()){
    return new ResponseEntity<>(all,HttpStatus.OK);
   }
   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
  

  @PostMapping
  public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry newEntry){
    try {
      newEntry.setDate(LocalDateTime.now());
      journalEntryService.saveEntry(newEntry);
      return new ResponseEntity<>(newEntry, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(value = "id/{mid}")
  public ResponseEntity<JournalEntry> getJournalById(@PathVariable("mid") ObjectId myId){
    Optional<JournalEntry> j= journalEntryService.findById(myId);
    if(j.isPresent())
    {
      return new ResponseEntity<>(j.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/id/{myId}")
  public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myId){
    journalEntryService.deleteById(myId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("/id/{myId}")
  public ResponseEntity<?> updateJournalById(@RequestBody JournalEntry newEntry,@PathVariable ObjectId myId){
    JournalEntry old=journalEntryService.findById(myId).orElse(null);
    if(old!=null){
      old.setTitle(newEntry.getTitle()!=null&&!newEntry.getTitle().equals("")?newEntry.getTitle():old.getTitle());
      old.setContent(newEntry.getContent()!=null&&!newEntry.getContent().equals("")?newEntry.getContent():old.getContent());
      journalEntryService.saveEntry(old);
      return new ResponseEntity<>(old,HttpStatus.OK);
    } 
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    
  }


}
