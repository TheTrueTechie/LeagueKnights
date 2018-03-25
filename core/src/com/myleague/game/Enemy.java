package com.myleague.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy {
	World world;
	int x = 0;
	int y = 5;
	float health = 10;
	TextureAtlas idleAtlas;
	TextureAtlas walkAtlas;
	TextureAtlas slashAtlas;
	TextureAtlas deathAtlas;
	Animation<TextureRegion> idleAnim;
	Animation<TextureRegion> walkAnim;
	Animation<TextureRegion> runAnim;
	Animation<TextureRegion> slashAnim;
	Animation<TextureRegion> deathAnim;
	
	float elapsedTime = 0;
	boolean isFacingRight = false;
	String anim = "idle";
	private boolean isDying = false;
	
	
	public void create(World world) {
		idleAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/EnemyRogue_Idle.atlas"));
		walkAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/EnemyRogue_Walk.atlas"));
		slashAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/EnemyRogue_Slash.atlas"));
		deathAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/EnemyRogue_Death.atlas"));
		idleAnim = new Animation<TextureRegion>(1 / 5f, idleAtlas.getRegions());
		walkAnim = new Animation<TextureRegion>(1 / 5f, walkAtlas.getRegions());
		runAnim = new Animation<TextureRegion>(1 / 5f, walkAtlas.getRegions());
		slashAnim = new Animation<TextureRegion>(1 / 5f, slashAtlas.getRegions());
		deathAnim = new Animation<TextureRegion>(1 / 5f, deathAtlas.getRegions());

		this.world = world;
	}
	
	public void takeDamage(int dmg) {
		health -= dmg;
		if ((int) health <= 0 && isDying == false) {
			setAnim("death");
			elapsedTime = 0;
			isDying  = true;
		}
	}
	
	private void setAnim(String string) {
		// TODO Auto-generated method stub
		this.anim = string;
	}

	public void render(SpriteBatch batch) {
		elapsedTime += Gdx.graphics.getDeltaTime();
		act();
		batch.draw(getAnimation(), x, y, 100, 100);
	}
	
	public void act() {
		
	}
	
	public TextureRegion getAnimation() {
		TextureRegion ret;
		if (anim.equals("attack")) {
			ret = slashAnim.getKeyFrame(elapsedTime, false);
		} else if (anim.equals("walk")) {
			ret = walkAnim.getKeyFrame(elapsedTime, true);
		} else if (anim.equals("run")) {
			ret = runAnim.getKeyFrame(elapsedTime, true);
		} else if (anim.equals("death")) {
			ret = deathAnim.getKeyFrame(elapsedTime, false);
		} else {
			ret = idleAnim.getKeyFrame(elapsedTime, true);
		}

		if (!isFacingRight && !ret.isFlipX()) {
			ret.flip(true, false);
		} else if (isFacingRight && ret.isFlipX()) {
			ret.flip(true, false);
		}
		return ret;
	}
	
	public void setX(int nX) {
		this.x = nX;
	}
	
	public int getX() {
		return this.x;
	}
}
