package net.minecraft.src;

import java.util.List;

public interface ICrafting
{
	void sendContainerAndContentsToPlayer(Container var1, List var2);
	
	void sendProgressBarUpdate(Container var1, int var2, int var3);
	
	void sendSlotContents(Container var1, int var2, ItemStack var3);
}
