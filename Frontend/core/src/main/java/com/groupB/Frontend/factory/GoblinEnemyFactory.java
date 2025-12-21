package com.groupB.Frontend.factory;

import com.groupB.Frontend.entity.Enemy;
import com.groupB.Frontend.entity.OrcEnemy;

public class OrcEnemyFactory implements EnemyFactory {
    @Override
    public Enemy createEnemy(float x, float y) {
        return new OrcEnemy(x, y, 1);
    }
}
