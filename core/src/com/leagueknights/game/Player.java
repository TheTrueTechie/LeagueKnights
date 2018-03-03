package com.leagueknights.game;

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
	String anim = "walk";

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

		batch.draw(getAnimation(), x, y);
		controls();
	}

	public TextureRegion getAnimation() {
		// System.out.println(anim);
		TextureRegion ret;
		if (anim.equals("attack")) {
			ret = knightSlashAnim.getKeyFrame(elapsedTime, true);
		}
		else if (anim.equals("walk")) {
			ret = knightWalkAnim.getKeyFrame(elapsedTime, true);
		}
		else if (anim.equals("idle")) {
			ret = knightIdleAnim.getKeyFrame(elapsedTime, true);
		} else {
			ret = knightWalkAnim.getKeyFrame(elapsedTime, true);
		}
		return ret;
	}

	public void controls() {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			anim = "walk";
			if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
				x -= 2;
			else
				x -= 1;
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			anim = "walk";
			if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
				x += 2;
			else
				x += 1;
		} else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			anim = "attack";
		} else {
			anim = "idle";
		}
	}
}