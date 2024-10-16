package com.api.Proyecto_Recetas.Controllers;


import com.api.Proyecto_Recetas.Models.Receta;
import com.api.Proyecto_Recetas.Services.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recetas")

public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @GetMapping
    public List<Receta> getAllRecetas() {
        return recetaService.getAllRecetas();
    }

    @PostMapping
    public Receta createReceta(@RequestBody Receta receta) {
        return recetaService.saveReceta(receta);
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
