package com.groupB.Frontend;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.groupB.Frontend.screen.GameScreen;
import com.groupB.Frontend.screen.NameInputScreen;

public class SpaceShooterGame extends Game {
    private String playerName;

    @Override
    public void create() {
        Assets.load();
        setScreen(new NameInputScreen(this));
    }

    public void setPlayerName(String name) {
        this.playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void dispose() {
        Assets.dispose();
    }
}
