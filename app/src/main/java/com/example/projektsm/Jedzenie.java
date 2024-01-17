package com.example.projektsm;


import android.media.Image;
import android.widget.ImageView;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "jedzenie")
@TypeConverters(Jedzenie.DateConverter.class)
public class Jedzenie {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nazwa;
    private int kalorie;
    private int tluszcze;
    private int bialka;
    private int weglowodany;
    private Date dataDodania;
    private String sciezkaZdjecia;

    public Jedzenie(String nazwa, int tluszcze, int bialka, int weglowodany, int kalorie) {
        this.nazwa = nazwa;
        this.kalorie = kalorie;
        this.tluszcze = tluszcze;
        this.bialka = bialka;
        this.weglowodany = weglowodany;
        this.dataDodania = new Date();
        this.sciezkaZdjecia = "";
    }
    @Ignore
    public Jedzenie(String nazwa, int tluszcze, int bialka, int weglowodany) {
        this.nazwa = nazwa;
        this.tluszcze = tluszcze;
        this.bialka = bialka;
        this.weglowodany = weglowodany;
        this.kalorie = oblicz_kalorie_potrawy(tluszcze,bialka,weglowodany);
        this.dataDodania = new Date();
        this.sciezkaZdjecia = "";
    }

    public int oblicz_kalorie_potrawy(int tluszcze, int bialka, int weglowodany)
    {
        int kalorie_potrawy = tluszcze*9 + bialka*4 + weglowodany*4;
        return kalorie_potrawy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public Date getDataDodania() {
        return dataDodania;
    }

    public void setDataDodania(Date dataDodania) {
        this.dataDodania = dataDodania;
    }

    public String getSciezkaZdjecia() {
        return sciezkaZdjecia;
    }

    public void setSciezkaZdjecia(String sciezkaZdjecia) {
        this.sciezkaZdjecia = sciezkaZdjecia;
    }

    public static class DateConverter {
        @TypeConverter
        public static Date fromTimestamp(Long value) {
            return value == null ? null : new Date(value);
        }

        @TypeConverter
        public static Long dateToTimestamp(Date date) {
            return date == null ? null : date.getTime();
        }
    }
}