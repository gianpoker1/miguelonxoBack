package com.miguelonxo.LigaMiguelonxo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.miguelonxo.LigaMiguelonxo.repositories.RolRepository;
import com.miguelonxo.LigaMiguelonxo.repositories.UserRolRepository;

@Entity
@Table(name="users")
public class User{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Email(message = "El email no es valido")
    private String email;

    @NotNull
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @Column
    private String name;

    @Transient
    private UserRolRepository userRolRepository;

    @Transient
    private RolRepository rolRepository;


    public User() {
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserRolRepository(UserRolRepository userRolRepository) {
        this.userRolRepository = userRolRepository;
    }

    public void setRolRepository(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }


}
