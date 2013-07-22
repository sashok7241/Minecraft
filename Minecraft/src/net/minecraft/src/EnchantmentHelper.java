package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EnchantmentHelper
{
	private static final Random enchantmentRand = new Random();
	private static final EnchantmentModifierDamage enchantmentModifierDamage = new EnchantmentModifierDamage((Empty3) null);
	private static final EnchantmentModifierLiving enchantmentModifierLiving = new EnchantmentModifierLiving((Empty3) null);
	
	public static ItemStack addRandomEnchantment(Random p_77504_0_, ItemStack p_77504_1_, int p_77504_2_)
	{
		List var3 = buildEnchantmentList(p_77504_0_, p_77504_1_, p_77504_2_);
		boolean var4 = p_77504_1_.itemID == Item.book.itemID;
		if(var4)
		{
			p_77504_1_.itemID = Item.enchantedBook.itemID;
		}
		if(var3 != null)
		{
			Iterator var5 = var3.iterator();
			while(var5.hasNext())
			{
				EnchantmentData var6 = (EnchantmentData) var5.next();
				if(var4)
				{
					Item.enchantedBook.getEnchantedItemStack_do(p_77504_1_, var6);
				} else
				{
					p_77504_1_.addEnchantment(var6.enchantmentobj, var6.enchantmentLevel);
				}
			}
		}
		return p_77504_1_;
	}
	
	private static void applyEnchantmentModifier(IEnchantmentModifier p_77518_0_, ItemStack p_77518_1_)
	{
		if(p_77518_1_ != null)
		{
			NBTTagList var2 = p_77518_1_.getEnchantmentTagList();
			if(var2 != null)
			{
				for(int var3 = 0; var3 < var2.tagCount(); ++var3)
				{
					short var4 = ((NBTTagCompound) var2.tagAt(var3)).getShort("id");
					short var5 = ((NBTTagCompound) var2.tagAt(var3)).getShort("lvl");
					if(Enchantment.enchantmentsList[var4] != null)
					{
						p_77518_0_.calculateModifier(Enchantment.enchantmentsList[var4], var5);
					}
				}
			}
		}
	}
	
	private static void applyEnchantmentModifierArray(IEnchantmentModifier p_77516_0_, ItemStack[] p_77516_1_)
	{
		ItemStack[] var2 = p_77516_1_;
		int var3 = p_77516_1_.length;
		for(int var4 = 0; var4 < var3; ++var4)
		{
			ItemStack var5 = var2[var4];
			applyEnchantmentModifier(p_77516_0_, var5);
		}
	}
	
	public static List buildEnchantmentList(Random p_77513_0_, ItemStack p_77513_1_, int p_77513_2_)
	{
		Item var3 = p_77513_1_.getItem();
		int var4 = var3.getItemEnchantability();
		if(var4 <= 0) return null;
		else
		{
			var4 /= 2;
			var4 = 1 + p_77513_0_.nextInt((var4 >> 1) + 1) + p_77513_0_.nextInt((var4 >> 1) + 1);
			int var5 = var4 + p_77513_2_;
			float var6 = (p_77513_0_.nextFloat() + p_77513_0_.nextFloat() - 1.0F) * 0.15F;
			int var7 = (int) (var5 * (1.0F + var6) + 0.5F);
			if(var7 < 1)
			{
				var7 = 1;
			}
			ArrayList var8 = null;
			Map var9 = mapEnchantmentData(var7, p_77513_1_);
			if(var9 != null && !var9.isEmpty())
			{
				EnchantmentData var10 = (EnchantmentData) WeightedRandom.getRandomItem(p_77513_0_, var9.values());
				if(var10 != null)
				{
					var8 = new ArrayList();
					var8.add(var10);
					for(int var11 = var7; p_77513_0_.nextInt(50) <= var11; var11 >>= 1)
					{
						Iterator var12 = var9.keySet().iterator();
						while(var12.hasNext())
						{
							Integer var13 = (Integer) var12.next();
							boolean var14 = true;
							Iterator var15 = var8.iterator();
							while(true)
							{
								if(var15.hasNext())
								{
									EnchantmentData var16 = (EnchantmentData) var15.next();
									if(var16.enchantmentobj.canApplyTogether(Enchantment.enchantmentsList[var13.intValue()]))
									{
										continue;
									}
									var14 = false;
								}
								if(!var14)
								{
									var12.remove();
								}
								break;
							}
						}
						if(!var9.isEmpty())
						{
							EnchantmentData var17 = (EnchantmentData) WeightedRandom.getRandomItem(p_77513_0_, var9.values());
							var8.add(var17);
						}
					}
				}
			}
			return var8;
		}
	}
	
	public static int calcItemStackEnchantability(Random p_77514_0_, int p_77514_1_, int p_77514_2_, ItemStack p_77514_3_)
	{
		Item var4 = p_77514_3_.getItem();
		int var5 = var4.getItemEnchantability();
		if(var5 <= 0) return 0;
		else
		{
			if(p_77514_2_ > 15)
			{
				p_77514_2_ = 15;
			}
			int var6 = p_77514_0_.nextInt(8) + 1 + (p_77514_2_ >> 1) + p_77514_0_.nextInt(p_77514_2_ + 1);
			return p_77514_1_ == 0 ? Math.max(var6 / 3, 1) : p_77514_1_ == 1 ? var6 * 2 / 3 + 1 : Math.max(var6, p_77514_2_ * 2);
		}
	}
	
	public static int func_92098_i(EntityLiving p_92098_0_)
	{
		return getMaxEnchantmentLevel(Enchantment.thorns.effectId, p_92098_0_.getLastActiveItems());
	}
	
	public static ItemStack func_92099_a(Enchantment p_92099_0_, EntityLiving p_92099_1_)
	{
		ItemStack[] var2 = p_92099_1_.getLastActiveItems();
		int var3 = var2.length;
		for(int var4 = 0; var4 < var3; ++var4)
		{
			ItemStack var5 = var2[var4];
			if(var5 != null && getEnchantmentLevel(p_92099_0_.effectId, var5) > 0) return var5;
		}
		return null;
	}
	
	public static boolean getAquaAffinityModifier(EntityLiving p_77510_0_)
	{
		return getMaxEnchantmentLevel(Enchantment.aquaAffinity.effectId, p_77510_0_.getLastActiveItems()) > 0;
	}
	
	public static int getEfficiencyModifier(EntityLiving p_77509_0_)
	{
		return getEnchantmentLevel(Enchantment.efficiency.effectId, p_77509_0_.getHeldItem());
	}
	
	public static int getEnchantmentLevel(int p_77506_0_, ItemStack p_77506_1_)
	{
		if(p_77506_1_ == null) return 0;
		else
		{
			NBTTagList var2 = p_77506_1_.getEnchantmentTagList();
			if(var2 == null) return 0;
			else
			{
				for(int var3 = 0; var3 < var2.tagCount(); ++var3)
				{
					short var4 = ((NBTTagCompound) var2.tagAt(var3)).getShort("id");
					short var5 = ((NBTTagCompound) var2.tagAt(var3)).getShort("lvl");
					if(var4 == p_77506_0_) return var5;
				}
				return 0;
			}
		}
	}
	
	public static int getEnchantmentModifierDamage(ItemStack[] p_77508_0_, DamageSource p_77508_1_)
	{
		enchantmentModifierDamage.damageModifier = 0;
		enchantmentModifierDamage.source = p_77508_1_;
		applyEnchantmentModifierArray(enchantmentModifierDamage, p_77508_0_);
		if(enchantmentModifierDamage.damageModifier > 25)
		{
			enchantmentModifierDamage.damageModifier = 25;
		}
		return (enchantmentModifierDamage.damageModifier + 1 >> 1) + enchantmentRand.nextInt((enchantmentModifierDamage.damageModifier >> 1) + 1);
	}
	
	public static int getEnchantmentModifierLiving(EntityLiving p_77512_0_, EntityLiving p_77512_1_)
	{
		enchantmentModifierLiving.livingModifier = 0;
		enchantmentModifierLiving.entityLiving = p_77512_1_;
		applyEnchantmentModifier(enchantmentModifierLiving, p_77512_0_.getHeldItem());
		return enchantmentModifierLiving.livingModifier > 0 ? 1 + enchantmentRand.nextInt(enchantmentModifierLiving.livingModifier) : 0;
	}
	
	public static Map getEnchantments(ItemStack p_82781_0_)
	{
		LinkedHashMap var1 = new LinkedHashMap();
		NBTTagList var2 = p_82781_0_.itemID == Item.enchantedBook.itemID ? Item.enchantedBook.func_92110_g(p_82781_0_) : p_82781_0_.getEnchantmentTagList();
		if(var2 != null)
		{
			for(int var3 = 0; var3 < var2.tagCount(); ++var3)
			{
				short var4 = ((NBTTagCompound) var2.tagAt(var3)).getShort("id");
				short var5 = ((NBTTagCompound) var2.tagAt(var3)).getShort("lvl");
				var1.put(Integer.valueOf(var4), Integer.valueOf(var5));
			}
		}
		return var1;
	}
	
	public static int getFireAspectModifier(EntityLiving p_90036_0_)
	{
		return getEnchantmentLevel(Enchantment.fireAspect.effectId, p_90036_0_.getHeldItem());
	}
	
	public static int getFortuneModifier(EntityLiving p_77517_0_)
	{
		return getEnchantmentLevel(Enchantment.fortune.effectId, p_77517_0_.getHeldItem());
	}
	
	public static int getKnockbackModifier(EntityLiving p_77507_0_, EntityLiving p_77507_1_)
	{
		return getEnchantmentLevel(Enchantment.knockback.effectId, p_77507_0_.getHeldItem());
	}
	
	public static int getLootingModifier(EntityLiving p_77519_0_)
	{
		return getEnchantmentLevel(Enchantment.looting.effectId, p_77519_0_.getHeldItem());
	}
	
	public static int getMaxEnchantmentLevel(int p_77511_0_, ItemStack[] p_77511_1_)
	{
		if(p_77511_1_ == null) return 0;
		else
		{
			int var2 = 0;
			ItemStack[] var3 = p_77511_1_;
			int var4 = p_77511_1_.length;
			for(int var5 = 0; var5 < var4; ++var5)
			{
				ItemStack var6 = var3[var5];
				int var7 = getEnchantmentLevel(p_77511_0_, var6);
				if(var7 > var2)
				{
					var2 = var7;
				}
			}
			return var2;
		}
	}
	
	public static int getRespiration(EntityLiving p_77501_0_)
	{
		return getMaxEnchantmentLevel(Enchantment.respiration.effectId, p_77501_0_.getLastActiveItems());
	}
	
	public static boolean getSilkTouchModifier(EntityLiving p_77502_0_)
	{
		return getEnchantmentLevel(Enchantment.silkTouch.effectId, p_77502_0_.getHeldItem()) > 0;
	}
	
	public static Map mapEnchantmentData(int p_77505_0_, ItemStack p_77505_1_)
	{
		Item var2 = p_77505_1_.getItem();
		HashMap var3 = null;
		boolean var4 = p_77505_1_.itemID == Item.book.itemID;
		Enchantment[] var5 = Enchantment.enchantmentsList;
		int var6 = var5.length;
		for(int var7 = 0; var7 < var6; ++var7)
		{
			Enchantment var8 = var5[var7];
			if(var8 != null && (var8.type.canEnchantItem(var2) || var4))
			{
				for(int var9 = var8.getMinLevel(); var9 <= var8.getMaxLevel(); ++var9)
				{
					if(p_77505_0_ >= var8.getMinEnchantability(var9) && p_77505_0_ <= var8.getMaxEnchantability(var9))
					{
						if(var3 == null)
						{
							var3 = new HashMap();
						}
						var3.put(Integer.valueOf(var8.effectId), new EnchantmentData(var8, var9));
					}
				}
			}
		}
		return var3;
	}
	
	public static void setEnchantments(Map p_82782_0_, ItemStack p_82782_1_)
	{
		NBTTagList var2 = new NBTTagList();
		Iterator var3 = p_82782_0_.keySet().iterator();
		while(var3.hasNext())
		{
			int var4 = ((Integer) var3.next()).intValue();
			NBTTagCompound var5 = new NBTTagCompound();
			var5.setShort("id", (short) var4);
			var5.setShort("lvl", (short) ((Integer) p_82782_0_.get(Integer.valueOf(var4))).intValue());
			var2.appendTag(var5);
			if(p_82782_1_.itemID == Item.enchantedBook.itemID)
			{
				Item.enchantedBook.getEnchantedItemStack_do(p_82782_1_, new EnchantmentData(var4, ((Integer) p_82782_0_.get(Integer.valueOf(var4))).intValue()));
			}
		}
		if(var2.tagCount() > 0)
		{
			if(p_82782_1_.itemID != Item.enchantedBook.itemID)
			{
				p_82782_1_.setTagInfo("ench", var2);
			}
		} else if(p_82782_1_.hasTagCompound())
		{
			p_82782_1_.getTagCompound().removeTag("ench");
		}
	}
}
