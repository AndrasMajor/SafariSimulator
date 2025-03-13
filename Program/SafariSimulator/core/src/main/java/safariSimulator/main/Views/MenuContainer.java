package safariSimulator.main.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/** First screen of the application. Displayed after the application is created. */
public class MenuContainer extends Window {
    public TextButton loadGameButton;
    public TextButton newGameButton;

    public MenuContainer(Skin skin) {
        super("Main Menu", skin);

        // BUTTON'S CONTAINER
        this.setSize(300, 200);
        this.setPosition(
            Gdx.graphics.getWidth() / 2f - this.getWidth() / 2,
            Gdx.graphics.getHeight() / 2f - this.getHeight() / 2
        );
        this.setMovable(false);

        // BUTTONS, TITLE
        Label title = new Label("Hi Manager!", skin);
        loadGameButton = new TextButton("Load Game", skin);
        newGameButton = new TextButton("New Game", skin);

        this.row();
        this.add(title).pad(20);
        this.row();
        this.add(loadGameButton).pad(10);
        this.row();
        this.add(newGameButton).pad(10);
    }
}
