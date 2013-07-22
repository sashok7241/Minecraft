package net.minecraft.src;

public class ItemTool extends Item
{
	private Block[] blocksEffectiveAgainst;
	protected float efficiencyOnProperMaterial = 4.0F;
	private int damageVsEntity;
	protected EnumToolMaterial toolMaterial;
	
	protected ItemTool(int p_i3643_1_, int p_i3643_2_, EnumToolMaterial p_i3643_3_, Block[] p_i3643_4_)
	{
		super(p_i3643_1_);
		toolMaterial = p_i3643_3_;
		blocksEffectiveAgainst = p_i3643_4_;
		maxStackSize = 1;
		setMaxDamage(p_i3643_3_.getMaxUses());
		efficiencyOnProperMaterial = p_i3643_3_.getEfficiencyOnProperMaterial();
		damageVsEntity = p_i3643_2_ + p_i3643_3_.getDamageVsEntity();
		setCreativeTab(CreativeTabs.tabTools);
	}
	
	@Override public int getDamageVsEntity(Entity p_77649_1_)
	{
		return damageVsEntity;
	}
	
	@Override public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_)
	{
		return toolMaterial.getToolCraftingMaterial() == p_82789_2_.itemID ? true : super.getIsRepairable(p_82789_1_, p_82789_2_);
	}
	
	@Override public int getItemEnchantability()
	{
		return toolMaterial.getEnchantability();
	}
	
	@Override public float getStrVsBlock(ItemStack p_77638_1_, Block p_77638_2_)
	{
		for(Block element : blocksEffectiveAgainst)
		{
			if(element == p_77638_2_) return efficiencyOnProperMaterial;
		}
		return 1.0F;
	}
	
	public String getToolMaterialName()
	{
		return toolMaterial.toString();
	}
	
	@Override public boolean hitEntity(ItemStack p_77644_1_, EntityLiving p_77644_2_, EntityLiving p_77644_3_)
	{
		p_77644_1_.damageItem(2, p_77644_3_);
		return true;
	}
	
	@Override public boolean isFull3D()
	{
		return true;
	}
	
	@Override public boolean onBlockDestroyed(ItemStack p_77660_1_, World p_77660_2_, int p_77660_3_, int p_77660_4_, int p_77660_5_, int p_77660_6_, EntityLiving p_77660_7_)
	{
		if(Block.blocksList[p_77660_3_].getBlockHardness(p_77660_2_, p_77660_4_, p_77660_5_, p_77660_6_) != 0.0D)
		{
			p_77660_1_.damageItem(1, p_77660_7_);
		}
		return true;
	}
}
