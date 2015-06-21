package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Decreases the color depth.
 */
public class DecreaseColorDepth extends Filter {

    private Bitmap bitmapIn;

    private int bitOffset;

    public DecreaseColorDepth(Bitmap bitmapIn, int bitOffset) {
        this.bitmapIn = bitmapIn;
        this.bitOffset = bitOffset;
    }

    /**
     * Execute filter.
     *
     * @return the bitmap
     */
    @Override
    public Bitmap executeFilter() {

        final long time = System.currentTimeMillis();
        if (this.getBitOffset() == 0) {
            this.setBitOffset(32);
        } else if (this.getBitOffset() == 1) {
            this.setBitOffset(64);
        } else if (this.getBitOffset() == 2) {
            this.setBitOffset(128);
        }

        final int width = this.getBitmapIn().getWidth();
        final int height = this.getBitmapIn().getHeight();
        final int[] pixels = new int[width * height];
        int red, green, blue;
        getBitmapIn().getPixels(pixels, 0, width, 0, 0, width, height);

        for (int i = 0; i < pixels.length; i++) {

            red = Color.red(pixels[i]);
            green = Color.green(pixels[i]);
            blue = Color.blue(pixels[i]);

            red = ((red + (this.getBitOffset() / 2))
                    - ((red + (this.getBitOffset() / 2)) % this
                    .getBitOffset()) - 1);
            if (red < 0)
                red = 0;

            green = ((green + (this.getBitOffset() / 2))
                    - ((green + (this.getBitOffset() / 2)) % this
                    .getBitOffset()) - 1);
            if (green < 0)
                green = 0;

            blue = ((blue + (this.getBitOffset() / 2))
                    - ((blue + (this.getBitOffset() / 2)) % this
                    .getBitOffset()) - 1);
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

    public int getBitOffset() {
        return bitOffset;
    }

    public void setBitOffset(int bitOffset) {
        this.bitOffset = bitOffset;
    }
}
