package net.minecraft.src;

public class EntityMinecartChest extends EntityMinecartContainer
{
	public EntityMinecartChest(World par1World)
	{
		super(par1World);
	}
	
	public EntityMinecartChest(World par1, double par2, double par4, double par6)
	{
		super(par1, par2, par4, par6);
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
	
	@Override public void killMinecart(DamageSource par1DamageSource)
	{
		super.killMinecart(par1DamageSource);
		dropItemWithOffset(Block.chest.blockID, 1, 0.0F);
	}
}
