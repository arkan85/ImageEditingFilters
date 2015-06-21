package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Vector;

/**
 * RGB -> HSV.
 */
public class RGBtoHSV {

    private Bitmap bitmapIn;

    public RGBtoHSV(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
    }

    /**
     * Execute filter.
     *
     * @param mRGBtoHSV the m rgb to hsv
     * @return the vector
     */
    public Vector<float[]> executeFilter(RGBtoHSV mRGBtoHSV) {

        final int width = mRGBtoHSV.getBitmapIn().getWidth();
        final int height = mRGBtoHSV.getBitmapIn().getHeight();
        final int[] pixels = new int[width * height];
        mRGBtoHSV.getBitmapIn().getPixels(pixels, 0, width, 0, 0, width, height);
        Vector<float[]> hsvValues = new Vector<>();
        float[] hsv;
        for (int i = 0; i < pixels.length; i++) {
            hsv = new float[3];
            Color.colorToHSV(pixels[i], hsv);
            hsvValues.add(hsv);
        }

        return hsvValues;
    }

    public Bitmap getBitmapIn() {
        return bitmapIn;
    }

    public void setBitmapIn(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
    }
}
