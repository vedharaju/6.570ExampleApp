package com.vedha.sampleapp;

import android.app.Activity;
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
	private static final String TAG = "HomeActivity";

	/**
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Button b = (Button) findViewById(R.id.start_button);

		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), CardMakerActivity.class);
				startActivity(i);
			}
		});
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
		case R.id.menu_popups:
			Intent intent = new Intent(this, PopupExamplesActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_settings:
			/* Implement later */
			break;
		}
		return super.onOptionsItemSelected(item);
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
