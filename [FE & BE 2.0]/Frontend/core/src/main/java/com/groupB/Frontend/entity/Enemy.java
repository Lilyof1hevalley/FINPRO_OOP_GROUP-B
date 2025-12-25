package com.groupB.Frontend.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Enemy {
    protected float x, y;
    protected int health;
    protected float speed;

    public Enemy(float x, float y) {
        this.x = x;
        this.y = y;
    }

    protected void chase(float targetX, float targetY, float delta) {
        float dx = targetX - x;
        float dy = targetY - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance > 0) {
            x += (dx / distance) * speed * delta;
            y += (dy / distance) * speed * delta;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0)
            this.health = 0;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public abstract void update(float delta, float shipX, float shipY);

    public abstract void render(SpriteBatch batch);

    public abstract void reset(float x, float y);

    public void update(float delta) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public int getScore() {

        return 0;
    }

    public Object addScore(int i) {
        return null;
    }
}
