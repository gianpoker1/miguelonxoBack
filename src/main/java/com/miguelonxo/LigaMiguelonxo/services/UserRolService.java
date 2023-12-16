package com.miguelonxo.LigaMiguelonxo.services;

import java.util.List;

import com.miguelonxo.LigaMiguelonxo.entities.UserRol;

public interface UserRolService {

    List<UserRol> findAll();
    UserRol findById(Long id);
    List<UserRol> findByUsuarioId(Long userId);
    UserRol save(UserRol userRol);
    void deleteById(Long id);
    void deleteUserRolesByIdUser(Long idUser);
    void deleteAllByIdUser(Long idUser);

    
}
