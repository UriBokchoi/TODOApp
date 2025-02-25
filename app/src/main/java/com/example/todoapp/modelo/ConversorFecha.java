// ConversorFecha.java
package com.example.todoapp.modelo;

import androidx.room.TypeConverter;
import java.util.Date;

public class ConversorFecha {
    @TypeConverter
    public static Date deTimestampAFecha(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }
    @TypeConverter
    public static Long deFechaATimestamp(Date fecha) {
        return fecha == null ? null : fecha.getTime();
    }
}
