package com.rightovers.wave.map.obstacles;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.rightovers.wave.map.Box2DWorld;
import com.rightovers.wave.utils.Box2DObject;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

class ObstaclePhysics {

    Body box2DBody;

    public Body getBox2DBody() {
        return this.box2DBody;
    }

    public ObstaclePhysics(Obstacle.Type type, Rectangle rect) {
        // create box2d body
        ArrayList<ArrayList<Vector2>> fixtures = new ArrayList<ArrayList<Vector2>>();
        ArrayList<Vector2> fixture1 = new ArrayList<Vector2>();
        fixture1.add(new Vector2(0, 0));
        fixture1.add(new Vector2(rect.width, 0));
        fixture1.add(new Vector2(rect.width, rect.height));
        fixture1.add(new Vector2(0, rect.height));
        fixtures.add(fixture1);


        fixtures = separateQuad(fixture1, new Vector2(rect.width * 0.25f, (int)(rect.height * 0.3f)));
        this.box2DBody = Box2DObject.createBody(false, Box2DWorld.getInstance().getWorld(), BodyDef.BodyType.KinematicBody, 1f, 1f, 0f, new Vector2(rect.x, rect.y), fixtures, 0, false);
        //createBrokenBody(rect);

    }

    // Separates quad on four triangles
    private ArrayList<ArrayList<Vector2>>  separateQuad(List<Vector2> points, Vector2 newPoint) {
        ArrayList<ArrayList<Vector2>> fixtures = new ArrayList<ArrayList<Vector2>>();

        int iterationsNumber = 3;

        ArrayList<Vector2> fixture1 = new ArrayList<Vector2>();
        fixture1.add(points.get(0));
        fixture1.add(points.get(1));
        fixture1.add(newPoint);

        separateTriangle(fixture1, newPoint, fixtures, iterationsNumber);


        ArrayList<Vector2> fixture2 = new ArrayList<Vector2>();
        fixture2.add(points.get(1));
        fixture2.add(points.get(2));
        fixture2.add(newPoint);

        separateTriangle(fixture2, newPoint, fixtures, iterationsNumber);


        ArrayList<Vector2> fixture3 = new ArrayList<Vector2>();
        fixture3.add(points.get(2));
        fixture3.add(points.get(3));
        fixture3.add(newPoint);

        separateTriangle(fixture3, newPoint, fixtures, iterationsNumber);


        ArrayList<Vector2> fixture4 = new ArrayList<Vector2>();
        fixture4.add(points.get(3));
        fixture4.add(points.get(0));
        fixture4.add(newPoint);

        separateTriangle(fixture4, newPoint, fixtures, iterationsNumber);

        return fixtures;

    }
    // Separates triangle on two smaller triangles
    private void separateTriangle(List<Vector2> points, Vector2 newPoint, ArrayList<ArrayList<Vector2>> fixtures, int times) {
        times--;

        Random random = new Random();
        Vector2 middlePoint = null;
        float randomNumber = (random.nextFloat()) - 1;
        if(points.get(0).x == points.get(1).x) {
             middlePoint = new Vector2((points.get(0).x), ((points.get(0).y + points.get(1).y) / 2) + randomNumber);
        }else{
             middlePoint = new Vector2(((points.get(0).x + points.get(1).x) / 2) + randomNumber, points.get(0).y);
        }



        ArrayList fixture1 = new ArrayList();
        fixture1.add(points.get(0));
        fixture1.add(middlePoint);
        fixture1.add(newPoint);
        if(times <= 1) {
            fixtures.add(fixture1);
        }else{
            separateTriangle(fixture1, newPoint, fixtures, times);
        }

        ArrayList fixture2 = new ArrayList();
        fixture2.add(middlePoint);
        fixture2.add(points.get(1));
        fixture2.add(newPoint);

        if(times <= 1) {
            fixtures.add(fixture2);
        }else{
            separateTriangle(fixture2, newPoint, fixtures, times);
        }

    }

    public void update(float delta) {

    }

    public void destroy() {
        Box2DWorld.getInstance().getWorld().destroyBody(box2DBody);
    }

    public void remove() {
        Box2DWorld.getInstance().world.destroyBody(this.box2DBody);
        this.box2DBody = null;
    }


}
