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
	public OrthographicCamera camera;
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
	float age = 0;
	float lifespan = 100;
	float health = 100;
	boolean isFacingRight = true;
	boolean isDying = false;
	int attackTimer = 0;
	int idleTimer = 0;
	int walkSpeed = 1;
	int velocity = 0;
	int sprintValue = 1;
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
		knightWalkAnim = new Animation<TextureRegion>(1 / 4f, knightWalkAtlas.getRegions());
		knightRunAnim = new Animation<TextureRegion>(1 / 8f, knightWalkAtlas.getRegions());
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

		KnightInputProcessor inputProcessor = new KnightInputProcessor(this);
		Gdx.input.setInputProcessor(inputProcessor);
	}

	public void render(SpriteBatch batch) {
		elapsedTime += Gdx.graphics.getDeltaTime();

		calculateAge();
		calculateHealth();
		if (attackTimer <= 0 && !anim.equals("death")) {
			determinePlayerFacing();
			int moveVal = walkSpeed * velocity * sprintValue;
			camera.translate(moveVal, 0);
			camera.update();
			x += moveVal;
			if (moveVal == 1) {
				setAnim("walk");
			} else if (moveVal == 8) {
				setAnim("run");
			} else {
				setAnim("idle");
			}
		}
		batch.setProjectionMatrix(camera.combined);
		batch.draw(getAnimation(), x, y, 128, 128);
		//font.draw(batch, "HEALTH: " + (int) this.health, x + 25, 500);
		attackTimer--;
		idleTimer--;

		// drawShapes(batch);
	}

	private void calculateAge() {
		// TODO Auto-generated method stub
		age += Gdx.graphics.getDeltaTime() * 10;
	}

	private void calculateHealth() {
		this.health = lifespan - (age / lifespan);
		if ((int) health <= 0 && isDying == false) {
			setAnim("death");
			elapsedTime = 0;
			isDying = true;
		}
	}

	public TextureRegion getAnimation() {
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
	 * 
	 * @param batch
	 */
	public void drawShapes(SpriteBatch batch) {
		batch.end();

		sr.begin(ShapeType.Line);
		sr.setColor(1, 1, 0, 1);
		sr.line(x, y, x + 1000, y + 1000);
		sr.rect(x, y, 100, 100);
		sr.circle(Gdx.graphics.getWidth() / 8, Gdx.graphics.getWidth() / 3, 100);
		sr.end();
		batch.begin();
	}

	public void attack() {
		if (!isDying) {
			anim = "attack";
			attackTimer = 30;
			elapsedTime = 0;
			SoundHandler.playSlash();
		}
	}

	public void setVelocity(int v) {
		velocity = v;
	}

	public void setSprintValue(int s) {
		sprintValue = s;
	}

	public void setIdleTimer(int i) {
		idleTimer = i;
	}

	public void setAnim(String a) {
		anim = a;
	}

	public void setAge(float a) {
		this.age = a * 100;
	}

	public void RunningInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && velocity > 0) {
			anim = "run";
			setVelocity(walkSpeed * 8);
		}
	}

	public void determinePlayerFacing() {
		if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
			isFacingRight = true;
		} else {
			isFacingRight = false;
		}
	}

	public void controlsMKB() {
		System.out.println(Gdx.input.isButtonPressed(Input.Buttons.LEFT));
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && attackTimer <= 0) {
			System.out.println("LMB");
			anim = "attack";
			attackTimer = 30;
			elapsedTime = 0;
			SoundHandler.playSlash();
		}
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
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
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			takeDamage(-5);
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			takeDamage(5);
		}
		camera.update();

		if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
			isFacingRight = true;
		} else {
			isFacingRight = false;
		}
	}

	public void takeDamage(int dmg) {
		this.age += dmg;
		calculateHealth();
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public float getHealth() {
		return this.health;
	}

	public void toggleMoveLeft() {
		// TODO Auto-generated method stub
		velocity = -1;
	}

	public void toggleMoveRight() {
		// TODO Auto-generated method stub
		velocity = 1;
	}
}