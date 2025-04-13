package safariSimulator.main.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import safariSimulator.main.Models.Entity.Human.Keeper;
import safariSimulator.main.Models.Point;

public class WinningContainer extends Window {
    public TextButton thanksButton;
    private Window insufficientFundsWindow;
    public MapScreen mapScreen;

    public WinningContainer(Skin skin, MapScreen mapScreen) {
        super("Congratulations!", skin);

        this.mapScreen = mapScreen;

        this.setSize(700, 600);
        this.setColor(0.2f, 0.5f, 0.3f, 0.95f);

        this.setPosition(
            Gdx.graphics.getWidth() / 2f - this.getWidth() / 2,
            Gdx.graphics.getHeight() / 2f - this.getHeight() / 2
        );
        this.setMovable(false);

        Label title = new Label(
            "You managed to collect enough money to make your safari\nthe most successful in the region.\nCongratulations, you won!",
            skin
        );
        title.setFontScale(1.5f);
        title.setColor(0.9f, 1f, 0.2f, 1f);

        thanksButton = new TextButton("Thanks, go back to play!", skin);

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(12);

        table.add(title).colspan(4).center().padBottom(10);
        table.row();

        table.add(thanksButton);
        table.row();

        this.addActor(table);

        thanksButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                WinningContainer.this.remove();
            }
        });
    }
}
