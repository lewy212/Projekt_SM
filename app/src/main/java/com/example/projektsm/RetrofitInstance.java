package com.example.projektsm;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitInstance {

    private static Retrofit retrofit;

    public static final String QUOTES_API_URL = "https://api.api-ninjas.com/v1/";

    public static Retrofit get() {
        if(retrofit == null) {
            OkHttpClient client = new OkHttpClient();
            retrofit = new Retrofit.Builder()
                    .baseUrl(QUOTES_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit;
    }
}