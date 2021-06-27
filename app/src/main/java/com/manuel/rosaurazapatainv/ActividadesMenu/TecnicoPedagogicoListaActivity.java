package com.manuel.rosaurazapatainv.ActividadesMenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.manuel.rosaurazapatainv.ActividadesPrincipales.InfoActivity;
import com.manuel.rosaurazapatainv.ActividadesPrincipales.MainActivity;
import com.manuel.rosaurazapatainv.ActividadesPrincipales.MapsActivity;
import com.manuel.rosaurazapatainv.ActividadesPrincipales.MenuActivity;
import com.manuel.rosaurazapatainv.Adaptadores.AdapterItemTecnicoPedagogico;
import com.manuel.rosaurazapatainv.CallBacks.MyItemTouchHelperCallbackTP;
import com.manuel.rosaurazapatainv.Firebase.Colecciones;
import com.manuel.rosaurazapatainv.Firebase.FirebaseHelper;
import com.manuel.rosaurazapatainv.Interfaces.Almacenamiento;
import com.manuel.rosaurazapatainv.Interfaces.CallBackItemTouch;
import com.manuel.rosaurazapatainv.Interfaces.Estado;
import com.manuel.rosaurazapatainv.Modelos.TecnicoPedagogico;
import com.manuel.rosaurazapatainv.R;

import java.util.ArrayList;

public class TecnicoPedagogicoListaActivity extends AppCompatActivity implements Almacenamiento, Estado, SearchView.OnQueryTextListener, CallBackItemTouch {
    DrawerLayout drawerLayout;
    LinearLayout linearLayoutHome, linearLayoutMap, linearLayoutAboutUs, linearLayoutLogout;
    ImageView imageViewHome, imageViewMap, imageViewAboutUs, imageViewLogout;
    TextView textView, titulo, textViewHome, textViewMap, textViewAboutUs, textViewLogout;
    FloatingActionButton floatingActionButton;
    ArrayList<TecnicoPedagogico> arrayList;
    RecyclerView recyclerView;
    SearchView searchView;
    AdapterItemTecnicoPedagogico adapterItemTecnicoPedagogico;

    @Override
    protected void onResume() {
        super.onResume();
        new FirebaseHelper().leerTecnicoPedagogico(TecnicoPedagogicoListaActivity.this, Colecciones.TECNICOPEDAGOGICO.toString(), this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tecnico_pedagogico_lista);
        drawerLayout = findViewById(R.id.drawer_layout);
        textView = drawerLayout.findViewById(R.id.usuario_nav);
        textView.setText("Usuario: " + FirebaseHelper.usuario.getUsuario());
        titulo = findViewById(R.id.tituloDelMenu);
        titulo.setText("Inventario Técnico Pedagógico");
        linearLayoutHome = findViewById(R.id.linearLayoutHome);
        linearLayoutMap = findViewById(R.id.linearLayoutMap);
        linearLayoutAboutUs = findViewById(R.id.linearLayoutAboutUs);
        linearLayoutLogout = findViewById(R.id.linearLayoutLogout);
        imageViewHome = findViewById(R.id.imageViewHome);
        imageViewMap = findViewById(R.id.imageViewMap);
        imageViewAboutUs = findViewById(R.id.imageViewAboutUs);
        imageViewLogout = findViewById(R.id.imageViewLogout);
        textViewHome = findViewById(R.id.textViewHome);
        textViewMap = findViewById(R.id.textViewMap);
        textViewAboutUs = findViewById(R.id.textViewAboutUs);
        textViewLogout = findViewById(R.id.textViewLogout);
        recyclerView = findViewById(R.id.lista_tecnico_pedagogico);
        searchView = findViewById(R.id.buscadorPedagogico);
        floatingActionButton = findViewById(R.id.fab_mas);
        initListener();
        floatingActionButton.setOnClickListener(view -> abrirActividad());
    }

    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view) {
        linearLayoutHome.setBackgroundColor(Color.parseColor("#DDDDDD"));
        imageViewHome.setColorFilter(textView.getContext().getResources().getColor(R.color.blue));
        textViewHome.setTextColor(Color.parseColor("#B5101E"));
        redirectActivity(this, MenuActivity.class);
    }

    public void ClickDashboard(View view) {
        linearLayoutMap.setBackgroundColor(Color.parseColor("#DDDDDD"));
        imageViewMap.setColorFilter(textView.getContext().getResources().getColor(R.color.blue));
        textViewMap.setTextColor(Color.parseColor("#B5101E"));
        redirectActivity(this, MapsActivity.class);
    }

    public void ClickAboutUs(View view) {
        linearLayoutAboutUs.setBackgroundColor(Color.parseColor("#DDDDDD"));
        imageViewAboutUs.setColorFilter(textView.getContext().getResources().getColor(R.color.blue));
        textViewAboutUs.setTextColor(Color.parseColor("#B5101E"));
        redirectActivity(this, InfoActivity.class);
    }

    public void ClickLogout(View view) {
        linearLayoutLogout.setBackgroundColor(Color.parseColor("#DDDDDD"));
        imageViewLogout.setColorFilter(textView.getContext().getResources().getColor(R.color.blue));
        textViewLogout.setTextColor(Color.parseColor("#B5101E"));
        logout(this);
    }

    public void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Cerrar sesión");
        builder.setMessage("¿Está seguro(a) que desea cerrar sesión?");
        builder.setIcon(R.drawable.ic_logout_rojo);
        builder.setPositiveButton("Sí", (dialogInterface, i) -> {
            FirebaseHelper.usuario = null;
            cerrarSesion();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            linearLayoutLogout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            imageViewLogout.setColorFilter(textView.getContext().getResources().getColor(R.color.black));
            textViewLogout.setTextColor(Color.parseColor("#000000"));
        });
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    public void cerrarSesion() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(getApplicationContext(), "Ha cerrado sesión", Toast.LENGTH_LONG).show();
    }

    public void abrirActividad() {
        Intent intent = new Intent(this, TecnicoPedagogicoFormActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void arrayList(ArrayList<Object> data) {
        arrayList = new ArrayList<>();
        for (Object o : data) {
            TecnicoPedagogico tecnicoPedagogico = (TecnicoPedagogico) o;
            Log.e("Error", tecnicoPedagogico.getId() + tecnicoPedagogico.getDescripcion());
            arrayList.add(tecnicoPedagogico);
        }
        llenar(arrayList);
    }

    public void llenar(ArrayList<TecnicoPedagogico> tecnicoPedagogico) {
        adapterItemTecnicoPedagogico = new AdapterItemTecnicoPedagogico(tecnicoPedagogico, TecnicoPedagogicoListaActivity.this, TecnicoPedagogicoListaActivity.this);
        recyclerView.setAdapter(adapterItemTecnicoPedagogico);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallbackTP(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initListener() {
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void id(String id) {
        new FirebaseHelper().eliminar(TecnicoPedagogicoListaActivity.this, Colecciones.TECNICOPEDAGOGICO.toString(), id, this);
    }

    @Override
    public void estado(String mensaje) {
        Toast.makeText(TecnicoPedagogicoListaActivity.this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapterItemTecnicoPedagogico.filter(newText);
        return false;
    }

    @Override
    public void itemTouchOnMode(int oldPosition, int newPosition) {
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {
        final TecnicoPedagogico deletedItem = arrayList.get(viewHolder.getAdapterPosition());
        adapterItemTecnicoPedagogico.removeItem(viewHolder.getAdapterPosition());
        AdapterItemTecnicoPedagogico.almacenamiento.id(deletedItem.getId());
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}