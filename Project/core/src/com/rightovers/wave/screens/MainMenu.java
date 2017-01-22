package com.rightovers.wave.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
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
        Image bg = new Image(Loader.getInstance().getTexture("images/grnBg.jpg"));
        Funcs.setWidth(bg, Funcs.percentWidth(100));
        bg.setPosition(Funcs.centerWidth(bg), Funcs.centerHeight(bg));

        Image playBtn = new Image(Loader.getInstance().getTexture("images/play.png"));
        Funcs.setWidth(playBtn, Funcs.percentWidth(20));
        playBtn.setPosition(Funcs.centerWidth(playBtn), Funcs.centerHeight(playBtn));
        playBtn.setOrigin(Align.center);
        playBtn.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.delay(0.5f), Actions.scaleTo(1.2f, 1.2f, 0.5f), Actions.scaleTo(1f, 1f, 0.5f))));

        Image jica = new Image(Loader.getInstance().getTexture("images/jica.png"));
        Funcs.setWidth(jica, Funcs.percentWidth(20));
        jica.setPosition(Funcs.percentWidth(100), Funcs.percentHeight(-5));
        jica.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.moveTo(Funcs.percentWidth(-20), Funcs.percentHeight(-5), 5), Actions.moveTo(Funcs.percentWidth(120), Funcs.percentHeight(-5)))));


        Image post = new Image(Loader.getInstance().getTexture("images/foregr.png"));
        Funcs.setWidth(post, Funcs.percentWidth(20));
        post.setPosition(Funcs.percentWidth(120), 0);
        post.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.moveTo(Funcs.percentWidth(-20), 0, 8), Actions.moveTo(Funcs.percentWidth(120), 0))));

        Main.getInstance().stage.addActor(bg);
        Main.getInstance().stage.addActor(playBtn);
        Main.getInstance().stage.addActor(post);
        Main.getInstance().stage.addActor(jica);
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
