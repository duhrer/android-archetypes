package com.blogspot.tonyatkins.archetype.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blogspot.tonyatkins.archetype.R;
import com.blogspot.tonyatkins.archetype.views.RingMaskedImageView;

public class ImageMaskActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.imagemask);
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.im_ll);
		
//		MaskedImageView view = new MaskedImageView(this);
//		layout.addView(view);

		// The original image we're going to cut into parts
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.BLUE);
		Bitmap originalBitmap = Bitmap.createBitmap(64,64, Bitmap.Config.ARGB_8888);
		Canvas originalCanvas = new Canvas(originalBitmap);
		originalCanvas.drawColor(Color.RED);
		originalCanvas.drawRect(16, 16, 48, 48, paint);
		
		ImageView intactImageView = new ImageView(this);
		intactImageView.setImageBitmap(originalBitmap);
		layout.addView(intactImageView);

		ImageView arrowImageView = new ImageView(this);
		arrowImageView.setImageResource(android.R.drawable.arrow_down_float);
		layout.addView(arrowImageView);
		
		layout.addView(new RingMaskedImageView(this, originalBitmap, 32, 64));
		layout.addView(new RingMaskedImageView(this, originalBitmap, 24, 32));
		layout.addView(new RingMaskedImageView(this, originalBitmap, 16, 24));
		layout.addView(new RingMaskedImageView(this, originalBitmap, 8, 16));
		layout.addView(new RingMaskedImageView(this, originalBitmap, 0, 8));
	}
}
