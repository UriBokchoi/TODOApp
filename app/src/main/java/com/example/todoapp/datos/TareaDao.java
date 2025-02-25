// TareaDao.java
package com.example.todoapp.datos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Query;
import com.example.todoapp.modelo.Tarea;
import java.util.List;

@Dao
public interface TareaDao {

    @Insert
    long insertar(Tarea tarea);

    @Update
    void actualizar(Tarea tarea);

    @Delete
    void eliminar(Tarea tarea);

    @Query("SELECT * FROM tareas WHERE id = :idTarea")
    Tarea obtenerTareaPorId(int idTarea);

    @Query("SELECT * FROM tareas " +
            "WHERE (:mostrarFinalizadas = 1 OR estado != 'finalizada') " +
            "AND (:mostrarEnProgreso = 1 OR estado != 'en-progreso') " +
            "AND (:mostrarPendientes = 1 OR estado != 'pendiente') " +
            "ORDER BY CASE " +
            "           WHEN prioridad = 'alta' THEN 1 " +
            "           WHEN prioridad = 'normal' THEN 2 " +
            "           WHEN prioridad = 'baja' THEN 3 " +
            "         END, fechaCreacion")
    List<Tarea> obtenerTareasFiltradas(boolean mostrarFinalizadas, boolean mostrarEnProgreso, boolean mostrarPendientes);

}
