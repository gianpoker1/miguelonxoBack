package com.miguelonxo.LigaMiguelonxo.services;

import java.util.List;

import com.miguelonxo.LigaMiguelonxo.entities.Rol;

public interface RolService {
    List<Rol> findAll();
    Rol findById(Long id);
    Rol findByNombre(String nombre);
    Rol save(Rol rol);
    void deleteById(Long id);
}
