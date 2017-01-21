package com.rightovers.wave.screens;

import com.badlogic.gdx.Screen;
import com.rightovers.wave.Main;
import com.rightovers.wave.map.Box2DWorld;
import com.rightovers.wave.map.Environment;
import com.rightovers.wave.map.obstacles.ObstacleGenerator;
import com.rightovers.wave.player.Player;


public class GameScreen implements Screen {
    public static GameScreen getInstance() {
        if (Main.getInstance().instances.get(GameScreen.class) == null) {
            Main.getInstance().instances.put(GameScreen.class, new GameScreen());
        }
        return (GameScreen) Main.getInstance().instances.get(GameScreen.class);
    }

    @Override
    public void show() {
        Box2DWorld.getInstance().setup(Main.getInstance().camera, Main.getInstance().density);
        Player.getInstance().create();
    }

    @Override
    public void render(float delta) {
        // update stuff
        Box2DWorld.getInstance().update(delta);
        Environment.getInstance().update(delta);
        ObstacleGenerator.getInstance().update(delta);
        Player.getInstance().update(delta);

        // draw stuff
        Main.getInstance().batch.begin();

        Environment.getInstance().drawBackground(delta);
        Player.getInstance().playerGraphics.draw(delta);
        Environment.getInstance().drawObstacles(delta);
        Environment.getInstance().drawForeground(delta);
        Main.getInstance().batch.end();

        Box2DWorld.getInstance().draw(delta);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
