package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */
public class GameScreen implements Screen {

    private final MazeRunnerGame game;
    private final OrthographicCamera camera;
    private final BitmapFont font;

    private float sinusInput = 0f;

    private final Stage stage;

    private float zoom = 0.28f;
    private final float defaultScreenHeight = 864.0f;


    /**
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(MazeRunnerGame game) {
        this.game = game;

        // Create and configure the camera for the game view
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = zoom;

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements


        // Get the font from the game's skin
        font = game.getSkin().getFont("font");
    }


    // Screen interface methods with necessary functionality
    @Override
    public void render(float delta) {
        final float screenWidth = Gdx.graphics.getWidth();
        final float screenHeight = Gdx.graphics.getHeight();
        // System.out.println("screenWidth: " + screenWidth);
        // System.out.println("screenHeight: " + screenHeight);
        // screenWidth: 1536.0
        // screenHeight: 864.0

        camera.zoom = (screenHeight * zoom) / defaultScreenHeight;

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        stage.draw(); // Draw the stage
//        // Check for escape key press to go back to the menu
//        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
//            game.goToMenu();
//        }
//
//        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen
//
//        camera.update(); // Update the camera
//
//        // Move text in a circular path to have an example of a moving object
//        sinusInput += delta;
//        float textX = (float) (camera.position.x + Math.sin(sinusInput) * 100);
//        float textY = (float) (camera.position.y + Math.cos(sinusInput) * 100);
//
//        // Set up and begin drawing with the sprite batch
//        game.getSpriteBatch().setProjectionMatrix(camera.combined);
//
//        game.getSpriteBatch().begin(); // Important to call this before drawing anything
//
//        // Render the text
//        font.draw(game.getSpriteBatch(), "Press ESC to go to menu", textX, textY);
//
//        // Draw the character next to the text :) / We can reuse sinusInput here
//        game.getSpriteBatch().draw(
//                game.getCharacterDownAnimation().getKeyFrame(sinusInput, true),
//                textX - 96,
//                textY - 64,
//                64,
//                128
//        );
//
//        game.getSpriteBatch().end(); // Important to call this after drawing everything
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    public void setBoard(int[][] board) {
        final float screenWidth = Gdx.graphics.getWidth();
        final float screenHeight = Gdx.graphics.getHeight();
        float diff = (screenWidth - screenHeight) / 7;

        System.out.println("screenWidth: " + screenWidth);
        System.out.println("screenHeight: " + screenHeight);
        System.out.println("diff: " + diff);
        // diff = 40;


        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                int type = board[r][c];

                switch (ElementType.findByOrdinal(type)) {

                    case Wall -> {
                        Wall wall = new Wall(diff + r * 16, c * 16, stage);
                    }
                    case Entry -> {

                    }
                    case Exit -> {
                    }
                    case Trap -> {

                    }
                    case Enemy -> {

                    }
                    case Key -> {
                    }
                    case Floor -> {
                        Floor floor = new Floor( diff + r * 16, c * 16, stage);
                    }
                }
            }
        }

    }

    // Additional methods and logic can be added as needed for the game screen
}
