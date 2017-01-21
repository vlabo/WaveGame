package com.rightovers.wave.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import java.util.HashMap;
import java.util.Map;

public class Font {
    private Map<String, BitmapFont> fonts = new HashMap<String, BitmapFont>();
    private Map<String, FileHandle> fontFiles = new HashMap<String, FileHandle>();


    public static Font getInstance() {
        if (Variables.modules.get("Font") == null) {
            Variables.modules.put("Font", new Font());
        }
        return (Font) Variables.modules.get("Font");
    }

    private Font() {
    }


    public BitmapFont getFont(String font, int size) {
        if (this.fonts.containsKey(font + size)) {
            return this.fonts.get(font + size);
        }
        else {
            return createFont(font, size);
        }
    }

    // generate font
    private BitmapFont createFont(String font, int size) {
        // load the font
        FileHandle fontFile;
        if (this.fontFiles.containsKey(font)) {
            fontFile = this.fontFiles.get(font);
        }
        else {
            fontFile = Gdx.files.internal(font);
            this.fontFiles.put(font, fontFile);
        }

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = size;
        parameter.magFilter = TextureFilter.Linear;
        parameter.minFilter = TextureFilter.Linear;

        BitmapFont bitmapFont = generator.generateFont(parameter);

        this.fonts.put(font + size, bitmapFont);
        generator.dispose();

        return bitmapFont;
    }

    // kill all fonts
    public void dispose() {
        this.fonts.clear();
        this.fontFiles.clear();
    }
}
