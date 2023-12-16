package com.miguelonxo.LigaMiguelonxo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguelonxo.LigaMiguelonxo.entities.User;
import com.miguelonxo.LigaMiguelonxo.repositories.UserRepository;
import com.miguelonxo.LigaMiguelonxo.repositories.UserRolRepository;
import com.miguelonxo.LigaMiguelonxo.services.UserService;

@Service
public class UserServiceImpl implements UserService{


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolRepository userRolRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    /* @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    } */

    @Override
    public User findByUserName(String username) {
        User usuario = userRepository.findByEmail(username);
        if(usuario == null){
            return null;
        }else{
            return usuario;
        }
    }

    @Override
    public boolean hasRole(Long userId, String roleName) {
        List<Long> roleIdList = userRolRepository.findRoleIdsByIdUser(userId);
        if(roleIdList.isEmpty()){
            return false;
        }

        List<String> roleNames = userRolRepository.findRoleNamesByIds(roleIdList);

        return roleNames.contains(roleName);
    }
    
}
