package com.vedha.sampleapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class CardView extends ImageView {

	Paint mTextPaint;
	String TAG = "CardView";

	public CardView(Context context) {
		super(context);
		initPaint();
	}

	public CardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	public CardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPaint();
	}

	private void initPaint() {
		mTextPaint = new Paint();
		mTextPaint.setTextSize(50);
		mTextPaint.setARGB(250, 26, 132, 57);
		mTextPaint.setTypeface(Typeface.SANS_SERIF);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Log.d(TAG, "canvas height: " + canvas.getHeight());
		Log.d(TAG, "canvas width: " + canvas.getWidth());
		int midH = canvas.getHeight() / 2;
		canvas.drawText("Happy Holidays", 50, midH, this.mTextPaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// need to override this method when you aren't extending an existing
		// View
	}

}
