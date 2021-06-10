package com.example.pokedex;


import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.ahmadrosid.svgloader.SvgLoader;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;


public class fetchDataTiposPokemon extends AsyncTask<Void, Void, Void> {

    protected String data = "";
    protected ArrayList<String> pokemonList;
    protected String pokSearch;

    public fetchDataTiposPokemon(String pokSearch) {
        this.pokSearch = pokSearch;
        pokemonList = new ArrayList<String>();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Make API connection
            URL url = new URL("https://pokeapi.co/api/v2/type/" + pokSearch.toLowerCase());
            Log.i("logtest", "https://pokeapi.co/api/v2/type/" + pokSearch);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // Read API results
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sBuilder = new StringBuilder();

            // Build JSON String
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            data = sBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(Void aVoid){
        JSONObject jObject = null;

        try {
            jObject = new JSONObject(data);


            JSONArray pokemon = new JSONArray(jObject.getString("pokemon"));
            JSONObject pokemon0 = new JSONObject(pokemon.getString(0));
            JSONObject pokemon02 = new JSONObject(pokemon0.getString("pokemon"));
            fetchData fetchData = new fetchData(pokemon02.getString("name"));
            fetchData.execute();

            for(int i=0; i<pokemon.length(); i++){
                JSONObject pokemonItem = new JSONObject(pokemon.getString(i));
                JSONObject pokemonItem2 = new JSONObject(pokemonItem.getString("pokemon"));
                String pokemonToList = pokemonItem2.getString("name");
                pokemonList.add(pokemonToList);
                MainActivity.pokemonList = pokemonList;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
