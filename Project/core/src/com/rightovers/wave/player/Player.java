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

    private final float maxSpeed = 2;
    private final float minSpeed = 1;

    public Controller controller;
    public PlayerGraphics playerGraphics;
    public PlayerPhysics physics;
    public float speed = 20;

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
        Funcs.print(this.distance + " dis");
    }

    public void accelerate() {
        if (this.speed < this.maxSpeed) {
            this.speed += 10;
        }
    }

    public void deccelerate() {
        if (this.speed > this.minSpeed) {
            this.speed -= 0.1f;
        }
    }

}
