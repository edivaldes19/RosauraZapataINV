package com.manuel.rosaurazapatainv.Adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.manuel.rosaurazapatainv.ActividadesMenu.TecnicoPedagogicoFormActivity;
import com.manuel.rosaurazapatainv.Interfaces.Almacenamiento;
import com.manuel.rosaurazapatainv.Modelos.TecnicoPedagogico;
import com.manuel.rosaurazapatainv.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterItemTecnicoPedagogico extends RecyclerView.Adapter<AdapterItemTecnicoPedagogico.ViewHolderTecnicoPedagogico> {
    TecnicoPedagogico pedagogico;
    List<TecnicoPedagogico> pedagogicoList;
    List<TecnicoPedagogico> originalItems;
    Context context;
    public static Almacenamiento almacenamiento;

    public AdapterItemTecnicoPedagogico(List<TecnicoPedagogico> pedagogicoList, Context context, Almacenamiento almacenamiento) {
        this.pedagogicoList = pedagogicoList;
        this.context = context;
        AdapterItemTecnicoPedagogico.almacenamiento = almacenamiento;
        this.originalItems = new ArrayList<>();
        originalItems.addAll(pedagogicoList);
    }

    @NonNull
    @Override
    public ViewHolderTecnicoPedagogico onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_inventario, parent, false);
        return new ViewHolderTecnicoPedagogico(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolderTecnicoPedagogico holder, final int position) {
        pedagogico = pedagogicoList.get(position);
        String numero = pedagogico.getNumero();
        String descripcion = pedagogico.getDescripcion();
        String cantidad = pedagogico.getCantidad();
        String estado = pedagogico.getEstado();
        holder.textView_nombre.setText(numero + " | " + descripcion + " | " + cantidad + " | " + estado);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, TecnicoPedagogicoFormActivity.class);
            intent.putExtra("Técnico Pedagógico", pedagogicoList.get(position));
            intent.putExtra("dato", true);
            context.startActivity(intent);
        });
        holder.itemView.setTooltipText("Deslice hacia la izquierda o derecha para eliminar");
    }

    @Override
    public int getItemCount() {
        return pedagogicoList.size();
    }

    public void filter(final String s) {
        if (s.length() == 0) {
            pedagogicoList.clear();
            pedagogicoList.addAll(originalItems);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                pedagogicoList.clear();
                List<TecnicoPedagogico> collect = originalItems.stream().filter(i -> i.getNumero().toLowerCase().contains(s) || i.getDescripcion().toLowerCase().contains(s) || i.getCantidad().toLowerCase().contains(s) || i.getEstado().toLowerCase().contains(s)).collect(Collectors.toList());
                pedagogicoList.addAll(collect);
            } else {
                pedagogicoList.clear();
                for (TecnicoPedagogico tecnicoPedagogico : originalItems) {
                    if (tecnicoPedagogico.getNumero().toLowerCase().contains(s) || tecnicoPedagogico.getDescripcion().toLowerCase().contains(s) || tecnicoPedagogico.getCantidad().toLowerCase().contains(s) || tecnicoPedagogico.getEstado().toLowerCase().contains(s)) {
                        pedagogicoList.add(tecnicoPedagogico);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolderTecnicoPedagogico extends RecyclerView.ViewHolder {
        TextView textView_nombre;
        public RelativeLayout viewF, viewB;

        public ViewHolderTecnicoPedagogico(View viewItem) {
            super(viewItem);
            textView_nombre = viewItem.findViewById(R.id.textNombre);
            viewF = viewItem.findViewById(R.id.rl);
            viewB = viewItem.findViewById(R.id.view_background);
        }

    }

    public void removeItem(int position) {
        pedagogicoList.remove(position);
        notifyItemRemoved(position);
    }
}