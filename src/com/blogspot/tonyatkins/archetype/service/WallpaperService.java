package com.blogspot.tonyatkins.archetype.service;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.view.SurfaceHolder;

public class WallpaperService extends android.service.wallpaper.WallpaperService {
	private List<Integer> resources = new ArrayList<Integer>();
	private List<Integer> forbidden = new ArrayList<Integer>();
	private int[] forbiddenArray = new int[] {0,1,4,5,6,18,19,20,21,22,24,25,26,27,28,98,101,108,109,112,113,134,135,139,140};
	private List<Bitmap> bitmaps = new ArrayList<Bitmap>();
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	@Override
	public Engine onCreateEngine() {
		for (int x : forbiddenArray) { forbidden.add(x); }
		
		for (int a=0; a<144; a++) {
			if (!forbidden.contains(a)) resources.add(17301504 + a);
		}
		
		for (int b=0;b<resources.size();b++) {
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resources.get(b));
			if (bitmap != null) bitmaps.add(bitmap);
		}

		paint.setStyle(Style.STROKE);
		paint.setColor(Color.RED);
		paint.setStrokeWidth(5f);
		
		return new WallpaperEngine();
	}
	
	private class WallpaperEngine extends Engine {
		private boolean visible = true;
		private int offset = 0;
		
		private final Handler handler = new Handler();
		private final Runnable drawRunner = new Runnable() {
			@Override
			public void run() {
				draw();
			}
		};

		private void draw() {
			SurfaceHolder holder = getSurfaceHolder();
			Canvas canvas = holder.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
				int cellHeight = canvas.getHeight()/10;
				int cellWidth = canvas.getWidth()/10;
				
				for (int a = 0; a < 100; a++) {
					int position = (a+offset) < bitmaps.size() ? a+offset : a+offset-bitmaps.size();
					Bitmap bitmap = bitmaps.get(position);
					
					int xOffset = a % 10;
					int yOffset = (int) Math.floor(a/10);
					
					canvas.drawBitmap(bitmap,(xOffset * cellWidth),(yOffset * cellHeight),paint);
				}
				
			}
			holder.unlockCanvasAndPost(canvas);
			
			handler.removeCallbacks(drawRunner);
			if (visible) {
				handler.postDelayed(drawRunner, 1000);
				offset++;
				if (offset > bitmaps.size()) offset = 0;
			}
		}
		
		public WallpaperEngine() {
			handler.post(drawRunner);
		}
		
		@Override
		public void onVisibilityChanged(boolean visible) {
			this.visible = visible;
			if (visible) {
				handler.post(drawRunner);
			}
			else {
				handler.removeCallbacks(drawRunner);
			}
		}
	}
}
