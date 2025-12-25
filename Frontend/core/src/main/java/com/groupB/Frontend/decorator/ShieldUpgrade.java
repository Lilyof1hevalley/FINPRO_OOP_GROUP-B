package com.groupB.Frontend.decorator;

import com.groupB.Frontend.entity.SpaceShip;

public class ShieldUpgrade extends ShipDecorator {
    public ShieldUpgrade(SpaceShip ship) {
        super(ship);
    }

    @Override
    public void takeDamage(int damage) {
        decoratedShip.takeDamage(damage / 2); // serap 50% damage
    }
}
