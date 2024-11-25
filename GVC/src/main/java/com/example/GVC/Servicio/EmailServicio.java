// EmailServicio.java
package com.example.GVC.Servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServicio {

    @Autowired
    private JavaMailSender mailSender;

    // Método centralizado para enviar un correo
    public void enviarCorreo(String destinatario, String asunto, String mensaje) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setFrom("erickzzxc@gmail.com");
            email.setTo(destinatario);
            email.setSubject(asunto);
            email.setText(mensaje);
            mailSender.send(email);
        } catch (Exception e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
            // Aquí puedes incluir una lógica adicional para manejar el error
        }
    }
}
