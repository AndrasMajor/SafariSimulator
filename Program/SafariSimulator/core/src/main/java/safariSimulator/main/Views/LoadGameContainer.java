package safariSimulator.main.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class LoadGameContainer extends Window {

    public TextButton loadButton1;
    public TextButton loadButton2;
    public TextButton loadButton3;
    public TextButton backButton;

    public LoadGameContainer(Skin skin) {
        super("Load Game", skin);

        this.setSize(300, 300);
        this.setPosition(
            Gdx.graphics.getWidth() / 2f - this.getWidth() / 2,
            Gdx.graphics.getHeight() / 2f - this.getHeight() / 2
        );
        this.setMovable(false);

        Label title = new Label("Choose!", skin);
        loadButton1 = new TextButton("Slot 1", skin);
        loadButton2 = new TextButton("Slot 2", skin);
        loadButton3 = new TextButton("Slot 3", skin);
        backButton = new TextButton("back", skin);

        this.row();
        this.add(title).pad(20);
        this.row();
        this.add(loadButton1).pad(10);
        this.row();
        this.add(loadButton2).pad(10);
        this.row();
        this.add(loadButton3).pad(10);
        this.row();
        this.add(backButton).pad(10);
    }
}
