package java.com.GroupB.Backend.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.spaceshooter.Assets;

public class BossEnemy extends Enemy {
    private float phaseTimer = 0;

    public BossEnemy() {
        super(300, 900); // mulai di luar layar atas
        this.health = 20; // sangat sulit
        this.speed = 40; // lambat tapi mengejar
    }

    @Override
    public void update(float delta, float shipX, float shipY) {
        // Gerak zig-zag + mengejar
        phaseTimer += delta;
        float zigzag = (float) Math.sin(phaseTimer * 2.0f) * 80;
        chase(shipX + zigzag, shipY, delta);

        // Jangan biarkan boss keluar layar horizontal
        if (x < 50)
            x = 50;
        if (x > 550)
            x = 550;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(1, 0, 1, 1); // ungu
        batch.draw(Assets.whitePixel, x - 50, y - 50, 100, 100); // ukuran besar
        batch.setColor(1, 1, 1, 1); // reset ke putih
    }

    @Override
    public void reset(float x, float y) {
        // Boss tidak di-reset, hanya di-spawn sekali
    }

}