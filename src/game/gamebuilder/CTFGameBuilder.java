package game.gamebuilder;

import itemplacer.ChargedIdentityDiscPlacer;
import itemplacer.FlagPlacer;
import itemplacer.ForceFieldGeneratorPlacer;
import itemplacer.IdentityDiscPlacer;
import itemplacer.TeleportPlacer;

public class CTFGameBuilder extends GameBuilder {

	
	public CTFGameBuilder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void constructPlayers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void constructGrid() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void placeItems() {
		ChargedIdentityDiscPlacer CIDPlacer = new ChargedIdentityDiscPlacer(grid, players);
		CIDPlacer.placeItems();
		
		ForceFieldGeneratorPlacer FFGPlacer = new ForceFieldGeneratorPlacer(grid, players);
		FFGPlacer.placeItems();
		
		IdentityDiscPlacer IDPlacer = new IdentityDiscPlacer(grid, players);
		IDPlacer.placeItems();
		
		TeleportPlacer TPPlacer = new TeleportPlacer(grid, players);
		TPPlacer.placeItems();
		
		FlagPlacer FPlacer = new FlagPlacer(grid,players);
		FPlacer.placeItems();
		
	}

}
