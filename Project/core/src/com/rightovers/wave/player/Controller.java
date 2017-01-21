package com.rightovers.wave.player;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.rightovers.wave.Main;
import com.rightovers.wave.utils.Funcs;

import java.util.ArrayList;

public class Controller {

    private ArrayList<Float> lastZ = new ArrayList<Float>();
    long lastTriggered = Funcs.getTimeMillis();

    public Controller() {
        // controller for PC
        Gdx.input.setInputProcessor(Main.getInstance().stage);
        Main.getInstance().stage.addListener(new InputListener() {

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (event.getKeyCode() == Input.Keys.RIGHT) {
                    Player.getInstance().accelerate();
                }
                else if (event.getKeyCode() == Input.Keys.LEFT) {
                    Player.getInstance().deccelerate();
                }
                return true;
            }

        });
    }

    // THE CONTROLS

    // Degrees needed to trigger the event
    float degrees = 60;
    // The minimum interval between triggers
    float interval = 700;
    // The size of the sample array
    int arraySize = 20;
    // The last item of the array (arraySize - 1)
    int lastItem = this.arraySize - 1;

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
        this.lastZ.add(zaxis);
        if (this.lastZ.size() <= this.arraySize) {
            return;
        }
        else {

            if (this.lastZ.size() > this.arraySize) {
                this.lastZ.remove(0);
            }

            if (this.lastZ.get(this.lastItem) - this.lastZ.get(0) > this.degrees) {
                if (Funcs.getTimeMillis() - this.lastTriggered > this.interval) {
                    // Here you can log your trigger for acceleration
                    // Funcs.print("Trigger accelerate");
                    this.lastTriggered = Funcs.getTimeMillis();
                    Player.getInstance().accelerate();
                }
            }
        }
        //Funcs.print(" " + Funcs.getTimeMillis());
        //Funcs.print("firstZ: " + lastZ.get(0) + "arr length" + lastZ.size());
    }
}
