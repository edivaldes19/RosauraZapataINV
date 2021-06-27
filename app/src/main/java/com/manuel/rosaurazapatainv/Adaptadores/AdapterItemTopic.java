package com.manuel.rosaurazapatainv.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.manuel.rosaurazapatainv.ActividadesMenu.ActivoFijoListaActivity;
import com.manuel.rosaurazapatainv.ActividadesMenu.MaterialConsumoListaActivity;
import com.manuel.rosaurazapatainv.ActividadesMenu.MaterialDidacticoListaActivity;
import com.manuel.rosaurazapatainv.ActividadesMenu.TecnicoPedagogicoListaActivity;
import com.manuel.rosaurazapatainv.Firebase.Colecciones;
import com.manuel.rosaurazapatainv.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterItemTopic extends RecyclerView.Adapter<AdapterItemTopic.ViewHolderTopic> {
    List<String> registrosList;
    List<Integer> imagenesList;
    Context context;
    long mLastClickTime = 0;

    public AdapterItemTopic(ArrayList<String> productosArrayList, ArrayList<Integer> imagesArrayList, Context context) {
        this.registrosList = productosArrayList;
        this.imagenesList = imagesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderTopic onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_grid_layout, parent, false);
        return new ViewHolderTopic(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTopic holder, int position) {
        final int[] clic = {0};
        String categoria = registrosList.get(position);
        holder.titulo.setText(categoria);
        holder.icono.setImageResource(imagenesList.get(position));
        holder.icono.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide));
        holder.titulo.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        holder.itemView.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            ++clic[0];
            if (clic[0] == 1) {
                Intent intent;
                if (categoria.equals(Colecciones.ACTIVOFIJO.toString())) {
                    intent = new Intent(context, ActivoFijoListaActivity.class);
                } else if (categoria.equals(Colecciones.TECNICOPEDAGOGICO.toString())) {
                    intent = new Intent(context, TecnicoPedagogicoListaActivity.class);
                } else if (categoria.equals(Colecciones.MATERIALDIDACTICO.toString())) {
                    intent = new Intent(context, MaterialDidacticoListaActivity.class);
                } else {
                    intent = new Intent(context, MaterialConsumoListaActivity.class);
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return registrosList.size();
    }

    public static class ViewHolderTopic extends RecyclerView.ViewHolder {
        TextView titulo;
        ImageView icono;

        public ViewHolderTopic(View viewItem) {
            super(viewItem);
            titulo = viewItem.findViewById(R.id.titulo_grid);
            icono = viewItem.findViewById(R.id.imagen_grid);
        }
    }
}