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
        generator = ObstacleGenerator.getInstance();
    }


    public void drawBackground(float deltaTime) {
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
            }
        }

        drawTextureSequence(background, backgroundPositions);
        drawTextureSequence(ground, groundPositions);

    }

    private void drawTextureSequence(Texture texture, List<Rectangle> positions) {
        boolean shouldMoveFirst = false;
        for(Rectangle currentPosition : positions) {
            Main.getInstance().batch.draw(texture, currentPosition.x, currentPosition.y,
                    currentPosition.getWidth(), currentPosition.getHeight());
            currentPosition.x += BACKGROUND_MOVE_SPEED;

            if(currentPosition.x + currentPosition.getWidth() < 0) {
                Rectangle last = positions.get(positions.size() - 1);
                currentPosition.x = last.x + last.getWidth();
                shouldMoveFirst = true;
            }
        }

        if(shouldMoveFirst) {
            Rectangle rec = positions.remove(0);
            positions.add(rec);
        }

    }

    public void drawObstacles(float delta) {
        generator.draw(delta);
    }

    public void drawForeground(float deltaTime) {

    }

    public void update(float delta) {

    }

    public float getGroundLevel() {
        return ground.getHeight();
    }

}
