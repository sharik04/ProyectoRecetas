package com.api.Proyecto_Recetas.Controllers;

import com.api.Proyecto_Recetas.Models.User;
import com.api.Proyecto_Recetas.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/user")

public class UserController {
   @Autowired

   private UserService userService;
   @GetMapping

    public ArrayList<User>getUsers(){
       return this.getUsers();
   }

   @PostMapping
   public User saveUser(@RequestBody User user){
      return this.userService.saveUser(user);
   }

   @GetMapping(path="/{id}")
   public Optional<User>getUserbyId(@PathVariable("id") Long id){
      return this.userService.getUserById(id);
   }
   @PutMapping(path = "{id}")
   public User updateUserById(@RequestBody User request ,@PathVariable Long id){
      return this.userService.updateByID(request, id);
   }

   @DeleteMapping(path="{id}")
   public Boolean deleteUserById(@PathVariable("id") Long id){
      boolean delete=this.userService.deleteUser(id);
      if(delete){
         return true;
      }
      else{
         return false;
      }
   }





}
