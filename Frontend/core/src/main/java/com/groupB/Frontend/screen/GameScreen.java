package com.groupB.Frontend.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Align;
import com.groupB.Frontend.Assets;
import com.groupB.Frontend.SpaceShooterGame;
import com.groupB.Frontend.entity.*;
import com.groupB.Frontend.manager.GameManager;
import com.groupB.Frontend.strategy.SingleShotStrategy;
import java.util.*;

public class GameScreen implements Screen {
    public enum GameState { PLAYING, WIN, GAME_OVER }
    private SpaceShooterGame game;
    private SpriteBatch batch;
    private BasicSpaceShip ship;
    private List<Bullet> bullets = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private List<ExplosionParticle> explosions = new ArrayList<>();

    private float enemySpawnTimer = 0, shootTimer = 0, holdTimer = 0, levelUpTimer = 0;
    private BitmapFont uiFont;
    private GameState gameState = GameState.PLAYING;
    private final int SCREEN_SIZE = 800;
    private int currentLevel = 1;

    // BOSS SETTINGS
    private Vector2 bossPos = new Vector2(250, 900);
    private int bossHP = 200; // Darah boss gue sesuain dikit
    private boolean bossActive = false;

    private class ExplosionParticle {
        float x, y, timer;
        ExplosionParticle(float x, float y) { this.x = x; this.y = y; this.timer = 0.3f; }
    }

    public GameScreen(SpaceShooterGame game) { this.game = game; }

    @Override
    public void show() {
        batch = new SpriteBatch();
        uiFont = new BitmapFont();
        uiFont.getData().setScale(2.5f);
        ship = new BasicSpaceShip(400, 100);
        ship.setShootingStrategy(new SingleShotStrategy(bullets));
        GameManager.getInstance().reset();
        if (Assets.backgroundMusic != null) Assets.backgroundMusic.play();
    }

    @Override
    public void render(float delta) {
        update(delta);

        if (gameState == GameState.GAME_OVER) Gdx.gl.glClearColor(0.5f, 0, 0, 1);
        else Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(Assets.background, 0, 0, SCREEN_SIZE, SCREEN_SIZE);

        if (gameState == GameState.PLAYING) {
            if (bossActive) {
                batch.draw(Assets.finalBoss, bossPos.x, bossPos.y, 300, 300);
                batch.setColor(Color.RED);
                batch.draw(Assets.whitePixel, bossPos.x, bossPos.y + 310, (bossHP / 200f) * 300, 8);
                batch.setColor(Color.WHITE);
            }

            for (ExplosionParticle ep : explosions) {
                batch.setColor(1, 1, 1, ep.timer * 3);
                batch.draw(Assets.whitePixel, ep.x, ep.y, 30, 30);
            }
            batch.setColor(Color.WHITE);

            for (Bullet b : bullets) {
                if (b.active) {
                    batch.setColor(Color.PINK);
                    batch.draw(Assets.whitePixel, b.x - 4, b.y, 8, b.height);
                }
            }
            batch.setColor(Color.WHITE);

            for (Enemy e : enemies) { e.render(batch); }

            ship.updateTexture(currentLevel);
            batch.draw(ship.getCurrentTexture(), ship.getPosition().x - 50, ship.getPosition().y - 50, 100, 100);

            uiFont.draw(batch, "SCORE: " + GameManager.getInstance().getScore(), 20, 780);
            uiFont.draw(batch, "HP: " + ship.getHealth(), 20, 730);
            uiFont.draw(batch, "LVL: " + currentLevel, 630, 780);

            if (levelUpTimer > 0) {
                uiFont.getData().setScale(4f);
                uiFont.setColor(Color.YELLOW);
                String msg = (bossActive) ? "FINAL BOSS!" : "LEVEL UP!";
                uiFont.draw(batch, msg, 0, 450, SCREEN_SIZE, Align.center, false);
                uiFont.getData().setScale(2.5f);
                uiFont.setColor(Color.WHITE);
            }
        } else {
            uiFont.getData().setScale(4f);
            String txt = (gameState == GameState.WIN) ? "VICTORY!" : "GAME OVER";
            uiFont.draw(batch, txt, 0, 450, SCREEN_SIZE, Align.center, false);
            uiFont.getData().setScale(2f);
            uiFont.draw(batch, "PRESS SPACE TO RESTART", 0, 350, SCREEN_SIZE, Align.center, false);
        }
        batch.end();
    }

    private void update(float delta) {
        if (gameState != GameState.PLAYING) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) resetGame();
            return;
        }

        int score = GameManager.getInstance().getScore();

        // --- SCORE THRESHOLDS SESUAI REQUEST ---
        if (score >= 5000 && currentLevel == 1) {
            currentLevel = 2;
            levelUpTimer = 2.0f;
        }
        if (score >= 15000 && !bossActive) { // BOSS MUNCUL DI 15.000
            bossActive = true;
            enemies.clear();
            levelUpTimer = 3.0f;
        }
        if (score >= 20000) { // WIN DI 20.000
            gameState = GameState.WIN;
        }

        if (levelUpTimer > 0) levelUpTimer -= delta;

        // Kontrol Kapal
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) ship.getPosition().x -= 700 * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) ship.getPosition().x += 700 * delta;
        ship.getPosition().x = MathUtils.clamp(ship.getPosition().x, 50, 750);

        // Tembak
        shootTimer += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            holdTimer += delta;
            if (holdTimer > 0.4f && shootTimer > 0.15f) {
                bullets.add(new Bullet(ship.getPosition().x, ship.getPosition().y + 80, 150));
                shootTimer = 0;
            }
        } else {
            if (holdTimer > 0 && holdTimer <= 0.4f) bullets.add(new Bullet(ship.getPosition().x, ship.getPosition().y + 80, 40));
            holdTimer = 0;
        }

        for (Bullet b : bullets) b.y += 1200 * delta;
        bullets.removeIf(b -> !b.active || b.y > 850);
        explosions.removeIf(ep -> (ep.timer -= delta) <= 0);

        if (!bossActive) {
            if ((enemySpawnTimer += delta) > 0.5f) {
                enemies.add(new OrcEnemy(MathUtils.random(100, 700), 850, currentLevel));
                enemySpawnTimer = 0;
            }
            for (Enemy e : enemies) e.update(delta, ship.getPosition().x, ship.getPosition().y);
            enemies.removeIf(e -> !e.isAlive() || e.getY() < -150);
        } else {
            if (bossPos.y > 400) bossPos.y -= 90 * delta;
            bossPos.x = 250 + MathUtils.sin(Gdx.graphics.getFrameId() * 0.05f) * 150;
        }

        checkCollisions();
    }

    private void checkCollisions() {
        for (Bullet b : bullets) {
            if (!b.active) continue;
            Rectangle bRect = new Rectangle(b.x - 4, b.y, 8, b.height);

            if (bossActive && bRect.overlaps(new Rectangle(bossPos.x, bossPos.y, 300, 300))) {
                bossHP -= (b.height > 100) ? 5 : 2;
                b.active = false;
                explosions.add(new ExplosionParticle(b.x, b.y));
                if (bossHP <= 0) {
                    GameManager.getInstance().addScore(5000); // Ngasih 5k poin biar lsg 20k
                    bossActive = false;
                }
            }

            for (Enemy e : enemies) {
                if (e.isAlive() && bRect.overlaps(new Rectangle(e.getX(), e.getY(), 120, 120))) {
                    e.takeDamage(100);
                    b.active = false;
                    explosions.add(new ExplosionParticle(e.getX() + 45, e.getY() + 45));
                    GameManager.getInstance().addScore(100);
                    break;
                }
            }
        }

        Rectangle sRect = new Rectangle(ship.getPosition().x - 40, ship.getPosition().y - 40, 80, 80);
        for (Enemy e : enemies) {
            if (e.isAlive() && sRect.overlaps(new Rectangle(e.getX(), e.getY(), 120, 120))) {
                ship.takeDamage(1);
                e.takeDamage(100);
                explosions.add(new ExplosionParticle(e.getX() + 45, e.getY() + 45));
                if (ship.getHealth() <= 0) gameState = GameState.GAME_OVER;
            }
        }

        if (bossActive && sRect.overlaps(new Rectangle(bossPos.x, bossPos.y, 300, 300))) {
            ship.takeDamage(1);
            if (ship.getHealth() <= 0) gameState = GameState.GAME_OVER;
        }
    }

    private void resetGame() {
        bullets.clear(); enemies.clear(); explosions.clear();
        currentLevel = 1; bossActive = false; bossHP = 200; levelUpTimer = 0;
        bossPos.set(250, 900); gameState = GameState.PLAYING; show();
    }

    @Override public void dispose() { batch.dispose(); uiFont.dispose(); }
    @Override public void hide() { if (Assets.backgroundMusic != null) Assets.backgroundMusic.pause(); }
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
}
