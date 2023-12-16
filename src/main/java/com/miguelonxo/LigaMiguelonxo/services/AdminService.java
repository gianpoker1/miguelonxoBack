package com.miguelonxo.LigaMiguelonxo.services;



import java.util.List;

import com.miguelonxo.LigaMiguelonxo.entities.Admin;


public interface AdminService {
    List<Admin> findAll();
    Admin obtenerAdminPorIdUser(Long idUser);
    Admin findById(Long id);
    Admin save(Admin admin);
    void deleteById(Long id);
    
}
