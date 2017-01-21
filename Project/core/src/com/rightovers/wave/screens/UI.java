package com.rightovers.wave.screens;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.rightovers.wave.utils.Funcs;
import com.rightovers.wave.utils.Loader;

public class UI {

    public static void drawStrengthBar() {
        Image playBtn = new Image(Loader.getInstance().getTexture("images/play.png"));
        Funcs.setWidth(playBtn, Funcs.percentWidth(20));
        playBtn.setPosition(Funcs.centerWidth(playBtn), Funcs.centerHeight(playBtn));
        playBtn.addAction(Actions.sequence(Actions.scaleBy(1.2f, 1.2f, 1), Actions.scaleBy(0.8f, 0.8f, 1)));
    }

}
