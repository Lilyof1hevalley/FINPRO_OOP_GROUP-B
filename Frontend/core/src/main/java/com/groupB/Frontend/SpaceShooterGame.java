package com.groupB.Frontend;

import com.badlogic.gdx.Game;
import com.groupB.Frontend.SpaceShooterGame;
import com.groupB.Frontend.SpaceShooterGame;
import com.groupB.Frontend.Assets;
import com.groupB.Frontend.SpaceShooter.screen.NameInputScreen

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
