package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;

import de.arkan85.android.image.editing.library.models.ConvolutionMask;

/**
 * Applies a gaussian blur.
 */
public class GaussianBlurStrong extends Filter {

    private Bitmap bitmapIn;

    private ConvolutionMask convolutionMask;

    public GaussianBlurStrong(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
        final double[][] convolutionMask = new double[][]{
                {1, 4, 6, 4, 1},
                {4, 16, 24, 16, 4},
                {6, 24, 36, 24, 16, 6},
                {4, 16, 24, 16, 4},
                {1, 4, 6, 4, 1},
        };
        this.convolutionMask = new ConvolutionMask(5);
        this.convolutionMask.applyConvolutionSettingsBig(convolutionMask);
        this.convolutionMask.Factor = 256;
        this.convolutionMask.Offset = 0;
    }

    /**
     * Execute filter.
     *
     * @return the bitmap
     */
    @Override
    public Bitmap executeFilter() {
        return ConvolutionMask.calculateConvolution5x5(this.getBitmapIn(),
                this.getConvolutionMask());
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
}
