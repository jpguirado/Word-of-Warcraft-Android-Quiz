package com.example.alserranog.practicaquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Clase que implementa la actividad de la pantalla de juego. Desde aqui se maneja todo lo referente a la partida
 */
public class MainActivity extends AppCompatActivity {

    private RadioButton respuestaSeleccionada;
    private Pregunta[] preguntasEasy;
    private Pregunta[] preguntasHard;
    private Pregunta[] currentPreguntas;

    private int numPreguntas = 10;
    private int currentPregunta = 0;
    private int puntuacion = 0;
    private boolean reStart = false;
    private int record = 0;
    private int numFallos = 0;
    private int numAciertos = 0;

    //Radio Buttons respuestas
    RadioButton opcion1;
    RadioButton opcion2;
    RadioButton opcion3;

    //Botones para las preguntas con imagenes como respuesta
    Button buttonImage1;
    Button buttonImage2;
    Button buttonImage3;
    Button verificar;

    ImageView imagen;
    VideoView video;
    MediaPlayer audio;

    TextView textoPregunta;
    TextView textPuntuacion;
    TextView textCurrentPregunta;
    TextView textAciertosFallos;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        PackageManager m = getPackageManager();
        String s = getPackageName();
        PackageInfo p = null;
        try {
            p = m.getPackageInfo(s, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        s = p.applicationInfo.dataDir;

        opcion1 = (RadioButton) findViewById(R.id.opcion1);
        opcion2 = (RadioButton) findViewById(R.id.opcion2);
        opcion3 = (RadioButton) findViewById(R.id.opcion3);

        buttonImage1 = (Button) findViewById(R.id.BotonImagen1);
        buttonImage2 = (Button) findViewById(R.id.BotonImagen2);
        buttonImage3 = (Button) findViewById(R.id.BotonImagen3);
        verificar = (Button) findViewById(R.id.VerifyButton);

        verificar.setBackgroundResource(R.drawable.verificar);

        imagen = (ImageView) findViewById(R.id.Imagen);

        video = (VideoView)findViewById(R.id.videoView);

        textPuntuacion = (TextView) findViewById(R.id.ScoreValue);
        textoPregunta = (TextView) findViewById(R.id.Pregunta);
        textCurrentPregunta = (TextView) findViewById(R.id.currentPregunta);
        textAciertosFallos = (TextView) findViewById(R.id.aciertosFallos);
        readPreguntas();

        preguntasSetUp();
        resetPregunta();

        verificarRespuesta();

        changeActivity();
    }

    //Lee las preguntas del JSON
    public void readPreguntas(){
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("jsonpreguntas.json");
            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            Log.d("OJO", json);
            preguntasMake(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //Crea los objetos preguntas en funcion del contenido del JSON
    public void preguntasMake(JSONObject json) throws JSONException {
        JSONArray preguntasFaciles = json.getJSONArray("PreguntasFaciles");
        JSONArray preguntasDificiles = json.getJSONArray("PreguntasDificiles");

        preguntasEasy = new Pregunta[preguntasFaciles.length()];

        for (int i=0; i<preguntasFaciles.length(); i++) {
            JSONObject obj = preguntasFaciles.getJSONObject(i);
            int idPregunta =  obj.getInt("idPregunta");
            String pregunta = obj.getString("Pregunta");
            String respuesta1 = obj.getString("Respuesta1");
            boolean acierto1 = obj.getBoolean("Acierto1");
            String respuesta2 = obj.getString("Respuesta2");
            boolean acierto2 = obj.getBoolean("Acierto2");
            String respuesta3 = obj.getString("Respuesta3");
            boolean acierto3 = obj.getBoolean("Acierto3");
            boolean hasImage = obj.getBoolean("hasImage");
            String imageResource = obj.getString("imageResource");
            boolean hasImageButtons = obj.getBoolean("hasImageButtons");
            String imageButtonResource1 = obj.getString("imageButtonResource1");
            String imageButtonResource2 = obj.getString("imageButtonResource2");
            String imageButtonResource3 = obj.getString("imageButtonResource3");
            boolean hasVideo = obj.getBoolean("hasVideo");
            String videoResource = obj.getString("videoResource");
            boolean hasAudio = obj.getBoolean("hasAudio");
            String audioResource = obj.getString("audioResource");

            int imgRsc =  getResources().getIdentifier(imageResource, "drawable", getApplicationContext().getPackageName());
            int vidRsc = getResources().getIdentifier(videoResource, "raw", getApplicationContext().getPackageName());
            int audRsc = getResources().getIdentifier(audioResource, "raw", getApplicationContext().getPackageName());

            int [] preguntasButtonId = new int[3];

            preguntasButtonId[0] = getResources().getIdentifier(imageButtonResource1, "drawable", getApplicationContext().getPackageName());
            preguntasButtonId[1] = getResources().getIdentifier(imageButtonResource2, "drawable", getApplicationContext().getPackageName());
            preguntasButtonId[2] = getResources().getIdentifier(imageButtonResource3, "drawable", getApplicationContext().getPackageName());

            preguntasEasy[i] = new Pregunta(idPregunta, pregunta,new Respuesta(respuesta1, acierto1), new Respuesta(respuesta2, acierto2),
                    new Respuesta(respuesta3, acierto3), hasImage, imgRsc, hasImageButtons, preguntasButtonId, hasVideo, vidRsc, hasAudio, audRsc);

        }

        preguntasHard = new Pregunta[preguntasDificiles.length()];
        for (int i=0; i<preguntasDificiles.length(); i++) {
            JSONObject obj = preguntasDificiles.getJSONObject(i);
            int idPregunta =  obj.getInt("idPregunta");
            String pregunta = obj.getString("Pregunta");
            String respuesta1 = obj.getString("Respuesta1");
            boolean acierto1 = obj.getBoolean("Acierto1");
            String respuesta2 = obj.getString("Respuesta2");
            boolean acierto2 = obj.getBoolean("Acierto2");
            String respuesta3 = obj.getString("Respuesta3");
            boolean acierto3 = obj.getBoolean("Acierto3");
            boolean hasImage = obj.getBoolean("hasImage");
            String imageResource = obj.getString("imageResource");
            boolean hasImageButtons = obj.getBoolean("hasImageButtons");
            String imageButtonResource1 = obj.getString("imageButtonResource1");
            String imageButtonResource2 = obj.getString("imageButtonResource2");
            String imageButtonResource3 = obj.getString("imageButtonResource3");
            boolean hasVideo = obj.getBoolean("hasVideo");
            String videoResource = obj.getString("videoResource");
            boolean hasAudio = obj.getBoolean("hasAudio");
            String audioResource = obj.getString("audioResource");

            int imgRsc =  getResources().getIdentifier(imageResource, "drawable", getApplicationContext().getPackageName());
            int vidRsc = getResources().getIdentifier(videoResource, "raw", getApplicationContext().getPackageName());
            int audRsc = getResources().getIdentifier(audioResource, "raw", getApplicationContext().getPackageName());

            int [] preguntasButtonId = new int[3];

            preguntasButtonId[0] = getResources().getIdentifier(imageButtonResource1, "drawable", getApplicationContext().getPackageName());
            preguntasButtonId[1] = getResources().getIdentifier(imageButtonResource2, "drawable", getApplicationContext().getPackageName());
            preguntasButtonId[2] = getResources().getIdentifier(imageButtonResource3, "drawable", getApplicationContext().getPackageName());

            preguntasHard[i] = new Pregunta(idPregunta, pregunta,new Respuesta(respuesta1, acierto1), new Respuesta(respuesta2, acierto2),
                    new Respuesta(respuesta3, acierto3), hasImage, imgRsc, hasImageButtons, preguntasButtonId, hasVideo, vidRsc, hasAudio, audRsc);

        }
        currentPreguntas = preguntasEasy;

    }

    //Verificar respuesta para las preguntas con imagenes como respuestas
    public void preguntasSetUp(){


        buttonImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPreguntas[currentPregunta].respuestas[0].isAcierto()){
                    Toast.makeText(MainActivity.this, "Has acertado", Toast.LENGTH_SHORT).show();
                    incPuntuacion();
                }else {
                    preguntaFallada();
                }
                incCurrentPregunta();
                resetPregunta();
            }

        });

        buttonImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPreguntas[currentPregunta].respuestas[1].isAcierto()){
                    Toast.makeText(MainActivity.this, "Has acertado", Toast.LENGTH_SHORT).show();
                    incPuntuacion();
                }else {
                    preguntaFallada();
                }
                incCurrentPregunta();
                resetPregunta();
            }
        });

        buttonImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPreguntas[currentPregunta].respuestas[2].isAcierto()){
                    Toast.makeText(MainActivity.this, "Has acertado", Toast.LENGTH_SHORT).show();
                    incPuntuacion();
                }else {
                    preguntaFallada();
                }
                incCurrentPregunta();
                resetPregunta();
            }
        });
    }

    //Verificar respuesta correcta
    public void verificarRespuesta()
    {

       if(!currentPreguntas[currentPregunta].hasImageButtons()) {
           final RadioGroup respuestas = (RadioGroup) findViewById(R.id.Respuestas);

           verificar.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int idSeleccionado = respuestas.getCheckedRadioButtonId();
                   respuestas.clearCheck();
                   if (idSeleccionado == -1) {
                       Toast.makeText(MainActivity.this, "¡Selecciona una opción!", Toast.LENGTH_SHORT).show();
                   } else {
                       respuestaSeleccionada = (RadioButton) findViewById(idSeleccionado);
                       respuestaSeleccionada.setChecked(false);
                       if (currentPreguntas[currentPregunta].hasAudio())
                           audio.stop();

                       if (respuestaSeleccionada.getText() == currentPreguntas[currentPregunta].getRespuestas()[0].getRespuesta()) {
                           if (currentPreguntas[currentPregunta].getRespuestas()[0].isAcierto()) {
                               Toast.makeText(MainActivity.this, "Has acertado", Toast.LENGTH_SHORT).show();
                               incPuntuacion();
                           } else {
                               preguntaFallada();
                           }
                           incCurrentPregunta();
                           resetPregunta();
                       }
                       if (respuestaSeleccionada.getText() == currentPreguntas[currentPregunta].getRespuestas()[1].getRespuesta()) {
                           if (currentPreguntas[currentPregunta].getRespuestas()[1].isAcierto()) {
                               Toast.makeText(MainActivity.this, "Has acertado", Toast.LENGTH_SHORT).show();
                               incPuntuacion();
                           } else {
                               preguntaFallada();
                           }
                           incCurrentPregunta();
                           resetPregunta();
                       }

                       if (respuestaSeleccionada.getText() == currentPreguntas[currentPregunta].getRespuestas()[2].getRespuesta()) {
                           if (currentPreguntas[currentPregunta].getRespuestas()[2].isAcierto()) {
                               Toast.makeText(MainActivity.this, "Has acertado", Toast.LENGTH_SHORT).show();
                               incPuntuacion();
                           } else {
                               preguntaFallada();
                           }
                           idSeleccionado = -1;
                           incCurrentPregunta();
                           resetPregunta();
                       }

                       if (reStart) {
                           reStart = false;


                           //Grabar la puntuación en la base de datos
                           String puntuacionAGrabar = Integer.toString(puntuacion);
                           Toast.makeText(MainActivity.this, "Tu puntuación ha sido de: " +puntuacionAGrabar + " puntos", Toast.LENGTH_SHORT).show();


                           changetoPuntuación();
                           //resetPregunta();
                       }
                   }
               }
           });
       }
    }

    //Resetea y cambia de pregunta
    public void resetPregunta(){

        textoPregunta.setText(currentPreguntas[currentPregunta].getPregunta());

        if(!currentPreguntas[currentPregunta].hasImageButtons()) {

            opcion1.setVisibility(View.VISIBLE);
            opcion2.setVisibility(View.VISIBLE);
            opcion3.setVisibility(View.VISIBLE);
            verificar.setVisibility(View.VISIBLE);
            imagen.setVisibility(View.VISIBLE);
            buttonImage1.setVisibility(View.INVISIBLE);
            buttonImage2.setVisibility(View.INVISIBLE);
            buttonImage3.setVisibility(View.INVISIBLE);

            video.setVisibility(View.INVISIBLE);
            video.stopPlayback();
            //audio.stop();
            opcion2.setText(currentPreguntas[currentPregunta].getRespuestas()[1].getRespuesta());
            opcion1.setText(currentPreguntas[currentPregunta].getRespuestas()[0].getRespuesta());
            opcion3.setText(currentPreguntas[currentPregunta].getRespuestas()[2].getRespuesta());


            textPuntuacion.setText(Integer.toString(puntuacion));


            if (currentPreguntas[currentPregunta].hasImage()) {
                imagen.setImageResource(currentPreguntas[currentPregunta].getImageResource());
            } else {
                imagen.setImageResource(0);
            }

            if(currentPreguntas[currentPregunta].hasVideo()){

                video.setVisibility(View.VISIBLE);
                video.setVideoURI(Uri.parse("android.resource://" +getPackageName()+ "/" + currentPreguntas[currentPregunta].getVideoResoure() ));
                video.setMediaController(new MediaController(this));
                video.requestFocus();
                video.start();


            }
            if(currentPreguntas[currentPregunta].hasAudio()){
                audio = MediaPlayer.create(MainActivity.this,  currentPreguntas[currentPregunta].getAudioResource());
                audio.start();
                imagen.setImageResource(R.drawable.imagenhasaudio);

            }
        }
        else {
            opcion1.setVisibility(View.INVISIBLE);
            opcion2.setVisibility(View.INVISIBLE);
            opcion3.setVisibility(View.INVISIBLE);
            verificar.setVisibility(View.INVISIBLE);
            imagen.setVisibility(View.INVISIBLE);
            buttonImage1.setVisibility(View.VISIBLE);
            buttonImage2.setVisibility(View.VISIBLE);
            buttonImage3.setVisibility(View.VISIBLE);
            video.setVisibility(View.INVISIBLE);
            video.stopPlayback();
            //audio.stop();
            buttonImage1.setBackgroundResource(currentPreguntas[currentPregunta].getImageButtonsResources()[0]);
            buttonImage2.setBackgroundResource(currentPreguntas[currentPregunta].getImageButtonsResources()[1]);
            buttonImage3.setBackgroundResource(currentPreguntas[currentPregunta].getImageButtonsResources()[2]);


        }

    }

    //Pasar a la siguiente pregunta
    private void incCurrentPregunta(){
        String aciertosfallos = Integer.toString(numAciertos) + " / " + Integer.toString(numFallos);
        if(currentPregunta == numPreguntas-1 ){
            String numPreguntastext = Integer.toString(currentPregunta) + " / " + Integer.toString(numPreguntas);
            currentPregunta = 0;
            reStart = true;
            textCurrentPregunta.setText(numPreguntastext);
            textAciertosFallos.setText(aciertosfallos);
        }else {
            currentPregunta++;
            String numPreguntastext = Integer.toString((currentPregunta+1)) + " / " + Integer.toString(numPreguntas);
            textCurrentPregunta.setText(numPreguntastext);
            textAciertosFallos.setText(aciertosfallos);
        }
    }

    //Cambiar a la actividad del menu
    private void changeActivity() {

        Intent nextScreen = new Intent(getApplicationContext(), Menu.class);
        boolean firstTime = true;
        nextScreen.putExtra("FirstTime", firstTime);
        startActivity(nextScreen);

    }

    //Cambiar a la actividad de las puntuaciones
    private void changetoPuntuación() {

        Intent nextScreen = new Intent(getApplicationContext(), Puntuaciones.class);

        nextScreen.putExtra("puntuacion",puntuacion);
        nextScreen.putExtra("escribir",true);
        nextScreen.putExtra("fromGame", true);

        startActivity(nextScreen);

    }

    private void incPuntuacion(){
        puntuacion = puntuacion +10;
        numAciertos++;
    }

    private void decPuntuacion(){
        if(puntuacion >0)
            puntuacion = puntuacion -10;
    }


    private void preguntaFallada() {
        numFallos++;
        decPuntuacion();
    }

   private boolean getDificultad(){
       SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
       return pref.getBoolean("Dificultad", false);
   }

    private int getNumPreguntas(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        return pref.getInt("NumPreguntas", 10);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(!getDificultad())
        currentPreguntas = preguntasEasy;
        else
            currentPreguntas = preguntasHard;
        puntuacion = 0;
        numAciertos = 0;
        numFallos = 0;
        String aciertosfallos = Integer.toString(numAciertos) + " / " + Integer.toString(numFallos);
        textAciertosFallos.setText(aciertosfallos);
        numPreguntas = getNumPreguntas();

        String numPreguntastext = Integer.toString((currentPregunta+1)) + " / " + Integer.toString(numPreguntas);
        textCurrentPregunta.setText(numPreguntastext);

        resetPregunta();
    }

}
