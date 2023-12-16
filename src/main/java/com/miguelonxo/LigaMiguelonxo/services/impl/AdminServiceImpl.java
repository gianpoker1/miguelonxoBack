package com.miguelonxo.LigaMiguelonxo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguelonxo.LigaMiguelonxo.entities.Admin;
import com.miguelonxo.LigaMiguelonxo.repositories.AdminRepository;
import com.miguelonxo.LigaMiguelonxo.services.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminRepository adminRepository; 

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Admin findById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public void deleteById(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public Admin obtenerAdminPorIdUser(Long idUsuario) {
        return adminRepository.findByIdUser(idUsuario);
    }
    
}
