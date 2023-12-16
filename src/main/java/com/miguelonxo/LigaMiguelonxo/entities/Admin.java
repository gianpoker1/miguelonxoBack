package com.miguelonxo.LigaMiguelonxo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "admins")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idAdmin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdmin;

    @Column(nullable = false)
    private Long idUser;

    @Column(nullable = false)
    private String tipo;


    public Admin() {
    }
    
    public Admin(String tipo){
        this.tipo = tipo;
    }


    public Long getIdAdmin() {
        return this.idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Long getIdUsuario() {
        return this.idUser;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUser = idUsuario;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
