package com.rightovers.wave.map.obstacles;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.rightovers.wave.map.Box2DWorld;
import com.rightovers.wave.utils.Box2DObject;

import java.util.ArrayList;

class ObstaclePhysics {

    Body box2DBody;

    public Body getBox2DBody() {
        return this.box2DBody;
    }

    // spawn clouds/asteroids
    public ObstaclePhysics(Obstacle.Type type, Vector2 pos) {
        // create box2d body
        ArrayList<ArrayList<Vector2>> fixtures = new ArrayList<ArrayList<Vector2>>();
        ArrayList<Vector2> fixture1 = new ArrayList<Vector2>();
        fixture1.add(new Vector2(0, 0));
        fixture1.add(new Vector2(1, 1));
        fixture1.add(new Vector2(2, 0));
        fixtures.add(fixture1);
        this.box2DBody = Box2DObject.createBody(false, Box2DWorld.getInstance().getWorld(), BodyDef.BodyType.KinematicBody, 1f, 1f, 0f, pos, fixtures, 0, false);

    }

    public void update(float delta) {

    }

    public void remove() {
        Box2DWorld.getInstance().world.destroyBody(this.box2DBody);
        this.box2DBody = null;
    }


}
