package com.college.supermarket.barcodescaner;

import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageProxy;

import com.google.mlkit.common.MlKitException;

import java.nio.ByteBuffer;

public interface VisionImageProcessor {

    void processBitmap(Bitmap bitmap, GraphicOverlay graphicOverlay);

    void processByteBuffer(
            ByteBuffer data, FrameMetadata frameMetadata, GraphicOverlay graphicOverlay)
            throws MlKitException;

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    void processImageProxy(ImageProxy image, GraphicOverlay graphicOverlay)
            throws MlKitException;

    void stop();
}
