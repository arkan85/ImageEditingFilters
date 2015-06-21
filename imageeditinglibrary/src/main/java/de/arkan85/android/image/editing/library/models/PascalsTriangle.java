package de.arkan85.android.image.editing.library.models;

/**
 * Is needed in order to create Gaussian convolution masks.
 */
public class PascalsTriangle {

    private static final int[][] pascalsTriangle = {{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 3, 3, 1, 0, 0, 0, 0, 0, 0, 0}, {1, 4, 6, 4, 1, 0, 0, 0, 0, 0, 0},
            {1, 5, 10, 10, 5, 1, 0, 0, 0, 0, 0}, {1, 6, 15, 20, 15, 6, 1, 0, 0, 0, 0},
            {1, 7, 21, 35, 35, 21, 7, 1, 0, 0, 0}, {1, 8, 28, 56, 70, 56, 28, 8, 1, 0, 0},
            {1, 9, 36, 84, 126, 126, 84, 36, 9, 1}};

    /**
     * Generate convolution mask. This method is used if you have some kind of preview feature in your application.
     * Since you'll apply the filter to a possibly larger image while writing it to a destination you need to
     * size the convolution mask appropriately.
     *
     * @param sizeMask      the size mask
     * @param prevImgWidth  the prev img width
     * @param prevImgHeight the prev img height
     * @param currImgWidth  the curr img width
     * @param currImgHeight the curr img height
     * @return the double[][]
     */
    public static double[][] generateConvolutionMask(int sizeMask, int prevImgWidth, int prevImgHeight, int currImgWidth, int currImgHeight) {
        /**
         * Using the resolution from the scaled image and the size of the convolution mask used there the new value for the
         * differently sized output image is calculated.
         */
        final int ratioImage = prevImgWidth * prevImgHeight;
        final int ratioMask = sizeMask * sizeMask;
        final float ratioImageToMask = ratioImage / ratioMask;

        /**
         * Calculation of the new convolution mask. The square root of the pixels from the original image is divided by
         * the pixel ratio of the new one.
         */
        float dblNewMaskSize = (float) Math.sqrt(((currImgWidth * currImgHeight) / ratioImageToMask));
        int newMaskSize = Math.round(dblNewMaskSize);
        if (newMaskSize % 2 == 0) {
            newMaskSize += 1;
        }
        /** The output array **/
        double[][] outputArray = new double[newMaskSize][newMaskSize];
        /** Creates an array and fills it with rows from the pascals triangle */
        int[] arrayFromPasc = new int[newMaskSize];
        for (int i = 0; i < newMaskSize; i++) {
            arrayFromPasc[i] = pascalsTriangle[newMaskSize - 1][i];
        }
        /** Fills the convolution mask. **/
        for (int x = 0; x < newMaskSize; x++) {
            for (int y = 0; y < newMaskSize; y++) {
                outputArray[x][y] = arrayFromPasc[y] * arrayFromPasc[x];
            }
        }

        return outputArray;
    }

    /**
     * Coefficients calculation for normalization.
     *
     * @param array the array
     * @return the int
     */
    public static int coefficientsForNormalization(double[][] array) {
        int coefficients, x, y;
        coefficients = 0;

        for (x = 0; x < array.length; x++) {
            for (y = 0; y < array.length; y++) {
                coefficients += array[x][y];
            }
        }

        return coefficients;
    }
}
