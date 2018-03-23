package com.myleague.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Dialog {
	String speakerName;
	String content;
	Texture profile;
	OrthographicCamera camera;
	

	BitmapFont font;
	
	ShapeRenderer sr;
	public Dialog(String name, String content, Texture profile, OrthographicCamera cam) {
		this.speakerName = name;
		this.content = content;
		this.profile = profile;
		this.camera = cam;
		
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		
		sr = new ShapeRenderer();
		sr.setProjectionMatrix(camera.combined);
	}
	
	public void render(SpriteBatch batch) {
		drawDialogBox(batch);
		drawDialogContent(batch);
	}
	
	public void drawDialogBox(SpriteBatch batch) {
		batch.end();
		
		sr.begin(ShapeType.Filled);
		sr.setColor(0.5f, 0.8f, 0, 0.5f);
		sr.rect(-Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()-200, 1000, 100);	
		sr.end();
		sr.begin(ShapeType.Line);
		sr.setColor(1f, 1f, 1f, 0.9f);
		sr.line(-Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()-125, -Gdx.graphics.getWidth()/3+200, Gdx.graphics.getHeight()-125);
		sr.end();
		
		batch.begin();
	}
	
	public void drawDialogContent(SpriteBatch batch) {
		batch.draw(profile, camera.position.x-Gdx.graphics.getWidth()/2+130, Gdx.graphics.getHeight()-200, 100, 100);
		font.draw(batch, this.speakerName, camera.position.x-Gdx.graphics.getWidth()/2+250, Gdx.graphics.getHeight()-110);
		font.draw(batch, this.content, camera.position.x-Gdx.graphics.getWidth()/2+250, Gdx.graphics.getHeight()-130);
	}
}
