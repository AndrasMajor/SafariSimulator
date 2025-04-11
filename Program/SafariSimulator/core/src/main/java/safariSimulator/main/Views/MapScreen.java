package safariSimulator.main.Views;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;


import safariSimulator.main.Models.Entity.Animal.Animal;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Hyena;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Lion;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Elephant;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Zebra;
import safariSimulator.main.Models.Entity.Entity;
import safariSimulator.main.Models.Entity.Human.Poacher;
import safariSimulator.main.Models.Entity.Jeep;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Objects.*;
import safariSimulator.main.Models.Objects.Object;
import safariSimulator.main.Models.Tile.Tile;
import safariSimulator.main.Models.Point;
import safariSimulator.main.Models.Objects.RoadBuildType;

public class MapScreen extends InputAdapter implements Screen {

    public PlantType selectedPlantType = null;
    public int selectedTileType = 0;
    public RoadBuildType selectedRoadType = null;
    public final int TILE_SIZE = 64;

    private BitmapFont font;

    private Texture waterTexture;
    private Texture sandTexture;
    private Texture grassTexture;
    private SpriteBatch batch;
    private Texture lionTexture;
    private Texture zebraTexture;
    private Texture hyenaTexture;
    private Texture elephantTexture;
    private Texture jeepTexture;
    private Texture treeTexture;
    private Texture bushTexture;
    private Texture roadTexture;
    private Texture poacherTexture;
    private Texture entranceRoadTexture;
    private Texture exitRoadTexture;
    private Texture pixelTexture;
    private Texture roadStraightNS, roadStraightWE;
    private Texture roadTurnNE, roadTurnNW, roadTurnSE, roadTurnSW;
    private RoadBuildType currentRoadType = RoadBuildType.STRAIGHT_NS;


    public Map map;

    private OrthographicCamera camera;
    private float cameraSpeed = 200;
    private float zoomSpeed = 0.02f;

    public Stage stage;
    public TextButton shopButton;
    public TextButton exitButton;
    public TextButton roadModeToggle;
    public Label moneyLabel;
    private Skin skin;
    public ShopContainer shopContainer;
    public SaveContainer saveContainer;
    private Minimap minimap;

    private TextButton pauseButton;
    private TextButton speedButton;
    private Label dateLabel;
    private int speedState = 0; // 0 = hour/sec, 1 = day/sec, 2 = week/sec


    public MapScreen(String level) {
        map = new Map(level);
        map.generateMap();
        map.generatePlants();
        map.placeEntranceAndExit();
        shopContainer = new ShopContainer(new Skin(Gdx.files.internal("uiskin.json")), this);
        saveContainer = new SaveContainer(new Skin(Gdx.files.internal("uiskin.json")), this);
    }

    public MapScreen(Map loadedMap) {
        map = loadedMap;
        shopContainer = new ShopContainer(new Skin(Gdx.files.internal("uiskin.json")), this);
        saveContainer = new SaveContainer(new Skin(Gdx.files.internal("uiskin.json")), this);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        waterTexture = new Texture(Gdx.files.internal("map_tile_images/water.png"));
        sandTexture = new Texture(Gdx.files.internal("map_tile_images/sand.png"));
        grassTexture = new Texture(Gdx.files.internal("map_tile_images/grass.png"));

        lionTexture = new Texture(Gdx.files.internal("entities/lion_top.png"));
        hyenaTexture = new Texture(Gdx.files.internal("entities/hyena_top.png"));
        elephantTexture = new Texture(Gdx.files.internal("entities/elephant_top.png"));
        zebraTexture = new Texture(Gdx.files.internal("entities/zebra_top.png"));

        poacherTexture = new Texture(Gdx.files.internal("entities/poacher.png"));
        jeepTexture = new Texture(Gdx.files.internal("entities/jeep.png"));

        treeTexture = new Texture(Gdx.files.internal("objects/tree.png"));
        bushTexture = new Texture(Gdx.files.internal("objects/bush.png"));
        entranceRoadTexture = new Texture(Gdx.files.internal("objects/entrance_road_32.png"));
        exitRoadTexture = new Texture(Gdx.files.internal("objects/exit_road_32.png"));
        roadStraightNS = new Texture(Gdx.files.internal("objects/road_straight_south_north.png"));
        roadStraightWE = new Texture(Gdx.files.internal("objects/road_straight_west_east.png"));
        roadTurnNE = new Texture(Gdx.files.internal("objects/road_turn_north_east.png"));
        roadTurnNW = new Texture(Gdx.files.internal("objects/road_turn_north_west.png"));
        roadTurnSE = new Texture(Gdx.files.internal("objects/road_turn_south_east.png"));
        roadTurnSW = new Texture(Gdx.files.internal("objects/road_turn_south_west.png"));

        entranceRoadTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        exitRoadTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // First Pixmap for pixelTexture
        Pixmap redPixel = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        redPixel.setColor(1, 1, 1, 1); // white pixel
        redPixel.fill();
        pixelTexture = new Texture(redPixel);
        redPixel.dispose();

// Second Pixmap for money label background
        Pixmap moneyBg = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        moneyBg.setColor(0, 0, 0, 0.7f);
        moneyBg.fill();
        Texture bgTexture = new Texture(moneyBg);
        moneyBg.dispose();


        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        //Gdx.input.setInputProcessor(stage);
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, this));

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table bottomBar = new Table();
        bottomBar.setFillParent(true);
        bottomBar.bottom();

        moneyLabel = new Label(this.map.money + "$", skin);

        shopButton = new TextButton("Shop", skin);
        shopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addActor(shopContainer);
            }
        });
        exitButton = new TextButton("Save", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("save");
                stage.addActor(saveContainer);
            }
        });

        bottomBar.add(shopButton).pad(10).width(150).height(50).left();
        bottomBar.add(exitButton).pad(10).width(150).height(50).left();
        stage.addActor(bottomBar);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table topBar = new Table();
        topBar.setFillParent(true);
        topBar.top();

        roadModeToggle = new TextButton("Build Mode", skin);
        roadModeToggle.setVisible(false);
        stage.addActor(roadModeToggle);

        Table roadToggleTable = new Table();
        roadToggleTable.setFillParent(true);
        roadToggleTable.top().left();
        roadToggleTable.add(roadModeToggle).pad(10).width(150).height(50);
        stage.addActor(roadToggleTable);

        roadModeToggle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (roadModeToggle.getText().toString().equals("Build Mode")) {
                    roadModeToggle.setText("Sell Mode");
                } else {
                    roadModeToggle.setText("Build Mode");
                }
            }
        });


        pauseButton = new TextButton("⏸", skin);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (map.isPaused()) {
                    if (!map.areEntranceAndExitConnected()) {
                        Dialog dialog = new Dialog("Warning", skin);
                        dialog.text("Roads must connect entrance to exit!");
                        dialog.button("OK");
                        dialog.show(stage);
                        return;
                    }
                    map.resumeGameClock();
                    pauseButton.setText("⏸");
                } else {
                    map.pauseGameClock();
                    pauseButton.setText("▶");
                }
            }
        });

        speedButton = new TextButton("→", skin);
        speedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                speedState = (speedState + 1) % 3;
                switch (speedState) {
                    case 0:
                        map.setSpeedToHourPerSecond();
                        speedButton.setText("→");
                        break;
                    case 1:
                        map.setSpeedToDayPerSecond();
                        speedButton.setText("→→");
                        break;
                    case 2:
                        map.setSpeedToWeekPerSecond();
                        speedButton.setText("→→→");
                        break;
                }
            }
        });

        dateLabel = new Label("", skin);
        dateLabel.setAlignment(Align.center);

        topBar.add(pauseButton).pad(10).width(50).height(50);
        topBar.add(dateLabel).pad(10).width(300).height(50);
        topBar.add(speedButton).pad(10).width(80).height(50);

        stage.addActor(topBar);


        minimap = new Minimap(camera, map);
        minimap.setSize(Minimap.SIZE, Minimap.SIZE);
        minimap.setPosition(Gdx.graphics.getWidth() - Minimap.SIZE - 10, Gdx.graphics.getHeight() - Minimap.SIZE - 10);
        stage.addActor(minimap);



        // Money label jobb alsó sarokban
        // Egyedi stílus létrehozása
        BitmapFont moneyFont = new BitmapFont();
        moneyFont.getData().setScale(2f); // nagyobb szöveg
        moneyFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Label.LabelStyle moneyStyle = new Label.LabelStyle();
        moneyStyle.font = moneyFont;
        moneyStyle.fontColor = com.badlogic.gdx.graphics.Color.GOLD;

        // Háttér hozzáadása (fekete átlátszatlan)

        Drawable background = new TextureRegionDrawable(new TextureRegion(bgTexture));
        moneyStyle.background = background;


        moneyLabel = new Label(map.money + "$", moneyStyle);
        moneyLabel.setAlignment(Align.right);

        Table moneyTable = new Table();
        moneyTable.setFillParent(true);
        moneyTable.bottom().right();
        moneyTable.add(moneyLabel).pad(10).right().bottom();
        stage.addActor(moneyTable);

    }


    @Override
    public void render(float delta) {
        handleInput(delta);
        clampCamera();

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // === 1. Draw tiles using shader ===
        batch.begin();
        for (Tile tile : map.getTiles()) {
            Texture tileTexture;
            int tileType;

            if (tile.health == -1) {
                tileTexture = waterTexture;
                tileType = 0;
            } else if (tile.health == 0) {
                tileTexture = sandTexture;
                tileType = 1;
            } else {
                tileTexture = grassTexture;
                tileType = 2;
            }
            int[] neighbors = getNeighborTypes(tile.getPos());


            batch.draw(tileTexture, tile.getPos().getX() * TILE_SIZE, tile.getPos().getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        if (map.isPaused() && roadModeToggle.getText().toString().equals("Build Mode")) {
            Vector3 mousePos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            int tx = (int)(mousePos.x / TILE_SIZE);
            int ty = (int)(mousePos.y / TILE_SIZE);

            Texture preview = getRoadTextureFor(currentRoadType);
            batch.draw(preview, tx * TILE_SIZE, ty * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
        batch.end();

        // === 2. Reset shader to draw entities and objects ===
        // === 2. Reset shader to draw entities and objects ===
        batch.setShader(null);
        batch.begin();

// === Updated animal/entity rendering with rotation and scaling ===
        /*for (Entity entity : map.getEntities()) {
            if (entity instanceof Animal animal && animal.isAlive()) {
                if (animal.mover != null && !animal.mover.isComplete()) {
                    animal.mover.update(delta);
                }

                float x = (animal.mover != null) ? animal.mover.getInterpolatedX() : animal.getPos().getX();
                float y = (animal.mover != null) ? animal.mover.getInterpolatedY() : animal.getPos().getY();
                float drawX = x * TILE_SIZE;
                float drawY = y * TILE_SIZE;

                float scale = animal.getScale();
                float scaledSize = TILE_SIZE * scale;

                float rotation = 90f; // Default north
                if (animal.mover != null && !animal.mover.isComplete()) {
                    rotation = animal.mover.getAngleDeg() - 90f; // FIXED: adjust so north=default
                }

                Texture texture = getTextureFor(animal);

                batch.draw(
                    texture,
                    drawX + 16 - scaledSize / 2f,
                    drawY + 16 - scaledSize / 2f,
                    scaledSize / 2f, scaledSize / 2f,
                    scaledSize, scaledSize,
                    1f, 1f,
                    rotation,
                    0, 0,
                    texture.getWidth(), texture.getHeight(),
                    false, false
                );
            } else {
                float drawX = entity.getPos().getX() * TILE_SIZE;
                float drawY = entity.getPos().getY() * TILE_SIZE;
                batch.draw(getTextureFor(entity), drawX, drawY, TILE_SIZE, TILE_SIZE);
            }
        }*/

        for (Entity entity : map.getEntities()) {
            // Frissítjük a mozgást, ha van
            if (entity.mover != null && !entity.mover.isComplete()) {
                entity.mover.update(delta);
            }

            // Pozíció meghatározása
            float x = (entity.mover != null) ? entity.mover.getInterpolatedX() : entity.getPos().getX();
            float y = (entity.mover != null) ? entity.mover.getInterpolatedY() : entity.getPos().getY();
            float drawX = x * TILE_SIZE;
            float drawY = y * TILE_SIZE;

            // Skálázás és forgatás
            float scale = entity.getScale();
            float scaledSize = TILE_SIZE * scale;
            float rotation = (entity.mover != null && !entity.mover.isComplete())
                ? entity.mover.getAngleDeg() - 90f
                : 0f;

            Texture texture = getTextureFor(entity);

            // Rajzolás
            if (scale != 1f || rotation != 0f) {
                batch.draw(
                    texture,
                    drawX + 16 - scaledSize / 2f,
                    drawY + 16 - scaledSize / 2f,
                    scaledSize / 2f, scaledSize / 2f,
                    scaledSize, scaledSize,
                    1f, 1f,
                    rotation,
                    0, 0,
                    texture.getWidth(), texture.getHeight(),
                    false, false
                );
            } else {
                batch.draw(texture, drawX, drawY, TILE_SIZE, TILE_SIZE);
            }
        }



        for (Object object : map.getObjects()) {
            Texture objectTexture = null;
            Point pos = object.getPos();

            if (object instanceof EntranceExitRoad) {
                EntranceExitRoad road = (EntranceExitRoad) object;
                //System.out.println((road.isEntrance ? "Entrance" : "Exit") + " at: " + pos.getX() + "," + pos.getY());
                objectTexture = road.isEntrance ? entranceRoadTexture : exitRoadTexture;
            } else if (object instanceof Plant) {
                if (((Plant) object).type == PlantType.Tree) objectTexture = treeTexture;
                else objectTexture = bushTexture;
            } else if (object instanceof SellableRoad sr) {
                objectTexture = getRoadTextureFor(sr.roadType);
            }

            if (objectTexture != null) {
                // 🔥 Always render entrance/exit, even on non-grass
                if (object instanceof EntranceExitRoad) {
                    batch.draw(objectTexture, pos.getX() * TILE_SIZE, pos.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                } else {
                    for (Tile tile : map.getTiles()) {
                        if (tile.getPos().getX() == pos.getX() && tile.getPos().getY() == pos.getY()) {
                            if (tile.getHealth() != -1) { // ✅ Allow on sand and grass, but NOT water
                                batch.draw(objectTexture, pos.getX() * TILE_SIZE, pos.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                            }
                            break;
                        }
                    }
                }
            }
        }


        moneyLabel.setText(map.money + "$");

        batch.end();

        // === 3. Draw UI ===
        stage.act(delta);
        roadModeToggle.setVisible(map.isPaused());

        dateLabel.setText(map.getTime().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        stage.draw();
    }

    private Texture getTextureFor(Entity entity) {
        if (entity instanceof Lion) return lionTexture;
        if (entity instanceof Hyena) return hyenaTexture;
        if (entity instanceof Elephant) return elephantTexture;
        if (entity instanceof Zebra) return zebraTexture;
        if (entity instanceof Poacher) return poacherTexture;
        if (entity instanceof Jeep) return jeepTexture;
        return null; // Unknown entity type
    }

    private int[] getNeighborTypes(Point p) {
        int[] types = new int[4];
        types[0] = getTileType(p.getX(), p.getY() + 1); // top
        types[1] = getTileType(p.getX() + 1, p.getY()); // right
        types[2] = getTileType(p.getX(), p.getY() - 1); // bottom
        types[3] = getTileType(p.getX() - 1, p.getY()); // left
        return types;
    }

    private int getTileType(int x, int y) {
        for (Tile tile : map.getTiles()) {
            if (tile.getPos().getX() == x && tile.getPos().getY() == y) {
                if (tile.getHealth() == -1) return 0; // water
                if (tile.getHealth() == 0) return 1;  // sand
                return 2;                             // grass
            }
        }
        return -1; // invalid or out of bounds
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
        int tileX = (int) (worldCoordinates.x / TILE_SIZE);
        int tileY = (int) (worldCoordinates.y / TILE_SIZE);
        Point pos = new Point(tileX, tileY);
        Tile clickedTile = map.getTileAt(tileX, tileY);

        if (selectedPlantType != null && clickedTile != null && clickedTile.getHealth() > 0) {
            map.buyObject(new Plant(pos, selectedPlantType));
            clickedTile.setHealth(clickedTile.getHealth() + (selectedPlantType == PlantType.Tree ? 100 : 50));
            selectedPlantType = null;
            return true;
        }

        if (selectedTileType != 0 && clickedTile != null) {
            if (selectedTileType == -1 && clickedTile.getHealth() != -1) {
                map.buyWater(tileX, tileY);
                selectedTileType = 0;
            } else if (selectedTileType == 100 && clickedTile.getHealth() <= 0) {
                map.buyGrass(tileX, tileY);
                selectedTileType = 0;
            }
            return true;
        }

        if (map.isPaused()) {
            Object obj = map.getObjects().stream().filter(o -> o.getPos().getX() == pos.getX() && o.getPos().getY() == pos.getY()).findFirst().orElse(null);

            if (roadModeToggle.getText().toString().equals("Sell Mode")) {
                if (obj instanceof SellableRoad road) {
                    map.sellObject(road);
                    return true;
                }
            } else if (roadModeToggle.getText().toString().equals("Build Mode") && clickedTile != null && clickedTile.getHealth() >= 0) {
                if (obj instanceof SellableRoad) {
                    // rotate road
                    ((SellableRoad) obj).rotateClockwise();
                } else if (obj instanceof Road) {
                    // ✅ There's already a road (entrance/exit) — don't place or rotate
                    return true;
                } else {
                    SellableRoad newRoad = new SellableRoad(pos, currentRoadType);
                    map.buyObject(newRoad);
                }

                return true;
            }
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }



    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        batch.dispose();
        font.dispose();
        lionTexture.dispose();
        zebraTexture.dispose();
        elephantTexture.dispose();
        hyenaTexture.dispose();
        roadTexture.dispose();
        jeepTexture.dispose();
        treeTexture.dispose();
        bushTexture.dispose();
        waterTexture.dispose();
        sandTexture.dispose();
        grassTexture.dispose();
        stage.dispose();
        skin.dispose();
        minimap.dispose();
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.position.y += cameraSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.position.y -= cameraSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.position.x -= cameraSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.position.x += cameraSpeed * delta;
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            currentRoadType = RoadBuildType.values()[(currentRoadType.ordinal() + 1) % RoadBuildType.values().length];
        }
        float targetZoom = camera.zoom;
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) targetZoom = Math.min(1f, targetZoom + zoomSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.E)) targetZoom = Math.max(0.5f, targetZoom - zoomSpeed);
        camera.zoom += (targetZoom - camera.zoom) * 0.4f;
    }

    private void clampCamera() {
        float halfWidth = camera.viewportWidth * camera.zoom / 2;
        float halfHeight = camera.viewportHeight * camera.zoom / 2;

        float minX = halfWidth;
        float maxX = 50 * TILE_SIZE - halfWidth;
        float minY = halfHeight;
        float maxY = 50 * TILE_SIZE - halfHeight;

        camera.position.x = Math.max(minX, Math.min(camera.position.x, maxX));
        camera.position.y = Math.max(minY, Math.min(camera.position.y, maxY));
    }

    private Texture getRoadTextureFor(RoadBuildType type) {
        return switch (type) {
            case STRAIGHT_NS -> roadStraightNS;
            case STRAIGHT_WE -> roadStraightWE;
            case TURN_NE -> roadTurnNE;
            case TURN_NW -> roadTurnNW;
            case TURN_SE -> roadTurnSE;
            case TURN_SW -> roadTurnSW;
        };
    }

}
