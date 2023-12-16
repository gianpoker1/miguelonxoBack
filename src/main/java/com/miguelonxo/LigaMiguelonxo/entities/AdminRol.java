package com.miguelonxo.LigaMiguelonxo.entities;

import java.util.List;

public class AdminRol {
    
    private Admin admin;
    private List<Long> roles;

    public AdminRol() {
    }

    public AdminRol(Admin admin, List<Long> roles){
        this.admin = admin;
        this.roles = roles;
    }


    public Admin getAdmin() {
        return this.admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<Long> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }

}
