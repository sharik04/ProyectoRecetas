package com.api.Proyecto_Recetas.Repositories;

import com.api.Proyecto_Recetas.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
