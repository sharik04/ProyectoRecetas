package com.api.Proyecto_Recetas.Services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.Proyecto_Recetas.Models.Usuario;
import com.api.Proyecto_Recetas.Repositories.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository userRepo;

    public ArrayList<Usuario> getUsers() {
        return (ArrayList<Usuario>) userRepo.findAll();
    }

    public Usuario saveUser(Usuario user) {
        return userRepo.save(user);

    }

    public Optional<Usuario> getUserById(Long id) {
        return userRepo.findById(id);
    }

    public Usuario updateByID(Usuario request, Long id) {
        Usuario user = userRepo.findById(id).get();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        return user;
    }

    public boolean deleteUser(Long id) {
        try {
            userRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<Usuario> getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public int login(String username, String password) {
        Usuario user = userRepo.findByEmail(username).orElse(null);
        if (user != null){
            if (user.getPassword().equals(password)){
                //cast long id to int
                return user.getId().intValue();
            } else {
                return -1;
            }
        }
        return -1;
    }

}
