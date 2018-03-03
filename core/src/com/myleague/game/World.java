package com.myleague.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class World {
	Player player = new Player();
	// Dep
	Texture img;
	Sprite sprite;
	float elapsedTime = 0;
	// End Dep
	
	int spriteX = 0;
	
	public void create() {
		player.create();
		
		img = new Texture("badlogic.jpg");


		sprite = new Sprite(img);
		
	}
	
	public void render(SpriteBatch batch) {
		//batch.draw(img2, 50, 50);
		//sprite.draw(batch);
		//sprite.setPosition(spriteX, sprite.getY());
		//spriteX++;
		player.render(batch);
	}
}
