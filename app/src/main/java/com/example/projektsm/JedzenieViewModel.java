package com.example.projektsm;



import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class JedzenieViewModel extends AndroidViewModel {
    private JedzenieRepository jedzenieRepository;
    private LiveData<List<Jedzenie>> jedzenie;

    public JedzenieViewModel(@NonNull Application application) {
        super(application);
        jedzenieRepository = new JedzenieRepository(application);
        jedzenie = jedzenieRepository.findAll();
    }

    public LiveData<List<Jedzenie>> findAll() {
        return jedzenie;
    }

    public void insert(Jedzenie jedzenie) {
        jedzenieRepository.insert(jedzenie);
    }

    public void update(Jedzenie jedzenie) {
        jedzenieRepository.update(jedzenie);
    }

    public void delete(Jedzenie jedzenie) {
        jedzenieRepository.delete(jedzenie);
    }
}