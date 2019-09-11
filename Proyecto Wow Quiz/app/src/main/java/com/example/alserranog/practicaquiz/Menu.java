package com.example.alserranog.practicaquiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

/**
    Clase que implementa la actividad de la pantalla de menú principal
*/


public class Menu extends AppCompatActivity {

    private VideoView video;
    private DbManager baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent i = getIntent();

        boolean firsTime  = i.getBooleanExtra("FirstTime",false);

        if(firsTime){
          changeNameDialog();
        }

       //Video de fondo
       video = (VideoView)findViewById(R.id.videobackground);
       video.setVideoURI(Uri.parse("android.resource://" +getPackageName()+ "/" + R.raw.videobackground ));
       video.requestFocus();
       video.start();
       video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
           @Override
           public void onPrepared(MediaPlayer mp) {
               mp.setLooping(true);
           }
       });


        Button opiones = (Button) findViewById(R.id.opciones);
        opiones.setBackgroundResource(R.drawable.opciones);

        Button puntuaciones = (Button)findViewById(R.id.puntuaciones) ;
        puntuaciones.setBackgroundResource(R.drawable.puntuaciones);

        Button reStart = (Button) findViewById(R.id.Restart);
        reStart.setBackgroundResource(R.drawable.jugar);

        reStart.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                finish();
            }
        });

        opiones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               changeActivity();
            }
        });


        //Pasar a puntuaciones
        puntuaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), Puntuaciones.class);
                startActivity(nextScreen);
            }
        });

    }

    //Insertar el nombre
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
                        Toast.makeText(Menu.this,  "Se ha introducido el nombre: " + name, Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialog.show();

    }

    //Cambiar a la actividad de opciones
    private void changeActivity() {
        Intent nextScreen = new Intent(getApplicationContext(), OpcionesActivity.class);
        startActivity(nextScreen);

    }

    //Establecer el nombre del usuario
    private void setName(String name){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Name", name);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        video.start();
    }
}
