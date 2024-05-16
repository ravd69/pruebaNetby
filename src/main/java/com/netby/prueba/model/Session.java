package com.netby.prueba.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    int remainingTime;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Talk> talks = new ArrayList<>();

    public Session() {

    }

    public Session(int remainingTime) {
        this.remainingTime = remainingTime;
    }


    public boolean addTalk(Talk talk) {
        if (talk.getDuracionMinutos() <= remainingTime) {
            talks.add(talk);
            remainingTime -= talk.getDuracionMinutos();
            return true;
        }
        return false;
    }

}
