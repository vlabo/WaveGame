package com.rightovers.wave.map.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.rightovers.wave.Main;
import com.rightovers.wave.map.Box2DWorld;
import com.rightovers.wave.utils.Box2DObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cthulhu on 1/21/17.
 */

public class ObstacleParticle {

    private Body body = null;
    private Texture obstacle = null;
    private float[] triangleVertices = null;
    private Vector2 offset = null;

    public ObstacleParticle(Texture texture, ArrayList<Vector2> triangle, Vector2 position) {
        this.obstacle = texture;

        ArrayList<ArrayList<Vector2>> listArray = new ArrayList<ArrayList<Vector2>>(1);
        listArray.add(triangle);

        triangleVertices = generateTriangleFromVertices(texture, triangle);

        this.body = Box2DObject.createBody(false, Box2DWorld.getInstance().getWorld(), BodyDef.BodyType.DynamicBody, 1f, 1f, 0f, position, listArray, 77, true);
    }

    float angle = 0;
    public void draw() {
        float x = body.getPosition().x;
        float y = body.getPosition().y;

        float[] array = triangleVertices.clone();

        float angle = body.getAngle();
        this.angle += 0.01f;
        Matrix3 matrix = new Matrix3();

        applyTransformation(0, 1, matrix, angle, x + offset.x, y + offset.y, array);
        applyTransformation(5, 6, matrix, angle, x + offset.x, y + offset.y, array);
        applyTransformation(10, 11, matrix, angle, x + offset.x, y + offset.y, array);
        applyTransformation(15, 16, matrix, angle, x + offset.x, y + offset.y, array);

        Main.getInstance().batch.draw(this.obstacle, array, 0, array.length);
    }

    private void applyTransformation(int ix, int iy, Matrix3 mat, float angle, float x, float y, float[] array) {
        mat.idt();
        float cos = MathUtils.cos(angle);
        float sin = MathUtils.sin(angle);

        float newX = array[ix] * cos - array[iy] * sin;
        float newY = array[ix] * sin + array[iy] * cos;


        array[ix] = newX;
        array[iy] = newY;

        array[ix] += x;
        array[iy] += y;

    }

    private float[] generateTriangleFromVertices(Texture texture, List<Vector2> triangle) {
        float u1, v1, u2, v2, u3, v3;


        float c = Color.WHITE.toFloatBits();

        int first = 0;
        int second = 1;
        int third = 2;


        float x1 = triangle.get(first).x;
        float y1 = triangle.get(first).y;

        float x2 = triangle.get(second).x;
        float y2 = triangle.get(second).y;

        float x3 = triangle.get(third).x;
        float y3 = triangle.get(third).y;


        u1 = triangle.get(first).x / texture.getWidth();
        v1 = triangle.get(first).y / texture.getHeight();

        u2 = triangle.get(second).x / texture.getWidth();
        v2 = triangle.get(second).y / texture.getHeight();

        u3 = triangle.get(third).x / texture.getWidth();
        v3 = triangle.get(third).y / texture.getHeight();




        offset = new Vector2(0, 0);

        return new float[] {
                x1, y1, c, u1, v1,
                x2, y2, c, u2, v2,
                x3, y3, c, u3, v3,
                x3, y3, c, u3, v3
        };
    }

    public Body getBody() {
        return body;
    }
}
