package com.groupB.Frontend.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.example.spaceshooter.Assets;

public class OrcEnemy extends Enemy {
    public Texture texture;

    public OrcEnemy(float x, float y, int level) {
        super(x, y);
        this.health = 1 + level;
        this.speed = 250 + (level * 20);
        if (Assets.enemyTextures != null && !Assets.enemyTextures.isEmpty()) {
            this.texture = Assets.enemyTextures.get(MathUtils.random(0, Assets.enemyTextures.size() - 1));
        }
    }

    @Override
    public void update(float delta, float shipX, float shipY) { chase(shipX, shipY, delta); }

    @Override
    public void render(SpriteBatch batch) {
        if (texture != null) {
            batch.draw(texture, x, y, 120, 120);
        }
    }

    @Override
    public void reset(float x, float y) { this.x = x; this.y = y; } //
}

