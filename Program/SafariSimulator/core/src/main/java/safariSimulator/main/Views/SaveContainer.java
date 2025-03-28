package safariSimulator.main.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import safariSimulator.main.Database.Database;
import safariSimulator.main.Models.Objects.PlantType;

public class SaveContainer extends Window {
    public MapScreen mapScreen;

    public TextButton saveToSlot1Button;
    public TextButton saveToSlot2Button;
    public TextButton saveToSlot3Button;
    public TextButton backButton;

    public SaveContainer(Skin skin, MapScreen mapScreen) {
        super("Save", skin);

        this.mapScreen = mapScreen;

        this.setSize(400, 300);
        this.setPosition(
            Gdx.graphics.getWidth() / 2f - this.getWidth() / 2,
            Gdx.graphics.getHeight() / 2f - this.getHeight() / 2
        );
        this.setMovable(false);

        Label title = new Label("Choose!", skin);

        saveToSlot1Button = new TextButton("Save to Slot1", skin);
        saveToSlot2Button = new TextButton("Save to Slot2", skin);
        saveToSlot3Button = new TextButton("Save to Slot3", skin);
        backButton = new TextButton("Back", skin);

        this.row();
        this.add(title).pad(20);
        this.row();
        this.add(saveToSlot1Button).pad(10);
        this.row();
        this.add(saveToSlot2Button).pad(10);
        this.row();
        this.add(saveToSlot3Button).pad(10);
        this.row();
        this.add(backButton).pad(10);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveContainer.this.remove();
            }
        });
        saveToSlot1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mapScreen.map.savingFileName = "save1.json";
                Database.saveGame(mapScreen.map);
                SaveContainer.this.remove();
            }
        });
        saveToSlot2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mapScreen.map.savingFileName = "save2.json";
                Database.saveGame(mapScreen.map);
                SaveContainer.this.remove();
            }
        });
        saveToSlot3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mapScreen.map.savingFileName = "save3.json";
                Database.saveGame(mapScreen.map);
                SaveContainer.this.remove();
            }
        });
    }
}
