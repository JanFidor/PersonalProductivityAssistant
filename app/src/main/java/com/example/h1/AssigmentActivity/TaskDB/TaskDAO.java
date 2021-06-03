package com.example.h1.AssigmentActivity.TaskDB;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.h1.AssigmentActivity.TaskDB.Task;

import java.util.List;

// TODO change getV and getT to select Task
@Dao
public interface TaskDAO {
    @Query("INSERT INTO notes (contents, spent, total) VALUES ('New note', 0, 1)")
    void create();

    @Query("SELECT * FROM notes")
    List<Task> getAllNotes();

    @Query("UPDATE notes SET spent = spent + 1 WHERE id = :id")
    void add(int id);

    @Query("SELECT * FROM notes WHERE id = :id")
    Task getTask(int id);

    @Query("UPDATE notes SET spent = spent - 1 WHERE id = :id")
    void subtract(int id);

    @Query("INSERT INTO notes (contents, spent, total) VALUES (:contents, :spent, :total)")
    void make(String contents, int spent,  int total);

    @Query("DELETE FROM notes WHERE id = :id")
    void delete(int id);
}
