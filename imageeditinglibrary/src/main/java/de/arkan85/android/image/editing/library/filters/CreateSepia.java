package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Creates a sepia effect.
 */
public class CreateSepia extends Filter {

    private Bitmap bitmapIn;

    private int intensity;

    private final static int depth = 20;

    public CreateSepia(Bitmap bitmapIn, int intensity) {
        this.bitmapIn = bitmapIn;
        this.intensity = intensity;
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
        int red, green, blue, grey;
        final int[] pixels = new int[width * height];
        getBitmapIn().getPixels(pixels, 0, width, 0, 0, width, height);

        for (int i = 0; i < pixels.length; i++) {

            red = Color.red(pixels[i]);
            green = Color.green(pixels[i]);
            blue = Color.blue(pixels[i]);

            grey = (red + green + blue) / 3;
            red = green = blue = grey;
            red = red + (depth * 2);
            green += depth;

            if (red > 255)
                red = 255;
            if (green > 255)
                green = 255;
            if (blue > 255)
                blue = 255;

            blue -= intensity;

            if (blue > 255)
                blue = 255;
            if (blue < 0)
                blue = 0;

            pixels[i] = Color.argb(Color.alpha(pixels[i]), red, green, blue);
        }
        final Bitmap bitmapOut = Bitmap.createBitmap(width, height, this.getBitmapIn().getConfig());
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

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }
}
