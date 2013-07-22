package net.minecraft.src;

public class ItemShears extends Item
{
	public ItemShears(int p_i3683_1_)
	{
		super(p_i3683_1_);
		setMaxStackSize(1);
		setMaxDamage(238);
		setCreativeTab(CreativeTabs.tabTools);
	}
	
	@Override public boolean canHarvestBlock(Block p_77641_1_)
	{
		return p_77641_1_.blockID == Block.web.blockID || p_77641_1_.blockID == Block.redstoneWire.blockID || p_77641_1_.blockID == Block.tripWire.blockID;
	}
	
	@Override public float getStrVsBlock(ItemStack p_77638_1_, Block p_77638_2_)
	{
		return p_77638_2_.blockID != Block.web.blockID && p_77638_2_.blockID != Block.leaves.blockID ? p_77638_2_.blockID == Block.cloth.blockID ? 5.0F : super.getStrVsBlock(p_77638_1_, p_77638_2_) : 15.0F;
	}
	
	@Override public boolean onBlockDestroyed(ItemStack p_77660_1_, World p_77660_2_, int p_77660_3_, int p_77660_4_, int p_77660_5_, int p_77660_6_, EntityLiving p_77660_7_)
	{
		if(p_77660_3_ != Block.leaves.blockID && p_77660_3_ != Block.web.blockID && p_77660_3_ != Block.tallGrass.blockID && p_77660_3_ != Block.vine.blockID && p_77660_3_ != Block.tripWire.blockID) return super.onBlockDestroyed(p_77660_1_, p_77660_2_, p_77660_3_, p_77660_4_, p_77660_5_, p_77660_6_, p_77660_7_);
		else
		{
			p_77660_1_.damageItem(1, p_77660_7_);
			return true;
		}
	}
}
