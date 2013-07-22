package net.minecraft.src;

public class ItemBow extends Item
{
	public static final String[] bowPullIconNameArray = new String[] { "pulling_0", "pulling_1", "pulling_2" };
	private Icon[] iconArray;
	
	public ItemBow(int par1)
	{
		super(par1);
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
	
	@Override public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.bow;
	}
	
	@Override public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 72000;
	}
	
	@Override public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		return par1ItemStack;
	}
	
	@Override public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItem(Item.arrow.itemID))
		{
			par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
		}
		return par1ItemStack;
	}
	
	@Override public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
		boolean var5 = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;
		if(var5 || par3EntityPlayer.inventory.hasItem(Item.arrow.itemID))
		{
			int var6 = getMaxItemUseDuration(par1ItemStack) - par4;
			float var7 = var6 / 20.0F;
			var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;
			if(var7 < 0.1D) return;
			if(var7 > 1.0F)
			{
				var7 = 1.0F;
			}
			EntityArrow var8 = new EntityArrow(par2World, par3EntityPlayer, var7 * 2.0F);
			if(var7 == 1.0F)
			{
				var8.setIsCritical(true);
			}
			int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);
			if(var9 > 0)
			{
				var8.setDamage(var8.getDamage() + var9 * 0.5D + 0.5D);
			}
			int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);
			if(var10 > 0)
			{
				var8.setKnockbackStrength(var10);
			}
			if(EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0)
			{
				var8.setFire(100);
			}
			par1ItemStack.damageItem(1, par3EntityPlayer);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);
			if(var5)
			{
				var8.canBePickedUp = 2;
			} else
			{
				par3EntityPlayer.inventory.consumeInventoryItem(Item.arrow.itemID);
			}
			if(!par2World.isRemote)
			{
				par2World.spawnEntityInWorld(var8);
			}
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		itemIcon = par1IconRegister.registerIcon(func_111208_A() + "_standby");
		iconArray = new Icon[bowPullIconNameArray.length];
		for(int var2 = 0; var2 < iconArray.length; ++var2)
		{
			iconArray[var2] = par1IconRegister.registerIcon(func_111208_A() + "_" + bowPullIconNameArray[var2]);
		}
	}
}
