package com.example.GVC.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "Nombre")
    private String Nombre;

    @Column(name = "Email")
    private String Email;

    @Column(name = "Rol")
    private String Rol;

    @Column(name = "Matricula")
    private Long Matricula;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    public Long getMatricula() {
        return Matricula;
    }

    public void setMatricula(Long matricula) {
        Matricula = matricula;
    }
}