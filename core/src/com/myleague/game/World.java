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
	Texture bgSun;
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
		player.create(this);
		
		bgSun = new Texture("tileset/parallax-sun.png");
		bgMountain = new Texture("tileset/parallax-sky.png");
		bgHills = new Texture("tileset/parallax-mountain-mountains.png");
		bgTrees = new Texture("tileset/parallax-mountain-trees.png");
		grass = new Texture("tileset/grass.png");
		
		grassTilesAtlas = new TextureAtlas(Gdx.files.internal("tileset/groundTiles.atlas"));
		grassTiles = new Animation<TextureRegion>(1f, grassTilesAtlas.getRegions());
		
		Random gen = new Random();
		for(int i = -2000; i < 2000; i++) {
			float fl = (float)gen.nextInt(2)+3;
			Tile t = new Tile(grassTiles.getKeyFrame(fl, true), i*64, -52);
			ground.add(t);
		}
		
	}
	
	public void render(SpriteBatch batch) {
		float x = player.getHealth()/100;
		float z = (1f - x)/2;
		float gb = Math.max(Math.min(x, 1f), 0f);
		batch.setColor(Math.max(Math.min(x*1.3f, 1f), 0f), gb, gb, 1);
		batch.draw(bgMountain, player.getX()-560, player.getY()-60, 1280, 720);
		float sunHeight = calculateSunHeight();
		batch.draw(bgSun, player.getX()-64, sunHeight, 256, 256);
		moveBackGround(player.getX());
		batch.draw(bgHills, hillsX, player.getY()-60, bgWidth, 900);
		batch.draw(bgHills, hillsX-1600, player.getY()-60, bgWidth, 900);
		
		batch.draw(bgTrees, treesX, player.getY()-30, bgWidth, 900);
		batch.draw(bgTrees, treesX-1600, player.getY()-30, bgWidth, 900);
		float rgb = Math.max(1-z, 0f);
		batch.setColor(rgb, rgb, rgb, 1);
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
	
	private float calculateSunHeight() {
		float y = 300 - (400 * (1 - player.getHealth()/100f));
		return y;
	}
	
	private int dist(int a, int b) {
		return Math.abs(a-b);
	}

	public void moveBackGround(int px) {
		hillsX = px-(int)( (px% ((bgWidth/2)/0.2) )*0.2);
		treesX = px-(int)( (px% ((bgWidth/2)/0.4) )*0.4);
	}
}
