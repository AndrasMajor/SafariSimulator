package safariSimulator.main.Views;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.*;
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

    public MapSummaryView(Skin skin, Map map) {
        super(skin);
        this.map = map;

        setBackground(skin.newDrawable("white", 0, 0, 0, 0.5f));
        pad(8);
        bottom().left();

        lionLabel = new Label("", skin);
        zebraLabel = new Label("", skin);
        elephantLabel = new Label("", skin);
        hyenaLabel = new Label("", skin);

        jeepActiveLabel = new Label("", skin);
        jeepAvailableLabel = new Label("", skin);

        touristsWaitingLabel = new Label("", skin);
        touristsTouringLabel = new Label("", skin);

        add(new Label("Animals:", skin)).left().row();
        add(lionLabel).left().row();
        add(zebraLabel).left().row();
        add(elephantLabel).left().row();
        add(hyenaLabel).left().padBottom(5).row();

        add(new Label("Jeeps:", skin)).left().row();
        add(jeepActiveLabel).left().row();
        add(jeepAvailableLabel).left().padBottom(5).row();

        add(new Label("Tourists:", skin)).left().row();
        add(touristsTouringLabel).left().row();
        add(touristsWaitingLabel).left().row();

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

        lionLabel.setText("Lions: " + lions);
        zebraLabel.setText("Zebras: " + zebras);
        elephantLabel.setText("Elephants: " + elephants);
        hyenaLabel.setText("Hyenas: " + hyenas);

        jeepActiveLabel.setText("Jeeps Touring: " + jeepsTouring);
        jeepAvailableLabel.setText("Available Jeeps: " + jeepsAvailable);

        touristsTouringLabel.setText("Touring: " + touristsTouring);
        touristsWaitingLabel.setText("Waiting: " + touristsWaiting);
    }
}
