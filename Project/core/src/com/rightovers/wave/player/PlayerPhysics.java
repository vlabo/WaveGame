package com.rightovers.wave.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.rightovers.wave.map.Box2DWorld;
import com.rightovers.wave.map.Environment;
import com.rightovers.wave.screens.GameScreen;
import com.rightovers.wave.utils.Box2DObject;

import java.util.ArrayList;

class PlayerPhysics {

    Body box2DBody;

    public Body getBox2DBody() {
        return this.box2DBody;
    }

    // spawn clouds/asteroids
    public PlayerPhysics() {
        // create box2d body
        ArrayList<ArrayList<Vector2>> fixtures = new ArrayList<ArrayList<Vector2>>();
        ArrayList<Vector2> fixture1 = new ArrayList<Vector2>();
        fixture1.add(new Vector2(0, 0));
        fixture1.add(new Vector2(50, 25));
        fixture1.add(new Vector2(0, 50));
        fixtures.add(fixture1);
        this.box2DBody = Box2DObject.createBody(false, Box2DWorld.getInstance().getWorld(), BodyDef.BodyType.KinematicBody, 1f, 1f, 0f, new Vector2(Environment.getInstance().VISIBLE_X_METERS / 10.5f, -8f), fixtures, -1, true);
        this.box2DBody.getFixtureList().get(0).setDensity(50);
    }

    public void update(float delta) {
        this.box2DBody.setLinearVelocity(Player.getInstance().getSpeed() * Environment.getInstance().getScaleRatio(), 0);
    }

    public void remove() {
        Box2DWorld.getInstance().world.destroyBody(this.box2DBody);
        this.box2DBody = null;
    }

    public void onCollision() {
        // TODO check strength and direction

        // good
        if (Player.getInstance().getStrength() > 0.5f) {
            GameScreen.getInstance().buildingsDestroyed++;
            // slow down
            Player.getInstance().setSpeed(Player.getInstance().getSpeed() * 0.8f);
        }
        // bad
        else {
            Player.getInstance().die();
        }
    }

}
