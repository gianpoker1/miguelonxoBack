package com.miguelonxo.LigaMiguelonxo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguelonxo.LigaMiguelonxo.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{

    Admin findByIdUser(Long idUser);
    
}
