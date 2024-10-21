package com.example.GVC.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "EventosEtiquetas")
public class EventosEtiquetas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvento")
    private Long idEvento;

    @Column(name = "idEtiqueta")
    private Long idEtiqueta;

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public Long getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(Long idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }
}