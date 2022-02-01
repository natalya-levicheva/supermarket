package com.college.supermarket.service;

import android.content.Context;
import android.graphics.Bitmap;

import com.college.supermarket.service.UserService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class BarcodeService {
    public static Bitmap userBarcode;
    public int widthMin = 400;
    public int heightMin = 170;
    public int widthMax = 500;
    public int heightMax = 270;

    public Bitmap getUserBarcode(Context context){
        createBarcode(context);
        return userBarcode;
    }

    private void createBarcode(Context context)
    {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        UserService userService = null;
        try {
            userService = new UserService(context);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(userService.getAuthUser().getCardCode(),
                    BarcodeFormat.CODE_128, 400, 170, null);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            userBarcode = barcodeEncoder.createBitmap(bitMatrix);
        } catch (
                WriterException e) {
            e.printStackTrace();
        }
    }
}
