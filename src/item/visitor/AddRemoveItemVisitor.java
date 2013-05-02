package item.visitor;

import item.ChargedIdentityDisc;
import item.ForceFieldGenerator;
import item.IdentityDisc;
import item.LightGrenade;

import item.Teleport;

/**
 * Visitor implementation for adding or removing specific items from an inventory.
 * 
 * @author Dieter, Stefan
 */
public interface AddRemoveItemVisitor {
	
	 public void addChargedDisc(ChargedIdentityDisc chargedDisc) throws IllegalStateException;
	 public void removeChargedDisc(ChargedIdentityDisc chargedDisc) throws IllegalStateException;
	 
	 public void addIdentityDisc(IdentityDisc identityDisc) throws IllegalStateException;
	 public void removeIdentityDisc(IdentityDisc identityDisc) throws IllegalStateException;
	 
	 public void addLightGrenade(LightGrenade lightGrenade) throws IllegalStateException;
	 public void removeLightGrenade(LightGrenade lightGrenade) throws IllegalStateException;
	 
	 public void addTeleport(Teleport teleport) throws IllegalStateException;
	 public void removeTeleport(Teleport teleport) throws IllegalStateException;

    public void addForceFieldGenerator( ForceFieldGenerator forceFieldGenerator) throws IllegalStateException;
    public void removeForceFieldGenerator(ForceFieldGenerator forceFieldGenerator) throws IllegalStateException;
}
