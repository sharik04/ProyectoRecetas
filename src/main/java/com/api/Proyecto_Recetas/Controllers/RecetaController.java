package com.api.Proyecto_Recetas.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.Proyecto_Recetas.Models.Receta;
import com.api.Proyecto_Recetas.Models.RequestEntities.NewRecetaRequest;
import com.api.Proyecto_Recetas.Services.RecetaService;


@RestController
@RequestMapping("/recetas")

public class RecetaController {
    @Autowired
    private RecetaService recetaService;

    @GetMapping("/user/{userId}")
    public List<Receta> getRecetasByUserId(@PathVariable Long userId) {
        List<Receta> recetas = recetaService.getRecetasByUserId(userId);
        return recetas;
    }

    @GetMapping("/userFavorites/{userId}")
    public List<Receta> getRecetasFavoritasByUserId(@PathVariable Long userId) {
        List<Receta> recetas = recetaService.getRecetasFavoritasByUserId(userId);
        return recetas;
    }

    @GetMapping("/buscarPorNombre/{nombre}")
    public List<Receta> getRecetaByNombre(@PathVariable String nombre) {
        List<Receta> recetas = recetaService.getRecetasByNombre(nombre);
        if (recetas != null) {
            return recetas;
        } else {
            return null;
        }
    }

    @PostMapping("/crearReceta")
    public ResponseEntity<String> crearReceta(@RequestBody NewRecetaRequest entity) {
        if (recetaService.crearReceta(entity.getReceta(), entity.getIngredientesXReceta()) == 1) {
            return ResponseEntity.ok("Receta creada correctamente");
        }   
        else {
            return ResponseEntity.badRequest().build();
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

    @GetMapping
    public ResponseEntity<List<Receta>> getAllRecetas() {
        List<Receta> recetas = recetaService.getAllRecetas();
        return ResponseEntity.ok(recetas);
    }

    @PutMapping("/toggleFavorita/{id}")
    public void toggleFavorita(@PathVariable Long id) {
        recetaService.toggleFavorita(id);
    }
    

}