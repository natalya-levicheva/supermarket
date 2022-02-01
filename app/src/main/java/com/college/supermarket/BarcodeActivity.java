package com.college.supermarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.college.supermarket.service.BarcodeService;

public class BarcodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        getSupportActionBar().hide();
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.purple));
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.screenBrightness = 1F;
        getWindow().setAttributes(layout);
        BarcodeService barcodeService = new BarcodeService();
        ImageView barcodeImage = findViewById(R.id.barcode);
        barcodeImage.setImageBitmap(barcodeService.getUserBarcode(this));
    }
}