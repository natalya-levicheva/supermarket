package com.college.supermarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.purple));
        ImageView imageCart = findViewById(R.id.image_cart);

        Animation animationBarcodeToMax = AnimationUtils.loadAnimation(this, R.anim.from_left_to_right);
        animationBarcodeToMax.setStartOffset(0);

        imageCart.startAnimation(animationBarcodeToMax);

        Thread thread = new Thread(){
            public void run(){
                try {
                    if (hasCameraPermission()) {
                        Thread.sleep(2100);
                        Intent intent = new Intent(MainActivity.this,
                                AuthActivity.class);
                        startActivity(intent);
                    } else {
                        requestPermission();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                CAMERA_PERMISSION,
                CAMERA_REQUEST_CODE
        );
    }
}