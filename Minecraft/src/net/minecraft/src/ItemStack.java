package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class ItemStack
{
	public int stackSize;
	public int animationsToGo;
	public int itemID;
	public NBTTagCompound stackTagCompound;
	private int itemDamage;
	private EntityItemFrame itemFrame;
	
	private ItemStack()
	{
		stackSize = 0;
		itemFrame = null;
	}
	
	public ItemStack(Block p_i3660_1_)
	{
		this(p_i3660_1_, 1);
	}
	
	public ItemStack(Block p_i3661_1_, int p_i3661_2_)
	{
		this(p_i3661_1_.blockID, p_i3661_2_, 0);
	}
	
	public ItemStack(Block p_i3662_1_, int p_i3662_2_, int p_i3662_3_)
	{
		this(p_i3662_1_.blockID, p_i3662_2_, p_i3662_3_);
	}
	
	public ItemStack(int p_i3666_1_, int p_i3666_2_, int p_i3666_3_)
	{
		stackSize = 0;
		itemFrame = null;
		itemID = p_i3666_1_;
		stackSize = p_i3666_2_;
		itemDamage = p_i3666_3_;
		if(itemDamage < 0)
		{
			itemDamage = 0;
		}
	}
	
	public ItemStack(Item p_i3663_1_)
	{
		this(p_i3663_1_.itemID, 1, 0);
	}
	
	public ItemStack(Item p_i3664_1_, int p_i3664_2_)
	{
		this(p_i3664_1_.itemID, p_i3664_2_, 0);
	}
	
	public ItemStack(Item p_i3665_1_, int p_i3665_2_, int p_i3665_3_)
	{
		this(p_i3665_1_.itemID, p_i3665_2_, p_i3665_3_);
	}
	
	public void addEnchantment(Enchantment p_77966_1_, int p_77966_2_)
	{
		if(stackTagCompound == null)
		{
			setTagCompound(new NBTTagCompound());
		}
		if(!stackTagCompound.hasKey("ench"))
		{
			stackTagCompound.setTag("ench", new NBTTagList("ench"));
		}
		NBTTagList var3 = (NBTTagList) stackTagCompound.getTag("ench");
		NBTTagCompound var4 = new NBTTagCompound();
		var4.setShort("id", (short) p_77966_1_.effectId);
		var4.setShort("lvl", (byte) p_77966_2_);
		var3.appendTag(var4);
	}
	
	public boolean attemptDamageItem(int p_96631_1_, Random p_96631_2_)
	{
		if(!isItemStackDamageable()) return false;
		else
		{
			if(p_96631_1_ > 0)
			{
				int var3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
				int var4 = 0;
				for(int var5 = 0; var3 > 0 && var5 < p_96631_1_; ++var5)
				{
					if(EnchantmentDurability.negateDamage(this, var3, p_96631_2_))
					{
						++var4;
					}
				}
				p_96631_1_ -= var4;
				if(p_96631_1_ <= 0) return false;
			}
			itemDamage += p_96631_1_;
			return itemDamage > getMaxDamage();
		}
	}
	
	public boolean canEditBlocks()
	{
		return getItem().canItemEditBlocks();
	}
	
	public boolean canHarvestBlock(Block p_77987_1_)
	{
		return Item.itemsList[itemID].canHarvestBlock(p_77987_1_);
	}
	
	public ItemStack copy()
	{
		ItemStack var1 = new ItemStack(itemID, stackSize, itemDamage);
		if(stackTagCompound != null)
		{
			var1.stackTagCompound = (NBTTagCompound) stackTagCompound.copy();
		}
		return var1;
	}
	
	public void damageItem(int p_77972_1_, EntityLiving p_77972_2_)
	{
		if(!(p_77972_2_ instanceof EntityPlayer) || !((EntityPlayer) p_77972_2_).capabilities.isCreativeMode)
		{
			if(isItemStackDamageable())
			{
				if(attemptDamageItem(p_77972_1_, p_77972_2_.getRNG()))
				{
					p_77972_2_.renderBrokenItemStack(this);
					if(p_77972_2_ instanceof EntityPlayer)
					{
						((EntityPlayer) p_77972_2_).addStat(StatList.objectBreakStats[itemID], 1);
					}
					--stackSize;
					if(stackSize < 0)
					{
						stackSize = 0;
					}
					itemDamage = 0;
				}
			}
		}
	}
	
	public int getDamageVsEntity(Entity p_77971_1_)
	{
		return Item.itemsList[itemID].getDamageVsEntity(p_77971_1_);
	}
	
	public String getDisplayName()
	{
		String var1 = getItem().getItemDisplayName(this);
		if(stackTagCompound != null && stackTagCompound.hasKey("display"))
		{
			NBTTagCompound var2 = stackTagCompound.getCompoundTag("display");
			if(var2.hasKey("Name"))
			{
				var1 = var2.getString("Name");
			}
		}
		return var1;
	}
	
	public NBTTagList getEnchantmentTagList()
	{
		return stackTagCompound == null ? null : (NBTTagList) stackTagCompound.getTag("ench");
	}
	
	public boolean getHasSubtypes()
	{
		return Item.itemsList[itemID].getHasSubtypes();
	}
	
	public Icon getIconIndex()
	{
		return getItem().getIconIndex(this);
	}
	
	public Item getItem()
	{
		return Item.itemsList[itemID];
	}
	
	public int getItemDamage()
	{
		return itemDamage;
	}
	
	public int getItemDamageForDisplay()
	{
		return itemDamage;
	}
	
	public EntityItemFrame getItemFrame()
	{
		return itemFrame;
	}
	
	public String getItemName()
	{
		return Item.itemsList[itemID].getUnlocalizedName(this);
	}
	
	public int getItemSpriteNumber()
	{
		return getItem().getSpriteNumber();
	}
	
	public EnumAction getItemUseAction()
	{
		return getItem().getItemUseAction(this);
	}
	
	public int getMaxDamage()
	{
		return Item.itemsList[itemID].getMaxDamage();
	}
	
	public int getMaxItemUseDuration()
	{
		return getItem().getMaxItemUseDuration(this);
	}
	
	public int getMaxStackSize()
	{
		return getItem().getItemStackLimit();
	}
	
	public EnumRarity getRarity()
	{
		return getItem().getRarity(this);
	}
	
	public int getRepairCost()
	{
		return hasTagCompound() && stackTagCompound.hasKey("RepairCost") ? stackTagCompound.getInteger("RepairCost") : 0;
	}
	
	public float getStrVsBlock(Block p_77967_1_)
	{
		return getItem().getStrVsBlock(this, p_77967_1_);
	}
	
	public NBTTagCompound getTagCompound()
	{
		return stackTagCompound;
	}
	
	public List getTooltip(EntityPlayer par1EntityPlayer, boolean par2)
	{
		ArrayList var3 = new ArrayList();
		Item var4 = Item.itemsList[itemID];
		String var5 = getDisplayName();
		if(hasDisplayName())
		{
			var5 = EnumChatFormatting.ITALIC + var5 + EnumChatFormatting.RESET;
		}
		if(par2)
		{
			String var6 = "";
			if(var5.length() > 0)
			{
				var5 = var5 + " (";
				var6 = ")";
			}
			if(getHasSubtypes())
			{
				var5 = var5 + String.format("#%04d/%d%s", new Object[] { Integer.valueOf(itemID), Integer.valueOf(itemDamage), var6 });
			} else
			{
				var5 = var5 + String.format("#%04d%s", new Object[] { Integer.valueOf(itemID), var6 });
			}
		} else if(!hasDisplayName() && itemID == Item.map.itemID)
		{
			var5 = var5 + " #" + itemDamage;
		}
		var3.add(var5);
		var4.addInformation(this, par1EntityPlayer, var3, par2);
		if(hasTagCompound())
		{
			NBTTagList var10 = getEnchantmentTagList();
			if(var10 != null)
			{
				for(int var7 = 0; var7 < var10.tagCount(); ++var7)
				{
					short var8 = ((NBTTagCompound) var10.tagAt(var7)).getShort("id");
					short var9 = ((NBTTagCompound) var10.tagAt(var7)).getShort("lvl");
					if(Enchantment.enchantmentsList[var8] != null)
					{
						var3.add(Enchantment.enchantmentsList[var8].getTranslatedName(var9));
					}
				}
			}
			if(stackTagCompound.hasKey("display"))
			{
				NBTTagCompound var11 = stackTagCompound.getCompoundTag("display");
				if(var11.hasKey("color"))
				{
					if(par2)
					{
						var3.add("Color: #" + Integer.toHexString(var11.getInteger("color")).toUpperCase());
					} else
					{
						var3.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("item.dyed"));
					}
				}
				if(var11.hasKey("Lore"))
				{
					NBTTagList var12 = var11.getTagList("Lore");
					if(var12.tagCount() > 0)
					{
						for(int var13 = 0; var13 < var12.tagCount(); ++var13)
						{
							var3.add(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.ITALIC + ((NBTTagString) var12.tagAt(var13)).data);
						}
					}
				}
			}
		}
		if(par2 && isItemDamaged())
		{
			var3.add("Durability: " + (getMaxDamage() - getItemDamageForDisplay()) + " / " + getMaxDamage());
		}
		return var3;
	}
	
	public boolean hasDisplayName()
	{
		return stackTagCompound == null ? false : !stackTagCompound.hasKey("display") ? false : stackTagCompound.getCompoundTag("display").hasKey("Name");
	}
	
	public boolean hasEffect()
	{
		return getItem().hasEffect(this);
	}
	
	public boolean hasTagCompound()
	{
		return stackTagCompound != null;
	}
	
	public void hitEntity(EntityLiving p_77961_1_, EntityPlayer p_77961_2_)
	{
		boolean var3 = Item.itemsList[itemID].hitEntity(this, p_77961_1_, p_77961_2_);
		if(var3)
		{
			p_77961_2_.addStat(StatList.objectUseStats[itemID], 1);
		}
	}
	
	public boolean interactWith(EntityLiving p_77947_1_)
	{
		return Item.itemsList[itemID].itemInteractionForEntity(this, p_77947_1_);
	}
	
	public boolean isItemDamaged()
	{
		return isItemStackDamageable() && itemDamage > 0;
	}
	
	public boolean isItemEnchantable()
	{
		return !getItem().isItemTool(this) ? false : !isItemEnchanted();
	}
	
	public boolean isItemEnchanted()
	{
		return stackTagCompound != null && stackTagCompound.hasKey("ench");
	}
	
	public boolean isItemEqual(ItemStack p_77969_1_)
	{
		return itemID == p_77969_1_.itemID && itemDamage == p_77969_1_.itemDamage;
	}
	
	public boolean isItemStackDamageable()
	{
		return Item.itemsList[itemID].getMaxDamage() > 0;
	}
	
	private boolean isItemStackEqual(ItemStack p_77959_1_)
	{
		return stackSize != p_77959_1_.stackSize ? false : itemID != p_77959_1_.itemID ? false : itemDamage != p_77959_1_.itemDamage ? false : stackTagCompound == null && p_77959_1_.stackTagCompound != null ? false : stackTagCompound == null || stackTagCompound.equals(p_77959_1_.stackTagCompound);
	}
	
	public boolean isOnItemFrame()
	{
		return itemFrame != null;
	}
	
	public boolean isStackable()
	{
		return getMaxStackSize() > 1 && (!isItemStackDamageable() || !isItemDamaged());
	}
	
	public void onBlockDestroyed(World p_77941_1_, int p_77941_2_, int p_77941_3_, int p_77941_4_, int p_77941_5_, EntityPlayer p_77941_6_)
	{
		boolean var7 = Item.itemsList[itemID].onBlockDestroyed(this, p_77941_1_, p_77941_2_, p_77941_3_, p_77941_4_, p_77941_5_, p_77941_6_);
		if(var7)
		{
			p_77941_6_.addStat(StatList.objectUseStats[itemID], 1);
		}
	}
	
	public void onCrafting(World p_77980_1_, EntityPlayer p_77980_2_, int p_77980_3_)
	{
		p_77980_2_.addStat(StatList.objectCraftStats[itemID], p_77980_3_);
		Item.itemsList[itemID].onCreated(this, p_77980_1_, p_77980_2_);
	}
	
	public ItemStack onFoodEaten(World p_77950_1_, EntityPlayer p_77950_2_)
	{
		return getItem().onEaten(this, p_77950_1_, p_77950_2_);
	}
	
	public void onPlayerStoppedUsing(World p_77974_1_, EntityPlayer p_77974_2_, int p_77974_3_)
	{
		getItem().onPlayerStoppedUsing(this, p_77974_1_, p_77974_2_, p_77974_3_);
	}
	
	public void readFromNBT(NBTTagCompound p_77963_1_)
	{
		itemID = p_77963_1_.getShort("id");
		stackSize = p_77963_1_.getByte("Count");
		itemDamage = p_77963_1_.getShort("Damage");
		if(itemDamage < 0)
		{
			itemDamage = 0;
		}
		if(p_77963_1_.hasKey("tag"))
		{
			stackTagCompound = p_77963_1_.getCompoundTag("tag");
		}
	}
	
	public void setItemDamage(int p_77964_1_)
	{
		itemDamage = p_77964_1_;
		if(itemDamage < 0)
		{
			itemDamage = 0;
		}
	}
	
	public void setItemFrame(EntityItemFrame p_82842_1_)
	{
		itemFrame = p_82842_1_;
	}
	
	public void setItemName(String p_82834_1_)
	{
		if(stackTagCompound == null)
		{
			stackTagCompound = new NBTTagCompound("tag");
		}
		if(!stackTagCompound.hasKey("display"))
		{
			stackTagCompound.setCompoundTag("display", new NBTTagCompound());
		}
		stackTagCompound.getCompoundTag("display").setString("Name", p_82834_1_);
	}
	
	public void setRepairCost(int p_82841_1_)
	{
		if(!hasTagCompound())
		{
			stackTagCompound = new NBTTagCompound("tag");
		}
		stackTagCompound.setInteger("RepairCost", p_82841_1_);
	}
	
	public void setTagCompound(NBTTagCompound p_77982_1_)
	{
		stackTagCompound = p_77982_1_;
	}
	
	public void setTagInfo(String p_77983_1_, NBTBase p_77983_2_)
	{
		if(stackTagCompound == null)
		{
			setTagCompound(new NBTTagCompound());
		}
		stackTagCompound.setTag(p_77983_1_, p_77983_2_);
	}
	
	public ItemStack splitStack(int p_77979_1_)
	{
		ItemStack var2 = new ItemStack(itemID, p_77979_1_, itemDamage);
		if(stackTagCompound != null)
		{
			var2.stackTagCompound = (NBTTagCompound) stackTagCompound.copy();
		}
		stackSize -= p_77979_1_;
		return var2;
	}
	
	@Override public String toString()
	{
		return stackSize + "x" + Item.itemsList[itemID].getUnlocalizedName() + "@" + itemDamage;
	}
	
	public boolean tryPlaceItemIntoWorld(EntityPlayer p_77943_1_, World p_77943_2_, int p_77943_3_, int p_77943_4_, int p_77943_5_, int p_77943_6_, float p_77943_7_, float p_77943_8_, float p_77943_9_)
	{
		boolean var10 = getItem().onItemUse(this, p_77943_1_, p_77943_2_, p_77943_3_, p_77943_4_, p_77943_5_, p_77943_6_, p_77943_7_, p_77943_8_, p_77943_9_);
		if(var10)
		{
			p_77943_1_.addStat(StatList.objectUseStats[itemID], 1);
		}
		return var10;
	}
	
	public void updateAnimation(World p_77945_1_, Entity p_77945_2_, int p_77945_3_, boolean p_77945_4_)
	{
		if(animationsToGo > 0)
		{
			--animationsToGo;
		}
		Item.itemsList[itemID].onUpdate(this, p_77945_1_, p_77945_2_, p_77945_3_, p_77945_4_);
	}
	
	public ItemStack useItemRightClick(World p_77957_1_, EntityPlayer p_77957_2_)
	{
		return getItem().onItemRightClick(this, p_77957_1_, p_77957_2_);
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound p_77955_1_)
	{
		p_77955_1_.setShort("id", (short) itemID);
		p_77955_1_.setByte("Count", (byte) stackSize);
		p_77955_1_.setShort("Damage", (short) itemDamage);
		if(stackTagCompound != null)
		{
			p_77955_1_.setTag("tag", stackTagCompound);
		}
		return p_77955_1_;
	}
	
	public static boolean areItemStacksEqual(ItemStack p_77989_0_, ItemStack p_77989_1_)
	{
		return p_77989_0_ == null && p_77989_1_ == null ? true : p_77989_0_ != null && p_77989_1_ != null ? p_77989_0_.isItemStackEqual(p_77989_1_) : false;
	}
	
	public static boolean areItemStackTagsEqual(ItemStack p_77970_0_, ItemStack p_77970_1_)
	{
		return p_77970_0_ == null && p_77970_1_ == null ? true : p_77970_0_ != null && p_77970_1_ != null ? p_77970_0_.stackTagCompound == null && p_77970_1_.stackTagCompound != null ? false : p_77970_0_.stackTagCompound == null || p_77970_0_.stackTagCompound.equals(p_77970_1_.stackTagCompound) : false;
	}
	
	public static ItemStack copyItemStack(ItemStack p_77944_0_)
	{
		return p_77944_0_ == null ? null : p_77944_0_.copy();
	}
	
	public static ItemStack loadItemStackFromNBT(NBTTagCompound p_77949_0_)
	{
		ItemStack var1 = new ItemStack();
		var1.readFromNBT(p_77949_0_);
		return var1.getItem() != null ? var1 : null;
	}
}
