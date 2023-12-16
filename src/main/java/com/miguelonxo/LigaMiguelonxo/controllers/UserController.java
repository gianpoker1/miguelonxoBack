package com.miguelonxo.LigaMiguelonxo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miguelonxo.LigaMiguelonxo.entities.User;
import com.miguelonxo.LigaMiguelonxo.services.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PreAuthorize("hasAnyRole('ROLE_PLAYER', 'ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List <User>> findAll() {
        List<User> usuarios = userService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @PreAuthorize("hasAnyRole('ROLE_PLAYER', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/userName/{username}")
    public ResponseEntity<User> findByUserName(@PathVariable String username) {
        User usuario = userService.findByUserName(username);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User usuario = userService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User usuario) {
        User existingUsuario = userService.findById(id);
        if (existingUsuario == null) {
            return ResponseEntity.notFound().build();
        }
        usuario.setId(id);
        User updatedUsuario = userService.save(usuario);
        return ResponseEntity.ok(updatedUsuario);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}") 
    public ResponseEntity<User> delete(@PathVariable Long id) {
        User existingUsuario = userService.findById(id);
        if (existingUsuario == null) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{id}/hasRole/{roleName}")
    public ResponseEntity<?> hasRole(@PathVariable Long id, @PathVariable String roleName) {
        boolean hasRole = userService.hasRole(id, roleName);
        return ResponseEntity.ok(hasRole);
    }



    /* @GetMapping
    public ResponseEntity<List <User>> findAll(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    } 
    public String listUsers(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user/list";
    } 

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    } 
    
    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User savedUser = userService.save(user);
        String encodedPassword = passwordEncoder.encode(savedUser.getPassword());
        user.setPassword(encodedPassword);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    } 

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable long id, @RequestBody User user){
        User existingUser = userService.findById(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        User updatedUser = userService.save(user);
        return ResponseEntity.ok(updatedUser);
    } 

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id){
        User existingUser = userService.findById(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(id);
        return ResponseEntity.ok(existingUser);
    } */
}
