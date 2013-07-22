package net.minecraft.src;

import java.util.List;

import net.minecraft.client.Minecraft;

public class CreativeCrafting implements ICrafting
{
	private final Minecraft mc;
	
	public CreativeCrafting(Minecraft p_i5008_1_)
	{
		mc = p_i5008_1_;
	}
	
	@Override public void sendContainerAndContentsToPlayer(Container p_71110_1_, List p_71110_2_)
	{
	}
	
	@Override public void sendProgressBarUpdate(Container p_71112_1_, int p_71112_2_, int p_71112_3_)
	{
	}
	
	@Override public void sendSlotContents(Container p_71111_1_, int p_71111_2_, ItemStack p_71111_3_)
	{
		mc.playerController.sendSlotPacket(p_71111_3_, p_71111_2_);
	}
}
