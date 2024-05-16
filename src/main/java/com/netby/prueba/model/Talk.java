package com.netby.prueba.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Talk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int duracionMinutos;

    @Column
    private boolean borrado = false;

    public void marcarComoBorrada() {
        this.borrado = true;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Session session;

    public static boolean isValidTitle(String nombre) {
        return !nombre.matches(".*\\d+.*");
    }
}
