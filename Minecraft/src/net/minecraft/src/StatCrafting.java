package net.minecraft.src;

public class StatCrafting extends StatBase
{
	private final int itemID;
	
	public StatCrafting(int p_i3414_1_, String p_i3414_2_, int p_i3414_3_)
	{
		super(p_i3414_1_, p_i3414_2_);
		itemID = p_i3414_3_;
	}
	
	public int getItemID()
	{
		return itemID;
	}
}
