package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;
import games.spooky.gdx.nativefilechooser.NativeFileChooserCallback;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 * The MenuScreen class is responsible for displaying the main menu of the game.
 * It extends the LibGDX Screen class and sets up the UI components for the menu.
 */
public class MenuScreen implements Screen {

    public int[][] arrayMap;

    private final Stage stage;

    private final MazeRunnerGame game;

    /**
     * Constructor for MenuScreen. Sets up the camera, viewport, stage, and UI elements.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public MenuScreen(MazeRunnerGame game) {
        this.game = game;
        var camera = new OrthographicCamera();
        camera.zoom = 2f; // Set camera zoom for a closer view

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements

        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        // Add a label as a title
        table.add(new Label("Hello World from the Menu!", game.getSkin(), "title")).padBottom(80).row();

        // Create and add a button to load anew map
        TextButton loadNewMap = new TextButton("Load a new map", game.getSkin());
        table.add(loadNewMap).width(300).row();
        loadNewMap.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                // Choose a file to load a new game
                chooseFile(game.getFileChooser());
               // game.goToGame(); // Change to the game screen when button is pressed
            }
        });

        // Create and add a button to load anew map
        TextButton exit = new TextButton("exit", game.getSkin());
        table.add(exit).width(300).row();
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }



    private void chooseFile(NativeFileChooser fileChooser) {
        // Configure
        NativeFileChooserConfiguration conf = new NativeFileChooserConfiguration();

// Starting from user's dir
        conf.directory = Gdx.files.absolute("");

// Filter out all files which do not have the .ogg extension and are not of an audio MIME type - belt and braces
        conf.mimeFilter = "audio/*";
        conf.nameFilter = (dir, name) -> true;

// Add a nice title
        conf.title = "Choose audio file";

        fileChooser.chooseFile(conf, new NativeFileChooserCallback() {
            @Override
            public void onFileChosen(FileHandle fileHandle) {
                // Do stuff with file, yay!
                File file = fileHandle.file();
                System.out.println(file);

                Properties properties = new Properties();
                try {
                    properties.load(new FileInputStream(file));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                final int[] max = {Integer.MIN_VALUE, Integer.MIN_VALUE};
                properties.forEach((k, v) -> {
                    try {
                        String[] rc = ((String) k).split(",");
                        int r = Integer.parseInt(rc[0]);
                        int c = Integer.parseInt(rc[1]);
                        if (r > max[0]) {
                            max[0] = r;
                        }
                        if (c > max[1]) {
                            max[1] = c;
                        }
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                });
                System.out.println("Borders: " + Arrays.toString(max));
                int[][] board = new int[max[0] + 1][max[1] + 1];
                for (var array : board) {
                    Arrays.fill(array, ElementType.Floor.ordinal());
                }
                // Wall- 0 , Entry - 1, Exit - 2, Trap - 3, Enemy - 4, Key - 5, Floor - 6;
                properties.forEach((k, v) -> {
                    try {
                        String[] xy = ((String) k).split(",");
                        int r = Integer.parseInt(xy[0]);
                        int c = Integer.parseInt(xy[1]);
                        int type = Integer.parseInt((String) v);
                        board[r][c] = type;
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                });
                arrayMap = board;
                game.setBoard(board);

            }

            @Override
            public void onCancellation() {
                // Warn user how rude it can be to cancel developer's effort
            }

            @Override
            public void onError(Exception exception) {
                // Handle error (hint: use exception type)
            }
        });
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        stage.draw(); // Draw the stage
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update the stage viewport on resize
    }

    @Override
    public void dispose() {
        // Dispose of the stage when screen is disposed
        stage.dispose();
    }

    @Override
    public void show() {
        // Set the input processor so the stage can receive input events
        Gdx.input.setInputProcessor(stage);
    }

    // The following methods are part of the Screen interface but are not used in this screen.
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
