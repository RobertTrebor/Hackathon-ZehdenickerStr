package com.philips.lighting.quickstart;

import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

/**
 * MyApplicationActivity - The starting point for creating your own Hue App.  
 * Currently contains a simple view with a button to change your lights to random colours.  Remove this and add your own app implementation here! Have fun!
 * 
 * @author SteveyO
 *
 */
public class MyApplicationActivity extends Activity implements MotionRecognizerDelegate {
    private PHHueSDK phHueSDK;
    private static final int MAX_HUE=65535;
    public static final String TAG = "QuickStart";
    private LightsController lightsController;
    private Action currentAction;
    private Random random = new Random();
    
    private int score = 0;
    private int maxActions = 5;
    private int actionCount = 0;
    
    private String mAccelText;
    private String mOrientationText;

    private MotionRecognizer mMotionRecognizer;
    private ToneGenerator mToneGenerator = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_main);
        phHueSDK = PHHueSDK.create();
        
        lightsController = new LightsController();
        
        
        mMotionRecognizer = new MotionRecognizer(this);
        mMotionRecognizer.onCreate((SensorManager) getSystemService(Context.SENSOR_SERVICE));

        mAccelText = "";
        mOrientationText = "";

        Button btnTutorial;
        btnTutorial = (Button) findViewById(R.id.btnTutorial);
        btnTutorial.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//getLightInfo();
				Runnable runnable = new Runnable() {
					
					@Override
					public void run() {
						for (int i=0; i<10; i++) {
							lightsController.setOn();
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							lightsController.setOff();
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				};
				Thread myThread = new Thread(runnable);
				myThread.start();
			}

		});
        
        Button btnStartGame;
        btnStartGame = (Button) findViewById(R.id.btnStartGame);
        btnStartGame.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Runnable runnable = new Runnable() {
					
					@Override
					public void run() {
						//trafficLight();
						startGame();
						//lightsController.success();
						
					}
				};
				Thread myThread = new Thread(runnable);
				myThread.start();
			}
		});
        

    }
    
    private void getLightInfo() {
		// TODO Auto-generated method stub
        PHBridge bridge = phHueSDK.getSelectedBridge();
        List<PHLight> allLights = bridge.getResourceCache().getAllLights();
        
        for (PHLight light : allLights) {
            PHLightState lightState = new PHLightState();
      
            // To validate your lightstate is valid (before sending to the bridge) you can use:  
            // String validState = lightState.validateState();
            bridge.updateLightState(light, lightState, listener);
            //  bridge.updateLightState(light, lightState);   // If no bridge response is required then use this simpler form.
        }
	}
    
    public void trafficLight() {
    	
    	Log.d("trafficLight", "before sending commands");
    	lightsController.setColor(HueColor.RED, 1000, false);
    	Log.d("trafficLight", "red set");
    	lightsController.setColor(HueColor.ORANGE, 1000, false);
    	Log.d("trafficLight", "orange set");
    	lightsController.setColor(HueColor.GREEN, 1000, false);
    	Log.d("trafficLight", "green set");
    }
    
    public void startGame() {
    	trafficLight();
    	nextAction();
    }
    
    private Action randomAction(int r) {
    	return Action.all().get(r);
    }

    // If you want to handle the response from the bridge, create a PHLightListener object.
    PHLightListener listener = new PHLightListener() {
        
        @Override
        public void onSuccess() {  
        }
        
        @Override
        public void onStateUpdate(Hashtable<String, String> arg0, List<PHHueError> arg1) {
           Log.w(TAG, "Light has updated");
        }
        
        @Override
        public void onError(int arg0, String arg1) {  
        }
    };
    
    @Override
    protected void onPause() {
        super.onPause();
        mMotionRecognizer.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMotionRecognizer.onResume();
    }

    @Override
    public void turnedAround() {
        mToneGenerator.startTone(ToneGenerator.TONE_PROP_ACK);
    }

    @Override
    public void jumped() {
        mToneGenerator.startTone(ToneGenerator.TONE_PROP_PROMPT);
        if(currentAction.getName() == "Jump") {
        	incrementScore();
        } else {
        	decrementScore();
        }
    }
    
    private void incrementScore() {
    	score++;
    	lightsController.setColor(HueColor.GREEN, 1000, false);
    	nextAction();
    }
    
    private void decrementScore() {
    	score--;
    	nextAction();
    }
    
    private void nextAction() {
    	Log.d("nextAction", "SCORE IS: " + score);
    	if(maxActions < actionCount) {
    		endGame();
    	} else {
    		actionCount++;
    		currentAction = randomAction(random.nextInt(Action.all().size()));
    		Log.d("ACTION", currentAction.toString());
    		Log.d("action" + currentAction.getColor().toString(), "dsdf");
	    	lightsController.setColor(currentAction.getColor(), 0, false);
	    	// Timer 3 seconds before calling decrementScore();
	    	Timer t = new Timer();  

			TimerTask task = new TimerTask() {

				@Override
				public void run() {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							nextAction();
						}
					});
				}
			};
	    	  
	    	  t.scheduleAtFixedRate(task, 0, 3000);
    	}
    }
    
    private void endGame() {
    	// Whatever
    	System.exit(0);
    }

    @Override
    protected void onDestroy() {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        if (bridge != null) {
            
            if (phHueSDK.isHeartbeatEnabled(bridge)) {
                phHueSDK.disableHeartbeat(bridge);
            }
            
            phHueSDK.disconnect(bridge);
            super.onDestroy();
        }
    }
}
