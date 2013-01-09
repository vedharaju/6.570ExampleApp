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
	private String myFileName = null;
	private Uri myUri = null;
	private Bitmap bitmap = null;

	/**
	 * Activity OnClickListeners
	 * 
	 */

	public class OnClickOpenGallery implements OnClickListener {

		@Override
		public void onClick(View v) {
			ImageView img = (ImageView) findViewById(R.id.imageview_pic);
			if (img != null) {
				img.setImageBitmap(null);
				if (bitmap != null) {
					bitmap.recycle();
					bitmap = null;
				}
			}
			Intent intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, RESULT_IMAGE);

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

		String externalDir = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		String file_path = externalDir + "/HolidayCards";
		File dir = new File(file_path);
		Log.d(TAG, "File directory path: " + dir);

		if (!dir.exists()) {
			dir.mkdirs();
		} 
		
		myFileName = "card_" + System.currentTimeMillis() + ".png";
		File file = new File(dir, myFileName);
		Log.d(TAG, "File path to holiday card: " + file);
		
		FileOutputStream fOut;
		try {
			fOut = new FileOutputStream(file);

			bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
			fOut.flush();
			fOut.close();
			Log.d(TAG, "Holiday Card bitmap compressed and saved");
			myUri = Uri.fromFile(file);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "Exception: check LogCat", Toast.LENGTH_SHORT)
					.show();
			Log.d(TAG, Log.getStackTraceString(e));
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_IMAGE && resultCode == RESULT_OK
				&& null != data) {

			Uri selectedImageUri = data.getData();
			mSelectedImageUri = selectedImageUri;
			Log.d(TAG, "Uri of selected image: " + selectedImageUri);
		
			CardView card = (CardView) findViewById(R.id.imageview_pic);
			try {
				bitmap = MediaStore.Images.Media.getBitmap(
						this.getContentResolver(), selectedImageUri);
				card.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(this, "file not found", Toast.LENGTH_SHORT)
						.show();
				card.setImageURI(mSelectedImageUri);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(this, "IO exception", Toast.LENGTH_SHORT).show();
				card.setImageURI(mSelectedImageUri);

			}
			// card.setImageURI(mSelectedImageUri);
			card.invalidate();

		}
	}

}
