package com.example.todoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.todoapp.adaptadores.AdaptadorTareas;
import com.example.todoapp.datos.BaseDatos;
import com.example.todoapp.modelo.Tarea;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerTareas;
    private AdaptadorTareas adaptador;
    private BaseDatos baseDatos;
    private SharedPreferences preferencias;

    public static final String NOMBRE_PREFERENCIAS = "prefs_todo";
    public static final String CLAVE_MOSTRAR_FINALIZADAS = "mostrarFinalizadas";
    public static final String CLAVE_MOSTRAR_EN_PROGRESO = "mostrarEnProgreso";
    public static final String CLAVE_MOSTRAR_PENDIENTES = "mostrarPendientes";
    public static final String CLAVE_LISTA_TABLA = "lista_tabla";

    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences prefs = newBase.getSharedPreferences(NOMBRE_PREFERENCIAS, MODE_PRIVATE);
        String language = prefs.getString("idioma_seleccionado", Locale.getDefault().getLanguage());
        Locale newLocale = new Locale(language);
        Locale.setDefault(newLocale);
        Configuration config = new Configuration();
        config.setLocale(newLocale);
        Context context = newBase.createConfigurationContext(config);
        super.attachBaseContext(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        baseDatos = BaseDatos.obtenerInstancia(this);
        preferencias = getSharedPreferences(NOMBRE_PREFERENCIAS, MODE_PRIVATE);

        if (!preferencias.contains(CLAVE_MOSTRAR_FINALIZADAS)) {
            preferencias.edit()
                    .putBoolean(CLAVE_MOSTRAR_FINALIZADAS, false)
                    .putBoolean(CLAVE_MOSTRAR_EN_PROGRESO, true)
                    .putBoolean(CLAVE_MOSTRAR_PENDIENTES, true)
                    .putBoolean(CLAVE_LISTA_TABLA, false)
                    .apply();
        }

        recyclerTareas = findViewById(R.id.recyclerTasks);
        if (preferencias.getBoolean(CLAVE_LISTA_TABLA, false)) {
            recyclerTareas.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerTareas.setLayoutManager(new LinearLayoutManager(this));
        }
        adaptador = new AdaptadorTareas(null, tarea -> {
            Intent intent = new Intent(MainActivity.this, DetalleTareaActivity.class);
            intent.putExtra("ID_TAREA", tarea.getId());
            startActivity(intent);
        });
        recyclerTareas.setAdapter(adaptador);

        FloatingActionButton fabAgregar = findViewById(R.id.fabAddTask);
        fabAgregar.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, NuevaTareaActivity.class)));

        Button btnCambiarIdioma = findViewById(R.id.btnCambiarIdioma);
        btnCambiarIdioma.setOnClickListener(v -> showIdiomaPopup(v));
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarTareas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_nueva_tarea) {
            startActivity(new Intent(this, NuevaTareaActivity.class));
            return true;
        } else if (id == R.id.menu_mostrar_finalizadas) {
            boolean mostrarFinalizadas = !item.isChecked();
            item.setChecked(mostrarFinalizadas);
            preferencias.edit().putBoolean(CLAVE_MOSTRAR_FINALIZADAS, mostrarFinalizadas).apply();
            cargarTareas();
            return true;
        } else if (id == R.id.menu_mostrar_en_progreso) {
            boolean mostrarEnProgreso = !item.isChecked();
            item.setChecked(mostrarEnProgreso);
            preferencias.edit().putBoolean(CLAVE_MOSTRAR_EN_PROGRESO, mostrarEnProgreso).apply();
            cargarTareas();
            return true;
        } else if (id == R.id.menu_mostrar_pendientes) {
            boolean mostrarPendientes = !item.isChecked();
            item.setChecked(mostrarPendientes);
            preferencias.edit().putBoolean(CLAVE_MOSTRAR_PENDIENTES, mostrarPendientes).apply();
            cargarTareas();
            return true;
        } else if (id == R.id.menu_lista_tabla) {
            boolean listaTabla = !item.isChecked();
            item.setChecked(listaTabla);
            preferencias.edit().putBoolean(CLAVE_LISTA_TABLA, listaTabla).apply();
            if (listaTabla) {
                recyclerTareas.setLayoutManager(new GridLayoutManager(this, 2));
            } else {
                recyclerTareas.setLayoutManager(new LinearLayoutManager(this));
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cargarTareas() {
        boolean mostrarFinalizadas = preferencias.getBoolean(CLAVE_MOSTRAR_FINALIZADAS, false);
        boolean mostrarEnProgreso = preferencias.getBoolean(CLAVE_MOSTRAR_EN_PROGRESO, true);
        boolean mostrarPendientes = preferencias.getBoolean(CLAVE_MOSTRAR_PENDIENTES, true);
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Tarea> listaTareas = baseDatos.tareaDao().obtenerTareasFiltradas(
                    mostrarFinalizadas, mostrarEnProgreso, mostrarPendientes);
            runOnUiThread(() -> adaptador.establecerTareas(listaTareas));
        });
    }

    private void showIdiomaPopup(View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.getMenuInflater().inflate(R.menu.menu_idiomas, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.idioma_ingles) {
                cambiarIdioma("en");
                return true;
            } else if (itemId == R.id.idioma_espanol) {
                cambiarIdioma("es");
                return true;
            } else if (itemId == R.id.idioma_catalan) {
                cambiarIdioma("ca");
                return true;
            }
            return false;
        });
        popup.show();
    }

    private void cambiarIdioma(String codigoIdioma) {
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("idioma_seleccionado", codigoIdioma);
        editor.apply();
        Log.d("MainActivity", "Idioma cambiado a: " + codigoIdioma);
        recreate();
    }
}
