package com.example.h1.TaskDB;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.h1.TaskDB.Task;

import java.util.List;

@Dao
public interface TaskDAO {
    @Query("INSERT INTO notes (contents, spent, total) VALUES ('New note', 0, 1)")
    void create();

    @Query("SELECT * FROM notes")
    List<Task> getAllNotes();

    @Query("UPDATE notes SET contents = :contents, spent =:spent, total =:total WHERE id = :id")
    void save(String contents, int spent, int total, int id);

    @Query("UPDATE notes SET spent = spent + 1 WHERE id = :id")
    void add(int id);

    @Query("SELECT spent FROM notes WHERE id = :id")
    int getV(int id);

    @Query("SELECT total FROM notes WHERE id = :id")
    int getT(int id);

    @Query("UPDATE notes SET spent = spent - 1 WHERE id = :id")
    void substract(int id);

    @Query("INSERT INTO notes (contents, spent, total) VALUES (:contents, :spent, :total)")
    void make(String contents, int spent,  int total);

    @Query("DELETE FROM notes WHERE id = :id")
    void delete(int id);
}
