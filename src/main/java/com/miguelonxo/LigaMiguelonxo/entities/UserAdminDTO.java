package com.miguelonxo.LigaMiguelonxo.entities;

import java.util.List;

public class UserAdminDTO {

    private User user;
    private Admin admin;
    private List<Long> roles;

    public UserAdminDTO() {
    }

    public UserAdminDTO(User user, Admin admin, List<Long> roles) {
        this.user = user;
        this.admin = admin;
        this.roles = roles;
    }


    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
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
