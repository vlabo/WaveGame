package com.rightovers.wave.map.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.rightovers.wave.Main;
import com.rightovers.wave.player.Player;
import com.rightovers.wave.utils.Loader;

import net.dermetfan.gdx.graphics.g2d.Box2DPolygonSprite;

import java.util.ArrayList;
import java.util.List;


class Obstacle {

    public static final String BUILDING_TEXTURE = "images/building.png";

    private Texture building = null;

    private List<float[]> triangles;

    public enum Type {
        BIG,
        SMALL
    }

    Type type;
    ObstaclePhysics physics;

    public Body getBox2DBody() {
        return this.physics.box2DBody;
    }

    // spawn clouds/asteroids
    public Obstacle(Type type, Rectangle rectangle) {
        this.type = type;
        this.physics = new ObstaclePhysics(type, rectangle);

        triangles = new ArrayList<float[]>(1);
        TextureRegion region = new TextureRegion();

    }

    public static void loadAssets() {
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, BUILDING_TEXTURE, Loader.AssetType.TEXTURE);
    }

    public void update(float delta) {
        this.physics.update(delta);
        //getBox2DBody().setTransform(getBox2DBody().getPosition().x + Environment.BACKGROUND_MOVE_SPEED, getBox2DBody().getPosition().y, 0);

    }

    public void drawBackground(float delta) {
        if (this.building == null) {
            this.building = Loader.getInstance().getTexture(BUILDING_TEXTURE);
        }

        Main.getInstance().batch.draw(this.building, getBox2DBody().getPosition().x - Player.getInstance().getDistance(), getBox2DBody().getPosition().y, this.building.getWidth(), this.building.getHeight());

    }

    public void drawForeground(float delta) {

    }

    public void destroy() {
        this.physics.destroy();
    }

    public Type getType() {
        return this.type;
    }

    public Vector2 getPosition() {
        return this.getBox2DBody().getPosition();
    }

}
