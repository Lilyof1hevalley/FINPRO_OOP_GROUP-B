package com.groupB.Frontend.factory;

import com.groupB.Frontend.entity.Enemy;
import com.groupB.Frontend.entity.GoblinEnemy;
import com.groupB.Frontend.factory.EnemyFactory;

public class GoblinEnemyFactory implements EnemyFactory {
    @Override
    public Enemy createEnemy(float x, float y) {
        return new GoblinEnemy(x, y);
    }
}
