package com.miguelonxo.LigaMiguelonxo.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguelonxo.LigaMiguelonxo.entities.Player;
import com.miguelonxo.LigaMiguelonxo.repositories.PlayerRepository;
import com.miguelonxo.LigaMiguelonxo.services.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public Player findById(Long id) {
        return playerRepository.findById(id).orElse(null);
    }

    @Override
    public Player findByIdUser(Long id) {
        return playerRepository.findByIdUser(id);
    }

    @Override
    public Player save(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public void deleteById(Long id) {
        playerRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByIdUser(Long id) {
        
        playerRepository.deleteByIdUser(id);
    }
    
}
