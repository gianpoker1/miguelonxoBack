package com.miguelonxo.LigaMiguelonxo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miguelonxo.LigaMiguelonxo.entities.UserRol;
import com.miguelonxo.LigaMiguelonxo.services.UserRolService;

@RestController
@RequestMapping("/api/userRol")
public class UserRolController {

    @Autowired
    private UserRolService userRolService;

    @GetMapping
    public ResponseEntity<List <UserRol>> findAll() {
        List<UserRol> usuarioRol = userRolService.findAll();
        return ResponseEntity.ok(usuarioRol);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRol> findById(@PathVariable Long id) {
        UserRol usuarioRol = userRolService.findById(id);
        if (usuarioRol == null) {
            System.out.println("No se ha encontrado el usuarioRol");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioRol);
    }

    @PostMapping
    public ResponseEntity<UserRol> save(@RequestBody UserRol usuarioRol) {
        userRolService.save(usuarioRol);
        return ResponseEntity.status(201).body(usuarioRol);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRol> update(@PathVariable Long id, @RequestBody UserRol usuarioRol) {
        UserRol existingUsuarioRol = userRolService.findById(id);
        if (existingUsuarioRol == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioRol.setId(id);
        userRolService.save(usuarioRol);
        return ResponseEntity.ok(usuarioRol);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserRol> deleteById(@PathVariable Long id) {
        UserRol usuarioRol = userRolService.findById(id);
        if (usuarioRol == null) {
            return ResponseEntity.notFound().build();
        }
        userRolService.deleteById(id);
        return ResponseEntity.ok(usuarioRol);
    }
    
}
