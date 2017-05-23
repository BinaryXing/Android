package com.xing.android.app.hardware.sensor.activity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.xing.android.R;
import com.xing.android.app.hardware.sensor.SensorUtil;
import com.xing.android.common.ui.CommonBaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoxx on 2017/1/11.
 */

public class SensorIntroActivity extends Activity {

    private ListView mSensorView;

    private SensorAdapter mAdapter;

    private SensorManager mSensorManager;

    private SparseArray<Sensor> mSupportSensorList = new SparseArray<Sensor>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_intro);

        mSensorView = (ListView) findViewById(R.id.lv_sensor);
        mAdapter = new SensorAdapter(this, null);
        mSensorView.setAdapter(mAdapter);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        List<Sensor> list = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        if(list != null) {
            for(Sensor sensor : list) {
                if(sensor == null) {
                    continue;
                }
                mSupportSensorList.put(sensor.getType(), sensor);
            }
        }
        mAdapter.setSupportSensor(mSupportSensorList);
        List<SensorType> sensorTypeList = new ArrayList<SensorType>();
        SensorType type;
        for(int i = 1 ; i < 22 ; i++) {
            type = new SensorType(i);
            sensorTypeList.add(type);
        }
        mAdapter.setData(sensorTypeList);
    }


    private static class SensorType {

        public SensorType(int type) {
            this.type = type;
        }

        public int type;
    }

    private static class SensorAdapter extends CommonBaseAdapter<SensorType> {

        private SparseArray<Sensor> mSupportSensorList = new SparseArray<Sensor>();

        public SensorAdapter(Context context, List<SensorType> list) {
            super(context, list);
        }

        public void setSupportSensor(SparseArray<Sensor> supportSensorList) {
            if(supportSensorList != null) {
                mSupportSensorList = supportSensorList;
            }
        }

        @Override
        protected View newView(int position, ViewGroup parent, SparseArray<View> sparseArray) {
            View layout = LayoutInflater.from(mContext).inflate(R.layout.item_sensor, parent, false);
            if(sparseArray != null) {
                sparseArray.put(R.id.tv_basic_info, layout.findViewById(R.id.tv_basic_info));
                sparseArray.put(R.id.tv_other_info, layout.findViewById(R.id.tv_other_info));
                sparseArray.put(R.id.tv_value_desc, layout.findViewById(R.id.tv_value_desc));
                sparseArray.put(R.id.cb_support, layout.findViewById(R.id.cb_support));
            }
            return layout;
        }

        @Override
        protected void bindView(int position, View convertView, ViewGroup parent, SparseArray<View> sparseArray) {
            SensorType sensorType = getItem(position);
            if(sensorType == null || sparseArray == null) {
                return;
            }
            TextView basicInfoView = (TextView) sparseArray.get(R.id.tv_basic_info);
            TextView otherInfoView = (TextView) sparseArray.get(R.id.tv_other_info);
            TextView valueDescView = (TextView) sparseArray.get(R.id.tv_value_desc);
            CheckBox supportView = (CheckBox) sparseArray.get(R.id.cb_support);
            basicInfoView.setText(SensorUtil.getBaseInfo(sensorType.type, mSupportSensorList.get(sensorType.type)));
            otherInfoView.setText(SensorUtil.getOtherInfo(mSupportSensorList.get(sensorType.type)));
            valueDescView.setText(SensorUtil.getSensorValueDesc(sensorType.type));
            supportView.setChecked(mSupportSensorList.get(sensorType.type) != null);
        }
    }
}
