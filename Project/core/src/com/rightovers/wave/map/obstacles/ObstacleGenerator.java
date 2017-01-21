package com.rightovers.wave.map.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
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
    private float time = 0;

    public void update(float delta) {
        this.time += delta;

        //        if (this.time >= (this.random.nextFloat() * 10 + 1)) {
        //            this.obstacles.add(createObsitcle());
        //            this.time = 0;
        //        }

        for (Obstacle obstacle : this.obstacles) {
            obstacle.update(delta);
        }

        for (int i = this.obstacles.size() - 1; i >= 0; --i) {
            if (this.obstacles.get(i).getPosition().x < 0) {
                Obstacle obstacle = this.obstacles.remove(i);
                obstacle.destroy();
            }
        }

    }

    public void draw(float delta) {
        for (Obstacle obstacle : this.obstacles) {
            obstacle.drawBackground(delta);
        }
    }

    private Obstacle createObsitcle() {
        Texture texture = Loader.getInstance().getTexture(Obstacle.BUILDING_TEXTURE);
        Rectangle rect = new Rectangle(Main.getInstance().width, Environment.getInstance().getGroundLevel(), texture.getWidth(), texture.getHeight());
        Obstacle obstacle = new Obstacle(Obstacle.Type.BIG, rect);
        return obstacle;
    }

}
