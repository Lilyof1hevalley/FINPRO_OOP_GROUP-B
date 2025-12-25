package com.groupB.Frontend.entity;

import com.groupB.Frontend.observer.Observable;
import com.groupB.Frontend.observer.Observer;
import com.groupB.Frontend.strategy.ShootingStrategy;
import com.badlogic.gdx.math.Vector2;

public interface SpaceShip extends Observable {
    void takeDamage(int damage);

    void fire();

    int getHealth();

    Vector2 getPosition();

    void setShootingStrategy(ShootingStrategy strategy);

    void addObserver(Observer observer);
}
