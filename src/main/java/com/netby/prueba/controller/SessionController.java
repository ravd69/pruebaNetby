package com.netby.prueba.controller;

import com.netby.prueba.model.Session;
import com.netby.prueba.model.Talk;
import com.netby.prueba.repository.TalkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/")
public class SessionController {

    @Autowired
    private TalkRepository talkRepository;

    @PostMapping(value = "/createSession")
    public List<Session> createSessions() {
        List<Session> sesionesManiana = new ArrayList<>();
        sesionesManiana.add(new Session(180));
        List<Session> sesionesTarde = new ArrayList<>();
        sesionesTarde.add(new Session(240));

        for (Talk talk : talkRepository.findAll()) {
            boolean added = false;
            for (Session session : sesionesManiana) {
                if (session.addTalk(talk)) {
                    added = true;
                    break;
                }
            }
            if (!added) {
                for (Session session : sesionesTarde) {
                    if (session.addTalk(talk)) {
                        break;
                    }
                }
            }
        }

        int trackNumber = 1;
        for (Session sesionMananiana : sesionesManiana) {
            System.out.println(sesionMananiana);
            imprimirPantalla(trackNumber, "Mañana", sesionMananiana);
            trackNumber++;
        }
        for (Session sesionTarde : sesionesTarde) {
            imprimirPantalla(trackNumber, "Tarde", sesionTarde);
            trackNumber++;
        }
        return null;
    }

    private static void imprimirPantalla(int trackNumber, String sessionType, Session session) {
        System.out.println("Track " + trackNumber + ":");
        int startTime = sessionType.equals("Mañana") ? 9 * 60 : 13 * 60;
        for (Talk talk : session.talks) {
            int hours = startTime / 60;
            int minutes = startTime % 60;
            String timeOfDay = hours < 12 ? "AM" : "PM";
            System.out.printf("%02d:%02d%s %s %dmin%n", hours % 12, minutes, timeOfDay, talk.getNombre(), talk.getDuracionMinutos());
            startTime += talk.getDuracionMinutos();
        }

        if (sessionType.equals("Mañana")) {
            System.out.println("12:00PM Lunch");
        } else {
            System.out.println("05:00PM Networking Event");
        }
    }
}


