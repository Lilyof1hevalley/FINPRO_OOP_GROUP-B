package com.groupB.Frontend.decorator;

import com.groupB.Frontend.entity.SpaceShip;
import com.groupB.Frontend.observer.Observer;
import com.groupB.Frontend.strategy.ShootingStrategy;
import com.badlogic.gdx.math.Vector2;

public abstract class ShipDecorator implements SpaceShip {
    protected SpaceShip decoratedShip;

    public ShipDecorator(SpaceShip ship) {
        this.decoratedShip = ship;
    }

    // Delegasi semua metode ke objek yang dihiasi
    @Override
    public void takeDamage(int damage) {
        decoratedShip.takeDamage(damage);
    }

    @Override
    public void fire() {
        decoratedShip.fire();
    }

    @Override
    public int getHealth() {
        return decoratedShip.getHealth();
    }

    @Override
    public Vector2 getPosition() {
        return decoratedShip.getPosition();
    }

    @Override
    public void setShootingStrategy(ShootingStrategy strategy) {
        decoratedShip.setShootingStrategy(strategy);
    }

    @Override
    public void addObserver(Observer o) {
        decoratedShip.addObserver(o);
    }

    @Override
    public void notifyObservers() {
        decoratedShip.notifyObservers();
    }
}
