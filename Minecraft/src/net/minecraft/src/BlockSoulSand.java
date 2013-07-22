package net.minecraft.src;

public class BlockSoulSand extends Block
{
	public BlockSoulSand(int par1)
	{
		super(par1, Material.sand);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		float var5 = 0.125F;
		return AxisAlignedBB.getAABBPool().getAABB(par2, par3, par4, par2 + 1, par3 + 1 - var5, par4 + 1);
	}
	
	@Override public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		par5Entity.motionX *= 0.4D;
		par5Entity.motionZ *= 0.4D;
	}
}
