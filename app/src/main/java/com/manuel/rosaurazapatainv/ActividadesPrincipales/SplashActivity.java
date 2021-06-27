package com.manuel.rosaurazapatainv.ActividadesPrincipales;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.manuel.rosaurazapatainv.R;

public class SplashActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);
        TextView rosauraText = findViewById(R.id.rosauraTextView);
        ImageView logoRosaura = findViewById(R.id.logoImagenView);
        rosauraText.setAnimation(animation2);
        logoRosaura.setAnimation(animation1);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            Pair[] pairs = new Pair[2];
            pairs[0] = new Pair<View, String>(logoRosaura, "logoImageTrans");
            pairs[1] = new Pair<View, String>(rosauraText, "textTrans");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs);
                startActivity(intent, activityOptions.toBundle());
            } else {
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}