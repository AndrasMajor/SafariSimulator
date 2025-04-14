package safariSimulator.main.Views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import safariSimulator.main.Database.Database;
import safariSimulator.main.Models.Map;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public MapScreen mapScreen;
    public Map map;
    @Override
    public void create() {
        MainScreen main = new MainScreen();
        setScreen(main);
        main.newGameConteiner.easyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mapScreen = new MapScreen("easy", Main.this);
                setScreen(mapScreen);
            }
        });
        main.newGameConteiner.mediumButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mapScreen = new MapScreen("medium", Main.this);
                setScreen(mapScreen);
            }
        });
        main.newGameConteiner.hardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mapScreen = new MapScreen("hard", Main.this);
                setScreen(mapScreen);
            }
        });

        main.loadGameContainer.loadButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Database.isExistsSave("save1.json")) {
                    map = Database.loadMap("save1.json");
                    mapScreen = new MapScreen(map);
                    setScreen(mapScreen);
                }
            }
        });
        main.loadGameContainer.loadButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Database.isExistsSave("save2.json")) {
                    map = Database.loadMap("save2.json");
                    mapScreen = new MapScreen(map);
                    setScreen(mapScreen);
                }
            }
        });
        main.loadGameContainer.loadButton3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Database.isExistsSave("save3.json")) {
                    map = Database.loadMap("save3.json");
                    mapScreen = new MapScreen(map);
                    setScreen(mapScreen);
                }
            }
        });
    }
}
