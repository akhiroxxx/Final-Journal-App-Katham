package com.akhilesh.journal_app.controller;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.extern.log4j.Log4j2;
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
import com.akhilesh.journal_app.entity.User;
import com.akhilesh.journal_app.service.JournalEntryService;
import com.akhilesh.journal_app.service.UserService;

@Log4j2
@RestController
@RequestMapping("/journal")
public class journalEntryController {
  

  @Autowired
  private JournalEntryService journalEntryService;

  @Autowired
  private UserService userService;

  @GetMapping("{userName}")
  public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
    User user = userService.findByUserName(userName);
  //  List<JournalEntry> all =  journalEntryService.getAll();/
  List<JournalEntry> all = user.getJournalEntries();
   if(all!=null && !all.isEmpty()){
    return new ResponseEntity<>(all,HttpStatus.OK);
   }
   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
  

  @PostMapping("{userName}")
  public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry newEntry, @PathVariable String userName){
    try {
      newEntry.setDate(LocalDateTime.now());
      journalEntryService.saveEntry(newEntry, userName);
      return new ResponseEntity<>(newEntry, HttpStatus.OK);
    } catch (Exception e) {
      log.error("e: ", e);
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

  @DeleteMapping("/id/{userName}/{myId}")
  public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myId, @PathVariable String userName){
    journalEntryService.deleteById(myId,userName);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("/id/{userName}/{myId}")
  public ResponseEntity<?> updateJournalById(@RequestBody JournalEntry newEntry,@PathVariable ObjectId myId,
  @PathVariable String userName){
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
