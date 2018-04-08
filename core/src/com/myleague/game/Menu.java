package com.myleague.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
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
	MyLeague game;
	Stage stage;
	TextButton textButton;
	TextButton genericButton;
	TextButtonStyle buttonStyle;
	BitmapFont font;
	Skin skin;
	TextureAtlas buttonAtlas;
	Animation<TextureRegion> buttonAnim;
	
	public void create(MyLeague league) {
		game = league;
		stage = new Stage();
		font = new BitmapFont();
		skin = new Skin();
		Gdx.input.setInputProcessor(stage);
		createGenericButton();
		
	}
	
	/**
	 * Functional (albiet generic) button creation
	 * 
	 */
	public void createGenericButton() {
		skin.add("default",  font);
		
		 //Create a texture
		  Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
		  pixmap.setColor(Color.WHITE);
		  pixmap.fill();
		  skin.add("background",new Texture(pixmap));
		  skin.add("startbutton", new Texture("ui/start.png"));
		  skin.add("default", font);
		  
		  //Create a button style
		  TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		  textButtonStyle.up = skin.newDrawable("startbutton", Color.GRAY);
		  textButtonStyle.down = skin.newDrawable("startbutton", Color.DARK_GRAY);
		  //textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
		  textButtonStyle.over = skin.newDrawable("startbutton", Color.LIGHT_GRAY);
		  textButtonStyle.font = skin.getFont("default");
		  skin.add("default", textButtonStyle);
		  
		  genericButton = new TextButton("", skin); // Use the initialized skin
		  genericButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/2);
	      stage.addActor(genericButton);
	}
	
	/**
	 * Non-functional button creation
	 */
	public void createTextButton() {
		buttonAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/npc.atlas"));
		buttonAnim = new Animation<TextureRegion>(1f, buttonAtlas.getRegions());
		
		skin.addRegions(buttonAtlas);
		
		buttonStyle = new TextButtonStyle();
		buttonStyle.font = font;
		buttonStyle.up = new TextureRegionDrawable(buttonAnim.getKeyFrame(2f, false));
		buttonStyle.down = new TextureRegionDrawable(buttonAnim.getKeyFrame(0f, false));
		buttonStyle.checked = new TextureRegionDrawable(buttonAnim.getKeyFrame(1f, false));
		//buttonStyle.over = skin.newDrawable("background", Color.BLUE);
        textButton = new TextButton("Test Button", buttonStyle);
        textButton.setBounds(0, 0, 200, 200);
        //ImageButton btn = new ImageButton();
        stage.addActor(textButton);
	}
	
	public void render(SpriteBatch batch) {
		stage.act();
		stage.draw();
		getButtonInput();
	}
	
	public void getButtonInput() {
		if(genericButton.isPressed()) {
			game.startGame();
		}
	}
}
