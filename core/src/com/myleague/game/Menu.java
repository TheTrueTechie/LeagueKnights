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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

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
		createSkin();
		createGenericButton();
	}
	
	public void createSkin() {
		skin.add("default",  font);
		Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("background",new Texture(pixmap));
		skin.add("startbutton", new Texture("ui/start.png"));
		skin.add("default", font);
		skin.add("default", createButtonStyle());
	}
	
	public TextButton.TextButtonStyle createButtonStyle() {
		  TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		  textButtonStyle.up = skin.newDrawable("startbutton", Color.GRAY);
		  textButtonStyle.down = skin.newDrawable("startbutton", Color.DARK_GRAY);
		  //textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
		  textButtonStyle.over = skin.newDrawable("startbutton", Color.LIGHT_GRAY);
		  textButtonStyle.font = skin.getFont("default");
		  return textButtonStyle;
	}
	
	/**
	 * Adds the file as a usable resource to the menu's skin object.
	 * @param String name
	 * @param String file
	 */
	public void addResourceToSkin(String name, String file) {
		skin.add(name, new Texture(file));
	}
	
	/**
	 * Creates a button with the given name, text, and position. To be used in conjunction with buttonIsPressed() 
	 * to make UI creation simpler and more modular.
	 * @param String name
	 * @param String text
	 * @param float x
	 * @param float y
	 */
	public void createButton(String name, String text, float x, float y) {
		TextButton btn = new TextButton(text, skin); // Use the initialized skin
		btn.setPosition(x, y);
		btn.setName(name);
	    stage.addActor(btn);
	}
	
	/**
	 * Functional (albiet generic) button creation
	 * 
	 */
	public void createGenericButton() {
		  genericButton = new TextButton("", skin); // Use the initialized skin
		  genericButton.setPosition(Gdx.graphics.getWidth()/2 - 165 , Gdx.graphics.getHeight()/2);
		  genericButton.setName("start");
	      stage.addActor(genericButton);
	}
	
	public void render(SpriteBatch batch) {
		stage.act();
		stage.draw();
		getButtonInput();
	}
	
	/**
	 * Searches the menu's stage for a button of the given name and returns true if it is pressed.
	 * @param String name
	 * @return true if button of given name is pressed
	 */
	public boolean buttonIsPressed(String name) {
		Array<Actor> actors = stage.getActors();
		TextButton btn;
		for (Actor a : actors) {
			if (a.getName().equals(name) && ((Button) a).isPressed()) {
				return true;
			}
		}
		return false;
	}
	
	public void getButtonInput() {
		if(genericButton.isPressed()) {
			game.startGame();
		}
	}
}
