package main.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import main.game.states.GameStateManager;
import main.game.states.MenuState;

public class FlappyGame extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	private GameStateManager gsm;
	private SpriteBatch batch;
	private Music music;

	@Override
	public void create() {
		batch = new SpriteBatch();
		
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();

		gsm = new GameStateManager();
		gsm.push(new MenuState(gsm));
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void dispose() {
		music.dispose();
	}
}
