package net.minecraft.src;

import java.util.List;

public class CreativeTabs
{
	public static final CreativeTabs[] creativeTabArray = new CreativeTabs[12];
	public static final CreativeTabs tabBlock = new CreativeTabBlock(0, "buildingBlocks");
	public static final CreativeTabs tabDecorations = new CreativeTabDeco(1, "decorations");
	public static final CreativeTabs tabRedstone = new CreativeTabRedstone(2, "redstone");
	public static final CreativeTabs tabTransport = new CreativeTabTransport(3, "transportation");
	public static final CreativeTabs tabMisc = new CreativeTabMisc(4, "misc");
	public static final CreativeTabs tabAllSearch = new CreativeTabSearch(5, "search").setBackgroundImageName("search.png");
	public static final CreativeTabs tabFood = new CreativeTabFood(6, "food");
	public static final CreativeTabs tabTools = new CreativeTabTools(7, "tools");
	public static final CreativeTabs tabCombat = new CreativeTabCombat(8, "combat");
	public static final CreativeTabs tabBrewing = new CreativeTabBrewing(9, "brewing");
	public static final CreativeTabs tabMaterials = new CreativeTabMaterial(10, "materials");
	public static final CreativeTabs tabInventory = new CreativeTabInventory(11, "inventory").setBackgroundImageName("survival_inv.png").setNoScrollbar().setNoTitle();
	private final int tabIndex;
	private final String tabLabel;
	private String backgroundImageName = "list_items.png";
	private boolean hasScrollbar = true;
	private boolean drawTitle = true;
	
	public CreativeTabs(int p_i3642_1_, String p_i3642_2_)
	{
		tabIndex = p_i3642_1_;
		tabLabel = p_i3642_2_;
		creativeTabArray[p_i3642_1_] = this;
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
	}
	
	public boolean drawInForegroundOfTab()
	{
		return drawTitle;
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
		return StringTranslate.getInstance().translateKey("itemGroup." + getTabLabel());
	}
	
	public boolean isTabInFirstRow()
	{
		return tabIndex < 6;
	}
	
	public CreativeTabs setBackgroundImageName(String p_78025_1_)
	{
		backgroundImageName = p_78025_1_;
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
