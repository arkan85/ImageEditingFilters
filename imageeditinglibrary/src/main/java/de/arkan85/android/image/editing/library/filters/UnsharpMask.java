package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Unsharp mask.
 */
public class UnsharpMask extends Filter {

    private Bitmap bitmapIn;

    private int boxWidth, boxHeight;

    public UnsharpMask(Bitmap bitmapIn, int boxWidth, int boxHeight) {
        this.bitmapIn = bitmapIn;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
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
        final Bitmap bitmapOut = Bitmap.createBitmap(width, height, this.getBitmapIn()
                .getConfig());
        final int[] pixels = new int[width * height];
        getBitmapIn().getPixels(pixels, 0, width, 0, 0, width, height);
        bitmapOut.setPixels(pixels, 0, width, 0, 0, width, height);

        final int left = boxWidth / 2 + 1;
        final int top = boxHeight / 2 + 1;
        final int right = width - left;
        final int bottom = height - top;

        // TODO: Make amount and threshold adjustable
        final float unsharpMaskAmount = 0.5f;
        final int unsharpMaskThreshold = 3;

        final int[][] sourcePixels = loadPixelsFromImage(this.getBitmapIn());
        final int[][] blurredPixels = new int[width][height];

        inlineApplyBlur(bitmapOut, sourcePixels, blurredPixels, left, top, right, bottom, boxWidth, boxHeight);
        applyUnsharpMask(bitmapOut, sourcePixels, blurredPixels, left, top, right, bottom, unsharpMaskAmount, unsharpMaskThreshold);

        System.out.println("Finished @ " + (System.currentTimeMillis() - time) + "ms");

        return bitmapOut;
    }

    /**
     * Load pixels from image.
     *
     * @param bitmapIn the bitmap in
     * @return the int[][]
     */
    private int[][] loadPixelsFromImage(Bitmap bitmapIn) {

        final int width = bitmapIn.getWidth();
        final int height = bitmapIn.getHeight();
        final int[][] pixels = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x][y] = bitmapIn.getPixel(x, y);
            }
        }

        return pixels;
    }

    /**
     * Inline apply blur.
     *
     * @param bitmapIn      the bitmap in
     * @param sourcePixels  the source pixels
     * @param blurredPixels the blurred pixels
     * @param left          the left
     * @param top           the top
     * @param right         the right
     * @param bottom        the bottom
     * @param filtX         the filt x
     * @param filtY         the filt y
     */
    private void inlineApplyBlur(Bitmap bitmapIn, int[][] sourcePixels, int[][] blurredPixels,
                                 int left, int top, int right, int bottom, int filtX, int filtY) {

        for (int x = left; x < right; x++) {
            for (int y = top; y < bottom; y++) {
                blurredPixels[x][y] = blurPixels(sourcePixels, (x - filtX / 2), (y - filtY / 2),
                        (x + filtX / 2), (y + filtY / 2));
                bitmapIn.setPixel(x, y, blurredPixels[x][y]);
            }
        }
    }

    /**
     * Blur pixels.
     *
     * @param sourcePixels the source pixels
     * @param left         the left
     * @param top          the top
     * @param right        the right
     * @param bottom       the bottom
     * @return the int
     */
    private int blurPixels(int[][] sourcePixels, int left, int top, int right, int bottom) {

        int red, green, blue;
        red = green = blue = 0;
        int boxSize = (right - left) * (bottom - top);
        int pixel;

        for (int x = left; x < right; x++) {
            for (int y = top; y < bottom; y++) {
                pixel = sourcePixels[x][y];
                red += Color.red(pixel);
                green += Color.green(pixel);
                blue += Color.blue(pixel);
            }
        }

        red /= boxSize;
        green /= boxSize;
        blue /= boxSize;

        return Color.argb(0xFF, red, green, blue);
    }

    /**
     * Apply unsharp mask.
     *
     * @param bitmapIn      the bitmap in
     * @param sourcePixels  the source pixels
     * @param blurredPixels the blurred pixels
     * @param left          the left
     * @param top           the top
     * @param right         the right
     * @param bottom        the bottom
     * @param amount        the amount
     * @param threshold     the threshold
     */
    private void applyUnsharpMask(Bitmap bitmapIn, int[][] sourcePixels, int[][] blurredPixels,
                                  int left, int top, int right, int bottom, float amount, int threshold) {

        int oRed, oGreen, oBlue;
        int bRed, bGreen, bBlue;
        int unsharpMaskPixel;
        int oPixel;
        int bPixel;

        for (int x = left; x < right; x++) {
            for (int y = top; y < bottom; y++) {

                oPixel = sourcePixels[x][y];
                bPixel = blurredPixels[x][y];

                oRed = Color.red(oPixel);
                oGreen = Color.green(oPixel);
                oBlue = Color.blue(oPixel);
                bRed = Color.red(bPixel);
                bGreen = Color.green(bPixel);
                bBlue = Color.blue(bPixel);

                if (Math.abs(oRed - bRed) >= threshold) {

                    oRed = (int) (amount * (oRed - bRed) + oRed);
                    oRed = oRed > 255 ? 255 : oRed < 0 ? 0 : oRed;
                }

                if (Math.abs(oGreen - bGreen) >= threshold) {

                    oGreen = (int) (amount * (oGreen - bGreen) + oGreen);
                    oGreen = oGreen > 255 ? 255 : oGreen < 0 ? 0 : oGreen;
                }

                if (Math.abs(oBlue - bBlue) >= threshold) {

                    oBlue = (int) (amount * (oBlue - bBlue) + oBlue);
                    oBlue = oBlue > 255 ? 255 : oBlue < 0 ? 0 : oBlue;
                }

                unsharpMaskPixel = Color.argb(0xFF, oRed, oGreen, oBlue);
                bitmapIn.setPixel(x, y, unsharpMaskPixel);
            }
        }
    }

    public Bitmap getBitmapIn() {
        return bitmapIn;
    }

    public void setBitmapIn(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
    }

    public int getBoxWidth() {
        return boxWidth;
    }

    public void setBoxWidth(int boxWidth) {
        this.boxWidth = boxWidth;
    }

    public int getBoxHeight() {
        return boxHeight;
    }

    public void setBoxHeight(int boxHeight) {
        this.boxHeight = boxHeight;
    }
}
