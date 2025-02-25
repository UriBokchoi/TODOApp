package com.example.todoapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todoapp.datos.BaseDatos;
import com.example.todoapp.modelo.Tarea;
import java.util.concurrent.Executors;

public class DetalleTareaActivity extends AppCompatActivity {

    private TextView txtTitulo, txtDescripcion, txtEstado, txtPrioridad;
    private Button btnEditar, btnEliminar, btnAgregarComentario;
    private int idTarea;
    private BaseDatos baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_tarea);

        baseDatos = BaseDatos.obtenerInstancia(this);

        // Inicializa
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtEstado = findViewById(R.id.txtEstado);
        txtPrioridad = findViewById(R.id.txtPrioridad);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnAgregarComentario = findViewById(R.id.btnAgregarComentario);

        // Recuperar el ID de la tarea
        idTarea = getIntent().getIntExtra("ID_TAREA", -1);
        if (idTarea != -1) {
            cargarTarea();
        }


        btnEditar.setOnClickListener(v -> {

        });

        btnEliminar.setOnClickListener(v -> {

        });

        btnAgregarComentario.setOnClickListener(v -> {

        });
    }

    private void cargarTarea() {
        Executors.newSingleThreadExecutor().execute(() -> {

            Tarea tarea = baseDatos.tareaDao().obtenerTareaPorId(idTarea);
            runOnUiThread(() -> {
                if (tarea != null) {
                    txtTitulo.setText(tarea.getTitulo());
                    txtDescripcion.setText(tarea.getDescripcion());
                    txtEstado.setText(tarea.getEstado());
                    txtPrioridad.setText(tarea.getPrioridad());
                }
            });
        });
    }
}
