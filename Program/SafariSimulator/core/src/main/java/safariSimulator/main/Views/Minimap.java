// Updated Minimap.java to respect TILE_SIZE = 64 instead of hardcoded 32 for accurate camera box scaling

package safariSimulator.main.Views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Hyena;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Lion;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Elephant;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Zebra;
import safariSimulator.main.Models.Entity.Entity;
import safariSimulator.main.Models.Entity.Human.Poacher;
import safariSimulator.main.Models.Entity.Jeep;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Objects.Plant;
import safariSimulator.main.Models.Objects.Road;
import safariSimulator.main.Models.Tile.Tile;
import safariSimulator.main.Models.Objects.Object;
import safariSimulator.main.Models.Objects.EntranceExitRoad;

public class Minimap extends Actor {
    public static final int SIZE = 200;
    public static final int TILE_SIZE = 64;

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
        renderObjects();
        renderEntities();
        minimapTexture.draw(pixmap, 0, 0);


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

    private void renderEntities() {
        float tileSize = (float) SIZE / map.getWidth();
        for (Entity entity : map.getEntities()) {
            Color color = null;

            if (entity instanceof Elephant) color = Color.LIGHT_GRAY;
            else if (entity instanceof Lion) color = new Color(0.5f, 0.25f, 0f, 1f); // brown
            else if (entity instanceof Hyena) color = Color.ORANGE;
            else if (entity instanceof Zebra) color = Color.WHITE;
            else if (entity instanceof Poacher) color = Color.BLACK;
            else if (entity instanceof Jeep) color = Color.RED;

            if (color != null) {
                pixmap.setColor(color);
                int x = (int)(entity.getPos().getX() * tileSize);
                int y = (int)((map.getHeight() - 1 - entity.getPos().getY()) * tileSize);
                pixmap.fillRectangle(x, y, (int)tileSize, (int)tileSize);
            }
        }
    }

    private void renderObjects() {
        float tileSize = (float) SIZE / map.getWidth();
        for (Object obj : map.getObjects()) {
            Color color = null;

            if (obj instanceof Plant) color = new Color(0f, 0.5f, 0f, 1f); // dark green
            else if (obj instanceof Road) color = Color.DARK_GRAY;

            if (color != null) {
                pixmap.setColor(color);
                int x = (int)(obj.getPos().getX() * tileSize);
                int y = (int)((map.getHeight() - 1 - obj.getPos().getY()) * tileSize);
                pixmap.fillRectangle(x, y, (int)tileSize, (int)tileSize);
            }
        }
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        updateMinimap();

        batch.setColor(1, 1, 1, parentAlpha);
        batch.draw(minimapTexture, getX(), getY(), getWidth(), getHeight());

        if (camera != null) {
            float mapWidth = map.getWidth();
            float mapHeight = map.getHeight();

            float tileToPixel = getWidth() / mapWidth;

            float camTileWidth = camera.viewportWidth * camera.zoom / TILE_SIZE;
            float camTileHeight = camera.viewportHeight * camera.zoom / TILE_SIZE;

            float camTileX = camera.position.x / TILE_SIZE;
            float camTileY = camera.position.y / TILE_SIZE;

            float rectWidth = camTileWidth * tileToPixel;
            float rectHeight = camTileHeight * tileToPixel;

            float rectX = getX() + camTileX * tileToPixel - rectWidth / 2f;
            float rectY = getY() + camTileY * tileToPixel - rectHeight / 2f;

            batch.setColor(new Color(0.5f, 0.25f, 0f, 1f)); // Brown frame

            // Top
            batch.draw(minimapTexture, rectX, rectY + rectHeight - 1, rectWidth, 1);
            // Bottom
            batch.draw(minimapTexture, rectX, rectY, rectWidth, 1);
            // Left
            batch.draw(minimapTexture, rectX, rectY, 1, rectHeight);
            // Right
            batch.draw(minimapTexture, rectX + rectWidth - 1, rectY, 1, rectHeight);
        }
    }

    public void updateMinimap() {
        pixmap.setColor(0, 0, 0, 1);
        pixmap.fill();

        int mapWidth = map.getWidth();
        int mapHeight = map.getHeight();
        float tileSize = (float) SIZE / mapWidth;

        // Draw tiles
        for (Tile tile : map.getTiles()) {
            Color color;
            if (tile.health == -1) color = Color.BLUE;
            else if (tile.health == 0) color = Color.YELLOW;
            else color = Color.GREEN;

            pixmap.setColor(color);
            int x = (int)(tile.pos.getX() * tileSize);
            int y = (int)((mapHeight - 1 - tile.pos.getY()) * tileSize);
            pixmap.fillRectangle(x, y, (int)tileSize, (int)tileSize);
        }

        // Draw objects
        for (safariSimulator.main.Models.Objects.Object obj : map.getObjects()) {
            if (obj instanceof Plant || obj instanceof Road || obj instanceof EntranceExitRoad) {
                pixmap.setColor(new Color(0f, 0.5f, 0f, 1f)); // dark green
                int x = (int)(obj.getPos().getX() * tileSize);
                int y = (int)((mapHeight - 1 - obj.getPos().getY()) * tileSize);
                pixmap.fillRectangle(x, y, (int)tileSize, (int)tileSize);
            }
        }

        // Draw entities
        for (Entity entity : map.getEntities()) {
            Color color = Color.WHITE;
            if (entity instanceof Elephant) color = Color.LIGHT_GRAY;
            else if (entity instanceof Lion) color = new Color(0.4f, 0.2f, 0f, 1f); // brown
            else if (entity instanceof Hyena) color = Color.ORANGE;
            else if (entity instanceof Poacher) color = Color.BLACK;
            else if (entity instanceof Zebra) color = Color.WHITE;
            else if (entity instanceof Jeep) color = Color.RED;

            pixmap.setColor(color);
            int x = (int)(entity.getPos().getX() * tileSize);
            int y = (int)((mapHeight - 1 - entity.getPos().getY()) * tileSize);
            pixmap.fillRectangle(x, y, (int)tileSize, (int)tileSize);
        }

        minimapTexture.draw(pixmap, 0, 0);
    }


    private void handleMinimapClick(float x, float y) {
        int mapWidth = map.getWidth();
        int mapHeight = map.getHeight();

        float tileSize = (float) SIZE / mapWidth;

        int tileX = (int)(x / tileSize);
        int tileY = (int)(y / tileSize);

        float targetX = tileX * TILE_SIZE + TILE_SIZE / 2f;
        float targetY = tileY * TILE_SIZE + TILE_SIZE / 2f;

        float mapPixelWidth = mapWidth * TILE_SIZE;
        float mapPixelHeight = mapHeight * TILE_SIZE;

        float halfViewWidth = camera.viewportWidth * camera.zoom * 0.5f;
        float halfViewHeight = camera.viewportHeight * camera.zoom * 0.5f;

        float minX = halfViewWidth;
        float maxX = mapPixelWidth - halfViewWidth;
        float minY = halfViewHeight;
        float maxY = mapPixelHeight - halfViewHeight;

        float clampedX = Math.max(minX, Math.min(targetX, maxX));
        float clampedY = Math.max(minY, Math.min(targetY, maxY));

        camera.position.set(clampedX, clampedY, 0);
        camera.update();
    }

    public void dispose() {
        pixmap.dispose();
        minimapTexture.dispose();
    }
}
