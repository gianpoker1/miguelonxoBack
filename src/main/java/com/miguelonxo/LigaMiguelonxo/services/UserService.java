package com.miguelonxo.LigaMiguelonxo.services;

import java.util.List;

import com.miguelonxo.LigaMiguelonxo.entities.User;

public interface UserService {

    List<User> findAll();
    User findById(Long id);
    User save(User user);
    void deleteById(Long id);
    User findByUserName(String userName);
    boolean hasRole(Long id, String roleName);
    
}
