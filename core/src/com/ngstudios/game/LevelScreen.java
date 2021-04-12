package com.ngstudios.game;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;

public class LevelScreen extends BaseScreen{

    private Turtle turtle;
    private boolean win;
    private int count;

    private final ArrayList<Rock> rocks = new ArrayList<>();
    private final ArrayList<Starfish> starfishs = new ArrayList<>();

    public void initialize() {

        BaseActor ocean = new BaseActor(0, 0,mainStage);
        ocean.loadTexture("water-border.jpg");
        ocean.setSize(1200, 900);
        BaseActor.setWorldBounds(ocean);

        Starfish s1 = new Starfish(400, 400, mainStage);
        starfishs.add(s1);
        count++;
        Starfish s2 = new Starfish(500, 100, mainStage);
        starfishs.add(s2);
        count++;
        Starfish s3 = new Starfish(100, 450, mainStage);
        starfishs.add(s3);
        count++;
        Starfish s4 = new Starfish(200, 250, mainStage);
        starfishs.add(s4);
        count++;

        Rock r1 = new Rock(200, 150, mainStage);
        rocks.add(r1);
        Rock r2 = new Rock(100, 300, mainStage);
        rocks.add(r2);
        Rock r3 = new Rock(300, 350, mainStage);
        rocks.add(r3);
        Rock r4 = new Rock(450, 200, mainStage);
        rocks.add(r4);

        turtle = new Turtle(20,20,mainStage);

        win = false;
    }

    public void update(float dt) {
        for(Rock rock : rocks) {
            turtle.preventOverlap(rock);
        }

        for(Starfish starfish : starfishs){
            if (turtle.overlaps(starfish) && !starfish.isCollected()){
                starfish.collect();
                Whirlpool whirl = new Whirlpool(0,0,mainStage);
                whirl.centerAtActor(starfish);
                whirl.setOpacity(0.25f);
                count--;
            }
        }

        if(count <= 0 && !win){
            win = true;
            BaseActor youWinMessage = new BaseActor(0,0,uiStage);
            youWinMessage.loadTexture("you-win.png");
            youWinMessage.centerAtPosition(400, 300);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }
    }
}
