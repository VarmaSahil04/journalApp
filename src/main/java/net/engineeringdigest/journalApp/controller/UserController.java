package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


     @Autowired
    private UserService userService;


     @GetMapping
     public List<User> getAllUsers(){
          return userService.getAll();
     }


     @PostMapping
     public void createUser(@RequestBody User user){
         userService.saveUser(user);
     }

     @PutMapping("/{userName}")
    public ResponseEntity<?> updateUserById(@RequestBody User user, @PathVariable String userName){
         User userInDb = userService.findByUserName(userName);
         if(userInDb != null){
             userInDb.setUserName(user.getUserName());
             userInDb.setPassword(user.getPassword());
             userService.saveUser(userInDb);
         }
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }








}
