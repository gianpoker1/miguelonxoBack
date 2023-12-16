package com.miguelonxo.LigaMiguelonxo.entities;

public class UserPlayerDTO {

    private User user;
    private Player player;

    public UserPlayerDTO() {
    }

    public UserPlayerDTO(User user, Player player) {
        this.user = user;
        this.player = player;
    }


    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    
}
