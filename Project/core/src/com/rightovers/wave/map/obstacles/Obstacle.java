package com.rightovers.wave.map.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.utils.Array;
import com.rightovers.wave.Main;
import com.rightovers.wave.player.Player;
import com.rightovers.wave.utils.Loader;

import net.dermetfan.gdx.graphics.g2d.Box2DPolygonSprite;

import com.badlogic.gdx.graphics.Color;

import java.util.Arrays;
import java.util.List;

import java.util.List;
import java.util.ArrayList;

class Obstacle {

    public static final String BUILDING_TEXTURE = "images/building.png";

    private Texture building = null;

    private List<ObstacleParticle> particles;
    private Vector2 startPosition = null;

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
        startPosition = new Vector2(rectangle.x, rectangle.y);

        //this.building = Loader.getInstance().getTexture(BUILDING_TEXTURE);

    }



    protected float[] createTriangle(int x, int y, int width, int height) {
        float w = width;
        float h = height;

        float c = Color.WHITE.toFloatBits();
        float u = 0;
        float v = 0;
        float u2 = 1;
        float v2 = 1;

        return new float[] {
                x, y, c, u, v,
                x, y+h, c, u, v2,
                x+w, y, c, u2, v,
                x, y, c, u, v
        };
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

            particles = new ArrayList<ObstacleParticle>();
            for(ArrayList<Vector2> triangle : this.physics.getTriangles()){
                ObstacleParticle p = new ObstacleParticle(this.building, triangle, new Vector2(startPosition.x, startPosition.y));
                particles.add(p);
                p.getBody().applyForceToCenter(new Vector2(10000, 10000), true);
                p.getBody().applyAngularImpulse(10000000, true);
            }



        }

        //Main.getInstance().batch.draw(this.building, getBox2DBody().getPosition().x - Player.getInstance().getDistance(), getBox2DBody().getPosition().y, this.building.getWidth(), this.building.getHeight());

        if(particles != null) {
            for (ObstacleParticle particle : particles) {
                particle.draw();
            }
        }


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
        return new Vector2(0, 0);//this.getBox2DBody().getPosition();
    }

}
