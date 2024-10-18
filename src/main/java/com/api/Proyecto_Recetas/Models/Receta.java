package com.api.Proyecto_Recetas.Models;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Receta {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String pasos;
    private String imagenUrl;
    private boolean favorita;
    private String tiempo;


    @JsonIgnore
    @OneToMany(mappedBy = "receta")
    private List<IngredienteXReceta> ingredientes = new ArrayList<>();

    @ManyToOne
    private Usuario user;

    public Receta(String nombre, String pasos, String imagenUrl, boolean favorita, String tiempo,
            List<IngredienteXReceta> ingredientes, Usuario user) {
        this.nombre = nombre;
        this.pasos = pasos;
        this.imagenUrl = imagenUrl;
        this.favorita = favorita;
        this.tiempo = tiempo;
        this.ingredientes = ingredientes;
        this.user = user;
    }

    public Receta(String nombre, String pasos, String imagenUrl, boolean favorita, String tiempo) {
        this.nombre = nombre;
        this.pasos = pasos;
        this.imagenUrl = imagenUrl;
        this.favorita = favorita;
        this.tiempo = tiempo;
    }

    public Receta(String nombre, String pasos, String imagenUrl, boolean favorita, String tiempo, Usuario user) {
        this.nombre = nombre;
        this.pasos = pasos;
        this.imagenUrl = imagenUrl;
        this.favorita = favorita;
        this.tiempo = tiempo;
        this.user = user;
    }

    

    
}


