package com.example.todoapp.datos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.todoapp.modelo.Comentario;
import java.util.List;

@Dao
public interface ComentarioDao {

    @Insert
    long insertar(Comentario comentario);
//
    @Query("SELECT * FROM comentarios WHERE tareaId = :idTarea ORDER BY timestamp")
    List<Comentario> obtenerComentariosPorTarea(int idTarea);
}
