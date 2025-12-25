package com.groupB.Frontend.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.spaceshooter.Assets;

public class BossEnemy extends Enemy {
    private float phaseTimer = 0;

    public BossEnemy() {
        super(300, 900);
        this.health = 20;
        this.speed = 40;
    }

    @Override
    public void update(float delta, float shipX, float shipY) {
        phaseTimer += delta;
        float zigzag = (float) Math.sin(phaseTimer * 2.0f) * 80;
        chase(shipX + zigzag, shipY, delta);

        if (x < 50)
            x = 50;
        if (x > 550)
            x = 550;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(1, 0, 1, 1);
        batch.draw(Assets.whitePixel, x - 50, y - 50, 100, 100);
        batch.setColor(1, 1, 1, 1);
    }

    @Override
    public void reset(float x, float y) {
    }

}
