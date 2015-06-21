package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Does a gamma correction.
 */
public class DoGamma extends Filter {

    private static final int MAX_SIZE = 256;
    private static final double MAX_VALUE_DBL = 255.0;
    private static final int MAX_VALUE_INT = 255;
    private static final double REVERSE = 1.0;

    private Bitmap bitmapIn;

    private double red;

    private double green;

    private double blue;

    public DoGamma(Bitmap bitmapIn, double red, double green, double blue) {
        this.bitmapIn = bitmapIn;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Execute filter.
     *
     * @return the bitmap
     */
    @Override
    public Bitmap executeFilter() {

        final long time = System.currentTimeMillis();
        final int width = this.getBitmapIn().getWidth();
        final int height = this.getBitmapIn().getHeight();
        int alpha, red, green, blue;

        final int[] gammaRed = new int[MAX_SIZE];
        final int[] gammaGreen = new int[MAX_SIZE];
        final int[] gammaBlue = new int[MAX_SIZE];

        for (int i = 0; i < MAX_SIZE; i++) {
            gammaRed[i] = Math.min(
                    MAX_VALUE_INT,
                    (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE
                            / this.getRed())) + 0.5));
            gammaGreen[i] = Math.min(
                    MAX_VALUE_INT,
                    (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE
                            / this.getGreen())) + 0.5));
            gammaBlue[i] = Math.min(
                    MAX_VALUE_INT,
                    (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE
                            / this.getBlue())) + 0.5));
        }

        final int[] pixels = new int[width * height];
        getBitmapIn().getPixels(pixels, 0, width, 0, 0, width, height);

        for (int i = 0; i < pixels.length; i++) {

            alpha = Color.alpha(pixels[i]);
            red = gammaRed[Color.red(pixels[i])];
            green = gammaGreen[Color.green(pixels[i])];
            blue = gammaBlue[Color.blue(pixels[i])];

            pixels[i] = Color.argb(alpha, red, green, blue);
        }
        final Bitmap bitmapOut = Bitmap.createBitmap(width, height, this
                .getBitmapIn().getConfig());
        bitmapOut.setPixels(pixels, 0, width, 0, 0, width, height);
        System.out.println("Finished @ " + (System.currentTimeMillis() - time) + "ms");

        return bitmapOut;
    }

    public Bitmap getBitmapIn() {
        return bitmapIn;
    }

    public void setBitmapIn(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
    }

    public double getRed() {
        return red;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public double getGreen() {
        return green;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public double getBlue() {
        return blue;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }
}
