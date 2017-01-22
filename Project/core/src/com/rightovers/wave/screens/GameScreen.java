package com.rightovers.wave.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.rightovers.wave.Main;
import com.rightovers.wave.map.Box2DWorld;
import com.rightovers.wave.map.Environment;
import com.rightovers.wave.map.obstacles.ObstacleGenerator;
import com.rightovers.wave.player.Player;
import com.rightovers.wave.player.PlayerParticles;
import com.rightovers.wave.utils.Funcs;


public class GameScreen implements Screen {

    public int timeStartedPlaying;
    public int buildingsDestroyed;

    public static GameScreen getInstance() {
        if (Main.getInstance().instances.get(GameScreen.class) == null) {
            Main.getInstance().instances.put(GameScreen.class, new GameScreen());
        }
        return (GameScreen) Main.getInstance().instances.get(GameScreen.class);
    }

    public GameScreen() {
        Box2DWorld.getInstance().setup(Main.getInstance().camera, Main.getInstance().density);
        Player.getInstance().create();
        UI.drawStrengthBar();
        PlayerParticles.getInstance().init();
    }

    @Override
    public void show() {
        Main.getInstance().stage.clear();
        this.timeStartedPlaying = 0;
        this.buildingsDestroyed = 0;
        Player.getInstance().dying = false;
        Player.getInstance().speed = Player.getInstance().INITIAL_SPEED;


        // start playing
        this.timeStartedPlaying = Funcs.getTime();

        // for test - kill the player
        Main.getInstance().stage.addAction(Actions.sequence(Actions.delay(2), new Action() {
            @Override
            public boolean act(float delta) {
                //Player.getInstance().die();
                return true;
            }
        }));
    }

    @Override
    public void render(float delta) {
        // update stuff
        Box2DWorld.getInstance().update(delta);
        Environment.getInstance().update(delta);
        ObstacleGenerator.getInstance().update(delta);
        Player.getInstance().update(delta);
        PlayerParticles.getInstance().update(delta);
        // draw stuff
        Main.getInstance().batch.begin();

        Environment.getInstance().drawBackground(delta);
        Environment.getInstance().drawObstacles(delta);
        Player.getInstance().waveGraphics.draw(delta);
        Player.getInstance().poseidonGraphics.draw(delta);
        PlayerParticles.getInstance().draw(delta);
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
