package com.college.supermarket.barcodescaner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GraphicOverlay extends View {
    private final Object lock = new Object();
    private final List<Graphic> graphics = new ArrayList<>();
    private final Matrix transformationMatrix = new Matrix();

    private int imageWidth;
    private int imageHeight;
    private float scaleFactor = 1.0f;
    private float postScaleWidthOffset;
    private float postScaleHeightOffset;
    private boolean isImageFlipped;
    private boolean needUpdateTransformation = true;

    public abstract static class Graphic {
        private GraphicOverlay overlay;

        public Graphic(GraphicOverlay overlay) {
            this.overlay = overlay;
        }


        public abstract void draw(Canvas canvas);


        public float scale(float imagePixel) {
            return imagePixel * overlay.scaleFactor;
        }


        public Context getApplicationContext() {
            return overlay.getContext().getApplicationContext();
        }

        public boolean isImageFlipped() {
            return overlay.isImageFlipped;
        }


        public float translateX(float x) {
            if (overlay.isImageFlipped) {
                return overlay.getWidth() - (scale(x) - overlay.postScaleWidthOffset);
            } else {
                return scale(x) - overlay.postScaleWidthOffset;
            }
        }


        public float translateY(float y) {
            return scale(y) - overlay.postScaleHeightOffset;
        }

        public Matrix getTransformationMatrix() {
            return overlay.transformationMatrix;
        }

        public void postInvalidate() {
            overlay.postInvalidate();
        }
    }

    public GraphicOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnLayoutChangeListener(
                (view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) ->
                        needUpdateTransformation = true);
    }


    public void clear() {
        synchronized (lock) {
            graphics.clear();
        }
        postInvalidate();
    }


    public void add(Graphic graphic) {
        synchronized (lock) {
            graphics.add(graphic);
        }
    }


    public void remove(Graphic graphic) {
        synchronized (lock) {
            graphics.remove(graphic);
        }
        postInvalidate();
    }


    public void setImageSourceInfo(int imageWidth, int imageHeight, boolean isFlipped) {
        if (imageWidth < 0) throw new IllegalStateException("image width must be positive");
        if (imageHeight < 0) throw new IllegalStateException("image height must be positive");
        synchronized (lock) {
            this.imageWidth = imageWidth;
            this.imageHeight = imageHeight;
            this.isImageFlipped = isFlipped;
            needUpdateTransformation = true;
        }
        postInvalidate();
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    private void updateTransformationIfNeeded() {
        if (!needUpdateTransformation || imageWidth <= 0 || imageHeight <= 0) {
            return;
        }
        float viewAspectRatio = (float) getWidth() / getHeight();
        float imageAspectRatio = (float) imageWidth / imageHeight;
        postScaleWidthOffset = 0;
        postScaleHeightOffset = 0;
        if (viewAspectRatio > imageAspectRatio) {
            // The image needs to be vertically cropped to be displayed in this view.
            scaleFactor = (float) getWidth() / imageWidth;
            postScaleHeightOffset = ((float) getWidth() / imageAspectRatio - getHeight()) / 2;
        } else {
            // The image needs to be horizontally cropped to be displayed in this view.
            scaleFactor = (float) getHeight() / imageHeight;
            postScaleWidthOffset = ((float) getHeight() * imageAspectRatio - getWidth()) / 2;
        }

        transformationMatrix.reset();
        transformationMatrix.setScale(scaleFactor, scaleFactor);
        transformationMatrix.postTranslate(-postScaleWidthOffset, -postScaleHeightOffset);

        if (isImageFlipped) {
            transformationMatrix.postScale(-1f, 1f, getWidth() / 2f, getHeight() / 2f);
        }

        needUpdateTransformation = false;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        synchronized (lock) {
            updateTransformationIfNeeded();

            for (Graphic graphic : graphics) {
                graphic.draw(canvas);
            }
        }
    }
}
