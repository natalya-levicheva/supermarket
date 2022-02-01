package com.college.supermarket.barcodescaner;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.google.mlkit.vision.barcode.Barcode;

public class BarcodeGraphic extends GraphicOverlay.Graphic {

    private static final float STROKE_WIDTH = 4.0f;

    private static final int BOX_COLOR = Color.GREEN;

    private final Paint rectPaint;
    private final Barcode barcode;

    BarcodeGraphic(GraphicOverlay overlay, Barcode barcode) {
        super(overlay);

        this.barcode = barcode;

        rectPaint = new Paint();
        rectPaint.setColor(BOX_COLOR);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(STROKE_WIDTH);

    }

    @Override
    public void draw(Canvas canvas) {
        if (barcode == null) {
            throw new IllegalStateException();
        }

        RectF rect = new RectF(barcode.getBoundingBox());
        rect.left = translateX(rect.left);
        rect.top = translateY(rect.top);
        rect.right = translateX(rect.right);
        rect.bottom = translateY(rect.bottom);
        canvas.drawRect(rect, rectPaint);

    }
}