package com.example.touchpand;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	void printSamples(MotionEvent ev) {
		final int historySize = ev.getHistorySize();
		final int pointerCount = ev.getPointerCount();
		for (int h = 0; h < historySize; h++) {
			System.out.printf("At time %d:", ev.getHistoricalEventTime(h));
			for (int p = 0; p < pointerCount; p++) {
				System.out.printf("  pointer %d: (%f,%f)",
						ev.getPointerId(p), ev.getHistoricalX(p, h), ev.getHistoricalY(p, h));
			}
		}
		System.out.printf("At time %d:", ev.getEventTime());
		for (int p = 0; p < pointerCount; p++) {
			System.out.printf("  pointer %d: (%f,%f)",
					ev.getPointerId(p), ev.getX(p), ev.getY(p));
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		TextView textView = (TextView)findViewById(R.id.coord_text);
		
		Float xPos = event.getRawX();
		Float yPos = event.getRawY();		
		
		textView.setText("raw X:" + xPos.toString() + ", raw Y: " + yPos.toString() + "\n" + "current X:" + event.getX() + ", raw Y: " + event.getY() + "\n");
		
		printSamples(event);
		
		
		return super.onTouchEvent(event);
	}

}

