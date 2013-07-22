package net.minecraft.src;

import java.util.List;

public class CreativeTabs
{
	public static final CreativeTabs[] creativeTabArray = new CreativeTabs[12];
	public static final CreativeTabs tabBlock = new CreativeTabCombat(0, "buildingBlocks");
	public static final CreativeTabs tabDecorations = new CreativeTabBlock(1, "decorations");
	public static final CreativeTabs tabRedstone = new CreativeTabDeco(2, "redstone");
	public static final CreativeTabs tabTransport = new CreativeTabRedstone(3, "transportation");
	public static final CreativeTabs tabMisc = new CreativeTabTransport(4, "misc").func_111229_a(new EnumEnchantmentType[] { EnumEnchantmentType.all });
	public static final CreativeTabs tabAllSearch = new CreativeTabMisc(5, "search").setBackgroundImageName("item_search.png");
	public static final CreativeTabs tabFood = new CreativeTabSearch(6, "food");
	public static final CreativeTabs tabTools = new CreativeTabFood(7, "tools").func_111229_a(new EnumEnchantmentType[] { EnumEnchantmentType.digger });
	public static final CreativeTabs tabCombat = new CreativeTabTools(8, "combat").func_111229_a(new EnumEnchantmentType[] { EnumEnchantmentType.armor, EnumEnchantmentType.armor_feet, EnumEnchantmentType.armor_head, EnumEnchantmentType.armor_legs, EnumEnchantmentType.armor_torso, EnumEnchantmentType.bow, EnumEnchantmentType.weapon });
	public static final CreativeTabs tabBrewing = new CreativeTabBrewing(9, "brewing");
	public static final CreativeTabs tabMaterials = new CreativeTabMaterial(10, "materials");
	public static final CreativeTabs tabInventory = new CreativeTabInventory(11, "inventory").setBackgroundImageName("inventory.png").setNoScrollbar().setNoTitle();
	private final int tabIndex;
	private final String tabLabel;
	private String backgroundImageName = "items.png";
	private boolean hasScrollbar = true;
	private boolean drawTitle = true;
	private EnumEnchantmentType[] field_111230_s;
	
	public CreativeTabs(int par1, String par2Str)
	{
		tabIndex = par1;
		tabLabel = par2Str;
		creativeTabArray[par1] = this;
	}
	
	public void addEnchantmentBooksToList(List par1List, EnumEnchantmentType ... par2ArrayOfEnumEnchantmentType)
	{
		Enchantment[] var3 = Enchantment.enchantmentsList;
		int var4 = var3.length;
		for(int var5 = 0; var5 < var4; ++var5)
		{
			Enchantment var6 = var3[var5];
			if(var6 != null && var6.type != null)
			{
				boolean var7 = false;
				for(int var8 = 0; var8 < par2ArrayOfEnumEnchantmentType.length && !var7; ++var8)
				{
					if(var6.type == par2ArrayOfEnumEnchantmentType[var8])
					{
						var7 = true;
					}
				}
				if(var7)
				{
					par1List.add(Item.enchantedBook.getEnchantedItemStack(new EnchantmentData(var6, var6.getMaxLevel())));
				}
			}
		}
	}
	
	public void displayAllReleventItems(List par1List)
	{
		Item[] var2 = Item.itemsList;
		int var3 = var2.length;
		for(int var4 = 0; var4 < var3; ++var4)
		{
			Item var5 = var2[var4];
			if(var5 != null && var5.getCreativeTab() == this)
			{
				var5.getSubItems(var5.itemID, this, par1List);
			}
		}
		if(func_111225_m() != null)
		{
			addEnchantmentBooksToList(par1List, func_111225_m());
		}
	}
	
	public boolean drawInForegroundOfTab()
	{
		return drawTitle;
	}
	
	public EnumEnchantmentType[] func_111225_m()
	{
		return field_111230_s;
	}
	
	public boolean func_111226_a(EnumEnchantmentType par1EnumEnchantmentType)
	{
		if(field_111230_s == null) return false;
		else
		{
			EnumEnchantmentType[] var2 = field_111230_s;
			int var3 = var2.length;
			for(int var4 = 0; var4 < var3; ++var4)
			{
				EnumEnchantmentType var5 = var2[var4];
				if(var5 == par1EnumEnchantmentType) return true;
			}
			return false;
		}
	}
	
	public CreativeTabs func_111229_a(EnumEnchantmentType ... par1ArrayOfEnumEnchantmentType)
	{
		field_111230_s = par1ArrayOfEnumEnchantmentType;
		return this;
	}
	
	public String getBackgroundImageName()
	{
		return backgroundImageName;
	}
	
	public int getTabColumn()
	{
		return tabIndex % 6;
	}
	
	public Item getTabIconItem()
	{
		return Item.itemsList[getTabIconItemIndex()];
	}
	
	public int getTabIconItemIndex()
	{
		return 1;
	}
	
	public int getTabIndex()
	{
		return tabIndex;
	}
	
	public String getTabLabel()
	{
		return tabLabel;
	}
	
	public String getTranslatedTabLabel()
	{
		return "itemGroup." + getTabLabel();
	}
	
	public boolean isTabInFirstRow()
	{
		return tabIndex < 6;
	}
	
	public CreativeTabs setBackgroundImageName(String par1Str)
	{
		backgroundImageName = par1Str;
		return this;
	}
	
	public CreativeTabs setNoScrollbar()
	{
		hasScrollbar = false;
		return this;
	}
	
	public CreativeTabs setNoTitle()
	{
		drawTitle = false;
		return this;
	}
	
	public boolean shouldHidePlayerInventory()
	{
		return hasScrollbar;
	}
}
