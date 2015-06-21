package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Rotates an image.
 */
public class RotateImage extends Filter {

    private Bitmap bitmapIn;

    private float degree;

    public RotateImage(Bitmap bitmapIn, float degree) {
        this.bitmapIn = bitmapIn;
        this.degree = degree;
    }

    /**
     * Execute filter.
     *
     * @return the bitmap
     */
    @Override
    public Bitmap executeFilter() {

        final long time = System.currentTimeMillis();
        final Matrix matrix = new Matrix();
        matrix.postRotate(this.getDegree());
        System.out.println("Finished @ " + (System.currentTimeMillis() - time) + "ms");

        return Bitmap.createBitmap(this.getBitmapIn(), 0, 0, this.getBitmapIn().getWidth(), this.getBitmapIn().getHeight(), matrix, true);
    }

    public Bitmap getBitmapIn() {
        return bitmapIn;
    }

    public void setBitmapIn(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
    }

    public float getDegree() {
        return degree;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }
}
