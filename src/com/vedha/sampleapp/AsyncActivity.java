package com.vedha.sampleapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AsyncActivity extends Activity {

	private static ProgressBar progress;

	public class ProgressTask extends AsyncTask<Integer, Integer, Void> {

		/* The first method called in the AsyncTask, called on the UI thread */
		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			// initialize the progress bar set maximum progress to 100.
			progress.setMax(100);
		}

		/*
		 * the method that executes the time consuming tasks and publish the
		 * task progress, executed in background thread.
		 */
		@Override
		protected Void doInBackground(Integer... params) {
			// get the initial starting value
			int incr = params[0];
			// increment the progress
			for (int i = 0; i <= 100; i += incr) {
				try {
					boolean cancelled = isCancelled();
					// if async task is not cancelled, update the progress
					if (!cancelled) {
						publishProgress(i);
						SystemClock.sleep(1000);

					}

				} catch (Exception e) {
					Log.e("Error", e.toString());
				}
			}
			return null;
		}

		/*
		 * method that updates the progress of the AsyncTask, run on the UI
		 * thread
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			// super.onProgressUpdate(values);
			// increment progress bar by progress value
			progress.setProgress(values[0]);

		}

		/*
		 * the final method that gets called after doInBackground finishes, here
		 * we can update the UI with the results of the AsyncTask
		 */
		@Override
		protected void onPostExecute(Void result) {
			// super.onPostExecute(result);
			// async task finished
			Log.v("Progress", "Finished");
		}

		/*
		 * gets called if the AsyncTask.cancel() methods is called, terminating
		 * the execution of the AsyncTask
		 */
		@Override
		protected void onCancelled() {
			super.onCancelled();
			// stop the progress
			progress.setMax(0);
		}

	}

	public class onClickTask implements OnClickListener {

		ProgressTask task = null;

		@Override
		public void onClick(View v) {
			
			switch (v.getId()) {
			case R.id.button_show:
				if (task==null){
				task = new ProgressTask();
				task.execute(10);
				}
				break;
			case R.id.button_cancel:
				if (task!=null){
				task.cancel(true);
				task = null;
				}
				break;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_async);

		Button toast = (Button) findViewById(R.id.button_async_toast);
		toast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "UI thread is still active",
						Toast.LENGTH_SHORT).show();
			}
		});

		final ProgressTask task = new ProgressTask();

		progress = (ProgressBar) findViewById(R.id.progress);

		Button show = (Button) findViewById(R.id.button_show);
		onClickTask onclick = new onClickTask();
		show.setOnClickListener(onclick);

		Button cancel = (Button) findViewById(R.id.button_cancel);
		cancel.setOnClickListener(onclick);

	}

}
