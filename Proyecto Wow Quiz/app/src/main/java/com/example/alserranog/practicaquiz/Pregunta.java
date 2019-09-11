package com.example.alserranog.practicaquiz;

/**
 * Clase utilizada para manejar las preguntas de las parttidas
 */

public class Pregunta {
    private int idPregunta;
    public Respuesta[] respuestas;
    private String pregunta;

    private boolean hasImage;
    private boolean hasImageButtons;
    private boolean hasVideo;
    private boolean hasAudio;


    private int audioResource;


    private int imageResource;
    private int [] imageButtonsResources;

    private int videoResoure;



    public Pregunta(int idPregunta, String pregunta, Respuesta respuesta1, Respuesta respuesta2, Respuesta respuesta3, Boolean hasImage,
                    int imageResource, boolean hasImageButtons, int[] imageButtonsResources, boolean hasVideo, int videoResource, boolean hasAudio, int audioResource) {
        this.idPregunta = idPregunta;
        this.respuestas = new Respuesta[3];
        this.pregunta = pregunta;
        this.respuestas[0] = respuesta1;
        this.respuestas[1] = respuesta2;
        this.respuestas[2] = respuesta3;
        this.hasImage = hasImage;
        this.imageResource = imageResource;
        this.hasImageButtons = hasImageButtons;
        this.imageButtonsResources = imageButtonsResources;
        this.hasVideo = hasVideo;
        this.videoResoure = videoResource;
        this.hasAudio = hasAudio;
        this.audioResource = audioResource;

    }

    public int getIdPregunta() {
        return idPregunta;
    }
    public String getPregunta() {
        return pregunta;
    }
    public Respuesta[] getRespuestas() {
        return respuestas;
    }
    public boolean hasImage() {
        return hasImage;
    }
    public int getImageResource() {
        return imageResource;
    }

    public boolean hasImageButtons() {
        return hasImageButtons;
    }

    public int[] getImageButtonsResources() {
        return imageButtonsResources;
    }

    public boolean hasVideo() {
        return hasVideo;
    }

    public int getVideoResoure() {
        return videoResoure;
    }

    public boolean hasAudio() {
        return hasAudio;
    }

    public int getAudioResource() {
        return audioResource;
    }

}
