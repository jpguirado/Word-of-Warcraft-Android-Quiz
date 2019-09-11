package com.example.alserranog.practicaquiz;

/**
 * Clase utilizada para manejar las respuestas de las preguntas
 */


public class Respuesta {
    private String respuesta;
    private boolean acierto;


    public Respuesta(String respuesta, boolean acierto) {
        this.respuesta = respuesta;
        this.acierto = acierto;
    }



    public String getRespuesta() {
        return respuesta;
    }

    public boolean isAcierto() {
        return acierto;
    }
}
