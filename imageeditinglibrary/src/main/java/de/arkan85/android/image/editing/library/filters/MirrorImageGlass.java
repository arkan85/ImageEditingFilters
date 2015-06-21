package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;

/**
 * Mirrors an image on a glassy like surface.
 */
public class MirrorImageGlass extends Filter {

    private Bitmap bitmapIn;

    private static final int GAP = 4;

    public MirrorImageGlass(Bitmap bitmapIn) {
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

        final Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);

        final Bitmap mirroredImage = Bitmap.createBitmap(this.bitmapIn, 0,
                height / 2, width, height / 2, matrix, false);
        final Bitmap fullImage = Bitmap.createBitmap(width, (height + height / 2),
                Config.ARGB_8888);

        final Canvas canvas = new Canvas(fullImage);
        canvas.drawBitmap(this.getBitmapIn(), 0, 0, null);

        final Paint paint = new Paint();
        canvas.drawRect(0, height, width, height + GAP, paint);

        canvas.drawBitmap(mirroredImage, 0, height + GAP, null);

        final Paint paint2 = new Paint();
        final LinearGradient lgrad = new LinearGradient(0, this.getBitmapIn()
                .getHeight(), 0, fullImage.getHeight() + GAP, 0x70ffffff,
                0x00ffffff, TileMode.CLAMP);
        paint2.setShader(lgrad);
        paint2.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        canvas.drawRect(0, height, width, fullImage.getHeight() + GAP, paint2);
        mirroredImage.recycle();
        System.out.println("Finished @ " + (System.currentTimeMillis() - time) + "ms");

        return fullImage;
    }

    public Bitmap getBitmapIn() {
        return bitmapIn;
    }

    public void setBitmapIn(Bitmap bitmapIn) {
        this.bitmapIn = bitmapIn;
    }
}
