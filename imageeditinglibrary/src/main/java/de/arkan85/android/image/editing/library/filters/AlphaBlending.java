package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * AlphaBlendish duh'.
 */
public class AlphaBlending extends Filter {

	private Bitmap bitmapIn;

	public AlphaBlending(Bitmap bitmapIn) {
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
		int alpha, red, green, /* blue, */pixel;

		final DoGreyscale doGreyscale = new DoGreyscale(this.getBitmapIn());
		Bitmap bitmapAlpha = doGreyscale.executeFilter();

		/* Setting alpha channel @ 0.5 */
		final int[] pixelsAlpha = new int[width * height];
		bitmapAlpha.getPixels(pixelsAlpha, 0, width, 0, 0, width, height);
		for (int i = 0; i < pixelsAlpha.length; i++) {
			pixel = pixelsAlpha[i];
			alpha = Color.alpha(pixel);
			alpha /= 2;
			pixel = Color.argb(alpha, Color.red(pixel), Color.green(pixel),
					Color.blue(pixel));
			pixelsAlpha[i] = pixel;
		}
		bitmapAlpha.setPixels(pixelsAlpha, 0, width, 0, 0, width, height);

		/* lowering the values in the red and green channels */
		final Bitmap bitmapOut = Bitmap.createBitmap(width, height, this.getBitmapIn().getConfig());
		final int[] pixels = new int[width * height];
		getBitmapIn().getPixels(pixels, 0, width, 0, 0, width, height);
		for (int i = 0; i < pixels.length; i++) {
			pixel = pixels[i];
			red = Color.red(pixel);
			red /= 2;
			green = Color.green(pixel);
			green /= 2;
			pixel = Color.argb(Color.alpha(pixel), red, green,
					Color.blue(pixel));
			pixels[i] = pixel;
		}
		bitmapOut.setPixels(pixels, 0, width, 0, 0, width, height);

		/* Blending the two bitmap on a canvas object */
		final Canvas canvas = new Canvas(bitmapOut);
		canvas.drawBitmap(bitmapAlpha, 0, 0, null);
		bitmapAlpha.recycle();
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
