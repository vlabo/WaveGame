package com.rightovers.wave.utils;

import java.util.HashMap;


public class Variables {
    public static enum Modes {
        DEVELOPMENT,
        Modes,
        PRODUCTION
    }

    public static Modes mode;
    public static int width, height;
    public static float density, metersWidth, metersHeight;
    public static HashMap<String, Object> modules;
    public static String logTag;

}
