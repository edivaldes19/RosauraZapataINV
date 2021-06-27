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
import com.manuel.rosaurazapatainv.Adaptadores.AdapterItemMaterialConsumo;
import com.manuel.rosaurazapatainv.CallBacks.MyItemTouchHelperCallbackMC;
import com.manuel.rosaurazapatainv.Firebase.Colecciones;
import com.manuel.rosaurazapatainv.Firebase.FirebaseHelper;
import com.manuel.rosaurazapatainv.Interfaces.Almacenamiento;
import com.manuel.rosaurazapatainv.Interfaces.CallBackItemTouch;
import com.manuel.rosaurazapatainv.Interfaces.Estado;
import com.manuel.rosaurazapatainv.Modelos.MaterialConsumo;
import com.manuel.rosaurazapatainv.R;

import java.util.ArrayList;

public class MaterialConsumoListaActivity extends AppCompatActivity implements Almacenamiento, Estado, androidx.appcompat.widget.SearchView.OnQueryTextListener, CallBackItemTouch {
    DrawerLayout drawerLayout;
    LinearLayout linearLayoutHome, linearLayoutMap, linearLayoutAboutUs, linearLayoutLogout;
    ImageView imageViewHome, imageViewMap, imageViewAboutUs, imageViewLogout;
    TextView textView, titulo, textViewHome, textViewMap, textViewAboutUs, textViewLogout;
    FloatingActionButton floatingActionButton;
    ArrayList<MaterialConsumo> arrayList;
    RecyclerView recyclerView;
    SearchView searchView;
    AdapterItemMaterialConsumo adapterItemMaterialConsumo;

    @Override
    protected void onResume() {
        super.onResume();
        new FirebaseHelper().leerMaterialConsumo(MaterialConsumoListaActivity.this, Colecciones.MATERIALDECONSUMO.toString(), this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_consumo_lista);
        drawerLayout = findViewById(R.id.drawer_layout);
        textView = drawerLayout.findViewById(R.id.usuario_nav);
        textView.setText("Usuario: " + FirebaseHelper.usuario.getUsuario());
        titulo = findViewById(R.id.tituloDelMenu);
        titulo.setText("Inventario Material de Consumo");
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
        recyclerView = findViewById(R.id.lista_material_consumo);
        searchView = findViewById(R.id.buscadorConsumo);
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
        Intent intent = new Intent(this, MaterialConsumoFormActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void arrayList(ArrayList<Object> data) {
        arrayList = new ArrayList<>();
        for (Object o : data) {
            MaterialConsumo materialConsumos = (MaterialConsumo) o;
            Log.e("Error", materialConsumos.getId() + materialConsumos.getDescripcion());
            arrayList.add(materialConsumos);
        }
        llenar(arrayList);
    }

    public void llenar(ArrayList<MaterialConsumo> materialConsumos) {
        adapterItemMaterialConsumo = new AdapterItemMaterialConsumo(materialConsumos, MaterialConsumoListaActivity.this, MaterialConsumoListaActivity.this);
        recyclerView.setAdapter(adapterItemMaterialConsumo);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallbackMC(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    void initListener() {
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void id(String id) {
        new FirebaseHelper().eliminar(MaterialConsumoListaActivity.this, Colecciones.MATERIALDECONSUMO.toString(), id, this);
    }

    @Override
    public void estado(String mensaje) {
        Toast.makeText(MaterialConsumoListaActivity.this, mensaje, Toast.LENGTH_SHORT).show();
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
        adapterItemMaterialConsumo.filter(newText);
        return false;
    }

    @Override
    public void itemTouchOnMode(int oldPosition, int newPosition) {
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {
        final MaterialConsumo deletedItem = arrayList.get(viewHolder.getAdapterPosition());
        adapterItemMaterialConsumo.removeItem(viewHolder.getAdapterPosition());
        AdapterItemMaterialConsumo.almacenamiento.id(deletedItem.getId());
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}