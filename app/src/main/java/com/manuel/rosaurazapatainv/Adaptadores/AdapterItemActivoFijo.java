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

import com.manuel.rosaurazapatainv.ActividadesMenu.ActivoFijoFormActivity;
import com.manuel.rosaurazapatainv.Interfaces.Almacenamiento;
import com.manuel.rosaurazapatainv.Modelos.ActivoFijo;
import com.manuel.rosaurazapatainv.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterItemActivoFijo extends RecyclerView.Adapter<AdapterItemActivoFijo.ViewHolderActivoFijo> {
    ActivoFijo fijo;
    List<ActivoFijo> fijoList;
    List<ActivoFijo> originalItems;
    Context context;
    public static Almacenamiento almacenamiento;

    public AdapterItemActivoFijo(List<ActivoFijo> fijoList, Context context, Almacenamiento almacenamiento) {
        this.fijoList = fijoList;
        this.context = context;
        AdapterItemActivoFijo.almacenamiento = almacenamiento;
        this.originalItems = new ArrayList<>();
        originalItems.addAll(fijoList);
    }

    @NonNull
    @Override
    public ViewHolderActivoFijo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_inventario, parent, false);
        return new ViewHolderActivoFijo(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderActivoFijo holder, int position) {
        fijo = fijoList.get(position);
        String numero = fijo.getNumero();
        String clave = fijo.getClave();
        String descripcion = fijo.getDescripcion();
        String cantidad = fijo.getCantidad();
        String precio = fijo.getPrecio();
        String total = fijo.getTotal();
        String estado = fijo.getEstado();
        holder.textView_nombre.setText(numero + " | " + clave + " | " + descripcion + " | " + cantidad + " | " + precio + " | " + total + " | " + estado);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ActivoFijoFormActivity.class);
            intent.putExtra("Activo Fijo", fijoList.get(position));
            intent.putExtra("dato", true);
            context.startActivity(intent);
        });
        holder.itemView.setTooltipText("Deslice hacia la izquierda o derecha para eliminar");
    }

    @Override
    public int getItemCount() {
        return fijoList.size();
    }

    public void filter(final String s) {
        if (s.length() == 0) {
            fijoList.clear();
            fijoList.addAll(originalItems);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fijoList.clear();
                List<ActivoFijo> collect = originalItems.stream().filter(i -> i.getNumero().toLowerCase().contains(s) || i.getClave().toLowerCase().contains(s) || i.getDescripcion().toLowerCase().contains(s) || i.getCantidad().toLowerCase().contains(s) || i.getPrecio().toLowerCase().contains(s) || i.getTotal().toLowerCase().contains(s) || i.getEstado().toLowerCase().contains(s)).collect(Collectors.toList());
                fijoList.addAll(collect);
            } else {
                fijoList.clear();
                for (ActivoFijo activoFijo : originalItems) {
                    if (activoFijo.getNumero().toLowerCase().contains(s) || activoFijo.getClave().toLowerCase().contains(s) || activoFijo.getDescripcion().toLowerCase().contains(s) || activoFijo.getCantidad().toLowerCase().contains(s) || activoFijo.getPrecio().toLowerCase().contains(s) || activoFijo.getTotal().toLowerCase().contains(s) || activoFijo.getEstado().toLowerCase().contains(s)) {
                        fijoList.add(activoFijo);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolderActivoFijo extends RecyclerView.ViewHolder {
        TextView textView_nombre;
        public RelativeLayout viewF1, viewB1;

        public ViewHolderActivoFijo(View viewItem) {
            super(viewItem);
            textView_nombre = viewItem.findViewById(R.id.textNombre);
            viewF1 = viewItem.findViewById(R.id.rl);
            viewB1 = viewItem.findViewById(R.id.view_background);
        }
    }

    public void removeItem(int position) {
        fijoList.remove(position);
        notifyItemRemoved(position);
    }
}