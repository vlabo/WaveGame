package com.rightovers.wave.player;

import com.rightovers.wave.Main;
import com.rightovers.wave.utils.Loader;

public class PlayerGraphics {

    public void draw(float delta) {
        Main.getInstance().batch.draw(Loader.getInstance().getTexture("images/ground.png"), Player.getInstance().physics.getBox2DBody().getPosition().x - Player.getInstance().getDistance(), Player.getInstance().physics.getBox2DBody().getPosition().y, 500, 500);
    }

    public void update(float delta) {
    }


}
