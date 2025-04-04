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

import safariSimulator.main.Models.Entity.Animal.Carnivore.Hyena;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Lion;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Elephant;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Zebra;
import safariSimulator.main.Models.Entity.Entity;
import safariSimulator.main.Models.Entity.Human.Poacher;
import safariSimulator.main.Models.Entity.Jeep;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Objects.Plant;
import safariSimulator.main.Models.Objects.PlantType;
import safariSimulator.main.Models.Objects.Road;
import safariSimulator.main.Models.Tile.Tile;
import safariSimulator.main.Models.Point;
import safariSimulator.main.Models.Objects.Object;

public class MapScreen extends InputAdapter implements Screen {

    public PlantType selectedPlantType = null;
    public int selectedTileType = 0;

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

    public Map map;

    private OrthographicCamera camera;
    private float cameraSpeed = 200;
    private float zoomSpeed = 0.02f;

    public Stage stage;
    public TextButton shopButton;
    public TextButton exitButton;
    public Label moneyLabel;
    private Skin skin;
    public ShopContainer shopContainer;
    public SaveContainer saveContainer;
    private Minimap minimap;

    private ShaderProgram tileShader;
    private TextButton pauseButton;
    private TextButton speedButton;
    private Label dateLabel;
    private int speedState = 0; // 0 = hour/sec, 1 = day/sec, 2 = week/sec


    public MapScreen(String level) {
        map = new Map(level);
        map.generateMap();
        map.generatePlants();
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

        lionTexture = new Texture(Gdx.files.internal("entities/lion.png"));
        hyenaTexture = new Texture(Gdx.files.internal("entities/hyena.png"));
        elephantTexture = new Texture(Gdx.files.internal("entities/elephant.png"));
        zebraTexture = new Texture(Gdx.files.internal("entities/zebra.png"));

        poacherTexture = new Texture(Gdx.files.internal("entities/poacher.png"));
        jeepTexture = new Texture(Gdx.files.internal("entities/jeep.png"));

        treeTexture = new Texture(Gdx.files.internal("objects/tree.png"));
        bushTexture = new Texture(Gdx.files.internal("objects/bush.png"));
        roadTexture = new Texture(Gdx.files.internal("objects/road.png"));


        ShaderProgram.pedantic = false;
        tileShader = new ShaderProgram(
            Gdx.files.internal("shaders/tile_blend.vert"),
            Gdx.files.internal("shaders/tile_blend.frag")
        );
        if (!tileShader.isCompiled()) {
            System.err.println("Shader compile error:\n" + tileShader.getLog());
        }

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

        pauseButton = new TextButton("⏸", skin);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (map.isPaused()) {
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
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.7f);
        pixmap.fill();
        Texture bgTexture = new Texture(pixmap);
        pixmap.dispose();

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

        batch.setShader(tileShader);
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

            tileShader.setUniformi("u_tileType", tileType);
            tileShader.setUniformi("u_neighborTop", neighbors[0]);
            tileShader.setUniformi("u_neighborRight", neighbors[1]);
            tileShader.setUniformi("u_neighborBottom", neighbors[2]);
            tileShader.setUniformi("u_neighborLeft", neighbors[3]);


            batch.draw(tileTexture, tile.getPos().getX() * 32, tile.getPos().getY() * 32, 32, 32);
        }

        for (Entity entity : map.getEntities()) {
            Texture entityTexture = null;

            if (entity instanceof Lion) {
                entityTexture = lionTexture;
            } else if (entity instanceof Zebra) {
                entityTexture = zebraTexture;
            } else if (entity instanceof Elephant) {
                entityTexture = elephantTexture;
            } else if (entity instanceof Hyena) {
                entityTexture = hyenaTexture;
            } else if( entity instanceof Jeep){
                entityTexture = jeepTexture;
            } else if (entity instanceof Poacher) {
                entityTexture = poacherTexture;
            }

            if (entityTexture != null) {
                Point pos = entity.getPos();
                batch.draw(entityTexture, pos.getX() * 32, pos.getY() * 32, 32, 32);
            }
        }

        for(Object object : map.getObjects()){
            Texture objectTexture = null;
            if (object instanceof Plant) {
                if (((Plant) object).type == PlantType.Tree){
                    objectTexture = treeTexture;
                }else{
                    objectTexture = bushTexture;
                }
            }else if(object instanceof Road){
                objectTexture = roadTexture;
            }

            if (objectTexture != null) {
                Point pos = object.getPos();
                for(Tile tile : map.getTiles()){
                    if(tile.getPos().getX() == pos.getX() && tile.getPos().getY() == pos.getY()){
                        if(tile.getHealth() > 0){
                            batch.draw(objectTexture, pos.getX() * 32, pos.getY() * 32, 32, 32);
                        }
                        else{
                            map.getObjects().remove(object);
                            break;
                        }
                    }
                }

            }
        }

        moneyLabel.setText(map.money + "$");


        batch.end();
        batch.setShader(null); // 🔥 reset shader so minimap & UI render normally

        stage.act(delta);
        dateLabel.setText(map.getTime().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        stage.draw();
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
        if (selectedPlantType != null) {
            Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
            int tileX = (int) (worldCoordinates.x / 32);
            int tileY = (int) (worldCoordinates.y / 32);

            Tile clickedTile = map.getTileAt(tileX, tileY);
            if (clickedTile != null && clickedTile.getHealth() > 0) {
                map.buyObject(new Plant(new Point(tileX, tileY), selectedPlantType));
                if(selectedPlantType == PlantType.Tree){
                    clickedTile.setHealth(clickedTile.getHealth() + 100);
                } else if (selectedPlantType == PlantType.Bush) {
                    clickedTile.setHealth(clickedTile.getHealth() + 50);
                }
                selectedPlantType = null;
            }
        }else if(selectedTileType != 0){
            Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
            int tileX = (int) (worldCoordinates.x / 32);
            int tileY = (int) (worldCoordinates.y / 32);

            Tile clickedTile = map.getTileAt(tileX, tileY);
            if (clickedTile != null) {
                if(selectedTileType == -1 && clickedTile.getHealth() != -1){
                    map.buyWater(tileX, tileY);
                    selectedTileType = 0;
                }else if(selectedTileType == 100 && clickedTile.getHealth() <= 0){
                    map.buyGrass(tileX, tileY);
                    selectedTileType = 0;
                }
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
        tileShader.dispose();
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.position.y += cameraSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.position.y -= cameraSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.position.x -= cameraSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.position.x += cameraSpeed * delta;

        float targetZoom = camera.zoom;
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) targetZoom = Math.min(1f, targetZoom + zoomSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.E)) targetZoom = Math.max(0.5f, targetZoom - zoomSpeed);
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
