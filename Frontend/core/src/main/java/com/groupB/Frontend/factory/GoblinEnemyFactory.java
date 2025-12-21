package com.example.spaceshooter.factory;

import com.example.spaceshooter.entity.Enemy;
import com.example.spaceshooter.entity.OrcEnemy;

public class OrcEnemyFactory implements EnemyFactory {
    @Override
    public Enemy createEnemy(float x, float y) {
        return new OrcEnemy(x, y, 1);
    }
}
