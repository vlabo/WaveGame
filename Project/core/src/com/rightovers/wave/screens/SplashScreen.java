package com.rightovers.wave.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.rightovers.wave.Main;
import com.rightovers.wave.map.Environment;
import com.rightovers.wave.player.Player;
import com.rightovers.wave.player.PlayerParticles;
import com.rightovers.wave.utils.Funcs;
import com.rightovers.wave.utils.Loader;
import com.rightovers.wave.utils.SoundManager;

public class SplashScreen implements Screen {

    private boolean lanselot = false;
    private boolean done = false;

    public static SplashScreen getInstance() {
        if (Main.getInstance().instances.get(SplashScreen.class) == null) {
            Main.getInstance().instances.put(SplashScreen.class, new SplashScreen());
        }
        return (SplashScreen) Main.getInstance().instances.get(SplashScreen.class);
    }

    @Override
    public void show() {

        // add all that needs to be loaded here to the queue
        Loader.getInstance().addAsset("splash", "splash.jpg", Loader.AssetType.TEXTURE);
        Loader.getInstance().addToLoadingQueue("splash");
        Loader.getInstance().finish();


        Player.getInstance().loadAssets();
        Environment.getInstance().loadAssets();
        PlayerParticles.getInstance().loadAssets();
        UI.getInstance().loadAssets();
        SoundManager.getInstance().loadAssets();
        Loader.getInstance().addToLoadingQueue(Main.getInstance().assetsGroupName);
    }

    @Override
    public void render(float delta) {
        // assets are loaded and splash screen is finished
        if (this.done == true && Loader.getInstance().isGroupLoaded(Main.getInstance().assetsGroupName)) {
            Loader.getInstance().unload("splash");

            // TO FIX
            //Main.getInstance().skin.addRegions(Loader.getInstance().getTextureAtlas("images/assets.pack"));

            Funcs.setScreen(MainMenu.getInstance());
        }

        // if the splash screens are not showed but ready
        if (Loader.getInstance().isGroupLoaded("splash") && this.lanselot == false) {
            this.lanselot = true;


            Main.getInstance().skin.add("splash", Loader.getInstance().getTexture("splash.jpg"));

            // splash
            Image splash = new Image(Main.getInstance().skin.getDrawable("splash"));
            Funcs.setWidth(splash, Main.getInstance().width);
            splash.setPosition(Funcs.centerWidth(splash), Funcs.centerHeight(splash));
            splash.addAction(Actions.alpha(0));
            splash.addAction(Actions.sequence(Actions.fadeIn(0.1f), Actions.delay(0.1f), Actions.fadeOut(0.1f), new Action() {
                // finish the splash screen
                @Override
                public boolean act(float delta) {
                    SplashScreen.this.done = true;
                    return true;
                }

            }));
            Main.getInstance().stage.addActor(splash);

        }
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
