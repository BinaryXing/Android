package com.xing.android.app.hardware.sensor.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xing.android.R;

/**
 * Created by zhaoxx on 2017/1/11.
 */

public class SensorHomeActivity extends Activity implements View.OnClickListener {

    private TextView mIntroView;
    private TextView mAccelerometerView;
    private TextView mGravitySensorView;
    private TextView mOrientationSensorView;
    private TextView mLightSensorView;
    private TextView mProximitySensorView;
    private TextView mEnvironmentSensorView;
    private TextView mStepSensorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_home);

        mIntroView = (TextView) findViewById(R.id.tv_sensor_intro);
        mAccelerometerView = (TextView) findViewById(R.id.tv_accelerometer_sensor);
        mGravitySensorView = (TextView) findViewById(R.id.tv_gravity_sensor);
        mOrientationSensorView = (TextView) findViewById(R.id.tv_magnetic_sensor);
        mLightSensorView = (TextView) findViewById(R.id.tv_light_sensor);
        mProximitySensorView = (TextView) findViewById(R.id.tv_proximity_sensor);
        mEnvironmentSensorView = (TextView) findViewById(R.id.tv_environment_sensor);
        mStepSensorView = (TextView) findViewById(R.id.tv_step_sensor);

        mIntroView.setOnClickListener(this);
        mAccelerometerView.setOnClickListener(this);
        mGravitySensorView.setOnClickListener(this);
        mOrientationSensorView.setOnClickListener(this);
        mLightSensorView.setOnClickListener(this);
        mProximitySensorView.setOnClickListener(this);
        mEnvironmentSensorView.setOnClickListener(this);
        mStepSensorView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tv_sensor_intro:
                intent.setClass(this, SensorIntroActivity.class);
                break;
            case R.id.tv_accelerometer_sensor:
                intent.setClass(this, AccelerometerSensorActivity.class);
                break;
            case R.id.tv_gravity_sensor:
                intent.setClass(this, GravitySensorActivity.class);
                break;
            case R.id.tv_magnetic_sensor:
                intent.setClass(this, OrientationSensorActivity.class);
                break;
            case R.id.tv_light_sensor:
                intent.setClass(this, LightSensorActivity.class);
                break;
            case R.id.tv_proximity_sensor:
                intent.setClass(this, ProximitySensorActivity.class);
                break;
            case R.id.tv_environment_sensor:
                intent.setClass(this, EnvironmentSensorActivity.class);
                break;
            case R.id.tv_step_sensor:
                intent.setClass(this, StepSensorActivity.class);
                break;
            default:
                return;
        }
        startActivity(intent);
    }
}
