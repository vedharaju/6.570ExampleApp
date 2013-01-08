package com.vedha.sampleapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class CardMakerActivity extends Activity {
	/**
	 * Activity Constants
	 */
	public static final int RESULT_IMAGE = 1;
	private static final String TAG = "CardMakerActivity";

	/**
	 * Activity OnClickListeners
	 * 
	 */

	public class OnClickOpenGallery implements OnClickListener {

		@Override
		public void onClick(View v) {
			 Intent intent = new Intent(
			 Intent.ACTION_PICK,
			 android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
			 startActivityForResult(intent, RESULT_IMAGE);
			
//			Intent intent = new Intent();
//			intent.setType("image/*");
//			intent.setAction(Intent.ACTION_GET_CONTENT);
//			startActivityForResult(
//					Intent.createChooser(intent, "Select Picture"),
//					RESULT_IMAGE);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardmaker);

		Button b = (Button) findViewById(R.id.button_select_pic);
		b.setOnClickListener(new OnClickOpenGallery());
	}

	public String getPath(Uri uri) {
		String[] filePathColumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = getContentResolver().query(uri, filePathColumn, null,
				null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();
		return picturePath;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_IMAGE && resultCode == RESULT_OK
				&& null != data) {

			Uri selectedImageUri = data.getData();
			Log.d(TAG, "img uri: " + selectedImageUri);
			String selectedImagePath = getPath(selectedImageUri);
			Log.d(TAG, "pic path: " + selectedImagePath);
			Log.d(TAG, "pic path built in: " + selectedImageUri.getPath());
			ImageView img = (ImageView) findViewById(R.id.imageview_pic);
//			img.setImageURI(selectedImageUri);

			img.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));


		}
	}

}
