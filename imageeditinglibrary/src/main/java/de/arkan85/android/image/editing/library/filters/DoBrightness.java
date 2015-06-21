package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Does a brightness adjustment.
 */
public class DoBrightness extends Filter {

    private static final Integer MAX = 255;

    private Bitmap bitmapIn;

    private int value;

    public DoBrightness(Bitmap bitmapIn, int value) {
        this.bitmapIn = bitmapIn;
        this.value = value;
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
        int red, green, blue, pixel;
        final int[] pixels = new int[width * height];
        getBitmapIn().getPixels(pixels, 0, width, 0, 0, width, height);

        for (int i = 0; i < pixels.length; i++) {
            pixel = pixels[i];
            red = Color.red(pixel);
            green = Color.green(pixel);
            blue = Color.blue(pixel);

            red += this.getValue();
            if (red > MAX)
                red = MAX;
            else if (red < 0)
                red = 0;

            green += this.getValue();
            if (green > MAX)
                green = MAX;
            else if (green < 0)
                green = 0;

            blue += this.getValue();
            if (blue > MAX)
                blue = MAX;
            else if (blue < 0)
                blue = 0;

            pixels[i] = Color.argb(Color.alpha(pixel), red, green, blue);
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
