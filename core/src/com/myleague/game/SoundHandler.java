package com.myleague.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundHandler {
	private Music musicbox = Gdx.audio.newMusic(Gdx.files.internal("sounds/musicbox.mp3"));
	private static Music piano = Gdx.audio.newMusic(Gdx.files.internal("sounds/piano.mp3"));
	private static Music bossMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/BossBattle.wav"));
	
	private static Sound slash = Gdx.audio.newSound(Gdx.files.internal("sounds/slash.wav"));
	private static long slashID = 0;
	
	public static void init() {
		piano.setLooping(true);
		piano.play();
	}
	
	public static void playBossMusic() {
		piano.stop();
		bossMusic.setLooping(true);
		bossMusic.play();
	}
	
	public static void playSlash() {
		slash.stop();
		long id = slash.play();
		slash.setPitch(id,getRandomPitch());
		//slash.dispose();
	}
	
	public static float getRandomPitch() {
		float pitch = new Random().nextFloat()*1.5f+0.5f;
		return pitch;
	}
	
}
