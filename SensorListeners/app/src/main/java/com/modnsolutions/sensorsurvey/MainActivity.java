package com.modnsolutions.sensorsurvey;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;

    // Individual light and proximity sensor
    private Sensor mSensorProximity;
    private Sensor mSensorLight;

    // TextViews to display current sensor values
    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);

        String sensor_error = getResources().getString(R.string.error_no_sensor);
        if (mSensorLight == null) mTextSensorLight.setText(sensor_error);
        if (mSensorProximity == null) mTextSensorProximity.setText(sensor_error);
    }

    /**
     * Listening to incoming sensor data uses device power and consumes battery life. Don't register
     * your listeners in onCreate(), as that would cause the sensors to be on and sending data
     * (using device power) even when your app was not in the foreground. Use the onStart() and
     * onStop() methods to register and unregister your sensor listeners.
     */
    @Override
    protected void onStart() {
        super.onStart();

        if (mSensorProximity != null) {
            mSensorManager.registerListener(this, mSensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        // The sensor event stores the new data from the sensor in the values array. Depending on
        // the sensor type, this array may contain a single piece of data or a multidimensional
        // array full of data. For example, the accelerometer reports data for the x-axis, y-axis,
        // and z-axis for every change in the values[0], values[1], and values[2] positions. Both
        // the light and proximity sensors only report one value, in values[0].
        float currentValue = event.values[0];

        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(getResources().getString(R.string.label_light,
                        currentValue));
                break;

            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(getResources().getString(R.string.label_proximity,
                        currentValue));

            default: // do nothing
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
