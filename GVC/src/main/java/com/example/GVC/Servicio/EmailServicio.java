package com.example.GVC.Servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServicio {

    @Autowired
    private JavaMailSender mailSender;

    // Método para enviar un correo en texto plano o HTML
    public void enviarCorreo(String destinatario, String asunto, String mensaje, boolean esHtml) {
        try {
            MimeMessage email = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(email, true); // `true` para correos HTML
            helper.setFrom("erickzzxc@gmail.com");
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(mensaje, esHtml); // `esHtml` indica si el mensaje es HTML
            mailSender.send(email);
        } catch (Exception e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
        }
    }
}
