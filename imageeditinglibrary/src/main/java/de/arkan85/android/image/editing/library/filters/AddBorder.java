package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Creates a black border around the image.
 */
public class AddBorder extends Filter {

    private Bitmap bitmapIn;

    private double borderSize;

    public AddBorder(Bitmap bitmapIn, double borderSize) {
        this.bitmapIn = bitmapIn;
        this.borderSize = borderSize;
    }

    /**
     * Execute filter.
     *
     * @return the bitmap
     */
    @Override
    public Bitmap executeFilter() {

        long time = System.currentTimeMillis();
        final int width = this.getBitmapIn().getWidth();
        final int height = this.getBitmapIn().getHeight();
        int x, y;
        final int border = (int) (((width * this.getBorderSize()) + (height * this.getBorderSize())) / 2);
        final Bitmap bitmapOut = Bitmap.createBitmap(width, height, this.getBitmapIn().getConfig());
        final Canvas canvas = new Canvas();
        canvas.setBitmap(bitmapOut);
        canvas.drawBitmap(this.getBitmapIn(), 0, 0, null);

        final Paint paint = new Paint();
        for (x = 0; x < width; x++) {
            for (y = 0; y < height; y++) {
                if (((x < border) || (x > width - border))
                        || ((y < border) || (y > height - border))) {
                    paint.setARGB(127, 0, 0, 0);
                    canvas.drawPoint(x, y, paint);
                }
                if (((x == border) || (x == width - border))
                        || ((y == border) || (y == height - border))) {
                    paint.setARGB(127, 255, 255, 255);
                    canvas.drawPoint(x, y, paint);
                }
            }
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Finished @ " + time + "ms");

        return bitmapOut;
    }

    public Bitmap getBitmapIn() {
        return bitmapIn;
    }

    public void setBitmapIn(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
    }

    public double getBorderSize() {
        return borderSize;
    }

    public void setBorderSize(double borderSize) {
        this.borderSize = borderSize;
    }
}
