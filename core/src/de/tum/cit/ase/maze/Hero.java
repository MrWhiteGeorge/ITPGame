package de.tum.cit.ase.maze;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;

public class Hero extends BaseActor
{
    Animation north;
    Animation south;
    Animation east;
    Animation west;
    
    public Hero(float x, float y, Stage s)
    {
        super(x,y,s);

        /*
         int fileCount = fileNames.length;
        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for (int n = 0; n < fileCount; n++)
        {
            String fileName = fileNames[n];
            Texture texture = new Texture( Gdx.files.internal(fileName) );
            texture.setFilter( TextureFilter.Linear, TextureFilter.Linear );
            textureArray.add( new TextureRegion( texture, (int)x, (int)y, (int)width, (int)height) );
        }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if (loop)
            anim.setPlayMode(PlayMode.LOOP);
        else
            anim.setPlayMode(PlayMode.NORMAL);

        if (animation == null)
            setAnimation(anim);
         */

        String fileName = "assets/hero.png";
        int rows = 4;
        int cols = 3;
        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;
        float frameDuration = 0.2f;

        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);

        Array<TextureRegion> textureArray = new Array<TextureRegion>();
        for (int c = 0; c < cols; c++)
            textureArray.add( temp[0][c] );
        south = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);

        textureArray.clear();
        for (int c = 0; c < cols; c++)
            textureArray.add( temp[1][c] );
        west = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);

        textureArray.clear();
        for (int c = 0; c < cols; c++)
            textureArray.add( temp[2][c] );
        east = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);

        textureArray.clear();
        for (int c = 0; c < cols; c++)
            textureArray.add( temp[3][c] );
        north = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);

        setAnimation(south);

        // set after animation established
        setBoundaryPolygon(8);

        setAcceleration(800);
        setMaxSpeed(100);
        setDeceleration(800);
    }

    public void act(float dt)
    {
        super.act(dt);

        // hero movement controls
        if (Gdx.input.isKeyPressed(Keys.LEFT)) 
            accelerateAtAngle(180);
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) 
            accelerateAtAngle(0);
        if (Gdx.input.isKeyPressed(Keys.UP)) 
            accelerateAtAngle(90);
        if (Gdx.input.isKeyPressed(Keys.DOWN)) 
            accelerateAtAngle(270);

        // pause animation when character not moving
        if ( getSpeed() == 0 )
        {
            setAnimationPaused(true);
        }
        else
        {
            setAnimationPaused(false);
            // set direction animation
            float angle = getMotionAngle();
            
            if (angle >= 45 && angle <= 135)
                setAnimation(north);
            else if (angle > 135 && angle < 225)
                setAnimation(west);
            else if (angle >= 225 && angle <= 315)
                setAnimation(south);
            else
                setAnimation(east);
        }

        applyPhysics(dt);
    }
}