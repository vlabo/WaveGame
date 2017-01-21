package com.rightovers.wave.map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.rightovers.wave.Main;
import com.rightovers.wave.utils.Box2DObject;

import java.util.ArrayList;

public class Box2DWorld {

    public static Box2DWorld getInstance() {
        if (Main.getInstance().instances.get(Box2DWorld.class) == null) {
            Main.getInstance().instances.put(Box2DWorld.class, new Box2DWorld());
        }
        return (Box2DWorld) Main.getInstance().instances.get(Box2DWorld.class);
    }

    private Box2DDebugRenderer debugRenderer;
    private Matrix4 debugMatrix;
    public World world;

    public void setup(Camera camera, float density) {
        this.debugRenderer = new Box2DDebugRenderer();
        this.debugMatrix = new Matrix4(camera.combined.cpy());
        this.debugMatrix.scale(density, density, 1f);

        this.world = new World(new Vector2(0, 0), true);
        this.world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                int bodyAId = (Integer) contact.getFixtureA().getBody().getUserData();
                int bodyBId = (Integer) contact.getFixtureB().getBody().getUserData();

            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void postSolve(Contact arg0, ContactImpulse arg1) {
            }

            @Override
            public void preSolve(Contact arg0, Manifold arg1) {
            }
        });


        // test object
        ArrayList<ArrayList<Vector2>> fixtures = new ArrayList<ArrayList<Vector2>>();
        ArrayList<Vector2> fixture1 = new ArrayList<Vector2>();
        fixture1.add(new Vector2(0, 0));
        fixture1.add(new Vector2(0, 100));
        fixtures.add(fixture1);
        Body bottom = Box2DObject.createBody(true, this.world, BodyDef.BodyType.StaticBody, 1, 1, 0, new Vector2(0, 0), fixtures, -1);
    }

    public World getWorld() {
        return this.world;
    }

    public void update(float deltaTime) {
        this.world.step(deltaTime, 8, 3);
    }

    public void draw(float deltaTime) {
        this.debugMatrix = new Matrix4(Main.getInstance().camera.combined.cpy());
        //this.debugMatrix.scale(Main.getInstance().density, Main.getInstance().density, 1);
        this.debugRenderer.render(this.world, this.debugMatrix);
    }

    public boolean interactionBetween(int bodyAId, int bodyBId, int neededObj1Id, int neededObj2Id) {
        if ((bodyAId == neededObj1Id || bodyAId == neededObj2Id) && (bodyBId == neededObj1Id || bodyBId == neededObj2Id)) {
            return true;
        }

        return false;
    }


}
