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

    @Autowired
    private EventosRepositorio eventosRepositorio;

    @Scheduled(cron = "0 0 9 * * ?")  // Ejecuta a las 9:00 AM, 1:00 PM y 5:00 PM
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

                    // Envía recordatorio una semana antes del evento
                    if (diasHastaEvento == 7) {
                        emailServicio.enviarCorreo(
                                destinatario,
                                "Recordatorio: Evento en una semana",
                                "Tu evento \"" + evento.getNomEvento() + "\" comenzará en una semana, el " + fechaHoraEvento
                        );
                    }

                    // Envía recordatorio un día antes del evento
                    if (diasHastaEvento == 1) {
                        emailServicio.enviarCorreo(
                                destinatario,
                                "Recordatorio: Evento mañana",
                                "Tu evento \"" + evento.getNomEvento() + "\" comenzará mañana, el " + fechaHoraEvento
                        );
                    }

                    // Envía notificación cuando el evento está comenzando
                    if (minutosHastaEvento <= 5 && minutosHastaEvento >= 0) {
                        emailServicio.enviarCorreo(
                                destinatario,
                                "Notificación: ¡El evento ha comenzado!",
                                "El evento \"" + evento.getNomEvento() + "\" está comenzando ahora."
                        );
                    }
                }
            }
        }
    }
}
