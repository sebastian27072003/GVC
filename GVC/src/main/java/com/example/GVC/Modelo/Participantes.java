package com.example.GVC.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Participantes")
public class Participantes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idParticipante")  // Define la columna idParticipante en la tabla
    private Long idParticipante;

    @Column(name = "Correo")
    private String email;

    @Column(name = "Nombre")  // Define la columna Nombre en la tabla
    private String nombre;

    @Column(name = "Notificaciones")
    private Boolean notificaciones;

    @Column(name = "Recordatorio")
    private String recordatorio;

    // Getters y Setters

    public Long getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Long idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(Boolean notificaciones) {
        this.notificaciones = notificaciones;
    }

    public String getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(String recordatorio) {
        this.recordatorio = recordatorio;
    }
}
