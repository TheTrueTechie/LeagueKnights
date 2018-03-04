package com.myleague.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyLeague extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont font;
	Texture img;
	World world = new World();
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.FIREBRICK);
		//img = new Texture("badlogic.jpg");
		world.create();
	}

	@Override
	public void render () {
		Gdx.graphics.setTitle("FPS : " + Gdx.graphics.getFramesPerSecond());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		world.render(batch);
		//font.draw(batch, "LEAGUE KNIGHTS", 300, 400);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
}
