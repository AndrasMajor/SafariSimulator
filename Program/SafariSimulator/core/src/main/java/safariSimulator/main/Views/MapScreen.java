package safariSimulator.main.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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

    private Viewport minimapViewport;
    private OrthographicCamera minimapCamera;
    private static final int MINIMAP_SIZE = 100;
    private static final float MINIMAP_SCALE = 0.05f;

    public Stage stage;
    //private Skin skin;
    public TextButton shopButton;
    private boolean isShopVisible = false;

    private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    public ShopContainer shopContainer;

    public MapScreen() {
        map = new Map();
        map.generateMap();
        shopContainer = new ShopContainer(skin, this);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        waterTexture = new Texture(Gdx.files.internal("water.png"));
        sandTexture = new Texture(Gdx.files.internal("sand.png"));
        grassTexture = new Texture(Gdx.files.internal("grass.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        minimapCamera = new OrthographicCamera();
        minimapCamera.setToOrtho(false, Gdx.graphics.getWidth() * MINIMAP_SCALE, Gdx.graphics.getHeight() * MINIMAP_SCALE);

        minimapViewport = new FitViewport(MINIMAP_SIZE, MINIMAP_SIZE, minimapCamera);

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table bottomBar = new Table();
        bottomBar.setFillParent(true);
        bottomBar.bottom();

        shopButton = new TextButton("Shop", skin);
        shopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(shopContainer);
            }
        });


        bottomBar.add(shopButton).pad(10).width(150).height(50).left();
        stage.addActor(bottomBar);
    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        clampCamera();

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

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        waterTexture.dispose();
        sandTexture.dispose();
        grassTexture.dispose();
        stage.dispose();
        skin.dispose();
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

        float targetZoom = camera.zoom;

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            targetZoom = Math.min(1f, targetZoom + zoomSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            targetZoom = Math.max(0.5f, targetZoom - zoomSpeed);
        }

        camera.zoom += (targetZoom - camera.zoom) * 0.4f;
    }

    private void clampCamera() {
        float halfWidth = camera.viewportWidth * camera.zoom / 2;
        float halfHeight = camera.viewportHeight * camera.zoom / 2;

        float minX = halfWidth;
        float maxX = 50 * 32 - halfWidth;
        float minY = halfHeight;
        float maxY = 50 * 32 - halfHeight;

        camera.position.x = Math.max(minX, Math.min(camera.position.x, maxX));
        camera.position.y = Math.max(minY, Math.min(camera.position.y, maxY));
    }
}
