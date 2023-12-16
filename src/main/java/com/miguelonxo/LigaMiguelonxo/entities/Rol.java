package com.miguelonxo.LigaMiguelonxo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @Column(name = "idUserRol")
    private Long idUserRol;

    public Rol() {
    }


    public Rol(String nombre, Long idUserRol) {
        this.nombre = nombre;
        this.idUserRol = idUserRol;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdUserRol() {
        return this.idUserRol;
    }

    public void setIdUserRol(Long idUserRol) {
        this.idUserRol = idUserRol;
    }




    
}
