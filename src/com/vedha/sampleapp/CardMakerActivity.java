package com.vedha.sampleapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CardMakerActivity extends Activity {
	/**
	 * Activity Constants
	 */
	public static final int RESULT_IMAGE = 1;
	private static final String TAG = "CardMakerActivity";
	private Uri mSelectedImageUri = null;
	private String mSelectedImagePath = null;
	private String myFileName = null;
	private Uri myUri = null;

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
			/* Save the card */
			Uri uri = saveCard();

			/* Create an implicit intent to share the holiday card */
			Intent share = new Intent(Intent.ACTION_SEND);
			share.setType("image/*");
			if (uri != null) {
				Log.d(TAG, "my uri was not null");
				share.putExtra(Intent.EXTRA_STREAM, uri);
			} else {
				Toast.makeText(v.getContext(), "No image found",
						Toast.LENGTH_SHORT).show();
			}
			share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

			startActivity(Intent.createChooser(share,
					"Send your card to someone"));
		}
	}

	private Uri saveCard() {
		View card = findViewById(R.id.imageview_pic);
		/* Get the Bitmap of the CardView */
		card.setDrawingCacheEnabled(true);
		Bitmap bitmap = Bitmap.createBitmap(card.getDrawingCache());

		/* Save the bitmap of the image */
		String file_path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/HolidayCards";
		Log.d(TAG, "file_path: " + file_path);
		File dir = new File(file_path);
		Log.d(TAG, "dir: " + dir);

		if (!dir.exists()) {
			Log.d(TAG, "dir did not exist");
			dir.mkdirs();
			myFileName = "card_" + System.currentTimeMillis() + ".png";
			File file = new File(dir, myFileName);
			Log.d(TAG, "whole file: " + file);
			FileOutputStream fOut;
			try {
				fOut = new FileOutputStream(file);

				bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
				fOut.flush();
				fOut.close();
				Log.d(TAG, "bitmap compressed and should be saved");
				myUri = Uri.fromFile(file);
				Log.d(TAG, "my uri: " + myUri);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Toast.makeText(this, "file not found exception",
						Toast.LENGTH_SHORT).show();
				Log.d(TAG, Log.getStackTraceString(e));
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(this, "IO exception", Toast.LENGTH_SHORT).show();
				Log.d(TAG, Log.getStackTraceString(e));
			}

		} else {
			Log.i(TAG, "dir already exists");
		}

		return myUri;
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


			CardView card = (CardView) findViewById(R.id.imageview_pic);

			card.setImageURI(mSelectedImageUri);
			card.invalidate();

		}
	}

}
