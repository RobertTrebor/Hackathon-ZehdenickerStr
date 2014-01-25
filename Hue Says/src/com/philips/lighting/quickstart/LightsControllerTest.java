package com.philips.lighting.quickstart;

import junit.framework.TestCase;

public class LightsControllerTest extends TestCase {

	LightsController lightsController;
	
	protected void setUp() throws Exception {
		super.setUp();
		lightsController = new LightsController();
	}

	
	

	public final void testSetColor() {
		lightsController.setColor("red", 5000, false);
		
	}

}
