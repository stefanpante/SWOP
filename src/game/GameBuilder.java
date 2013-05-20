package game;

public abstract class GameBuilder {

	public GameBuilder() {	}
	
	public abstract void constructPlayers();
	public abstract void constructGrid();
	public abstract void placeItems();

}
