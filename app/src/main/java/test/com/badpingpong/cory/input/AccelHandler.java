package test.com.badpingpong.cory.input;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;

/**
 * Created by Cory on 4/17/2017.
 */

public class AccelHandler implements SensorEventListener {
    private final SensorManager sensorManager;

    private float accelX = 0;
    private float accelY = 0;
    private float accelZ = 0;

    public AccelHandler(View view) {
        sensorManager = (SensorManager) view.getContext().getSystemService(Activity.SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelX = event.values[0];
                accelY = event.values[1];
                accelZ = event.values[2];
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }

    public synchronized float getAccelX() {
        return accelX;
    }

    public synchronized float getAccelY() {
        return accelY;
    }

    public synchronized float getAccelZ() {
        return accelZ;
    }
}
