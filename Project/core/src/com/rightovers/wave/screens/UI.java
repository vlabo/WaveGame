package com.rightovers.wave.screens;

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
        Image sock = new Image(Loader.getInstance().getTexture("images/chorap.png"));
        Funcs.setWidth(sock, Funcs.percentWidth(4));
        sock.setPosition(Funcs.percentWidth(4), Funcs.percentHeight(84));


        Image meter = new Image(Loader.getInstance().getTexture("images/meter.png"));
        Funcs.setWidth(meter, Funcs.percentWidth(20));
        meter.setPosition(Funcs.percentWidth(3), Funcs.percentHeight(87));

        Main.getInstance().stage.addActor(meter);
        Main.getInstance().stage.addActor(sock);
    }

    @Override
    public void loadAssets() {
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, "images/chorap.png", Loader.AssetType.TEXTURE);
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, "images/meter.png", Loader.AssetType.TEXTURE);

    }
}
