package com.rightovers.wave.player;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.rightovers.wave.Main;
import com.rightovers.wave.utils.Funcs;
import com.rightovers.wave.utils.Loader;

/**
 * Created by hp on 22.1.2017 г..
 */

public class PlayerParticles  {
    public ParticleEffect greenParticle;
    public static PlayerParticles inst;
    private PlayerParticles(){
        this.greenParticle = Loader.getInstance().getParticleEffect("particles/green-particle.particle");
        this.greenParticle.setPosition(Funcs.percentWidth(20),Funcs.percentHeight(5));
        this.greenParticle.scaleEffect(Main.getInstance().density/5);
    }
    public static PlayerParticles getInstance(){
        if(inst == null)
            inst = new PlayerParticles();
        return inst;
    }
    public void update(float deltaTime){
        this.greenParticle.update(deltaTime);
    }
    public void draw(float deltaTime){
        this.greenParticle.draw(Main.getInstance().batch);
    }
}
