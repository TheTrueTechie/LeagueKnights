package com.myleague.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
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
	int x = 0;
	int y = 20;
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

		font = new BitmapFont();
		font.setColor(Color.RED);
	}

	public void render(SpriteBatch batch) {
		elapsedTime += Gdx.graphics.getDeltaTime();

		controls();
		batch.draw(getAnimation(), x, y, 128, 128);
		font.draw(batch, "HEALTH: " + health, 10, 590);
		attackTimer--;
	}

	public TextureRegion getAnimation() {
		// System.out.println(anim);
		TextureRegion ret;
		if (anim.equals("attack")) {
			ret = knightSlashAnim.getKeyFrame(elapsedTime, false);
		} else if (anim.equals("walk_right")) {
			isFacingRight = true;
			ret = knightWalkAnim.getKeyFrame(elapsedTime, true);
		} else if (anim.equals("walk_left")) {
			isFacingRight = false;
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

	public void controls() {
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			anim = "attack";
			attackTimer = 50;
			elapsedTime = 0;
		}
		if (attackTimer < 0) {
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				anim = "walk_left";
				if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
					x -= 2;
				else
					x -= 1;
			} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				anim = "walk_right";
				if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
					x += 2;
				else
					x += 1;
			} else {
				anim = "idle";
			}
		}
	}
}