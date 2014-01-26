package com.philips.lighting.quickstart;

import java.util.ArrayList;
import java.util.List;

public class Action {

	private String name;
	private HueColor color;
	
	public static final Action JUMP = new Action ("Jump", HueColor.BLUE);
	public static final Action SPIN = new Action ("Spin", HueColor.RED);
	public static final Action TURNRIGHT = new Action ("Turn Right", HueColor.ORANGE);
	public static final Action TURNLEFT = new Action ("Turn Left", HueColor.GREEN);
	

	public Action(String name, HueColor color) {
		this.name = name;
		this.color = color;
	}

	public static List<Action> all() {
		List<Action> activityList = new ArrayList<Action>();
		activityList.add(JUMP);
		activityList.add(SPIN);
		activityList.add(TURNRIGHT);
		activityList.add(TURNLEFT);
		
		return activityList;
		
	}

	public HueColor getColor() {
		return color;
	}

	@Override
	public String toString() {
		return "Action [name=" + name + ", color=" + color + "]";
	}
	
	
}
