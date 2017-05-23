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

public class GravitySensorActivity extends Activity {

    private static final String LOG_TAG = "GravitySensorActivity";

    private TextView mStatusView;

    private SensorManager mSensorManager;

    private SensorEventListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravity_sensor);

        mStatusView = (TextView) findViewById(R.id.tv_phone_status);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                LogUtil.w(LOG_TAG, "onSensorChanged:type = " + event.sensor.getType() + ", value[0] = " + event.values[0] + ", value[1] = " + event.values[1] + ", value[2] = " + event.values[2]);
                updateStatus(event);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(SensorConstant.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void updateStatus(SensorEvent event) {
        if(event == null) {
            return;
        }

        if(event.values[0] > 8) {
            mStatusView.setText("手机左侧面朝下");
        } else if(event.values[0] < -8) {
            mStatusView.setText("手机右侧面朝下");
        } else if(event.values[1] > 8) {
            mStatusView.setText("手机后侧面朝下");
        } else if(event.values[1] < -8) {
            mStatusView.setText("手机前侧面朝下");
        } else if(event.values[2] > 8) {
            mStatusView.setText("手机屏幕朝上");
        } else if(event.values[2] < -8) {
            mStatusView.setText("手机屏幕朝下");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(mListener);
    }
}
