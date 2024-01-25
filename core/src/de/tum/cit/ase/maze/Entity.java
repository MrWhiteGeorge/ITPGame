package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {

  public enum Direction {UP, DOWN, RIGHT, LEFT}


  public MazeRunnerGame game;
  public Direction direction = Direction.RIGHT;

  public Sprite sprite;
  public int x;
  public int y;
  public float speed = 0.3f;

  public float tickSpeed;

  public Entity(MazeRunnerGame game, String spritePath) {
    this.game = game;
    sprite = new Sprite(new Texture(spritePath));
  }

/*
  public void draw(SpriteBatch spriteBatch) {
    sprite.setBounds(x * Map.TILE_SIZE + Map.OFFSET_X, y * Map.TILE_SIZE + Map.OFFSET_Y + 4,
        Map.TILE_SIZE, Map.TILE_SIZE);

    sprite.draw(spriteBatch);
  }

  public void update(float delta) {

  }

  public void teleport() {
    if (x > game.map.getWidth() - 1) {
      x = 0;
    }

    if (x < 0) {
      x = game.map.getWidth() - 1;
    }
  }


 */
}
