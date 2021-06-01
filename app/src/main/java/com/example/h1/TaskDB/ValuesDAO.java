package com.example.h1.TaskDB;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.h1.TaskDB.Value;

import java.util.List;

@Dao
public interface ValuesDAO {
    @Query("INSERT INTO `values` (name, value) VALUES ('New value',1)")
    void create();

    @Query("SELECT * FROM `values`")
    List<Value> getAllValues();

    @Query("SELECT value FROM `values` where name = :name")
    long getValue(String name);

    @Query("UPDATE `values` SET value = :value WHERE id = :id")
    void update(long value, int id);

    @Query("UPDATE `values` SET value = :value WHERE name = :name")
    void updateN(long value, String name);

    @Query("INSERT INTO `values` (name, value) VALUES (:name, :value)")
    void make(String name, long value);

    @Query("DELETE FROM `values` WHERE id = :id")
    void delete(int id);
}
