package com.api.Proyecto_Recetas.Models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCompleto;
    private String username;
    private String password;
    private String email;

    public Usuario(String nombreCompleto, String username, String password, String email) {
        this.nombreCompleto = nombreCompleto;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Receta> recetas = new ArrayList<>();

}
