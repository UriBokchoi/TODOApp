package com.example.todoapp.datos;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;
import com.example.todoapp.modelo.Tarea;
import com.example.todoapp.modelo.Comentario;
import com.example.todoapp.modelo.ConversorFecha;

@Database(entities = {Tarea.class, Comentario.class}, version = 1, exportSchema = false)
@TypeConverters({ConversorFecha.class})
public abstract class BaseDatos extends RoomDatabase {
    private static BaseDatos instancia;

    public abstract TareaDao tareaDao();
    public abstract ComentarioDao comentarioDao();

    public static synchronized BaseDatos obtenerInstancia(Context contexto) {
        if (instancia == null) {
            instancia = Room.databaseBuilder(contexto.getApplicationContext(),
                            BaseDatos.class, "bd_todo_app")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instancia;
    }
}
