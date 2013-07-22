package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ItemPotion extends Item
{
	private HashMap effectCache = new HashMap();
	private static final Map field_77835_b = new LinkedHashMap();
	private Icon field_94591_c;
	private Icon field_94590_d;
	private Icon field_94592_ct;
	
	public ItemPotion(int p_i3675_1_)
	{
		super(p_i3675_1_);
		setMaxStackSize(1);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(CreativeTabs.tabBrewing);
	}
	
	@Override public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if(par1ItemStack.getItemDamage() != 0)
		{
			List var5 = Item.potion.getEffects(par1ItemStack);
			if(var5 != null && !var5.isEmpty())
			{
				Iterator var9 = var5.iterator();
				while(var9.hasNext())
				{
					PotionEffect var7 = (PotionEffect) var9.next();
					String var8 = StatCollector.translateToLocal(var7.getEffectName()).trim();
					if(var7.getAmplifier() > 0)
					{
						var8 = var8 + " " + StatCollector.translateToLocal("potion.potency." + var7.getAmplifier()).trim();
					}
					if(var7.getDuration() > 20)
					{
						var8 = var8 + " (" + Potion.getDurationString(var7) + ")";
					}
					if(Potion.potionTypes[var7.getPotionID()].isBadEffect())
					{
						par3List.add(EnumChatFormatting.RED + var8);
					} else
					{
						par3List.add(EnumChatFormatting.GRAY + var8);
					}
				}
			} else
			{
				String var6 = StatCollector.translateToLocal("potion.empty").trim();
				par3List.add(EnumChatFormatting.GRAY + var6);
			}
		}
	}
	
	public int getColorFromDamage(int par1)
	{
		return PotionHelper.func_77915_a(par1, false);
	}
	
	@Override public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		return par2 > 0 ? 16777215 : getColorFromDamage(par1ItemStack.getItemDamage());
	}
	
	public List getEffects(int p_77834_1_)
	{
		List var2 = (List) effectCache.get(Integer.valueOf(p_77834_1_));
		if(var2 == null)
		{
			var2 = PotionHelper.getPotionEffects(p_77834_1_, false);
			effectCache.put(Integer.valueOf(p_77834_1_), var2);
		}
		return var2;
	}
	
	public List getEffects(ItemStack p_77832_1_)
	{
		if(p_77832_1_.hasTagCompound() && p_77832_1_.getTagCompound().hasKey("CustomPotionEffects"))
		{
			ArrayList var6 = new ArrayList();
			NBTTagList var3 = p_77832_1_.getTagCompound().getTagList("CustomPotionEffects");
			for(int var4 = 0; var4 < var3.tagCount(); ++var4)
			{
				NBTTagCompound var5 = (NBTTagCompound) var3.tagAt(var4);
				var6.add(PotionEffect.readCustomPotionEffectFromNBT(var5));
			}
			return var6;
		} else
		{
			List var2 = (List) effectCache.get(Integer.valueOf(p_77832_1_.getItemDamage()));
			if(var2 == null)
			{
				var2 = PotionHelper.getPotionEffects(p_77832_1_.getItemDamage(), false);
				effectCache.put(Integer.valueOf(p_77832_1_.getItemDamage()), var2);
			}
			return var2;
		}
	}
	
	@Override public Icon getIconFromDamage(int par1)
	{
		return isSplash(par1) ? field_94591_c : field_94590_d;
	}
	
	@Override public Icon getIconFromDamageForRenderPass(int par1, int par2)
	{
		return par2 == 0 ? field_94592_ct : super.getIconFromDamageForRenderPass(par1, par2);
	}
	
	@Override public String getItemDisplayName(ItemStack p_77628_1_)
	{
		if(p_77628_1_.getItemDamage() == 0) return StatCollector.translateToLocal("item.emptyPotion.name").trim();
		else
		{
			String var2 = "";
			if(isSplash(p_77628_1_.getItemDamage()))
			{
				var2 = StatCollector.translateToLocal("potion.prefix.grenade").trim() + " ";
			}
			List var3 = Item.potion.getEffects(p_77628_1_);
			String var4;
			if(var3 != null && !var3.isEmpty())
			{
				var4 = ((PotionEffect) var3.get(0)).getEffectName();
				var4 = var4 + ".postfix";
				return var2 + StatCollector.translateToLocal(var4).trim();
			} else
			{
				var4 = PotionHelper.func_77905_c(p_77628_1_.getItemDamage());
				return StatCollector.translateToLocal(var4).trim() + " " + super.getItemDisplayName(p_77628_1_);
			}
		}
	}
	
	@Override public EnumAction getItemUseAction(ItemStack p_77661_1_)
	{
		return EnumAction.drink;
	}
	
	@Override public int getMaxItemUseDuration(ItemStack p_77626_1_)
	{
		return 32;
	}
	
	@Override public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		super.getSubItems(par1, par2CreativeTabs, par3List);
		int var5;
		if(field_77835_b.isEmpty())
		{
			for(int var4 = 0; var4 <= 15; ++var4)
			{
				for(var5 = 0; var5 <= 1; ++var5)
				{
					int var6;
					if(var5 == 0)
					{
						var6 = var4 | 8192;
					} else
					{
						var6 = var4 | 16384;
					}
					for(int var7 = 0; var7 <= 2; ++var7)
					{
						int var8 = var6;
						if(var7 != 0)
						{
							if(var7 == 1)
							{
								var8 = var6 | 32;
							} else if(var7 == 2)
							{
								var8 = var6 | 64;
							}
						}
						List var9 = PotionHelper.getPotionEffects(var8, false);
						if(var9 != null && !var9.isEmpty())
						{
							field_77835_b.put(var9, Integer.valueOf(var8));
						}
					}
				}
			}
		}
		Iterator var10 = field_77835_b.values().iterator();
		while(var10.hasNext())
		{
			var5 = ((Integer) var10.next()).intValue();
			par3List.add(new ItemStack(par1, 1, var5));
		}
	}
	
	@Override public boolean hasEffect(ItemStack par1ItemStack)
	{
		List var2 = this.getEffects(par1ItemStack);
		return var2 != null && !var2.isEmpty();
	}
	
	public boolean isEffectInstant(int par1)
	{
		List var2 = this.getEffects(par1);
		if(var2 != null && !var2.isEmpty())
		{
			Iterator var3 = var2.iterator();
			PotionEffect var4;
			do
			{
				if(!var3.hasNext()) return false;
				var4 = (PotionEffect) var3.next();
			} while(!Potion.potionTypes[var4.getPotionID()].isInstant());
			return true;
		} else return false;
	}
	
	@Override public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
	{
		if(!p_77654_3_.capabilities.isCreativeMode)
		{
			--p_77654_1_.stackSize;
		}
		if(!p_77654_2_.isRemote)
		{
			List var4 = this.getEffects(p_77654_1_);
			if(var4 != null)
			{
				Iterator var5 = var4.iterator();
				while(var5.hasNext())
				{
					PotionEffect var6 = (PotionEffect) var5.next();
					p_77654_3_.addPotionEffect(new PotionEffect(var6));
				}
			}
		}
		if(!p_77654_3_.capabilities.isCreativeMode)
		{
			if(p_77654_1_.stackSize <= 0) return new ItemStack(Item.glassBottle);
			p_77654_3_.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
		}
		return p_77654_1_;
	}
	
	@Override public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		if(isSplash(p_77659_1_.getItemDamage()))
		{
			if(!p_77659_3_.capabilities.isCreativeMode)
			{
				--p_77659_1_.stackSize;
			}
			p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if(!p_77659_2_.isRemote)
			{
				p_77659_2_.spawnEntityInWorld(new EntityPotion(p_77659_2_, p_77659_3_, p_77659_1_));
			}
			return p_77659_1_;
		} else
		{
			p_77659_3_.setItemInUse(p_77659_1_, getMaxItemUseDuration(p_77659_1_));
			return p_77659_1_;
		}
	}
	
	@Override public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		return false;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		field_94590_d = par1IconRegister.registerIcon("potion");
		field_94591_c = par1IconRegister.registerIcon("potion_splash");
		field_94592_ct = par1IconRegister.registerIcon("potion_contents");
	}
	
	@Override public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
	
	public static Icon func_94589_d(String par0Str)
	{
		return par0Str == "potion" ? Item.potion.field_94590_d : par0Str == "potion_splash" ? Item.potion.field_94591_c : par0Str == "potion_contents" ? Item.potion.field_94592_ct : null;
	}
	
	public static boolean isSplash(int p_77831_0_)
	{
		return (p_77831_0_ & 16384) != 0;
	}
}
