package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Applies a box blur.
 */
public class BoxBlur extends Filter {

    private Bitmap bitmapIn;

    private int range;

    public BoxBlur(Bitmap bitmapIn, int range) {
        this.bitmapIn = bitmapIn;
        this.range = range;
    }

    /**
     * Execute filter.
     *
     * @return the bitmap
     */
    @SuppressWarnings("deprecation")
    @Override
    public Bitmap executeFilter() {

        final long time = System.currentTimeMillis();
        final int width = this.getBitmapIn().getWidth();
        final int height = this.getBitmapIn().getHeight();
        final Bitmap bitmapOut = Bitmap.createBitmap(width, height, this.getBitmapIn().getConfig());
        final Canvas canvas = new Canvas(bitmapOut);
        final int[] pixels = new int[width * height];
        getBitmapIn().getPixels(pixels, 0, width, 0, 0, width, height);

        boxBlurHorizontal(pixels, width, height, range / 2);
        boxBlurVertical(pixels, width, height, range / 2);

        canvas.drawBitmap(pixels, 0, width, 0.0f, 0.0f, width, height, true, null);
        System.out.println("Finished @ " + (System.currentTimeMillis() - time) + "ms");

        return bitmapOut;
    }

    /**
     * Box blur horizontal.
     *
     * @param pixels    the pixels
     * @param width     the width
     * @param height    the height
     * @param halfRange the half range
     */
    private void boxBlurHorizontal(int[] pixels, int width, int height, int halfRange) {

        int index = 0;
        int[] newColors = new int[width];
        int color;
        long red, green, blue;
        for (int y = 0; y < height; y++) {
            int hits = 0;
            red = green = blue = 0;

            for (int x = -halfRange; x < width; x++) {
                int oldPixel = x - halfRange - 1;
                if (oldPixel >= 0) {
                    color = pixels[index + oldPixel];
                    if (color != 0) {
                        red -= Color.red(color);
                        green -= Color.green(color);
                        blue -= Color.blue(color);
                    }
                    hits--;
                }

                int newPixel = x + halfRange;
                if (newPixel < width) {
                    color = pixels[index + newPixel];
                    if (color != 0) {
                        red += Color.red(color);
                        green += Color.green(color);
                        blue += Color.blue(color);
                    }
                    hits++;
                }

                if (x >= 0) {
                    newColors[x] = Color.argb(0xFF, (int) (red / hits), (int) (green / hits),
                            (int) (blue / hits));
                }
            }
            for (int x = 0; x < width; x++) {
                pixels[index + x] = newColors[x];
            }
            index += width;
        }
    }

    /**
     * Box blur vertical.
     *
     * @param pixels    the pixels
     * @param width     the width
     * @param height    the height
     * @param halfRange the half range
     */
    private void boxBlurVertical(int[] pixels, int width, int height, int halfRange) {

        int[] newColors = new int[height];
        int oldPixelOffset = -(halfRange + 1) * width;
        int newPixelOffset = (halfRange) * width;
        long red, green, blue;
        int index;
        int color;

        for (int x = 0; x < width; x++) {

            int hits = 0;

            red = green = blue = 0;
            index = -halfRange * width + x;
            for (int y = -halfRange; y < height; y++) {

                int oldPixel = y - halfRange - 1;
                if (oldPixel >= 0) {

                    color = pixels[index + oldPixelOffset];
                    if (color != 0) {
                        red -= Color.red(color);
                        green -= Color.green(color);
                        blue -= Color.blue(color);
                    }
                    hits--;
                }
                int newPixel = y + halfRange;
                if (newPixel < height) {
                    color = pixels[index + newPixelOffset];
                    if (color != 0) {
                        red += Color.red(color);
                        green += Color.green(color);
                        blue += Color.blue(color);
                    }
                    hits++;
                }
                if (y >= 0) {
                    newColors[y] = Color.argb(0xFF, (int) (red / hits), (int) (green / hits),
                            (int) (blue / hits));
                }
                index += width;
            }
            for (int y = 0; y < height; y++) {
                pixels[y * width + x] = newColors[y];
            }
        }
    }

    public Bitmap getBitmapIn() {
        return bitmapIn;
    }

    public void setBitmapIn(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
