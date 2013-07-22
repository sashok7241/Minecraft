package net.minecraft.src;

public class ItemMapBase extends Item
{
	protected ItemMapBase(int p_i3629_1_)
	{
		super(p_i3629_1_);
	}
	
	public Packet createMapDataPacket(ItemStack p_77871_1_, World p_77871_2_, EntityPlayer p_77871_3_)
	{
		return null;
	}
	
	@Override public boolean isMap()
	{
		return true;
	}
}
