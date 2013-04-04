package item;

import item.launchable.ChargedDisc;
import item.launchable.IdentityDisc;

public interface AddRemoveItemVisitor {
	
	 public void addItem(Item it);
	 public void removeItem(Item it);
	 
	 public void addChargedDisc(ChargedDisc chargedDisc);
	 public void removeChargedDisc(ChargedDisc chargedDisc);
	 
	 public void addIdentityDisc(IdentityDisc identityDisc);
	 public void removeIdentityDisc(IdentityDisc identityDisc);
	 
	 public void addLightGrenade(LightGrenade lightGrenade);
	 public void removeLightGrenade(LightGrenade lightGrenade);
	 
	 public void addTeleport(Teleport teleport);
	 public void removeTeleport(Teleport teleport);
	 
}
