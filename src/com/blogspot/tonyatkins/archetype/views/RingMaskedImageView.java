package com.blogspot.tonyatkins.archetype.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.widget.ImageView;

/**
 * @author duhrer
 * Take a source bitmap and mask it using a ring with the specified inner and outer radius
 */
public class RingMaskedImageView extends ImageView {

	/**
	 * @param context The Context we're drawing in, same as any other View.
	 * @param bitmap The original bitmap we're cutting up.
	 * @param innerRadius The inner (smaller) radius of the ring.
	 * @param outerRadius The outer (larger) radius of the ring.
	 */
	public RingMaskedImageView(Context context, Bitmap bitmap, int innerRadius, int outerRadius) {
		super(context);
		
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLUE);
		paint.setStyle(Style.STROKE);
		
		// A bitmap the same size as the original, to be used in making a mask
		Bitmap maskingBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
		
		// A canvas that we'll draw the new bitmap on.
		Canvas maskingCanvas = new Canvas(maskingBitmap);
		
		// Draw the ring using a very wide stroke		
		paint.setStrokeWidth(outerRadius-innerRadius);
		maskingCanvas.drawCircle(maskingCanvas.getWidth()/2, maskingCanvas.getHeight()/2, (innerRadius+outerRadius)/2, paint);		
		
		Bitmap combinedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
		Canvas combinedCanvas = new Canvas(combinedBitmap);
		
		// Draw the original image
		combinedCanvas.drawBitmap(bitmap, new Matrix(), paint);
		
		// Set the paint to the right XferMode 
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		
		combinedCanvas.drawBitmap(maskingBitmap, new Matrix(), paint);
		
		setImageBitmap(combinedBitmap);
	}
}
