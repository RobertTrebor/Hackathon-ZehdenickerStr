package com.philips.lighting.quickstart;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class FailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameover);

		TextView tv = (TextView) findViewById(R.id.textView1);
		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/KBREINDEERGAMES.ttf");
		tv.setTypeface(face);
		tv.setTextSize(250);
	}
}