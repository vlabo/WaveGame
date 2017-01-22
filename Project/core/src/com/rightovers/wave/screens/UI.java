package com.rightovers.wave.screens;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.rightovers.wave.Main;
import com.rightovers.wave.player.Player;
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
        final Image sock = new Image(Loader.getInstance().getTexture("images/chorap.png"));
        Funcs.setWidth(sock, Funcs.percentWidth(4));

        sock.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                // Min for xPos is 4 and max is 20
                float xPos = Funcs.changeRange(0, 1, 4, 20, Player.getInstance().getStrength());
                //You can print xPos here
                //Funcs.print(" " + xPos);

                sock.setPosition(Funcs.percentWidth(xPos), Funcs.percentHeight(84));
                return false;
            }
        });

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
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, "images/play.png", Loader.AssetType.TEXTURE);
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, "images/grnBg.jpg", Loader.AssetType.TEXTURE);
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, "images/jica.png", Loader.AssetType.TEXTURE);
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, "images/foregr.png", Loader.AssetType.TEXTURE);
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, "images/quit.png", Loader.AssetType.TEXTURE);

    }
}
