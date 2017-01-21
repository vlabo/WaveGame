package com.rightovers.wave.map.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
<<<<<<< HEAD
import com.badlogic.gdx.math.Vector2;
=======
>>>>>>> ee046f705a902909b6dc9e832b62e3dd99f2030f
import com.rightovers.wave.Main;
import com.rightovers.wave.map.Environment;
import com.rightovers.wave.utils.Loader;

import java.util.ArrayList;
import java.util.Random;

public class ObstacleGenerator {

    private Random random = new Random();

    public static ObstacleGenerator getInstance() {
        if (Main.getInstance().instances.get(ObstacleGenerator.class) == null) {
            Main.getInstance().instances.put(ObstacleGenerator.class, new ObstacleGenerator());
        }
        Obstacle.loadAssets();
        return (ObstacleGenerator) Main.getInstance().instances.get(ObstacleGenerator.class);
    }

    ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
<<<<<<< HEAD
    private float time = 0;
    public void update(float delta) {
        time += delta;

        if(time >= (random.nextFloat()*10 + 1)) {
            obstacles.add(createObsitcle());
            time = 0;
        }

        for(Obstacle obstacle : obstacles) {
            obstacle.update(delta);
        }

        for(int i = obstacles.size() - 1; i >= 0; --i) {
            if(obstacles.get(i).getPosition().x < 0) {
                Obstacle obstacle = obstacles.remove(i);
=======

    private float time = 0;

    public void update(float delta) {
        this.time += delta;

        // if we need to spawn new obstacle
        if (this.time >= (this.random.nextFloat() * 10 + 1)) {
            this.obstacles.add(createObsatcle());
            this.time = 0;
        }

        //update all obstacles
        for (Obstacle obstacle : this.obstacles) {
            obstacle.update(delta);
        }

        for (int i = this.obstacles.size() - 1; i >= 0; --i) {
            // if we need to remove an obstacle
            if (this.obstacles.get(i).getPosition().x < 0) {
                Obstacle obstacle = this.obstacles.remove(i);
>>>>>>> ee046f705a902909b6dc9e832b62e3dd99f2030f
                obstacle.destroy();
            }
        }

    }

    public void draw(float delta) {
<<<<<<< HEAD
        for(Obstacle obstacle : obstacles) {
=======
        // draw them all
        for (Obstacle obstacle : this.obstacles) {
>>>>>>> ee046f705a902909b6dc9e832b62e3dd99f2030f
            obstacle.drawBackground(delta);
        }
    }

<<<<<<< HEAD
    private Obstacle createObsitcle() {
=======
    private Obstacle createObsatcle() {
>>>>>>> ee046f705a902909b6dc9e832b62e3dd99f2030f
        Texture texture = Loader.getInstance().getTexture(Obstacle.BUILDING_TEXTURE);
        Rectangle rect = new Rectangle(Main.getInstance().width, Environment.getInstance().getGroundLevel(), texture.getWidth(), texture.getHeight());
        Obstacle obstacle = new Obstacle(Obstacle.Type.BIG, rect);
        return obstacle;
    }

}
