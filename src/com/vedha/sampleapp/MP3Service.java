package com.vedha.sampleapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class MP3Service extends Service {

	private MediaPlayer m;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
		m.stop();
		m.release();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();

		if (m != null) {
			m.stop();
		}
		m = MediaPlayer.create(this, R.raw.theme_song);
		m.setLooping(true);
		m.start();
		return super.onStartCommand(intent, flags, startId);
	}

}
