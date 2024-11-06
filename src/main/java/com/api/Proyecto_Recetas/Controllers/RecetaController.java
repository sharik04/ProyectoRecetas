package com.api.Proyecto_Recetas.Controllers;

import com.api.Proyecto_Recetas.Models.Receta;
import com.api.Proyecto_Recetas.Services.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")  // Define la URL base para las recetas
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    // Obtener todas las recetas
    @GetMapping()
    public ResponseEntity<List<Receta>> getAllRecetas() {
        List<Receta> recetas = recetaService.getAllRecetas();
        return new ResponseEntity<>(recetas, HttpStatus.OK);
    }

    // Obtener todas las recetas de un usuario específico
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Receta>> getRecetasByUserId(@PathVariable Long userId) {
        List<Receta> recetas = recetaService.getRecetasByUserId(userId);
        return new ResponseEntity<>(recetas, HttpStatus.OK);
    }

    // Obtener una receta por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Receta> getRecetaById(@PathVariable Long id) {
        Receta receta = recetaService.getRecetaById(id);
        if (receta != null) {
            return new ResponseEntity<>(receta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 404 Not Found
        }
    }

    // Crear una nueva receta
    @PostMapping("/{userId}")
    public ResponseEntity<Receta> saveReceta(@RequestBody Receta receta, @PathVariable Long userId) {
        Receta savedReceta = recetaService.saveReceta(receta, userId);
        return new ResponseEntity<>(savedReceta, HttpStatus.CREATED);  // 201 Created
    }

    // Eliminar una receta (solo si pertenece al usuario)
    @DeleteMapping("/{id}/{userId}")
    public ResponseEntity<Void> deleteReceta(@PathVariable Long id, @PathVariable Long userId) {
        try {
            recetaService.deleteReceta(id, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 204 No Content
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // 403 Forbidden si no se tiene permiso
        }
    }

    // Actualizar una receta (solo si pertenece al usuario)
    @PutMapping("/{id}/{userId}")
    public ResponseEntity<Receta> updateReceta(@PathVariable Long id, @PathVariable Long userId, @RequestBody Receta receta) {
        Receta updatedReceta = recetaService.updateReceta(id, receta, userId);
        if (updatedReceta != null) {
            return new ResponseEntity<>(updatedReceta, HttpStatus.OK);  // 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 404 Not Found si no se encuentra la receta
        }
    }


}

