package safariSimulator.main.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
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
    }

    @Override
    public void render(float delta) {
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
    }

    @Override
    public void resize(int width, int height) {

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

}
