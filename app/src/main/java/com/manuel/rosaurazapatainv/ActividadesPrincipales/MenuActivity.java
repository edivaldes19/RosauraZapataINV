package com.manuel.rosaurazapatainv.ActividadesPrincipales;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.manuel.rosaurazapatainv.Adaptadores.AdapterItemTopic;
import com.manuel.rosaurazapatainv.Firebase.Colecciones;
import com.manuel.rosaurazapatainv.Firebase.FirebaseHelper;
import com.manuel.rosaurazapatainv.R;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    LinearLayout linearLayoutHome, linearLayoutMap, linearLayoutAboutUs, linearLayoutLogout;
    ImageView imageViewHome, imageViewMap, imageViewAboutUs, imageViewLogout;
    TextView textView, titulo, textViewHome, textViewMap, textViewAboutUs, textViewLogout;
    RecyclerView recyclerView_topic;
    ArrayList<Integer> imagenesList;
    boolean isPressed = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        textView = drawerLayout.findViewById(R.id.usuario_nav);
        textView.setText("Usuario: " + FirebaseHelper.usuario.getUsuario());
        titulo = findViewById(R.id.tituloDelMenu);
        titulo.setText("Menú principal");
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
        recyclerView_topic = findViewById(R.id.recyclerView_topic);
        imagenesList = new ArrayList<>();
        imagenesList.add(R.drawable.ic_pedagogico);
        imagenesList.add(R.drawable.ic_didactico);
        imagenesList.add(R.drawable.ic_consumo);
        imagenesList.add(R.drawable.ic_activo);
        llenar();
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
        closeDrawer(drawerLayout);
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

    public void llenar() {
        ArrayList<String> topic = new ArrayList<>(Arrays.asList(Colecciones.TECNICOPEDAGOGICO.toString(), Colecciones.MATERIALDIDACTICO.toString(), Colecciones.MATERIALDECONSUMO.toString(), Colecciones.ACTIVOFIJO.toString()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView_topic.setLayoutManager(gridLayoutManager);
        recyclerView_topic.setAdapter(new AdapterItemTopic(topic, imagenesList, MenuActivity.this));
    }

    @Override
    public void onBackPressed() {
        if (isPressed) {
            finishAffinity();
            System.exit(0);
        } else {
            Toast.makeText(getApplicationContext(), "Presione de nuevo para salir", Toast.LENGTH_SHORT).show();
            isPressed = true;
        }
        Runnable runnable = () -> isPressed = false;
        new Handler().postDelayed(runnable, 2000);
    }
}