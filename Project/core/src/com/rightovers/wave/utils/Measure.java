package com.rightovers.wave.utils;

import com.badlogic.gdx.Gdx;

public class Measure {
    private static long startTime;
    private static long javaHeapSize, nativeHeapSize;

    private static long startTime2;
    private static long javaHeapSize2, nativeHeapSize2;

    public static void start() {
        if (startTime == -1) {
            startTime = (long) System.nanoTime();
            javaHeapSize = (long) Gdx.app.getJavaHeap();
            nativeHeapSize = (long) Gdx.app.getNativeHeap();
        }
    }

    public static void end(String name) {
        long endTime = (long) System.nanoTime() - startTime;
        javaHeapSize = (long) Gdx.app.getJavaHeap() - javaHeapSize;
        nativeHeapSize = (long) Gdx.app.getNativeHeap() - nativeHeapSize;

        if (name.equals("")) {
            Funcs.print(">>>>>>>>>>>>>>>>>> STATISTICS FOR THE MEASURED PERIOD >>>>>>>>>>>>>>>>>>>>>>>");
        }
        else {
            Funcs.print(">>>>>>>>>>>>>>>>>> STATISTICS ON " + name + " >>>>>>>>>>>>>>>>>>>>>>>");
        }

        Funcs.print("Execution time: " + endTime + "ns or " + (endTime / 1000000f) + " ms or " + (endTime / 1000000000f) + " seconds");

        System.out.println("Current java heap size: " + (long) Gdx.app.getJavaHeap() / (1024 * 1024f) + " MB");
        System.out.println("Current Native heap size: " + (long) Gdx.app.getNativeHeap() / (1024 * 1024f) + " MB");

        System.out.println("Java heap size difference: " + javaHeapSize / (1024 * 1024f) + " MB");
        System.out.println("Native heap size difference: " + nativeHeapSize / (1024 * 1024f) + " MB");

        startTime = -1;
        javaHeapSize = -1;
        nativeHeapSize = -1;

    }

    public static void end() {
        end("");
    }

    public static void start2() {
        if (startTime2 == -1) {
            startTime2 = (long) System.nanoTime();
            javaHeapSize2 = (long) Gdx.app.getJavaHeap();
            nativeHeapSize2 = (long) Gdx.app.getNativeHeap();
        }
    }

    public static void end2(String name) {
        long endTime2 = (long) System.nanoTime() - startTime2;
        javaHeapSize2 = (long) Gdx.app.getJavaHeap() - javaHeapSize2;
        nativeHeapSize2 = (long) Gdx.app.getNativeHeap() - nativeHeapSize2;

        if (name.equals("")) {
            Funcs.print(">>>>>>>>>>>>>>>>>> STATISTICS FOR THE MEASURED PERIOD >>>>>>>>>>>>>>>>>>>>>>>");
        }
        else {
            Funcs.print(">>>>>>>>>>>>>>>>>> STATISTICS ON " + name + " >>>>>>>>>>>>>>>>>>>>>>>");
        }

        Funcs.print("Execution time: " + endTime2 + "ns or " + (endTime2 / 1000000f) + " ms or " + (endTime2 / 1000000000f) + " seconds");

        System.out.println("Current java heap size: " + (long) Gdx.app.getJavaHeap() / (1024 * 1024f) + " MB");
        System.out.println("Current Native heap size: " + (long) Gdx.app.getNativeHeap() / (1024 * 1024f) + " MB");

        System.out.println("Java heap size difference: " + javaHeapSize2 / (1024 * 1024f) + " MB");
        System.out.println("Native heap size difference: " + nativeHeapSize2 / (1024 * 1024f) + " MB");

        startTime2 = -1;
        javaHeapSize2 = -1;
        nativeHeapSize2 = -1;

    }

    public static void end2() {
        end("");
    }
}
