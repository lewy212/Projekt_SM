package com.example.projektsm;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface QuotesService {

    @GET("quotes?category=success")
    Call<List<Quote>> fetchQuote(@Header("X-Api-Key") String apiKey);
}
