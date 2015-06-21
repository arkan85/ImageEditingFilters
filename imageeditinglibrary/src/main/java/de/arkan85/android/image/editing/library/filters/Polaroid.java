package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import java.sql.SQLOutput;

/**
 * Creates a polaroid like effect.
 */
public class Polaroid extends Filter {

    private Bitmap bitmapIn;

    private static final double side = 0.075;

    private static final double bottom = 0.2;

    public Polaroid(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
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
        int _width, _height;

        // Check if for format
        // Adjust size for Polaroid frame
        if (width > height) {
            // Case: Landscape
            _width = width + 2 * ((int) (width * side));
            _height = height + ((int) (height * side))
                    + ((int) (width * bottom));
        } else if (width < height) {
            // Case: Portrait
            _width = width + 2 * ((int) (width * side));
            _height = height + ((int) (height * side))
                    + ((int) (height * bottom));
        } else {
            // Case: same sides
            _width = width + 2 * ((int) (width * side));
            _height = height + ((int) (height * side))
                    + ((int) (height * bottom));
        }

        // Rect and Paint for Polaroid frame
        final Rect rect = new Rect(1, 1, _width - 1, _height - 1);
        final RectF rectf = new RectF(rect);
        final Paint paint = new Paint();
        paint.setColor(Color.argb(0xFF, 0xF1, 0xF1, 0xF1));

        final Bitmap bitmapOut = Bitmap.createBitmap(_width, _height, this
                .getBitmapIn().getConfig());

        final VintageEffect mVintageEffect = new VintageEffect(this.getBitmapIn());

        final Canvas canvas = new Canvas(bitmapOut);
        // Draw border
        canvas.drawColor(Color.argb(0xFF, 0xE1, 0xE1, 0xE1));
        canvas.drawRect(rectf, paint);
        canvas.drawBitmap(mVintageEffect.executeFilter(), (float) (width * side), (float) (width * side), null);

        System.out.println("Finished @ " + (System.currentTimeMillis() - time) + "ms");

        return bitmapOut;
    }

    public Bitmap getBitmapIn() {
        return bitmapIn;
    }

    public void setBitmapIn(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
    }
}
