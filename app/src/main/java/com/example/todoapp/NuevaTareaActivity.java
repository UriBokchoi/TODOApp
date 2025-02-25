package com.example.todoapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todoapp.datos.BaseDatos;
import com.example.todoapp.modelo.Tarea;

import java.util.Date;
import java.util.concurrent.Executors;

public class NuevaTareaActivity extends AppCompatActivity {

    private EditText edtTitulo, edtDescripcion;
    private Spinner spinnerEstado, spinnerPrioridad;
    private Button btnGuardar, btnCancelar;
    private BaseDatos baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_tarea);

        baseDatos = BaseDatos.obtenerInstancia(this);

        edtTitulo = findViewById(R.id.edtTitulo);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        spinnerEstado = findViewById(R.id.spinnerEstado);
        spinnerPrioridad = findViewById(R.id.spinnerPrioridad);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);

        spinnerEstado.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.estado_array)));
        spinnerPrioridad.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.prioridad_array)));

        btnGuardar.setOnClickListener(v -> guardarTarea());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void guardarTarea() {
        String titulo = edtTitulo.getText().toString().trim();
        String descripcion = edtDescripcion.getText().toString().trim();
        String estado = spinnerEstado.getSelectedItem().toString();
        String prioridad = spinnerPrioridad.getSelectedItem().toString();
        Date fechaCreacion = new Date();

        if (!titulo.isEmpty()) {
            Tarea tarea = new Tarea(titulo, descripcion, estado, prioridad, fechaCreacion);
            Executors.newSingleThreadExecutor().execute(() -> {
                baseDatos.tareaDao().insertar(tarea);
                runOnUiThread(this::finish);
            });
        }
    }
}
