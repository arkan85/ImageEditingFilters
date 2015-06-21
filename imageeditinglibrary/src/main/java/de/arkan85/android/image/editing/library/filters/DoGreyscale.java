package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Greyscales an image.
 */
public class DoGreyscale extends Filter {

    private Bitmap bitmapIn;

    public DoGreyscale(Bitmap bitmapIn) {
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
        // Define constant values
        final double constValueRed = 0.299;
        final double constValueGreen = 0.587;
        final double constValueBlue = 0.114;

        // Get the dimensions
        int alpha, red, green, blue;
        final int width = this.getBitmapIn().getWidth();
        final int height = this.getBitmapIn().getHeight();
        final int[] pixels = new int[width * height];
        // Store every pixel into an integer array
        getBitmapIn().getPixels(pixels, 0, width, 0, 0, width, height);

        for (int i = 0; i < pixels.length; i++) {

            alpha = Color.alpha(pixels[i]);
            red = Color.red(pixels[i]);
            green = Color.green(pixels[i]);
            blue = Color.blue(pixels[i]);

            // Apply constant values
            red = green = blue = (int) (constValueRed * red + constValueGreen
                    * green + constValueBlue * blue);
            pixels[i] = Color.argb(alpha, red, green, blue);
        }
        // Write in output Bitmap
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
}
