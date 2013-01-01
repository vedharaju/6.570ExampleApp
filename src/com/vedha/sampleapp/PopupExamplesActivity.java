package com.vedha.sampleapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class PopupExamplesActivity extends Activity {
	private static final String TAG = "PopupExamplesActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Always call the superclass
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");

		// Set the user interface layout for this Activity
		// The layout file is defined in the project
		// res/layout/activity_lifecycle.xml file
		setContentView(R.layout.popupexamples_activity);
	}

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

	}

	@Override
	public void onDestroy() {
		// Always call the superclass
		super.onDestroy(); 
		Log.v(TAG, "onDestroy");

	}

}
