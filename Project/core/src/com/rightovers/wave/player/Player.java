package com.rightovers.wave.player;

import com.rightovers.wave.Main;


public class Player {

    public static Player getInstance() {
        if (Main.getInstance().instances.get(Player.class) == null) {
            Main.getInstance().instances.put(Player.class, new Player());
        }
        return (Player) Main.getInstance().instances.get(Player.class);
    }

    private enum DIRECTION {
        UP,
        DOWN
    }

    private final float maxSpeed = 2;
    private final float minSpeed = 1;

    public Controller controller;
    public PlayerGraphics playerGraphics;
    public PlayerPhysics physics;
    public float speed;

    public float getDistance() {
        return this.distance;
    }

    private float distance;

    public DIRECTION direction;

    public void create() {
        this.controller = new Controller();
        this.playerGraphics = new PlayerGraphics();
        this.physics = new PlayerPhysics();

    }


    public void update(float delta) {
        this.distance += this.speed;
        this.physics.update(delta);
        this.playerGraphics.update(delta);
        this.controller.update(delta);
    }

    public void accelerate() {
        if (this.speed < this.maxSpeed) {
            this.speed += 0.1f;
        }
    }

    public void deccelerate() {
        if (this.speed > this.minSpeed) {
            this.speed -= 0.1f;
        }
    }

}
