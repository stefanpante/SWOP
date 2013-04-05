package item.visitor;

import item.Item;
import item.LightGrenade;
import item.Teleport;
import item.launchable.ChargedDisc;
import item.launchable.IdentityDisc;

/**
 * Visitor implementation for adding or removing specific items from an inventory.
 * 
 * @author Dieter, Vincent
 */
public interface AddRemoveItemVisitor {
	
	 public void addItem(ChargedDisc chargedDisc) throws IllegalStateException;
	 public void removeItem(ChargedDisc chargedDisc) throws IllegalStateException;
	 
	 public void addItem(IdentityDisc identityDisc) throws IllegalStateException;
	 public void removeItem(IdentityDisc identityDisc) throws IllegalStateException;
	 
	 public void addItem(LightGrenade lightGrenade) throws IllegalStateException;
	 public void removeItem(LightGrenade lightGrenade) throws IllegalStateException;
	 
	 public void addItem(Teleport teleport) throws IllegalStateException;
	 public void removeItem(Teleport teleport) throws IllegalStateException;
}
