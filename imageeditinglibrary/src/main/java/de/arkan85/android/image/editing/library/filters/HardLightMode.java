package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * The hard light filter.
 */
public class HardLightMode extends Filter {

    private Bitmap bitmapIn;

    public HardLightMode(Bitmap bitmapIn) {
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
        final DoGreyscale mDoGreyscale = new DoGreyscale(this.getBitmapIn());
        Bitmap bitmapOut = mDoGreyscale.executeFilter();

        final int width = this.getBitmapIn().getWidth();
        final int height = this.getBitmapIn().getHeight();
        final int[] pixels = new int[width * height];
        bitmapOut.getPixels(pixels, 0, width, 0, 0, width, height);
        bitmapOut.recycle();
        double grey;
        int alpha, red, green, blue;

        for (int i = 0; i < pixels.length; i++) {

            alpha = Color.alpha(pixels[i]);
            red = Color.red(pixels[i]);
            green = Color.green(pixels[i]);
            blue = Color.blue(pixels[i]);
            grey = ((red * 0.3f) + (green * 0.59f) + (blue * 0.11f));

            red = green = blue = (int) hardLightLayer(grey, grey);
            pixels[i] = Color.argb(alpha, red, green, blue);
        }
        bitmapOut = Bitmap.createBitmap(width, height, this.getBitmapIn()
                .getConfig());
        bitmapOut.setPixels(pixels, 0, width, 0, 0, width, height);
        System.out.println("Finished @ " + (System.currentTimeMillis() - time) + "ms");

        return bitmapOut;
    }

    /**
     * Hard light layer.
     *
     * @param maskVal the mask val
     * @param imgVal  the img val
     * @return the double
     */
    private double hardLightLayer(double maskVal, double imgVal) {
        if (maskVal > 128)
            return 255 - (((255 - (2 * (maskVal - 128))) * (255 - imgVal)) / 256);
        else
            return (2 * maskVal * imgVal) / 256;
    }

    public Bitmap getBitmapIn() {
        return bitmapIn;
    }

    public void setBitmapIn(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
    }
}
