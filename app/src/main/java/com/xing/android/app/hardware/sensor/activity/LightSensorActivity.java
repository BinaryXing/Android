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

public class LightSensorActivity extends Activity {

    private static final String LOG_TAG = "LightSensorActivity";

    private TextView mStatusView;

    private SensorManager mSensorManager;

    private SensorEventListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor);

        mStatusView = (TextView) findViewById(R.id.tv_status);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                LogUtil.w(LOG_TAG, "onSensorChanged:values[0] = " + event.values[0] + ", values[1] = " + event.values[1] + ", values[2] = " + event.values[2]);
                update(event);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(SensorConstant.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
    }


    private void update(SensorEvent event) {
        if(event == null || event.values == null || event.values.length == 0) {
            return;
        }
        if(event.values[0] < SensorManager.LIGHT_NO_MOON) {
            mStatusView.setText("伸手不见五指");
        } else if(event.values[0] < SensorManager.LIGHT_FULLMOON) {
            mStatusView.setText("十五的月亮十六圆");
        } else if(event.values[0] < SensorManager.LIGHT_CLOUDY) {
            mStatusView.setText("黑云压城城欲摧");
        } else if(event.values[0] < SensorManager.LIGHT_SUNRISE) {
            mStatusView.setText("早上的太阳");
        } else if(event.values[0] < SensorManager.LIGHT_OVERCAST) {
            mStatusView.setText("多云时的太阳");
        } else if(event.values[0] < SensorManager.LIGHT_SHADE) {
            mStatusView.setText("大树底下好乘凉");
        } else if(event.values[0] < SensorManager.LIGHT_SUNLIGHT) {
            mStatusView.setText("太阳当空照");
        } else {
            mStatusView.setText("天上好像不止九个太阳");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(mListener);
    }
}
