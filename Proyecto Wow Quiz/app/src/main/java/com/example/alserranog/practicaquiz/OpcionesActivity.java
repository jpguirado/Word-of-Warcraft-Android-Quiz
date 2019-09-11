package com.example.alserranog.practicaquiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * Clase que implementa la actividad de la pantalla de opciones
 */


public class OpcionesActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones_layout);


        Button facil = (Button) findViewById(R.id.facil);
        Button dificil = (Button) findViewById(R.id.dificil);
        Button cerrar = (Button) findViewById(R.id.close);
        Button cambiarNombre = (Button) findViewById(R.id.cambiarNombre);

        facil.setBackgroundResource(R.drawable.botonfacil);

        dificil.setBackgroundResource(R.drawable.botondificil);

        cerrar.setBackgroundResource(R.drawable.volvermenu);
        cambiarNombre.setBackgroundResource(R.drawable.botoncambiarnombre);
        Button diezPreguntas = (Button) findViewById(R.id.diezpreguntas);
        Button cincoPreguntas = (Button) findViewById(R.id.cincopreguntas);


        diezPreguntas.setBackgroundResource(R.drawable.botondiez);
        cincoPreguntas.setBackgroundResource(R.drawable.botoncinco);


        VideoView video = (VideoView)findViewById(R.id.videobackground2);
        video.setVideoURI(Uri.parse("android.resource://" +getPackageName()+ "/" + R.raw.videobackground ));
        video.requestFocus();
        video.start();
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        //Botones dificultadores
        facil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("Dificultad", false);
                editor.apply();

                Toast.makeText(OpcionesActivity.this,  "Se ha cambiado la dificultad a: Facil.", Toast.LENGTH_SHORT).show();
            }
        });

        dificil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("Dificultad", true);
                editor.apply();
                Toast.makeText(OpcionesActivity.this,  "Se ha cambiado la dificultad a: Dificil.", Toast.LENGTH_SHORT).show();

            }
        });

        //Botones número de preguntas
        diezPreguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("NumPreguntas", 10);
                editor.apply();
                Toast.makeText(OpcionesActivity.this,  "Se ha cambiado el número de preguntas a: 10.", Toast.LENGTH_SHORT).show();

            }
        });

        cincoPreguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("NumPreguntas", 5);
                editor.apply();
                Toast.makeText(OpcionesActivity.this,  "Se ha cambiado el número de preguntas a: 5.", Toast.LENGTH_SHORT).show();

            }
        });

        cambiarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeNameDialog();
            }
        });

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Cambiar el nombre del usuario
    private void changeNameDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Inserte Nombre de Usuario");
        alertDialog.setMessage("Nombre:");
        alertDialog.setCancelable(false);

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Insertar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String name = input.getText().toString();
                        setName(name);
                        Toast.makeText(OpcionesActivity.this,  "Se ha introducido el nombre: " + name, Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialog.show();

    }

    //Establecer nombre
    private void setName(String name){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Name", name);
        editor.apply();
    }
}
