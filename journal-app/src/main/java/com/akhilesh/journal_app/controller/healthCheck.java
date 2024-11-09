package com.akhilesh.journal_app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class healthCheck {
  
  @GetMapping("/health-check")
  public String checkOk(){
    return "Ok";
  }
}
