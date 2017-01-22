package com.rightovers.wave.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.rightovers.wave.Main;
import com.rightovers.wave.map.obstacles.ObstacleGenerator;
import com.rightovers.wave.player.Player;
import com.rightovers.wave.utils.IResourceable;
import com.rightovers.wave.utils.Loader;

import java.util.ArrayList;
import java.util.List;


public class Environment implements IResourceable {

    private static final String BACKGROUND_IMAGE = "images/background.png";
    private static final String GROUND_IMAGE = "images/ground.png";
    private static final String BUILDINGS = "images/buildings.pack";

    private Texture background = null;
    private Texture ground = null;

    private float backgroundRatio = 1;
    private float groundRatio = 1;
    private ObstacleGenerator generator = null;


    private List<Rectangle> backgroundPositions = null;
    private List<Rectangle> groundPositions = null;

    private float lastDistance = 0;


    public static Environment getInstance() {
        if (Main.getInstance().instances.get(Environment.class) == null) {
            Main.getInstance().instances.put(Environment.class, new Environment());
        }
        return (Environment) Main.getInstance().instances.get(Environment.class);

    }

    public void loadAssets() {
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, BACKGROUND_IMAGE, Loader.AssetType.TEXTURE);
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, GROUND_IMAGE, Loader.AssetType.TEXTURE);
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, BUILDINGS, Loader.AssetType.TEXTURE_ATLAS);

        this.generator = ObstacleGenerator.getInstance();
    }


    public void drawBackground(float deltaTime) {

        drawTextureSequence(this.background, this.backgroundPositions, Player.getInstance().getDistance() / 2 - this.lastDistance / 2);
        drawTextureSequence(this.ground, this.groundPositions, Player.getInstance().getDistance() - this.lastDistance);

        this.lastDistance = Player.getInstance().getDistance();

    }

    private void drawTextureSequence(Texture texture, List<Rectangle> positions, float speed) {
        boolean shouldMoveFirst = false;

        for (Rectangle currentPosition : positions) {
            currentPosition.x -= speed;
            Main.getInstance().batch.draw(texture, currentPosition.x, currentPosition.y, currentPosition.getWidth(), currentPosition.getHeight());

            if (currentPosition.x + currentPosition.getWidth() < 0) {
                Rectangle last = positions.get(positions.size() - 1);
                currentPosition.x = last.x + last.getWidth();
                shouldMoveFirst = true;
            }
        }

        if (shouldMoveFirst) {
            Rectangle rec = positions.remove(0);
            positions.add(rec);
        }

    }

    public void drawObstacles(float delta) {
        this.generator.draw(delta);
    }

    public void drawForeground(float deltaTime) {

    }

    public void update(float delta) {
        if (this.background == null) {
            this.background = Loader.getInstance().getTexture(BACKGROUND_IMAGE);
            this.backgroundPositions = new ArrayList<Rectangle>();
            this.backgroundRatio = (float) this.background.getHeight() / (float) Main.getInstance().height;

            int backgroundFitTimes = (int) (Main.getInstance().width * this.backgroundRatio / this.background.getHeight()) + 2;
            Gdx.app.log("ENV", this.backgroundRatio + "  " + backgroundFitTimes);
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
            }
        }

    }

    public float getGroundLevel() {
        return this.ground.getHeight();
    }

}
