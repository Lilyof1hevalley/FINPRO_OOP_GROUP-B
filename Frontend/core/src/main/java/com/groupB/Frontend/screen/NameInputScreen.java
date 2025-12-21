package com.groupB.Frontend.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.groupB.Frontend.SpaceShooterGame.Assets;
import com.groupB.Frontend.SpaceShooterGame;

public class NameInputScreen implements Screen {
    private SpaceShooterGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private final int SCREEN_SIZE = 800; // Rasio 1:1

    private Texture logoTexture;
    private Rectangle playButtonBounds;
    private boolean isButtonPressed = false;

    public NameInputScreen(SpaceShooterGame game) { this.game = game; }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(1.5f);

        logoTexture = new Texture(Gdx.files.internal("logo.png"));

        float btnW = 200, btnH = 60;
        float btnX = (SCREEN_SIZE / 2f) - (btnW / 2f);
        float btnY = 250;
        playButtonBounds = new Rectangle(btnX, btnY, btnW, btnH);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int x, int y, int p, int b) {
                if (playButtonBounds.contains(x, SCREEN_SIZE - y)) isButtonPressed = true;
                return true;
            }
            @Override
            public boolean touchUp(int x, int y, int p, int b) {
                if (isButtonPressed && playButtonBounds.contains(x, SCREEN_SIZE - y)) {
                    game.setPlayerName("Player"); // Nama default karena input diapus
                    game.setScreen(new GameScreen(game));
                }
                isButtonPressed = false;
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        float logoSize = 400;
        float logoX = (SCREEN_SIZE / 2f) - (logoSize / 2f);
        batch.draw(logoTexture, logoX, 400, logoSize, logoSize);

        batch.setColor(isButtonPressed ? Color.MAROON : Color.PINK);
        batch.draw(Assets.whitePixel, playButtonBounds.x, playButtonBounds.y, playButtonBounds.width, playButtonBounds.height);

        batch.setColor(Color.WHITE);
        font.setColor(Color.WHITE);
        font.draw(batch, "PLAY", playButtonBounds.x, playButtonBounds.y + 42, playButtonBounds.width, Align.center, false);

        batch.setColor(Color.WHITE);
        batch.end();
    }

    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void resize(int w, int h) {}
    @Override public void dispose() { batch.dispose(); font.dispose(); logoTexture.dispose(); }
}
