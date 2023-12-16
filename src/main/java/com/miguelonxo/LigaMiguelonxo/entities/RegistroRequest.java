package com.miguelonxo.LigaMiguelonxo.entities;

public class RegistroRequest {

    private User usuario;
    private String tipo;

    public RegistroRequest() {
    }

    public RegistroRequest(User usuario, String tipo) {
        this.usuario = usuario;
        this.tipo = tipo;
    }


    public User getUsuario() {
        return this.usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
