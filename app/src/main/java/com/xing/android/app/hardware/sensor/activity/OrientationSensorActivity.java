package com.xing.android.app.hardware.sensor.activity;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;

import com.xing.android.R;
import com.xing.android.app.hardware.sensor.SensorConstant;
import com.xing.android.common.util.LogUtil;

/**
 * Created by zhaoxx on 2017/1/12.
 */

public class OrientationSensorActivity extends Activity {

    private static final String LOG_TAG = "OrientationSensorActivity";

    private ImageView mImageView;

    private SensorManager mSensorManager;

    private SensorEventListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation_sensor);

        mImageView = (ImageView) findViewById(R.id.iv_image);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event == null) {
                    return;
                }
                LogUtil.w(LOG_TAG, "onSensorChanged:values[0] = " + event.values[0] + ", values[1] = " + event.values[1] + ", values[2] = " + event.values[2]);
                update(event);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(SensorConstant.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void update(SensorEvent event) {
        if(event == null) {
            return;
        }
        mImageView.setRotation(-event.values[0]);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(mListener);
    }
}
