package com.api.Proyecto_Recetas.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.Proyecto_Recetas.Models.IngredienteXReceta;
import com.api.Proyecto_Recetas.Models.Receta;
import com.api.Proyecto_Recetas.Models.RequestEntities.NewRecetaRequest;
import com.api.Proyecto_Recetas.Services.IngredienteXRecetaService;
import com.api.Proyecto_Recetas.Services.RecetaService;
import com.api.Proyecto_Recetas.Services.UsuarioService;

@RestController
@RequestMapping("/recetas")

public class RecetaController {
    @Autowired
    private RecetaService recetaService;

    @Autowired
    private UsuarioService userService;

    @Autowired
    private IngredienteXRecetaService repoPrueba;

    @GetMapping("/user/{userId}")
    public List<Receta> getRecetasByUserId(@PathVariable Long userId) {
        System.out.println(userId.toString());
        List<Receta> recetas = recetaService.getRecetasByUserId(userId);
        return recetas;
    }

    @GetMapping("/buscarPorNombre")
    public ResponseEntity<Receta> getRecetaByNombre(@RequestParam String nombre) {
        System.out.println("Recibido parámetro nombre: " + nombre);
        Receta receta = recetaService.getRecetaByNombre(nombre.trim());
        if (receta != null) {
            return ResponseEntity.ok(receta);
        } else {
            return ResponseEntity.notFound().build();
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

    @GetMapping("/prueba")
    public List<IngredienteXReceta> getMethodName() {
        return repoPrueba.getAllIngredienteXReceta();
    }

    @PostMapping("/toggleFavorita/{id}")
    public ResponseEntity<Receta> toggleFavorita(@PathVariable Long id) {
        Receta recetaActualizada = recetaService.toggleFavorita(id);
        if (recetaActualizada != null) {
            return ResponseEntity.ok(recetaActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Nuevo endpoint para editar una receta
    @PutMapping("/editar/{id}")
    public ResponseEntity<Receta> updateReceta(@PathVariable Long id, @RequestBody Receta receta) {
        Receta updatedReceta = recetaService.updateRecetaById(id, receta);
        if (updatedReceta != null) {
            return ResponseEntity.ok(updatedReceta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}