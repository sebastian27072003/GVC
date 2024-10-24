package com.example.GVC.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Define la columna id en la tabla
    private Long id;

    @Column(name = "Nombre")  // Define la columna Nombre en la tabla
    private String nombre;

    @Column(name = "Email")  // Define la columna Email en la tabla
    private String email;

    @Column(name = "Rol")  // Define la columna Rol en la tabla
    private String rol;

    @Column(name = "Matricula")  // Define la columna Matricula en la tabla
    private Long matricula;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }
}
