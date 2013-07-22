package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MerchantRecipeList extends ArrayList
{
	private static final long serialVersionUID = 1L;
	
	public MerchantRecipeList()
	{
	}
	
	public MerchantRecipeList(NBTTagCompound p_i3725_1_)
	{
		readRecipiesFromTags(p_i3725_1_);
	}
	
	public void addToListWithCheck(MerchantRecipe p_77205_1_)
	{
		for(int var2 = 0; var2 < size(); ++var2)
		{
			MerchantRecipe var3 = (MerchantRecipe) get(var2);
			if(p_77205_1_.hasSameIDsAs(var3))
			{
				if(p_77205_1_.hasSameItemsAs(var3))
				{
					set(var2, p_77205_1_);
				}
				return;
			}
		}
		this.add(p_77205_1_);
	}
	
	public MerchantRecipe canRecipeBeUsed(ItemStack p_77203_1_, ItemStack p_77203_2_, int p_77203_3_)
	{
		if(p_77203_3_ > 0 && p_77203_3_ < size())
		{
			MerchantRecipe var6 = (MerchantRecipe) get(p_77203_3_);
			return p_77203_1_.itemID == var6.getItemToBuy().itemID && (p_77203_2_ == null && !var6.hasSecondItemToBuy() || var6.hasSecondItemToBuy() && p_77203_2_ != null && var6.getSecondItemToBuy().itemID == p_77203_2_.itemID) && p_77203_1_.stackSize >= var6.getItemToBuy().stackSize && (!var6.hasSecondItemToBuy() || p_77203_2_.stackSize >= var6.getSecondItemToBuy().stackSize) ? var6 : null;
		} else
		{
			for(int var4 = 0; var4 < size(); ++var4)
			{
				MerchantRecipe var5 = (MerchantRecipe) get(var4);
				if(p_77203_1_.itemID == var5.getItemToBuy().itemID && p_77203_1_.stackSize >= var5.getItemToBuy().stackSize && (!var5.hasSecondItemToBuy() && p_77203_2_ == null || var5.hasSecondItemToBuy() && p_77203_2_ != null && var5.getSecondItemToBuy().itemID == p_77203_2_.itemID && p_77203_2_.stackSize >= var5.getSecondItemToBuy().stackSize)) return var5;
			}
			return null;
		}
	}
	
	public NBTTagCompound getRecipiesAsTags()
	{
		NBTTagCompound var1 = new NBTTagCompound();
		NBTTagList var2 = new NBTTagList("Recipes");
		for(int var3 = 0; var3 < size(); ++var3)
		{
			MerchantRecipe var4 = (MerchantRecipe) get(var3);
			var2.appendTag(var4.writeToTags());
		}
		var1.setTag("Recipes", var2);
		return var1;
	}
	
	public void readRecipiesFromTags(NBTTagCompound p_77201_1_)
	{
		NBTTagList var2 = p_77201_1_.getTagList("Recipes");
		for(int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
			this.add(new MerchantRecipe(var4));
		}
	}
	
	public void writeRecipiesToStream(DataOutputStream p_77200_1_) throws IOException
	{
		p_77200_1_.writeByte((byte) (size() & 255));
		for(int var2 = 0; var2 < size(); ++var2)
		{
			MerchantRecipe var3 = (MerchantRecipe) get(var2);
			Packet.writeItemStack(var3.getItemToBuy(), p_77200_1_);
			Packet.writeItemStack(var3.getItemToSell(), p_77200_1_);
			ItemStack var4 = var3.getSecondItemToBuy();
			p_77200_1_.writeBoolean(var4 != null);
			if(var4 != null)
			{
				Packet.writeItemStack(var4, p_77200_1_);
			}
			p_77200_1_.writeBoolean(var3.func_82784_g());
		}
	}
	
	public static MerchantRecipeList readRecipiesFromStream(DataInputStream par0DataInputStream) throws IOException
	{
		MerchantRecipeList var1 = new MerchantRecipeList();
		int var2 = par0DataInputStream.readByte() & 255;
		for(int var3 = 0; var3 < var2; ++var3)
		{
			ItemStack var4 = Packet.readItemStack(par0DataInputStream);
			ItemStack var5 = Packet.readItemStack(par0DataInputStream);
			ItemStack var6 = null;
			if(par0DataInputStream.readBoolean())
			{
				var6 = Packet.readItemStack(par0DataInputStream);
			}
			boolean var7 = par0DataInputStream.readBoolean();
			MerchantRecipe var8 = new MerchantRecipe(var4, var6, var5);
			if(var7)
			{
				var8.func_82785_h();
			}
			var1.add(var8);
		}
		return var1;
	}
}
