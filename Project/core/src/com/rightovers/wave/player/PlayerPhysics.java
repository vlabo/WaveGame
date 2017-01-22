package com.rightovers.wave.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.rightovers.wave.map.Box2DWorld;
import com.rightovers.wave.map.Environment;
import com.rightovers.wave.screens.GameScreen;
import com.rightovers.wave.utils.Box2DObject;
import com.rightovers.wave.utils.Funcs;

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
        fixture1.add(new Vector2(0, 50));
        fixture1.add(new Vector2(50, 50));
        fixture1.add(new Vector2(50, 0));
        fixtures.add(fixture1);
        this.box2DBody = Box2DObject.createBody(false, Box2DWorld.getInstance().getWorld(), BodyDef.BodyType.KinematicBody, 1f, 1f, 0f, new Vector2(Environment.getInstance().VISIBLE_X_METERS / 2, 10), fixtures, -1, true);

    }

    public void update(float delta) {
        this.box2DBody.setLinearVelocity(45, 0);
        Funcs.print(this.box2DBody.getPosition());
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
        }
        // bad
        else {
            Player.getInstance().die();
        }
    }

}
