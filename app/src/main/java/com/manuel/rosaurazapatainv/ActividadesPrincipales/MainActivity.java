package com.manuel.rosaurazapatainv.ActividadesPrincipales;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.manuel.rosaurazapatainv.Firebase.FirebaseHelper;
import com.manuel.rosaurazapatainv.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText textInputEditText_usuario;
    TextInputEditText textInputEditText_contrasena;
    TextView textViewOlvidasteContra;
    MaterialCardView materialCardView;
    boolean aBoolean1 = false;
    boolean aBoolean2 = false;
    FirebaseHelper firebaseHelper = new FirebaseHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textInputEditText_usuario = findViewById(R.id.usuarioTextField);
        textInputEditText_contrasena = findViewById(R.id.contrasenaTextField);
        textViewOlvidasteContra = findViewById(R.id.olvidasteContra);
        materialCardView = findViewById(R.id.inicioSesion);
        materialCardView.setOnClickListener(this);
        materialCardView.setEnabled(false);
        textInputEditText_usuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    aBoolean1 = false;
                    materialCardView.setEnabled(false);
                } else {
                    aBoolean1 = true;
                    materialCardView.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    aBoolean1 = false;
                    materialCardView.setEnabled(false);
                } else {
                    aBoolean1 = true;
                    materialCardView.setEnabled(true);
                }
            }
        });
        textInputEditText_contrasena.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    aBoolean2 = false;
                    materialCardView.setEnabled(false);
                } else {
                    aBoolean2 = true;
                    materialCardView.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    aBoolean2 = false;
                    materialCardView.setEnabled(false);
                } else {
                    aBoolean2 = true;
                    materialCardView.setEnabled(true);
                }
            }
        });
        textViewOlvidasteContra.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
            finish();
        });
        if (!isOnline(this)) {
            Toast.makeText(getApplicationContext(), "Error de red, verifique su conexión", Toast.LENGTH_LONG).show();
            finishAffinity();
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.inicioSesion) {
            if (aBoolean1 && aBoolean2) {
                ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "Procesando...", "Por favor, espere un momento", true);
                dialog.show();
                firebaseHelper.iniciarSesion(MainActivity.this, Objects.requireNonNull(textInputEditText_usuario.getText()).toString().trim(), Objects.requireNonNull(textInputEditText_contrasena.getText()).toString().trim(), dialog);
            } else {
                materialCardView.setEnabled(false);
                Toast.makeText(this, "Aún hay campos vacíos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}