package com.rightovers.wave.screens;

import com.badlogic.gdx.Screen;
import com.rightovers.wave.Main;

/**
 * Created by Evkalipt on 1/20/2017.
 */

public class MainMenu implements Screen {
    public static MainMenu getInstance() {
        if (Main.getInstance().instances.get(MainMenu.class) == null) {
            Main.getInstance().instances.put(MainMenu.class, new MainMenu());
        }
        return (MainMenu) Main.getInstance().instances.get(MainMenu.class);
    }

    @Override
    public void show() {

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
