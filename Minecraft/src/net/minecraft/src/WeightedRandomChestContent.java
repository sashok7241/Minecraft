package net.minecraft.src;

import java.util.Random;

public class WeightedRandomChestContent extends WeightedRandomItem
{
	private ItemStack theItemId = null;
	private int theMinimumChanceToGenerateItem;
	private int theMaximumChanceToGenerateItem;
	
	public WeightedRandomChestContent(int p_i3424_1_, int p_i3424_2_, int p_i3424_3_, int p_i3424_4_, int p_i3424_5_)
	{
		super(p_i3424_5_);
		theItemId = new ItemStack(p_i3424_1_, 1, p_i3424_2_);
		theMinimumChanceToGenerateItem = p_i3424_3_;
		theMaximumChanceToGenerateItem = p_i3424_4_;
	}
	
	public WeightedRandomChestContent(ItemStack p_i8006_1_, int p_i8006_2_, int p_i8006_3_, int p_i8006_4_)
	{
		super(p_i8006_4_);
		theItemId = p_i8006_1_;
		theMinimumChanceToGenerateItem = p_i8006_2_;
		theMaximumChanceToGenerateItem = p_i8006_3_;
	}
	
	public static WeightedRandomChestContent[] func_92080_a(WeightedRandomChestContent[] p_92080_0_, WeightedRandomChestContent ... p_92080_1_)
	{
		WeightedRandomChestContent[] var2 = new WeightedRandomChestContent[p_92080_0_.length + p_92080_1_.length];
		int var3 = 0;
		for(WeightedRandomChestContent element : p_92080_0_)
		{
			var2[var3++] = element;
		}
		WeightedRandomChestContent[] var8 = p_92080_1_;
		int var5 = p_92080_1_.length;
		for(int var6 = 0; var6 < var5; ++var6)
		{
			WeightedRandomChestContent var7 = var8[var6];
			var2[var3++] = var7;
		}
		return var2;
	}
	
	public static void generateChestContents(Random p_76293_0_, WeightedRandomChestContent[] p_76293_1_, IInventory p_76293_2_, int p_76293_3_)
	{
		for(int var4 = 0; var4 < p_76293_3_; ++var4)
		{
			WeightedRandomChestContent var5 = (WeightedRandomChestContent) WeightedRandom.getRandomItem(p_76293_0_, p_76293_1_);
			int var6 = var5.theMinimumChanceToGenerateItem + p_76293_0_.nextInt(var5.theMaximumChanceToGenerateItem - var5.theMinimumChanceToGenerateItem + 1);
			if(var5.theItemId.getMaxStackSize() >= var6)
			{
				ItemStack var7 = var5.theItemId.copy();
				var7.stackSize = var6;
				p_76293_2_.setInventorySlotContents(p_76293_0_.nextInt(p_76293_2_.getSizeInventory()), var7);
			} else
			{
				for(int var9 = 0; var9 < var6; ++var9)
				{
					ItemStack var8 = var5.theItemId.copy();
					var8.stackSize = 1;
					p_76293_2_.setInventorySlotContents(p_76293_0_.nextInt(p_76293_2_.getSizeInventory()), var8);
				}
			}
		}
	}
	
	public static void generateDispenserContents(Random p_76294_0_, WeightedRandomChestContent[] p_76294_1_, TileEntityDispenser p_76294_2_, int p_76294_3_)
	{
		for(int var4 = 0; var4 < p_76294_3_; ++var4)
		{
			WeightedRandomChestContent var5 = (WeightedRandomChestContent) WeightedRandom.getRandomItem(p_76294_0_, p_76294_1_);
			int var6 = var5.theMinimumChanceToGenerateItem + p_76294_0_.nextInt(var5.theMaximumChanceToGenerateItem - var5.theMinimumChanceToGenerateItem + 1);
			if(var5.theItemId.getMaxStackSize() >= var6)
			{
				ItemStack var7 = var5.theItemId.copy();
				var7.stackSize = var6;
				p_76294_2_.setInventorySlotContents(p_76294_0_.nextInt(p_76294_2_.getSizeInventory()), var7);
			} else
			{
				for(int var9 = 0; var9 < var6; ++var9)
				{
					ItemStack var8 = var5.theItemId.copy();
					var8.stackSize = 1;
					p_76294_2_.setInventorySlotContents(p_76294_0_.nextInt(p_76294_2_.getSizeInventory()), var8);
				}
			}
		}
	}
}
