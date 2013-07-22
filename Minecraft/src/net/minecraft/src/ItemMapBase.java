package net.minecraft.src;

public class ItemMapBase extends Item
{
	protected ItemMapBase(int par1)
	{
		super(par1);
	}
	
	public Packet createMapDataPacket(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		return null;
	}
	
	@Override public boolean isMap()
	{
		return true;
	}
}
