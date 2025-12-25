package com.groupB.Frontend.pools;

import com.badlogic.gdx.utils.Pool;
import com.groupB.Frontend.entity.Bullet;

public class BulletPool extends Pool<Bullet> {
    @Override
    protected Bullet newObject() {
        returnnew Bullet;
        return null;
    }
}
