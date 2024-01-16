package com.example.projektsm;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuotesContainer {
    @SerializedName("quotes")
    private List<Quote> quotes;

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }
}
