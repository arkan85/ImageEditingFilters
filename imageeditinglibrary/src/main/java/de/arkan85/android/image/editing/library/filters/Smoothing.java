package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;

import de.arkan85.android.image.editing.library.models.ConvolutionMask;

/**
 * Smoothes an image.
 */
public class Smoothing extends Filter {

    private Bitmap bitmapIn;

    private int value;

    private ConvolutionMask convolutionMask;

    public Smoothing(Bitmap bitmapIn, int value) {
        this.bitmapIn = bitmapIn;
        this.value = value;
        this.convolutionMask = new ConvolutionMask(value);
    }

    /**
     * Execute filter.
     *
     * @return the bitmap
     */
    @Override
    public Bitmap executeFilter() {

        this.getConvolutionMask().setAll(1);
        this.getConvolutionMask().Factor = this.getConvolutionMask().mask.length;
        this.getConvolutionMask().Offset = 0;

        return ConvolutionMask.calculateConvolutionMxM(this.getBitmapIn(), this.getConvolutionMask());
    }

    public Bitmap getBitmapIn() {
        return bitmapIn;
    }

    public void setBitmapIn(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ConvolutionMask getConvolutionMask() {
        return convolutionMask;
    }

    public void setConvolutionMask(ConvolutionMask convolutionMask) {
        this.convolutionMask = convolutionMask;
    }
}
