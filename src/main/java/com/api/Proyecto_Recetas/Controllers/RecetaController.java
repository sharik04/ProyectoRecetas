package com.api.Proyecto_Recetas.Controllers;


import com.api.Proyecto_Recetas.Models.Receta;
import com.api.Proyecto_Recetas.Models.User;
import com.api.Proyecto_Recetas.Services.RecetaService;
import com.api.Proyecto_Recetas.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//Indica que esta clase es un controlador REST,
// lo que significa que manejará las peticiones HTTP y devolverá respuestas en formato JSON
@RestController
//Se define la ruta
@RequestMapping("/recetas")

public class RecetaController {
    //Inyecta las dependencias
    @Autowired
    private RecetaService recetaService;

    @Autowired
    private UserService userService;
    //Se manejan las solicitudes Post mediante el userId
    @PostMapping("/{userId}")
    //
    //@PathVariable Long userId: Captura el userId de la URL.
    //@RequestBody Receta receta: Toma el cuerpo de la solicitud y lo convierte en un objeto Receta.
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

    @GetMapping("/buscarPorNombre")
    public ResponseEntity<Receta> getRecetaByNombre(@RequestParam String nombre) {
        Receta receta = recetaService.getRecetaByNombre(nombre);
        if (receta != null) {
            return ResponseEntity.ok(receta);
        } else {
            return ResponseEntity.notFound().build();
        }
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

    @GetMapping
    public ResponseEntity<List<Receta>> getAllRecetas() {
        List<Receta> recetas = recetaService.getAllRecetas();
        return ResponseEntity.ok(recetas);
    }

}