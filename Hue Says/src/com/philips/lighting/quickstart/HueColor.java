package com.philips.lighting.quickstart;

public class HueColor {

	private int colorTemperature;
	private int hue;
	private int saturation;
	private int brightness;
	private float x;
	private float y;
	
	public HueColor(int colorTemperature, int hue, int saturation, int brightness, float x, float y) {
		this.colorTemperature = colorTemperature;
		this.hue = hue;
		this.saturation = saturation;
		this.brightness = brightness;
		this.x = x;
		this.y = y;
	}

	public int getColorTemperature() {
		return colorTemperature;
	}

	public int getHue() {
		return hue;
	}

	public int getSaturation() {
		return saturation;
	}

	public int getBrightness() {
		return brightness;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	
}
