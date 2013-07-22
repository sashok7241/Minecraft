package net.minecraft.src;

public class TileEntityDropper extends TileEntityDispenser
{
	@Override public String getInvName()
	{
		return isInvNameLocalized() ? customName : "container.dropper";
	}
}
