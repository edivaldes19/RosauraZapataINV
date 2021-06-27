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

import com.manuel.rosaurazapatainv.ActividadesMenu.MaterialConsumoFormActivity;
import com.manuel.rosaurazapatainv.Interfaces.Almacenamiento;
import com.manuel.rosaurazapatainv.Modelos.MaterialConsumo;
import com.manuel.rosaurazapatainv.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterItemMaterialConsumo extends RecyclerView.Adapter<AdapterItemMaterialConsumo.ViewHolderMaterialConsumo> {
    MaterialConsumo consumo;
    List<MaterialConsumo> consumoList;
    List<MaterialConsumo> originalItems;
    Context context;
    public static Almacenamiento almacenamiento;

    public AdapterItemMaterialConsumo(List<MaterialConsumo> consumoList, Context context, Almacenamiento almacenamiento) {
        this.consumoList = consumoList;
        this.context = context;
        AdapterItemMaterialConsumo.almacenamiento = almacenamiento;
        this.originalItems = new ArrayList<>();
        originalItems.addAll(consumoList);
    }

    @NonNull
    @Override
    public ViewHolderMaterialConsumo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_inventario, parent, false);
        return new ViewHolderMaterialConsumo(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderMaterialConsumo holder, int position) {
        consumo = consumoList.get(position);
        String numero = consumo.getNumero();
        String descripcion = consumo.getDescripcion();
        String cantidad = consumo.getCantidad();
        holder.textView_nombre.setText(numero + " | " + descripcion + " | " + cantidad);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, MaterialConsumoFormActivity.class);
            intent.putExtra("Material de Consumo", consumoList.get(position));
            intent.putExtra("dato", true);
            context.startActivity(intent);
        });
        holder.itemView.setTooltipText("Deslice hacia la izquierda o derecha para eliminar");
    }

    @Override
    public int getItemCount() {
        return consumoList.size();
    }

    public void filter(final String s) {
        if (s.length() == 0) {
            consumoList.clear();
            consumoList.addAll(originalItems);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                consumoList.clear();
                List<MaterialConsumo> collect = originalItems.stream().filter(i -> i.getNumero().toLowerCase().contains(s) || i.getDescripcion().toLowerCase().contains(s) || i.getCantidad().toLowerCase().contains(s)).collect(Collectors.toList());
                consumoList.addAll(collect);
            } else {
                consumoList.clear();
                for (MaterialConsumo materialConsumo : originalItems) {
                    if (materialConsumo.getNumero().toLowerCase().contains(s) || materialConsumo.getDescripcion().toLowerCase().contains(s) || materialConsumo.getCantidad().toLowerCase().contains(s)) {
                        consumoList.add(materialConsumo);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolderMaterialConsumo extends RecyclerView.ViewHolder {
        TextView textView_nombre;
        public RelativeLayout viewF2, viewB2;

        public ViewHolderMaterialConsumo(View viewItem) {
            super(viewItem);
            textView_nombre = viewItem.findViewById(R.id.textNombre);
            viewF2 = viewItem.findViewById(R.id.rl);
            viewB2 = viewItem.findViewById(R.id.view_background);
        }
    }

    public void removeItem(int position) {
        consumoList.remove(position);
        notifyItemRemoved(position);
    }
}