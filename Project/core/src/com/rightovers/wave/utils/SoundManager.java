package com.rightovers.wave.utils;

import com.rightovers.wave.Main;

/**
 * Created by hp on 22.1.2017 г..
 */

public class SoundManager implements IResourceable {

    public static SoundManager getInstance() {
        if (Main.getInstance().instances.get(SoundManager.class) == null) {
            Main.getInstance().instances.put(SoundManager.class, new SoundManager());
        }
        return (SoundManager) Main.getInstance().instances.get(SoundManager.class);
    }


    @Override
    public void loadAssets() {
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, "sounds/punch.mp3", Loader.AssetType.SOUND);
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, "sounds/trash-ride.mp3", Loader.AssetType.MUSIC);
    }


    public void playAndRewindMusic(int part){
        Loader.getInstance().getMusic("sounds/trash-ride.mp3").play();
        switch(part){
            case 1:
                break;
            case 2:
                Loader.getInstance().getMusic("sounds/trash-ride.mp3").setPosition(35.5f);
                break;
        }
    }
    public void whip(){
        Loader.getInstance().getSound("sounds/punch.mp3").play();
    }
}
