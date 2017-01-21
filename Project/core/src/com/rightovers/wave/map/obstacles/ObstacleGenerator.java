package com.rightovers.wave.map.obstacles;

import com.badlogic.gdx.math.Vector2;
import com.rightovers.wave.Main;
import com.rightovers.wave.map.Environment;

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
    private float time = 0;
    public void update(float delta) {
        time += delta;

        if(time >= (random.nextFloat()*10 + 1)) {
            obstacles.add(createObsitcle());
            time = 0;
        }

        for(Obstacle obstacle : obstacles) {
            obstacle.getPosition().x += Environment.BACKGROUND_MOVE_SPEED;
        }

        for(int i = obstacles.size() - 1; i >= 0; --i) {
            if(obstacles.get(i).getPosition().x < 0) {
                obstacles.remove(i);
            }
        }

    }

    public void draw(float delta) {
        for(Obstacle obstacle : obstacles) {
            obstacle.drawBackground(delta);
        }
    }

    private Obstacle createObsitcle() {
        Obstacle obstacle = new Obstacle(Obstacle.Type.BIG, new Vector2(Main.getInstance().width, Environment.getInstance().getGroundLevel()));
        return obstacle;
    }

}
