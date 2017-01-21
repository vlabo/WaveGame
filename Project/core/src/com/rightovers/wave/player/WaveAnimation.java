package com.rightovers.wave.player;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.rightovers.wave.utils.Funcs;

import java.util.ArrayList;
import java.util.Arrays;

/** <p>
 * An Animation stores a list of objects representing an animated sequence, e.g. for running or jumping. Each
 * object in the Animation is called a key frame, and multiple key frames make up the animation.
 * <p>
 * The animation's type is the class representing a frame of animation. For example, a typical 2D animation could be made
 * up of {@link com.badlogic.gdx.graphics.g2d.TextureRegion TextureRegions} and would be specified as:
 * <p><code>Animation&lt;TextureRegion&gt; myAnimation = new Animation&lt;TextureRegion&gt;(...);</code>
 *
 * @author mzechner */
public class WaveAnimation<T> {

    public int getLeftLoopFrameNumber() {
        return leftLoopFrameNumber;
    }

    public void setLeftLoopFrameNumber(int leftLoopFrameNumber) {
        this.leftLoopFrameNumber = leftLoopFrameNumber;
    }

    public int getRightLoopFrameNumber() {
        return rightLoopFrameNumber;
    }

    public void setRightLoopFrameNumber(int rightLoopFrameNumber) {
        this.rightLoopFrameNumber = rightLoopFrameNumber;
    }

    public enum PlayMode {
        NORMAL,
        REVERSED,
        LOOP,
        LOOP_REVERSED,
        LOOP_PINGPONG,
        LOOP_RANDOM,
    }

    /** Length must not be modified without updating {@link #animationDuration}. See {@link #setKeyFrames(T[])}. */

    ArrayList<T> keyFrames;
    T[] realKeyFrames;
    private float frameDuration;
    private float animationDuration;
    private int lastFrameNumber;
    private int leftLoopFrameNumber, rightLoopFrameNumber;
    private float lastStateTime;

    private PlayMode playMode = PlayMode.NORMAL;

    /** Constructor, storing the frame duration and key frames.
     *
     * @param frameDuration the time between frames in seconds.
     * @param keyFrames the objects representing the frames. */
    public WaveAnimation (float frameDuration, Array<? extends T> keyFrames) {
        this.frameDuration = frameDuration;
        T[] frames = (T[]) new Object[keyFrames.size];
        for (int i = 0, n = keyFrames.size; i < n; i++) {
            frames[i] = keyFrames.get(i);
        }
        setKeyFrames(frames);
    }

    /** Constructor, storing the frame duration and key frames.
     *
     * @param frameDuration the time between frames in seconds.
     * @param keyFrames the objects representing the frames. */
    public WaveAnimation (float frameDuration, Array<? extends T> keyFrames, PlayMode playMode) {
        this(frameDuration, keyFrames);
        setPlayMode(playMode);
    }




    /** Constructor, storing the frame duration and key frames.
     *
     * @param frameDuration the time between frames in seconds.
     * @param keyFrames the objects representing the frames. */
    public WaveAnimation (float frameDuration, T... keyFrames) {
        this.frameDuration = frameDuration;
        setKeyFrames(keyFrames);
    }

    /** Returns a frame based on the so called state time. This is the amount of seconds an object has spent in the
     * state this Animation instance represents, e.g. running, jumping and so on. The mode specifies whether the animation is
     * looping or not.
     *
     * @param stateTime the time spent in the state represented by this animation.
     * @param looping whether the animation is looping or not.
     * @return the frame of animation for the given state time. */
    public T getKeyFrame (float stateTime, boolean looping) {
        // we set the play mode by overriding the previous mode based on looping
        // parameter value
        PlayMode oldPlayMode = playMode;
        if (looping && (playMode == PlayMode.NORMAL || playMode == PlayMode.REVERSED)) {
            if (playMode == PlayMode.NORMAL)
                playMode = PlayMode.LOOP;
            else
                playMode = PlayMode.LOOP_REVERSED;
        } else if (!looping && !(playMode == PlayMode.NORMAL || playMode == PlayMode.REVERSED)) {
            if (playMode == PlayMode.LOOP_REVERSED)
                playMode = PlayMode.REVERSED;
            else
                playMode = PlayMode.LOOP;
        }

        T frame = getKeyFrame(stateTime);
        playMode = oldPlayMode;
        return frame;
    }

    /** Returns a frame based on the so called state time. This is the amount of seconds an object has spent in the
     * state this Animation instance represents, e.g. running, jumping and so on using the mode specified by
     * {@link #setPlayMode(PlayMode)} method.
     *
     * @param stateTime
     * @return the frame of animation for the given state time. */
    public T getKeyFrame (float stateTime) {
        int frameNumber = getKeyFrameIndex(stateTime);
        return keyFrames.get(frameNumber);
    }

    /** Returns the current frame number.
     * @param stateTime
     * @return current frame number */
    public int getKeyFrameIndex (float stateTime) {
        if (keyFrames.size() == 1) return 0;

        int frameNumber = (int)(stateTime / frameDuration);



        switch (playMode) {
            case NORMAL:
                frameNumber = Math.min(keyFrames.size()  - 1, frameNumber);
                break;
            case LOOP:
                frameNumber = frameNumber % keyFrames.size();
                break;
            case LOOP_PINGPONG:
                frameNumber = frameNumber % ((keyFrames.size() * 2) - 2);
                if (frameNumber >= keyFrames.size()) frameNumber = keyFrames.size() - 2 - (frameNumber - keyFrames.size());
                break;
            case LOOP_RANDOM:
                int lastFrameNumber = (int) ((lastStateTime) / frameDuration);
                if (lastFrameNumber != frameNumber) {
                    frameNumber = MathUtils.random(keyFrames.size() - 1);
                } else {
                    frameNumber = this.lastFrameNumber;
                }
                break;
            case REVERSED:
                frameNumber = Math.max(keyFrames.size() - frameNumber - 1, 0);
                break;
            case LOOP_REVERSED:
                frameNumber = frameNumber % keyFrames.size();
                frameNumber = keyFrames.size() - frameNumber - 1;
                break;
        }

        lastFrameNumber = frameNumber;
        lastStateTime = stateTime;




        return frameNumber;
    }

    /** Returns the keyframes[] array where all the frames of the animation are stored.
     * @return The keyframes[] field. */
    public ArrayList<T> getKeyFrames () {
        return keyFrames;
    }

    protected void setKeyFrames (T... keyFrames) {
        this.keyFrames = new ArrayList(Arrays.asList(keyFrames));
        this.realKeyFrames = keyFrames;
        this.animationDuration = keyFrames.length * frameDuration;
    }

    public void calculateTrimmedKeyFrames () {
        int rightFrame = this.getRightLoopFrameNumber();
        int leftFrame = this.getLeftLoopFrameNumber();
        int trimmedSize = rightFrame-leftFrame;
        this.keyFrames = new ArrayList();

        for(int i=(leftFrame-1);i<=(rightFrame-1);i++){
            this.keyFrames.add(this.realKeyFrames[i]);
        }
        this.animationDuration = keyFrames.size() * frameDuration;
    }


    /** Returns the animation play mode. */
    public PlayMode getPlayMode () {
        return playMode;
    }

    /** Sets the animation play mode.
     *
     * @param playMode The animation {@link PlayMode} to use. */
    public void setPlayMode (PlayMode playMode) {
        this.playMode = playMode;
    }

    /** Whether the animation would be finished if played without looping (PlayMode#NORMAL), given the state time.
     * @param stateTime
     * @return whether the animation is finished. */
    public boolean isAnimationFinished (float stateTime) {
        int frameNumber = (int)(stateTime / frameDuration);
        return keyFrames.size() - 1 < frameNumber;
    }

    /** Sets duration a frame will be displayed.
     * @param frameDuration in seconds */
    public void setFrameDuration (float frameDuration) {
        this.frameDuration = frameDuration;
        this.animationDuration = keyFrames.size() * frameDuration;
    }

    /** @return the duration of a frame in seconds */
    public float getFrameDuration () {
        return frameDuration;
    }

    /** @return the duration of the entire animation, number of frames times frame duration, in seconds */
    public float getAnimationDuration () {
        return animationDuration;
    }
}
