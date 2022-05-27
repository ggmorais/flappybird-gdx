package main.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		
		config.setWindowedMode(FlappyGame.WIDTH, FlappyGame.HEIGHT);
		config.setForegroundFPS(60);
		config.setTitle("GDX Game");

		new Lwjgl3Application(new FlappyGame(), config);
	}
}
