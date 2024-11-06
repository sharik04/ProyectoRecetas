package com.api.Proyecto_Recetas.Controllers;

import com.api.Proyecto_Recetas.Models.Ingrediente;
import com.api.Proyecto_Recetas.Services.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    // Obtener todos los ingredientes
    @GetMapping
    public List<Ingrediente> getAllIngredientes() {
        return ingredienteService.getAllIngredientes();
    }

    // Guardar un ingrediente
    @PostMapping
    public Ingrediente saveIngrediente(@RequestBody Ingrediente ingrediente) {
        return ingredienteService.saveIngrediente(ingrediente);
    }

    // Obtener ingrediente por ID
    @GetMapping("/{id}")
    public Ingrediente getIngredienteById(@PathVariable Long id) {
        return ingredienteService.getIngredienteById(id);
    }

    // Eliminar ingrediente
    @DeleteMapping("/{id}")
    public void deleteIngrediente(@PathVariable Long id) {
        ingredienteService.deleteIngrediente(id);
    }

    // Actualizar ingrediente
    @PutMapping("/{id}")
    public Ingrediente updateIngrediente(@PathVariable Long id, @RequestBody Ingrediente ingrediente) {
        return ingredienteService.updateIngrediente(id, ingrediente);
    }

    // Asociar ingrediente con receta
    @PostMapping("/{ingredienteId}/receta/{recetaId}")
    public void asociarIngredienteAReceta(@PathVariable Long ingredienteId, @PathVariable Long recetaId) {
        ingredienteService.asociarIngredienteAReceta(ingredienteId, recetaId);
    }
}

