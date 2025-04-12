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
import safariSimulator.main.Models.Entity.Jeep;
import safariSimulator.main.Models.Objects.Plant;
import safariSimulator.main.Models.Objects.PlantType;
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

    public final int treePrice = 40;
    public final int bushPrice = 30;
    public final int waterPrice = 30;
    public final int grassPrice = 30;

    private Window insufficientFundsWindow;

    public ShopContainer(Skin skin, MapScreen mapScreen) {
        super("Safari Shop", skin);
        this.mapScreen = mapScreen;

        this.setSize(700, 600);
        this.setColor(0.2f, 0.5f, 0.3f, 0.95f);


        this.setPosition(
            Gdx.graphics.getWidth() / 2f - this.getWidth() / 2,
            Gdx.graphics.getHeight() / 2f - this.getHeight() / 2
        );
        this.setMovable(false);

        Label title = new Label("Safari Shop", skin);
        title.setFontScale(2.0f);
        title.setColor(0.9f, 1f, 0.2f, 1f);

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

        Label lionPrice = new Label("100$", skin);
        Label hyenaPrice = new Label("50$", skin);
        Label zebraPrice = new Label("50$", skin);
        Label elephantPrice = new Label("100$", skin);
        Label roadPrice = new Label("80$", skin);
        Label jeepPrice = new Label("50$", skin);
        Label grassPrice = new Label("30$", skin);
        Label treePrice = new Label("40$", skin);
        Label bushPrice = new Label("30$", skin);
        Label waterPrice = new Label("30$", skin);

        for (Label label : new Label[]{
            lionPrice, hyenaPrice, zebraPrice, elephantPrice,
            roadPrice, jeepPrice, grassPrice, treePrice,
            bushPrice, waterPrice}) {
            label.setFontScale(1.0f);
            label.setColor(0,1f,0,1f);

        }

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(12);
        // Title row
        table.add(title).colspan(4).center().padBottom(10);
        table.row();

        // Animal row
        table.add(lionButton);
        table.add(hyenaButton);
        table.add(zebraButton);
        table.add(elephantButton);
        table.row();

        table.add(lionPrice).padTop(0).padBottom(5).center();
        table.add(hyenaPrice).padTop(0).padBottom(5).center();
        table.add(zebraPrice).padTop(0).padBottom(5).center();
        table.add(elephantPrice).padTop(0).padBottom(5).center();
        table.row();

        // Plant row
        table.add(grassButton).pad(10);
        table.add(treeButton).pad(10);
        table.add(bushButton).pad(10);
        table.add().pad(5); // Empty for spacing
        table.row();

        table.add(grassPrice).padTop(5).padBottom(5).center();
        table.add(treePrice).padTop(5).padBottom(5).center();
        table.add(bushPrice).padTop(5).padBottom(5).center();
        table.add().pad(5); // Empty for spacing
        table.row();

        // Vehicles and roads row
        table.add(roadButton).pad(10);
        table.add(jeepButton).pad(10);
        table.add(waterButton).pad(10);
        table.add().pad(5); // Empty for spacing

        table.row();

        table.add(roadPrice).padTop(5).padBottom(5).center();
        table.add(jeepPrice).padTop(5).padBottom(5).center();
        table.add(waterPrice).padTop(5).padBottom(5).center();
        table.add().pad(5); // Empty for spacing
        table.row();

        // Close button row
        table.add(closeButton).padTop(50).colspan(4).center().bottom();

        for (TextButton button : new TextButton[]{
            lionButton, hyenaButton, zebraButton, elephantButton,
            roadButton, jeepButton, grassButton, treeButton,
            bushButton, waterButton, closeButton}) {
            button.getLabel().setFontScale(1.3f);
            button.pad(15);
            button.setColor(0.2f, 0.6f, 1f, 1f);
        }

        this.addActor(table);

        insufficientFundsWindow = new Window("Error", skin);
        insufficientFundsWindow.setSize(300, 150);
        insufficientFundsWindow.setMovable(false);
        insufficientFundsWindow.setPosition(
            Gdx.graphics.getWidth() / 2f - insufficientFundsWindow.getWidth() / 2,
            Gdx.graphics.getHeight() / 2f - insufficientFundsWindow.getHeight() / 2
        );

        TextButton okButton = new TextButton("OK", skin);
        Label insufficientFundsLabel = new Label("Insufficient Funds", skin);

        okButton.getLabel().setFontScale(1.2f);
        okButton.setColor(0f, 0f, 1f, 1f);
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                insufficientFundsWindow.remove();
            }
        });

        Table errorTable = new Table();
        errorTable.setFillParent(true);

// Add the label and button in separate rows
        errorTable.row().pad(10).center();
        errorTable.add(insufficientFundsLabel).colspan(1).center();

        errorTable.row().pad(10).center();
        errorTable.add(okButton).padTop(10).width(100).height(40).center();

// Add the table to the insufficientFundsWindow
        insufficientFundsWindow.addActor(errorTable);


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
                if(ShopContainer.this.mapScreen.map.buyEntity(new Jeep(new Point())) == 1){
                    ShopContainer.this.remove();
                    ShopContainer.this.mapScreen.stage.addActor(ShopContainer.this.mapScreen.shopButton);
                }else{
                    ShopContainer.this.mapScreen.stage.addActor(insufficientFundsWindow);
                }
            }
        });
        roadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ShopContainer.this.remove();
                ShopContainer.this.mapScreen.stage.addActor(ShopContainer.this.mapScreen.shopButton);
            }
        });
        waterButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (ShopContainer.this.mapScreen.map.money > ShopContainer.this.waterPrice) {
                    mapScreen.selectedTileType = -1;
                    ShopContainer.this.remove();
                }else{
                    ShopContainer.this.mapScreen.stage.addActor(insufficientFundsWindow);
                }
            }
        });
        grassButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (ShopContainer.this.mapScreen.map.money > ShopContainer.this.grassPrice) {
                    mapScreen.selectedTileType = 100;
                    ShopContainer.this.remove();
                }else{
                    ShopContainer.this.mapScreen.stage.addActor(insufficientFundsWindow);
                }
            }
        });
        bushButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (ShopContainer.this.mapScreen.map.money > ShopContainer.this.bushPrice) {
                    mapScreen.selectedPlantType = PlantType.Bush;
                    ShopContainer.this.remove();
                }else{
                    ShopContainer.this.mapScreen.stage.addActor(insufficientFundsWindow);
                }
            }
        });
        treeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (ShopContainer.this.mapScreen.map.money > ShopContainer.this.treePrice) {
                    mapScreen.selectedPlantType = PlantType.Tree;
                    ShopContainer.this.remove();
                }else{
                    ShopContainer.this.mapScreen.stage.addActor(insufficientFundsWindow);
                }
            }
        });
        elephantButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(ShopContainer.this.mapScreen.map.buyEntity(new Elephant(new Point())) == 1){
                    ShopContainer.this.remove();
                    ShopContainer.this.mapScreen.stage.addActor(ShopContainer.this.mapScreen.shopButton);
                }else{
                    ShopContainer.this.mapScreen.stage.addActor(insufficientFundsWindow);
                }
            }
        });
        zebraButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(ShopContainer.this.mapScreen.map.buyEntity(new Zebra(new Point())) == 1){
                    ShopContainer.this.remove();
                    ShopContainer.this.mapScreen.stage.addActor(ShopContainer.this.mapScreen.shopButton);
                }else{
                    ShopContainer.this.mapScreen.stage.addActor(insufficientFundsWindow);
                }
            }
        });
        hyenaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(ShopContainer.this.mapScreen.map.buyEntity(new Hyena(new Point())) == 1){
                    ShopContainer.this.remove();
                    ShopContainer.this.mapScreen.stage.addActor(ShopContainer.this.mapScreen.shopButton);
                }else{
                    ShopContainer.this.mapScreen.stage.addActor(insufficientFundsWindow);
                }
            }
        });
        lionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(ShopContainer.this.mapScreen.map.buyEntity(new Lion(new Point())) == 1){
                    ShopContainer.this.remove();
                    ShopContainer.this.mapScreen.stage.addActor(ShopContainer.this.mapScreen.shopButton);
                }else{
                    ShopContainer.this.mapScreen.stage.addActor(insufficientFundsWindow);
                }
            }
        });


    }
}
