package net.minecraft.src;

public class BlockSoulSand extends Block
{
	public BlockSoulSand(int p_i9062_1_)
	{
		super(p_i9062_1_, Material.sand);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		float var5 = 0.125F;
		return AxisAlignedBB.getAABBPool().getAABB(p_71872_2_, p_71872_3_, p_71872_4_, p_71872_2_ + 1, p_71872_3_ + 1 - var5, p_71872_4_ + 1);
	}
	
	@Override public void onEntityCollidedWithBlock(World p_71869_1_, int p_71869_2_, int p_71869_3_, int p_71869_4_, Entity p_71869_5_)
	{
		p_71869_5_.motionX *= 0.4D;
		p_71869_5_.motionZ *= 0.4D;
	}
}
