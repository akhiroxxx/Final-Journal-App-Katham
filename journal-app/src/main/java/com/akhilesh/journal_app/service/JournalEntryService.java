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


//  @Transactional  ---  good to have it but check later why it is not working
  public void saveEntry(JournalEntry journalEntry, String userName) {
    try {
      User user = userService.findByUserName(userName);
      journalEntry.setDate(LocalDateTime.now());
      JournalEntry saved = journalEntryRepository.save(journalEntry);
      user.getJournalEntries().add(saved);
      userService.saveEntry(user);
    } catch (Exception e) {
      throw new RuntimeException("An error occurred while saving the entry.", e);
    }
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
