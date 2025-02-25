package com.example.todoapp.modelo;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "comentarios",
        foreignKeys = @ForeignKey(entity = Tarea.class,
                parentColumns = "id",
                childColumns = "tareaId",
                onDelete = CASCADE),
        indices = {@Index("tareaId")} // Agregamos el índice para tareaId
)
public class Comentario {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int tareaId;
    private String textoComentario;
    private long timestamp;

    public Comentario(int tareaId, String textoComentario, long timestamp) {
        this.tareaId = tareaId;
        this.textoComentario = textoComentario;
        this.timestamp = timestamp;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTareaId() { return tareaId; }
    public void setTareaId(int tareaId) { this.tareaId = tareaId; }
    public String getTextoComentario() { return textoComentario; }
    public void setTextoComentario(String textoComentario) { this.textoComentario = textoComentario; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
