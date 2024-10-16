package com.api.Proyecto_Recetas.Controllers;

import com.api.Proyecto_Recetas.Models.Ingrediente;
import com.api.Proyecto_Recetas.Services.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    @GetMapping
    public List<Ingrediente> getAllIngredientes() {
        return ingredienteService.getAllIngredientes();
    }

    @PostMapping
    public Ingrediente createIngrediente(@RequestBody Ingrediente ingrediente) {
        return ingredienteService.saveIngrediente(ingrediente);
    }

    @GetMapping("/{id}")
    public Ingrediente getIngrediente(@PathVariable Long id) {
        return ingredienteService.getIngredienteById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteIngrediente(@PathVariable Long id) {
        ingredienteService.deleteIngrediente(id);
    }

    @PutMapping("/{id}")
    public Ingrediente updateIngrediente(@PathVariable Long id, @RequestBody Ingrediente ingrediente) {
        return ingredienteService.updateIngrediente(id, ingrediente);
    }
}
