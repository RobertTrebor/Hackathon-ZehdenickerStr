package com.philips.lighting.quickstart;

public class HueColor {

	private int colorTemperature;
	private int hue;
	private int saturation;
	private int brightness;
	private float x;
	private float y;
	// hash,
			// :color_temperature => 1000,
			// :transition => 0,
			// :hue => 65312,
			// :saturation => 253,
			// :brightness => 149,
			// :xy => [0.6677, 0.3188]
	public static final HueColor RED = new HueColor(500, 65312, 253, 149, 0.6677f, 0.3188f);
	public static final HueColor ORANGE = new HueColor(500, 11851, 252, 188, 0.5505f, 0.4125f);
	public static final HueColor GREEN = new HueColor(500, 65312, 254, 254, 0.4091f, 0.518f);
	public static final HueColor BLUE = new HueColor(500, 47096, 254, 254, 0.167f, 0.04f);
	public static final HueColor WHITE = new HueColor(0, 29126, 254, 254, 0.3693f, 0.4396f);
	
	
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


	@Override
	public String toString() {
		return "HueColor [colorTemperature=" + colorTemperature + ", hue="
				+ hue + ", saturation=" + saturation + ", brightness="
				+ brightness + ", x=" + x + ", y=" + y + "]";
	}

	
}
