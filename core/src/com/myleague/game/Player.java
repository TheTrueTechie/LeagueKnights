package com.myleague.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {

	TextureAtlas knightIdleAtlas;
	TextureAtlas knightWalkAtlas;
	TextureAtlas knightSlashAtlas;
	Animation<TextureRegion> knightIdleAnim;
	Animation<TextureRegion> knightWalkAnim;
	Animation<TextureRegion> knightSlashAnim;
	float elapsedTime = 0;

	int x = 0;
	int y = 20;
	String anim = "walk_left";
	

	public void create() {
		knightIdleAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/knight_idle.atlas"));
		knightWalkAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/knight_walk.atlas"));
		knightSlashAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/knight_slash.atlas"));
		knightIdleAnim = new Animation<TextureRegion>(1 / 4f, knightIdleAtlas.getRegions());
		knightWalkAnim = new Animation<TextureRegion>(1 / 8f, knightWalkAtlas.getRegions());
		knightSlashAnim = new Animation<TextureRegion>(1 / 10f, knightSlashAtlas.getRegions());
	}

	public void render(SpriteBatch batch) {
		elapsedTime += Gdx.graphics.getDeltaTime();

		batch.draw(getAnimation(), x, y, 128, 128);
		controls();
	}

	public TextureRegion getAnimation() {
		// System.out.println(anim);
		TextureRegion ret;
		if (anim.equals("attack")) {
			ret = knightSlashAnim.getKeyFrame(elapsedTime, true);
		} else if (anim.equals("walk_right")) {
			ret = knightWalkAnim.getKeyFrame(elapsedTime, true);
			if(ret.isFlipX()) {
				ret.flip(true, false);
			}
		} else if (anim.equals("walk_left")) {
			ret = knightWalkAnim.getKeyFrame(elapsedTime, true);
			if(!ret.isFlipX()) {
				ret.flip(true, false);
			}
		} else if (anim.equals("idle")) {
			ret = knightIdleAnim.getKeyFrame(elapsedTime, true);
		} else {
			ret = knightWalkAnim.getKeyFrame(elapsedTime, true);
		}
		return ret;
	}

	public void controls() {
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			anim = "attack";
		} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
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