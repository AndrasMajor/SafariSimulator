package safariSimulator.main.Views;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.*;
import safariSimulator.main.Models.Entity.Human.Keeper;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Entity.*;
import safariSimulator.main.Models.Entity.Animal.*;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Lion;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Elephant;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Zebra;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Hyena;

public class MapSummaryView extends Table {

    private final Map map;
    private final Label lionLabel, zebraLabel, elephantLabel, hyenaLabel;
    private final Label jeepActiveLabel, jeepAvailableLabel;
    private final Label touristsWaitingLabel, touristsTouringLabel;
    private final Label keeperLabel;
    private final Label ratingLabel;
    private final Label weeklyTouristLabel;


    public MapSummaryView(Skin skin, Map map) {
        super(skin);
        this.map = map;
        setBackground(skin.newDrawable("white", 0, 0, 0, 0.6f));
        pad(10);
        left().top();

        // Címsor stílus
        Label.LabelStyle headerStyle = new Label.LabelStyle(skin.get(Label.LabelStyle.class));
        headerStyle.font = skin.getFont("default-font"); // vagy amit használsz
        headerStyle.font.getData().setScale(1.1f); // nagyobb méret
        Label animalHeader = new Label("Animals", headerStyle);
        Label jeepHeader = new Label("Jeeps", headerStyle);
        Label touristHeader = new Label("Tourists", headerStyle);
        Label keeperHeader = new Label("Keepers", headerStyle);

        // Inicializáció
        lionLabel = new Label("", skin);
        zebraLabel = new Label("", skin);
        elephantLabel = new Label("", skin);
        hyenaLabel = new Label("", skin);
        jeepActiveLabel = new Label("", skin);
        jeepAvailableLabel = new Label("", skin);
        touristsWaitingLabel = new Label("", skin);
        touristsTouringLabel = new Label("", skin);
        keeperLabel = new Label("", skin);
        ratingLabel = new Label("", skin);
        weeklyTouristLabel = new Label("", skin);


        // Layout
        add(animalHeader).left().padBottom(5).row();
        add(lionLabel).left().padLeft(10).row();
        add(zebraLabel).left().padLeft(10).row();
        add(elephantLabel).left().padLeft(10).row();
        add(hyenaLabel).left().padLeft(10).padBottom(10).row();

        add(jeepHeader).left().padBottom(5).row();
        add(jeepActiveLabel).left().padLeft(10).row();
        add(jeepAvailableLabel).left().padLeft(10).padBottom(10).row();

        add(touristHeader).left().padBottom(5).row();
        add(touristsTouringLabel).left().padLeft(10).row();
        add(touristsWaitingLabel).left().padLeft(10).row();

        add(keeperHeader).left().padBottom(5).row();
        add(keeperLabel).left().padLeft(10).row();
        add(new Label("Current Rating", headerStyle)).left().padTop(10).row();
        add(ratingLabel).left().padLeft(10).row();
        add(new Label("Tourists This Week", headerStyle)).left().padTop(10).row();
        add(weeklyTouristLabel).left().padLeft(10).row();


        pack();
    }


    public void update() {
        long lions = map.getEntities().stream().filter(e -> e instanceof Lion).count();
        long zebras = map.getEntities().stream().filter(e -> e instanceof Zebra).count();
        long elephants = map.getEntities().stream().filter(e -> e instanceof Elephant).count();
        long hyenas = map.getEntities().stream().filter(e -> e instanceof Hyena).count();
        long jeepsTouring = map.getEntities().stream().filter(e -> e instanceof Jeep).count();
        long jeepsAvailable = map.getAvailableJeepCount();
        int touristsWaiting = map.getWaitingTouristCount();
        int touristsTouring = map.getTouristCountOnTour();
        long keepers = map.getEntities().stream().filter(e -> e instanceof Keeper).count();

        lionLabel.setText("Lions: " + lions);
        zebraLabel.setText("Zebras: " + zebras);
        elephantLabel.setText("Elephants: " + elephants);
        hyenaLabel.setText("Hyenas: " + hyenas);

        jeepActiveLabel.setText("Jeeps Touring: " + jeepsTouring);
        jeepAvailableLabel.setText("Available Jeeps: " + jeepsAvailable);

        touristsTouringLabel.setText("Touring: " + touristsTouring);
        touristsWaitingLabel.setText("Waiting: " + touristsWaiting);

        keeperLabel.setText("Keepers: " + keepers);
        ratingLabel.setText("Rating: " + String.format("%.2f", map.getCurrentAverageRating()));
        weeklyTouristLabel.setText("Visited: " + map.getTotalTouristsThisWeek());


        pack();
    }
}
