package com.groupB.Frontend.entity;

import com.example.spaceshooter.observer.Observable;
import com.example.spaceshooter.strategy.ShootingStrategy;
import com.badlogic.gdx.math.Vector2;

public interface SpaceShip extends Observable {
    void takeDamage(int damage);

    void fire();

    int getHealth();

    Vector2 getPosition();

    void setShootingStrategy(ShootingStrategy strategy);
