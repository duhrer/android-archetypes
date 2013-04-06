package com.blogspot.tonyatkins.archetype.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.blogspot.tonyatkins.archetype.Constants;
import com.blogspot.tonyatkins.archetype.R;

import android.app.Activity;
import android.database.DataSetObserver;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NfcActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.nfc);

		byte[] id = getIntent().getByteArrayExtra(NfcAdapter.EXTRA_ID);
		StringBuffer idStringBuffer = new StringBuffer();
		for (byte b : id)
		{
			int intValue = b & 0xff;
			idStringBuffer.append(intValue);
		}

		Tag tag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
		Parcelable[] messages = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

		TextView idView = (TextView) findViewById(R.id.nfcIdView);
		idView.setText(idStringBuffer.toString());

		TextView tagView = (TextView) findViewById(R.id.nfcTagView);
		StringBuffer tagBuffer = new StringBuffer();
		for (String tech : tag.getTechList())
		{
			tagBuffer.append("'" + tech + "', ");
		}
		tagView.setText(tagBuffer.toString());

		ListView messageListView = (ListView) findViewById(R.id.nfcListView);
		messageListView.setAdapter(new NdefRecordListAdapter(messages));
	}

	private class NdefRecordListAdapter implements ListAdapter {
		private final List<NdefRecord> records = new ArrayList<NdefRecord>();

		public NdefRecordListAdapter(Parcelable[] parcelableArray) {
			for (Parcelable parcelable : parcelableArray)
			{
				NdefMessage message = (NdefMessage) parcelable;
				for (NdefRecord record : message.getRecords())
				{
					records.add(record);
				}
			}
		}

		@Override
		public int getCount() {
			return records.size();
		}

		@Override
		public Object getItem(int position) {
			return records.toArray()[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = new TextView(parent.getContext());

			NdefRecord record = (NdefRecord) getItem(position);

			byte[] payload = record.getPayload();
//			String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
			String textEncoding = "UTF-8";
			int languageCodeLength = payload[0] & 0077;
			try
			{
				String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
				String text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
				textView.setText("Text: '" + text + "' (" + languageCode + ")");
			}
			catch (UnsupportedEncodingException e)
			{
				Log.e(Constants.TAG, "Error unpacking NFC text record", e);
			}
			
			return textView;
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
		}

		@Override
		public boolean areAllItemsEnabled() {
			return true;
		}

		@Override
		public boolean isEnabled(int position) {
			return true;
		}
	}
}
