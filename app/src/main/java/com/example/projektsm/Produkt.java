package com.example.projektsm;

public class Produkt {
    private String nazwa;
    private int kalorie;
    private int tluszcze;
    private int bialka;
    private int weglowodany;

    public Produkt(String nazwa, int kalorie, int tluszcze, int bialka, int weglowodany) {
        this.nazwa = nazwa;
        this.kalorie = kalorie;
        this.tluszcze = tluszcze;
        this.bialka = bialka;
        this.weglowodany = weglowodany;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getKalorie() {
        return kalorie;
    }

    public void setKalorie(int kalorie) {
        this.kalorie = kalorie;
    }

    public int getTluszcze() {
        return tluszcze;
    }

    public void setTluszcze(int tluszcze) {
        this.tluszcze = tluszcze;
    }

    public int getBialka() {
        return bialka;
    }

    public void setBialka(int bialka) {
        this.bialka = bialka;
    }

    public int getWeglowodany() {
        return weglowodany;
    }

    public void setWeglowodany(int weglowodany) {
        this.weglowodany = weglowodany;
    }
}
