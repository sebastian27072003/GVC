package com.example.GVC.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "eventos_etiquetas")
public class EventosEtiquetas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento_etiqueta")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)  // Relación con la entidad Evento
    private Eventos evento;

    @ManyToOne
    @JoinColumn(name = "id_etiqueta", nullable = false, insertable = false, updatable = false)
    private Etiquetas etiqueta;

    @Column(name = "id_etiqueta")  // Columna duplicada corregida
    private Long idEtiqueta;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Eventos getEvento() {
        return evento;
    }

    public void setEvento(Eventos evento) {
        this.evento = evento;
    }

    public Etiquetas getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(Etiquetas etiqueta) {
        this.etiqueta = etiqueta;
    }

    public Long getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(Long idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }
}
