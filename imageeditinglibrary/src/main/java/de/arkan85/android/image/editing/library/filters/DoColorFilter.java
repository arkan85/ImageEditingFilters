package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Is a color filter apparently..
 */
public class DoColorFilter extends Filter {

    private Bitmap bitmapIn;

    private double red, green, blue;

    public DoColorFilter(Bitmap bitmapIn, double red, double green, double blue) {
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
        final int[] pixels = new int[width * height];
        getBitmapIn().getPixels(pixels, 0, width, 0, 0, width, height);

        for (int i = 0; i < pixels.length; i++) {

            alpha = Color.alpha(pixels[i]);
            red = (int) (Color.red(pixels[i]) * this.getRed());
            green = (int) (Color.green(pixels[i]) * this.getGreen());
            blue = (int) (Color.blue(pixels[i]) * this.getBlue());
            pixels[i] = Color.argb(alpha, red, green, blue);
        }
        Bitmap bitmapOut = Bitmap.createBitmap(width, height, this.getBitmapIn()
                .getConfig());
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
