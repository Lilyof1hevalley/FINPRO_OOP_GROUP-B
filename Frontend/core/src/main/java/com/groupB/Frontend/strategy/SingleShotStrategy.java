package com.groupB.Frontend.strategy;

import com.groupB.Frontend.entity.Bullet;
import com.groupB.Frontend.pools.BulletPool;
import java.util.List;

public class SingleShotStrategy implements ShootingStrategy {
    private BulletPool pool;
    private List<Bullet> bullets;

    public SingleShotStrategy(List<Bullet> bullets) {
        this.bullets = bullets;
        this.pool = new BulletPool();
    }

    @Override
    public void shoot(float x, float y) {
        Bullet b = pool.obtain();
        b.init(x, y);
        bullets.add(b);
    }
}
