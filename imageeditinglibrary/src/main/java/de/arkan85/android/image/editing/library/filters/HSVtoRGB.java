package de.arkan85.android.image.editing.library.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Vector;

/**
 * HSV -> RGB.
 */
public class HSVtoRGB extends Filter {

	private Vector<float[]> vectorIn;

	private int width;

	private int height;

	public HSVtoRGB(Vector<float[]> vectorIn, int width, int height) {
		this.vectorIn = vectorIn;
		this.width = width;
		this.height = height;
	}

	/**
	 * Execute filter.
	 * 
	 * @return the bitmap
	 */
	@Override
	public Bitmap executeFilter() {

		final Bitmap bitmapOut = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
		final int[] pixels = new int[this.getVectorIn().size()];

		for (int i = 0; i < this.getVectorIn().size(); i++) {
			pixels[i] = Color.HSVToColor(0xFF, this.getVectorIn().get(i));
		}
		bitmapOut.setPixels(pixels, 0, this.getWidth(), 0, 0, this.getWidth(), this.getHeight());

		return bitmapOut;
	}

	public Vector<float[]> getVectorIn() {
		return vectorIn;
	}

	public void setVectorIn(Vector<float[]> vectorIn) {
		this.vectorIn = vectorIn;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
