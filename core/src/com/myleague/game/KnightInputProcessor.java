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
		if (player.attackTimer < 0) {
			int move = player.velocity;
			if (keycode == Input.Keys.A) {
				move = -1;
			} else if (keycode == Input.Keys.D) {
				move = 1;
			}
			player.setVelocity(move);
		}
		
		
		if(keycode == 59) {
			player.setSprintValue(8);
		}
		if(keycode == Input.Keys.UP) {
			player.takeDamage(-500);
		}
		else if(keycode ==Input.Keys.DOWN) {
			player.takeDamage(500);
		}

		else if(keycode ==Input.Keys.NUM_1) {
			player.setAge(98f);
		}
		if(keycode == Input.Keys.LEFT) {
			player.toggleMoveLeft();
		}
		else if(keycode ==Input.Keys.RIGHT) {
			player.toggleMoveRight();
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		if (keycode == Input.Keys.A || keycode == Input.Keys.D) {
			player.setVelocity(0);
		} if(keycode == 59) {
			player.setSprintValue(1);
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
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
