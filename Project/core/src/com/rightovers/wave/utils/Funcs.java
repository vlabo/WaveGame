package com.rightovers.wave.utils;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class Funcs {
    public static void setScreen(Screen screen) {
        ((Game) Gdx.app.getApplicationListener()).setScreen(screen);
    }

    /**
     * Random float number
     */
    public static float random(float min, float max) {
        return min + (float) (Math.random() * ((max - min)));
    }

    /**
     * Random int number
     */
    public static int random(int min, int max) {
        return (int) (min + (int) (Math.random() * ((max - min) + 1)));
    }

    public static int randomWeight(ArrayList<Integer> numbers) {
        int sum_of_weight = 0;
        for (int weight : numbers) {
            sum_of_weight += weight;
        }

        int rnd = Funcs.random(0, sum_of_weight);

        for (int i = 0; i < numbers.size(); i++) {
            if (rnd < numbers.get(i)) {
                return i;
            }
            rnd -= numbers.get(i);
        }

        return numbers.get(Funcs.random(0, sum_of_weight));

    }

    /**
     * bullshit
     */
    public static float format(float number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        decimalFormat.setDecimalFormatSymbols(dfs);

        return Float.parseFloat(decimalFormat.format(number));
    }

    /**
     * example formats:
     * "#,###,###,##0.00" - some long decimal (dont use it)
     * "0.00" - x.xx float
     */
    public static float formatNumber(float number, String format) {
        DecimalFormat df2 = new DecimalFormat(format, new DecimalFormatSymbols(Locale.US));
        return new Float(df2.format(number));
    }


    public static void print(Object... objects) {
        for (Object object : objects) {
            print(object);
        }
    }

    public static void print(Object object) {
        try {
            print((String) object);
        }
        catch (Exception e) {
            try {
                print((Map<?, ?>) object);
            }
            catch (Exception e1) {
                if (Variables.mode == Variables.Modes.PRODUCTION) {
                    return;
                }
                if (Gdx.app.getType() == ApplicationType.Desktop) {
                    try {
                        System.out.println(ToStringBuilder.reflectionToString(object, ToStringStyle.MULTI_LINE_STYLE));
                    }
                    catch (Exception e2) {
                    }
                }
                else if (Gdx.app.getType() == ApplicationType.Android) {
                    return;
                }
            }

        }


    }

    private static void print(Map<?, ?> object) {
        for (Object key : object.keySet()) {
            print(key);
            print(object.get(key));
        }
    }

    public static void print(String string) {
        if (Variables.mode == Variables.Modes.PRODUCTION) {
            return;
        }
        if (string == null) {
            string = "";
        }
        if (Gdx.app.getType() == ApplicationType.Android) {
            Gdx.app.log(Variables.logTag, string);
        }
        else {
            System.out.println(string);
        }
    }

    public static float percentOf(float value, float percent) {
        return value / 100f * percent;
    }

    public static float percentFind(float fraction, float amount) {
        float percent = fraction / amount * 100;
        if (percent < 0) {
            percent = 0;
        }
        if (percent > 100) {
            percent = 100;
        }
        return percent;
    }

    public static float percentFindInfinite(float fraction, float amount) {
        float percent = fraction / amount * 100;
        return percent;
    }


    public static float percentWidth(float percent) {
        return Variables.width / 100f * percent;
    }

    public static float percentHeight(float percent) {
        return Variables.height / 100f * percent;
    }


    public static Vector2 pixels(Vector2 meters) {
        return meters.scl(Variables.density);
    }

    public static float pixels(float meters) {
        return meters * Variables.density;
    }

    public static float meters(float pixels) {
        return pixels / Variables.density;
    }

    public static Vector2 meters(Vector2 pixels) {
        Vector2 meters = new Vector2(pixels.x / Variables.density, pixels.y / Variables.density);
        return meters;
    }


    public static boolean metersPositionOffScreen(Vector2 pos) {
        if (pos.x < 0 || pos.y < 0 || pos.x > Variables.metersWidth || pos.y > Variables.metersHeight) {
            return true;
        }
        return false;
    }


    public static int getTime() {
        return (int) (System.currentTimeMillis() / 1000L);
    }


    public static long getTimeMillis() {
        return System.currentTimeMillis();
    }


    public static Vector2 centerAround(Vector2 size, Vector2 point) {
        return new Vector2(point.x - size.x / 2, point.y - size.y / 2);

    }

    public static int centerWidth(Actor actor) {
        return (int) (Variables.width - actor.getWidth()) / 2;
    }

    public static int centerHeight(Actor actor) {
        return (int) (Variables.height - actor.getHeight()) / 2;
    }

    public static int topHeight(Actor actor) {
        return (int) (Variables.height - actor.getHeight());
    }

    // ACTOR SHIT
    public static void setWidth(Actor actor, float width) {
        float ratio = actor.getWidth() / actor.getHeight();
        actor.setHeight(Math.round(width / ratio));
        actor.setWidth(width);
    }

    public static void setHeight(Actor actor, float height) {
        float ratio = actor.getHeight() / actor.getWidth();
        actor.setWidth(Math.round(height / ratio));
        actor.setHeight(height);
    }

    // WTF !?!?!?!
    public static float getHeight(TextureRegion texture, float width) {
        float ratio = texture.getRegionWidth() / (float) texture.getRegionHeight();
        return Math.round(width / ratio);
    }

    public static float getHeight(Actor actor, float width) {
        float ratio = actor.getWidth() / (float) actor.getHeight();
        return Math.round(width / ratio);
    }

    public static int getWidth(TextureRegion texture, int height) {
        float ratio = texture.getRegionHeight() / (float) texture.getRegionWidth();
        return Math.round(height / ratio);
    }

    public static float getWidth(TextureRegion texture, float height) {
        float ratio = texture.getRegionHeight() / (float) texture.getRegionWidth();
        return height / ratio;
    }

    public static float getWidth(Vector2 size, float height) {
        float ratio = size.y / (float) size.x;
        return height / ratio;
    }

    public static float getHeight(Vector2 size, float width) {
        float ratio = size.x / (float) size.y;
        return width / ratio;
    }


    public static String floatToTime(float inputSeconds) {
        int minutes = (int) (inputSeconds / 60);
        int seconds = (int) (inputSeconds % 60);
        String minString = (minutes < 10) ? "0" + minutes : "" + minutes;
        String secString = (seconds < 10) ? "0" + seconds : "" + seconds;
        return "" + minString + ":" + secString;
    }


    public enum Directions {
        TOP,
        RIGHT,
        BOTTOM,
        LEFT
    }


    public static void closeAction(Array<Actor> actors, Directions... directions) {

        if (directions.length > 1) {
            //for(Directions direction : directions){

            //}
        }
        else if (directions.length > 0) {
            Directions direction = directions[0];
            switch (direction) {
                case TOP:
                    for (Actor actor : actors) {
                        actor.addAction(Actions.moveTo(actor.getX(), Funcs.percentWidth(100)));
                    }
                    break;
                case BOTTOM:
                    for (Actor actor : actors) {
                        actor.addAction(Actions.moveTo(actor.getX(), Funcs.percentWidth(0) - actor.getHeight()));
                    }
                    break;
                case LEFT:
                    for (Actor actor : actors) {
                        actor.addAction(Actions.moveTo(Funcs.percentWidth(0) - actor.getWidth(), actor.getY()));
                    }
                    break;
                case RIGHT:
                    for (Actor actor : actors) {
                        actor.addAction(Actions.moveTo(Funcs.percentWidth(100), actor.getY()));
                    }
                    break;
            }
        }
    }


    public static float changeRange(float OldMin, float OldMax, float NewMin, float NewMax, float OldValue) {
        return (((OldValue - OldMin) * (NewMax - NewMin)) / (OldMax - OldMin)) + NewMin;
    }


    public static TextureRegion fixBleeding(TextureRegion region) {
        float fix = 0.01f;
        float x = region.getRegionX();
        float y = region.getRegionY();
        float width = region.getRegionWidth();
        float height = region.getRegionHeight();
        float invTexWidth = 1f / region.getTexture().getWidth();
        float invTexHeight = 1f / region.getTexture().getHeight();
        region.setRegion((x + fix) * invTexWidth, (y + fix) * invTexHeight, (x + width - fix) * invTexWidth, (y + height - fix) * invTexHeight); // Trims Region
        return region;
    }

    public static Vector2 getStringSizeWithFont(String string, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout(); //dont do this every frame! Store it as member
        layout.setText(font, string);
        return new Vector2(layout.width, layout.height);
    }

    public static boolean renameFile(String fileOrig, String fileChanged) {
        File oldfile = new File(fileOrig);
        File newfile = new File(fileChanged);

        Funcs.print("ERROR: Failed to rename " + oldfile.getPath() + " to " + newfile.getPath());

        return oldfile.renameTo(newfile);
    }


    public static float[] splitNumber(float number, int split) {
        float part = number / split;
        float[] val = new float[split];

        int i = 0;
        for (float added = 0; added < number; added += part) {
            val[i] = Funcs.formatNumber(added, "0.00");
            i++;
        }

        return val;
    }

    /*
     * MooLander Shit DEPRECATED BELOW
     */
    public static float fontScale(float percent) {
        return Funcs.percentHeight(percent) / 150;
    }

    // samnitelen direction
    public static float getDirection(float targetY, float objectY, float targetX, float objectX) {
        return (float) Math.toDegrees(Math.atan2(targetY - objectY, targetX - objectX));
    }

    public static float getDistance(float targetY, float y, float targetX, float x) {
        float xd = targetX - x;
        float yd = targetY - y;
        return (float) (Math.sqrt(xd * xd + yd * yd));
    }

    public static Vector2 radiansToVector2(float radians) {
        return new Vector2((float) Math.cos(radians), (float) -Math.sin(radians));
    }

    public static Vector2 degreesToVector2(float degrees) {
        degrees = (float) Math.toRadians(degrees);
        return new Vector2((float) Math.cos(degrees), (float) -Math.sin(degrees));
    }
}


