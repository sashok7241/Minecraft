package net.minecraft.src;

public class ItemSword extends Item
{
	private int weaponDamage;
	private final EnumToolMaterial toolMaterial;
	
	public ItemSword(int p_i3695_1_, EnumToolMaterial p_i3695_2_)
	{
		super(p_i3695_1_);
		toolMaterial = p_i3695_2_;
		maxStackSize = 1;
		setMaxDamage(p_i3695_2_.getMaxUses());
		setCreativeTab(CreativeTabs.tabCombat);
		weaponDamage = 4 + p_i3695_2_.getDamageVsEntity();
	}
	
	@Override public boolean canHarvestBlock(Block p_77641_1_)
	{
		return p_77641_1_.blockID == Block.web.blockID;
	}
	
	public int func_82803_g()
	{
		return toolMaterial.getDamageVsEntity();
	}
	
	@Override public int getDamageVsEntity(Entity p_77649_1_)
	{
		return weaponDamage;
	}
	
	@Override public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_)
	{
		return toolMaterial.getToolCraftingMaterial() == p_82789_2_.itemID ? true : super.getIsRepairable(p_82789_1_, p_82789_2_);
	}
	
	@Override public int getItemEnchantability()
	{
		return toolMaterial.getEnchantability();
	}
	
	@Override public EnumAction getItemUseAction(ItemStack p_77661_1_)
	{
		return EnumAction.block;
	}
	
	@Override public int getMaxItemUseDuration(ItemStack p_77626_1_)
	{
		return 72000;
	}
	
	@Override public float getStrVsBlock(ItemStack p_77638_1_, Block p_77638_2_)
	{
		if(p_77638_2_.blockID == Block.web.blockID) return 15.0F;
		else
		{
			Material var3 = p_77638_2_.blockMaterial;
			return var3 != Material.plants && var3 != Material.vine && var3 != Material.coral && var3 != Material.leaves && var3 != Material.pumpkin ? 1.0F : 1.5F;
		}
	}
	
	public String getToolMaterialName()
	{
		return toolMaterial.toString();
	}
	
	@Override public boolean hitEntity(ItemStack p_77644_1_, EntityLiving p_77644_2_, EntityLiving p_77644_3_)
	{
		p_77644_1_.damageItem(1, p_77644_3_);
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
			p_77660_1_.damageItem(2, p_77660_7_);
		}
		return true;
	}
	
	@Override public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		p_77659_3_.setItemInUse(p_77659_1_, getMaxItemUseDuration(p_77659_1_));
		return p_77659_1_;
	}
}
