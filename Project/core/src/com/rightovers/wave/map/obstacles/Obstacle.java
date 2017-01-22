package com.rightovers.wave.map.obstacles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.rightovers.wave.Main;
import com.rightovers.wave.map.Environment;
import com.rightovers.wave.player.Player;

import java.util.ArrayList;
import java.util.List;


class Obstacle {

    private List<ObstacleParticle> particles;
    private Vector2 startPosition = null;

    public enum Type {
        BIG,
        SMALL
    }

    Type type;
    ObstaclePhysics physics;
    boolean firstTime = true;

    boolean isExploded = false;

    Texture texture;

    public Texture getTexture() {
        return this.texture;
    }

    public void setTexture(Texture textureRegion) {
        this.texture = textureRegion;
    }


    public Body getBox2DBody() {
        return this.physics.box2DBody;
    }

    // spawn clouds/asteroids
    public Obstacle(Type type, Rectangle rectangle) {
        this.type = type;
        this.physics = new ObstaclePhysics(type, rectangle);
        this.startPosition = new Vector2(rectangle.x, rectangle.y);

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

        return new float[]{x, y, c, u, v, x, y + h, c, u, v2, x + w, y, c, u2, v, x, y, c, u, v};
    }

    public static void loadAssets() {
    }

    public void update(float delta) {
        this.physics.update(delta);
        //getBox2DBody().setTransform(getBox2DBody().getPosition().x + Environment.BACKGROUND_MOVE_SPEED, getBox2DBody().getPosition().y, 0);

    }

    public void drawBackground(float delta) {
        if (this.firstTime == true) {
            if (this.texture == null) {
                // return;
            }

            this.firstTime = false;
        }



        if(!isExploded) {
            Main.getInstance().batch.draw(this.texture, (getBox2DBody().getPosition().x - Player.getInstance().getDistance()) * Environment.getInstance().getScaleRatio(), getBox2DBody().getPosition().y, this.physics.getRect().width * Environment.getInstance().getScaleRatio(), this.physics.getRect().height * Environment.getInstance().getScaleRatio());
        }else{
            if (this.particles != null) {
                for (ObstacleParticle particle : this.particles) {
                    particle.draw();
                }
            }
        }


    }

    public void explode() {
        isExploded = true;
        this.particles = new ArrayList<ObstacleParticle>();
        for (ArrayList<Vector2> triangle : this.physics.getTriangles()) {
            ObstacleParticle p = new ObstacleParticle(this.texture, triangle, new Vector2(this.startPosition.x, this.startPosition.y));
            this.particles.add(p);
        }
    }

    public void drawForeground(float delta) {

    }

    public void destroy() {
        this.physics.destroy();
        if(particles != null) {
            for (ObstacleParticle particle : particles) {
                particle.destroy();
            }

        }
    }

    public Type getType() {
        return this.type;
    }

    public Vector2 getPosition() {
        return new Vector2(0, 0); //this.getBox2DBody().getPosition();
    }

}
