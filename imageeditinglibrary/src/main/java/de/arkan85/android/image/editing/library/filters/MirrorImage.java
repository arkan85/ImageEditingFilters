package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Mirrors an image.
 */
public class MirrorImage extends Filter {

	private Bitmap bitmapIn;

	private int type;

	private static final int FLIP_VERTICAL = 1;

	private static final int FLIP_HORIZONTAL = 2;

	public MirrorImage(Bitmap bitmapIn, int type) {
		this.bitmapIn = bitmapIn;
		this.type = type;
	}

	/**
	 * Execute filter.
	 * 
	 * @return the bitmap
	 */
	@Override
	public Bitmap executeFilter() {

		final long time = System.currentTimeMillis();
		final Matrix matrix = new Matrix();

		if (type == FLIP_VERTICAL)
			matrix.preScale(1.0f, -1.0f);
		if (type == FLIP_HORIZONTAL)
			matrix.preScale(-1.0f, 1.0f);
		System.out.println("Finished @ " + (System.currentTimeMillis() - time) + "ms");

		return Bitmap.createBitmap(this.getBitmapIn(), 0, 0, this.getBitmapIn().getWidth(), this.getBitmapIn().getHeight(), matrix, true);
	}

	public Bitmap getBitmapIn() {
		return bitmapIn;
	}

	public void setBitmapIn(Bitmap bitmapIn) {
		this.bitmapIn = bitmapIn;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
