package com.myleague.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile {
	TextureRegion tex;
	int x;
	int y;
	public Tile(TextureRegion t, int newX, int newY) {
		this.tex = t;
		this.x = newX;
		this.y = newY;
	}
	public TextureRegion getTex() {
		return tex;
	}
	public void setTex(TextureRegion tex) {
		this.tex = tex;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
