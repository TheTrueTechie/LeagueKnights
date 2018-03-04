package com.myleague.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class Player {
	private OrthographicCamera camera;
	BitmapFont font;
	TextureAtlas knightIdleAtlas;
	TextureAtlas knightWalkAtlas;
	TextureAtlas knightSlashAtlas;
	TextureAtlas knightDeathAtlas;
	Animation<TextureRegion> knightIdleAnim;
	Animation<TextureRegion> knightWalkAnim;
	Animation<TextureRegion> knightSlashAnim;
	Animation<TextureRegion> knightDeathAnim;
	float elapsedTime = 0;
	float health = 100;
	boolean isFacingRight = true;
	int attackTimer = 0;
	int walkSpeed = 1;
	int x = 0;
	int y = 0;
	String anim = "idle";

	public void create() {
		knightIdleAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/knight_idle.atlas"));
		knightWalkAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/knight_walk.atlas"));
		knightSlashAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/knight_slash.atlas"));
		knightDeathAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/knight_death.atlas"));
		knightIdleAnim = new Animation<TextureRegion>(1 / 4f, knightIdleAtlas.getRegions());
		knightWalkAnim = new Animation<TextureRegion>(1 / 8f, knightWalkAtlas.getRegions());
		knightSlashAnim = new Animation<TextureRegion>(1 / 10f, knightSlashAtlas.getRegions());
		knightDeathAnim = new Animation<TextureRegion>(1 / 9f, knightDeathAtlas.getRegions());
		camera = new OrthographicCamera(1280, 720);
		camera.translate(80, 300);
		camera.update();

		
		font = new BitmapFont();
		font.setColor(Color.RED);
	}

	public void render(SpriteBatch batch) {
		elapsedTime += Gdx.graphics.getDeltaTime();
		controlsMKB();
		batch.setProjectionMatrix(camera.combined);
		batch.draw(getAnimation(), x, y, 128, 128);
		font.draw(batch, "HEALTH: " + health, x, 590);
		attackTimer--;
		
		System.out.println("Player: (" + x + "," + y +"), MouseX: " + Gdx.input.getX() );
	}

	public TextureRegion getAnimation() {
		// System.out.println(anim);
		TextureRegion ret;
		if (anim.equals("attack")) {
			ret = knightSlashAnim.getKeyFrame(elapsedTime, false);
		} else if (anim.equals("walk")) {
			ret = knightWalkAnim.getKeyFrame(elapsedTime, true);
		} else if (anim.equals("death")) {
			ret = knightDeathAnim.getKeyFrame(elapsedTime, true);
		} else {
			ret = knightIdleAnim.getKeyFrame(elapsedTime, true);
		}

		if (!isFacingRight && !ret.isFlipX()) {
			ret.flip(true, false);
		} else if (isFacingRight && ret.isFlipX()) {
			ret.flip(true, false);
		}
		return ret;
	}

	public void controlsKB() {
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			anim = "attack";
			attackTimer = 50;
			elapsedTime = 0;
		}
		if (attackTimer < 0) {
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				anim = "walk";
				isFacingRight = false;
				if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
					x -= 2;
				else
					x -= 1;
			} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				anim = "walk";
				isFacingRight = true;
				if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
					x += 2;
				else
					x += 1;
			} else {
				anim = "idle";
			}
		}
	}
	
	public void controlsMKB() {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			anim = "attack";
			attackTimer = 50;
			elapsedTime = 0;
			SoundHandler.playSlash();
		}
		if (attackTimer < 0) {
			int move = 0;
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				anim = "walk";
				if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) 
					move = walkSpeed*-2;
				else
					move = -walkSpeed;
			} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				anim = "walk";
				if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
					move = walkSpeed*2;
				else
					move = walkSpeed;
			} else {
				anim = "idle";
			}
			
			x += move;
			camera.translate(move, 0);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.translate(2, 0);
			System.out.println(camera.position);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camera.translate(-2, 0);
		}
		camera.update();
		
		if(Gdx.input.getX() > 625) {
			isFacingRight = true;
		}
		else {
			isFacingRight = false;
		}
	}
}