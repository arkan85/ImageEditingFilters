package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;

import de.arkan85.android.image.editing.library.models.ConvolutionMask;
import de.arkan85.android.image.editing.library.models.PascalsTriangle;

/**
 * Applies a gaussian blur.
 */
public class GaussianBlurForOutput extends Filter {

    private Bitmap bitmapIn;

    private ConvolutionMask convolutionMask;

    private int originalWidth;

    private int originalHeight;

    private int currentWidth;

    private int currentHeight;

    private int usedMaskSize;

    public GaussianBlurForOutput(Bitmap bitmapIn, int originalWidth, int originalHeight, int currentWidth, int currentHeight, int usedMaskSize) {
        this.bitmapIn = bitmapIn;
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
        this.currentWidth = currentWidth;
        this.currentHeight = currentHeight;
        this.usedMaskSize = usedMaskSize;
        final double[][] convolutionMask = PascalsTriangle.generateConvolutionMask(usedMaskSize, originalWidth, originalHeight, currentWidth, currentHeight);
        this.convolutionMask = new ConvolutionMask(convolutionMask.length);
        this.convolutionMask.applyConvolutionSettingsBig(convolutionMask, convolutionMask.length);
        this.convolutionMask.Factor = PascalsTriangle.coefficientsForNormalization(convolutionMask);
        this.convolutionMask.Offset = 0;
    }

    /**
     * Execute filter.
     *
     * @return the bitmap
     */
    @Override
    public Bitmap executeFilter() {
        return ConvolutionMask.calculateConvolutionMxM(this.getBitmapIn(), this.getConvolutionMask());
    }

    public Bitmap getBitmapIn() {
        return bitmapIn;
    }

    public void setBitmapIn(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
    }

    public ConvolutionMask getConvolutionMask() {
        return convolutionMask;
    }

    public void setConvolutionMask(ConvolutionMask convolutionMask) {
        this.convolutionMask = convolutionMask;
    }

    public int getOriginalWidth() {
        return originalWidth;
    }

    public void setOriginalWidth(int originalWidth) {
        this.originalWidth = originalWidth;
    }

    public int getOriginalHeight() {
        return originalHeight;
    }

    public void setOriginalHeight(int originalHeight) {
        this.originalHeight = originalHeight;
    }

    public int getCurrentWidth() {
        return currentWidth;
    }

    public void setCurrentWidth(int currentWidth) {
        this.currentWidth = currentWidth;
    }

    public int getCurrentHeight() {
        return currentHeight;
    }

    public void setCurrentHeight(int currentHeight) {
        this.currentHeight = currentHeight;
    }

    public int getUsedMaskSize() {
        return usedMaskSize;
    }

    public void setUsedMaskSize(int usedMaskSize) {
        this.usedMaskSize = usedMaskSize;
    }
}
