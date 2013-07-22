package net.minecraft.src;

public class ItemBow extends Item
{
	public static final String[] bowPullIconNameArray = new String[] { "bow_pull_0", "bow_pull_1", "bow_pull_2" };
	private Icon[] iconArray;
	
	public ItemBow(int p_i3623_1_)
	{
		super(p_i3623_1_);
		maxStackSize = 1;
		setMaxDamage(384);
		setCreativeTab(CreativeTabs.tabCombat);
	}
	
	@Override public int getItemEnchantability()
	{
		return 1;
	}
	
	public Icon getItemIconForUseDuration(int par1)
	{
		return iconArray[par1];
	}
	
	@Override public EnumAction getItemUseAction(ItemStack p_77661_1_)
	{
		return EnumAction.bow;
	}
	
	@Override public int getMaxItemUseDuration(ItemStack p_77626_1_)
	{
		return 72000;
	}
	
	@Override public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
	{
		return p_77654_1_;
	}
	
	@Override public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		if(p_77659_3_.capabilities.isCreativeMode || p_77659_3_.inventory.hasItem(Item.arrow.itemID))
		{
			p_77659_3_.setItemInUse(p_77659_1_, getMaxItemUseDuration(p_77659_1_));
		}
		return p_77659_1_;
	}
	
	@Override public void onPlayerStoppedUsing(ItemStack p_77615_1_, World p_77615_2_, EntityPlayer p_77615_3_, int p_77615_4_)
	{
		boolean var5 = p_77615_3_.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, p_77615_1_) > 0;
		if(var5 || p_77615_3_.inventory.hasItem(Item.arrow.itemID))
		{
			int var6 = getMaxItemUseDuration(p_77615_1_) - p_77615_4_;
			float var7 = var6 / 20.0F;
			var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;
			if(var7 < 0.1D) return;
			if(var7 > 1.0F)
			{
				var7 = 1.0F;
			}
			EntityArrow var8 = new EntityArrow(p_77615_2_, p_77615_3_, var7 * 2.0F);
			if(var7 == 1.0F)
			{
				var8.setIsCritical(true);
			}
			int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, p_77615_1_);
			if(var9 > 0)
			{
				var8.setDamage(var8.getDamage() + var9 * 0.5D + 0.5D);
			}
			int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, p_77615_1_);
			if(var10 > 0)
			{
				var8.setKnockbackStrength(var10);
			}
			if(EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, p_77615_1_) > 0)
			{
				var8.setFire(100);
			}
			p_77615_1_.damageItem(1, p_77615_3_);
			p_77615_2_.playSoundAtEntity(p_77615_3_, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);
			if(var5)
			{
				var8.canBePickedUp = 2;
			} else
			{
				p_77615_3_.inventory.consumeInventoryItem(Item.arrow.itemID);
			}
			if(!p_77615_2_.isRemote)
			{
				p_77615_2_.spawnEntityInWorld(var8);
			}
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		super.registerIcons(par1IconRegister);
		iconArray = new Icon[bowPullIconNameArray.length];
		for(int var2 = 0; var2 < iconArray.length; ++var2)
		{
			iconArray[var2] = par1IconRegister.registerIcon(bowPullIconNameArray[var2]);
		}
	}
}
