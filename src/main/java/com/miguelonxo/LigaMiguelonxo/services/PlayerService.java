package com.miguelonxo.LigaMiguelonxo.services;

import java.util.List;

import com.miguelonxo.LigaMiguelonxo.entities.Player;

public interface PlayerService {

    List<Player> findAll();
    Player findById(Long id);
    Player findByIdUser(Long id);
    Player save(Player player);
    void deleteById(Long id);
    void deleteByIdUser(Long id);
    
}
