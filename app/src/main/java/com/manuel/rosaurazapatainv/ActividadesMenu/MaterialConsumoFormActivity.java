package com.manuel.rosaurazapatainv.ActividadesMenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.manuel.rosaurazapatainv.ActividadesPrincipales.InfoActivity;
import com.manuel.rosaurazapatainv.ActividadesPrincipales.MainActivity;
import com.manuel.rosaurazapatainv.ActividadesPrincipales.MapsActivity;
import com.manuel.rosaurazapatainv.ActividadesPrincipales.MenuActivity;
import com.manuel.rosaurazapatainv.Firebase.Colecciones;
import com.manuel.rosaurazapatainv.Firebase.FirebaseHelper;
import com.manuel.rosaurazapatainv.Firebase.StaticHelper;
import com.manuel.rosaurazapatainv.Interfaces.Estado;
import com.manuel.rosaurazapatainv.Modelos.MaterialConsumo;
import com.manuel.rosaurazapatainv.R;

import java.util.Objects;

public class MaterialConsumoFormActivity extends AppCompatActivity implements Estado {
    DrawerLayout drawerLayout;
    LinearLayout linearLayoutHome, linearLayoutMap, linearLayoutAboutUs, linearLayoutLogout;
    ImageView imageViewHome, imageViewMap, imageViewAboutUs, imageViewLogout, imageViewEdit;
    TextView textView, titulo, textViewHome, textViewMap, textViewAboutUs, textViewLogout, textViewAgregarMC, titulodelmenu;
    TextInputLayout textInputLayout_numero, textInputLayout_descripcion, textInputLayout_cantidad;
    MaterialCardView materialCardView_agregar, materialCardView_limpiar;
    MaterialConsumo consumo;

    @SuppressLint({"SetTextI18n", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_consumo_form);
        drawerLayout = findViewById(R.id.drawer_layout);
        textView = drawerLayout.findViewById(R.id.usuario_nav);
        textView.setText("Usuario: " + FirebaseHelper.usuario.getUsuario());
        titulo = findViewById(R.id.tituloDelMenu);
        titulo.setText("Nuevo");
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
        textInputLayout_numero = findViewById(R.id.textInputLayout_numero);
        textInputLayout_descripcion = findViewById(R.id.textInputLayout_descripcion);
        textInputLayout_cantidad = findViewById(R.id.textInputLayout_cantidad);
        materialCardView_agregar = findViewById(R.id.agregarMaterialConsumo);
        materialCardView_limpiar = findViewById(R.id.limpiarMaterialConsumo);
        textViewAgregarMC = findViewById(R.id.textViewAgregarMC);
        imageViewEdit = findViewById(R.id.imageViewCheckMC);
        titulodelmenu = findViewById(R.id.tituloDelMenu);
        getDatos();
        materialCardView_agregar.setOnClickListener(view -> {
            String numero = Objects.requireNonNull(textInputLayout_numero.getEditText()).getText().toString().trim();
            String descripcion = Objects.requireNonNull(textInputLayout_descripcion.getEditText()).getText().toString().trim();
            String cantidad = Objects.requireNonNull(textInputLayout_cantidad.getEditText()).getText().toString().trim();
            if (evaluarDatos(numero, textInputLayout_numero) && evaluarDatos(descripcion, textInputLayout_descripcion) && evaluarDatos(cantidad, textInputLayout_cantidad)) {
                String[] datos = {numero, descripcion, cantidad};
                if (getIntent().getBooleanExtra("dato", false)) {
                    new FirebaseHelper().editar(MaterialConsumoFormActivity.this, Colecciones.MATERIALDECONSUMO.toString(), consumo.getId(), StaticHelper.MATERIALDECONSUMOKEYS, datos, MaterialConsumoFormActivity.this);
                } else {
                    new FirebaseHelper().add(MaterialConsumoFormActivity.this, Colecciones.MATERIALDECONSUMO.toString(), StaticHelper.MATERIALDECONSUMOKEYS, datos, MaterialConsumoFormActivity.this);
                }
                Intent intent = new Intent(getApplicationContext(), MaterialConsumoListaActivity.class);
                startActivity(intent);
                finish();
            } else {
                Snackbar.make(view, "Datos inválidos", Snackbar.LENGTH_SHORT).show();
            }
        });
        materialCardView_limpiar.setOnClickListener(view -> clean());
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
        Toast.makeText(getApplicationContext(), "Ha cerrado sesión", Toast.LENGTH_LONG).show();
    }

    public boolean evaluarDatos(String valor, TextInputLayout textInputLayout) {
        if (!valor.equals("")) {
            return true;
        } else {
            textInputLayout.setError("Este campo es obligatorio");
            return false;
        }
    }

    @SuppressLint("SetTextI18n")
    public void getDatos() {
        if (getIntent().getBooleanExtra("dato", false)) {
            consumo = (MaterialConsumo) getIntent().getSerializableExtra("Material de Consumo");
            Objects.requireNonNull(textInputLayout_numero.getEditText()).setText(consumo.getNumero());
            Objects.requireNonNull(textInputLayout_descripcion.getEditText()).setText(consumo.getDescripcion());
            Objects.requireNonNull(textInputLayout_cantidad.getEditText()).setText(consumo.getCantidad());
            titulodelmenu.setText("Editar registro " + consumo.getNumero());
            textViewAgregarMC.setText("Editar");
            imageViewEdit.setImageResource(R.drawable.ic_edit);
        }
    }

    @Override
    public void estado(String mensaje) {
        Toast.makeText(MaterialConsumoFormActivity.this, mensaje, Toast.LENGTH_SHORT).show();
        if (mensaje.contains("Registro agregado")) {
            clean();
        }
    }

    public void clean() {
        Objects.requireNonNull(textInputLayout_numero.getEditText()).setText(null);
        Objects.requireNonNull(textInputLayout_descripcion.getEditText()).setText(null);
        Objects.requireNonNull(textInputLayout_cantidad.getEditText()).setText(null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MaterialConsumoListaActivity.class);
        startActivity(intent);
        finish();
    }
}