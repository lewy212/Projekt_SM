package com.example.projektsm;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class DodajGotoweActivity extends AppCompatActivity {
    public static final String EXTRA_EDIT_JEDZENIE_NAZWA = "EDIT_JEDZENIE_NAZWA";
    public static final String EXTRA_EDIT_JEDZENIE_TLUSZCZE = "EDIT_JEDZENIE_TLUSZCZE";
    public static final String EXTRA_EDIT_JEDZENIE_BIALKA = "EDIT_JEDZENIE_BIALKA";
    public static final String EXTRA_EDIT_JEDZENIE_WEGLOWODANY = "EDIT_JEDZENIE_WEGLOWODANY";
    public static final String EXTRA_EDIT_JEDZENIE_KALORIE = "EDIT_JEDZENIE_KALORIE";

    private Spinner nazwaSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_gotowe_jedzenie);
        List<String> nazwyProduktow = new ArrayList<>();
        List<Produkt> gotoweProduktyList = GotoweProdukty.getInstance().getGotoweProduktyList();
        for (Produkt produkt : gotoweProduktyList ) {
            nazwyProduktow.add(produkt.getNazwa());
        }
        nazwaSpinner = findViewById(R.id.jedzenie_wybierz);
        nazwaSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nazwyProduktow));
        nazwaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final Button button = findViewById(R.id.zapisz_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wybranaNazwaProduktu = (String) nazwaSpinner.getSelectedItem();
                Produkt dodawanyProdukt = null;
                if (!TextUtils.isEmpty(wybranaNazwaProduktu)) {
                    Intent replyIntent = new Intent();
                    for (Produkt produkt : gotoweProduktyList ) {
                        if(produkt.getNazwa().equals(wybranaNazwaProduktu))
                        {
                            dodawanyProdukt = produkt;
                            break;
                        }
                    }
                    if(dodawanyProdukt!=null)
                    {
                        replyIntent.putExtra(EXTRA_EDIT_JEDZENIE_NAZWA, dodawanyProdukt.getNazwa());

                        replyIntent.putExtra(EXTRA_EDIT_JEDZENIE_KALORIE, String.valueOf(dodawanyProdukt.getKalorie()));


                        replyIntent.putExtra(EXTRA_EDIT_JEDZENIE_TLUSZCZE, String.valueOf(dodawanyProdukt.getTluszcze()));

                        replyIntent.putExtra(EXTRA_EDIT_JEDZENIE_BIALKA,String.valueOf(dodawanyProdukt.getBialka()) );

                        replyIntent.putExtra(EXTRA_EDIT_JEDZENIE_WEGLOWODANY, String.valueOf(dodawanyProdukt.getWeglowodany()));
                    }


                    setResult(RESULT_OK, replyIntent);
                } else {
                    // Jeśli nazwa produktu jest pusta, możesz obsłużyć to odpowiednio, na przykład wyświetlając komunikat błędu
                    // Toast.makeText(DodajGotoweActivity.this, "Wybierz nazwę produktu", Toast.LENGTH_SHORT).show();
                }

                // Zakończ aktywność
                finish();
            }
        });

    }
}
