package com.example.todoapp.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todoapp.R;
import com.example.todoapp.modelo.Tarea;
import java.util.List;

public class AdaptadorTareas extends RecyclerView.Adapter<AdaptadorTareas.TareaViewHolder> {

    public interface OnItemClickListener {
        void alHacerClic(Tarea tarea);
    }

    private List<Tarea> tareas;
    private OnItemClickListener oyente;

    public AdaptadorTareas(List<Tarea> tareas, OnItemClickListener oyente) {
        this.tareas = tareas;
        this.oyente = oyente;
    }

    public void establecerTareas(List<Tarea> tareas) {
        this.tareas = tareas;
        notifyDataSetChanged();
    }

    @Override
    public TareaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(TareaViewHolder holder, int position) {
        final Tarea tarea = tareas.get(position);
        holder.enlazar(tarea, oyente);
    }

    @Override
    public int getItemCount() {
        return tareas == null ? 0 : tareas.size();
    }

    public static class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtEstado, txtPrioridad;

        public TareaViewHolder(View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtEstado = itemView.findViewById(R.id.txtEstado);
            txtPrioridad = itemView.findViewById(R.id.txtPrioridad);
        }

        public void enlazar(final Tarea tarea, final OnItemClickListener oyente) {
            txtTitulo.setText(tarea.getTitulo());
            txtEstado.setText(tarea.getEstado());
            txtPrioridad.setText(tarea.getPrioridad());
            itemView.setOnClickListener(v -> oyente.alHacerClic(tarea));
        }
    }
}
