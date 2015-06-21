package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;

import de.arkan85.android.image.editing.library.models.ConvolutionMask;

/**
 * Applies a gaussian blur.
 */
public class GaussianBlur extends Filter {

    private Bitmap bitmapIn;

    private ConvolutionMask convolutionMask;

    public GaussianBlur(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
        final double[][] convolutionMask = new double[][]{{1, 2, 1}, {2, 4, 2},
                {1, 2, 1},};
        this.convolutionMask = new ConvolutionMask(3);
        this.convolutionMask.applyConvolutionSettings(convolutionMask);
        this.convolutionMask.Factor = 16;
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
