package com.manuel.rosaurazapatainv.ActividadesPrincipales;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.manuel.rosaurazapatainv.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    TextView textViewLlamar, textViewMensaje, textViewWhatsApp, textViewCorreo;
    String telDirector = "4521314122", titulo = "Recuperar Contraseña";
    String[] emailDirector = {"recio-manuel@outlook.com"};
    final int REQUEST_CODE_ASK = 0;

    @SuppressLint("IntentReset")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        solicitarPermisos();
        textViewLlamar = findViewById(R.id.textView_llamar);
        textViewMensaje = findViewById(R.id.textView_mensaje);
        textViewWhatsApp = findViewById(R.id.textView_whatsapp);
        textViewCorreo = findViewById(R.id.textView_email);
        textViewLlamar.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + telDirector));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        });
        textViewMensaje.setOnClickListener(view -> {
            Uri uri = Uri.parse("smsto:" + telDirector);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(intent);
        });
        textViewWhatsApp.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            String uri = "whatsapp://send?phone=52" + telDirector;
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        });
        textViewCorreo.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:"));
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, emailDirector);
            intent.putExtra(Intent.EXTRA_SUBJECT, titulo);
            startActivity(Intent.createChooser(intent, "Send Email"));
        });
    }

    private void solicitarPermisos() {
        int permisoLlamadas = ActivityCompat.checkSelfPermission(ForgotPasswordActivity.this, Manifest.permission.CALL_PHONE);
        int permisoMensajes = ActivityCompat.checkSelfPermission(ForgotPasswordActivity.this, Manifest.permission.SEND_SMS);
        if (permisoLlamadas != PackageManager.PERMISSION_GRANTED || permisoMensajes != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS}, REQUEST_CODE_ASK);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}