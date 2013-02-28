package gui;

import java.util.ArrayList;

import items.Inventory;
import items.Item;

import javax.swing.DefaultListModel;

@SuppressWarnings("hiding")
public class InventoryListModel<String> extends DefaultListModel<String>{
	
	Inventory inventory;

	public InventoryListModel() {
		super();
	}
	
	public void setInventory(Inventory inventory){
		this.inventory = inventory;
		updateModel();
	}
	
	private void updateModel(){
		this.clear();
		ArrayList<Item> items = inventory.getAllItems();
		for(int i = 0; i < items.size(); i++){
			Item item = items.get(i);
			java.lang.String desc = item.toString();
			this.add(i, desc );
		}
	}
	
	public Item getItemAtIndex(int index){
		return inventory.getItem(index);
	}

}
