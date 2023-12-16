package com.miguelonxo.LigaMiguelonxo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguelonxo.LigaMiguelonxo.entities.Rol;

public interface RolRepository extends JpaRepository<Rol, Long>{
    Rol findByNombre(String nombre);
    
}
