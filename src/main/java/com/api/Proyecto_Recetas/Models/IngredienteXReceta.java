package com.api.Proyecto_Recetas.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredienteXReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Ingrediente ingrediente;
    @ManyToOne
    private Receta receta;
    private int cantidad; 
    private String unidad;
    public IngredienteXReceta(Ingrediente ingrediente, Receta receta, int cantidad, String unidad) {
        this.ingrediente = ingrediente;
        this.receta = receta;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }


}
