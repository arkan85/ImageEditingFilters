package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Creates contrast b/w.
 */
public class CreateContrastBlackWhite extends Filter {

    private Bitmap bitmapIn;

    private double value;

    public CreateContrastBlackWhite(Bitmap bitmapIn, double value) {
        this.bitmapIn = bitmapIn;
        this.value = value;
    }

    /**
     * Execute filter.
     *
     * @return the bidtmap
     */
    @Override
    public Bitmap executeFilter() {

        final long time = System.currentTimeMillis();
        final int width = this.getBitmapIn().getWidth();
        final int height = this.getBitmapIn().getHeight();
        int alpha, red, green, blue;
        final double contrast = Math.pow((100 + this.getValue()) / 100, 2);
        final int[] pixels = new int[width * height];
        getBitmapIn().getPixels(pixels, 0, width, 0, 0, width, height);

        for (int i = 0; i < pixels.length; i++) {

            alpha = Color.alpha(pixels[i]);

            red = Color.red(pixels[i]);
            red = (int) (((((red / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
            if (red > 255)
                red = 255;
            if (red < 0)
                red = 0;

            green = Color.red(pixels[i]);
            green = (int) (((((green / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
            if (green > 255)
                green = 255;
            if (green < 0)
                green = 0;

            blue = Color.red(pixels[i]);
            blue = (int) (((((blue / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
            if (blue > 255)
                blue = 255;
            if (blue < 0)
                blue = 0;

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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
