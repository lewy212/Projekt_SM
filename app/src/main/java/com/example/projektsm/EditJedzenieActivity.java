package com.example.projektsm;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditJedzenieActivity extends AppCompatActivity {
    public static final String EXTRA_EDIT_JEDZENIE_NAZWA = "EDIT_JEDZENIE_NAZWA";
    public static final String EXTRA_EDIT_JEDZENIE_TLUSZCZE = "EDIT_JEDZENIE_TLUSZCZE";
    public static final String EXTRA_EDIT_JEDZENIE_BIALKA = "EDIT_JEDZENIE_BIALKA";
    public static final String EXTRA_EDIT_JEDZENIE_WEGLOWODANY = "EDIT_JEDZENIE_WEGLOWODANY";
    public static final String EXTRA_EDIT_JEDZENIE_KALORIE = "EDIT_JEDZENIE_KALORIE";
    private EditText editNazwaEditText;
    private EditText editTluszczeEditText;
    private EditText editBialkaEditText;
    private EditText editWeglowodanyEditText;
    private EditText editKalorieEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_jedzenie);

        editNazwaEditText = findViewById(R.id.edit_jedzenie_nazwa);
        editTluszczeEditText = findViewById(R.id.edit_jedzenie_tluszcze);
        editBialkaEditText = findViewById(R.id.edit_jedzenie_bialka);
        editWeglowodanyEditText = findViewById(R.id.edit_jedzenie_weglowodany);
        editKalorieEditText = findViewById(R.id.edit_jedzenie_kalorie);
        Intent starter = getIntent();
        if (starter.hasExtra(EXTRA_EDIT_JEDZENIE_NAZWA))
            editNazwaEditText.setText(starter.getStringExtra(EXTRA_EDIT_JEDZENIE_NAZWA));
        if (starter.hasExtra(EXTRA_EDIT_JEDZENIE_KALORIE))
            editKalorieEditText.setText(starter.getStringExtra(EXTRA_EDIT_JEDZENIE_KALORIE));
        if (starter.hasExtra(EXTRA_EDIT_JEDZENIE_TLUSZCZE))
            editTluszczeEditText.setText(starter.getStringExtra(EXTRA_EDIT_JEDZENIE_TLUSZCZE));
        if (starter.hasExtra(EXTRA_EDIT_JEDZENIE_WEGLOWODANY))
            editWeglowodanyEditText.setText(starter.getStringExtra(EXTRA_EDIT_JEDZENIE_WEGLOWODANY));
        if (starter.hasExtra(EXTRA_EDIT_JEDZENIE_BIALKA))
            editBialkaEditText.setText(starter.getStringExtra(EXTRA_EDIT_JEDZENIE_BIALKA));

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(editNazwaEditText.getText())
                        || TextUtils.isEmpty(editBialkaEditText.getText())
                        || TextUtils.isEmpty(editTluszczeEditText.getText())
                        || TextUtils.isEmpty(editWeglowodanyEditText.getText()))
                    setResult(RESULT_CANCELED, replyIntent);
                else {
                    String nazwa = editNazwaEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_EDIT_JEDZENIE_NAZWA, nazwa);

                    String kalorie = editKalorieEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_EDIT_JEDZENIE_KALORIE, kalorie);

                    String tluszcze = editTluszczeEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_EDIT_JEDZENIE_TLUSZCZE, tluszcze);

                    String bialka = editBialkaEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_EDIT_JEDZENIE_BIALKA,bialka );

                    String weglowodany = editWeglowodanyEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_EDIT_JEDZENIE_WEGLOWODANY, weglowodany);

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
