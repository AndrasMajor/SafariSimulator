package safariSimulator.main.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Tile.Tile;

public class MapScreen extends InputAdapter implements Screen {

    private Texture waterTexture;
    private Texture sandTexture;
    private Texture grassTexture;
    private SpriteBatch batch;
    public Map map;

    private OrthographicCamera camera;
    private float cameraSpeed = 200;
    private float zoomSpeed = 0.02f;

    public MapScreen() {
        map = new Map();
        map.generateMap();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        waterTexture = new Texture(Gdx.files.internal("water.png"));
        sandTexture = new Texture(Gdx.files.internal("sand.png"));
        grassTexture = new Texture(Gdx.files.internal("grass.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        handleInput(delta);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        Texture tileTexture;
        for (Tile tile : map.getTiles()) {
            if (tile.health == -1) tileTexture = waterTexture;
            else if (tile.health == 0) tileTexture = sandTexture;
            else tileTexture = grassTexture;

            batch.draw(tileTexture, tile.pos.getX() * 32, tile.pos.getY() * 32, 32, 32);
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.position.y += cameraSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.position.y -= cameraSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.position.x -= cameraSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.position.x += cameraSpeed * delta;
        }

        // Zoom beállítása
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom += zoomSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoom -= zoomSpeed;
        }
    }

}
