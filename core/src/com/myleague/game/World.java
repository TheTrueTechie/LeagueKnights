package com.myleague.game;

import java.util.ArrayList;
import java.util.Random;

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
	Texture bgTrees;
	
	Texture grass;

	TextureAtlas grassTilesAtlas;
	Animation<TextureRegion> grassTiles;
	
	ArrayList<Tile> ground = new ArrayList<Tile>();
	
	int bgWidth = 1600;
	int hillsX = 0;
	int treesX = 0;
	
	public void create() {
		player.create();
		
		bgMountain = new Texture("tileset/parallax-mountain-bg.png");
		bgHills = new Texture("tileset/parallax-mountain-mountains.png");
		bgTrees = new Texture("tileset/parallax-mountain-trees.png");
		
		grass = new Texture("tileset/grass.png");
		
		grassTilesAtlas = new TextureAtlas(Gdx.files.internal("tileset/groundTiles.atlas"));
		grassTiles = new Animation<TextureRegion>(1f, grassTilesAtlas.getRegions());
		//img = new Texture("badlogic.jpg");
		Random gen = new Random();
		for(int i = -2000; i < 2000; i++) {
			float fl = (float)gen.nextInt(2)+3;
			Tile t = new Tile(grassTiles.getKeyFrame(fl, true), i*64, -52);
			ground.add(t);
		}

		//sprite = new Sprite(img);
		
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(bgMountain, player.getX()-560, player.getY()-60, 1280, 720);
		moveBackGround(player.getX());
		batch.draw(bgHills, hillsX, player.getY()-60, bgWidth, 900);
		batch.draw(bgHills, hillsX-1600, player.getY()-60, bgWidth, 900);
		
		batch.draw(bgTrees, treesX, player.getY()-30, bgWidth, 900);
		batch.draw(bgTrees, treesX-1600, player.getY()-30, bgWidth, 900);

		//batch.draw(img2, 50, 50);
		//sprite.draw(batch);
		//sprite.setPosition(spriteX, sprite.getY());
		//spriteX++;
		player.render(batch);
		renderGround(batch);
	}
	
	public void renderGround(SpriteBatch batch) {
		for (Tile t : ground) {
			if(dist(t.getX(), player.getX()) < Gdx.graphics.getWidth()*0.6) {
				batch.draw(t.getTex(), t.getX(), t.getY(), 64, 64);
			}
		}
	}
	
	private int dist(int a, int b) {
		return Math.abs(a-b);
	}

	public void moveBackGround(int px) {
		hillsX = px-(int)( (px% ((bgWidth/2)/0.2) )*0.2);
		treesX = px-(int)( (px% ((bgWidth/2)/0.4) )*0.4);
	}
}
