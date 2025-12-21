// core/src/com/example/spaceshooter/factory/EnemyFactory.java
package com.groupB.Frontend.factory;

import com.groupB.Frontend.entity.Enemy;

public interface EnemyFactory {
    Enemy createEnemy(float x, float y);
}
