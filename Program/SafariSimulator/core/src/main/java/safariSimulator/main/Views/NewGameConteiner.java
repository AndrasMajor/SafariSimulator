package safariSimulator.main.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class NewGameConteiner extends Window {
    public TextButton easyButton;
    public TextButton mediumButton;
    public TextButton hardButton;
    public TextButton backButton;

    public NewGameConteiner(Skin skin) {
        super("New Game", skin);

        this.setSize(300, 300);
        this.setPosition(
            Gdx.graphics.getWidth() / 2f - this.getWidth() / 2,
            Gdx.graphics.getHeight() / 2f - this.getHeight() / 2
        );
        this.setMovable(false);

        Label title = new Label("Choose difficulty!", skin);
        easyButton = new TextButton("Easy", skin);
        mediumButton = new TextButton("Medium", skin);
        hardButton = new TextButton("Hard", skin);
        backButton = new TextButton("Back", skin);

        this.row();
        this.add(title).pad(20);
        this.row();
        this.add(easyButton).pad(10);
        this.row();
        this.add(mediumButton).pad(10);
        this.row();
        this.add(hardButton).pad(10);
        this.row();
        this.add(backButton).pad(10);
    }
}
