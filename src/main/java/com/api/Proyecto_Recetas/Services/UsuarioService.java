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
    private UsuarioRepository userRepo;

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
        Optional<Usuario> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            Usuario user = optionalUser.get();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            return userRepo.save(user);  // Asegurarse de guardar los cambios
        }
        return null;
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
        Optional<Usuario> optionalUser = userRepo.findByEmail(username);
        if (optionalUser.isPresent()) {
            Usuario user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                return user.getId().intValue();
            }
        }
        return -1; // Usuario no encontrado o contraseña incorrecta
    }
}

