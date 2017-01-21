package com.rightovers.wave.map.obstacles;

<<<<<<< HEAD
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.rightovers.wave.map.Box2DWorld;
import com.rightovers.wave.map.Environment;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.rightovers.wave.Main;
=======
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.rightovers.wave.Main;
import com.rightovers.wave.map.Environment;
>>>>>>> ee046f705a902909b6dc9e832b62e3dd99f2030f
import com.rightovers.wave.utils.Loader;


class Obstacle {

    public static final String BUILDING_TEXTURE = "images/building.png";

    private Texture building = null;

    public enum Type {
        BIG,
        SMALL
    }

    Type type;
    ObstaclePhysics physics;

    public Body getBox2DBody() {
        return this.physics.box2DBody;
    }

<<<<<<< HEAD
    // spawn clouds/asteroids
=======
>>>>>>> ee046f705a902909b6dc9e832b62e3dd99f2030f
    public Obstacle(Type type, Rectangle rectangle) {
        this.type = type;
        this.physics = new ObstaclePhysics(type, rectangle);
    }

    public static void loadAssets() {
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, BUILDING_TEXTURE, Loader.AssetType.TEXTURE);
    }

    public void update(float delta) {
        this.physics.update(delta);
        getBox2DBody().setTransform(getBox2DBody().getPosition().x + Environment.BACKGROUND_MOVE_SPEED, getBox2DBody().getPosition().y, 0);

    }

    public void drawBackground(float delta) {
<<<<<<< HEAD
        if(building == null) {
            building = Loader.getInstance().getTexture(BUILDING_TEXTURE);
        }

        Main.getInstance().batch.draw(building, getBox2DBody().getPosition().x, getBox2DBody().getPosition().y, building.getWidth(), building.getHeight());
=======
        if (this.building == null) {
            this.building = Loader.getInstance().getTexture(BUILDING_TEXTURE);
        }

        Main.getInstance().batch.draw(this.building, getBox2DBody().getPosition().x, getBox2DBody().getPosition().y, this.building.getWidth(), this.building.getHeight());
>>>>>>> ee046f705a902909b6dc9e832b62e3dd99f2030f

    }

    public void drawForeground(float delta) {

    }

    public void destroy() {
<<<<<<< HEAD
        physics.destroy();
=======
        this.physics.destroy();
>>>>>>> ee046f705a902909b6dc9e832b62e3dd99f2030f
    }

    public Type getType() {
        return this.type;
    }

    public Vector2 getPosition() {
        return this.getBox2DBody().getPosition();
    }

}
