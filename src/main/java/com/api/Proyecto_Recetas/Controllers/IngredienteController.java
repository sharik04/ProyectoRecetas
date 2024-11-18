package com.api.Proyecto_Recetas.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.Proyecto_Recetas.Models.Ingrediente;
import com.api.Proyecto_Recetas.Services.IngredienteService;

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

    @GetMapping("/name/{name}")
    public Ingrediente getIngredienteByName(@PathVariable String name) {
        return ingredienteService.getIngredienteByName(name);
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
