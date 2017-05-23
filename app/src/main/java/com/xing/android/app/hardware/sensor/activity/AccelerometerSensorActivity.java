package com.xing.android.app.hardware.sensor.activity;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.xing.android.R;
import com.xing.android.app.hardware.sensor.SensorConstant;
import com.xing.android.common.util.LogUtil;

/**
 * Created by zhaoxx on 2017/1/12.
 */

public class AccelerometerSensorActivity extends Activity {

    private static final String LOG_TAG = "AccelerometerSensorActivity";

    private SensorManager mSensorManager;

    private SensorEventListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer_sensor);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event == null) {
                    return;
                }
                LogUtil.w(LOG_TAG, "onSensorChanged:value[0] = " + event.values[0] + ", value[1] = " + event.values[1] + ", value[2] = " + event.values[2]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                if(sensor == null) {
                    return;
                }
                LogUtil.w(LOG_TAG, "onAccuracyChanged:accuracy = " + accuracy);
            }
        };
        mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(SensorConstant.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(mListener);
    }
}
