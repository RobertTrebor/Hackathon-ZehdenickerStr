package com.philips.lighting.quickstart;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import android.app.AlertDialog;
import android.util.Log;

import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHGroup;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import com.philips.lighting.model.PHLight.PHLightAlertMode;
import com.philips.lighting.hue.listener.PHGroupListener;
import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHHueSDK;

public class LightsController {

	private PHBridge bridge;
	private PHHueSDK phHueSDK;

	private PHLightAlertMode alertMode;
	//PHBridge bridge = phHueSDK.getSelectedBridge();
//	PHBridgeResourcesCache cache = bridge.getResourceCache();
	private List<PHLight> allLights;

	private static final int MAX_HUE = 65535;
	public static final String TAG = "QuickStart";

	public LightsController() {

		phHueSDK = PHHueSDK.create();
		bridge = phHueSDK.getSelectedBridge();
		allLights = bridge.getResourceCache().getAllLights();
		String[] allLightsId = new String[3];
		int i = 0;
		for (PHLight light : allLights) {
			allLightsId[i] = light.getIdentifier();
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

	public void setColor(HueColor hueColor, int duration, boolean flash) {

		// To validate your lightstate is valid (before sending to the bridge)
		// you can use:
		// String validState = lightState.validateState();

		PHLightState lightState = new PHLightState();
		lightState.setHue(hueColor.getBrightness());
		lightState.setCt(hueColor.getColorTemperature());
		lightState.setHue(hueColor.getHue());
		lightState.setSaturation(hueColor.getSaturation());
		lightState.setX(hueColor.getX());
		lightState.setY(hueColor.getY());

		lightState.setTransitionTime(10);
		if (flash) {
			alertMode = PHLightAlertMode.ALERT_SELECT;
			lightState.setAlertMode(alertMode);
		}
		lightState.setOn(true);

		/*
		 * String validState = lightState.validateState();
		 * Log.d("Is command valid? ", validState);
		 */

		bridge.setLightStateForGroup("Hackathon", lightState);

		Log.d("Controller", "after updateLightState");

		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void success() {
		PHBridge bridge = phHueSDK.getSelectedBridge();
		PHBridgeResourcesCache cache = bridge.getResourceCache();
		PHLightState lightState = new PHLightState();

		List<PHLight> allLights = cache.getAllLights();
		Random rand = new Random();
		int delay = 100;
		int duration = 3;
		// and now disco lights

		for (int count = 0; count < duration; count++) {
			for (int i = 0; i < allLights.size(); i++) {
				lightState = new PHLightState();
				lightState.setHue(rand.nextInt(65535));
				lightState.setTransitionTime(0);
				bridge.updateLightState(allLights.get(i), lightState);
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void setOff() {
		PHLightState lightState = new PHLightState();
		lightState.setOn(false);
		lightState.setTransitionTime(0);
		bridge.setLightStateForGroup("Hackathon", lightState);

	}

	public void setOn() {
		PHLightState lightState = new PHLightState();
		lightState.setOn(true);
		lightState.setTransitionTime(0);
		bridge.setLightStateForGroup("Hackathon", lightState);
	}

//	public void setOneOn() {
//		List<PHLight> allLights = cache.getAllLights();
//		PHLightState lightState = new PHLightState();
//		lightState.setOn(true);
//		lightState.setTransitionTime(0);
//		//bridge.setLightStateForGroup("Hackathon", lightState);
//		bridge.updateLightState(allLights.get(2), lightState);
//	}
//	public void setOneOff() {
//		List<PHLight> allLights = cache.getAllLights();
//		PHLightState lightState = new PHLightState();
//		lightState.setOn(false);
//		lightState.setTransitionTime(0);
//		//bridge.setLightStateForGroup("Hackathon", lightState);
//		bridge.updateLightState(allLights.get(2), lightState);
//	}
	
	public void setAlert() {
		alertMode = PHLightAlertMode.ALERT_SELECT;
		PHLightState lightState = new PHLightState();
		lightState.setAlertMode(alertMode);
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
