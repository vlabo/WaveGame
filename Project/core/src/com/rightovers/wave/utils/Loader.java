package com.rightovers.wave.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.HashMap;

public class Loader {
    private boolean debug = false;
    // lists with data paths, by groups
    public HashMap<String, ArrayList<String>> musicAssets;
    public HashMap<String, ArrayList<String>> soundAssets;
    public HashMap<String, ArrayList<String>> textureAssets;
    public HashMap<String, ArrayList<String>> fileAssets;
    public HashMap<String, ArrayList<String>> textureAtlasAssets;

    // array with all group names
    public HashMap<String, Boolean> groups;

    // array with all group names
    public HashMap<String, Boolean> currLoadingGroups;

    // asset manager
    public AssetManager manager;

    public enum AssetType {
        TEXTURE,
        MUSIC,
        SOUND,
        PARTICLE_EFFECT,
        TEXTURE_ATLAS
    }

    public static Loader getInstance() {
        if (Variables.modules.get("Loader") == null) {
            Variables.modules.put("Loader", new Loader());
        }
        return (Loader) Variables.modules.get("Loader");
    }

    private Loader() {
        this.musicAssets = new HashMap<String, ArrayList<String>>();
        this.soundAssets = new HashMap<String, ArrayList<String>>();
        this.textureAssets = new HashMap<String, ArrayList<String>>();
        this.fileAssets = new HashMap<String, ArrayList<String>>();
        this.textureAtlasAssets = new HashMap<String, ArrayList<String>>();
        this.groups = new HashMap<String, Boolean>();
        this.currLoadingGroups = new HashMap<String, Boolean>();
        this.manager = new AssetManager();
    }

    public Boolean isGroupLoading(String group) {
        if (this.currLoadingGroups.get(group) != null && this.currLoadingGroups.get(group) == true) {
            return true;
        }
        return false;
    }

    public Boolean isGroupLoaded(String group) {
        if (this.groups.get(group) != null && this.groups.get(group) == true) {
            return true;
        }
        return false;
    }

    public Boolean isGroupAdded(String group) {
        if (this.groups.get(group) != null) {
            return true;
        }
        return false;
    }

    public Boolean isAssetAdded(String group, String name, AssetType type) {
        if (this.groups.get(group) != null) {
            switch (type) {
                case TEXTURE:
                    if (this.textureAssets.get(group) == null) {
                        return this.textureAssets.get(group).contains(name);
                    }
                    break;
                case SOUND:
                    if (this.soundAssets.get(group) == null) {
                        return this.soundAssets.get(group).contains(name);
                    }
                    break;
                case MUSIC:
                    if (this.musicAssets.get(group) == null) {
                        return this.musicAssets.get(group).contains(name);
                    }
                    break;
                case PARTICLE_EFFECT:
                    if (this.fileAssets.get(group) == null) {
                        return this.fileAssets.get(group).contains(name);
                    }
                    break;
                case TEXTURE_ATLAS:
                    if (this.textureAtlasAssets.get(group) == null) {
                        return this.textureAtlasAssets.get(group).contains(name);
                    }
                    break;
            }
            return false;
        }
        return false;
    }

    // add asset with filter
    public Boolean addAsset(String group, String name, AssetType type) {
        return addAsset(group, name, type, true);
    }

    // add asset to group for loading /only if the group is not added to loading queue and the group is not loaded/
    public Boolean addAsset(String group, String name, AssetType type, boolean filter) {
        if (this.currLoadingGroups.get(group) == null && (this.groups.get(group) == null || this.groups.get(group) == false)) {

            this.groups.put(group, false);
            switch (type) {
                case TEXTURE:
                    if (this.textureAssets.get(group) == null) {
                        this.textureAssets.put(group, new ArrayList<String>());
                    }
                    // add noFilter string to the name
                    if (filter == false) {
                        name = name + ".NF";
                    }
                    this.textureAssets.get(group).add(name);
                    break;
                case SOUND:
                    if (this.soundAssets.get(group) == null) {
                        this.soundAssets.put(group, new ArrayList<String>());
                    }
                    this.soundAssets.get(group).add(name);
                    break;
                case MUSIC:
                    if (this.musicAssets.get(group) == null) {
                        this.musicAssets.put(group, new ArrayList<String>());
                    }
                    this.musicAssets.get(group).add(name);
                    break;
                case PARTICLE_EFFECT:
                    if (this.fileAssets.get(group) == null) {
                        this.fileAssets.put(group, new ArrayList<String>());
                    }
                    this.fileAssets.get(group).add(name);
                    break;
                case TEXTURE_ATLAS:
                    if (this.textureAtlasAssets.get(group) == null) {
                        this.textureAtlasAssets.put(group, new ArrayList<String>());
                    }
                    this.textureAtlasAssets.get(group).add(name);
                    break;
            }
            if (this.debug == true) {
                Funcs.print("Loader: Success on adding asset " + name + " to group " + group);
            }
            return true;
        }
        if (this.debug == true) {
            Funcs.print("Loader: Error on adding asset " + name + " to group " + group);
        }
        return false;
    }

    // remove group asset from the data lists /only if the group is not added to loading queue and the group is not loaded/
    public Boolean removeAsset(String group, String name, AssetType type) {
        if (this.currLoadingGroups.get(group) == null && (this.groups.get(group) == null || this.groups.get(group) == false)) {
            switch (type) {
                case TEXTURE:
                    if (this.textureAssets.get(group) != null) {
                        this.textureAssets.get(group).remove(name);
                    }
                    break;
                case SOUND:
                    if (this.soundAssets.get(group) != null) {
                        this.soundAssets.get(group).remove(name);
                    }
                    break;
                case MUSIC:
                    if (this.musicAssets.get(group) != null) {
                        this.musicAssets.get(group).remove(name);
                    }
                    break;
                case PARTICLE_EFFECT:
                    if (this.fileAssets.get(group) != null) {
                        this.fileAssets.get(group).remove(name);
                    }
                    break;
                case TEXTURE_ATLAS:
                    if (this.textureAtlasAssets.get(group) != null) {
                        this.textureAtlasAssets.get(group).remove(name);
                    }
                    break;
            }
            if (this.debug == true) {
                Funcs.print("Loader: Success on removing asset " + name + " to group " + group);
            }
            return true;
        }
        if (this.debug == true) {
            Funcs.print("Loader: Error on removing asset " + name + " to group " + group);
        }
        return false;
    }

    // remove group from the data lists /only if the group is not added to loading queue and the group is not loaded/
    public Boolean removeGroup(String group) {
        if (this.currLoadingGroups.get(group) == null && (this.groups.get(group) == null || this.groups.get(group) == false)) {
            this.textureAssets.put(group, null);
            this.soundAssets.put(group, null);
            this.musicAssets.put(group, null);
            this.textureAtlasAssets.put(group, null);
            this.fileAssets.put(group, null);
            return true;
        }
        return false;
    }


    // add the entire group of assets /music,audio and textures/ to the loading queue
    public Boolean addToLoadingQueue(String group) {
        if (this.groups.get(group) != null && this.groups.get(group) == false) {
            this.currLoadingGroups.put(group, true);

            ArrayList<String> musics = this.musicAssets.get(group);
            ArrayList<String> sounds = this.soundAssets.get(group);
            ArrayList<String> textures = this.textureAssets.get(group);
            ArrayList<String> files = this.fileAssets.get(group);
            ArrayList<String> textureAtlasses = this.textureAtlasAssets.get(group);
            if (musics != null) {
                for (String music : musics) {
                    this.manager.load(music, Music.class);
                }
            }
            if (sounds != null) {
                for (String sound : sounds) {
                    this.manager.load(sound, Sound.class);
                }
            }
            if (files != null) {
                for (String file : files) {
                    this.manager.load(file, ParticleEffect.class);
                }
            }
            if (textures != null) {
                for (String texture : textures) {
                    if (texture.contains(".NF")) {
                        texture = texture.replace(".NF", "");
                        this.manager.load(texture, Texture.class);
                    }
                    else {
                        TextureParameter param = new TextureParameter();
                        param.minFilter = TextureFilter.Linear;
                        param.magFilter = TextureFilter.Linear;
                        this.manager.load(texture, Texture.class, param);
                    }
                }
            }
            if (textureAtlasses != null) {
                for (String textureAtlas : textureAtlasses) {
                    this.manager.load(textureAtlas, TextureAtlas.class);
                }
            }

        }
        return true;
    }


    // unload the entire group of assets
    public Boolean unload(String group) {
        if (this.groups.get(group) == true) {

            ArrayList<String> musics = this.musicAssets.get(group);
            ArrayList<String> sounds = this.soundAssets.get(group);
            ArrayList<String> textures = this.textureAssets.get(group);
            ArrayList<String> files = this.fileAssets.get(group);
            ArrayList<String> textureAtlasses = this.textureAtlasAssets.get(group);
            if (musics != null) {
                for (String music : musics) {
                    this.manager.unload(music);
                }
            }
            if (sounds != null) {
                for (String sound : sounds) {
                    this.manager.unload(sound);
                }
            }
            if (files != null) {
                for (String file : files) {
                    this.manager.unload(file);
                }
            }
            if (textures != null) {
                for (String texture : textures) {
                    this.manager.unload(texture);
                }
            }
            if (textureAtlasses != null) {
                for (String textureAtlas : textureAtlasses) {
                    this.manager.unload(textureAtlas);
                }
            }
            this.groups.put(group, false);
            return true;
        }
        return false;
    }

    // load some of the loading queue
    public Boolean update() {
        if (this.currLoadingGroups.isEmpty() == false) {
            if (this.manager.update()) {

                for (String group : this.currLoadingGroups.keySet()) {
                    this.groups.put(group, true);
                }
                this.currLoadingGroups.clear();
                return true;
            }
            return false;
        }
        return true;
    }

    // load some of the loading queue
    public Boolean finish() {
        if (this.currLoadingGroups.isEmpty() == false) {
            this.manager.finishLoading();
            for (String group : this.currLoadingGroups.keySet()) {
                this.groups.put(group, true);
            }
            this.currLoadingGroups.clear();
            return true;
        }
        return false;
    }


    // getters for assets
    public Texture getTexture(String name) {
        return this.manager.get(name, Texture.class);
    }

    public Sound getSound(String name) {
        return this.manager.get(name, Sound.class);
    }

    public Music getMusic(String name) {
        return this.manager.get(name, Music.class);
    }

    public TextureAtlas getTextureAtlas(String name) {
        return this.manager.get(name, TextureAtlas.class);
    }

    public ParticleEffect getParticleEffect(String name) {
        return this.manager.get(name, ParticleEffect.class);
    }

    // dispose all data
    public void dispose() {
        this.manager.clear();
        this.musicAssets = null;
        this.soundAssets = null;
        this.textureAssets = null;
        this.fileAssets = null;
        this.groups = null;
        this.currLoadingGroups = null;
        this.manager = null;
        Variables.modules.put("Loader", null);
    }
}
