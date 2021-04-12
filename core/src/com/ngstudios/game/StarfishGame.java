package com.ngstudios.game;

public class StarfishGame extends BaseGame{
    public void create(){
        setActiveScreen(new LevelScreen());
    }
}
