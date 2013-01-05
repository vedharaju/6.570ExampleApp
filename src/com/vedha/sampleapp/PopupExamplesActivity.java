package com.vedha.sampleapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class PopupExamplesActivity extends Activity {
	private static final String TAG = "PopupExamplesActivity";

	/**
	 * OnClickListeners
	 */

	public class SimpleDialogOnClick implements OnClickListener {

		@Override
		public void onClick(View view) {
			Context c = view.getContext();
			Dialog d = new Dialog(c);
			d.setContentView(R.layout.dialog_example);
			d.setTitle("Example Dialog Window");
			d.show();
		}

	}

	public class AlertDialogOnClick implements OnClickListener {
		/*
		 * for more info:
		 * http://developer.android.com/guide/topics/ui/dialogs.html
		 */
		@Override
		public void onClick(View view) {
			// 1. Instantiate an AlertDialog.Builder with its constructor
			AlertDialog.Builder builder = new AlertDialog.Builder(
					view.getContext());

			// 2. Chain together various setter methods to set the dialog
			// characteristics
			builder.setMessage(R.string.dialog_example_message).setTitle(
					R.string.dialog_example_title);

			// Add the buttons
			builder.setPositiveButton(R.string.dialog_example_ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// User clicked OK button
						}
					});
			builder.setNegativeButton(R.string.dialog_example_cancel,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// User cancelled the dialog
						}
					});
			// Set other dialog properties

			// 3. Get the AlertDialog from create()
			AlertDialog dialog = builder.create();
			dialog.show();
		}

	}

	public class NotificationOnClick implements OnClickListener {

		/*
		 * For more info:
		 * http://developer.android.com/guide/topics/ui/notifiers/
		 * notifications.html
		 */
		private final int NOTIFICATION_ID = 65;

		@Override
		public void onClick(View view) {
			Context c = view.getContext();

			// Creates an explicit intent for an Activity in your app
			Intent notificationIntent = new Intent(c, HomeActivity.class);
			PendingIntent contentIntent = PendingIntent.getActivity(c, 0,
					notificationIntent, Notification.FLAG_AUTO_CANCEL);

			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					c).setSmallIcon(R.drawable.present)
					.setContentTitle("Card Sent")
					.setContentText("Your holiday card has been sent!")
					.setContentIntent(contentIntent).setAutoCancel(true);

			// // The stack builder object will contain an artificial back stack
			// // for the started Activity. This ensures that navigating
			// backward
			// // from the Activity leads out of your application to the Home
			// // screen.
			// TaskStackBuilder stackBuilder = TaskStackBuilder.create(c);
			// // Adds the back stack for the Intent (but not the Intent itself)
			// stackBuilder.addParentStack(HomeActivity.class);
			// // Adds the Intent that starts the Activity to the top of the
			// stack
			// stackBuilder.addNextIntent(notificationIntent);

			NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			// NOTIFICATION_ID allows you to update the notification later on.
			mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Always call the superclass
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");

		// Set the user interface layout for this Activity
		// The layout file is defined in the project
		// res/layout/activity_lifecycle.xml file
		setContentView(R.layout.popupexamples_activity);

		Button toastButton = (Button) findViewById(R.id.toast_example_button);

		/*
		 * One way of setting the OnClickListener is to create an anonymous
		 * class like this
		 */
		toastButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(),
						"Don't forget to butter your toast!",
						Toast.LENGTH_SHORT).show();
			}
		});

		/*
		 * Another way is to define the implementing class explicitly and then
		 * create an instance of it here This is better when you expect to use
		 * the listener in multiple places, or if the code for the listener is
		 * lengthy and will clutter the onCreate method
		 */
		Button dialogButton = (Button) findViewById(R.id.dialog_example_button);
		dialogButton.setOnClickListener(new AlertDialogOnClick());
		Button notifButton = (Button) findViewById(R.id.notification_example_button);
		notifButton.setOnClickListener(new NotificationOnClick());

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
