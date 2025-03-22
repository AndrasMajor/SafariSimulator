package safariSimulator.main.Views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Tile.Tile;

public class Minimap extends Actor {
    public static final int SIZE = 200;

    private final OrthographicCamera camera;
    private final Map map;

    private final Pixmap pixmap;
    private final Texture minimapTexture;

    public Minimap(OrthographicCamera camera, Map map) {
        this.camera = camera;
        this.map = map;

        pixmap = new Pixmap(SIZE, SIZE, Pixmap.Format.RGBA8888);
        minimapTexture = new Texture(pixmap);

        renderMinimap();

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                handleMinimapClick(x, y);
                return true;
            }
        });
    }

    private void renderMinimap() {
        int mapWidth = map.getWidth();
        int mapHeight = map.getHeight();
        float tileSize = (float) SIZE / mapWidth;

        for (Tile tile : map.getTiles()) {
            Color color;
            if (tile.health == -1) color = Color.BLUE;
            else if (tile.health == 0) color = Color.YELLOW;
            else color = Color.GREEN;

            pixmap.setColor(color);

            int x = (int) (tile.pos.getX() * tileSize);
            int y = (int) ((mapHeight - 1 - tile.pos.getY()) * tileSize); // Flip Y

            pixmap.fillRectangle(x, y, (int) tileSize, (int) tileSize);
        }

        minimapTexture.draw(pixmap, 0, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.setColor(1, 1, 1, parentAlpha);
        batch.draw(minimapTexture, getX(), getY(), getWidth(), getHeight());

        if (camera != null) {
            float mapWidth = map.getWidth() * 32f;
            float mapHeight = map.getHeight() * 32f;

            float camLeft = camera.position.x - camera.viewportWidth * 0.5f * camera.zoom;
            float camBottom = camera.position.y - camera.viewportHeight * 0.5f * camera.zoom;
            float camWidth = camera.viewportWidth * camera.zoom;
            float camHeight = camera.viewportHeight * camera.zoom;

            float normX = camLeft / mapWidth;
            float normY = camBottom / mapHeight;
            float normW = camWidth / mapWidth;
            float normH = camHeight / mapHeight;

            float rectX = getX() + normX * getWidth();
            float rectY = getY() + normY * getHeight();
            float rectW = normW * getWidth();
            float rectH = normH * getHeight();

            batch.setColor(new Color(0.5f, 0.25f, 0f, 1f)); // Brown frame

            // Top
            batch.draw(minimapTexture, rectX, rectY + rectH - 1, rectW, 1);
            // Bottom
            batch.draw(minimapTexture, rectX, rectY, rectW, 1);
            // Left
            batch.draw(minimapTexture, rectX, rectY, 1, rectH);
            // Right
            batch.draw(minimapTexture, rectX + rectW - 1, rectY, 1, rectH);
        }
    }

    private void handleMinimapClick(float x, float y) {
        int mapWidth = map.getWidth();
        int mapHeight = map.getHeight();

        float tileSize = (float) SIZE / mapWidth;

        // Convert click to tile coordinates
        int tileX = (int)(x / tileSize);
        int tileY = (int)(y / tileSize);

        // Flip Y to match renderMinimap()

        // Convert tile to world position (center of tile)
        float targetX = tileX * 32f + 16f;
        float targetY = tileY * 32f + 16f;

        // Clamp camera to avoid out-of-bounds scrolling
        float mapPixelWidth = mapWidth * 32f;
        float mapPixelHeight = mapHeight * 32f;

        float halfViewWidth = camera.viewportWidth * camera.zoom * 0.5f;
        float halfViewHeight = camera.viewportHeight * camera.zoom * 0.5f;

        float minX = halfViewWidth;
        float maxX = mapPixelWidth - halfViewWidth;
        float minY = halfViewHeight;
        float maxY = mapPixelHeight - halfViewHeight;

        float clampedX = Math.max(minX, Math.min(targetX, maxX));
        float clampedY = Math.max(minY, Math.min(targetY, maxY));

        camera.position.set(clampedX, (clampedY), 0);
        camera.update();
    }

    public void dispose() {
        pixmap.dispose();
        minimapTexture.dispose();
    }
}
