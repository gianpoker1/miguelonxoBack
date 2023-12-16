package com.miguelonxo.LigaMiguelonxo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguelonxo.LigaMiguelonxo.entities.Player;


public interface PlayerRepository extends JpaRepository<Player, Long>{

    Player findByIdUser(Long id);
    void deleteByIdUser(Long id);
    
}
