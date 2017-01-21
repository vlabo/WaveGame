package com.rightovers.wave.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.rightovers.wave.Main;
import com.rightovers.wave.utils.Funcs;
import com.rightovers.wave.utils.Loader;


public class MainMenu implements Screen {
    public static MainMenu getInstance() {
        if (Main.getInstance().instances.get(MainMenu.class) == null) {
            Main.getInstance().instances.put(MainMenu.class, new MainMenu());
        }
        return (MainMenu) Main.getInstance().instances.get(MainMenu.class);
    }

    @Override
    public void show() {
        Image bg = new Image(Loader.getInstance().getTexture("images/bg.jpg"));
        Funcs.setWidth(bg, Funcs.percentWidth(100));

        Image playBtn = new Image(Loader.getInstance().getTexture("images/play.png"));
        Funcs.setWidth(playBtn, Funcs.percentWidth(20));
        playBtn.setPosition(Funcs.centerWidth(playBtn), Funcs.centerHeight(playBtn));
        playBtn.addAction(Actions.sequence(Actions.scaleBy(1.2f, 1.2f, 1), Actions.scaleBy(0.8f, 0.8f, 1)));

        Main.getInstance().stage.addActor(bg);
        Main.getInstance().stage.addActor(playBtn);
    }

    @Override
    public void render(float delta) {

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
