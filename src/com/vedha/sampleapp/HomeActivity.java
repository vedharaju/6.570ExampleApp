package com.vedha.sampleapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends Activity {

	/**
	 * Activity Constants
	 */
	public static final int RESULT_IMAGE = 1;
	private static final String TAG = "HomeActivity";

	/**
	 * Activity OnClickListeners
	 * 
	 */
	public class OnClickOpenExample implements OnClickListener {

		@Override
		public void onClick(View view) {
			Context c = view.getContext();
			Intent intent = new Intent(c, PopupExamplesActivity.class);
			c.startActivity(intent);
		}

	}

	public class OnClickOpenGallery implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, RESULT_IMAGE);
		}
	}

	public class OnClickMusic implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent i = new Intent(v.getContext(), MP3Activity.class);
			startActivity(i);
		}

	}

	/**
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Button b = (Button) findViewById(R.id.start_button);

		/* Toggle between the two listeners */
		b.setOnClickListener(new OnClickOpenExample());
		// b.setOnClickListener(new OnClickOpenGallery());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_music:
			Intent i = new Intent(this, MP3Activity.class);
			startActivity(i);
			break;
		case R.id.menu_settings:
			/* Implement later */
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			Toast.makeText(this, "picture selected!", Toast.LENGTH_SHORT)
					.show();
			// String picturePath contains the path of selected Image
		}
	}

	/**
	 * Background Music Methods
	 */

	public void startService() {
		Intent i = new Intent(this, MP3Service.class);
		startService(i);
	}

	public void stopService() {
		stopService(new Intent(this, MP3Service.class));
	}

	/**
	 * Other Lifecycle Methods
	 */
	@Override
	public void onStart() {
		// Always call the superclass
		super.onStart();
		Log.v(TAG, "onStart");

	}

	@Override
	public void onResume() {
		// Always call the superclass
		super.onResume();
		Log.v(TAG, "onResume");
		startService();

	}

	@Override
	public void onPause() {
		// Always call the superclass
		super.onPause();
		Log.v(TAG, "onPause");

	}

	@Override
	public void onStop() {
		// Always call the superclass
		super.onStop();
		Log.v(TAG, "onStop");
		stopService();

	}

	@Override
	public void onDestroy() {
		// Always call the superclass
		super.onDestroy();
		Log.v(TAG, "onDestroy");

	}
}
