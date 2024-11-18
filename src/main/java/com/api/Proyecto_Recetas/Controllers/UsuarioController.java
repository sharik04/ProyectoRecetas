package com.api.Proyecto_Recetas.Controllers;

import java.util.ArrayList;
import java.util.Optional;

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

import com.api.Proyecto_Recetas.Models.Usuario;
import com.api.Proyecto_Recetas.Services.UsuarioService;

@RestController
@RequestMapping("/user")

public class UsuarioController {
   @Autowired
   private UsuarioService userService;

   @GetMapping
   public ArrayList<Usuario> getUsers() {
      return this.userService.getUsers();
   }

   @PostMapping
   public Usuario saveUser(@RequestBody Usuario user) {
      return this.userService.saveUser(user);
   }

   @GetMapping(path = "/{id}")
   public Optional<Usuario> getUserbyId(@PathVariable("id") Long id) {
      return this.userService.getUserById(id);
   }

   @PutMapping(path = "/{id}")
   public Usuario updateUserById(@RequestBody Usuario request, @PathVariable Long id) {
      return this.userService.updateByID(request, id);
   }

   @DeleteMapping(path = "/{id}")
   public Boolean deleteUserById(@PathVariable("id") Long id) {
      return this.userService.deleteUser(id);
   }

   @PostMapping("/login")
   public ResponseEntity<Integer> login(@RequestBody Usuario user) {
      int response = this.userService.login(user.getEmail(), user.getPassword());
      if (response != -1) {
         return new ResponseEntity<>(response, HttpStatus.OK);
      }
      else {
         return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
      }
   }

}
