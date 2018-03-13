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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

public class Player {
	private OrthographicCamera camera;
	BitmapFont font;
	TextureAtlas knightIdleAtlas;
	TextureAtlas knightWalkAtlas;
	TextureAtlas knightSlashAtlas;
	TextureAtlas knightBlockAtlas;
	TextureAtlas knightDeathAtlas;
	Animation<TextureRegion> knightIdleAnim;
	Animation<TextureRegion> knightWalkAnim;
	Animation<TextureRegion> knightRunAnim;
	Animation<TextureRegion> knightSlashAnim;
	Animation<TextureRegion> knightBlockAnim;
	Animation<TextureRegion> knightDeathAnim;
	float elapsedTime = 0;
	float health = 100;
	boolean isFacingRight = true;
	int attackTimer = 0;
	int walkSpeed = 1;
	int x = 0;
	int y = 0;
	String anim = "idle";
	ShapeRenderer sr;

	public void create() {
		knightIdleAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/knight_idle.atlas"));
		knightWalkAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/knight_walk.atlas"));
		knightSlashAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/knight_slash.atlas"));
		knightBlockAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/knight_block.atlas"));
		knightDeathAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/knight_death.atlas"));
		knightIdleAnim = new Animation<TextureRegion>(1 / 4f, knightIdleAtlas.getRegions());
		knightWalkAnim = new Animation<TextureRegion>(1 / 8f, knightWalkAtlas.getRegions());
		knightRunAnim = new Animation<TextureRegion>(1 / 4f, knightWalkAtlas.getRegions());
		knightSlashAnim = new Animation<TextureRegion>(1 / 10f, knightSlashAtlas.getRegions());
		knightBlockAnim = new Animation<TextureRegion>(1 / 7f, knightBlockAtlas.getRegions());
		knightDeathAnim = new Animation<TextureRegion>(1 / 9f, knightDeathAtlas.getRegions());
		camera = new OrthographicCamera(1280, 720);
		camera.translate(80, 300);
		camera.update();

		sr = new ShapeRenderer();
		sr.setProjectionMatrix(camera.combined);

		font = new BitmapFont();
		font.setColor(Color.RED);
	}

	public void render(SpriteBatch batch) {
		elapsedTime += Gdx.graphics.getDeltaTime();
		if(!anim.equals("death")) {
			controlsMKB();
		}
		batch.setProjectionMatrix(camera.combined);
		batch.draw(getAnimation(), x, y, 128, 128);
		font.draw(batch, "HEALTH: " + health, x + 25, 500);
		attackTimer--;

		// drawShapes(batch);
		// System.out.println("Player: (" + x + "," + y +"), MouseX: " +
		// Gdx.input.getX() );
	}

	public TextureRegion getAnimation() {
		// System.out.println(anim);
		TextureRegion ret;
		if (anim.equals("attack")) {
			ret = knightSlashAnim.getKeyFrame(elapsedTime, false);
		} else if (anim.equals("block")) {
			ret = knightBlockAnim.getKeyFrame(elapsedTime, false);
		} else if (anim.equals("walk")) {
			ret = knightWalkAnim.getKeyFrame(elapsedTime, true);
		} else if (anim.equals("run")) {
			ret = knightRunAnim.getKeyFrame(elapsedTime, true);
		} else if (anim.equals("death")) {
			ret = knightDeathAnim.getKeyFrame(elapsedTime, false);
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

	/**
	 * Demonstrates the various shaperender capabilities
	 * @param batch
	 */
	public void drawShapes(SpriteBatch batch) {
		batch.end();
		// camera.update();

		sr.begin(ShapeType.Line);
		sr.setColor(1, 1, 0, 1);
		sr.line(x, y, x + 1000, y + 1000);
		sr.rect(x, y, 100, 100);
		sr.circle(Gdx.graphics.getWidth()/8, Gdx.graphics.getWidth()/3, 100);
		sr.end();
		batch.begin();
	}

	public void controlsKB() {
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			anim = "attack";
			attackTimer = 50;
			elapsedTime = 0;
		}
		if (attackTimer < 0) {
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				isFacingRight = false;
				if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
					anim = "run";
					x -= 2;
				} else {
					anim = "walk";
					x -= 1;
				}
			} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				isFacingRight = true;
				if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
					anim = "run";
					x += 2;
				} else {
					anim = "walk";
					x += 1;
				}
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
		}if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			anim = "block";
			attackTimer = 50;
			elapsedTime = 0;
		}
		if (attackTimer < 0) {
			int move = 0;
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
					anim = "walk";
					move = walkSpeed * -8;
				} else {
					anim = "run";
					move = -walkSpeed;
				}
			} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
					anim = "walk";
					move = walkSpeed * 8;
				} else {
					anim = "run";
					move = walkSpeed;
				}
			} else {
				anim = "idle";
			}

			x += move;
			camera.translate(move, 0);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.translate(2, 0);
			// System.out.println(camera.position);
		} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camera.translate(-2, 0);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			takeDamage(-5);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			takeDamage(5);
		}
		camera.update();

		if (Gdx.input.getX() > Gdx.graphics.getWidth()/2) {
			isFacingRight = true;
		} else {
			isFacingRight = false;
		}
	}

	public void takeDamage(int dmg) {
		this.health -= dmg;
		if(health <=0) {
			this.anim= "death";
			elapsedTime = 0;
		}
	}
	
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
}