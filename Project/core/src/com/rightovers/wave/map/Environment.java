package com.rightovers.wave.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.rightovers.wave.Main;
import com.rightovers.wave.map.obstacles.ObstacleGenerator;
import com.rightovers.wave.utils.Loader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evkalipt on 1/20/2017.
 */

public class Environment {

    private static final String BACKGROUND_IMAGE = "images/background.png";
    private static final String GROUND_IMAGE = "images/ground.png";

    public static float BACKGROUND_MOVE_SPEED = -5;

    private Texture background = null;
    private Texture ground = null;

    private float backgroundRatio = 1;
    private float groundRatio = 1;
    private ObstacleGenerator generator = null;


<<<<<<< HEAD

=======
>>>>>>> ee046f705a902909b6dc9e832b62e3dd99f2030f
    private List<Rectangle> backgroundPositions = null;
    private List<Rectangle> groundPositions = null;


    public static Environment getInstance() {
        if (Main.getInstance().instances.get(Environment.class) == null) {
            Main.getInstance().instances.put(Environment.class, new Environment());
        }
        return (Environment) Main.getInstance().instances.get(Environment.class);

    }

    public void loadAssets() {
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, BACKGROUND_IMAGE, Loader.AssetType.TEXTURE);
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, GROUND_IMAGE, Loader.AssetType.TEXTURE);
<<<<<<< HEAD
        generator = ObstacleGenerator.getInstance();
=======
        this.generator = ObstacleGenerator.getInstance();
>>>>>>> ee046f705a902909b6dc9e832b62e3dd99f2030f
    }


    public void drawBackground(float deltaTime) {

<<<<<<< HEAD
        drawTextureSequence(background, backgroundPositions);
        drawTextureSequence(ground, groundPositions);
=======
        //drawTextureSequence(background, backgroundPositions);
        //drawTextureSequence(ground, groundPositions);
>>>>>>> ee046f705a902909b6dc9e832b62e3dd99f2030f

    }

    private void drawTextureSequence(Texture texture, List<Rectangle> positions) {
        boolean shouldMoveFirst = false;
<<<<<<< HEAD
        for(Rectangle currentPosition : positions) {
            Main.getInstance().batch.draw(texture, currentPosition.x, currentPosition.y,
                    currentPosition.getWidth(), currentPosition.getHeight());
            currentPosition.x += BACKGROUND_MOVE_SPEED;

            if(currentPosition.x + currentPosition.getWidth() < 0) {
=======
        for (Rectangle currentPosition : positions) {
            Main.getInstance().batch.draw(texture, currentPosition.x, currentPosition.y, currentPosition.getWidth(), currentPosition.getHeight());
            currentPosition.x += BACKGROUND_MOVE_SPEED;

            if (currentPosition.x + currentPosition.getWidth() < 0) {
>>>>>>> ee046f705a902909b6dc9e832b62e3dd99f2030f
                Rectangle last = positions.get(positions.size() - 1);
                currentPosition.x = last.x + last.getWidth();
                shouldMoveFirst = true;
            }
        }

<<<<<<< HEAD
        if(shouldMoveFirst) {
=======
        if (shouldMoveFirst) {
>>>>>>> ee046f705a902909b6dc9e832b62e3dd99f2030f
            Rectangle rec = positions.remove(0);
            positions.add(rec);
        }

    }

    public void drawObstacles(float delta) {
<<<<<<< HEAD
        generator.draw(delta);
=======
        this.generator.draw(delta);
>>>>>>> ee046f705a902909b6dc9e832b62e3dd99f2030f
    }

    public void drawForeground(float deltaTime) {

    }

    public void update(float delta) {
<<<<<<< HEAD
        if(background == null) {
            background = Loader.getInstance().getTexture(BACKGROUND_IMAGE);
            backgroundPositions = new ArrayList<Rectangle>();
            backgroundRatio = (float)background.getHeight() / (float)Main.getInstance().height;
            int backgroundFitTimes = (int)(Main.getInstance().width / backgroundRatio) + 2;
            for(int i = 0; i < backgroundFitTimes; i++) {
                backgroundPositions.add(new Rectangle(i * (background.getWidth() / backgroundRatio), 0, background.getWidth() / backgroundRatio , background.getHeight() / backgroundRatio));
            }
        }

        if(ground == null) {
            ground = Loader.getInstance().getTexture(GROUND_IMAGE);
            groundPositions = new ArrayList<Rectangle>();
            int groundFitTimes = (int)(Main.getInstance().width / ground.getWidth()) + 2;
            for(int i = 0; i < groundFitTimes;i++) {
                groundPositions.add(new Rectangle(i * ground.getWidth(), 0, ground.getWidth(), ground.getHeight()));
=======
        if (1 == 1) {
            return;
        }
        if (this.background == null) {
            this.background = Loader.getInstance().getTexture(BACKGROUND_IMAGE);
            this.backgroundPositions = new ArrayList<Rectangle>();
            this.backgroundRatio = (float) this.background.getHeight() / (float) Main.getInstance().height;
            int backgroundFitTimes = (int) (Main.getInstance().width / this.backgroundRatio) + 2;
            for (int i = 0; i < backgroundFitTimes; i++) {
                this.backgroundPositions.add(new Rectangle(i * (this.background.getWidth() / this.backgroundRatio), 0, this.background.getWidth() / this.backgroundRatio, this.background.getHeight() / this.backgroundRatio));
            }
        }

        if (this.ground == null) {
            this.ground = Loader.getInstance().getTexture(GROUND_IMAGE);
            this.groundPositions = new ArrayList<Rectangle>();
            int groundFitTimes = (int) (Main.getInstance().width / this.ground.getWidth()) + 2;
            for (int i = 0; i < groundFitTimes; i++) {
                this.groundPositions.add(new Rectangle(i * this.ground.getWidth(), 0, this.ground.getWidth(), this.ground.getHeight()));
>>>>>>> ee046f705a902909b6dc9e832b62e3dd99f2030f
            }
        }

    }

    public float getGroundLevel() {
<<<<<<< HEAD
        return ground.getHeight();
=======
        return 10;
        //return this.ground.getHeight();
>>>>>>> ee046f705a902909b6dc9e832b62e3dd99f2030f
    }

}
