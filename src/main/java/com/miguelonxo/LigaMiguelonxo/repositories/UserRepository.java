package com.miguelonxo.LigaMiguelonxo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.miguelonxo.LigaMiguelonxo.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

    public User findByEmail(String email);
    public boolean existsByEmail(String email);
    
}
