package com.miguelonxo.LigaMiguelonxo.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguelonxo.LigaMiguelonxo.entities.UserRol;
import com.miguelonxo.LigaMiguelonxo.repositories.UserRolRepository;
import com.miguelonxo.LigaMiguelonxo.services.UserRolService;

@Service
public class UserRolServiceImpl implements UserRolService{


    @Autowired
    private UserRolRepository userRolRepository;

    @Override
    public List<UserRol> findAll() {
        return userRolRepository.findAll();
    }

    @Override
    public UserRol findById(Long id) {
        return userRolRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserRol> findByUsuarioId(Long userId) {
        return userRolRepository.findByIdUser(userId);
    }

    @Override
    public UserRol save(UserRol userRol) {
        return userRolRepository.save(userRol);
    }

    @Override
    public void deleteById(Long id) {
        userRolRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteUserRolesByIdUser(Long idUser) {
        userRolRepository.deleteAllByIdUser(idUser);
    }

    @Override
    @Transactional
    public void deleteAllByIdUser(Long idUser) {
        List<UserRol> userRoles = userRolRepository.findByIdUser(idUser);
        userRolRepository.deleteAll(userRoles);
    }
    
}
