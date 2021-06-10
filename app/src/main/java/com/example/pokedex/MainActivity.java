package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    public static Activity act;
    public static TextView txtDisplay;
    public static ImageView imgPok;
    public static String id = "";
    public static int idint = 0;
    public static int firts = 0;
    public static ArrayList<String> pokemonList = new ArrayList<>();


    public static ImageView [] imgType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (firts == 0){
            fetchData process = new fetchData("1");
            process.execute();
            firts++;
        }

        act = this;
        imgType = new ImageView[2];

        txtDisplay = findViewById(R.id.txtDisplay);
        imgPok = findViewById(R.id.imgPok);
        imgType[0] = findViewById(R.id.imgType0);
        imgType[1] = findViewById(R.id.imgType1);

       ImageButton btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        ImageButton btnTypes = findViewById(R.id.btnTypes);
        btnTypes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BusquedaTipos();
            }
        });

        Button btnLeft = findViewById(R.id.btnLeft);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (id != "") {
                    idint = Integer.parseInt(id);
                    if (idint - 1 >= 1){
                        idint--;
                        id = String.valueOf(idint);
                        fetchData process = new fetchData(id);
                        process.execute();
                    }
                }
            }
        });


        Button btnRight = findViewById(R.id.btnRight);
        btnRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (id != "") {
                    idint = Integer.parseInt(id);
                    idint++;
                    id = String.valueOf(idint);
                    fetchData process = new fetchData(id);
                    process.execute();
                }
            }
        });



    }

    public void showTxtSearch(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Search a Pokemon");

        final EditText input = new EditText(this);
        input.setHint("Pokemon");
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String pokSearch = input.getText().toString();
                fetchData process = new fetchData(pokSearch);
                process.execute();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    public void BusquedaTipos() {
        String[] items = {"fire", "water", "grass", "normal", "bug", "dragon", "electric", "ghost", "fighting",
                "psychic", "rock", "dark", "ground", "poison", "flying"};
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Buscar Pokemon");
        alerta.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String tipoSeleccionado = items[which];
                fetchDataTiposPokemon process = new fetchDataTiposPokemon(tipoSeleccionado);
                process.execute();
            }});
        alerta.show();
    }
}