package com.blogspot.tonyatkins.archetype.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.blogspot.tonyatkins.archetype.Constants;
import com.blogspot.tonyatkins.archetype.R;

public class HttpRequestActivity extends Activity {
	private TextView statusView;
	private ListView resultsListView;
	private TextView resultsTextView;
	private String statusText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.http_request);

		statusView = (TextView) findViewById(R.id.httpRequestStatusText);

		// resultsListView = (ListView) findViewById(R.id.httpRequestListView);
		resultsTextView = (TextView) findViewById(R.id.httpRequestTextView);

		try
		{
			new HttpRequestAsyncTask().execute(new URL("https://gdata.youtube.com/feeds/api/standardfeeds/recently_featured"));
		}
		catch (MalformedURLException e)
		{
			Log.e(Constants.TAG, "Malformed URL, can't continue:", e);
		}
	}

	private class HttpRequestAsyncTask extends AsyncTask<URL, String, Long> {
		@Override
		protected Long doInBackground(URL... params) {
			try
			{
				AndroidHttpClient httpClient = AndroidHttpClient.newInstance("Android Device");
				HttpGet httpGet = new HttpGet(params[0].toURI());
				HttpResponse httpResponse = httpClient.execute(httpGet);
//				httpClient.close();

				StringBuffer output = new StringBuffer();

				InputStream responseInputStream = httpResponse.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(responseInputStream), Constants.BUFFER_SIZE);
				int bytes = 0;
				char[] buffer = new char[Constants.BUFFER_SIZE];
				while ((bytes = reader.read(buffer)) != -1)
				{
					output.append(buffer);
				}

				publishProgress(output.toString());
			}
			catch (Exception e)
			{
				String message = "Unable to load http request content...";
				statusView.setText(message);
				Log.e(Constants.TAG, message, e);
			}

			return 0L;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);

			resultsTextView.setText(values[0]);
		}
	}
}
