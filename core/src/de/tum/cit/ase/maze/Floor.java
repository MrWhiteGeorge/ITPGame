package de.tum.cit.ase.maze;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class Floor extends BaseActor
{
    public static final int NORTH = 0;
    public static final int SOUTH = 1;
    public static final int EAST  = 2;
    public static final int WEST  = 3;
    public static int[] directionArray = {NORTH, SOUTH, EAST, WEST};

    private Floor[] neighborArray; // some may be null!

    private boolean connected;  // used in maze generation
    private boolean visited;    // used in pathfinding
    private Floor previousFloor;  // used in pathfinding

    public Floor(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("assets/basictiles.png", 80, 16, 16, 16);

        
        // t = wall thickness in pixels
        float t = 6;

        neighborArray = new Floor[4];
        // contents initialized by Maze class

        connected = false;
        visited = false;
    }

    public void setNeighbor(int direction, Floor neighbor)
    {  neighborArray[direction] = neighbor;  }

    public boolean hasNeighbor(int direction)
    {  return neighborArray[direction] != null;  }

    public Floor getNeighbor(int direction)
    {  return neighborArray[direction];  }

    // check if wall in this direction still exists

    public void setConnected(boolean b)
    {  connected = b;  }

    public boolean isConnected()
    {  return connected;  }
    
     public boolean hasUnconnectedNeighbor()
    {
        for (int direction : directionArray)
        {
            if ( hasNeighbor(direction) && !getNeighbor(direction).isConnected() )
                return true;
        }
        return false;
    }

    public Floor getRandomUnconnectedNeighbor()
    {
        ArrayList<Integer> directionList = new ArrayList<Integer>();

        for (int direction : directionArray)
        {
            if ( hasNeighbor(direction) && !getNeighbor(direction).isConnected() )
                directionList.add(direction);
        }

        int directionIndex = (int)Math.floor( Math.random() * directionList.size() );
        int direction = directionList.get(directionIndex);
        return getNeighbor(direction);
    }

    public void setVisited(boolean b)
    {  visited = b;  }

    public boolean isVisited()
    {  return visited;  }
    
     public void setPreviousRoom(Floor r)
    {  previousFloor = r;  }

    public Floor getPreviousRoom()
    {  return previousFloor;  }

}