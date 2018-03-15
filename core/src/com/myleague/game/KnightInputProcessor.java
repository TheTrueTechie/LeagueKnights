package com.myleague.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class KnightInputProcessor implements InputProcessor {
	Player player;
	
	public KnightInputProcessor(Player p) {
		super();
		this.player = p;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
				return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		if (keycode == Input.Keys.A || keycode == Input.Keys.D) {
			player.idleTimer = 0;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		if (player.attackTimer < 0) {
			int move = 0;
			if (character == 'a') {
				if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
					player.anim = "walk";
					move = player.walkSpeed * -8;
				} else {
					player.anim = "run";
					move = -player.walkSpeed;
				}
			} else if (character == 'd') {
				if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
					player.anim = "walk";
					move = player.walkSpeed * 8;
				} else {
					player.anim = "run";
					move = player.walkSpeed;
				}
			} 
			player.setVelocity(move);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			player.takeDamage(-5);
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			player.takeDamage(5);
		}
		player.setIdleTimer(15);

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.LEFT) {
			player.attack();
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
