package com.miguelonxo.LigaMiguelonxo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miguelonxo.LigaMiguelonxo.entities.Admin;
import com.miguelonxo.LigaMiguelonxo.entities.AdminRol;
import com.miguelonxo.LigaMiguelonxo.entities.User;
import com.miguelonxo.LigaMiguelonxo.entities.UserAdminDTO;
import com.miguelonxo.LigaMiguelonxo.entities.UserRol;
import com.miguelonxo.LigaMiguelonxo.services.AdminService;
import com.miguelonxo.LigaMiguelonxo.services.PlayerService;
import com.miguelonxo.LigaMiguelonxo.services.UserRolService;
import com.miguelonxo.LigaMiguelonxo.services.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRolService userRolService;

    @Autowired
    private PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<Admin>> findAll() {
        List<Admin> admins = adminService.findAll();
        return ResponseEntity.ok(admins);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/{idAdmin}")
    public ResponseEntity<UserAdminDTO> findById(@PathVariable Long idAdmin) {
        Admin admin = adminService.findById(idAdmin);
        if (admin == null) {
            return ResponseEntity.notFound().build();
        }

        User user = userService.findById(admin.getIdUsuario());
        UserAdminDTO userAdminDTO = new UserAdminDTO();

        // obtener roles de admin
        List<Long> listaRoles = new ArrayList<>();
        List<UserRol> usuarioRoles = userRolService.findByUsuarioId(admin.getIdUsuario());
        usuarioRoles.forEach(usuarioRol -> {
            listaRoles.add(usuarioRol.getRol());
        });
        userAdminDTO.setRoles(listaRoles);

        userAdminDTO.setAdmin(admin);
        userAdminDTO.setUser(user);

        return ResponseEntity.ok(userAdminDTO);
    }

    // Obtener Admins por id de user
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/user/{idUser}")
    public ResponseEntity<Admin> findByIdUser(@PathVariable Long idUser) {
        Admin admin = adminService.obtenerAdminPorIdUser(idUser);
        if (admin == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(admin);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Admin> save(@RequestBody AdminRol adminRol) {
        System.out.println("ADMIN : " + adminRol.getAdmin().toString());
        Admin admin = adminRol.getAdmin();
        User usuario = userService.findById(adminRol.getAdmin().getIdUsuario());
        List<Long> roles = adminRol.getRoles();

        // eliminar roles previamente
        userRolService.deleteAllByIdUser(usuario.getId());

        // guardar roles
        roles.forEach(rolId -> {
            UserRol usuarioRol = new UserRol();
            usuarioRol.setRol(rolId);
            usuarioRol.setIdUser(rolId);
            this.userRolService.save(usuarioRol);
        });

        // eliminar cliente
        playerService.deleteByIdUser(usuario.getId());

        Admin savedAdmin = adminService.save(admin);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedAdmin);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/Admin/{idAdmin}/usuario/{idUser}")
    public ResponseEntity<Admin> update(@PathVariable Long idAdmin, @PathVariable Long idUser,
            @RequestBody UserAdminDTO userAdmin) {

        // obtener usuario y admin y roles
        Admin admin = userAdmin.getAdmin();
        User usuario = userAdmin.getUser();
        List<Long> listRolesTrabajador = userAdmin.getRoles();

        // borrar roles antiguos
        userRolService.deleteAllByIdUser(idUser);

        // guardar roles
        listRolesTrabajador.forEach(rolId -> {
            UserRol usuarioRol = new UserRol();
            usuarioRol.setRol(rolId);
            usuarioRol.setIdUser(usuario.getId());
            this.userRolService.save(usuarioRol);
        });

        // actualizar usuario y trabajador
        usuario.setId(idUser);
        userService.save(usuario);
        admin.setIdAdmin(idAdmin);
        Admin updatedAdmin = adminService.save(admin);
        return ResponseEntity.ok(updatedAdmin);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Admin admin = adminService.findById(id);
        if (admin == null) {
            return ResponseEntity.notFound().build();
        }
        adminService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /*
     * @PostMapping
     * 
     * @RolesAllowed("ROLE_ADMIN")
     * public ResponseEntity<Admin> save(@RequestBody Admin admin){
     * Admin savedAdmin = adminService.save(admin);
     * return ResponseEntity.status(HttpStatus.CREATED).body(savedAdmin);
     * }
     * 
     * @PutMapping("/{id}")
     * public ResponseEntity<Admin> update(@PathVariable Long id, @RequestBody Admin
     * admin){
     * Admin existingAdmin = adminService.findById(id);
     * if (existingAdmin == null) {
     * return ResponseEntity.notFound().build();
     * }
     * admin.setId(id);
     * Admin updatedAdmin = adminService.save(admin);
     * return ResponseEntity.ok(updatedAdmin);
     * }
     * 
     * @DeleteMapping("/{id}")
     * public ResponseEntity<Void> deletedById(@PathVariable Long id){
     * Admin admin = adminService.findById(id);
     * if (admin == null) {
     * return ResponseEntity.notFound().build();
     * }
     * adminService.deleteById(id);
     * return ResponseEntity.ok(null);
     * }
     */
}
