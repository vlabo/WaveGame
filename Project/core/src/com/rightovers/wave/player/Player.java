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

    private final int maxSpeed = 2;
    private final int minSpeed = 1;

    public Controller controller;
    public Graphics graphics;
    public PlayerPhysics physics;
    public float speed;

    public DIRECTION direction;

    public void create() {
        this.controller = new Controller();
        this.graphics = new Graphics();
        this.physics = new PlayerPhysics();
    }


    public void update(float delta) {
        this.physics.update(delta);
        this.graphics.update(delta);
        this.controller.update(delta);
    }

}
