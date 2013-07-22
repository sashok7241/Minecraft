package net.minecraft.src;

public interface IEntitySelector
{
	IEntitySelector selectAnything = new EntitySelectorAlive();
	IEntitySelector selectInventories = new EntitySelectorInventory();
	
	boolean isEntityApplicable(Entity var1);
}
