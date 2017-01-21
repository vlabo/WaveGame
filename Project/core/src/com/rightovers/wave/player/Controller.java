package com.rightovers.wave.player;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.rightovers.wave.utils.Funcs;

import java.util.ArrayList;

public class Controller {

    private ArrayList<Float> lastZ = new ArrayList<Float>();
    float lastTriggered = Funcs.getTime();
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
        if (lastZ.size() <= 30) {
            return;
        } else {

            if (lastZ.size() > 30) {
                lastZ.remove(0);
            }

            if (lastZ.get(29) - lastZ.get(0) > 60) {
                if (Funcs.getTime() - lastTriggered < 300) {
                    lastTriggered = Funcs.getTime();
                    Funcs.print("Triggered");
                }
            }
        }
        //Funcs.print(" " + Funcs.getTime();
        //Funcs.print("firstZ: " + lastZ.get(0) + "arr length" + lastZ.size());
    }
}
