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
        Funcs.print("as2d");
        // controller for PC
        Main.getInstance().stage.addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (event.getKeyCode() == Input.Keys.LEFT) {
                    Player.getInstance().setInertiaDecrementStep(Player.getInstance().INERTIA_DECREMENT_STEP_FAST);
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (event.getKeyCode() == Input.Keys.RIGHT) {
                    Player.getInstance().incrementInertia();
                    Player.getInstance().whip();
                }
                else if (event.getKeyCode() == Input.Keys.LEFT) {
                    Player.getInstance().setInertiaDecrementStep(Player.getInstance().INERTIA_DECREMENT_STEP_SLOW);
                }
                return true;
            }

        });

        // tap listener
        Main.getInstance().stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Player.getInstance().setInertiaDecrementStep(5);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Player.getInstance().setInertiaDecrementStep(2);
            }
        });
    }

    // THE CONTROLS

    // Degrees needed to trigger the event
    float degrees = 20;
    // The minimum interval between triggers
    float interval = 500;
    // The size of the sample array
    int arraySize = 15;
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
Funcs.print("Degree:"+this.lastZ.get(this.lastItem),"Difference"+(this.lastZ.get(this.lastItem) - this.lastZ.get(0) ));
            if (this.lastZ.get(this.lastItem) - this.lastZ.get(0) > this.degrees) {
                if (Funcs.getTimeMillis() - this.lastTriggered > this.interval) {
                    // Here you can log your trigger for acceleration
                    // Funcs.print("Trigger incrementInertia");
                    this.lastTriggered = Funcs.getTimeMillis();
                    Player.getInstance().incrementInertia();
                    this.lastZ.set(0,this.lastZ.get(this.lastItem));
                    Player.getInstance().whip();
                }
            }else if (this.lastZ.get(this.lastItem) - this.lastZ.get(0) < -this.degrees) {
                this.lastZ.set(0,this.lastZ.get(this.lastItem));
            }
        }
        //Funcs.print(" " + Funcs.getTimeMillis());
        //Funcs.print("firstZ: " + lastZ.get(0) + "arr length" + lastZ.size());
    }
}
