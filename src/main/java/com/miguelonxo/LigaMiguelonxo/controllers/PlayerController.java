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

import com.miguelonxo.LigaMiguelonxo.entities.Player;
import com.miguelonxo.LigaMiguelonxo.entities.User;
import com.miguelonxo.LigaMiguelonxo.entities.UserPlayerDTO;
import com.miguelonxo.LigaMiguelonxo.services.PlayerService;
import com.miguelonxo.LigaMiguelonxo.services.UserService;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_PLAYER', 'ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Player>> findAll() {
        List<Player> player = playerService.findAll();
        return ResponseEntity.ok(player);
    }

    @PreAuthorize("hasAnyRole('ROLE_PLAYER', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{idPlayer}")
    public ResponseEntity<UserPlayerDTO> findById(@PathVariable Long idPlayer) {
        Player player = playerService.findById(idPlayer);
        if (player == null) {
            return ResponseEntity.notFound().build();
        }
        User usuario = userService.findById(player.getIdUser());
        UserPlayerDTO userClienteDTO = new UserPlayerDTO();
        userClienteDTO.setPlayer(player);
        userClienteDTO.setUser(usuario);

        return ResponseEntity.ok(userClienteDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<Player> findByIdUsuario(@PathVariable Long idUsuario) {
        Player player = playerService.findByIdUser(idUsuario);
        if (player == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(player);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @PutMapping("/cliente/{idPlayer}/usuario/{idUsuario}")
    public ResponseEntity<Player> update(@PathVariable Long idPlayer, @PathVariable Long idUsuario, @RequestBody UserPlayerDTO userPlayerDTO) {
        //obtener usuario y cliente
        Player player = userPlayerDTO.getPlayer();
        User usuario = userPlayerDTO.getUser();

        //actualizar usuario y cliente
        usuario.setId(idUsuario);
        userService.save(usuario);
        player.setId(idPlayer);

        Player updatedPlayer = playerService.save(player);
        return ResponseEntity.ok(updatedPlayer);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Player player = playerService.findById(id);
        if (player == null) {
            return ResponseEntity.notFound().build();
        }
        //eliminar usuario y cliente
        userService.deleteById(player.getIdUser());
        playerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /* @GetMapping
    public ResponseEntity<List<Player>> findAll(){
        List<Player> players = playerService.findAll();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> findById(@PathVariable Long id){
        Player player = playerService.findById(id);
        if(player == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(player);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Player> save(@RequestBody Player player){
        Player savedPlayer = playerService.save(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPlayer);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Player> update(@PathVariable Long id, @RequestBody Player player){
        Player existingPlayer = playerService.findById(id);
        if (existingPlayer == null) {
            return ResponseEntity.notFound().build();
        }
        player.setId(id);
        Player updatedPlayer = playerService.save(player);
        return ResponseEntity.ok(updatedPlayer);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletedById(@PathVariable Long id){
        Player player = playerService.findById(id);
        if(player == null){
            return ResponseEntity.notFound().build();
        }
        playerService.deleteById(id);
        return ResponseEntity.ok(null);
    } */
    
}
