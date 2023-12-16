package com.miguelonxo.LigaMiguelonxo.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "user_roles")
public class UserRol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idUser")
    private Long idUser;

    @Column(name = "idRol")
    private Long rol;

    public UserRol() {
    }

    public UserRol(Long idUser, Long rol) {
        this.idUser = idUser;
        this.rol = rol;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return this.idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getRol() {
        return this.rol;
    }

    public void setRol(Long rol) {
        this.rol = rol;
    }    
}
