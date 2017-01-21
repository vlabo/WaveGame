package com.rightovers.wave.player;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.rightovers.wave.utils.Funcs;

public class Controller {

    /**
     * check if gyroscope is available
     */
    public boolean isAvailable() {
        return Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
    }

    /**
     * get all 2 axis of the gyroscope
     */
    public Vector3 getGyro() {
        return new Vector3(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerZ());
    }

    public float getX() {
        return Gdx.input.getAccelerometerX();
    }

    public float getY() {
        return Funcs.changeRange(-10, 10, -180, 180, Gdx.input.getAccelerometerY());
    }

    public float getZ() {
        return Funcs.changeRange(-10, 10, -180, 180, Gdx.input.getAccelerometerZ());
    }


}
