package com.philips.lighting.quickstart;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class StartLights extends Activity {
	
	private GameLogic gameLogic;
	private TextView textLabelRoundNumber, textLabelUserScore, countdown;
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startlights);
        
        //Button goButton = (Button) findViewById(R.id.lightsStart);
        //goButton.setOnClickListener(buttonListener);
        
        gameLogic = new GameLogic( this );
        gameLogic.declareColors();
        
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/KBREINDEERGAMES.ttf");
        
        textLabelRoundNumber = (TextView) findViewById(R.id.textLabelRoundNumber);
        textLabelRoundNumber.setTypeface(face); textLabelRoundNumber.setTextSize(50);
        textLabelRoundNumber.setText("Round: " + gameLogic.getRoundNr() );
        
        textLabelUserScore = (TextView) findViewById(R.id.textLabelUserScore);
        textLabelUserScore.setTypeface(face); textLabelUserScore.setTextSize(50);
        textLabelUserScore.setText("High Score: 7");
        
        countdown = (TextView) findViewById(R.id.countdown);
        countdown.setTypeface(face); countdown.setTextSize(450);
     
        
        try {
			startCountdown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	private void startCountdown() throws InterruptedException
	{
		
		new CountDownTimer(5000, 1000) {

		     public void onTick(long millisUntilFinished) {
		    	 countdown.setText("" + millisUntilFinished / 1000);
		     }

		     public void onFinish()
		     {
		    	 countdown.setText(" ");
		    	 
		    	 try {
		         	gameLogic.startRound();
		         }catch (Exception e) {}
		 		
		 		Intent newGame = new Intent(StartLights.this, GameActivity.class);
		 		int[] array = new int[gameLogic.arrayPattern.size()];
		 		for(int k=0; k < array.length; k++)
		 		{
		 			array[k] = gameLogic.arrayPattern.get(k);
		 		}
		 		
		 		newGame.putExtra("answers", array);
		 		startActivityForResult(newGame, 1);
		     }
		  }.start();
       
	}
	
	//anonymous inner class
/*		private OnClickListener buttonListener = new OnClickListener()
		{
			public void onClick(View v)
			{
				if( v.getId() == R.id.lightsStart )
				{
					System.out.println("go....");
					
					
			        gameLogic.declareColors();
			        try {
			        	gameLogic.startRound();
			        }catch (Exception e) {}
					
					Intent newGame = new Intent(StartLights.this, GameActivity.class);
					//newGame.putExtra("countries", countries);
					
					//Integer[] array = gameLogic.arrayPattern.toArray(new Integer[0]);
					//int[] array = (int[]) gameLogic.arrayPattern.toArray();
					int[] array = new int[gameLogic.arrayPattern.size()];
					for(int k=0; k < array.length; k++)
					{
						array[k] = gameLogic.arrayPattern.get(k);
					}
					
					
					for(int k=0; k < array.length; k++)
					{
						System.out.println( array[k] );
					}
					
					newGame.putExtra("answers", array);
					//newGame.putIntegerArrayListExtra("answers", array);
					//newGame.putExtra(name, value) 
					
					startActivityForResult(newGame, 1);
				}
			}

		};*/
	
}
