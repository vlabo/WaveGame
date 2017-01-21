package com.rightovers.wave.player;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.rightovers.wave.utils.Funcs;

import java.util.ArrayList;

public class Controller {

    private ArrayList<Float> lastZ = new ArrayList<Float>();
    long lastTriggered = Funcs.getTimeMillis();

    // THE CONTROLS

    // Degrees needed to trigger the event
    float degrees = 60;
    // The minimum interval between triggers
    float interval = 700;
    // The size of the sample array
    int arraySize = 20;
    // The last item of the array (arraySize - 1)
    int lastItem = arraySize - 1;
    /**
     * check if gyroscope is available
     */
    public boolean isAvailable() {
        return Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
    }

    /**
     * get all 2 axis of the gyroscope
     */
//    public Vector3 getGyro() {
//        return new Vector3(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerZ());
//    }

    public float getX() {
        return Gdx.input.getAccelerometerX();
    }

    public float getY() {
        return Funcs.changeRange(-10, 10, -180, 180, Gdx.input.getAccelerometerY());
    }

    public float getZ() {
        return Funcs.changeRange(-10, 10, -180, 180, Gdx.input.getAccelerometerZ());
    }

    public void update(float delta) {
    float zaxis = getZ();
    lastZ.add(zaxis);
        if (lastZ.size() <= arraySize) {
            return;
        } else {

            if (lastZ.size() > arraySize) {
                lastZ.remove(0);
            }

            if (lastZ.get(lastItem) - lastZ.get(0) > degrees) {
                if (Funcs.getTimeMillis() - lastTriggered > interval) {
                    // Here you can log your trigger for acceleration
                    // Funcs.print("Trigger accelerate");
                    lastTriggered = Funcs.getTimeMillis();
                    Player.getInstance().accelerate();
                }
            }
        }
        //Funcs.print(" " + Funcs.getTimeMillis());
        //Funcs.print("firstZ: " + lastZ.get(0) + "arr length" + lastZ.size());
    }
}
