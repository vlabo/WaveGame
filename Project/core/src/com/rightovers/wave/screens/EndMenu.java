package com.rightovers.wave.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.rightovers.wave.Main;
import com.rightovers.wave.player.Player;
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
        Main.getInstance().stage.clear();

        Image bg = new Image(Loader.getInstance().getTexture("images/background.png"));
        Funcs.setWidth(bg, Funcs.percentWidth(100));

        Image playBtn = new Image(Loader.getInstance().getTexture("images/building.png"));
        Funcs.setWidth(playBtn, Funcs.percentWidth(20));
        playBtn.setOrigin(Align.center);
        playBtn.setPosition(Funcs.centerWidth(playBtn), Funcs.percentHeight(70));
        playBtn.addAction(Actions.sequence(Actions.delay(3), Actions.scaleTo(1.2f, 1.2f, 0.5f), Actions.scaleTo(1f, 1f, 0.5f)));

        Label.LabelStyle textStyle = new Label.LabelStyle(Font.getInstance().getFont("fonts/regular.ttf", (int) Funcs.percentHeight(10)), Color.WHITE);

        Label text1 = new Label("Distance: " + Player.getInstance().getDistance() + "m", textStyle);
        text1.setPosition(Funcs.centerWidth(text1), Funcs.percentHeight(120));
        text1.addAction(Actions.moveTo(Funcs.centerWidth(text1), Funcs.percentHeight(50), 1, Interpolation.bounce));

        Label text2 = new Label("Time: " + (Funcs.getTime() - GameScreen.getInstance().timeStartedPlaying), textStyle);
        text2.setPosition(Funcs.centerWidth(text2), Funcs.percentHeight(150));
        text2.addAction(Actions.moveTo(Funcs.centerWidth(text2), Funcs.percentHeight(35), 1, Interpolation.bounce));

        Label text3 = new Label("Buildings destroyed: " + GameScreen.getInstance().buildingsDestroyed, textStyle);
        text3.setPosition(Funcs.centerWidth(text3), Funcs.percentHeight(180));
        text3.addAction(Actions.moveTo(Funcs.centerWidth(text3), Funcs.percentHeight(20), 1, Interpolation.bounce));


        Main.getInstance().stage.addActor(bg);
        Main.getInstance().stage.addActor(playBtn);

        Main.getInstance().stage.addActor(text1);
        Main.getInstance().stage.addActor(text2);
        Main.getInstance().stage.addActor(text3);

        playBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Funcs.setScreen(GameScreen.getInstance());
                return true;
            }
        });

        // reset data
        GameScreen.getInstance().buildingsDestroyed = 0;
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
