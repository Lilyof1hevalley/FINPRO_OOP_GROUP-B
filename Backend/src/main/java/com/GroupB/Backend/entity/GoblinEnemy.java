package java.com.GroupB.Backend.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.spaceshooter.Assets;

public class GoblinEnemy extends Enemy {
    public GoblinEnemy(float x, float y) {
        super(x, y);
    }

    public void update(float delta) {
        y -= 150 * delta;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(0, 1, 0, 1); // hijau untuk Goblin
        batch.draw(Assets.whitePixel, x, y, 32, 32);
        batch.setColor(1, 1, 1, 1);
    }

    @Override
    public void reset(float x, float y) {
        this.x = x;
        this.y = y;
        health = 1;
    }

    @Override
    public void update(float delta, float shipX, float shipY) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}