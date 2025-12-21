package com.groupB.Frontend.strategy;

import com.groupB.Frontend.pools.BulletPool;
import com.groupB.Frontend.entity.Bullet;
import java.util.List;

public class SpreadShotStrategy implements ShootingStrategy {
    private BulletPool pool;
    private List<Bullet> bullets;

    public SpreadShotStrategy(List<Bullet> bullets) {
        this.bullets = bullets;
        this.pool = new BulletPool();
    }

    @Override
    public void shoot(float x, float y) {
        Bullet b1 = pool.obtain();
        b1.init(x - 20, y);
        bullets.add(b1);
        Bullet b2 = pool.obtain();
        b2.init(x, y);
        bullets.add(b2);
        Bullet b3 = pool.obtain();
        b3.init(x + 20, y);
        bullets.add(b3);
    }
}
