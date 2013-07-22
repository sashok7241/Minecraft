package net.minecraft.src;

import java.util.List;

import net.minecraft.client.Minecraft;

public class CreativeCrafting implements ICrafting
{
	private final Minecraft mc;
	
	public CreativeCrafting(Minecraft par1Minecraft)
	{
		mc = par1Minecraft;
	}
	
	@Override public void sendContainerAndContentsToPlayer(Container par1Container, List par2List)
	{
	}
	
	@Override public void sendProgressBarUpdate(Container par1Container, int par2, int par3)
	{
	}
	
	@Override public void sendSlotContents(Container par1Container, int par2, ItemStack par3ItemStack)
	{
		mc.playerController.sendSlotPacket(par3ItemStack, par2);
	}
}
