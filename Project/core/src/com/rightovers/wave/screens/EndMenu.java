package com.rightovers.wave.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.rightovers.wave.Main;
import com.rightovers.wave.utils.Font;
import com.rightovers.wave.utils.Funcs;
import com.rightovers.wave.utils.Loader;

public class EndMenu implements Screen {
    public static EndMenu getInstance() {
        if (Main.getInstance().instances.get(EndMenu.class) == null) {
            Main.getInstance().instances.put(EndMenu.class, new EndMenu());
        }
        return (EndMenu) Main.getInstance().instances.get(EndMenu.class);
    }

    @Override
    public void show() {
        Image bg = new Image(Loader.getInstance().getTexture("images/bg.jpg"));
        Funcs.setWidth(bg, Funcs.percentWidth(100));

        Image playBtn = new Image(Loader.getInstance().getTexture("images/play.png"));
        Funcs.setWidth(playBtn, Funcs.percentWidth(20));
        playBtn.setPosition(Funcs.centerWidth(playBtn), Funcs.percentHeight(70));
        playBtn.addAction(Actions.sequence(Actions.scaleBy(1.2f, 1.2f, 1), Actions.scaleBy(0.8f, 0.8f, 1)));

        Label.LabelStyle textStyle = new Label.LabelStyle(Font.getInstance().getFont("fonts/regular.ttf", (int) Funcs.percentHeight(10)), Color.WHITE);

        Label text1 = new Label("Distance: 12", textStyle);
        text1.setPosition(Funcs.centerWidth(text1), Funcs.percentHeight(50));

        Label text2 = new Label("Time: 12", textStyle);
        text2.setPosition(Funcs.centerWidth(text2), Funcs.percentHeight(40));

        Label text3 = new Label("Buildings down: 12", textStyle);
        text3.setPosition(Funcs.centerWidth(text3), Funcs.percentHeight(30));


        Main.getInstance().stage.addActor(bg);
        Main.getInstance().stage.addActor(playBtn);

        Main.getInstance().stage.addActor(text1);
        Main.getInstance().stage.addActor(text2);
        Main.getInstance().stage.addActor(text3);
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
