package com.philips.lighting.quickstart;

import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import android.app.Activity;
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
public class MyApplicationActivity extends Activity {
    private PHHueSDK phHueSDK;
    private static final int MAX_HUE=65535;
    public static final String TAG = "QuickStart";
    private LightsController lightsController;
    private List<Activity> activities;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_main);
        phHueSDK = PHHueSDK.create();
        
        lightsController = new LightsController();
        
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
							lightsController.setOneOn();
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							lightsController.setOneOff();
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
						lightsController.success();
						
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
    	Random random = new Random();
    	for (int i=0; i<5; i++) {
    		
    		Action action = randomAction(random.nextInt(Action.all().size()));
    		Log.d("ACTION", action.toString());
    		Log.d("action" + action.getColor().toString(), "dsdf");
	    	lightsController.setColor(action.getColor(), 3000, false);
	    	try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    private Action randomAction(int r) {
    	return Action.all().get(r);
    }

    public void randomLights() {
        PHBridge bridge = phHueSDK.getSelectedBridge();

        List<PHLight> allLights = bridge.getResourceCache().getAllLights();
        Random rand = new Random();
        
        for (PHLight light : allLights) {
            PHLightState lightState = new PHLightState();
            lightState.setHue(rand.nextInt(MAX_HUE));
            // To validate your lightstate is valid (before sending to the bridge) you can use:  
            // String validState = lightState.validateState();
            bridge.updateLightState(light, lightState, listener);
            //  bridge.updateLightState(light, lightState);   // If no bridge response is required then use this simpler form.
        }
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
