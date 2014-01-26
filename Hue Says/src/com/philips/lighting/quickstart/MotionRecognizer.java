package com.philips.lighting.quickstart;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;

import static android.util.FloatMath.sqrt;

/**
 * Created by kammeyer on 26.01.14.
 */


public class MotionRecognizer implements SensorEventListener {

    private SensorManager mSensorManager;

    private Sensor mOrientationSensor;
    private Sensor mAccelerometer;

    private ArrayList<Float> mDeltaTurns;
    private Float mLastTurnAngle;

    private MotionRecognizerDelegate mDelegate;
    private float lastTurnTime;

    protected MotionRecognizer(MotionRecognizerDelegate delegate) {
        mDelegate = delegate;
    }

    protected void onCreate(SensorManager sensorManager) {
        mSensorManager = sensorManager;
        mOrientationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    protected void onResume() {
        mSensorManager.registerListener(this, mOrientationSensor, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }


    protected void onPause() {
        mSensorManager.unregisterListener(this);
    }


    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.

        if (event.sensor == mOrientationSensor) {
            float newTurnAngle = event.values[0];
            if (mLastTurnAngle != null) {
                float lastTurnAngle = mLastTurnAngle;
                float turnDiff = mLastTurnAngle;
            }
            mLastTurnAngle = new Float(newTurnAngle);
        }
        else if (event.sensor == mAccelerometer) {
            float[] v = event.values;
            float tAccel = sqrt(v[0]*v[0]+v[1]*v[1]+v[2]*v[2]);
            if (tAccel <= 1) {
                mDelegate.jumped();
            }
        }
        // Do something with this sensor value.
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

}
