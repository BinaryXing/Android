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

/**
 * Created by zhaoxx on 2017/1/13.
 */

public class EnvironmentSensorActivity extends Activity {

    private static final String LOG_TAG = "EnvironmentSensorActivity";

    private TextView mAirPressView;
    private TextView mTemperatureView;
    private TextView mHumidityView;

    private SensorManager mSensorManager;
    private SensorEventListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment_sensor);

        mAirPressView = (TextView) findViewById(R.id.tv_air_press);
        mTemperatureView = (TextView) findViewById(R.id.tv_temperature);
        mHumidityView = (TextView) findViewById(R.id.tv_humidity);

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
        mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(SensorConstant.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(SensorConstant.TYPE_AMBIENT_TEMPERATURE), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(SensorConstant.TYPE_RELATIVE_HUMIDITY), SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void update(SensorEvent event) {
        if(event == null || event.sensor == null || event.values == null || event.values.length == 0) {
            return;
        }
        switch (event.sensor.getType()) {
            case SensorConstant.TYPE_PRESSURE:
                mAirPressView.setText("大气压：" + event.values[0]);
                break;
            case SensorConstant.TYPE_AMBIENT_TEMPERATURE:
                mTemperatureView.setText("环境温度：" + event.values[0]);
                break;
            case SensorConstant.TYPE_RELATIVE_HUMIDITY:
                mHumidityView.setText("相对湿度：" + event.values[0]);
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
