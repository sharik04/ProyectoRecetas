package com.api.Proyecto_Recetas.Controllers;


import com.api.Proyecto_Recetas.Models.Receta;
import com.api.Proyecto_Recetas.Models.User;
import com.api.Proyecto_Recetas.Services.RecetaService;
import com.api.Proyecto_Recetas.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recetas")

public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private UserService userService;

    @PostMapping("/{userId}")
    public ResponseEntity<Receta> saveReceta(@PathVariable Long userId, @RequestBody Receta receta) {
        User user = userService.getUserById(userId).orElse(null);
        if (user != null) {
            receta.setUser(user);
            Receta savedReceta = recetaService.saveReceta(receta);
            return ResponseEntity.ok(savedReceta);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Receta>> getRecetasByUserId(@PathVariable Long userId) {
        List<Receta> recetas = recetaService.getRecetasByUserId(userId);
        return ResponseEntity.ok(recetas);
    }

    @GetMapping("/{id}")
    public Receta getReceta(@PathVariable Long id) {
        return recetaService.getRecetaById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteReceta(@PathVariable Long id) {
        recetaService.deleteReceta(id);
    }

    @PutMapping("/{id}")
    public Receta updateReceta(@PathVariable Long id, @RequestBody Receta receta) {
        return recetaService.updateReceta(id, receta);
    }

}
