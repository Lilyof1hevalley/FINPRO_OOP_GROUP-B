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
    private boolean scoreSent = false; // Flag to prevent multiple API calls

    private Vector2 bossPos = new Vector2(250, 900);
    private int bossHP = 200;
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

        // Reset game manager and score sent flag
        GameManager.getInstance().reset(game.getPlayerName(), 0);
        scoreSent = false;

        if (Assets.backgroundMusic != null) Assets.backgroundMusic.play();
    }

    /**
     * Sends the player's final score and health to the Spring Boot Backend.
     */
    private void sendScoreToBackend(String name, int score, int health) {
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        request.setUrl("http://localhost:8080/api/score");
        request.setHeader("Content-Type", "application/json");

        // Format the JSON payload manually to match the Backend's ScoreSubmissionRequest class
        String json = "{\"playerName\":\"" + name + "\", \"score\":" + score + ", \"health\":" + health + "}";
        request.setContent(json);

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                Gdx.app.log("API", "SUCCESSFULLY SAVED TO NEON: " + httpResponse.getResultAsString());
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.error("API", "FAILED: Is the Backend running? " + t.getMessage());
            }

            @Override
            public void cancelled() {
                Gdx.app.log("API", "HTTP Request was cancelled.");
            }
        });
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
            renderGameElements();
        } else {
            renderEndScreen();
        }
        batch.end();
    }

    private void renderGameElements() {
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
        uiFont.draw(batch, "PLAYER: " + game.getPlayerName(), 600, 780);
    }

    private void renderEndScreen() {
        uiFont.getData().setScale(4f);
        String txt = (gameState == GameState.WIN) ? "VICTORY!" : "GAME OVER";
        uiFont.draw(batch, txt, 0, 450, SCREEN_SIZE, Align.center, false);
        uiFont.getData().setScale(2f);
        uiFont.draw(batch, "FINAL SCORE: " + GameManager.getInstance().getScore(), 0, 380, SCREEN_SIZE, Align.center, false);
        uiFont.draw(batch, "PRESS SPACE TO RESTART", 0, 300, SCREEN_SIZE, Align.center, false);
    }

    private void update(float delta) {
        if (gameState != GameState.PLAYING) {
            // Trigger score submission once when transitioning to non-playing state
            if (!scoreSent) {
                String nameToSave = (game.getPlayerName() == null || game.getPlayerName().isEmpty()) ? "Player1" : game.getPlayerName();
                sendScoreToBackend(nameToSave, GameManager.getInstance().getScore(), ship.getHealth());
                scoreSent = true;
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) resetGame();
            return;
        }

        handleLevelProgression(delta);
        handleInput(delta);
        updateEntities(delta);
        checkCollisions();
    }

    private void handleLevelProgression(float delta) {
        int score = GameManager.getInstance().getScore();
        if (score >= 5000 && currentLevel == 1) currentLevel = 2;
        if (score >= 15000 && !bossActive) {
            bossActive = true;
            enemies.clear();
        }
        if (score >= 20000) gameState = GameState.WIN;
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) ship.getPosition().x -= 700 * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) ship.getPosition().x += 700 * delta;
        ship.getPosition().x = MathUtils.clamp(ship.getPosition().x, 50, 750);

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
    }

    private void updateEntities(float delta) {
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
    }

    private void checkCollisions() {
        Rectangle sRect = new Rectangle(ship.getPosition().x - 40, ship.getPosition().y - 40, 80, 80);

        // Ship collision with Boss or Enemies
        if (bossActive && sRect.overlaps(new Rectangle(bossPos.x, bossPos.y, 300, 300))) {
            ship.takeDamage(100); // Instant death on boss collision
        }

        for (Enemy e : enemies) {
            if (e.isAlive() && sRect.overlaps(new Rectangle(e.getX(), e.getY(), 120, 120))) {
                ship.takeDamage(1);
                e.takeDamage(100);
            }
        }

        // Bullet collisions
        for (Bullet b : bullets) {
            if (!b.active) continue;
            Rectangle bRect = new Rectangle(b.x - 4, b.y, 8, b.height);

            if (bossActive && bRect.overlaps(new Rectangle(bossPos.x, bossPos.y, 300, 300))) {
                bossHP -= (b.height > 100) ? 5 : 2;
                b.active = false;
                if (bossHP <= 0) {
                    GameManager.getInstance().addScore(5000);
                    bossActive = false;
                }
            }
            for (Enemy e : enemies) {
                if (e.isAlive() && bRect.overlaps(new Rectangle(e.getX(), e.getY(), 120, 120))) {
                    e.takeDamage(100);
                    b.active = false;
                    GameManager.getInstance().addScore(100);
                    break;
                }
            }
        }

        // Check if Game Over
        if (ship.getHealth() <= 0) {
            gameState = GameState.GAME_OVER;
        }
    }

    private void resetGame() {
        bullets.clear(); enemies.clear(); explosions.clear();
        currentLevel = 1; bossActive = false; bossHP = 200;
        bossPos.set(250, 900); gameState = GameState.PLAYING;
        show();
    }

    @Override public void dispose() { batch.dispose(); uiFont.dispose(); }
    @Override public void hide() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
}
