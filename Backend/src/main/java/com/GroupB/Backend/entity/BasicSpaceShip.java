package java.com.GroupB.Backend.entity;

import com.badlogic.gdx.math.Vector2;
import com.example.spaceshooter.observer.Observer;
import com.example.spaceshooter.strategy.ShootingStrategy;
import com.example.spaceshooter.Assets;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.List;

public class BasicSpaceShip implements SpaceShip {
    private Vector2 position;
    private int health = 3;
    private ShootingStrategy shootingStrategy;
    private List<Observer> observers = new ArrayList<>();
    private Texture currentTexture;

    public BasicSpaceShip(float x, float y) {
        this.position = new Vector2(x, y);
        this.currentTexture = Assets.shipLevel1; // Default awal
    }

    // Method FIX: Menerima level dari GameScreen
    public void updateTexture(int level) {
        if (level == 1) currentTexture = Assets.shipLevel1;
        else if (level == 2) currentTexture = Assets.shipLevel2;
        else if (level == 3) currentTexture = Assets.shipLevel3;
    }

    public Texture getCurrentTexture() { return currentTexture; }
    @Override public void takeDamage(int damage) { this.health -= damage; notifyObservers(); }
    @Override public void fire() { if (shootingStrategy != null) shootingStrategy.shoot(position.x, position.y + 40); }
    @Override public int getHealth() { return health; }
    @Override public Vector2 getPosition() { return position; }
    @Override public void setShootingStrategy(ShootingStrategy strategy) { this.shootingStrategy = strategy; }
    @Override public void addObserver(Observer o) { observers.add(o); }
    @Override public void notifyObservers() { for (Observer o : observers) o.update(); }
}
