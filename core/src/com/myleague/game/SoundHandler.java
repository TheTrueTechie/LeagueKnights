package com.myleague.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundHandler {
	private Music musicbox = Gdx.audio.newMusic(Gdx.files.internal("sounds/musicbox.mp3"));
	private static Music piano = Gdx.audio.newMusic(Gdx.files.internal("sounds/piano.mp3"));
	
	private static Sound slash = Gdx.audio.newSound(Gdx.files.internal("sounds/slash.wav"));
	
	public static void init() {
		piano.setLooping(true);
		piano.play();
	}
	
	public static void playSlash() {
		long id = slash.play();
		slash.setPitch(id,getRandomPitch());
		//slash.dispose();
	}
	
	public static float getRandomPitch() {
		return new Random().nextFloat();
	}
	
}
