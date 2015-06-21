package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Boosts the color type.
 */
public class BoostColorType extends Filter {

    private static final int MAX = 255;

    private Bitmap bitmapIn;

    private int type;

    private float percent;

    private static final int RED = 1;

    private static final int GREEN = 2;

    private static final int BLUE = 3;

    public BoostColorType(Bitmap bitmapIn, int type, float percent) {
        this.bitmapIn = bitmapIn;
        this.type = type;
        this.percent = percent;
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
            red = Color.red(pixels[i]);
            green = Color.green(pixels[i]);
            blue = Color.blue(pixels[i]);

            if (this.getType() == RED) {
                red = (int) (red * (1 + this.getPercent()));
                if (red > MAX)
                    red = MAX;
            }

            if (this.getType() == GREEN) {
                green = (int) (green * (1 + this.getPercent()));
                if (green > MAX)
                    green = MAX;
            }

            if (this.getType() == BLUE) {
                blue = (int) (blue * (1 + this.getPercent()));
                if (blue > MAX)
                    blue = MAX;
            }
            pixels[i] = Color.argb(alpha, red, green, blue);
        }
        final Bitmap bitmapOut = Bitmap.createBitmap(width, height, this.getBitmapIn()
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
}
