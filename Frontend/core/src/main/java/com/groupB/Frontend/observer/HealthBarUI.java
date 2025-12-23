package com.groupB.Frontend.observer;

import com.groupB.Frontend.entity.SpaceShip;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Observable;
import java.util.Observer;

public class HealthBarUI implements Observer {
    private SpaceShip ship;
    private BitmapFont font = new BitmapFont();
    private SpriteBatch batch;

    public HealthBarUI(SpaceShip ship, SpriteBatch batch) {
        this.ship = ship;
        this.batch = batch;
        ship.addObserver(this);
    }

    public void render() {
        String text = "Health: " + ship.getHealth();
        font.draw(batch, text, 20, Gdx.graphics.getHeight() - 20);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
