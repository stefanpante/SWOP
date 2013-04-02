package controller;

import game.Game;

import item.Item;

import java.beans.PropertyChangeListener;

import square.Direction;

public class ThrowLaunchableHandler extends Handler {

	public ThrowLaunchableHandler() {
		// TODO Auto-generated constructor stub
	}

	public ThrowLaunchableHandler(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	public ThrowLaunchableHandler(Game game, PropertyChangeListener listener) {
		super(game, listener);
		// TODO Auto-generated constructor stub
	}
	
	public void throwItem(Item item, Direction direction){
		
	}

}
