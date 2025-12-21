package java.com.GroupB.Backend.entity;

public class Bullet {
    public float x, y;
    public float speed = 500;
    public boolean active = true;
    public float height = 20;

    public Bullet() {}

    public Bullet(float x, float y, float height) {
        this.x = x;
        this.y = y;
        this.height = height;
    }

    public void update(float delta) {
        y += speed * delta;
    }

    public void init(float x, float y) {
    }
}
