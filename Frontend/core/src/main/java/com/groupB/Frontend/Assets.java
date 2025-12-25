package com.groupB.Frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import java.util.ArrayList;
import java.util.List;

public class Assets {
    public static Texture whitePixel, background, shipLevel1, shipLevel2, shipLevel3, finalBoss;
    public static List<Texture> enemyTextures = new ArrayList<>();

    public static Music backgroundMusic;
    public static Sound shootSound;
    public static Sound explosionSound;

    public static void load() {
        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        whitePixel = new Texture(pixmap);
        pixmap.dispose();

        background = new Texture("bg.jpeg");
        shipLevel1 = new Texture("level1.png");
        shipLevel2 = new Texture("level2.png");
        shipLevel3 = new Texture("level3.png");
        finalBoss = new Texture("final boss.png");

        String[] enemies = {"alvin.png", "azra.png", "benedict.png", "deandro.png", "dimas dermawan.png", "fairuz muhammad.png", "george william.png", "ikhsan.png", "kamal.png", "musyaffa.png", "nelson.png", "raditya alif.png", "raihan ihsan.png", "ryan.png", "sofwan.png", "yasmin.png"};
        for (String file : enemies) { enemyTextures.add(new Texture(Gdx.files.internal(file))); }

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bgm.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
    }

    public static void dispose() {
        if (whitePixel != null) whitePixel.dispose();
        if (background != null) background.dispose();
        if (shipLevel1 != null) shipLevel1.dispose();
        if (shipLevel2 != null) shipLevel2.dispose();
        if (shipLevel3 != null) shipLevel3.dispose();
        if (finalBoss != null) finalBoss.dispose();
        if (backgroundMusic != null) backgroundMusic.dispose();
        for (Texture t : enemyTextures) t.dispose();
    }
}
