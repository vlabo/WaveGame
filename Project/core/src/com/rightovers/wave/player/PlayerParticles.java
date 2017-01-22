package com.rightovers.wave.player;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.rightovers.wave.Main;
import com.rightovers.wave.utils.Funcs;
import com.rightovers.wave.utils.IResourceable;
import com.rightovers.wave.utils.Loader;


public class PlayerParticles implements IResourceable {
    public ParticleEffect greenParticle;
    public ParticleEffect deathParticle;

    public static PlayerParticles getInstance() {
        if (Main.getInstance().instances.get(PlayerParticles.class) == null) {
            Main.getInstance().instances.put(PlayerParticles.class, new PlayerParticles());
        }
        return (PlayerParticles) Main.getInstance().instances.get(PlayerParticles.class);
    }

    public void init() {
        this.greenParticle = Loader.getInstance().getParticleEffect("particles/green-particle.particle");
        this.greenParticle.setPosition(Funcs.percentWidth(0), Funcs.percentHeight(5));
        this.greenParticle.scaleEffect(Main.getInstance().density / 20);

        this.deathParticle = Loader.getInstance().getParticleEffect("particles/death-particle.particle");
        this.deathParticle.setPosition(Funcs.percentWidth(0), Funcs.percentHeight(5));
        //this.deathParticle.scaleEffect(Main.getInstance().density * 10);
    }


    public void update(float deltaTime) {
        this.greenParticle.update(deltaTime);
        // when dying
        if (Player.getInstance().dying == true) {
            this.deathParticle.update(deltaTime);
        }
    }

    public void drawBackground(float deltaTime) {
        this.greenParticle.draw(Main.getInstance().batch);
    }
    public void drawForeground(float deltaTime) {
        if (Player.getInstance().dying == true) {
            this.deathParticle.draw(Main.getInstance().batch);
        }
    }
    @Override
    public void loadAssets() {
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, "particles/green-particle.particle", Loader.AssetType.PARTICLE_EFFECT);
        Loader.getInstance().addAsset(Main.getInstance().assetsGroupName, "particles/death-particle.particle", Loader.AssetType.PARTICLE_EFFECT);
    }
}
