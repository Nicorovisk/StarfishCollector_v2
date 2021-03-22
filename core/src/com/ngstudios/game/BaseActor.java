package com.ngstudios.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

public class BaseActor extends Actor {

    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;
    private Vector2 velocityVec;

    public BaseActor(float x, float y, Stage s){

        //call constructor from Actor Class
        super();

        //perform additional initialization tasks
        setPosition(x, y);
        s.addActor(this);

        animation = null;
        elapsedTime = 0;
        animationPaused = false;
        velocityVec = new Vector2(0,0);

    }

    public void setAnimation(Animation<TextureRegion> anim){

        animation = anim;
        TextureRegion tr = animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize(w, h);
        setOrigin(w/2, h/2);
    }

    public void setSpeed(float speed){

        //if length is zero, then assume motion angle is zero degrees
        if (velocityVec.len() == 0){
            velocityVec.set(speed,0);
        }
        else{
            velocityVec.setLength(speed);
        }
    }

    public float getSpeed(){
        return velocityVec.len();
    }

    public void setMotionAngle(float angle){
        velocityVec.setAngle(angle);
    }

    public float getMotionAngle(){
        return velocityVec.angle();
    }

    public boolean isMoving(){
        return (getSpeed() > 0);
    }

    public void setAnimationPaused(boolean pause){

        animationPaused = pause;
    }

    public void act(float dt){
        super.act(dt);

        if(!animationPaused){
            elapsedTime += dt;
        }
    }

    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);

        //apply color tint effect
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);

        if (animation != null && isVisible()){
            batch.draw(animation.getKeyFrame(elapsedTime), getX(), getY(), getOriginX(), getOriginY(), getWidth(),
                    getHeight(), getScaleX(), getScaleY(), getRotation());
        }

    }

    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop){

        int fileCount = fileNames.length;
        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for(int n = 0; n < fileCount; n++){

            String fileName = fileNames[n];
            Texture texture =  new Texture(fileName);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureArray.add(new TextureRegion(texture));

        }
        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if(loop){
            anim.setPlayMode(Animation.PlayMode.LOOP);
        }
        else{
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        }

        if (animation == null){
            setAnimation(anim);
        }

        return anim;
    }

    public Animation<TextureRegion> loadAnimationFromSheet(String fileName, int rows, int cols, float frameDuration,
                                                           boolean loop){

        Texture texture = new Texture(fileName);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;

        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);

        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for(int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                textureArray.add(temp[r][c]);
            }
        }

        Animation<TextureRegion> anim =  new Animation<TextureRegion>(frameDuration, textureArray);

        if (loop){
            anim.setPlayMode(Animation.PlayMode.LOOP);
        }
        else{
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        }

        if(animation == null){
            setAnimation(anim);
        }

        return anim;
    }

    public Animation<TextureRegion> loadTexture(String fileName){

        String[] fileNames = new String[1];
        fileNames[0] = fileName;
        return loadAnimationFromFiles(fileNames, 1, true);
    }

    public boolean isAnimationFinished(){
        return animation.isAnimationFinished(elapsedTime);
    }
}
