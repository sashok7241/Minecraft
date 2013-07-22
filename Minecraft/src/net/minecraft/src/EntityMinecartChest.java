package net.minecraft.src;

public class EntityMinecartChest extends EntityMinecartContainer
{
	public EntityMinecartChest(World p_i9001_1_)
	{
		super(p_i9001_1_);
	}
	
	public EntityMinecartChest(World p_i9002_1_, double p_i9002_2_, double p_i9002_4_, double p_i9002_6_)
	{
		super(p_i9002_1_, p_i9002_2_, p_i9002_4_, p_i9002_6_);
	}
	
	@Override public Block getDefaultDisplayTile()
	{
		return Block.chest;
	}
	
	@Override public int getDefaultDisplayTileOffset()
	{
		return 8;
	}
	
	@Override public int getMinecartType()
	{
		return 1;
	}
	
	@Override public int getSizeInventory()
	{
		return 27;
	}
	
	@Override public void killMinecart(DamageSource p_94095_1_)
	{
		super.killMinecart(p_94095_1_);
		dropItemWithOffset(Block.chest.blockID, 1, 0.0F);
	}
}
