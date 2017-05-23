package com.xing.android.app.hardware.sensor.activity;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.xing.android.R;
import com.xing.android.app.hardware.sensor.SensorConstant;
import com.xing.android.common.util.LogUtil;

/**
 * Created by zhaoxx on 2017/1/12.
 */

public class StepSensorActivity extends Activity {

    private static final String LOG_TAG = "StepSensorActivity";

    private TextView mStatusView;

    private SensorManager mSensorManager;

    private SensorEventListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_sensor);

        mStatusView = (TextView) findViewById(R.id.tv_status);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                update(event);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(SensorConstant.TYPE_STEP_DETECTOR), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(SensorConstant.TYPE_STEP_COUNTER), SensorManager.SENSOR_DELAY_NORMAL);

    }

    private void update(SensorEvent event) {
        if(event == null) {
            return;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("update:");
        if(event.sensor != null) {
            buffer.append(" sensorType = " + event.sensor.getType()).append(", ");
        }
        if(event.values != null && event.values.length > 0) {
            for(int i = 0 ; i < event.values.length ; i++) {
                buffer.append("values[" + i + "] = " + event.values[i]);
            }
        }
        LogUtil.w(LOG_TAG, buffer.toString());
        switch (event.sensor.getType()) {
            case 18:
                mStatusView.setText("步行检测：values[0] = " + event.values[0]);
                break;
            case 19:
                mStatusView.setText("计步器:values[0] = " + event.values[0]);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(mListener);
    }
}
