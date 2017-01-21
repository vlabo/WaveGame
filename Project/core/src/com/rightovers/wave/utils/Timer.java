/**
 * Timer v1.1 - Adds support for executing functionalities after given time or at given interval
 * Dimitar Popov
 */
package com.rightovers.wave.utils;

import java.util.ArrayList;

public abstract class Timer {
    public Object userObject, userObject2, userObject3;
    private static ArrayList<Timer> timers = new ArrayList<Timer>();

    private float initTime, time;
    private boolean isPaused = false;

    private boolean isRepeatable;

    public void setTime(float time) {
        this.initTime = this.time = time;
    }

    public void tick(float deltaTime) {
        if (this.isPaused == false) {
            this.time -= deltaTime;
            if (this.time <= 0) {
                callback();
                if (this.isRepeatable != true) {
                    timers.remove(this);
                }
                else {
                    this.time = this.initTime;
                }
            }
        }
    }

    public void pause() {
        this.isPaused = true;
    }

    public void resume() {
        this.isPaused = false;
    }

    public void reset() {
        this.time = this.initTime;
    }

    public void remove() {
        timers.remove(this);
    }

    public abstract void callback();

    public static Timer setTimeout(float time, Timer timer) {
        timer.setTime(time);
        timers.add(timer);
        return timer;
    }

    public static Timer setInterval(float time, Timer timer) {
        timer.setTime(time);
        timer.isRepeatable = true;
        timers.add(timer);
        return timer;
    }

    public static void update(float deltaTime) {
        if (timers != null && timers.size() > 0) {
            for (int i = 0; i < timers.size(); i++) {
                timers.get(i).tick(deltaTime);
            }
        }
    }
}
