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
	Texture bgMountain;
	Texture bgHills;
	
	
	int bgWidth = 1600;
	int hillsX = 0;
	
	public void create() {
		player.create();

		bgMountain = new Texture("tileset/parallax-mountain-bg.png");
		bgHills = new Texture("tileset/parallax-mountain-mountains.png");
		//img = new Texture("badlogic.jpg");


		//sprite = new Sprite(img);
		
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(bgMountain, player.getX()-560, player.getY()-60, 1280, 720);
		moveBackGround(player.getX());
		batch.draw(bgHills, hillsX, player.getY()-60, bgWidth, 900);
		batch.draw(bgHills, hillsX-1600, player.getY()-60, bgWidth, 900);
		//batch.draw(img2, 50, 50);
		//sprite.draw(batch);
		//sprite.setPosition(spriteX, sprite.getY());
		//spriteX++;
		player.render(batch);
	}
	
	public void moveBackGround(int px) {
		hillsX = px-(int)( (px% ((bgWidth/2)/0.2) )*0.2);
	}
}
