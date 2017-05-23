package com.xing.android.app.hardware.sensor;

import android.hardware.Sensor;

/**
 * Created by zhaoxx on 2017/1/11.
 */

public class SensorUtil {

    public static String getBaseInfo(int type, Sensor sensor){
        StringBuffer info = new StringBuffer();
        info.append("type = ").append(type);
        info.append(", name = ");
        switch (type) {
            case SensorConstant.TYPE_ACCELEROMETER:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_ACCELEROMETER : sensor.getName());
                break;
            case SensorConstant.TYPE_MAGNETIC_FIELD:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_MAGNETIC_FIELD : sensor.getName());
                break;
            case SensorConstant.TYPE_ORIENTATION:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_ORIENTATION : sensor.getName());
                break;
            case SensorConstant.TYPE_GYROSCOPE:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_GYROSCOPE : sensor.getName());
                break;
            case SensorConstant.TYPE_LIGHT:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_LIGHT : sensor.getName());
                break;
            case SensorConstant.TYPE_PRESSURE:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_PRESSURE : sensor.getName());
                break;
            case SensorConstant.TYPE_TEMPERATURE:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_TEMPERATURE : sensor.getName());
                break;
            case SensorConstant.TYPE_PROXIMITY:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_PROXIMITY : sensor.getName());
                break;
            case SensorConstant.TYPE_GRAVITY:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_GRAVITY : sensor.getName());
                break;
            case SensorConstant.TYPE_LINEAR_ACCELERATION:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_LINEAR_ACCELERATION : sensor.getName());
                break;
            case SensorConstant.TYPE_ROTATION_VECTOR:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_ROTATION_VECTOR : sensor.getName());
                break;
            case SensorConstant.TYPE_RELATIVE_HUMIDITY:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_RELATIVE_HUMIDITY : sensor.getName());
                break;
            case SensorConstant.TYPE_AMBIENT_TEMPERATURE:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_AMBIENT_TEMPERATURE : sensor.getName());
                break;
            case SensorConstant.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_MAGNETIC_FIELD_UNCALIBRATED : sensor.getName());
                break;
            case SensorConstant.TYPE_GAME_ROTATION_VECTOR:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_GAME_ROTATION_VECTOR : sensor.getName());
                break;
            case SensorConstant.TYPE_GYROSCOPE_UNCALIBRATED:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_GYROSCOPE_UNCALIBRATED : sensor.getName());
                break;
            case SensorConstant.TYPE_SIGNIFICANT_MOTION:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_SIGNIFICANT_MOTION : sensor.getName());
                break;
            case SensorConstant.TYPE_STEP_DETECTOR:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_STEP_DETECTOR : sensor.getName());
                break;
            case SensorConstant.TYPE_STEP_COUNTER:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_STEP_COUNTER : sensor.getName());
                break;
            case SensorConstant.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_GEOMAGNETIC_ROTATION_VECTOR : sensor.getName());
                break;
            case SensorConstant.TYPE_HEART_RATE:
                info.append(sensor == null ? SensorConstant.STRING_TYPE_HEART_RATE : sensor.getName());
                break;
            default:
                return "";
        }
        return info.toString();
    }

    public static String getOtherInfo(Sensor sensor) {
        if(sensor == null) {
            return "";
        }
        StringBuffer info = new StringBuffer();
        info.append("最小间隔是").append(sensor.getMinDelay() / 1000).append("毫秒");
        info.append(", 最小单位是").append(sensor.getResolution());
        info.append(", 最大范围是").append(sensor.getMaximumRange());

        return info.toString();
    }

    public static String getSensorValueDesc(int type) {
        StringBuffer desc = new StringBuffer();
        switch (type) {
            case SensorConstant.TYPE_ACCELEROMETER:
                desc.append("values[0]是在X轴的加速度，values[1]是在Y轴的加速度，value[2]是在Z轴的加速度");
                break;
            case SensorConstant.TYPE_MAGNETIC_FIELD:
                desc.append("values[0]是在X轴的磁力，values[1]是在Y轴的磁力，values[2]是在Z轴的磁力");
                break;
            case SensorConstant.TYPE_ORIENTATION:
                desc.append("values[0]是手机正前方所在的方位，values[1]是手机的倾斜度，values[2]是手机的翻滚度");
                break;
            case SensorConstant.TYPE_GYROSCOPE:
                desc.append("values[0]是X轴的角速度，values[1]是Y轴的角速度，values[2]是Z轴的角速度");
                break;
            case SensorConstant.TYPE_LIGHT:
                break;
            case SensorConstant.TYPE_PRESSURE:
                break;
            case SensorConstant.TYPE_TEMPERATURE:
                break;
            case SensorConstant.TYPE_PROXIMITY:
                break;
            case SensorConstant.TYPE_GRAVITY:
                break;
            case SensorConstant.TYPE_LINEAR_ACCELERATION:
                break;
            case SensorConstant.TYPE_ROTATION_VECTOR:
                break;
            case SensorConstant.TYPE_RELATIVE_HUMIDITY:
                break;
            case SensorConstant.TYPE_AMBIENT_TEMPERATURE:
                break;
            case SensorConstant.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                break;
            case SensorConstant.TYPE_GAME_ROTATION_VECTOR:
                break;
            case SensorConstant.TYPE_GYROSCOPE_UNCALIBRATED:
                break;
            case SensorConstant.TYPE_SIGNIFICANT_MOTION:
                break;
            case SensorConstant.TYPE_STEP_DETECTOR:
                break;
            case SensorConstant.TYPE_STEP_COUNTER:
                break;
            case SensorConstant.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                break;
            case SensorConstant.TYPE_HEART_RATE:
                break;
            default:
                return "";
        }
        return desc.toString();
    }
}
