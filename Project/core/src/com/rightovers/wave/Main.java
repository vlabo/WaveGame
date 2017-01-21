package com.rightovers.wave;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.rightovers.wave.screens.SplashScreen;
import com.rightovers.wave.utils.Font;
import com.rightovers.wave.utils.Funcs;
import com.rightovers.wave.utils.IActivityCaller;
import com.rightovers.wave.utils.Loader;
import com.rightovers.wave.utils.Timer;
import com.rightovers.wave.utils.Variables;

import java.util.HashMap;

import static com.rightovers.wave.utils.Variables.Modes.Modes;

public class Main extends Game {

    public HashMap<Class, Object> instances = new HashMap<Class, Object>();

    public String assetsGroupName = "assets";

    public int width, height;
    public float metersWidth, metersHeight, density;

    public Variables.Modes mode = Variables.Modes.DEVELOPMENT;

    private final boolean FULL_SCREEN = false;
    private FPSLogger fpsLogger;
    public HashMap<String, BitmapFont> fonts;

    public static Main inst;
    public static IActivityCaller activityCaller;

    public Main(IActivityCaller activityCaller) {
        Main.activityCaller = activityCaller;
    }

    public static Main getInstance() {
        return inst;
    }


    public Skin skin = new Skin();
    public Stage stage;
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public TextButton.TextButtonStyle styleReg, styleRegBig;
    private float DESIRED_METERS_WIDTH = 40f;
    public Game game;

    @Override
    public void create() {
        inst = this;

        Main.inst.instances = new HashMap<Class, Object>();

        // resolution variables
        Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        this.width = displayMode.width;
        this.height = displayMode.height;

        Gdx.graphics.setWindowedMode(this.width, this.height);
        //density = (float)(Gdx.graphics.getDensity() * 160);
        this.density = this.width / this.DESIRED_METERS_WIDTH;
        // metersWidth is always DESIRED_METERS_WIDTH, metersHeight varies in dependence of the screen height to width ratio
        this.metersWidth = (this.width / this.density);
        this.metersHeight = (this.height / this.density);


        // some inits
        this.fpsLogger = new FPSLogger();

        this.batch = new SpriteBatch();
        this.stage = new Stage();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, this.width, this.height);
        this.batch.setProjectionMatrix(this.camera.combined);


        // Send the vars to the utils lib
        Variables.mode = Modes.DEVELOPMENT;
        Variables.density = this.density;
        Variables.metersWidth = this.metersWidth;
        Variables.metersHeight = this.metersHeight;
        Variables.width = this.width;
        Variables.height = this.height;
        Variables.logTag = "Game";
        Variables.modules = new HashMap<String, Object>();

        Gdx.input.setInputProcessor(this.stage);


        // fonts
        this.styleReg = new TextButton.TextButtonStyle();
        this.styleReg.font = Font.getInstance().getFont("fonts/regular.ttf", (int) Funcs.percentHeight(5));
        this.styleReg.fontColor = Color.valueOf("ffffff");

        this.styleRegBig = new TextButton.TextButtonStyle();
        this.styleRegBig.font = Font.getInstance().getFont("fonts/regular.ttf", (int) Funcs.percentHeight(8));
        this.styleRegBig.fontColor = Color.valueOf("ffffff");


        // go to splash screen to load assets
        setScreen(SplashScreen.getInstance());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();

        this.stage.act();
        this.stage.draw();

        Timer.update(Gdx.graphics.getDeltaTime());
        Loader.getInstance().update();
        if (this.mode == Modes.DEVELOPMENT) {
            this.fpsLogger.log();
        }
    }

    @Override
    public void dispose() {
        super.dispose();

        // dispose all classes
        Font.getInstance().dispose();
        Loader.getInstance().dispose();

        // remove all instances
        Main.inst.instances.clear();
        Main.inst = null;

        Gdx.app.exit();
    }

}
