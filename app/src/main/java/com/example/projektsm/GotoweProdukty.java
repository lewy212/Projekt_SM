package com.example.projektsm;

import java.util.ArrayList;
import java.util.List;

public class GotoweProdukty {

    private static GotoweProdukty instance = new GotoweProdukty();

    private List<Produkt> gotoweProduktyList;
    private GotoweProdukty() {
        gotoweProduktyList = new ArrayList<>();
        stworzGotoweProdukty();
    }

    public static GotoweProdukty getInstance() {
        if(instance==null)
        {
            instance = new GotoweProdukty();
        }
        return instance;
    }
    public void stworzGotoweProdukty(){
        Produkt produkt = new Produkt("Bułka",218,2,9,56);
        this.gotoweProduktyList.add(produkt);
        produkt = new Produkt("Jabłko",52,1,1,10);
        this.gotoweProduktyList.add(produkt);
        produkt = new Produkt("Czekolada",530,30,6,58);
        this.gotoweProduktyList.add(produkt);
        produkt = new Produkt("Chipsy",535,35,6,49);
        this.gotoweProduktyList.add(produkt);
    }

    public List<Produkt> getGotoweProduktyList() {
        return gotoweProduktyList;
    }

    public void setGotoweProduktyList(List<Produkt> gotoweProduktyList) {
        this.gotoweProduktyList = gotoweProduktyList;
    }
}
