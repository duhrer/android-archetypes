package com.blogspot.tonyatkins.archetype.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.blogspot.tonyatkins.archetype.Constants;
import com.blogspot.tonyatkins.archetype.R;

public class ArchetypeService extends Service {
	public static final String STOP = "STOP";

	private Notification notification;

	private SampleTimerTask task;

	@Override
	public void onCreate() {
		super.onCreate();

		Log.i(Constants.TAG, "Service create() in progress...");

		task = new SampleTimerTask();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		boolean stopRunning = intent.getBooleanExtra(STOP, false);

		if (task.isRunning()) {
			if (stopRunning) {
				Log.i(Constants.TAG, "Start was called with the STOP flag, cancelling...");
				task.cancel();
			} 
			else {
				Log.i(Constants.TAG, "Start was called again, but I'm already running.  Ignoring...");
			}
		}
		else if (!stopRunning) {
			Log.i(Constants.TAG, "Start was called and Runnable is not already running.  Starting runnable...");
			Timer timer = new Timer();
			timer.schedule(task, 200);
		} 
		else {
			Log.i(Constants.TAG, "Start was called with the STOP flag, but I'm not running.  Shutting down.");
			stopSelf();
		}
	}

	public void onDestroy() {
		Log.i(Constants.TAG, "Service destroy() in progress...");

		final NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
		notificationManager.cancel(42);

		super.onDestroy();
	}

	private class SampleTimerTask extends TimerTask {
		private boolean cancelled = false;
		private boolean running = false;

		@Override
		public void run() {
			running = true;
			Log.i(Constants.TAG, "Creating sample notification...");

			final NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
			notification = new Notification(R.drawable.icon,"Performing Sample Tasks", System.currentTimeMillis());
			notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT;
			notification.contentView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.service_progress_bar);

			notification.contentView.setTextViewText(R.id.serviceProgressBarTotal, "0/100");
			notification.contentView.setTextViewText(R.id.serviceProgressBarPercentage, "0%");

			notification.contentView.setProgressBar(R.id.serviceProgressBar,100, 0, false);

			notificationManager.notify(42, notification);

			Log.i(Constants.TAG, "Running sample task...");
			int i = 0;
			try {
				for (i = 0; i <= 100; i++) {
					if (isCancelled()) {
						throw new InterruptedException(
								"Cancelled from within for loop...");
					}
					notification.contentView.setTextViewText(R.id.serviceProgressBarTotal, i + "/100");
					notification.contentView.setTextViewText(R.id.serviceProgressBarPercentage, i + "%");
					notification.contentView.setProgressBar(R.id.serviceProgressBar, 100, i, false);
					notificationManager.notify(42, notification);

					Thread.sleep(100L);
				}
				Log.i(Constants.TAG, "Processing completed normally...");
			} 
			catch (InterruptedException e) {
				Log.i(Constants.TAG, "Processing interrupted at " + i + "%...");
				Log.i(Constants.TAG, e.getMessage());
			}
			finally {
				running = false;
				ArchetypeService.this.stopSelf();
			}
		}

		@Override
		public boolean cancel() {
			cancelled = true;
			return super.cancel();
		}

		private boolean isCancelled() {
			return cancelled;
		}

		private boolean isRunning() {
			return running;
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}
