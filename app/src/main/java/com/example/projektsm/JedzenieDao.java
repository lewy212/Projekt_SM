package com.example.projektsm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface JedzenieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Jedzenie jedzenie);

    @Update
    void update(Jedzenie jedzenie);

    @Delete
    void delete(Jedzenie jedzenie);

    @Query("DELETE FROM jedzenie")
    void deleteAll();

    @Query("SELECT * FROM jedzenie ORDER BY dataDodania")
    LiveData<List<Jedzenie>> findAll();

    @Query("SELECT * FROM jedzenie WHERE nazwa LIKE :nazwa")
    List<Jedzenie> findJedzenieWithNazwa(String nazwa);
}
