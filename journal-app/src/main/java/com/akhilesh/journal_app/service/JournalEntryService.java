package com.akhilesh.journal_app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.akhilesh.journal_app.Repository.JournalEntryRepository;
import com.akhilesh.journal_app.entity.JournalEntry;


@Component
public class JournalEntryService {

  @Autowired
  private JournalEntryRepository journalEntryRepository;


  public void saveEntry(JournalEntry journalEntry){
    journalEntry.setDate(LocalDateTime.now());
    journalEntryRepository.save(journalEntry);
  }


  public List<JournalEntry> getAll(){
    return journalEntryRepository.findAll();
  }


  public Optional<JournalEntry> findById(ObjectId id){
    return journalEntryRepository.findById(id);
  }


  public void deleteById(ObjectId id){
    journalEntryRepository.deleteById(id);
  } 



  
}
