package main.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import main.game.FlappyGame;

public class MenuState extends State {
    private Texture background;
    private Texture playBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);

        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(background, 0, 0, FlappyGame.WIDTH, FlappyGame.HEIGHT);
        batch.draw(playBtn, (FlappyGame.WIDTH / 2) - playBtn.getWidth() / 2, (FlappyGame.HEIGHT / 2) - playBtn.getHeight() / 2);
        batch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }
    
}
