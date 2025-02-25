// Tarea.java
package com.example.todoapp.modelo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.util.Date;

@Entity(tableName = "tareas")
public class Tarea {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titulo;
    private String descripcion;
    // Valores: "pendiente", "en-progreso", "finalizada"
    private String estado;
    // Valores: "alta", "normal", "baja"
    private String prioridad;

    @TypeConverters(ConversorFecha.class)
    private Date fechaCreacion;

    public Tarea(String titulo, String descripcion, String estado, String prioridad, Date fechaCreacion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
