package com.philips.lighting.quickstart;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLightState;

public class GameActivity extends Activity implements OnTouchListener
{
	//
	//PHBridge bridge;
	//private PHHueSDK phHueSDK;
	//
	private Button buttonRed, buttonYellow, buttonBlue, buttonGreen;
	//
	private ArrayList<Integer> userList;
	private ArrayList<Integer> answerList;
	
	private TextView feedback;
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        
        userList = new ArrayList<Integer>();
        
        /*answerList = new ArrayList<Integer>();
        answerList.add(1); answerList.add(1);
        answerList.add(4); answerList.add(3);*/
        
        //get array (with key "countries") from the input bundle data
        Bundle data = getIntent().getExtras();
        int[] p = data.getIntArray("answers");
        
        answerList = new ArrayList<Integer>();
        
        feedback = (TextView) findViewById(R.id.feedback);
        
        //loop through the array and set class variable words from bundle data
        if( p != null)
        {
        	System.out.println("Not Null");
        	//answerList = new ArrayList<Integer>();
        	for(int k=0; k < p.length; k++)
        	{
        		answerList.add( p[k] );
        	}	
        }
        else
        {
        	System.out.println("NULL");
        }
        
        System.out.println( answerList.toString() );
        
        buttonRed = (Button) findViewById(R.id.buttonRed);
        buttonRed.setOnClickListener(buttonListener);
        buttonYellow = (Button) findViewById(R.id.buttonYellow);
        buttonYellow.setOnClickListener(buttonListener);
        buttonBlue = (Button) findViewById(R.id.buttonBlue);
        buttonBlue.setOnClickListener(buttonListener);
        buttonGreen = (Button) findViewById(R.id.buttonGreen);
        buttonGreen.setOnClickListener(buttonListener);
        
        buttonRed.setOnTouchListener(this);
        buttonRed.setBackgroundColor(Color.RED);
        
        buttonYellow.setOnTouchListener(this);
        buttonYellow.setBackgroundColor(Color.YELLOW);
           
        buttonBlue.setOnTouchListener(this);
        buttonBlue.setBackgroundColor(Color.BLUE);

     
        buttonGreen.setOnTouchListener(this);
        buttonGreen.setBackgroundColor(Color.GREEN);
       
    }

	//anonymous inner class
		private OnClickListener buttonListener = new OnClickListener()
		{
			public void onClick(View v)
			{

				if( v.getId() == R.id.buttonRed )
				{
					userList.add( 0 );
				}
				else if( v.getId() == R.id.buttonYellow )
				{
					userList.add( 1 );
				}
				else if( v.getId() == R.id.buttonBlue )
				{
					userList.add( 2 );
				}
				else if( v.getId() == R.id.buttonGreen )
				{
					userList.add( 3 );
				}
				
				if( userList.size() == answerList.size() )
				{
					Intent intent = getIntent();
					Bundle b = new Bundle();
					//int scoreChange = 0;
					
					String feedbackString = "";
					
					if( userList.equals(answerList) )
					{
						System.out.println("true-------");
						//scoreChange = 1;
						feedbackString = "Correct!";
						setResult(Activity.RESULT_OK, intent);
				        finish();
					}
					else
					{
						finish();
						finish();
						finish();
						Intent fail = new Intent(GameActivity.this, FailActivity.class);
						startActivityForResult(fail, 1);
						
						//scoreChange = -1;
						feedbackString = "Incorrect!";
						GameLogic.discoLights();
					}
					

			        feedback.setText(feedbackString);
					
					/*try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			        //b.putParcelableArray("countries", countries);
			        intent.putExtras(b);
			        
			        setResult(Activity.RESULT_OK, intent);
			        finish();*/
				}
				
			}

		};

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if( v.getId() == R.id.buttonRed )
		{
			//userList.add( 0 );
			System.out.println("Now Turn Button to Black");
			System.out.println("Event" + event.getAction());
			if (event.getAction()==event.ACTION_DOWN) {
				System.out.println("ACTION_DOWN");
				buttonRed.setBackgroundColor(Color.BLACK);						
			}
			if (event.getAction()==event.ACTION_UP) {
				buttonRed.setBackgroundColor(Color.RED);
			}
		}
		if( v.getId() == R.id.buttonYellow )
		{
			//userList.add( 0 );
			if (event.getAction()==event.ACTION_DOWN) {
				System.out.println("ACTION_DOWN");
				buttonYellow.setBackgroundColor(Color.BLACK);						
			}
			if (event.getAction()==event.ACTION_UP) {
				buttonYellow.setBackgroundColor(Color.YELLOW);
			}
		}
		if( v.getId() == R.id.buttonBlue )
		{
			//userList.add( 0 );
			if (event.getAction()==event.ACTION_DOWN) {
				System.out.println("ACTION_DOWN");
				buttonBlue.setBackgroundColor(Color.BLACK);						
			}
			if (event.getAction()==event.ACTION_UP) {
				buttonBlue.setBackgroundColor(Color.BLUE);
			}
		}
		if( v.getId() == R.id.buttonGreen )
		{
			//userList.add( 0 );
			if (event.getAction()==event.ACTION_DOWN) {
				System.out.println("ACTION_DOWN");
				buttonGreen.setBackgroundColor(Color.BLACK);						
			}
			if (event.getAction()==event.ACTION_UP) {
				buttonGreen.setBackgroundColor(Color.GREEN);
			}
		}
		return false;
	}
	
}
