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

import com.manuel.rosaurazapatainv.ActividadesMenu.MaterialDidacticoFormActivity;
import com.manuel.rosaurazapatainv.Interfaces.Almacenamiento;
import com.manuel.rosaurazapatainv.Modelos.MaterialDidactico;
import com.manuel.rosaurazapatainv.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterItemMaterialDidactico extends RecyclerView.Adapter<AdapterItemMaterialDidactico.ViewHolderMaterialDidactico> {
    MaterialDidactico didactico;
    List<MaterialDidactico> didacticoList;
    List<MaterialDidactico> originalItems;
    Context context;
    public static Almacenamiento almacenamiento;

    public AdapterItemMaterialDidactico(List<MaterialDidactico> didacticoList, Context context, Almacenamiento almacenamiento) {
        this.didacticoList = didacticoList;
        this.context = context;
        AdapterItemMaterialDidactico.almacenamiento = almacenamiento;
        this.originalItems = new ArrayList<>();
        originalItems.addAll(didacticoList);
    }

    @NonNull
    @Override
    public ViewHolderMaterialDidactico onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_inventario, parent, false);
        return new ViewHolderMaterialDidactico(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderMaterialDidactico holder, int position) {
        didactico = didacticoList.get(position);
        String numero = didactico.getNumero();
        String descripcion = didactico.getDescripcion();
        String cantidad = didactico.getCantidad();
        String estado = didactico.getEstado();
        holder.textView_nombre.setText(numero + " | " + descripcion + " | " + cantidad + " | " + estado);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, MaterialDidacticoFormActivity.class);
            intent.putExtra("Material Didáctico", didacticoList.get(position));
            intent.putExtra("dato", true);
            context.startActivity(intent);
        });
        holder.itemView.setTooltipText("Deslice hacia la izquierda o derecha para eliminar");
    }

    @Override
    public int getItemCount() {
        return didacticoList.size();
    }

    public void filter(final String s) {
        if (s.length() == 0) {
            didacticoList.clear();
            didacticoList.addAll(originalItems);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                didacticoList.clear();
                List<MaterialDidactico> collect = originalItems.stream().filter(i -> i.getNumero().toLowerCase().contains(s) || i.getDescripcion().toLowerCase().contains(s) || i.getCantidad().toLowerCase().contains(s) || i.getEstado().toLowerCase().contains(s)).collect(Collectors.toList());
                didacticoList.addAll(collect);
            } else {
                didacticoList.clear();
                for (MaterialDidactico materialDidactico : originalItems) {
                    if (materialDidactico.getNumero().toLowerCase().contains(s) || materialDidactico.getDescripcion().toLowerCase().contains(s) || materialDidactico.getCantidad().toLowerCase().contains(s) || materialDidactico.getEstado().toLowerCase().contains(s)) {
                        didacticoList.add(materialDidactico);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolderMaterialDidactico extends RecyclerView.ViewHolder {
        TextView textView_nombre;
        public RelativeLayout viewF3, viewB3;

        public ViewHolderMaterialDidactico(View viewItem) {
            super(viewItem);
            textView_nombre = viewItem.findViewById(R.id.textNombre);
            viewF3 = viewItem.findViewById(R.id.rl);
            viewB3 = viewItem.findViewById(R.id.view_background);
        }
    }

    public void removeItem(int position) {
        didacticoList.remove(position);
        notifyItemRemoved(position);
    }
}