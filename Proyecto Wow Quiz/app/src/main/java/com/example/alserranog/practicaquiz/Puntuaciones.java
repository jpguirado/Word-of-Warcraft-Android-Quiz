package com.example.alserranog.practicaquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Clase que implementa la actividad de la pantalla de puntuaciones.
 */

public class Puntuaciones extends AppCompatActivity {

    private ArrayList<String> puntuaciones;
    private ArrayAdapter<String> adaptador;
    private DbManager dbManager;

    private ListView lv1;
    private EditText et1;

    //Necesarios para manejar la base de datos
    private Cursor entradas;
    ArrayList<String> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuaciones2);

        this.dbManager = new DbManager(this);

        Intent i = getIntent();

        int puntuacion = i.getIntExtra("puntuacion", 0);
        boolean escribir = i.getBooleanExtra("escribir",false);
        final boolean fromGame =  i.getBooleanExtra("fromGame",false);

        if(puntuacion!=0  && escribir == true) {

            dbManager.insertEntry(getName(), Integer.toString(puntuacion));
        }

        entradas = dbManager.getEntries();

        players = new ArrayList<String>();

        entradas.moveToFirst();
        while(!entradas.isAfterLast()) {
            String playerName = entradas.getString(entradas.getColumnIndex(DbContract.DbEntry.COLUMN_NAME_PLAYER));
            String numericValue = entradas.getString(entradas.getColumnIndex(DbContract.DbEntry.COLUMN_NAME_RESULT));
            String playerEntry = playerName + ": " + numericValue;
            players.add(playerEntry); //add the item
            entradas.moveToNext();
        }

        mostrarInformacion();

        Button botonBorrar = findViewById(R.id.botonBorrar);
        botonBorrar.setBackgroundResource(R.drawable.botonborrar);
        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManager.deleteAll();

                entradas = dbManager.getEntries();
                players = new ArrayList<String>();

                entradas.moveToFirst();
                while(!entradas.isAfterLast()) {
                    String playerName = entradas.getString(entradas.getColumnIndex(DbContract.DbEntry.COLUMN_NAME_PLAYER));
                    String numericValue = entradas.getString(entradas.getColumnIndex(DbContract.DbEntry.COLUMN_NAME_RESULT));
                    String playerEntry = playerName + ": " + numericValue;
                    players.add(playerEntry); //add the item
                    entradas.moveToNext();
                }
                mostrarInformacion();
            }
        });

        Button botonVolverMenu = findViewById(R.id.botonVolverMenu);
        botonVolverMenu.setBackgroundResource(R.drawable.botonvolvermenu);
        botonVolverMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fromGame){
                    Intent nextScreen = new Intent(getApplicationContext(), Menu.class);
                    startActivity(nextScreen);
                    finish();
                }
                else
                finish();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }



    //Mostrar en la lista las puntuaciones actuales
    public void mostrarInformacion(){

        ArrayList<String> puntNombresOrdenados = new ArrayList<>();
        if(players.size()!=0) {

            ArrayList<puntuacionNombre> puntuacionesNombresOrdenados = new ArrayList<>();

            for(int j = 0; j<players.size();j++) {
                StringTokenizer st1 = new StringTokenizer(players.get(j), ":");
                for (int i = 0; st1.hasMoreTokens(); i++) {
                    String saux1 = st1.nextToken();
                    if(i == 0){
                        puntuacionesNombresOrdenados.add(new puntuacionNombre(saux1,0));
                    }
                    if (i == 1) {
                        StringTokenizer st2 = new StringTokenizer(saux1);
                        String saux2 = st2.nextToken();
                        int valorPuntuacion = Integer.parseInt(saux2);
                        puntuacionesNombresOrdenados.get(j).setPuntuación(valorPuntuacion);

                    }
                }
            }

            Collections.sort(puntuacionesNombresOrdenados, new ComparadorPuntuaciones());

            for(int i = 0; i<puntuacionesNombresOrdenados.size();i++){
                String playerEntry = puntuacionesNombresOrdenados.get(i).getNombre() + ": " + puntuacionesNombresOrdenados.get(i).getPuntuación();
                puntNombresOrdenados.add(playerEntry);
            }

        }

        ArrayAdapter adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,puntNombresOrdenados);
        lv1=(ListView)findViewById(R.id.listaPuntuaciones);
        lv1.setAdapter(adaptador);
    }

    //Recuperar el nombre
    private String getName(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        return pref.getString("Name", "Anónimo");
    }
}
