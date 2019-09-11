package com.example.alserranog.practicaquiz;

import java.util.Comparator;

/**
 * Clase utilizada para almacenar nombre y puntuación del jugador para posteriormente ordenar por por puntuaciones
 */

public class puntuacionNombre {
    String nombre;
    int puntuación;

    public puntuacionNombre(String nombre, int puntuación) {
        this.nombre = nombre;
        this.puntuación = puntuación;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntuación() {
        return puntuación;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPuntuación(int puntuación) {
        this.puntuación = puntuación;
    }


}
