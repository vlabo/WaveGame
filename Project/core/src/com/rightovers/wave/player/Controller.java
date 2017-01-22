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
        Main.getInstance().stage.addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                if (event.getKeyCode() == Input.Keys.LEFT) {
                    Player.getInstance().speedMultiplier = Player.getInstance().SPEED_SLOWDOWN_MULTIPLIER;
                   // Player.getInstance().setInertiaDecrementStep(Player.getInstance().INERTIA_DECREMENT_STEP_FAST);
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
                    Player.getInstance().speedMultiplier = 1f;
                   // Player.getInstance().setInertiaDecrementStep(Player.getInstance().INERTIA_DECREMENT_STEP_SLOW);
                }
                return true;
            }

        });

        // tap listener
        Main.getInstance().stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //Player.getInstance().setInertiaDecrementStep(5);
                Player.getInstance().speedMultiplier = Player.getInstance().SPEED_SLOWDOWN_MULTIPLIER;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //Player.getInstance().setInertiaDecrementStep(2);
                Player.getInstance().speedMultiplier = 1f;
            }
        });
    }

    // THE CONTROLS

    // Degrees needed to trigger the event
    float degrees = 50;
    // The minimum interval between triggers
    float interval = 500;
    // The size of the sample array
    int arraySize = 10;
    // The last item of the array (arraySize - 1)
    int lastItem = this.arraySize - 1;

    /**
     * check if gyroscope is available
     */
    public boolean isAvailable() {
        return Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
    }

    public float getZ() {
        double norm_Of_g = Math.sqrt(Gdx.input.getAccelerometerZ() * Gdx.input.getAccelerometerZ() + Gdx.input.getAccelerometerY() * Gdx.input.getAccelerometerY() + Gdx.input.getAccelerometerX() * Gdx.input.getAccelerometerX());

        // Normalize the accelerometer vector
        double accelX = Gdx.input.getAccelerometerX() / norm_Of_g;
        double accelY = Gdx.input.getAccelerometerY() / norm_Of_g;
        double accelZ = Gdx.input.getAccelerometerZ() / norm_Of_g;
        int inclinationZ = (int) Math.round(Math.toDegrees(Math.acos(accelZ)));
        return inclinationZ;
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

            if (this.lastZ.get(this.lastItem) - this.lastZ.get(0) < -this.degrees) {
                if (Funcs.getTimeMillis() - this.lastTriggered > this.interval) {
                    this.lastTriggered = Funcs.getTimeMillis();
                    Player.getInstance().incrementInertia();
                    Player.getInstance().whip();
                }
            }
        }
    }
}
