package com.akhilesh.journal_app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.akhilesh.journal_app.Repository.JournalEntryRepository;
import com.akhilesh.journal_app.entity.JournalEntry;
import com.akhilesh.journal_app.entity.User;


@Component
public class JournalEntryService {

  @Autowired
  private JournalEntryRepository journalEntryRepository;

  @Autowired
  private UserService userService;

  public void saveEntry(JournalEntry journalEntry){
    journalEntryRepository.save(journalEntry);
  }


  @Transactional
  public void saveEntry(JournalEntry journalEntry, String userName){
    User user = userService.findByUserName(userName);
    journalEntry.setDate(LocalDateTime.now());
    user.getJournalEntries().add(journalEntry);
    journalEntryRepository.save(journalEntry);
    userService.saveEntry(user);
  }


  public List<JournalEntry> getAll(){
    return journalEntryRepository.findAll();
  }


  public Optional<JournalEntry> findById(ObjectId id){
    return journalEntryRepository.findById(id);
  }


  public void deleteById(ObjectId id, String userName){
    journalEntryRepository.deleteById(id);
    User user = userService.findByUserName(userName);
    user.getJournalEntries().removeIf(x->x.getId().equals(id));
    userService.saveEntry(user);
  } 



  
}
