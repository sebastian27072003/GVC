package com.example.GVC.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Define la columna id en la tabla
    private Long id;

    @Column(name = "username")  // Define la columna Password en la tabla
    private String username;  // Agrega el campo para la contraseña

    @Column(name = "Nombre")  // Define la columna Nombre en la tabla
    private String nombre;

    @Column(name = "Email")  // Define la columna Email en la tabla
    private String email;

    @Column(name = "rol")  // Define la columna Rol en la tabla
    private String rol;

    @Column(name = "Matricula")  // Define la columna Matricula en la tabla
    private Long matricula;

    @Column(name = "password")  // Define la columna Password en la tabla
    private String password;  // Agrega el campo para la contraseña


    // Getters y Setters


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getPassword() {  // Método getter para la contraseña
        return password;
    }

    public void setPassword(String password) {  // Método setter para la contraseña
        this.password = password;
    }
}
