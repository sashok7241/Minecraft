package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class ItemFirework extends Item
{
	public ItemFirework(int p_i8013_1_)
	{
		super(p_i8013_1_);
	}
	
	@Override public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if(par1ItemStack.hasTagCompound())
		{
			NBTTagCompound var5 = par1ItemStack.getTagCompound().getCompoundTag("Fireworks");
			if(var5 != null)
			{
				if(var5.hasKey("Flight"))
				{
					par3List.add(StatCollector.translateToLocal("item.fireworks.flight") + " " + var5.getByte("Flight"));
				}
				NBTTagList var6 = var5.getTagList("Explosions");
				if(var6 != null && var6.tagCount() > 0)
				{
					for(int var7 = 0; var7 < var6.tagCount(); ++var7)
					{
						NBTTagCompound var8 = (NBTTagCompound) var6.tagAt(var7);
						ArrayList var9 = new ArrayList();
						ItemFireworkCharge.func_92107_a(var8, var9);
						if(var9.size() > 0)
						{
							for(int var10 = 1; var10 < var9.size(); ++var10)
							{
								var9.set(var10, "  " + (String) var9.get(var10));
							}
							par3List.addAll(var9);
						}
					}
				}
			}
		}
	}
	
	@Override public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		if(!p_77648_3_.isRemote)
		{
			EntityFireworkRocket var11 = new EntityFireworkRocket(p_77648_3_, p_77648_4_ + p_77648_8_, p_77648_5_ + p_77648_9_, p_77648_6_ + p_77648_10_, p_77648_1_);
			p_77648_3_.spawnEntityInWorld(var11);
			if(!p_77648_2_.capabilities.isCreativeMode)
			{
				--p_77648_1_.stackSize;
			}
			return true;
		} else return false;
	}
}
