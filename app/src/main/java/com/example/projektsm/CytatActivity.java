package com.example.projektsm;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CytatActivity extends AppCompatActivity {
    public static final String EXTRA_CYTAT_API= "CYTAT_API";
    private TextView wyswietlCytat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cytat);
        wyswietlCytat = findViewById(R.id.wyswietl_cytat);
        final Button button = findViewById(R.id.cofnij_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fetchQuoteData();
    }

    private void fetchQuoteData() {
        QuotesService quotesService = RetrofitInstance.get().create(QuotesService.class);
        String apiKey = "Bk8B3C9O57ims+/wCdIvAQ==HBOPXDoarPLgWmIT";
        Call<List<Quote>> quotesContainerCall = quotesService.fetchQuote(apiKey);
        quotesContainerCall.enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(Call<List<Quote>> call, Response<List<Quote>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Quote> quotesList = response.body();
                    if (quotesList != null && !quotesList.isEmpty()) {
                        Quote firstQuote = quotesList.get(0);
                        String content="'";
                         content += firstQuote.getQuote()+"'";
                        wyswietlCytat.setText(content);
                    } else {

                        Log.e("API Call", "Empty quotes list");
                    }
                } else {

                    Log.e("API Call", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Quote>> call, Throwable t) {

                Log.e("API Call", "Failure: " + t.getMessage());
            }
        });
    }


}
