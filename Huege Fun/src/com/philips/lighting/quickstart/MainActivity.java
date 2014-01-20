package com.philips.lighting.quickstart;

//import com.amazon.device.ads.AdLayout;
//import com.amazon.device.ads.AdRegistration;
//import com.amazon.device.ads.AdSize;
//import com.amazon.device.ads.AdTargetingOptions;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amazon.device.ads.AdLayout;
import com.amazon.device.ads.AdRegistration;
import com.amazon.device.ads.AdSize;
import com.amazon.device.ads.AdTargetingOptions;

public class MainActivity extends Activity {

	private AdLayout adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AdRegistration.setAppKey("68b092f37bfc4bb9b11a8bad7f36ee92");

		ImageButton goButton = (ImageButton) findViewById(R.id.eventStart);
		goButton.setOnClickListener(buttonListener);

		AdRegistration.enableTesting(true);
		AdRegistration.enableLogging(true);

		// If you declared AdLayout in your xml you would instead
		// replace the 3 lines above with the following line
		this.adView = (AdLayout) findViewById(R.id.adview);
		// async task to retrieve an ad
		this.adView.loadAd(new AdTargetingOptions().enableGeoLocation(false)); 

		TextView tv = (TextView) findViewById(R.id.textView1);
		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/KBREINDEERGAMES.ttf");
		tv.setTypeface(face);
		tv.setTextSize(50);

		tv = (TextView) findViewById(R.id.textView2);
		tv.setTypeface(face);
		tv.setTextSize(25);

	}

	// anonymous inner class
	private OnClickListener buttonListener = new OnClickListener() {
		public void onClick(View v) {
			if (v.getId() == R.id.eventStart) {
				System.out.println("go....");
				Intent newGame = new Intent(MainActivity.this,
						StartLights.class);
				// newGame.putExtra("countries", countries);
				startActivityForResult(newGame, 1);
			}
		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.adView.destroy();
	}
}
