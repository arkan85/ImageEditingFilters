package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * A brownish filter.
 */
public class BrownishFilter extends Filter {

    private Bitmap bitmapIn;

    public BrownishFilter(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
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
        int alpha, red, green, blue, pixel;
        final int[] pixels = new int[width * height];
        getBitmapIn().getPixels(pixels, 0, width, 0, 0, width, height);
        float[] hsv = new float[3];
        float hue, saturation, value;
        for (int i = 0; i < pixels.length; i++) {

            pixel = pixels[i];
            alpha = Color.alpha(pixel);
            red = Color.red(pixel);
            red *= 1.25;
            if (red > 255)
                red = 255;

            green = Color.green(pixel);

            blue = Color.blue(pixel);
            blue *= 0.5;

            Color.RGBToHSV(red, green, blue, hsv);
            hue = hsv[0];
            saturation = hsv[1];
            value = hsv[2];

            hue *= 0.7f;
            saturation *= 0.9f;
            value *= 0.85f;

            hsv[0] = hue;
            hsv[1] = saturation;
            hsv[2] = value;

            pixel = Color.HSVToColor(alpha, hsv);
            pixels[i] = pixel;
        }
        final Bitmap bitmapOut = Bitmap.createBitmap(width, height,
                this.bitmapIn.getConfig());
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
}
