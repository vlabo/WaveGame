package com.rightovers.wave.player;

import com.rightovers.wave.Main;
import com.rightovers.wave.utils.Funcs;
import com.rightovers.wave.utils.IResourceable;


public class Player implements IResourceable {

    public static Player getInstance() {
        if (Main.getInstance().instances.get(Player.class) == null) {
            Main.getInstance().instances.put(Player.class, new Player());
        }
        return (Player) Main.getInstance().instances.get(Player.class);
    }

    @Override
    public void loadAssets() {

    }

    private enum DIRECTION {
        UP,
        DOWN
    }

    private final float MAX_SPEED = 1500;
    private final float MIN_SPEED = 200;

    public Controller controller;
    public PlayerGraphics playerGraphics;
    public PlayerPhysics physics;
    public float speed = 200;

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
        this.physics.update(delta);
        this.playerGraphics.update(delta);
        this.controller.update(delta);

        this.distance += this.speed * delta;
        Funcs.print(this.distance + " " + this.speed);
    }

    public void accelerate() {
        if (this.speed < this.MAX_SPEED) {
            this.speed += 50;
        }
    }

    public void deccelerate() {
        if (this.speed > this.MIN_SPEED) {
            this.speed -= 50;
        }
    }

}
