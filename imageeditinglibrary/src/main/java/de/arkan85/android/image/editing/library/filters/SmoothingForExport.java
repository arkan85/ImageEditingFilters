package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;

import de.arkan85.android.image.editing.library.models.ConvolutionMask;

/**
 * Smoothes an image.
 */
public class SmoothingForExport extends Filter {

    private Bitmap bitmapIn;

    private int oldSizeOfMask, prevWidth, prevHeight, curWidth, curHeight;

    private ConvolutionMask convolutionMask;

    public SmoothingForExport(Bitmap bitmapIn, int oldSizeOfMask, int prevWidth, int prevHeight, int curWidth, int curHeight) {
        this.bitmapIn = bitmapIn;
        this.oldSizeOfMask = oldSizeOfMask;
        this.prevWidth = prevWidth;
        this.prevHeight = prevHeight;
        this.curWidth = curWidth;
        this.curHeight = curHeight;
        this.convolutionMask = new ConvolutionMask(adjustSizeForMask(oldSizeOfMask, prevWidth, prevHeight, curWidth, curHeight));
    }

    /**
     * Execute filter.
     *
     * @return the bitmap
     */
    @Override
    public Bitmap executeFilter() {

        this.getConvolutionMask().setAll(1);
        this.getConvolutionMask().Factor = convolutionMask.mask.length;
        this.getConvolutionMask().Offset = 0;

        return ConvolutionMask.calculateConvolutionMxM(this.getBitmapIn(),
                this.getConvolutionMask());
    }

    /**
     * Adjust size for mask.
     *
     * @param oldSizeOfMask the old size of mask
     * @param prevWidth     the prev width
     * @param prevHeight    the prev height
     * @param curWidth      the cur width
     * @param curHeight     the cur height
     * @return the int
     */
    private int adjustSizeForMask(int oldSizeOfMask, int prevWidth, int prevHeight, int curWidth, int curHeight) {
        return Math.round((float) Math.sqrt(((curWidth * curHeight) / ((prevWidth * prevHeight) / (oldSizeOfMask * oldSizeOfMask)))));
    }

    public Bitmap getBitmapIn() {
        return bitmapIn;
    }

    public void setBitmapIn(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
    }

    public int getOldSizeOfMask() {
        return oldSizeOfMask;
    }

    public void setOldSizeOfMask(int oldSizeOfMask) {
        this.oldSizeOfMask = oldSizeOfMask;
    }

    public int getPrevWidth() {
        return prevWidth;
    }

    public void setPrevWidth(int prevWidth) {
        this.prevWidth = prevWidth;
    }

    public int getPrevHeight() {
        return prevHeight;
    }

    public void setPrevHeight(int prevHeight) {
        this.prevHeight = prevHeight;
    }

    public int getCurWidth() {
        return curWidth;
    }

    public void setCurWidth(int curWidth) {
        this.curWidth = curWidth;
    }

    public int getCurHeight() {
        return curHeight;
    }

    public void setCurHeight(int curHeight) {
        this.curHeight = curHeight;
    }

    public ConvolutionMask getConvolutionMask() {
        return convolutionMask;
    }

    public void setConvolutionMask(ConvolutionMask convolutionMask) {
        this.convolutionMask = convolutionMask;
    }
}
