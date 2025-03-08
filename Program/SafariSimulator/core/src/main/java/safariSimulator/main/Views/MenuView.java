package safariSimulator.main.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** First screen of the application. Displayed after the application is created. */
public class MenuView implements Screen {
    private Texture backgroundTexture;
    private SpriteBatch batch;

    private Skin skin;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;

    public MenuView() {
        // BACKGROUND
        backgroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));
        batch = new SpriteBatch();


        // CAMERA, VIEWPORT, STAGE
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // BUTTON'S CONTAINER
        Window container = new Window("", skin);
        container.setSize(300, 200);
        container.setPosition(
            Gdx.graphics.getWidth() / 2f - container.getWidth() / 2,
            Gdx.graphics.getHeight() / 2f - container.getHeight() / 2
        );
        container.setMovable(false);

        // BUTTONS, TITLE
        Label title = new Label("Hi Manager!", skin);
        TextButton loadGameButton = new TextButton("Load Game", skin);
        TextButton newGameButton = new TextButton("New Game", skin);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        // LISTENERS
        loadGameButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                System.out.println("Csicska Bundáskenyér liga");
            }
        });
        loadGameButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                System.out.println("Csicska Bundáskenyér liga");
            }
        });

        container.row();
        container.add(title).pad(20);
        container.row();
        container.add(loadGameButton).pad(10);
        container.row();
        container.add(newGameButton).pad(10);


        stage.addActor(container);
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        batch.dispose();
        skin.dispose();
        stage.dispose();
    }
}
