package com.rightovers.wave.map.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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

        this.body = Box2DObject.createBody(false, Box2DWorld.getInstance().getWorld(), BodyDef.BodyType.DynamicBody, 1f, 1f, 0f, position, listArray, 77, false);
    }

    private Vector2 removeOffset(ArrayList<Vector2> triangle) {
        float length = -1;
        Vector2 offset = null;
        for(Vector2 vec : triangle) {
            float currentLength = vec.dst(Vector2.Zero);

            if(vec.equals(offset)) {
                return null;
            }

            if(offset == null) {
                length = currentLength;
                offset = vec;
            } else {
                if(length > currentLength) {
                    offset = vec;
                    length = currentLength;
                }
            }

        }


        triangle.remove(offset);
        for(Vector2 vec : triangle) {
            vec.sub(offset);
        }

        Gdx.app.log("Offset: ", offset.toString());

        triangle.add(new Vector2(0, 0));

        return offset;
    }

    float angle = 0;
    public void draw() {
        float x = body.getPosition().x;
        float y = body.getPosition().y;





        float[] array = triangleVertices.clone();

        float angle = this.angle;//body.getAngle();
        this.angle = 90f;
        Matrix3 matrix = new Matrix3();

        //matrix.rotate(angle);

        //translate(array, x, y, 0, 1);
        //translate(array, x, y, 5, 6);
        //translate(array, x, y, 10, 11);
        //translate(array, x, y, 15, 16);

        applyTranformation(0, 1, matrix, angle, x + offset.x, y + offset.y, array);
        applyTranformation(5, 6, matrix, angle, x + offset.x, y + offset.y, array);
        applyTranformation(10, 11, matrix, angle, x + offset.x, y + offset.y, array);
        applyTranformation(15, 16, matrix, angle, x + offset.x, y + offset.y, array);

        Main.getInstance().batch.draw(this.obstacle, array, 0, array.length);
    }

    private void translate(float[] array, float x, float y, int xi, int yi) {
        array[xi] += x;
        array[yi] += y;
    }

    private void applyTranformation(int ix, int iy, Matrix3 mat, float angle, float x, float y, float[] array) {
        mat.idt();

        //mat.translate(offset.x, offset.y);
        //mat.rotate(new Vector3(0, 0, 1), angle);


        array[ix] = rotateX((float)Math.toRadians(angle), array[ix], array[iy], 0, 0);
        array[iy] = rotateY((float)Math.toRadians(angle), array[ix], array[iy], 0, 0);

        mat.translate(x, y);
        //array[ix] = multiplyX(array[ix], array[iy], mat);
        //array[iy] = multiplyY(array[ix], array[iy], mat);
        if(ix >= 15) {
            return;
        }


        //Vector3 ro = new Vector3();
        //ro.rotate();

    }


    private float multiplyX(float x, float y, Matrix3 mat) {
        return x * mat.val[0] + y * mat.val[3] + mat.val[6];
    }

    private float multiplyY(float x, float y, Matrix3 mat) {
        return x * mat.val[1] + y * mat.val[4] + mat.val[7];
    }


    //cos(theta) * (px-ox) - sin(theta) * (py-oy) + ox
    private float rotateX(float angle,float x,float y, float ox, float oy) {
        return (float)(Math.cos(angle) * (x - ox) - Math.sin(angle) * (y - (oy))) + ox;
    }
    //sin(theta) * (px-ox) + cos(theta) * (py-oy) + oy
    private float rotateY(float angle, float x, float y, float ox, float oy) {
        return (float)(Math.sin(angle)*(x - ox) + Math.cos(angle) * (y - oy)) + oy;
    }

    private float[] generateTriangleFromVertices(Texture texture, List<Vector2> triangle) {
        float u1, v1, u2, v2, u3, v3;
        u1 = triangle.get(0).x / texture.getWidth();
        v1 = triangle.get(0).y / texture.getHeight();

        u2 = triangle.get(1).x / texture.getWidth();
        v2 = triangle.get(1).y / texture.getHeight();

        u3 = triangle.get(2).x / texture.getWidth();
        v3 = triangle.get(2).y / texture.getHeight();


        float c = Color.WHITE.toFloatBits();

        float x1 = triangle.get(0).x;
        float y1 = triangle.get(0).y;

        float x2 = triangle.get(1).x;
        float y2 = triangle.get(1).y;

        float x3 = triangle.get(2).x;
        float y3 = triangle.get(2).y;

        offset = new Vector2(x1, y1);

        x1 = 0;
        y1 = 0;

        x2 -= offset.x;
        y2 -= offset.y;

        x3 -= offset.x;
        y3 -= offset.y;


        return new float[] {
                x1, y1, c, u1, v1,
                x2, y2, c, u2, v2,
                x3, y3, c, u3, v3,
                0, 0, c, 0, 0
        };
    }

    public Body getBody() {
        return body;
    }
}
