package main.game.states;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import main.game.FlappyGame;
import main.game.sprites.Bird;
import main.game.sprites.Tube;

public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;
    
    private Texture background;
    private Texture ground;
    private Vector2 groundPosition1, groundPosition2;
    private Bird bird;
    private Array<Tube> tubes;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        
        camera.setToOrtho(false, FlappyGame.WIDTH / 2, FlappyGame.HEIGHT / 2);

        background = new Texture("bg.png");
        bird = new Bird(50, 500);
        tubes = new Array<Tube>();
        ground = new Texture("ground.png");
        
        groundPosition1 = new Vector2(camera.position.x - camera.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPosition2 = new Vector2((camera.position.y - camera.viewportHeight / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube((TUBE_SPACING + Tube.TUBE_WIDTH) * i));
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    protected void update(float delta) {
        handleInput();
        updateGround();
        bird.update(delta);
        camera.position.x = bird.getPosition().x + 80;

        for (Tube tube : tubes) {
            if (camera.position.x - (camera.viewportWidth / 2) > tube.getPositionTopTube().x + Tube.TUBE_WIDTH) {
                tube.reposition(tube.getPositionTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if (tube.collides(bird.getBounds())) {
                gsm.set(new PlayState(gsm));
                break;
            }
        }

        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            gsm.set(new PlayState(gsm));
        }
    
        camera.update();
    }

    @Override
    protected void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        batch.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);

        for (Tube tube : tubes) {
            batch.draw(tube.getTopTube(), tube.getPositionTopTube().x, tube.getPositionTopTube().y);
            batch.draw(tube.getBottomTube(), tube.getPositionBottomTube().x, tube.getPositionBottomTube().y);
        }

        batch.draw(ground, groundPosition1.x, groundPosition1.y);
        batch.draw(ground, groundPosition2.x, groundPosition2.y);

        batch.end();
    }

    @Override
    protected void dispose() {
        background.dispose();
        bird.dispose();
        ground.dispose();

        for (Tube tube : tubes) {
            tube.dispose();
        }
    }

    private void updateGround() {
        if (camera.position.x - (camera.viewportWidth / 2) > groundPosition1.x + ground.getWidth()) {
            groundPosition1.add(ground.getWidth() * 2, 0);
        }

        if (camera.position.x - (camera.viewportWidth / 2) > groundPosition2.x + ground.getWidth()) {
            groundPosition2.add(ground.getWidth() * 2, 0);
        }
    }
    
}
