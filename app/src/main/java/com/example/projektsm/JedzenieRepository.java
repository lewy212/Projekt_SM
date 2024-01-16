package com.example.projektsm;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class JedzenieRepository {
    private JedzenieDao jedzenieDao;
    private LiveData<List<Jedzenie>> jedzenie;

    JedzenieRepository(Application application) {
        JedzenieDatabase database = JedzenieDatabase.getDatabase(application);
        jedzenieDao = database.jedzenieDao();
        jedzenie = jedzenieDao.findAll();
    }

    LiveData<List<Jedzenie>> findAll() {
        return jedzenie;
    }

    void insert(Jedzenie jedzenie1) {
        JedzenieDatabase.databaseWriteExecutor.execute(() -> {
            jedzenieDao.insert(jedzenie1);
        });
    }

    void update(Jedzenie jedzenie) {
        JedzenieDatabase.databaseWriteExecutor.execute(() -> {
            jedzenieDao.update(jedzenie);
        });
    }

    void delete(Jedzenie jedzenie) {
        JedzenieDatabase.databaseWriteExecutor.execute(() -> {
            jedzenieDao.delete(jedzenie);
        });
    }
}