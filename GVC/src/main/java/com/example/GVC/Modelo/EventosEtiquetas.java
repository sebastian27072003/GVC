package com.example.GVC.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "EventosEtiquetas")
public class EventosEtiquetas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idEvento", nullable = false)
    private Eventos evento;

    @ManyToOne
    @JoinColumn(name = "idEtiqueta", nullable = false)
    private Etiquetas etiqueta;

    public EventosEtiquetas() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventosEtiquetas that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "EventosEtiquetas{" +
                "id=" + id +
                ", evento=" + evento +
                ", etiqueta=" + etiqueta +
                '}';
    }
}