package com.philips.lighting.quickstart;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import android.util.Log;

import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHGroup;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import com.philips.lighting.hue.listener.PHGroupListener;
import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHHueSDK;

public class LightsController {

	private PHBridge bridge;
	private PHHueSDK phHueSDK;
	
	private List<PHLight> allLights;

	private static final int MAX_HUE = 65535;
	public static final String TAG = "QuickStart";

	HashMap<String, HueColor> arrayColorsPallete = new HashMap<String, HueColor>();

	private HueColor red = new HueColor(500, 65312, 253, 149, 0.6677f, 0.3188f);
	private HueColor blue = new HueColor(500, 47096, 254, 254, 0.167f, 0.04f);
	private HueColor green = new HueColor(500, 65312, 254, 254, 0.4091f, 0.518f);
	private HueColor orange = new HueColor(500, 11851, 252, 188, 0.5505f, 0.4125f);

	public LightsController() {
		// hash,
		// :color_temperature => 1000,
		// :transition => 0,
		// :hue => 65312,
		// :saturation => 253,
		// :brightness => 149,
		// :xy => [0.6677, 0.3188]

		arrayColorsPallete.put("red", red);
		arrayColorsPallete.put("blue", blue);
		arrayColorsPallete.put("orange", orange);
		arrayColorsPallete.put("green", green);
		phHueSDK = PHHueSDK.create();
		bridge = phHueSDK.getSelectedBridge();
		allLights = bridge.getResourceCache().getAllLights();
		String[] allLightsId = new String[3];
		int i=0;
		for (PHLight light : allLights) {		
			allLightsId[i]=light.getIdentifier();
					i++;
		}
		
		bridge.createGroup("Hackathon", allLightsId, new PHGroupListener() {
			
			@Override
			public void onCreated(PHGroup group) {
			}

			@Override
			public void onError(int arg0, String arg1) {
			}

			@Override
			public void onStateUpdate(Hashtable<String, String> arg0,
					List<PHHueError> arg1) {
			}

			@Override
			public void onSuccess() {
			}
		});
	}

	public void setColor(String color, int duration, boolean flash) {

		// To validate your lightstate is valid (before sending to the bridge)
		// you can use:
		// String validState = lightState.validateState();
		HueColor hueColor = arrayColorsPallete.get(color);
		
			PHLightState lightState = new PHLightState();
			lightState.setHue(hueColor.getBrightness());
			lightState.setCt(hueColor.getColorTemperature());
			lightState.setHue(hueColor.getHue());
			lightState.setSaturation(hueColor.getSaturation());
			lightState.setX(hueColor.getX());
			lightState.setY(hueColor.getY());

			lightState.setTransitionTime(0);

			/*String validState = lightState.validateState();
			Log.d("Is command valid? ", validState);*/
			
			bridge.setLightStateForGroup("Hackathon", lightState,
	                new PHGroupListener() {
						
						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub	
						}
						
						@Override
						public void onStateUpdate(Hashtable<String, String> arg0,
								List<PHHueError> arg1) {
							// TODO Auto-generated method stub
						}
						
						@Override
						public void onError(int arg0, String arg1) {
							// TODO Auto-generated method stub
						}
					});
			
			Log.d("Controller", "after updateLightState");
		
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	// If you want to handle the response from the bridge, create a
	// PHLightListener object.
	PHLightListener listener = new PHLightListener() {

		@Override
		public void onSuccess() {
		}

		@Override
		public void onStateUpdate(Hashtable<String, String> arg0,
				List<PHHueError> arg1) {
			Log.w(TAG, "Light has updated");
		}

		@Override
		public void onError(int arg0, String arg1) {
		}
	};

}
