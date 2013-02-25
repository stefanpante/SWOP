package gui.model;

import items.Inventory;
import items.Item;
import items.LightGrenade;

import java.util.Observable;

public class GuiModel extends Observable {
	
	private Inventory inventory;
	
	public GuiModel(){
		this.inventory = new Inventory();
	}
	
	
	public GridConfiguration getGridConfiguration(){
		return new GridConfiguration();
	}
	
	public void updateInventory(){
		// TODO: Fetch da datah
		inventory.addItem(new LightGrenade());
		setChanged();
		notifyObservers();
		System.out.println("Notify");
	}
	
	public Inventory getInventory(){
		return this.inventory;
	}
}


