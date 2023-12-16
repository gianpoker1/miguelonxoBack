package com.miguelonxo.LigaMiguelonxo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguelonxo.LigaMiguelonxo.entities.Rol;
import com.miguelonxo.LigaMiguelonxo.repositories.RolRepository;
import com.miguelonxo.LigaMiguelonxo.services.RolService;

@Service
public class RolServiceImpl implements RolService{

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> findAll(){
        return rolRepository.findAll();
    }

    @Override
    public Rol findById(Long id){
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    public Rol save(Rol rol){
        return rolRepository.save(rol);
    }

    @Override
    public void deleteById(Long id){
        rolRepository.deleteById(id);
    }

    @Override
    public Rol findByNombre(String nombre){
        return rolRepository.findByNombre(nombre);
    }
    
}
