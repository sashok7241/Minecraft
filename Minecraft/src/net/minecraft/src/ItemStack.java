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
	
	public ItemStack(Block par1Block)
	{
		this(par1Block, 1);
	}
	
	public ItemStack(Block par1Block, int par2)
	{
		this(par1Block.blockID, par2, 0);
	}
	
	public ItemStack(Block par1Block, int par2, int par3)
	{
		this(par1Block.blockID, par2, par3);
	}
	
	public ItemStack(int par1, int par2, int par3)
	{
		stackSize = 0;
		itemFrame = null;
		itemID = par1;
		stackSize = par2;
		itemDamage = par3;
		if(itemDamage < 0)
		{
			itemDamage = 0;
		}
	}
	
	public ItemStack(Item par1Item)
	{
		this(par1Item.itemID, 1, 0);
	}
	
	public ItemStack(Item par1Item, int par2)
	{
		this(par1Item.itemID, par2, 0);
	}
	
	public ItemStack(Item par1Item, int par2, int par3)
	{
		this(par1Item.itemID, par2, par3);
	}
	
	public void addEnchantment(Enchantment par1Enchantment, int par2)
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
		var4.setShort("id", (short) par1Enchantment.effectId);
		var4.setShort("lvl", (byte) par2);
		var3.appendTag(var4);
	}
	
	public boolean attemptDamageItem(int par1, Random par2Random)
	{
		if(!isItemStackDamageable()) return false;
		else
		{
			if(par1 > 0)
			{
				int var3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
				int var4 = 0;
				for(int var5 = 0; var3 > 0 && var5 < par1; ++var5)
				{
					if(EnchantmentDurability.negateDamage(this, var3, par2Random))
					{
						++var4;
					}
				}
				par1 -= var4;
				if(par1 <= 0) return false;
			}
			itemDamage += par1;
			return itemDamage > getMaxDamage();
		}
	}
	
	public boolean canEditBlocks()
	{
		return getItem().canItemEditBlocks();
	}
	
	public boolean canHarvestBlock(Block par1Block)
	{
		return Item.itemsList[itemID].canHarvestBlock(par1Block);
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
	
	public void damageItem(int par1, EntityLiving par2EntityLiving)
	{
		if(!(par2EntityLiving instanceof EntityPlayer) || !((EntityPlayer) par2EntityLiving).capabilities.isCreativeMode)
		{
			if(isItemStackDamageable())
			{
				if(attemptDamageItem(par1, par2EntityLiving.getRNG()))
				{
					par2EntityLiving.renderBrokenItemStack(this);
					if(par2EntityLiving instanceof EntityPlayer)
					{
						((EntityPlayer) par2EntityLiving).addStat(StatList.objectBreakStats[itemID], 1);
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
	
	public int getDamageVsEntity(Entity par1Entity)
	{
		return Item.itemsList[itemID].getDamageVsEntity(par1Entity);
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
	
	public float getStrVsBlock(Block par1Block)
	{
		return getItem().getStrVsBlock(this, par1Block);
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
	
	public void hitEntity(EntityLiving par1EntityLiving, EntityPlayer par2EntityPlayer)
	{
		boolean var3 = Item.itemsList[itemID].hitEntity(this, par1EntityLiving, par2EntityPlayer);
		if(var3)
		{
			par2EntityPlayer.addStat(StatList.objectUseStats[itemID], 1);
		}
	}
	
	public boolean interactWith(EntityLiving par1EntityLiving)
	{
		return Item.itemsList[itemID].itemInteractionForEntity(this, par1EntityLiving);
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
	
	public boolean isItemEqual(ItemStack par1ItemStack)
	{
		return itemID == par1ItemStack.itemID && itemDamage == par1ItemStack.itemDamage;
	}
	
	public boolean isItemStackDamageable()
	{
		return Item.itemsList[itemID].getMaxDamage() > 0;
	}
	
	private boolean isItemStackEqual(ItemStack par1ItemStack)
	{
		return stackSize != par1ItemStack.stackSize ? false : itemID != par1ItemStack.itemID ? false : itemDamage != par1ItemStack.itemDamage ? false : stackTagCompound == null && par1ItemStack.stackTagCompound != null ? false : stackTagCompound == null || stackTagCompound.equals(par1ItemStack.stackTagCompound);
	}
	
	public boolean isOnItemFrame()
	{
		return itemFrame != null;
	}
	
	public boolean isStackable()
	{
		return getMaxStackSize() > 1 && (!isItemStackDamageable() || !isItemDamaged());
	}
	
	public void onBlockDestroyed(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer)
	{
		boolean var7 = Item.itemsList[itemID].onBlockDestroyed(this, par1World, par2, par3, par4, par5, par6EntityPlayer);
		if(var7)
		{
			par6EntityPlayer.addStat(StatList.objectUseStats[itemID], 1);
		}
	}
	
	public void onCrafting(World par1World, EntityPlayer par2EntityPlayer, int par3)
	{
		par2EntityPlayer.addStat(StatList.objectCraftStats[itemID], par3);
		Item.itemsList[itemID].onCreated(this, par1World, par2EntityPlayer);
	}
	
	public ItemStack onFoodEaten(World par1World, EntityPlayer par2EntityPlayer)
	{
		return getItem().onEaten(this, par1World, par2EntityPlayer);
	}
	
	public void onPlayerStoppedUsing(World par1World, EntityPlayer par2EntityPlayer, int par3)
	{
		getItem().onPlayerStoppedUsing(this, par1World, par2EntityPlayer, par3);
	}
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		itemID = par1NBTTagCompound.getShort("id");
		stackSize = par1NBTTagCompound.getByte("Count");
		itemDamage = par1NBTTagCompound.getShort("Damage");
		if(itemDamage < 0)
		{
			itemDamage = 0;
		}
		if(par1NBTTagCompound.hasKey("tag"))
		{
			stackTagCompound = par1NBTTagCompound.getCompoundTag("tag");
		}
	}
	
	public void setItemDamage(int par1)
	{
		itemDamage = par1;
		if(itemDamage < 0)
		{
			itemDamage = 0;
		}
	}
	
	public void setItemFrame(EntityItemFrame par1EntityItemFrame)
	{
		itemFrame = par1EntityItemFrame;
	}
	
	public void setItemName(String par1Str)
	{
		if(stackTagCompound == null)
		{
			stackTagCompound = new NBTTagCompound("tag");
		}
		if(!stackTagCompound.hasKey("display"))
		{
			stackTagCompound.setCompoundTag("display", new NBTTagCompound());
		}
		stackTagCompound.getCompoundTag("display").setString("Name", par1Str);
	}
	
	public void setRepairCost(int par1)
	{
		if(!hasTagCompound())
		{
			stackTagCompound = new NBTTagCompound("tag");
		}
		stackTagCompound.setInteger("RepairCost", par1);
	}
	
	public void setTagCompound(NBTTagCompound par1NBTTagCompound)
	{
		stackTagCompound = par1NBTTagCompound;
	}
	
	public void setTagInfo(String par1Str, NBTBase par2NBTBase)
	{
		if(stackTagCompound == null)
		{
			setTagCompound(new NBTTagCompound());
		}
		stackTagCompound.setTag(par1Str, par2NBTBase);
	}
	
	public ItemStack splitStack(int par1)
	{
		ItemStack var2 = new ItemStack(itemID, par1, itemDamage);
		if(stackTagCompound != null)
		{
			var2.stackTagCompound = (NBTTagCompound) stackTagCompound.copy();
		}
		stackSize -= par1;
		return var2;
	}
	
	@Override public String toString()
	{
		return stackSize + "x" + Item.itemsList[itemID].getUnlocalizedName() + "@" + itemDamage;
	}
	
	public boolean tryPlaceItemIntoWorld(EntityPlayer par1EntityPlayer, World par2World, int par3, int par4, int par5, int par6, float par7, float par8, float par9)
	{
		boolean var10 = getItem().onItemUse(this, par1EntityPlayer, par2World, par3, par4, par5, par6, par7, par8, par9);
		if(var10)
		{
			par1EntityPlayer.addStat(StatList.objectUseStats[itemID], 1);
		}
		return var10;
	}
	
	public void updateAnimation(World par1World, Entity par2Entity, int par3, boolean par4)
	{
		if(animationsToGo > 0)
		{
			--animationsToGo;
		}
		Item.itemsList[itemID].onUpdate(this, par1World, par2Entity, par3, par4);
	}
	
	public ItemStack useItemRightClick(World par1World, EntityPlayer par2EntityPlayer)
	{
		return getItem().onItemRightClick(this, par1World, par2EntityPlayer);
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setShort("id", (short) itemID);
		par1NBTTagCompound.setByte("Count", (byte) stackSize);
		par1NBTTagCompound.setShort("Damage", (short) itemDamage);
		if(stackTagCompound != null)
		{
			par1NBTTagCompound.setTag("tag", stackTagCompound);
		}
		return par1NBTTagCompound;
	}
	
	public static boolean areItemStacksEqual(ItemStack par0ItemStack, ItemStack par1ItemStack)
	{
		return par0ItemStack == null && par1ItemStack == null ? true : par0ItemStack != null && par1ItemStack != null ? par0ItemStack.isItemStackEqual(par1ItemStack) : false;
	}
	
	public static boolean areItemStackTagsEqual(ItemStack par0ItemStack, ItemStack par1ItemStack)
	{
		return par0ItemStack == null && par1ItemStack == null ? true : par0ItemStack != null && par1ItemStack != null ? par0ItemStack.stackTagCompound == null && par1ItemStack.stackTagCompound != null ? false : par0ItemStack.stackTagCompound == null || par0ItemStack.stackTagCompound.equals(par1ItemStack.stackTagCompound) : false;
	}
	
	public static ItemStack copyItemStack(ItemStack par0ItemStack)
	{
		return par0ItemStack == null ? null : par0ItemStack.copy();
	}
	
	public static ItemStack loadItemStackFromNBT(NBTTagCompound par0NBTTagCompound)
	{
		ItemStack var1 = new ItemStack();
		var1.readFromNBT(par0NBTTagCompound);
		return var1.getItem() != null ? var1 : null;
	}
}
