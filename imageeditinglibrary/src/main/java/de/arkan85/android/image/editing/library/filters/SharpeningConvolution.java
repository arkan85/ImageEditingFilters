package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;

import de.arkan85.android.image.editing.library.models.ConvolutionMask;

/**
 * A convolution for sharpening.
 */
public class SharpeningConvolution extends Filter {

    private Bitmap bitmapIn;

    private ConvolutionMask convolutionMask;

    public SharpeningConvolution(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
        final double[][] sharpeningConfig = new double[][]{
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}
        };
        this.convolutionMask = new ConvolutionMask(3);
        this.convolutionMask.applyConvolutionSettings(sharpeningConfig);
        this.convolutionMask.Factor = 1;
        this.convolutionMask.Offset = 0;
    }

    /**
     * Execute filter.
     *
     * @return the bitmap
     */
    @Override
    public Bitmap executeFilter() {
        return ConvolutionMask.calculateConvolution3x3(this.getBitmapIn(),
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
