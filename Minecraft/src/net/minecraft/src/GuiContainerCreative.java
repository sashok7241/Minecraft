package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GuiContainerCreative extends InventoryEffectRenderer
{
	private static final ResourceLocation field_110424_t = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
	private static InventoryBasic inventory = new InventoryBasic("tmp", true, 45);
	private static int selectedTabIndex = CreativeTabs.tabBlock.getTabIndex();
	private float currentScroll;
	private boolean isScrolling;
	private boolean wasClicking;
	private GuiTextField searchField;
	private List backupContainerSlots;
	private Slot field_74235_v;
	private boolean field_74234_w;
	private CreativeCrafting field_82324_x;
	
	public GuiContainerCreative(EntityPlayer par1EntityPlayer)
	{
		super(new ContainerCreative(par1EntityPlayer));
		par1EntityPlayer.openContainer = inventorySlots;
		allowUserInput = true;
		par1EntityPlayer.addStat(AchievementList.openInventory, 1);
		ySize = 136;
		xSize = 195;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.id == 0)
		{
			mc.displayGuiScreen(new GuiAchievements(mc.statFileWriter));
		}
		if(par1GuiButton.id == 1)
		{
			mc.displayGuiScreen(new GuiStats(this, mc.statFileWriter));
		}
	}
	
	@Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.enableGUIStandardItemLighting();
		CreativeTabs var4 = CreativeTabs.creativeTabArray[selectedTabIndex];
		CreativeTabs[] var5 = CreativeTabs.creativeTabArray;
		int var6 = var5.length;
		int var7;
		for(var7 = 0; var7 < var6; ++var7)
		{
			CreativeTabs var8 = var5[var7];
			mc.func_110434_K().func_110577_a(field_110424_t);
			if(var8.getTabIndex() != selectedTabIndex)
			{
				renderCreativeTab(var8);
			}
		}
		mc.func_110434_K().func_110577_a(new ResourceLocation("textures/gui/container/creative_inventory/tab_" + var4.getBackgroundImageName()));
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		searchField.drawTextBox();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int var9 = guiLeft + 175;
		var6 = guiTop + 18;
		var7 = var6 + 112;
		mc.func_110434_K().func_110577_a(field_110424_t);
		if(var4.shouldHidePlayerInventory())
		{
			drawTexturedModalRect(var9, var6 + (int) ((var7 - var6 - 17) * currentScroll), 232 + (needsScrollBars() ? 0 : 12), 0, 12, 15);
		}
		renderCreativeTab(var4);
		if(var4 == CreativeTabs.tabInventory)
		{
			GuiInventory.func_110423_a(guiLeft + 43, guiTop + 45, 20, guiLeft + 43 - par2, guiTop + 45 - 30 - par3, mc.thePlayer);
		}
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		CreativeTabs var3 = CreativeTabs.creativeTabArray[selectedTabIndex];
		if(var3.drawInForegroundOfTab())
		{
			fontRenderer.drawString(I18n.func_135053_a(var3.getTranslatedTabLabel()), 8, 6, 4210752);
		}
	}
	
	@Override protected void drawItemStackTooltip(ItemStack par1ItemStack, int par2, int par3)
	{
		if(selectedTabIndex == CreativeTabs.tabAllSearch.getTabIndex())
		{
			List var4 = par1ItemStack.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips);
			CreativeTabs var5 = par1ItemStack.getItem().getCreativeTab();
			if(var5 == null && par1ItemStack.itemID == Item.enchantedBook.itemID)
			{
				Map var6 = EnchantmentHelper.getEnchantments(par1ItemStack);
				if(var6.size() == 1)
				{
					Enchantment var7 = Enchantment.enchantmentsList[((Integer) var6.keySet().iterator().next()).intValue()];
					CreativeTabs[] var8 = CreativeTabs.creativeTabArray;
					int var9 = var8.length;
					for(int var10 = 0; var10 < var9; ++var10)
					{
						CreativeTabs var11 = var8[var10];
						if(var11.func_111226_a(var7.type))
						{
							var5 = var11;
							break;
						}
					}
				}
			}
			if(var5 != null)
			{
				var4.add(1, "" + EnumChatFormatting.BOLD + EnumChatFormatting.BLUE + I18n.func_135053_a(var5.getTranslatedTabLabel()));
			}
			for(int var12 = 0; var12 < var4.size(); ++var12)
			{
				if(var12 == 0)
				{
					var4.set(var12, "\u00a7" + Integer.toHexString(par1ItemStack.getRarity().rarityColor) + (String) var4.get(var12));
				} else
				{
					var4.set(var12, EnumChatFormatting.GRAY + (String) var4.get(var12));
				}
			}
			func_102021_a(var4, par2, par3);
		} else
		{
			super.drawItemStackTooltip(par1ItemStack, par2, par3);
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		boolean var4 = Mouse.isButtonDown(0);
		int var5 = guiLeft;
		int var6 = guiTop;
		int var7 = var5 + 175;
		int var8 = var6 + 18;
		int var9 = var7 + 14;
		int var10 = var8 + 112;
		if(!wasClicking && var4 && par1 >= var7 && par2 >= var8 && par1 < var9 && par2 < var10)
		{
			isScrolling = needsScrollBars();
		}
		if(!var4)
		{
			isScrolling = false;
		}
		wasClicking = var4;
		if(isScrolling)
		{
			currentScroll = (par2 - var8 - 7.5F) / (var10 - var8 - 15.0F);
			if(currentScroll < 0.0F)
			{
				currentScroll = 0.0F;
			}
			if(currentScroll > 1.0F)
			{
				currentScroll = 1.0F;
			}
			((ContainerCreative) inventorySlots).scrollTo(currentScroll);
		}
		super.drawScreen(par1, par2, par3);
		CreativeTabs[] var11 = CreativeTabs.creativeTabArray;
		int var12 = var11.length;
		for(int var13 = 0; var13 < var12; ++var13)
		{
			CreativeTabs var14 = var11[var13];
			if(renderCreativeInventoryHoveringText(var14, par1, par2))
			{
				break;
			}
		}
		if(field_74235_v != null && selectedTabIndex == CreativeTabs.tabInventory.getTabIndex() && isPointInRegion(field_74235_v.xDisplayPosition, field_74235_v.yDisplayPosition, 16, 16, par1, par2))
		{
			drawCreativeTabHoveringText(I18n.func_135053_a("inventory.binSlot"), par1, par2);
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
	}
	
	protected boolean func_74232_a(CreativeTabs par1CreativeTabs, int par2, int par3)
	{
		int var4 = par1CreativeTabs.getTabColumn();
		int var5 = 28 * var4;
		byte var6 = 0;
		if(var4 == 5)
		{
			var5 = xSize - 28 + 2;
		} else if(var4 > 0)
		{
			var5 += var4;
		}
		int var7;
		if(par1CreativeTabs.isTabInFirstRow())
		{
			var7 = var6 - 32;
		} else
		{
			var7 = var6 + ySize;
		}
		return par2 >= var5 && par2 <= var5 + 28 && par3 >= var7 && par3 <= var7 + 32;
	}
	
	public int getCurrentTabIndex()
	{
		return selectedTabIndex;
	}
	
	@Override protected void handleMouseClick(Slot par1Slot, int par2, int par3, int par4)
	{
		field_74234_w = true;
		boolean var5 = par4 == 1;
		par4 = par2 == -999 && par4 == 0 ? 4 : par4;
		ItemStack var7;
		InventoryPlayer var11;
		if(par1Slot == null && selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && par4 != 5)
		{
			var11 = mc.thePlayer.inventory;
			if(var11.getItemStack() != null)
			{
				if(par3 == 0)
				{
					mc.thePlayer.dropPlayerItem(var11.getItemStack());
					mc.playerController.func_78752_a(var11.getItemStack());
					var11.setItemStack((ItemStack) null);
				}
				if(par3 == 1)
				{
					var7 = var11.getItemStack().splitStack(1);
					mc.thePlayer.dropPlayerItem(var7);
					mc.playerController.func_78752_a(var7);
					if(var11.getItemStack().stackSize == 0)
					{
						var11.setItemStack((ItemStack) null);
					}
				}
			}
		} else
		{
			int var10;
			if(par1Slot == field_74235_v && var5)
			{
				for(var10 = 0; var10 < mc.thePlayer.inventoryContainer.getInventory().size(); ++var10)
				{
					mc.playerController.sendSlotPacket((ItemStack) null, var10);
				}
			} else
			{
				ItemStack var6;
				if(selectedTabIndex == CreativeTabs.tabInventory.getTabIndex())
				{
					if(par1Slot == field_74235_v)
					{
						mc.thePlayer.inventory.setItemStack((ItemStack) null);
					} else if(par4 == 4 && par1Slot != null && par1Slot.getHasStack())
					{
						var6 = par1Slot.decrStackSize(par3 == 0 ? 1 : par1Slot.getStack().getMaxStackSize());
						mc.thePlayer.dropPlayerItem(var6);
						mc.playerController.func_78752_a(var6);
					} else if(par4 == 4 && mc.thePlayer.inventory.getItemStack() != null)
					{
						mc.thePlayer.dropPlayerItem(mc.thePlayer.inventory.getItemStack());
						mc.playerController.func_78752_a(mc.thePlayer.inventory.getItemStack());
						mc.thePlayer.inventory.setItemStack((ItemStack) null);
					} else
					{
						mc.thePlayer.inventoryContainer.slotClick(par1Slot == null ? par2 : SlotCreativeInventory.func_75240_a((SlotCreativeInventory) par1Slot).slotNumber, par3, par4, mc.thePlayer);
						mc.thePlayer.inventoryContainer.detectAndSendChanges();
					}
				} else if(par4 != 5 && par1Slot.inventory == inventory)
				{
					var11 = mc.thePlayer.inventory;
					var7 = var11.getItemStack();
					ItemStack var8 = par1Slot.getStack();
					ItemStack var9;
					if(par4 == 2)
					{
						if(var8 != null && par3 >= 0 && par3 < 9)
						{
							var9 = var8.copy();
							var9.stackSize = var9.getMaxStackSize();
							mc.thePlayer.inventory.setInventorySlotContents(par3, var9);
							mc.thePlayer.inventoryContainer.detectAndSendChanges();
						}
						return;
					}
					if(par4 == 3)
					{
						if(var11.getItemStack() == null && par1Slot.getHasStack())
						{
							var9 = par1Slot.getStack().copy();
							var9.stackSize = var9.getMaxStackSize();
							var11.setItemStack(var9);
						}
						return;
					}
					if(par4 == 4)
					{
						if(var8 != null)
						{
							var9 = var8.copy();
							var9.stackSize = par3 == 0 ? 1 : var9.getMaxStackSize();
							mc.thePlayer.dropPlayerItem(var9);
							mc.playerController.func_78752_a(var9);
						}
						return;
					}
					if(var7 != null && var8 != null && var7.isItemEqual(var8))
					{
						if(par3 == 0)
						{
							if(var5)
							{
								var7.stackSize = var7.getMaxStackSize();
							} else if(var7.stackSize < var7.getMaxStackSize())
							{
								++var7.stackSize;
							}
						} else if(var7.stackSize <= 1)
						{
							var11.setItemStack((ItemStack) null);
						} else
						{
							--var7.stackSize;
						}
					} else if(var8 != null && var7 == null)
					{
						var11.setItemStack(ItemStack.copyItemStack(var8));
						var7 = var11.getItemStack();
						if(var5)
						{
							var7.stackSize = var7.getMaxStackSize();
						}
					} else
					{
						var11.setItemStack((ItemStack) null);
					}
				} else
				{
					inventorySlots.slotClick(par1Slot == null ? par2 : par1Slot.slotNumber, par3, par4, mc.thePlayer);
					if(Container.func_94532_c(par3) == 2)
					{
						for(var10 = 0; var10 < 9; ++var10)
						{
							mc.playerController.sendSlotPacket(inventorySlots.getSlot(45 + var10).getStack(), 36 + var10);
						}
					} else if(par1Slot != null)
					{
						var6 = inventorySlots.getSlot(par1Slot.slotNumber).getStack();
						mc.playerController.sendSlotPacket(var6, par1Slot.slotNumber - inventorySlots.inventorySlots.size() + 9 + 36);
					}
				}
			}
		}
	}
	
	@Override public void handleMouseInput()
	{
		super.handleMouseInput();
		int var1 = Mouse.getEventDWheel();
		if(var1 != 0 && needsScrollBars())
		{
			int var2 = ((ContainerCreative) inventorySlots).itemList.size() / 9 - 5 + 1;
			if(var1 > 0)
			{
				var1 = 1;
			}
			if(var1 < 0)
			{
				var1 = -1;
			}
			currentScroll = (float) (currentScroll - (double) var1 / (double) var2);
			if(currentScroll < 0.0F)
			{
				currentScroll = 0.0F;
			}
			if(currentScroll > 1.0F)
			{
				currentScroll = 1.0F;
			}
			((ContainerCreative) inventorySlots).scrollTo(currentScroll);
		}
	}
	
	@Override public void initGui()
	{
		if(mc.playerController.isInCreativeMode())
		{
			super.initGui();
			buttonList.clear();
			Keyboard.enableRepeatEvents(true);
			searchField = new GuiTextField(fontRenderer, guiLeft + 82, guiTop + 6, 89, fontRenderer.FONT_HEIGHT);
			searchField.setMaxStringLength(15);
			searchField.setEnableBackgroundDrawing(false);
			searchField.setVisible(false);
			searchField.setTextColor(16777215);
			int var1 = selectedTabIndex;
			selectedTabIndex = -1;
			setCurrentCreativeTab(CreativeTabs.creativeTabArray[var1]);
			field_82324_x = new CreativeCrafting(mc);
			mc.thePlayer.inventoryContainer.addCraftingToCrafters(field_82324_x);
		} else
		{
			mc.displayGuiScreen(new GuiInventory(mc.thePlayer));
		}
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		if(selectedTabIndex != CreativeTabs.tabAllSearch.getTabIndex())
		{
			if(GameSettings.isKeyDown(mc.gameSettings.keyBindChat))
			{
				setCurrentCreativeTab(CreativeTabs.tabAllSearch);
			} else
			{
				super.keyTyped(par1, par2);
			}
		} else
		{
			if(field_74234_w)
			{
				field_74234_w = false;
				searchField.setText("");
			}
			if(!checkHotbarKeys(par2))
			{
				if(searchField.textboxKeyTyped(par1, par2))
				{
					updateCreativeSearch();
				} else
				{
					super.keyTyped(par1, par2);
				}
			}
		}
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		if(par3 == 0)
		{
			int var4 = par1 - guiLeft;
			int var5 = par2 - guiTop;
			CreativeTabs[] var6 = CreativeTabs.creativeTabArray;
			int var7 = var6.length;
			for(int var8 = 0; var8 < var7; ++var8)
			{
				CreativeTabs var9 = var6[var8];
				if(func_74232_a(var9, var4, var5)) return;
			}
		}
		super.mouseClicked(par1, par2, par3);
	}
	
	@Override protected void mouseMovedOrUp(int par1, int par2, int par3)
	{
		if(par3 == 0)
		{
			int var4 = par1 - guiLeft;
			int var5 = par2 - guiTop;
			CreativeTabs[] var6 = CreativeTabs.creativeTabArray;
			int var7 = var6.length;
			for(int var8 = 0; var8 < var7; ++var8)
			{
				CreativeTabs var9 = var6[var8];
				if(func_74232_a(var9, var4, var5))
				{
					setCurrentCreativeTab(var9);
					return;
				}
			}
		}
		super.mouseMovedOrUp(par1, par2, par3);
	}
	
	private boolean needsScrollBars()
	{
		return selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && CreativeTabs.creativeTabArray[selectedTabIndex].shouldHidePlayerInventory() && ((ContainerCreative) inventorySlots).hasMoreThan1PageOfItemsInList();
	}
	
	@Override public void onGuiClosed()
	{
		super.onGuiClosed();
		if(mc.thePlayer != null && mc.thePlayer.inventory != null)
		{
			mc.thePlayer.inventoryContainer.removeCraftingFromCrafters(field_82324_x);
		}
		Keyboard.enableRepeatEvents(false);
	}
	
	protected boolean renderCreativeInventoryHoveringText(CreativeTabs par1CreativeTabs, int par2, int par3)
	{
		int var4 = par1CreativeTabs.getTabColumn();
		int var5 = 28 * var4;
		byte var6 = 0;
		if(var4 == 5)
		{
			var5 = xSize - 28 + 2;
		} else if(var4 > 0)
		{
			var5 += var4;
		}
		int var7;
		if(par1CreativeTabs.isTabInFirstRow())
		{
			var7 = var6 - 32;
		} else
		{
			var7 = var6 + ySize;
		}
		if(isPointInRegion(var5 + 3, var7 + 3, 23, 27, par2, par3))
		{
			drawCreativeTabHoveringText(I18n.func_135053_a(par1CreativeTabs.getTranslatedTabLabel()), par2, par3);
			return true;
		} else return false;
	}
	
	protected void renderCreativeTab(CreativeTabs par1CreativeTabs)
	{
		boolean var2 = par1CreativeTabs.getTabIndex() == selectedTabIndex;
		boolean var3 = par1CreativeTabs.isTabInFirstRow();
		int var4 = par1CreativeTabs.getTabColumn();
		int var5 = var4 * 28;
		int var6 = 0;
		int var7 = guiLeft + 28 * var4;
		int var8 = guiTop;
		byte var9 = 32;
		if(var2)
		{
			var6 += 32;
		}
		if(var4 == 5)
		{
			var7 = guiLeft + xSize - 28;
		} else if(var4 > 0)
		{
			var7 += var4;
		}
		if(var3)
		{
			var8 -= 28;
		} else
		{
			var6 += 64;
			var8 += ySize - 4;
		}
		GL11.glDisable(GL11.GL_LIGHTING);
		drawTexturedModalRect(var7, var8, var5, var6, 28, var9);
		zLevel = 100.0F;
		itemRenderer.zLevel = 100.0F;
		var7 += 6;
		var8 += 8 + (var3 ? 1 : -1);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		ItemStack var10 = new ItemStack(par1CreativeTabs.getTabIconItem());
		itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.func_110434_K(), var10, var7, var8);
		itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.func_110434_K(), var10, var7, var8);
		GL11.glDisable(GL11.GL_LIGHTING);
		itemRenderer.zLevel = 0.0F;
		zLevel = 0.0F;
	}
	
	private void setCurrentCreativeTab(CreativeTabs par1CreativeTabs)
	{
		int var2 = selectedTabIndex;
		selectedTabIndex = par1CreativeTabs.getTabIndex();
		ContainerCreative var3 = (ContainerCreative) inventorySlots;
		field_94077_p.clear();
		var3.itemList.clear();
		par1CreativeTabs.displayAllReleventItems(var3.itemList);
		if(par1CreativeTabs == CreativeTabs.tabInventory)
		{
			Container var4 = mc.thePlayer.inventoryContainer;
			if(backupContainerSlots == null)
			{
				backupContainerSlots = var3.inventorySlots;
			}
			var3.inventorySlots = new ArrayList();
			for(int var5 = 0; var5 < var4.inventorySlots.size(); ++var5)
			{
				SlotCreativeInventory var6 = new SlotCreativeInventory(this, (Slot) var4.inventorySlots.get(var5), var5);
				var3.inventorySlots.add(var6);
				int var7;
				int var8;
				int var9;
				if(var5 >= 5 && var5 < 9)
				{
					var7 = var5 - 5;
					var8 = var7 / 2;
					var9 = var7 % 2;
					var6.xDisplayPosition = 9 + var8 * 54;
					var6.yDisplayPosition = 6 + var9 * 27;
				} else if(var5 >= 0 && var5 < 5)
				{
					var6.yDisplayPosition = -2000;
					var6.xDisplayPosition = -2000;
				} else if(var5 < var4.inventorySlots.size())
				{
					var7 = var5 - 9;
					var8 = var7 % 9;
					var9 = var7 / 9;
					var6.xDisplayPosition = 9 + var8 * 18;
					if(var5 >= 36)
					{
						var6.yDisplayPosition = 112;
					} else
					{
						var6.yDisplayPosition = 54 + var9 * 18;
					}
				}
			}
			field_74235_v = new Slot(inventory, 0, 173, 112);
			var3.inventorySlots.add(field_74235_v);
		} else if(var2 == CreativeTabs.tabInventory.getTabIndex())
		{
			var3.inventorySlots = backupContainerSlots;
			backupContainerSlots = null;
		}
		if(searchField != null)
		{
			if(par1CreativeTabs == CreativeTabs.tabAllSearch)
			{
				searchField.setVisible(true);
				searchField.setCanLoseFocus(false);
				searchField.setFocused(true);
				searchField.setText("");
				updateCreativeSearch();
			} else
			{
				searchField.setVisible(false);
				searchField.setCanLoseFocus(true);
				searchField.setFocused(false);
			}
		}
		currentScroll = 0.0F;
		var3.scrollTo(0.0F);
	}
	
	private void updateCreativeSearch()
	{
		ContainerCreative var1 = (ContainerCreative) inventorySlots;
		var1.itemList.clear();
		Item[] var2 = Item.itemsList;
		int var3 = var2.length;
		int var4;
		for(var4 = 0; var4 < var3; ++var4)
		{
			Item var5 = var2[var4];
			if(var5 != null && var5.getCreativeTab() != null)
			{
				var5.getSubItems(var5.itemID, (CreativeTabs) null, var1.itemList);
			}
		}
		Enchantment[] var8 = Enchantment.enchantmentsList;
		var3 = var8.length;
		for(var4 = 0; var4 < var3; ++var4)
		{
			Enchantment var12 = var8[var4];
			if(var12 != null && var12.type != null)
			{
				Item.enchantedBook.func_92113_a(var12, var1.itemList);
			}
		}
		Iterator var9 = var1.itemList.iterator();
		String var10 = searchField.getText().toLowerCase();
		while(var9.hasNext())
		{
			ItemStack var11 = (ItemStack) var9.next();
			boolean var13 = false;
			Iterator var6 = var11.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips).iterator();
			while(true)
			{
				if(var6.hasNext())
				{
					String var7 = (String) var6.next();
					if(!var7.toLowerCase().contains(var10))
					{
						continue;
					}
					var13 = true;
				}
				if(!var13)
				{
					var9.remove();
				}
				break;
			}
		}
		currentScroll = 0.0F;
		var1.scrollTo(0.0F);
	}
	
	@Override public void updateScreen()
	{
		if(!mc.playerController.isInCreativeMode())
		{
			mc.displayGuiScreen(new GuiInventory(mc.thePlayer));
		}
	}
	
	static InventoryBasic getInventory()
	{
		return inventory;
	}
}
