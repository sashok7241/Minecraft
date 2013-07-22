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
	
	public ItemArmor(int p_i3619_1_, EnumArmorMaterial p_i3619_2_, int p_i3619_3_, int p_i3619_4_)
	{
		super(p_i3619_1_);
		material = p_i3619_2_;
		armorType = p_i3619_4_;
		renderIndex = p_i3619_3_;
		damageReduceAmount = p_i3619_2_.getDamageReductionAmount(p_i3619_4_);
		setMaxDamage(p_i3619_2_.getDurability(p_i3619_4_));
		maxStackSize = 1;
		setCreativeTab(CreativeTabs.tabCombat);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, field_96605_cw);
	}
	
	public void func_82813_b(ItemStack p_82813_1_, int p_82813_2_)
	{
		if(material != EnumArmorMaterial.CLOTH) throw new UnsupportedOperationException("Can\'t dye non-leather!");
		else
		{
			NBTTagCompound var3 = p_82813_1_.getTagCompound();
			if(var3 == null)
			{
				var3 = new NBTTagCompound();
				p_82813_1_.setTagCompound(var3);
			}
			NBTTagCompound var4 = var3.getCompoundTag("display");
			if(!var3.hasKey("display"))
			{
				var3.setCompoundTag("display", var4);
			}
			var4.setInteger("color", p_82813_2_);
		}
	}
	
	public EnumArmorMaterial getArmorMaterial()
	{
		return material;
	}
	
	public int getColor(ItemStack p_82814_1_)
	{
		if(material != EnumArmorMaterial.CLOTH) return -1;
		else
		{
			NBTTagCompound var2 = p_82814_1_.getTagCompound();
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
	
	@Override public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_)
	{
		return material.getArmorCraftingMaterial() == p_82789_2_.itemID ? true : super.getIsRepairable(p_82789_1_, p_82789_2_);
	}
	
	@Override public int getItemEnchantability()
	{
		return material.getEnchantability();
	}
	
	public boolean hasColor(ItemStack p_82816_1_)
	{
		return material != EnumArmorMaterial.CLOTH ? false : !p_82816_1_.hasTagCompound() ? false : !p_82816_1_.getTagCompound().hasKey("display") ? false : p_82816_1_.getTagCompound().getCompoundTag("display").hasKey("color");
	}
	
	@Override public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		int var4 = EntityLiving.getArmorPosition(p_77659_1_) - 1;
		ItemStack var5 = p_77659_3_.getCurrentArmor(var4);
		if(var5 == null)
		{
			p_77659_3_.setCurrentItemOrArmor(var4, p_77659_1_.copy());
			p_77659_1_.stackSize = 0;
		}
		return p_77659_1_;
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
	
	public void removeColor(ItemStack p_82815_1_)
	{
		if(material == EnumArmorMaterial.CLOTH)
		{
			NBTTagCompound var2 = p_82815_1_.getTagCompound();
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
