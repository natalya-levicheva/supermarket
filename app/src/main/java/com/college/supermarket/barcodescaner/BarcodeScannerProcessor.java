package com.college.supermarket.barcodescaner;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;

public class BarcodeScannerProcessor extends VisionProcessorBase<List<Barcode>> {

    private static final String TAG = "BarcodeProcessor";

    private static final String MANUAL_TESTING_LOG = "BarcodeProcessor_LOG";

    private final BarcodeScanner barcodeScanner;

    private ExchangeScannedData exchangeScannedData;

    public BarcodeScannerProcessor(Context context, ExchangeScannedData exchangeScannedData) {
        super(context);

        BarcodeScannerOptions options = new BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE, Barcode.FORMAT_CODE_128)
                .build();

        barcodeScanner = BarcodeScanning.getClient(options);

        this.exchangeScannedData = exchangeScannedData;
    }

    @Override
    public void stop() {
        super.stop();
        barcodeScanner.close();
    }

    @Override
    protected Task<List<Barcode>> detectInImage(InputImage image) {
        return barcodeScanner.process(image);
    }

    @Override
    protected void onSuccess(
            @NonNull List<Barcode> barcodes, @NonNull GraphicOverlay graphicOverlay) {
        for (int i = 0; i < barcodes.size(); ++i) {
            Barcode barcode = barcodes.get(i);
            graphicOverlay.add(new BarcodeGraphic(graphicOverlay, barcode));

            if (barcode != null && barcode.getRawValue() != null && !barcode.getRawValue().isEmpty()) {
                exchangeScannedData.sendScannedCode(barcode.getRawValue());
            }
        }
    }

    @Override
    protected void onFailure(@NonNull Exception e) {
        Log.e(TAG, "Barcode detection failed " + e);
    }
}