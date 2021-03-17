package com.ngstudios.game.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ngstudios.game.GameBeta;
import com.ngstudios.game.StarfishCollector;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Game myGame = new StarfishCollector();
		LwjglApplication launcher = new LwjglApplication(myGame, "StarfishCollector", 800,600);
	}
}
