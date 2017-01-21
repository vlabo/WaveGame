package com.rightovers.wave.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rightovers.wave.Main;
import com.rightovers.wave.utils.IActivityCaller;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new Main(new IActivityCaller() {
            @Override
            public void print(String tag, String msg) {
				
            }
        }), config);
    }
}
