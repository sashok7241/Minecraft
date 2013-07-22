package net.minecraft.src;

public class ItemArmor extends Item
{
	private static final int[] maxDamageArray = new int[] { 11, 16, 15, 13 };
	private static final String[] field_94606_cu = new String[] { "helmetCloth_overlay", "chestplateCloth_overlay", "leggingsCloth_overlay", "bootsCloth_overlay" };
	public static final String[] field_94603_a = new String[] { "slot_empty_helmet", "slot_empty_chestplate", "slot_empty_leggings", "slot_empty_boots" };
	private static final IBehaviorDispenseItem field_96605_cw = new BehaviorDispenseArmor();
	public final int armorType;
	public final int damageReduceAmount;
	public final int renderIndex;
	private final EnumArmorMaterial material;
	private Icon field_94605_cw;
	private Icon field_94604_cx;
	
	public ItemArmor(int par1, EnumArmorMaterial par2EnumArmorMaterial, int par3, int par4)
	{
		super(par1);
		material = par2EnumArmorMaterial;
		armorType = par4;
		renderIndex = par3;
		damageReduceAmount = par2EnumArmorMaterial.getDamageReductionAmount(par4);
		setMaxDamage(par2EnumArmorMaterial.getDurability(par4));
		maxStackSize = 1;
		setCreativeTab(CreativeTabs.tabCombat);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, field_96605_cw);
	}
	
	public void func_82813_b(ItemStack par1ItemStack, int par2)
	{
		if(material != EnumArmorMaterial.CLOTH) throw new UnsupportedOperationException("Can\'t dye non-leather!");
		else
		{
			NBTTagCompound var3 = par1ItemStack.getTagCompound();
			if(var3 == null)
			{
				var3 = new NBTTagCompound();
				par1ItemStack.setTagCompound(var3);
			}
			NBTTagCompound var4 = var3.getCompoundTag("display");
			if(!var3.hasKey("display"))
			{
				var3.setCompoundTag("display", var4);
			}
			var4.setInteger("color", par2);
		}
	}
	
	public EnumArmorMaterial getArmorMaterial()
	{
		return material;
	}
	
	public int getColor(ItemStack par1ItemStack)
	{
		if(material != EnumArmorMaterial.CLOTH) return -1;
		else
		{
			NBTTagCompound var2 = par1ItemStack.getTagCompound();
			if(var2 == null) return 10511680;
			else
			{
				NBTTagCompound var3 = var2.getCompoundTag("display");
				return var3 == null ? 10511680 : var3.hasKey("color") ? var3.getInteger("color") : 10511680;
			}
		}
	}
	
	@Override public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		if(par2 > 0) return 16777215;
		else
		{
			int var3 = getColor(par1ItemStack);
			if(var3 < 0)
			{
				var3 = 16777215;
			}
			return var3;
		}
	}
	
	@Override public Icon getIconFromDamageForRenderPass(int par1, int par2)
	{
		return par2 == 1 ? field_94605_cw : super.getIconFromDamageForRenderPass(par1, par2);
	}
	
	@Override public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return material.getArmorCraftingMaterial() == par2ItemStack.itemID ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}
	
	@Override public int getItemEnchantability()
	{
		return material.getEnchantability();
	}
	
	public boolean hasColor(ItemStack par1ItemStack)
	{
		return material != EnumArmorMaterial.CLOTH ? false : !par1ItemStack.hasTagCompound() ? false : !par1ItemStack.getTagCompound().hasKey("display") ? false : par1ItemStack.getTagCompound().getCompoundTag("display").hasKey("color");
	}
	
	@Override public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		int var4 = EntityLiving.getArmorPosition(par1ItemStack) - 1;
		ItemStack var5 = par3EntityPlayer.getCurrentArmor(var4);
		if(var5 == null)
		{
			par3EntityPlayer.setCurrentItemOrArmor(var4, par1ItemStack.copy());
			par1ItemStack.stackSize = 0;
		}
		return par1ItemStack;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		super.registerIcons(par1IconRegister);
		if(material == EnumArmorMaterial.CLOTH)
		{
			field_94605_cw = par1IconRegister.registerIcon(field_94606_cu[armorType]);
		}
		field_94604_cx = par1IconRegister.registerIcon(field_94603_a[armorType]);
	}
	
	public void removeColor(ItemStack par1ItemStack)
	{
		if(material == EnumArmorMaterial.CLOTH)
		{
			NBTTagCompound var2 = par1ItemStack.getTagCompound();
			if(var2 != null)
			{
				NBTTagCompound var3 = var2.getCompoundTag("display");
				if(var3.hasKey("color"))
				{
					var3.removeTag("color");
				}
			}
		}
	}
	
	@Override public boolean requiresMultipleRenderPasses()
	{
		return material == EnumArmorMaterial.CLOTH;
	}
	
	public static Icon func_94602_b(int par0)
	{
		switch(par0)
		{
			case 0:
				return Item.helmetDiamond.field_94604_cx;
			case 1:
				return Item.plateDiamond.field_94604_cx;
			case 2:
				return Item.legsDiamond.field_94604_cx;
			case 3:
				return Item.bootsDiamond.field_94604_cx;
			default:
				return null;
		}
	}
	
	static int[] getMaxDamageArray()
	{
		return maxDamageArray;
	}
}
