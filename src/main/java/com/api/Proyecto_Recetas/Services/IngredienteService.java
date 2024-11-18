package com.api.Proyecto_Recetas.Services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.Proyecto_Recetas.Models.Ingrediente;
import com.api.Proyecto_Recetas.Repositories.IngredienteRepository;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    public List<Ingrediente> getAllIngredientes() {
        return ingredienteRepository.findAll();
    }

    public Ingrediente saveIngrediente(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    public Ingrediente getIngredienteById(Long id) {
        return ingredienteRepository.findById(id).orElse(null);
    }

    public Ingrediente getIngredienteByName(String nombre) {
        return ingredienteRepository.findByNombre(nombre).orElse(null);
    }

    public void deleteIngrediente(Long id) {
        ingredienteRepository.deleteById(id);
    }

    public Ingrediente updateIngrediente(Long id, Ingrediente ingrediente) {
        Ingrediente existingIngrediente = ingredienteRepository.findById(id).orElse(null);
        if (existingIngrediente != null) {
            existingIngrediente.setNombre(ingrediente.getNombre());
            return ingredienteRepository.save(existingIngrediente);
        }
        return null;
    }

}
