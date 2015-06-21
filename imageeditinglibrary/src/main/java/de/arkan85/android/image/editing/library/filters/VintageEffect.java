package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Vector;

/**
 * Creates a vintage effect.
 */
public class VintageEffect extends Filter {

    private Bitmap bitmapIn;

    public VintageEffect(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
    }

    // For reference: http://www.youtube.com/watch?v=m6NyJHKWgZg

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
        final int[] pixels = new int[width * height];
        getBitmapIn().getPixels(pixels, 0, width, 0, 0, width, height);
        final Vector<float[]> pixelsHSV = new Vector<>();

        float[] hsv;
        for (int pixel : pixels) {
            hsv = new float[]{0.0f, 0.0f, 0.0f};
            Color.RGBToHSV(Color.red(pixel), Color.green(pixel), Color.blue(pixel), hsv);
            pixelsHSV.add(hsv);
        }

        increaseSaturation(pixelsHSV);
        // Inline contrast insert here

        // Adjust color channels
        // Overwriting with saturated pixels
        for (int i = 0; i < width * height; i++) {
            pixels[i] = Color.HSVToColor(pixelsHSV.get(i));
        }
        // Vector not needed anymore
        adjustChannels(pixels);
        // Insert vignette effect
        // Insert hue
        final int[] pixelsHue = new int[width * height];
        for (int i = 0; i < width * height; i++) {
            pixelsHue[i] = pixels[i];
        }
        adjustHueAndAlpha(pixels, pixelsHue);
        blendArrays(pixels, pixelsHue, 0.7);
        // Insert color
        final int[] fullColor = new int[width * height];
        for (int i = 0; i < fullColor.length; i++) {
            fullColor[i] = Color.argb(0xFF, 0xFF, 0x0, 0x99);
        }
        blendArrays(pixels, fullColor, 0.9);

        Bitmap bitmapOut = Bitmap.createBitmap(width, height, getBitmapIn().getConfig());
        bitmapOut.setPixels(pixels, 0, width, 0, 0, width, height);

        bitmapOut = vignette(bitmapOut);
        System.out.println("Finished @ " + (System.currentTimeMillis() - time) + "ms");

        return bitmapOut;
    }

    /**
     * Vignette.
     *
     * @param bitmapIn the bitmap in
     * @return the bitmap
     */
    private Bitmap vignette(Bitmap bitmapIn) {

        final Vignette mVignette = new Vignette(bitmapIn);

        return mVignette.executeFilter();
    }

    /**
     * Blend arrays.
     *
     * @param pixels    the pixels
     * @param pixelsHue the pixels hue
     * @param value     the value
     */
    private void blendArrays(int[] pixels, int[] pixelsHue, double value) {

        int r1, r2, r, g1, g2, g, b1, b2, b;

        for (int i = 0; i < pixels.length; i++) {
            r1 = Color.red(pixels[i]);
            r2 = Color.red(pixelsHue[i]);
            r = (int) (r1 * value + r2 * (1.0 - value));

            g1 = Color.green(pixels[i]);
            g2 = Color.green(pixelsHue[i]);
            g = (int) (g1 * value + g2 * (1.0 - value));

            b1 = Color.blue(pixels[i]);
            b2 = Color.blue(pixelsHue[i]);
            b = (int) (b1 * value + b2 * (1.0 - value));

            pixels[i] = Color.argb(0xFF, r, g, b);
        }
    }

    /**
     * Increase saturation.
     *
     * @param hsv the hsv
     */
    private void increaseSaturation(Vector<float[]> hsv) {
        for (float[] hsvPixel : hsv) {
            hsvPixel[1] *= 1.2;
        }
    }

    /**
     * Adjust channels.
     *
     * @param pixels the pixels
     */
    private void adjustChannels(int[] pixels) {
        int red, green, blue;
        for (int i = 0; i < pixels.length; i++) {
            red = Color.red(pixels[i]);
            red *= 0.9;
            if (red > 240)
                red = 240;

            green = Color.green(pixels[i]);
            if (green > 123)
                green *= 1.05;
            if (green > 255)
                green = 255;

            blue = Color.blue(pixels[i]);
            if (blue < 125) {
                blue *= 1.15;
            } else if (blue >= 125) {
                blue *= 0.85;
            }
            if (blue > 255)
                blue = 255;

            pixels[i] = Color.argb(Color.alpha(pixels[i]), red, green, blue);
        }
    }

    /**
     * Adjust hue and alpha.
     *
     * @param pixels    the pixels
     * @param pixelsHue the pixels hue
     */
    private void adjustHueAndAlpha(int[] pixels, int[] pixelsHue) {

        final Vector<float[]> pixelsHueHsv = new Vector<>();
        for (int pixel : pixels) {
            float[] hsv = {0.0f, 0.0f, 0.0f};
            Color.RGBToHSV(Color.red(pixel), Color.green(pixel), Color.blue(pixel), hsv);
            pixelsHueHsv.add(hsv);
        }

        float[] pixelHSV;
        for (int i = 0; i < pixelsHueHsv.size(); i++) {
            pixelHSV = pixelsHueHsv.get(i);
            pixelHSV[0] = 50.0f;
            pixelHSV[1] = 0.25f;
            pixelsHueHsv.set(i, pixelHSV);
        }

        for (int i = 0; i < pixelsHueHsv.size(); i++) {
            pixelsHue[i] = Color.HSVToColor(0xFF, pixelsHueHsv.get(i));
        }
    }

    public Bitmap getBitmapIn() {
        return bitmapIn;
    }

    public void setBitmapIn(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
    }
}