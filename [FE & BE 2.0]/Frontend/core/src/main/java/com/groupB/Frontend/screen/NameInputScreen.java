package com.groupB.Frontend.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.groupB.Frontend.Assets;
import com.groupB.Frontend.SpaceShooterGame;

public class NameInputScreen implements Screen {
    private SpaceShooterGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera cam;
    private FitViewport viewport;
    private Vector3 touchPoint = new Vector3();

    private Texture logoTexture;
    private Rectangle playButtonBounds;
    private boolean isButtonPressed = false;
    private boolean isInputMode = false; // Flag untuk ganti tampilan

    private Stage stage;
    private TextField usernameField;
    private Skin skin;

    public NameInputScreen(SpaceShooterGame game) { this.game = game; }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2.5f); // Font Gede & Rapi

        cam = new OrthographicCamera();
        viewport = new FitViewport(800, 800, cam);
        viewport.apply();
        cam.position.set(400, 400, 0);

        logoTexture = new Texture(Gdx.files.internal("logo.png"));
        playButtonBounds = new Rectangle(400 - 150, 300, 300, 80);

        // Setup Stage untuk TextField
        stage = new Stage(viewport, batch);

        // Buat Skin Box Input
        skin = new Skin();
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = font;
        style.fontColor = Color.valueOf("FF69B4"); // Warna Pink pas ngetik
        style.background = skin.newDrawable("white", Color.WHITE);
        style.cursor = skin.newDrawable("white", Color.BLACK);
        skin.add("default", style);

        usernameField = new TextField("", skin);
        usernameField.setMessageText("NAME...");
        usernameField.setSize(450, 80);
        usernameField.setPosition(400 - 225, 400);
        usernameField.setAlignment(Align.center);
        usernameField.setVisible(false); // Sembunyi dulu
        stage.addActor(usernameField);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int x, int y, int p, int b) {
                viewport.unproject(touchPoint.set(x, y, 0));
                if (!isInputMode && playButtonBounds.contains(touchPoint.x, touchPoint.y)) {
                    isButtonPressed = true;
                }
                return true;
            }

            @Override
            public boolean touchUp(int x, int y, int p, int b) {
                viewport.unproject(touchPoint.set(x, y, 0));
                if (isButtonPressed && playButtonBounds.contains(touchPoint.x, touchPoint.y)) {
                    isInputMode = true; // Masuk ke mode input nama
                    usernameField.setVisible(true);
                    Gdx.input.setInputProcessor(stage); // Ganti input ke keyboard stage
                    stage.setKeyboardFocus(usernameField);
                }
                isButtonPressed = false;
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        if (isInputMode) {
            Gdx.gl.glClearColor(1, 0.75f, 0.8f, 1); // BG PINK
        } else {
            Gdx.gl.glClearColor(1, 1, 1, 1); // BG PUTIH AWAL
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        batch.setProjectionMatrix(cam.combined);

        batch.begin();
        if (!isInputMode) {
            // TAMPILAN AWAL
            batch.draw(logoTexture, 400 - 200, 450, 400, 400);
            batch.setColor(isButtonPressed ? Color.MAROON : Color.PINK);
            batch.draw(Assets.whitePixel, playButtonBounds.x, playButtonBounds.y, playButtonBounds.width, playButtonBounds.height);
            batch.setColor(Color.WHITE);
            font.draw(batch, "PLAY", playButtonBounds.x, playButtonBounds.y + 55, playButtonBounds.width, Align.center, false);
        } else {
            // TAMPILAN INPUT NAMA
            font.setColor(Color.WHITE);
            font.draw(batch, "ENTER YOUR NAME", 0, 580, 800, Align.center, false);
            font.draw(batch, "PRESS ENTER TO START", 0, 320, 800, Align.center, false);

            // Cek Input Enter
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                String name = usernameField.getText().trim();
                game.setPlayerName(name.isEmpty() ? "Player1" : name);
                game.setScreen(new GameScreen(game));
            }
        }
        batch.end();

        if (isInputMode) {
            stage.act(delta);
            stage.draw();
        }
    }

    @Override public void resize(int w, int h) { viewport.update(w, h); }
    @Override public void dispose() { batch.dispose(); font.dispose(); stage.dispose(); logoTexture.dispose(); skin.dispose(); }
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}
