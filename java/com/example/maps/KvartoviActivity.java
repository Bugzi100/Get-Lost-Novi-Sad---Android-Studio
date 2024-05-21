package com.example.maps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class KvartoviActivity extends AppCompatActivity implements View.OnClickListener {

    private MapaGrada mapaGrada;
    private Zadatak zadatak;
    private String imeKvarta;
    private TextView kvartImage;
    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kvartovi);

        mapaGrada = new MapaGrada(new HashMap<>());
        zadatak = new Zadatak(new ArrayList<>(), new ArrayList<>());

        prefs = getSharedPreferences("DistrictPrefs", MODE_PRIVATE);

        if (prefs.getBoolean("Stari grad", false)) {
            changeImage("Stari grad");
        }
        if (prefs.getBoolean("Rotkvarija", false)) {
            changeImage("Rotkvarija");
        }
        if (prefs.getBoolean("Podbara", false)) {
            changeImage("Podbara");
        }
        if (prefs.getBoolean("Petrovaradin", false)) {
            changeImage("Petrovaradin");
        }
        if (prefs.getBoolean("Liman", false)) {
            changeImage("Liman");
        }
        if (prefs.getBoolean("Telep", false)) {
            changeImage("Telep");
        }
        if (prefs.getBoolean("Novo naselje", false)) {
            changeImage("Novo naselje");
        }
        if (prefs.getBoolean("Detelinara", false)) {
            changeImage("Detelinara");
        }
        if (prefs.getBoolean("Sajmište", false)) {
            changeImage("Sajmište");
        }
        if (prefs.getBoolean("Grbavica", false)) {
            changeImage("Grbavica");
        }

        Intent intent = getIntent();
        if (intent != null) {
            imeKvarta = intent.getStringExtra("resen_kvart");
            changeImage(imeKvarta);
        }
    }

    public void onClick(View v) {
        if (v instanceof TextView) {
            TextView clickedTextView = (TextView) v;

            int clickedTextViewId = clickedTextView.getId();
            String districtName = clickedTextView.getText().toString();

            HashMap<String, HashMap<String, ArrayList<String>>> kvartovi = (HashMap<String, HashMap<String, ArrayList<String>>>) mapaGrada.getKvartovi();
            HashMap<String, ArrayList<String>> uliceIZnamenitosti = kvartovi.get(districtName);

            if (uliceIZnamenitosti != null && !uliceIZnamenitosti.isEmpty()) {
                String randomUlica = getRandomUlica(uliceIZnamenitosti);
                String randomZnamenitost = getRandomZnamenitost(uliceIZnamenitosti.get(randomUlica));
                String randomObicanZadatak = getRandomObicanZadatak();
                String randomZanimljivZadatak = getRandomZanimljivZadatak();

                Intent intent = new Intent(this, MapsActivity.class);
                intent.putExtra("kvart", districtName);
                intent.putExtra("random_street", randomUlica);
                intent.putExtra("random_landmark", randomZnamenitost);
                intent.putExtra("random_obican", randomObicanZadatak);
                intent.putExtra("random_zanimljiv", randomZanimljivZadatak);
                startActivity(intent);
            }

//            if (clickedTextViewId == R.id.subMenu1) {
//
//            } else if (clickedTextViewId == R.id.subMenu2) {
//                clickedTextView.setText("2");
//            } else if (clickedTextViewId == R.id.subMenu3) {
//                clickedTextView.setText("3");
//            } else if (clickedTextViewId == R.id.subMenu4) {
//                clickedTextView.setText("4");
//            } else if (clickedTextViewId == R.id.subMenu5) {
//                clickedTextView.setText("5");
//            } else if (clickedTextViewId == R.id.subMenu6) {
//                clickedTextView.setText("6");
//            } else if (clickedTextViewId == R.id.subMenu7) {
//                clickedTextView.setText("7");
//            } else if (clickedTextViewId == R.id.subMenu8) {
//                clickedTextView.setText("8");
//            } else if (clickedTextViewId == R.id.subMenu9) {
//                clickedTextView.setText("9");
//            } else if (clickedTextViewId == R.id.subMenu10) {
//                clickedTextView.setText("10");
//            }
        }
    }

    private void changeImage(String imeKvarta) {
        if (imeKvarta != null) {
            switch (imeKvarta) {
                case "Stari grad":
                    kvartImage = findViewById(R.id.subMenu1);
                    kvartImage.setBackgroundResource(R.drawable.checked);
                    kvartImage.setTextColor(Color.BLACK);
                    break;
                case "Rotkvarija":
                    kvartImage = findViewById(R.id.subMenu2);
                    kvartImage.setBackgroundResource(R.drawable.checked);
                    kvartImage.setTextColor(Color.BLACK);
                    break;
                case "Podbara":
                    kvartImage = findViewById(R.id.subMenu3);
                    kvartImage.setBackgroundResource(R.drawable.checked);
                    kvartImage.setTextColor(Color.BLACK);
                    break;
                case "Petrovaradin":
                    kvartImage = findViewById(R.id.subMenu4);
                    kvartImage.setBackgroundResource(R.drawable.checked);
                    kvartImage.setTextColor(Color.BLACK);
                    break;
                case "Liman":
                    kvartImage = findViewById(R.id.subMenu5);
                    kvartImage.setBackgroundResource(R.drawable.checked);
                    kvartImage.setTextColor(Color.BLACK);
                    break;
                case "Telep":
                    kvartImage = findViewById(R.id.subMenu6);
                    kvartImage.setBackgroundResource(R.drawable.checked);
                    kvartImage.setTextColor(Color.BLACK);
                    break;
                case "Novo naselje":
                    kvartImage = findViewById(R.id.subMenu7);
                    kvartImage.setBackgroundResource(R.drawable.checked);
                    kvartImage.setTextColor(Color.BLACK);
                    break;
                case "Detelinara":
                    kvartImage = findViewById(R.id.subMenu8);
                    kvartImage.setBackgroundResource(R.drawable.checked);
                    kvartImage.setTextColor(Color.BLACK);
                    break;
                case "Sajmište":
                    kvartImage = findViewById(R.id.subMenu9);
                    kvartImage.setBackgroundResource(R.drawable.checked);
                    kvartImage.setTextColor(Color.BLACK);
                    break;
                case "Grbavica":
                    kvartImage = findViewById(R.id.subMenu10);
                    kvartImage.setBackgroundResource(R.drawable.checked);
                    kvartImage.setTextColor(Color.BLACK);
                    break;
            }
        }
    }

    private String getRandomUlica(HashMap<String, ArrayList<String>> uliceIZnamenitosti) {
        Random random = new Random();
        Set<String> randomUlice = uliceIZnamenitosti.keySet();
        List<String> randomUliceList = new ArrayList<>(randomUlice);
        int randomIndex = random.nextInt(randomUlice.size());
        return randomUliceList.get(randomIndex);
    }

    private String getRandomZnamenitost(List<String> znamenitosti) {
        if (znamenitosti == null || znamenitosti.isEmpty()) {
            return "Nema dostupnih znamenitosti!";
        }
        Random random = new Random();
        int randomIndex = random.nextInt(znamenitosti.size());
        return znamenitosti.get(randomIndex);
    }

    private String getRandomObicanZadatak() {
        List<String> obicniZadaci = zadatak.getObicniZadaci();

        Random random = new Random();
        int randomIndex1 = random.nextInt(obicniZadaci.size());
        return obicniZadaci.get(randomIndex1);
    }

    private String getRandomZanimljivZadatak() {
        List<String> zanimljiviZadaci = zadatak.getZanimljiviZadaci();

        Random random = new Random();
        int randomIndex2 = random.nextInt(zanimljiviZadaci.size());
        return zanimljiviZadaci.get(randomIndex2);
    }
}
