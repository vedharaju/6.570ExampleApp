package com.vedha.sampleapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class CardMakerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardmaker);
		Intent i = getIntent();
		Uri selectedImage = i.getData();
		ImageView iv = (ImageView) findViewById(R.id.imageview_pic);
		iv.setImageURI(selectedImage);

	}

}
