package de.tum.cit.ase.maze;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Entry extends BaseActor
{
    public Entry(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("assets/basictiles.png", 0, 0, 16, 16);
        setBoundaryRectangle();
    }
}