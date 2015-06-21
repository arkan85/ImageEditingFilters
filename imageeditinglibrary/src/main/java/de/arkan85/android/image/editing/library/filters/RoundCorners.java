package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Adds rounded corners to the image.
 */
public class RoundCorners extends Filter {

    private Bitmap bitmapIn;

    private float round;

    public RoundCorners(Bitmap bitmapIn, float round) {
        this.bitmapIn = bitmapIn;
        this.round = round;
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
        final Bitmap bitmapOut = Bitmap.createBitmap(width, height, this.getBitmapIn()
                .getConfig());

        final Canvas canvas = new Canvas(bitmapOut);
        canvas.drawARGB(0, 0, 0, 0);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);

        final Rect rectangle = new Rect(0, 0, width, height);
        final RectF rectanglef = new RectF(rectangle);

        canvas.drawRoundRect(rectanglef, round, round, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(this.getBitmapIn(), rectangle, rectangle, paint);
        System.out.println("Finished @ " + (System.currentTimeMillis() - time) + "ms");

        return bitmapOut;
    }

    public Bitmap getBitmapIn() {
        return bitmapIn;
    }

    public void setBitmapIn(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
    }

    public float getRound() {
        return round;
    }

    public void setRound(float round) {
        this.round = round;
    }
}
