package com.vedha.sampleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MP3Activity extends Activity {

	private Button startButton;
	private Button stopButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mp3);
		startButton = (Button) findViewById(R.id.start_music_button);
		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startService(v);
			}
		});
		stopButton = (Button) findViewById(R.id.stop_music_button);
		stopButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopService(v);
			}
		});
	}

	public void startService(View v) {
		Intent i = new Intent(this, MP3Service.class);
		startService(i);
	}

	public void stopService(View v) {
		stopService(new Intent(this, MP3Service.class));
	}
}
