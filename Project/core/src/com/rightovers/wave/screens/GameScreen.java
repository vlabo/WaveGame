package com.rightovers.wave.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.rightovers.wave.Main;
import com.rightovers.wave.map.Box2DWorld;
import com.rightovers.wave.map.Environment;
import com.rightovers.wave.map.obstacles.ObstacleGenerator;
import com.rightovers.wave.player.Player;
import com.rightovers.wave.player.PlayerParticles;
import com.rightovers.wave.utils.Funcs;
import com.rightovers.wave.utils.SoundManager;

import static com.badlogic.gdx.math.MathUtils.random;


public class GameScreen implements Screen {

    public int timeStartedPlaying;
    public int buildingsDestroyed;

    // camera shake vars
    float cameraShakeElapsed, cameraShakeDuration, cameraShakeRadius, cameraShakeRandomAngle;

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
        SoundManager.getInstance().playAndRewindMusic(2);

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


    public void startCameraShake(float radius, float duration) {
        this.cameraShakeElapsed = 0;
        this.cameraShakeDuration = duration / 1000f;
        this.cameraShakeRadius = radius;
        this.cameraShakeRandomAngle = random.nextFloat() % 360f;
    }


    @Override
    public void render(float delta) {
        cameraShakeUpdate(delta, Main.getInstance().camera);

        // cameraShakeUpdate stuff
        Box2DWorld.getInstance().update(delta);
        Environment.getInstance().update(delta);
        ObstacleGenerator.getInstance().update(delta);
        Player.getInstance().update(delta);
        PlayerParticles.getInstance().update(delta);
        // draw stuff
        Main.getInstance().batch.begin();

        Environment.getInstance().drawBackground(delta);
        Environment.getInstance().drawObstacles(delta);

        PlayerParticles.getInstance().drawBackground(delta);
        Player.getInstance().waveGraphics.draw(delta);
        Player.getInstance().poseidonGraphics.draw(delta);
        PlayerParticles.getInstance().drawForeground(delta);
        Environment.getInstance().drawForeground(delta);
        Main.getInstance().batch.end();

        Box2DWorld.getInstance().draw(delta);

    }

    public void cameraShakeUpdate(float delta, OrthographicCamera camera) {
        // Only startCameraShake when required.
        if (this.cameraShakeElapsed < this.cameraShakeDuration) {

            // Calculate the amount of startCameraShake based on how long it has been shaking already
            float currentPower = this.cameraShakeRadius * camera.zoom * ((this.cameraShakeDuration - this.cameraShakeElapsed) / this.cameraShakeDuration);
            float x = (random.nextFloat() - 0.5f) * currentPower;
            float y = (random.nextFloat() - 0.5f) * currentPower;
            camera.translate(-x, -y);

            // Increase the cameraShakeElapsed time by the delta provided.
            this.cameraShakeElapsed += delta;

            // Update the startCameraShake position, then the camera.
            Main.getInstance().batch.setProjectionMatrix(camera.combined);
            camera.update();
        }
    }

    public void resetAfterCameraShake(OrthographicCamera camera) {
        //reset stuff
        camera.zoom = 1;
        camera.lookAt(0, 0, 0);
        Main.getInstance().batch.setProjectionMatrix(camera.combined);
        camera.update();
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
