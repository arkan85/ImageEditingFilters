package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Create a binary effect on the image.
 */
public class BinaryImage extends Filter {

    private static final Double VALUE_RED = 0.3;
    private static final Double VALUE_GREEN = 0.59;
    private static final Double VALUE_BLUE = 0.11;

    private Bitmap bitmapIn;

    private int threshold;

    public BinaryImage(Bitmap bitmapIn, int threshold) {
        this.bitmapIn = bitmapIn;
        this.threshold = threshold;
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
        int pixel, red, green, blue;
        double grey;
        final int[] pixelsGreyscaled = new int[width * height];
        bitmapOut.getPixels(pixelsGreyscaled, 0, width, 0, 0, width, height);
        bitmapOut.recycle();

        for (int i = 0; i < pixelsGreyscaled.length; i++) {
            pixel = pixelsGreyscaled[i];
            red = Color.red(pixel);
            green = Color.green(pixel);
            blue = Color.blue(pixel);
            grey = ((red * VALUE_RED) + (green * VALUE_GREEN) + (blue * VALUE_BLUE));
            red = green = blue = thresholdOperation(this.getThreshold(), grey);
            pixelsGreyscaled[i] = Color.argb(Color.alpha(pixel), red, green, blue);
        }
        bitmapOut = Bitmap.createBitmap(width, height, this.getBitmapIn().getConfig());
        bitmapOut.setPixels(pixelsGreyscaled, 0, width, 0, 0, width, height);
        System.out.println("Finished @ " + (System.currentTimeMillis() - time) + "ms");

        return bitmapOut;
    }

    /**
     * Threshold operation.
     *
     * @param threshold the threshold
     * @param grey      the grey
     * @return the int
     */
    private int thresholdOperation(int threshold, double grey) {
        if (grey > threshold)
            return 255;
        else
            return 0;
    }

    public Bitmap getBitmapIn() {
        return bitmapIn;
    }

    public void setBitmapIn(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
