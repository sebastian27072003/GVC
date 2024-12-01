package com.example.GVC.Servicio;

import com.example.GVC.Modelo.Participantes;
import com.example.GVC.Modelo.ParticipantesEventos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Repositorio.EventosRepositorio;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotiServicio {

    @Autowired
    private EmailServicio emailServicio;


    // hola alejandro quiero hacer un commit
    @Autowired
    private EventosRepositorio eventosRepositorio;

    @Scheduled(cron = "0 * 9 * * ?")  // Ejecuta a las 9:00 AM
    @Transactional
    public void enviarRecordatoriosEventos() {
        LocalDateTime ahora = LocalDateTime.now();
        List<Eventos> eventos = eventosRepositorio.findAll();

        for (Eventos evento : eventos) {
            LocalDateTime fechaHoraEvento = LocalDateTime.of(evento.getFecha(), evento.getHoraInicio());

            long diasHastaEvento = ChronoUnit.DAYS.between(ahora, fechaHoraEvento);
            long minutosHastaEvento = ChronoUnit.MINUTES.between(ahora, fechaHoraEvento);

            // Recorre los participantes de cada evento a través de ParticipantesEventos
            for (ParticipantesEventos participantesEvento : evento.getParticipantesEventos()) {
                if (Boolean.TRUE.equals(participantesEvento.getNotificaciones())) {
                    Participantes participante = participantesEvento.getParticipante();
                    String destinatario = participante.getemail();




                    // Enviar correo de recordatorio una semana antes del evento
                    if (diasHastaEvento == 7) {
                        String asunto = "Recordatorio: Evento en una semana";
                        String urlImagen = evento.getImagen(); // Asegúrate de que este valor sea la URL completa y accesible
                        String mensajeHTML = "<html>\n"
                                + "<head>\n"
                                + "<style>\n"
                                + "body {\n"
                                + "font-family: Arial, sans-serif;\n"
                                + "background-color: #f4f4f9;\n"
                                + "color: #333;\n"
                                + "line-height: 1.6;\n"
                                + "}\n"
                                + ".container {\n"
                                + "width: 90%;\n"
                                + "max-width: 600px;\n"
                                + "margin: 20px auto;\n"
                                + "background: #ffffff;\n"
                                + "padding: 20px;\n"
                                + "border-radius: 8px;\n"
                                + "box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);\n"
                                + "}\n"
                                + "h1 {\n"
                                + "color: #0c6eef;\n"
                                + "text-align: center;\n"
                                + "}\n"
                                + ".highlight {\n"
                                + "color: #0c6eef;\n"
                                + "font-weight: bold;\n"
                                + "}\n"
                                + ".details {\n"
                                + "margin-top: 20px;\n"
                                + "}\n"
                                + ".details p {\n"
                                + "margin: 5px 0;\n"
                                + "}\n"
                                + ".footer {\n"
                                + "text-align: center;\n"
                                + "margin-top: 20px;\n"
                                + "font-size: 0.9em;\n"
                                + "color: #777;\n"
                                + "}\n"
                                + "</style>\n"
                                + "</head>\n"
                                + "<body>\n"
                                + "<div class=\"container\">\n"
                                + "<h1>¡Recordatorio de tu evento en una semana!</h1>\n"
                                + "<div class=\"details\">\n"
                                + "<h2>Evento: <span class=\"highlight\">" + evento.getNomEvento() + "</span></h2>\n"
                                + "<p><strong>Fecha:</strong> " + evento.getFecha() + "</p>\n"
                                + "<p><strong>Hora:</strong> De " + evento.getHoraInicio() + " a " + evento.getHoraFinal() + "</p>\n"
                                + "<p><strong>Facultad:</strong> " + evento.getFacultad() + "</p>\n"
                                + "<p><strong>Campus:</strong> " + evento.getCampus() + "</p>\n"
                                + "<p><strong>Lugar:</strong> " + evento.getLugar() + "</p>\n"
                                + "<p><strong>Descripción:</strong> " + evento.getDescripcion() + "</p>\n"
                                + "<p><strong>Encargado:</strong> " + evento.getEncargado() + "</p>\n"
                                + "</div>\n"
                                + "<img src='" + urlImagen + "' alt='Imagen del Evento' />"
                                + "<div class=\"footer\">\n"
                                + "<p>Si tienes alguna pregunta, no dudes en contactarnos. ¡Nos vemos pronto!</p>\n"

                                + "</div>\n"
                                + "</div>\n"
                                + "</body>\n"
                                + "</html>";

                        emailServicio.enviarCorreo(destinatario, asunto, mensajeHTML, true); // `true` indica que es HTML
                    }


// Enviar correo de recordatorio un día antes del evento
                    if (diasHastaEvento == 1) {
                        String asunto = "Recordatorio: Evento mañana";
                        String urlImagen = evento.getImagen(); // Asegúrate de que este valor sea la URL completa y accesible
                        String mensajeHTML = "<html>\n"
                                + "<head>\n"
                                + "<style>\n"
                                + "body {\n"
                                + "font-family: Arial, sans-serif;\n"
                                + "background-color: #f4f4f9;\n"
                                + "color: #333;\n"
                                + "line-height: 1.6;\n"
                                + "}\n"
                                + ".container {\n"
                                + "width: 90%;\n"
                                + "max-width: 600px;\n"
                                + "margin: 20px auto;\n"
                                + "background: #ffffff;\n"
                                + "padding: 20px;\n"
                                + "border-radius: 8px;\n"
                                + "box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);\n"
                                + "}\n"
                                + "h1 {\n"
                                + "color: #0c6eef;\n"
                                + "text-align: center;\n"
                                + "}\n"
                                + ".highlight {\n"
                                + "color: #0c6eef;\n"
                                + "font-weight: bold;\n"
                                + "}\n"
                                + ".details {\n"
                                + "margin-top: 20px;\n"
                                + "}\n"
                                + ".details p {\n"
                                + "margin: 5px 0;\n"
                                + "}\n"
                                + ".footer {\n"
                                + "text-align: center;\n"
                                + "margin-top: 20px;\n"
                                + "font-size: 0.9em;\n"
                                + "color: #777;\n"
                                + "}\n"
                                + "</style>\n"
                                + "</head>\n"
                                + "<body>\n"
                                + "<div class=\"container\">\n"
                                + "<h1>¡Recordatorio de tu evento!</h1>\n"
                                + "<div class=\"details\">\n"
                                + "<h2>Evento: <span class=\"highlight\">" + evento.getNomEvento() + "</span></h2>\n"
                                + "<p><strong>Fecha:</strong> " + evento.getFecha() + "</p>\n"
                                + "<p><strong>Hora:</strong> De " + evento.getHoraInicio() + " a " + evento.getHoraFinal() + "</p>\n"
                                + "<p><strong>Facultad:</strong> " + evento.getFacultad() + "</p>\n"
                                + "<p><strong>Campus:</strong> " + evento.getCampus() + "</p>\n"
                                + "<p><strong>Lugar:</strong> " + evento.getLugar() + "</p>\n"
                                + "<p><strong>Descripción:</strong> " + evento.getDescripcion() + "</p>\n"
                                + "<p><strong>Encargado:</strong> " + evento.getEncargado() + "</p>\n"
                                + "</div>\n"
                                + "<img src='" + urlImagen + "' alt='Imagen del Evento' />"
                                + "<div class=\"footer\">\n"
                                + "<p>Si tienes alguna pregunta, no dudes en contactarnos. ¡Nos vemos pronto!</p>\n"

                                + "</div>\n"
                                + "</div>\n"
                                + "</body>\n"
                                + "</html>";

                        emailServicio.enviarCorreo(destinatario, asunto, mensajeHTML, true); // `true` indica que es HTML
                    }

// Enviar correo de notificación minutos antes del evento
                    if (minutosHastaEvento <= 5 && minutosHastaEvento >= 0) {
                        String asunto = "Notificación: El evento comienza pronto";
                        String mensajeHTML = "<html>\n"
                                + "<head>\n"
                                + "<style>\n"
                                + "body { font-family: Arial, sans-serif; }\n"
                                + ".highlight { color: #e63946; font-weight: bold; }\n"
                                + "</style>\n"
                                + "</head>\n"
                                + "<body>\n"
                                + "<h1>¡El evento está por comenzar!</h1>\n"
                                + "<p>El evento <span class=\"highlight\">" + evento.getNomEvento() + "</span> comenzará en pocos minutos.</p>\n"
                                + "<p><strong>Hora:</strong> " + evento.getHoraInicio() + "</p>\n"
                                + "<p>¡No olvides asistir puntualmente!</p>\n"
                                + "</body>\n"
                                + "</html>";

                        emailServicio.enviarCorreo(destinatario, asunto, mensajeHTML, true);
                    }

                }
            }
        }
    }
}
