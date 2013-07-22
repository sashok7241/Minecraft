package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ItemEnchantedBook extends Item
{
	public ItemEnchantedBook(int p_i8011_1_)
	{
		super(p_i8011_1_);
	}
	
	@Override public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		NBTTagList var5 = func_92110_g(par1ItemStack);
		if(var5 != null)
		{
			for(int var6 = 0; var6 < var5.tagCount(); ++var6)
			{
				short var7 = ((NBTTagCompound) var5.tagAt(var6)).getShort("id");
				short var8 = ((NBTTagCompound) var5.tagAt(var6)).getShort("lvl");
				if(Enchantment.enchantmentsList[var7] != null)
				{
					par3List.add(Enchantment.enchantmentsList[var7].getTranslatedName(var8));
				}
			}
		}
	}
	
	public ItemStack func_92109_a(Random p_92109_1_)
	{
		Enchantment var2 = Enchantment.enchantmentsBookList[p_92109_1_.nextInt(Enchantment.enchantmentsBookList.length)];
		ItemStack var3 = new ItemStack(itemID, 1, 0);
		int var4 = MathHelper.getRandomIntegerInRange(p_92109_1_, var2.getMinLevel(), var2.getMaxLevel());
		getEnchantedItemStack_do(var3, new EnchantmentData(var2, var4));
		return var3;
	}
	
	public NBTTagList func_92110_g(ItemStack p_92110_1_)
	{
		return p_92110_1_.stackTagCompound != null && p_92110_1_.stackTagCompound.hasKey("StoredEnchantments") ? (NBTTagList) p_92110_1_.stackTagCompound.getTag("StoredEnchantments") : new NBTTagList();
	}
	
	public WeightedRandomChestContent func_92112_a(Random p_92112_1_, int p_92112_2_, int p_92112_3_, int p_92112_4_)
	{
		Enchantment var5 = Enchantment.enchantmentsBookList[p_92112_1_.nextInt(Enchantment.enchantmentsBookList.length)];
		ItemStack var6 = new ItemStack(itemID, 1, 0);
		int var7 = MathHelper.getRandomIntegerInRange(p_92112_1_, var5.getMinLevel(), var5.getMaxLevel());
		getEnchantedItemStack_do(var6, new EnchantmentData(var5, var7));
		return new WeightedRandomChestContent(var6, p_92112_2_, p_92112_3_, p_92112_4_);
	}
	
	public void func_92113_a(Enchantment par1Enchantment, List par2List)
	{
		for(int var3 = par1Enchantment.getMinLevel(); var3 <= par1Enchantment.getMaxLevel(); ++var3)
		{
			par2List.add(getEnchantedItemStack(new EnchantmentData(par1Enchantment, var3)));
		}
	}
	
	public WeightedRandomChestContent func_92114_b(Random p_92114_1_)
	{
		return func_92112_a(p_92114_1_, 1, 1, 1);
	}
	
	public ItemStack getEnchantedItemStack(EnchantmentData p_92111_1_)
	{
		ItemStack var2 = new ItemStack(this);
		getEnchantedItemStack_do(var2, p_92111_1_);
		return var2;
	}
	
	public void getEnchantedItemStack_do(ItemStack p_92115_1_, EnchantmentData p_92115_2_)
	{
		NBTTagList var3 = func_92110_g(p_92115_1_);
		boolean var4 = true;
		for(int var5 = 0; var5 < var3.tagCount(); ++var5)
		{
			NBTTagCompound var6 = (NBTTagCompound) var3.tagAt(var5);
			if(var6.getShort("id") == p_92115_2_.enchantmentobj.effectId)
			{
				if(var6.getShort("lvl") < p_92115_2_.enchantmentLevel)
				{
					var6.setShort("lvl", (short) p_92115_2_.enchantmentLevel);
				}
				var4 = false;
				break;
			}
		}
		if(var4)
		{
			NBTTagCompound var7 = new NBTTagCompound();
			var7.setShort("id", (short) p_92115_2_.enchantmentobj.effectId);
			var7.setShort("lvl", (short) p_92115_2_.enchantmentLevel);
			var3.appendTag(var7);
		}
		if(!p_92115_1_.hasTagCompound())
		{
			p_92115_1_.setTagCompound(new NBTTagCompound());
		}
		p_92115_1_.getTagCompound().setTag("StoredEnchantments", var3);
	}
	
	@Override public EnumRarity getRarity(ItemStack par1ItemStack)
	{
		return func_92110_g(par1ItemStack).tagCount() > 0 ? EnumRarity.uncommon : super.getRarity(par1ItemStack);
	}
	
	@Override public boolean hasEffect(ItemStack par1ItemStack)
	{
		return true;
	}
	
	@Override public boolean isItemTool(ItemStack p_77616_1_)
	{
		return false;
	}
}
