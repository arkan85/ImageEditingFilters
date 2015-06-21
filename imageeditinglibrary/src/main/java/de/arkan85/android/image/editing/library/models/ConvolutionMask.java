package de.arkan85.android.image.editing.library.models;

import android.graphics.Bitmap;
import android.graphics.Color;

public class ConvolutionMask {

    public static final int SIZE = 3;
    public static final int SIZEBIG = 5;
    public final double[][] mask;
    public double Factor = 1;
    public double Offset = 1;

    /**
     * Instantiates a new convolution mask.
     *
     * @param size the size
     */
    public ConvolutionMask(int size) {
        mask = new double[size][size];
    }

    /**
     * Sets all the values inside the convolution mask.
     *
     * @param value the new all
     */
    public void setAll(double value) {
        for (int x = 0; x < SIZE; ++x) {
            for (int y = 0; y < SIZE; ++y) {
                mask[x][y] = value;
            }
        }
    }

    /**
     * Apply convolution settings.
     *
     * @param config the config
     */
    public void applyConvolutionSettings(double[][] config) {
        for (int x = 0; x < SIZE; ++x) {
            for (int y = 0; y < SIZE; ++y) {
                mask[x][y] = config[x][y];
            }
        }
    }

    /**
     * Apply convolution settings [big].
     *
     * @param config the config
     */
    public void applyConvolutionSettingsBig(double[][] config) {
        for (int x = 0; x < SIZEBIG; ++x) {
            for (int y = 0; y < SIZEBIG; ++y) {
                mask[x][y] = config[x][y];
            }
        }
    }

    /**
     * Apply convolution settings [big]..
     *
     * @param config the config
     * @param size   masks size
     */
    public void applyConvolutionSettingsBig(double[][] config, int size) {
        for (int x = 0; x < size; ++x) {
            for (int y = 0; y < size; ++y) {
                mask[x][y] = config[x][y];
            }
        }
    }

    /**
     * Calculates the convolution of a 3x3 convolution mask.
     *
     * @param bitmapIn        input bitmap
     * @param convolutionMask convolution mask
     * @return output bitmap
     */
    public static Bitmap calculateConvolution3x3(Bitmap bitmapIn,
                                                 ConvolutionMask convolutionMask) {
        final int width = bitmapIn.getWidth();
        final int height = bitmapIn.getHeight();
        final Bitmap bitmapOut = Bitmap.createBitmap(width, height, bitmapIn.getConfig());

        int A, R, G, B;
        int sumR, sumG, sumB;
        int[][] pixels = new int[SIZE][SIZE];

        // Inside the bitmap
        for (int y = 0; y < height - 2; ++y) {
            for (int x = 0; x < width - 2; ++x) {
                // inside the mask
                for (int i = 0; i < SIZE; ++i) {
                    for (int j = 0; j < SIZE; ++j) {
                        pixels[i][j] = bitmapIn.getPixel(x + i, y + j);
                    }
                }
                // Gets the alpha of the current pixel
                A = Color.alpha(pixels[1][1]);
                sumR = sumG = sumB = 0;
                for (int i = 0; i < SIZE; ++i) {
                    for (int j = 0; j < SIZE; ++j) {
                        sumR += (Color.red(pixels[i][j]) * convolutionMask.mask[i][j]);
                        sumG += (Color.green(pixels[i][j]) * convolutionMask.mask[i][j]);
                        sumB += (Color.blue(pixels[i][j]) * convolutionMask.mask[i][j]);
                    }
                }

                // Calculate the final color value (R, G, B)
                R = (int) (sumR / convolutionMask.Factor + convolutionMask.Offset);
                if (R < 0)
                    R = 0;
                else if (R > 255)
                    R = 255;

                G = (int) (sumG / convolutionMask.Factor + convolutionMask.Offset);
                if (G < 0)
                    G = 0;
                else if (G > 255)
                    G = 255;

                B = (int) (sumB / convolutionMask.Factor + convolutionMask.Offset);
                if (B < 0)
                    B = 0;
                else if (B > 255)
                    B = 255;

                // Write the calculated values
                bitmapOut.setPixel(x + 1, y + 1, Color.argb(A, R, G, B));
            }
        }

        return bitmapOut;
    }

    /**
     * Calculates the convolution of a 5x5 convolution mask.
     * For code comments check 3x3 convolution.
     *
     * @param bitmapIn        input bitmap
     * @param convolutionMask convolution mask
     * @return output bitmap
     */
    public static Bitmap calculateConvolution5x5(Bitmap bitmapIn, ConvolutionMask convolutionMask) {
        final int width = bitmapIn.getWidth();
        final int height = bitmapIn.getHeight();
        final Bitmap bitmapOut = Bitmap.createBitmap(width, height,
                bitmapIn.getConfig());

        int A, R, G, B;
        int sumR, sumG, sumB;
        int[][] pixels = new int[SIZEBIG][SIZEBIG];

        for (int y = 0; y < height - 4; ++y) {
            for (int x = 0; x < width - 4; ++x) {
                for (int i = 0; i < SIZEBIG; ++i) {
                    for (int j = 0; j < SIZEBIG; ++j) {
                        pixels[i][j] = bitmapIn.getPixel(x + i, y + j);
                    }
                }
                A = Color.alpha(pixels[2][2]);
                sumR = sumG = sumB = 0;
                for (int i = 0; i < SIZEBIG; ++i) {
                    for (int j = 0; j < SIZEBIG; ++j) {
                        sumR += (Color.red(pixels[i][j]) * convolutionMask.mask[i][j]);
                        sumG += (Color.green(pixels[i][j]) * convolutionMask.mask[i][j]);
                        sumB += (Color.blue(pixels[i][j]) * convolutionMask.mask[i][j]);
                    }
                }

                R = (int) (sumR / convolutionMask.Factor + convolutionMask.Offset);
                if (R < 0)
                    R = 0;
                else if (R > 255)
                    R = 255;

                G = (int) (sumG / convolutionMask.Factor + convolutionMask.Offset);
                if (G < 0)
                    G = 0;
                else if (G > 255)
                    G = 255;

                B = (int) (sumB / convolutionMask.Factor + convolutionMask.Offset);
                if (B < 0)
                    B = 0;
                else if (B > 255)
                    B = 255;

                bitmapOut.setPixel(x + 1, y + 1, Color.argb(A, R, G, B));
            }
        }

        return bitmapOut;
    }

    /**
     * Calculates the convolution of a MxM convolution mask.
     * For code comments check 3x3 convolution.
     *
     * @param bitmapIn        input bitmap
     * @param convolutionMask convolution mask
     * @return output bitmap
     */
    public static Bitmap calculateConvolutionMxM(Bitmap bitmapIn,
                                                 ConvolutionMask convolutionMask) {
        final int width = bitmapIn.getWidth();
        final int height = bitmapIn.getHeight();
        final Bitmap bitmapOut = Bitmap.createBitmap(width, height, bitmapIn.getConfig());

        int sizeOfMask = convolutionMask.mask.length;

        int A, R, G, B;
        int sumR, sumG, sumB;
        int[][] pixels = new int[sizeOfMask][sizeOfMask];

        for (int y = 0; y < height - (sizeOfMask - 1); ++y) {
            for (int x = 0; x < width - (sizeOfMask - 1); ++x) {
                for (int i = 0; i < sizeOfMask; ++i) {
                    for (int j = 0; j < sizeOfMask; ++j) {
                        pixels[i][j] = bitmapIn.getPixel(x + i, y + j);
                    }
                }
                A = Color.alpha(pixels[(int) Math.sqrt(sizeOfMask)][(int) Math
                        .sqrt(sizeOfMask)]);
                sumR = sumG = sumB = 0;
                for (int i = 0; i < sizeOfMask; ++i) {
                    for (int j = 0; j < sizeOfMask; ++j) {
                        sumR += (Color.red(pixels[i][j]) * convolutionMask.mask[i][j]);
                        sumG += (Color.green(pixels[i][j]) * convolutionMask.mask[i][j]);
                        sumB += (Color.blue(pixels[i][j]) * convolutionMask.mask[i][j]);
                    }
                }

                R = (int) (sumR / convolutionMask.Factor + convolutionMask.Offset);
                if (R < 0)
                    R = 0;
                else if (R > 255)
                    R = 255;

                G = (int) (sumG / convolutionMask.Factor + convolutionMask.Offset);
                if (G < 0)
                    G = 0;
                else if (G > 255)
                    G = 255;

                B = (int) (sumB / convolutionMask.Factor + convolutionMask.Offset);
                if (B < 0)
                    B = 0;
                else if (B > 255)
                    B = 255;

                bitmapOut.setPixel(x + 1, y + 1, Color.argb(A, R, G, B));
            }
        }

        return bitmapOut;
    }
}
