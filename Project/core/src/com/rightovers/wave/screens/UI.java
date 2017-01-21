package com.rightovers.wave.screens;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.rightovers.wave.Main;
import com.rightovers.wave.utils.Funcs;
import com.rightovers.wave.utils.IResourceable;
import com.rightovers.wave.utils.Loader;

public class UI implements IResourceable {

    public static UI getInstance() {
        if (Main.getInstance().instances.get(UI.class) == null) {
            Main.getInstance().instances.put(UI.class, new UI());
        }
        return (UI) Main.getInstance().instances.get(UI.class);
    }

    public static void drawStrengthBar() {
        Image playBtn = new Image(Loader.getInstance().getTexture("images/chorap.png"));
        Funcs.setWidth(playBtn, Funcs.percentWidth(20));
        playBtn.setPosition(Funcs.centerWidth(playBtn), Funcs.percentHeight(80));
        playBtn.addAction(Actions.sequence(Actions.scaleBy(1.2f, 1.2f, 1), Actions.scaleBy(0.8f, 0.8f, 1)));
    }

    @Override
    public void loadAssets() {
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, "images/chorap.png", Loader.AssetType.TEXTURE);
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, "images/meter.png", Loader.AssetType.TEXTURE);

    }
}
