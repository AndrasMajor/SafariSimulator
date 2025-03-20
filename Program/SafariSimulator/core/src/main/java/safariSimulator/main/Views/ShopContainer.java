package safariSimulator.main.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Hyena;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Lion;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Elephant;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Zebra;
import safariSimulator.main.Models.Point;

public class ShopContainer extends Window {

    public TextButton lionButton;
    public TextButton hyenaButton;
    public TextButton zebraButton;
    public TextButton elephantButton;
    public TextButton roadButton;
    public TextButton jeepButton;
    public TextButton grassButton;
    public TextButton treeButton;
    public TextButton bushButton;
    public TextButton waterButton;
    public TextButton closeButton;
    public MapScreen mapScreen;

    public ShopContainer(Skin skin, MapScreen mapScreen) {
        super("Shop", skin);
        this.mapScreen = mapScreen;

        this.setSize(400, 300);
        this.setPosition(
            Gdx.graphics.getWidth() / 2f - this.getWidth() / 2,
            Gdx.graphics.getHeight() / 2f - this.getHeight() / 2
        );
        this.setMovable(false);

        Label title = new Label("Choose!", skin);

        lionButton = new TextButton("Lion", skin);
        hyenaButton = new TextButton("Hyena", skin);
        zebraButton = new TextButton("Zebra", skin);
        elephantButton = new TextButton("Elephant", skin);
        roadButton = new TextButton("Road", skin);
        jeepButton = new TextButton("Jeep", skin);
        grassButton = new TextButton("Grass", skin);
        treeButton = new TextButton("Tree", skin);
        bushButton = new TextButton("Bush", skin);
        waterButton = new TextButton("Water", skin);
        closeButton = new TextButton("Close", skin);

        Table table = new Table();
        table.setFillParent(true);

        // Title row
        table.add(title).colspan(4).center().padBottom(10);
        table.row();

        // Animal row
        table.add(lionButton).pad(5);
        table.add(hyenaButton).pad(5);
        table.add(zebraButton).pad(5);
        table.add(elephantButton).pad(5);
        table.row();

        // Plant row
        table.add(grassButton).pad(5);
        table.add(treeButton).pad(5);
        table.add(bushButton).pad(5);
        table.add(); // Empty for spacing
        table.row();

        // Vehicles and roads row
        table.add(roadButton).pad(5);
        table.add(jeepButton).pad(5);
        table.add(waterButton).pad(5);
        table.add(); // Empty for spacing

        table.row();
        table.add(closeButton).pad(5);

        this.addActor(table);

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ShopContainer.this.remove();
                ShopContainer.this.mapScreen.stage.addActor(ShopContainer.this.mapScreen.shopButton);
                // Removes the shop window from the stage
            }
        });
        jeepButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        roadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        waterButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        grassButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        bushButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        treeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        elephantButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ShopContainer.this.mapScreen.map.buyAnimal(new Elephant(new Point()));
            }
        });
        zebraButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ShopContainer.this.mapScreen.map.buyAnimal(new Zebra(new Point()));
            }
        });
        hyenaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ShopContainer.this.mapScreen.map.buyAnimal(new Hyena(new Point()));
            }
        });
        lionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ShopContainer.this.mapScreen.map.buyAnimal(new Lion(new Point()));
            }
        });


    }


}
