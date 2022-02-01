package com.college.supermarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.college.supermarket.service.BarcodeService;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().hide();
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.purple));
        ImageView barcodeImage = findViewById(R.id.barcode);
        BarcodeService barcodeService = new BarcodeService();
        barcodeImage.setImageBitmap(barcodeService.getUserBarcode(this));

    }

    public void onViewBarcode(View view){
        Intent intent;
        intent = new Intent(this, BarcodeActivity.class);
        startActivity(intent);
    }

    public void onFindPrice(View view){
        Intent intent;
        intent = new Intent(this, BarcodeScannerActivity.class);
        startActivity(intent);
    }
}