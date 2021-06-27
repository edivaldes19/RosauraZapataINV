package com.manuel.rosaurazapatainv.ActividadesMenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.manuel.rosaurazapatainv.ActividadesPrincipales.InfoActivity;
import com.manuel.rosaurazapatainv.ActividadesPrincipales.MainActivity;
import com.manuel.rosaurazapatainv.ActividadesPrincipales.MapsActivity;
import com.manuel.rosaurazapatainv.ActividadesPrincipales.MenuActivity;
import com.manuel.rosaurazapatainv.Firebase.Colecciones;
import com.manuel.rosaurazapatainv.Firebase.FirebaseHelper;
import com.manuel.rosaurazapatainv.Firebase.StaticHelper;
import com.manuel.rosaurazapatainv.Interfaces.Estado;
import com.manuel.rosaurazapatainv.Modelos.ActivoFijo;
import com.manuel.rosaurazapatainv.R;

import java.util.Objects;

public class ActivoFijoFormActivity extends AppCompatActivity implements Estado, AdapterView.OnItemSelectedListener {
    DrawerLayout drawerLayout;
    LinearLayout linearLayoutHome, linearLayoutMap, linearLayoutAboutUs, linearLayoutLogout;
    ImageView imageViewHome, imageViewMap, imageViewAboutUs, imageViewLogout, imageViewEdit;
    TextView textView, titulo, textViewHome, textViewMap, textViewAboutUs, textViewLogout, textViewAgregarA, titulodelmenu;
    TextInputLayout textInputLayout_numero, textInputLayout_clave, textInputLayout_descripcion, textInputLayout_cantidad, textInputLayout_precio, textInputLayout_total;
    MaterialTextView materialTextView;
    MaterialCardView materialCardView_agregar, materialCardView_limpiar;
    ActivoFijo fijo;
    Spinner spinner;
    String estado;

    @SuppressLint({"SetTextI18n", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activo_fijo_form);
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
        textInputLayout_clave = findViewById(R.id.textInputLayout_clave);
        textInputLayout_descripcion = findViewById(R.id.textInputLayout_descripcion);
        textInputLayout_cantidad = findViewById(R.id.textInputLayout_cantidad);
        textInputLayout_precio = findViewById(R.id.textInputLayout_precio);
        textInputLayout_total = findViewById(R.id.textInputLayout_total);
        materialTextView = findViewById(R.id.materialTextEstadoA);
        materialCardView_agregar = findViewById(R.id.agregarActivoFijo);
        materialCardView_limpiar = findViewById(R.id.limpiarActivoFijo);
        textViewAgregarA = findViewById(R.id.textViewAgregarA);
        imageViewEdit = findViewById(R.id.imageViewCheckA);
        titulodelmenu = findViewById(R.id.tituloDelMenu);
        spinner = findViewById(R.id.spinnerEstadoA);
        ArrayAdapter<CharSequence> charSequenceArrayAdapter = ArrayAdapter.createFromResource(this, R.array.estados, android.R.layout.simple_spinner_item);
        charSequenceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(charSequenceArrayAdapter);
        spinner.setOnItemSelectedListener(this);
        getDatos();
        materialCardView_agregar.setOnClickListener(view -> {
            String numero = Objects.requireNonNull(textInputLayout_numero.getEditText()).getText().toString();
            String clave = Objects.requireNonNull(textInputLayout_clave.getEditText()).getText().toString();
            String descripcion = Objects.requireNonNull(textInputLayout_descripcion.getEditText()).getText().toString();
            String cantidad = Objects.requireNonNull(textInputLayout_cantidad.getEditText()).getText().toString();
            String precio = Objects.requireNonNull(textInputLayout_precio.getEditText()).getText().toString();
            String total = Objects.requireNonNull(textInputLayout_total.getEditText()).getText().toString();
            if (evaluarDatos(numero, textInputLayout_numero) && evaluarDatos(clave, textInputLayout_clave) && evaluarDatos(descripcion, textInputLayout_descripcion) && evaluarDatos(cantidad, textInputLayout_cantidad) && evaluarDatos(precio, textInputLayout_precio) && evaluarDatos(total, textInputLayout_total)) {
                String[] datos = {numero, clave, descripcion, cantidad, precio, total, estado};
                if (getIntent().getBooleanExtra("dato", false)) {
                    new FirebaseHelper().editar(ActivoFijoFormActivity.this, Colecciones.ACTIVOFIJO.toString(), fijo.getId(), StaticHelper.ACTIVOFIJOKEYS, datos, ActivoFijoFormActivity.this);
                } else {
                    new FirebaseHelper().add(ActivoFijoFormActivity.this, Colecciones.ACTIVOFIJO.toString(), StaticHelper.ACTIVOFIJOKEYS, datos, ActivoFijoFormActivity.this);
                }
                Intent intent = new Intent(getApplicationContext(), ActivoFijoListaActivity.class);
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
        Toast.makeText(getApplicationContext(), "Ha cerrado sesión", Toast.LENGTH_SHORT).show();
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
            fijo = (ActivoFijo) getIntent().getSerializableExtra("Activo Fijo");
            Objects.requireNonNull(textInputLayout_numero.getEditText()).setText(fijo.getNumero());
            Objects.requireNonNull(textInputLayout_clave.getEditText()).setText(fijo.getClave());
            Objects.requireNonNull(textInputLayout_descripcion.getEditText()).setText(fijo.getDescripcion());
            Objects.requireNonNull(textInputLayout_cantidad.getEditText()).setText(fijo.getCantidad());
            Objects.requireNonNull(textInputLayout_precio.getEditText()).setText(fijo.getPrecio());
            Objects.requireNonNull(textInputLayout_total.getEditText()).setText(fijo.getTotal());
            switch (fijo.getEstado()) {
                case "Bueno":
                    spinner.setSelection(0);
                    break;
                case "Regular":
                    spinner.setSelection(1);
                    break;
                case "Malo":
                    spinner.setSelection(2);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + fijo.getEstado());
            }
            materialTextView.setText("Estado: " + fijo.getEstado());
            titulodelmenu.setText("Editar registro " + textInputLayout_numero.getEditText().getText().toString());
            textViewAgregarA.setText("Editar");
            imageViewEdit.setImageResource(R.drawable.ic_edit);
        }
    }

    @Override
    public void estado(String mensaje) {
        Toast.makeText(ActivoFijoFormActivity.this, mensaje, Toast.LENGTH_SHORT).show();
        if (mensaje.contains("Registro agregado")) {
            clean();
        }
    }

    public void clean() {
        Objects.requireNonNull(textInputLayout_numero.getEditText()).setText("");
        Objects.requireNonNull(textInputLayout_clave.getEditText()).setText("");
        Objects.requireNonNull(textInputLayout_descripcion.getEditText()).setText("");
        Objects.requireNonNull(textInputLayout_cantidad.getEditText()).setText("");
        Objects.requireNonNull(textInputLayout_precio.getEditText()).setText("");
        Objects.requireNonNull(textInputLayout_total.getEditText()).setText("");
        spinner.setSelection(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, ActivoFijoListaActivity.class);
        startActivity(intent);
        finish();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        estado = adapterView.getItemAtPosition(i).toString().trim();
        materialTextView.setText("Estado: " + estado);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}