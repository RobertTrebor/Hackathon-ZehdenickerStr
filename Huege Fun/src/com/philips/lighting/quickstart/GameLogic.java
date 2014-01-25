package com.philips.lighting.quickstart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

import android.content.Context;
import android.util.Log;

import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

public class GameLogic {
	private PHHueSDK phHueSDK;

	public static boolean stateGameOver = false;
	private int roundNr = 1;

	static PHBridge bridge;
	private static PHLightState lightState;
	HashMap<Integer, Integer> arrayColorsPallete = new HashMap();
	ArrayList<PHLight> allLights;

	public ArrayList<Integer> arrayPattern = new ArrayList();

	// lightState.setTransitionTime(1); <= to main method

	public GameLogic(Context context) {
		phHueSDK = PHHueSDK.getInstance(context);

		bridge = phHueSDK.getSelectedBridge();
		lightState = new PHLightState();

		// allLights = bridge.getResourceCache().getAllLights();

		lightState.setOn(true);
		lightState.setHue(32768);
		// lightState.setBrightness(255);
		bridge.setLightStateForDefaultGroup(lightState);
	}

	public void declareColors() { // <= init in main method

		arrayColorsPallete.put(0, 0); // RED
		arrayColorsPallete.put(1, 17000); // YELLOW
		arrayColorsPallete.put(2, 46920); // BLUE
		arrayColorsPallete.put(3, 25000); // GREEN
	}

	public void startRound() throws InterruptedException {
		// System.out.println("here");
		arrayPattern.clear();
		// here we init the stuff starting sequence
		// PHBridge bridge = phHueSDK.getSelectedBridge();
		Random rand = new Random();

		for (int i = 0; i <= roundNr + 2; i++) {
			int randomColor = 0;
			do {
				randomColor = rand.nextInt(arrayColorsPallete.size());
			} while ((arrayPattern.size() > 0)
					&& (randomColor == arrayPattern
							.get(arrayPattern.size() - 1)));

			System.out.println("color: " + randomColor);

			// lightState.setOn(true);
			lightState = new PHLightState();
			lightState.setHue(arrayColorsPallete.get(randomColor));
			lightState.setTransitionTime(0);
			bridge.setLightStateForDefaultGroup(lightState);
			arrayPattern.add(randomColor);
			Thread.sleep(800);
		}

		lightState = new PHLightState();
		lightState.setHue(32768);
		lightState.setTransitionTime(0);
		bridge.setLightStateForDefaultGroup(lightState);
		Thread.sleep(800);

		roundNr++;

	}

	public int getRoundNr() {
		return roundNr;
	}

	public static void discoLights() {
		Random rand = new Random();
		// and now disco lights
		ArrayList<PHLight> allLights = bridge.getResourceCache().getAllLights();
		for (int count = 0; count < 30; ++count) {
			for (int i = 0; i < allLights.size(); ++i) {
				lightState = new PHLightState();
				lightState.setHue(rand.nextInt(65535));
				lightState.setTransitionTime(0);
				bridge.updateLightState(allLights.get(i), lightState);
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lightState = new PHLightState();
		lightState.setHue(32768);
		lightState.setTransitionTime(0);
		bridge.setLightStateForDefaultGroup(lightState);

	}
}