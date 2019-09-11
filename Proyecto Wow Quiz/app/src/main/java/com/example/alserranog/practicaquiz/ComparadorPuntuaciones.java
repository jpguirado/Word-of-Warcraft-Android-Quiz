package com.example.alserranog.practicaquiz;

import java.util.Comparator;

/**
 * Clase empleada para poder ordenar objetos de la clase puntuación nombre
 */

public class ComparadorPuntuaciones implements Comparator<puntuacionNombre> {

    @Override
    public int compare(puntuacionNombre o1, puntuacionNombre o2) {
        return o2.getPuntuación() - o1.getPuntuación();
    }
}
