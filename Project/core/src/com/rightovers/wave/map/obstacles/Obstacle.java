package com.rightovers.wave.map.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.rightovers.wave.Main;
import com.rightovers.wave.utils.Loader;

class Obstacle {

    private static final String BUILDING_TEXTURE = "images/building.png";

    private Texture building = null;

    public enum Type {
        BIG,
        SMALL
    }

    Type type;
    ObstaclePhysics physics;
    Vector2 position;

    public Body getBox2DBody() {
        return this.physics.box2DBody;
    }

    // spawn clouds/asteroids
    public Obstacle(Type type, Vector2 pos) {
        this.type = type;
        this.physics = new ObstaclePhysics(type, pos);
        this.position = pos;
    }

    public static void loadAssets() {
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, BUILDING_TEXTURE, Loader.AssetType.TEXTURE);
    }

    public void update(float delta) {
        this.physics.update(delta);
    }

    public void drawBackground(float delta) {
        if(building == null) {
            building = Loader.getInstance().getTexture(BUILDING_TEXTURE);
        }

        Main.getInstance().batch.draw(building, position.x, position.y, building.getWidth(), building.getHeight());

    }

    public void drawForeground(float delta) {

    }

    public Type getType() {
        return this.type;
    }

    public Vector2 getPosition() {
         return position;
    }

}
