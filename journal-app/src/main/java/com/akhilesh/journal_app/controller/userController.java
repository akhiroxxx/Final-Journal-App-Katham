package com.akhilesh.journal_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akhilesh.journal_app.entity.User;
import com.akhilesh.journal_app.service.UserService;

@RestController
@RequestMapping("/user")
public class userController {
  
  @Autowired
  private UserService userService;

  @GetMapping
  public List<User> getAll(){
    return userService.getAll();
  } 

  @PostMapping
  public void createUser(@RequestBody User user){
    userService.saveEntry(user);
  }

  @PutMapping("/{username}")
  public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String username){
    User userInDb = userService.findByUserName(username);
    if(userInDb!=null){
      userInDb.setUserName(user.getUserName());
      userInDb.setPassword(user.getPassword());
      userService.saveEntry(userInDb);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


}
