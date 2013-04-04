package item.visitor;

import item.Item;
import item.LightGrenade;
import item.Teleport;
import item.launchable.ChargedDisc;
import item.launchable.IdentityDisc;

/**
 * Visitor implementation for adding or removing specific items from an inventory.
 * 
 * @author Dieter
 */
public interface AddRemoveItemVisitor {
	
	 public void addChargedDisc(ChargedDisc chargedDisc) throws IllegalStateException;
	 public void removeChargedDisc(ChargedDisc chargedDisc) throws IllegalStateException;
	 
	 public void addIdentityDisc(IdentityDisc identityDisc) throws IllegalStateException;
	 public void removeIdentityDisc(IdentityDisc identityDisc) throws IllegalStateException;
	 
	 public void addLightGrenade(LightGrenade lightGrenade) throws IllegalStateException;
	 public void removeLightGrenade(LightGrenade lightGrenade) throws IllegalStateException;
	 
	 public void addTeleport(Teleport teleport) throws IllegalStateException;
	 public void removeTeleport(Teleport teleport) throws IllegalStateException;
}
