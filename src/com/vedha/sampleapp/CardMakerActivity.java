package com.vedha.sampleapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class CardMakerActivity extends Activity {
	/**
	 * Activity Constants
	 */
	public static final int RESULT_IMAGE = 1;
	private static final String TAG = "CardMakerActivity";
	private Uri mSelectedImageUri = null;
	private String mSelectedImagePath = null;

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

			// Intent intent = new Intent();
			// intent.setType("image/*");
			// intent.setAction(Intent.ACTION_GET_CONTENT);
			// startActivityForResult(
			// Intent.createChooser(intent, "Select Picture"),
			// RESULT_IMAGE);
		}
	}

	public class onClickSend implements OnClickListener {

		@Override
		public void onClick(View v) {
			/*
			 * Create an implicit intent to share the holiday card
			 */
			Intent share = new Intent(Intent.ACTION_SEND);
			share.setType("image/*");
			if (mSelectedImageUri != null) {
				Log.d(TAG, "my uri was not null");
				share.putExtra(Intent.EXTRA_STREAM, mSelectedImageUri);
			} else {
				Toast.makeText(v.getContext(), "No image found",
						Toast.LENGTH_SHORT).show();
			}
			share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

			startActivity(Intent.createChooser(share,
					"Send your card to someone"));
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardmaker);

		Button b = (Button) findViewById(R.id.button_select_pic);
		b.setOnClickListener(new OnClickOpenGallery());

		Button sendButton = (Button) findViewById(R.id.button_send);
		sendButton.setOnClickListener(new onClickSend());
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
			mSelectedImageUri = selectedImageUri;
			Log.d(TAG, "img uri: " + selectedImageUri);
			String selectedImagePath = getPath(selectedImageUri);
			mSelectedImagePath = selectedImagePath;
			Log.d(TAG, "pic path: " + selectedImagePath);
			Log.d(TAG, "pic path built in: " + selectedImageUri.getPath());

			/* */
			// String[] id = {MediaStore.Images.Media._ID};
			// Cursor imageCursor =
			// managedQuery(MediaStore.Images.Media.INTERNAL_CONTENT_URI, id,
			// null, null, MediaStore.Images.Media.DATA);
			// int image_col_index =
			// imageCursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
			// int count = imageCursor.getCount();
			// //in a loop the below code goes {
			// ImageView i = new ImageView(this.getApplicationContext());
			// imageCursor.moveToPosition(position);
			// int id = imageCursor.getInt(image_col_index);
			// i.setImageURI(Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
			// ""+ id));
			// i.setScaleType(ImageView.ScaleType.CENTER_CROP);
			// i.setLayoutParams(new GridView.LayoutParams(70, 70));
			// //here I am storing this image i to a list for further use
			// }//here loop ends

			/* */

			CardView card = (CardView) findViewById(R.id.imageview_pic);

			card.setImageURI(mSelectedImageUri);
			card.invalidate();

		}
	}

}
