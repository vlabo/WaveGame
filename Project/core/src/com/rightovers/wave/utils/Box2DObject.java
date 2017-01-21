package com.rightovers.wave.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;


enum BodyTypeEnum {
    STATIC,
    DYNAMIC,
    KINEMATIC
}

public class Box2DObject {
    public static Body createBody(boolean isChainShape, World world, BodyType bodyType, float density, float friction, float restitution, Vector2 pos, ArrayList<ArrayList<Vector2>> fixturesPoints, int gameObjectId) {

        return createBody(isChainShape, world, bodyType, density, friction, restitution, pos, fixturesPoints, gameObjectId, true);
    }

    public static Body createBody(boolean isChainShape, World world, BodyType bodyType, float density, float friction, float restitution, Vector2 pos, ArrayList<ArrayList<Vector2>> fixturesPoints, int gameObjectId, boolean isSolid) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = bodyType;
        bodyDef.position.x = pos.x;
        bodyDef.position.y = pos.y;


        Body box2DBody = world.createBody(bodyDef);
        box2DBody.setSleepingAllowed(false);

        box2DBody.setUserData(gameObjectId);


        for (ArrayList<Vector2> points : fixturesPoints) {
            FixtureDef fixtureDef = new FixtureDef();

            fixtureDef.density = density;
            fixtureDef.restitution = restitution;
            fixtureDef.friction = friction;
            if (isSolid == false) {
                fixtureDef.isSensor = true;
            }

            Vector2[] vector2Points;

            if (!isChainShape) {
                // create polygon
                PolygonShape polygonShape = new PolygonShape();

                // collect points
                vector2Points = points.toArray(new Vector2[points.size()]);

                // set fixture shape
                polygonShape.set(vector2Points);
                fixtureDef.shape = polygonShape;
            }
            else {
                ChainShape chainShape = new ChainShape();
                // collect points
                vector2Points = points.toArray(new Vector2[points.size()]);
                chainShape.createChain(vector2Points);
                fixtureDef.shape = chainShape;
            }
            box2DBody.createFixture(fixtureDef);
            box2DBody.setBullet(true);
        }


        // attach fixture to body
        box2DBody.resetMassData();

        return box2DBody;
    }

    public static void destroyBody(World world, Body box2DBody) {
        world.destroyBody(box2DBody);
    }


}
