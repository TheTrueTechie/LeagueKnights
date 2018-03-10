package com.myleague.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Menu {
	Stage stage;
	TextButton test;
	TextButtonStyle buttonStyle;
	BitmapFont font;
	Skin skin;
	TextureAtlas buttonAtlas;
	Animation<TextureRegion> buttonAnim;
	
	public void create() {
		stage = new Stage();
		font = new BitmapFont();
		skin = new Skin();
		Gdx.input.setInputProcessor(stage);
		buttonAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/npc.atlas"));
		buttonAnim = new Animation<TextureRegion>(1f, buttonAtlas.getRegions());
		
		skin.addRegions(buttonAtlas);
		
		buttonStyle = new TextButtonStyle();
		buttonStyle.font = font;
		buttonStyle.up = new TextureRegionDrawable(buttonAnim.getKeyFrame(2f, false));
		buttonStyle.down = new TextureRegionDrawable(buttonAnim.getKeyFrame(0f, false));
		buttonStyle.checked = new TextureRegionDrawable(buttonAnim.getKeyFrame(1f, false));
		//buttonStyle.over = skin.newDrawable("background", Color.BLUE);
        test = new TextButton("Test Button", buttonStyle);
        test.setBounds(0, 0, 200, 200);
        //ImageButton btn = new ImageButton();
        stage.addActor(test);
	}
	
	public void render(SpriteBatch batch) {
		stage.act();
		stage.draw();
		//test.draw(batch, 1);
		//batch.draw(buttonAnim.getKeyFrame(0, false), 0, 0, 16, 16);
	}
}
