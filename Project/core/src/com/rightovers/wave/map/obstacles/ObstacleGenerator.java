package com.rightovers.wave.map.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.rightovers.wave.Main;
import com.rightovers.wave.map.Environment;
import com.rightovers.wave.player.Player;
import com.rightovers.wave.utils.Funcs;
import com.rightovers.wave.utils.Loader;

import java.util.ArrayList;
import java.util.Random;

public class ObstacleGenerator {


    private ArrayList<Rectangle> buldingsSizes = new ArrayList<Rectangle>();
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

    public ObstacleGenerator() {
        setBuldingsSizes();
    }

    public void update(float delta) {
        this.time += delta;

        if (this.time >= (this.random.nextFloat() * 50 + 7)) {

            this.obstacles.add(createObstacle(random.nextInt(buldingsSizes.size() - 1)));
            this.time = 0;
        }

        for (Obstacle obstacle : this.obstacles) {
            obstacle.update(delta);
        }

        for (int i = this.obstacles.size() - 1; i >= 0; --i) {

            if (this.obstacles.get(i).getBox2DBody().getPosition().x < Player.getInstance().getDistance() - 10) {
                Gdx.app.log("Destroyed", "Obsticle  " + this.obstacles.get(i).getBox2DBody().getPosition().x);
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

    private Obstacle createObstacle(int bulidingId) {
        Texture texture = Loader.getInstance().getTexture("images/building" + (bulidingId + 1) + ".png");
        //Rectangle rect = new Rectangle(Main.getInstance().width, Environment.getInstance().GROUND_LEVEL, texture.getWidth(), texture.getHeight());\
        Rectangle reference = buldingsSizes.get(bulidingId);
        Rectangle rect = new Rectangle(reference.x + Player.getInstance().getDistance() - 500, reference.y, reference.width, reference.height);
        Obstacle obstacle = new Obstacle(Obstacle.Type.BIG, rect);
        obstacle.setTexture(texture);

        return obstacle;
    }

    private void setBuldingsSizes() {
        this.buldingsSizes.add(new Rectangle(Environment.getInstance().VISIBLE_X_METERS, Environment.getInstance().GROUND_LEVEL, 17, 32));
        this.buldingsSizes.add(new Rectangle(Environment.getInstance().VISIBLE_X_METERS, Environment.getInstance().GROUND_LEVEL, 35, 33));
        this.buldingsSizes.add(new Rectangle(Environment.getInstance().VISIBLE_X_METERS, Environment.getInstance().GROUND_LEVEL, 28, 35));
        this.buldingsSizes.add(new Rectangle(Environment.getInstance().VISIBLE_X_METERS, Environment.getInstance().GROUND_LEVEL, 14, 80));
        this.buldingsSizes.add(new Rectangle(Environment.getInstance().VISIBLE_X_METERS, Environment.getInstance().GROUND_LEVEL, 15, 79));
        this.buldingsSizes.add(new Rectangle(Environment.getInstance().VISIBLE_X_METERS, Environment.getInstance().GROUND_LEVEL, 20, 68));
    }

}
