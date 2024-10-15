package com.api.Proyecto_Recetas.Services;

import com.api.Proyecto_Recetas.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.Proyecto_Recetas.Models.User;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ArrayList<User> getUsers() {
        return (ArrayList<User>) userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);

    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateByID(User request, Long id ){
        User user = userRepository.findById(id).get();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        return user;
    }

    public boolean deleteUser(Long id) {
        try{
            userRepository.deleteById(id);
            return true;
        }catch(Exception e){
return false;
        }
    }




}
