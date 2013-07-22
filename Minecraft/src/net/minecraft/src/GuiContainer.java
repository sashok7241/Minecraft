package net.minecraft.src;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;

public abstract class GuiContainer extends GuiScreen
{
	protected static RenderItem itemRenderer = new RenderItem();
	protected int xSize = 176;
	protected int ySize = 166;
	public Container inventorySlots;
	protected int guiLeft;
	protected int guiTop;
	private Slot theSlot;
	private Slot clickedSlot = null;
	private boolean isRightMouseClick = false;
	private ItemStack draggedStack = null;
	private int field_85049_r = 0;
	private int field_85048_s = 0;
	private Slot returningStackDestSlot = null;
	private long returningStackTime = 0L;
	private ItemStack returningStack = null;
	private Slot field_92033_y = null;
	private long field_92032_z = 0L;
	protected final Set field_94077_p = new HashSet();
	protected boolean field_94076_q;
	private int field_94071_C = 0;
	private int field_94067_D = 0;
	private boolean field_94068_E = false;
	private int field_94069_F;
	private long field_94070_G = 0L;
	private Slot field_94072_H = null;
	private int field_94073_I = 0;
	private boolean field_94074_J;
	private ItemStack field_94075_K = null;
	
	public GuiContainer(Container p_i3079_1_)
	{
		inventorySlots = p_i3079_1_;
		field_94068_E = true;
	}
	
	protected boolean checkHotbarKeys(int par1)
	{
		if(mc.thePlayer.inventory.getItemStack() == null && theSlot != null)
		{
			for(int var2 = 0; var2 < 9; ++var2)
			{
				if(par1 == 2 + var2)
				{
					handleMouseClick(theSlot, theSlot.slotNumber, var2, 2);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	protected void drawCreativeTabHoveringText(String par1Str, int par2, int par3)
	{
		func_102021_a(Arrays.asList(new String[] { par1Str }), par2, par3);
	}
	
	protected abstract void drawGuiContainerBackgroundLayer(float var1, int var2, int var3);
	
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
	}
	
	private void drawItemStack(ItemStack par1ItemStack, int par2, int par3, String par4Str)
	{
		GL11.glTranslatef(0.0F, 0.0F, 32.0F);
		zLevel = 200.0F;
		itemRenderer.zLevel = 200.0F;
		itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.renderEngine, par1ItemStack, par2, par3);
		itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, par1ItemStack, par2, par3 - (draggedStack == null ? 0 : 8), par4Str);
		zLevel = 0.0F;
		itemRenderer.zLevel = 0.0F;
	}
	
	protected void drawItemStackTooltip(ItemStack par1ItemStack, int par2, int par3)
	{
		List var4 = par1ItemStack.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips);
		for(int var5 = 0; var5 < var4.size(); ++var5)
		{
			if(var5 == 0)
			{
				var4.set(var5, "\u00a7" + Integer.toHexString(par1ItemStack.getRarity().rarityColor) + (String) var4.get(var5));
			} else
			{
				var4.set(var5, EnumChatFormatting.GRAY + (String) var4.get(var5));
			}
		}
		func_102021_a(var4, par2, par3);
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		int var4 = guiLeft;
		int var5 = guiTop;
		drawGuiContainerBackgroundLayer(par3, par1, par2);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		super.drawScreen(par1, par2, par3);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glPushMatrix();
		GL11.glTranslatef((float) var4, (float) var5, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		theSlot = null;
		short var6 = 240;
		short var7 = 240;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int var9;
		for(int var13 = 0; var13 < inventorySlots.inventorySlots.size(); ++var13)
		{
			Slot var14 = (Slot) inventorySlots.inventorySlots.get(var13);
			drawSlotInventory(var14);
			if(isMouseOverSlot(var14, par1, par2))
			{
				theSlot = var14;
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				int var8 = var14.xDisplayPosition;
				var9 = var14.yDisplayPosition;
				drawGradientRect(var8, var9, var8 + 16, var9 + 16, -2130706433, -2130706433);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			}
		}
		drawGuiContainerForegroundLayer(par1, par2);
		InventoryPlayer var15 = mc.thePlayer.inventory;
		ItemStack var16 = draggedStack == null ? var15.getItemStack() : draggedStack;
		if(var16 != null)
		{
			byte var18 = 8;
			var9 = draggedStack == null ? 8 : 16;
			String var10 = null;
			if(draggedStack != null && isRightMouseClick)
			{
				var16 = var16.copy();
				var16.stackSize = MathHelper.ceiling_float_int(var16.stackSize / 2.0F);
			} else if(field_94076_q && field_94077_p.size() > 1)
			{
				var16 = var16.copy();
				var16.stackSize = field_94069_F;
				if(var16.stackSize == 0)
				{
					var10 = "" + EnumChatFormatting.YELLOW + "0";
				}
			}
			drawItemStack(var16, par1 - var4 - var18, par2 - var5 - var9, var10);
		}
		if(returningStack != null)
		{
			float var17 = (Minecraft.getSystemTime() - returningStackTime) / 100.0F;
			if(var17 >= 1.0F)
			{
				var17 = 1.0F;
				returningStack = null;
			}
			var9 = returningStackDestSlot.xDisplayPosition - field_85049_r;
			int var20 = returningStackDestSlot.yDisplayPosition - field_85048_s;
			int var11 = field_85049_r + (int) (var9 * var17);
			int var12 = field_85048_s + (int) (var20 * var17);
			drawItemStack(returningStack, var11, var12, (String) null);
		}
		GL11.glPopMatrix();
		if(var15.getItemStack() == null && theSlot != null && theSlot.getHasStack())
		{
			ItemStack var19 = theSlot.getStack();
			drawItemStackTooltip(var19, par1, par2);
		}
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();
	}
	
	private void drawSlotInventory(Slot par1Slot)
	{
		int var2 = par1Slot.xDisplayPosition;
		int var3 = par1Slot.yDisplayPosition;
		ItemStack var4 = par1Slot.getStack();
		boolean var5 = false;
		boolean var6 = par1Slot == clickedSlot && draggedStack != null && !isRightMouseClick;
		ItemStack var7 = mc.thePlayer.inventory.getItemStack();
		String var8 = null;
		if(par1Slot == clickedSlot && draggedStack != null && isRightMouseClick && var4 != null)
		{
			var4 = var4.copy();
			var4.stackSize /= 2;
		} else if(field_94076_q && field_94077_p.contains(par1Slot) && var7 != null)
		{
			if(field_94077_p.size() == 1) return;
			if(Container.func_94527_a(par1Slot, var7, true) && inventorySlots.canDragIntoSlot(par1Slot))
			{
				var4 = var7.copy();
				var5 = true;
				Container.func_94525_a(field_94077_p, field_94071_C, var4, par1Slot.getStack() == null ? 0 : par1Slot.getStack().stackSize);
				if(var4.stackSize > var4.getMaxStackSize())
				{
					var8 = EnumChatFormatting.YELLOW + "" + var4.getMaxStackSize();
					var4.stackSize = var4.getMaxStackSize();
				}
				if(var4.stackSize > par1Slot.getSlotStackLimit())
				{
					var8 = EnumChatFormatting.YELLOW + "" + par1Slot.getSlotStackLimit();
					var4.stackSize = par1Slot.getSlotStackLimit();
				}
			} else
			{
				field_94077_p.remove(par1Slot);
				func_94066_g();
			}
		}
		zLevel = 100.0F;
		itemRenderer.zLevel = 100.0F;
		if(var4 == null)
		{
			Icon var9 = par1Slot.getBackgroundIconIndex();
			if(var9 != null)
			{
				GL11.glDisable(GL11.GL_LIGHTING);
				mc.renderEngine.bindTexture("/gui/items.png");
				drawTexturedModelRectFromIcon(var2, var3, var9, 16, 16);
				GL11.glEnable(GL11.GL_LIGHTING);
				var6 = true;
			}
		}
		if(!var6)
		{
			if(var5)
			{
				drawRect(var2, var3, var2 + 16, var3 + 16, -2130706433);
			}
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.renderEngine, var4, var2, var3);
			itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, var4, var2, var3, var8);
		}
		itemRenderer.zLevel = 0.0F;
		zLevel = 0.0F;
	}
	
	protected void func_102021_a(List par1List, int par2, int par3)
	{
		if(!par1List.isEmpty())
		{
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			int var4 = 0;
			Iterator var5 = par1List.iterator();
			while(var5.hasNext())
			{
				String var6 = (String) var5.next();
				int var7 = fontRenderer.getStringWidth(var6);
				if(var7 > var4)
				{
					var4 = var7;
				}
			}
			int var14 = par2 + 12;
			int var15 = par3 - 12;
			int var8 = 8;
			if(par1List.size() > 1)
			{
				var8 += 2 + (par1List.size() - 1) * 10;
			}
			if(var14 + var4 > width)
			{
				var14 -= 28 + var4;
			}
			if(var15 + var8 + 6 > height)
			{
				var15 = height - var8 - 6;
			}
			zLevel = 300.0F;
			itemRenderer.zLevel = 300.0F;
			int var9 = -267386864;
			drawGradientRect(var14 - 3, var15 - 4, var14 + var4 + 3, var15 - 3, var9, var9);
			drawGradientRect(var14 - 3, var15 + var8 + 3, var14 + var4 + 3, var15 + var8 + 4, var9, var9);
			drawGradientRect(var14 - 3, var15 - 3, var14 + var4 + 3, var15 + var8 + 3, var9, var9);
			drawGradientRect(var14 - 4, var15 - 3, var14 - 3, var15 + var8 + 3, var9, var9);
			drawGradientRect(var14 + var4 + 3, var15 - 3, var14 + var4 + 4, var15 + var8 + 3, var9, var9);
			int var10 = 1347420415;
			int var11 = (var10 & 16711422) >> 1 | var10 & -16777216;
			drawGradientRect(var14 - 3, var15 - 3 + 1, var14 - 3 + 1, var15 + var8 + 3 - 1, var10, var11);
			drawGradientRect(var14 + var4 + 2, var15 - 3 + 1, var14 + var4 + 3, var15 + var8 + 3 - 1, var10, var11);
			drawGradientRect(var14 - 3, var15 - 3, var14 + var4 + 3, var15 - 3 + 1, var10, var10);
			drawGradientRect(var14 - 3, var15 + var8 + 2, var14 + var4 + 3, var15 + var8 + 3, var11, var11);
			for(int var12 = 0; var12 < par1List.size(); ++var12)
			{
				String var13 = (String) par1List.get(var12);
				fontRenderer.drawStringWithShadow(var13, var14, var15, -1);
				if(var12 == 0)
				{
					var15 += 2;
				}
				var15 += 10;
			}
			zLevel = 0.0F;
			itemRenderer.zLevel = 0.0F;
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			RenderHelper.enableStandardItemLighting();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		}
	}
	
	private void func_94066_g()
	{
		ItemStack var1 = mc.thePlayer.inventory.getItemStack();
		if(var1 != null && field_94076_q)
		{
			field_94069_F = var1.stackSize;
			ItemStack var4;
			int var5;
			for(Iterator var2 = field_94077_p.iterator(); var2.hasNext(); field_94069_F -= var4.stackSize - var5)
			{
				Slot var3 = (Slot) var2.next();
				var4 = var1.copy();
				var5 = var3.getStack() == null ? 0 : var3.getStack().stackSize;
				Container.func_94525_a(field_94077_p, field_94071_C, var4, var5);
				if(var4.stackSize > var4.getMaxStackSize())
				{
					var4.stackSize = var4.getMaxStackSize();
				}
				if(var4.stackSize > var3.getSlotStackLimit())
				{
					var4.stackSize = var3.getSlotStackLimit();
				}
			}
		}
	}
	
	private Slot getSlotAtPosition(int par1, int par2)
	{
		for(int var3 = 0; var3 < inventorySlots.inventorySlots.size(); ++var3)
		{
			Slot var4 = (Slot) inventorySlots.inventorySlots.get(var3);
			if(isMouseOverSlot(var4, par1, par2)) return var4;
		}
		return null;
	}
	
	protected void handleMouseClick(Slot par1Slot, int par2, int par3, int par4)
	{
		if(par1Slot != null)
		{
			par2 = par1Slot.slotNumber;
		}
		mc.playerController.windowClick(inventorySlots.windowId, par2, par3, par4, mc.thePlayer);
	}
	
	@Override public void initGui()
	{
		super.initGui();
		mc.thePlayer.openContainer = inventorySlots;
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
	}
	
	private boolean isMouseOverSlot(Slot par1Slot, int par2, int par3)
	{
		return isPointInRegion(par1Slot.xDisplayPosition, par1Slot.yDisplayPosition, 16, 16, par2, par3);
	}
	
	protected boolean isPointInRegion(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		int var7 = guiLeft;
		int var8 = guiTop;
		par5 -= var7;
		par6 -= var8;
		return par5 >= par1 - 1 && par5 < par1 + par3 + 1 && par6 >= par2 - 1 && par6 < par2 + par4 + 1;
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		if(par2 == 1 || par2 == mc.gameSettings.keyBindInventory.keyCode)
		{
			mc.thePlayer.closeScreen();
		}
		checkHotbarKeys(par2);
		if(theSlot != null && theSlot.getHasStack())
		{
			if(par2 == mc.gameSettings.keyBindPickBlock.keyCode)
			{
				handleMouseClick(theSlot, theSlot.slotNumber, 0, 3);
			} else if(par2 == mc.gameSettings.keyBindDrop.keyCode)
			{
				handleMouseClick(theSlot, theSlot.slotNumber, isCtrlKeyDown() ? 1 : 0, 4);
			}
		}
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		boolean var4 = par3 == mc.gameSettings.keyBindPickBlock.keyCode + 100;
		Slot var5 = getSlotAtPosition(par1, par2);
		long var6 = Minecraft.getSystemTime();
		field_94074_J = field_94072_H == var5 && var6 - field_94070_G < 250L && field_94073_I == par3;
		field_94068_E = false;
		if(par3 == 0 || par3 == 1 || var4)
		{
			int var8 = guiLeft;
			int var9 = guiTop;
			boolean var10 = par1 < var8 || par2 < var9 || par1 >= var8 + xSize || par2 >= var9 + ySize;
			int var11 = -1;
			if(var5 != null)
			{
				var11 = var5.slotNumber;
			}
			if(var10)
			{
				var11 = -999;
			}
			if(mc.gameSettings.touchscreen && var10 && mc.thePlayer.inventory.getItemStack() == null)
			{
				mc.displayGuiScreen((GuiScreen) null);
				return;
			}
			if(var11 != -1)
			{
				if(mc.gameSettings.touchscreen)
				{
					if(var5 != null && var5.getHasStack())
					{
						clickedSlot = var5;
						draggedStack = null;
						isRightMouseClick = par3 == 1;
					} else
					{
						clickedSlot = null;
					}
				} else if(!field_94076_q)
				{
					if(mc.thePlayer.inventory.getItemStack() == null)
					{
						if(par3 == mc.gameSettings.keyBindPickBlock.keyCode + 100)
						{
							handleMouseClick(var5, var11, par3, 3);
						} else
						{
							boolean var12 = var11 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
							byte var13 = 0;
							if(var12)
							{
								field_94075_K = var5 != null && var5.getHasStack() ? var5.getStack() : null;
								var13 = 1;
							} else if(var11 == -999)
							{
								var13 = 4;
							}
							handleMouseClick(var5, var11, par3, var13);
						}
						field_94068_E = true;
					} else
					{
						field_94076_q = true;
						field_94067_D = par3;
						field_94077_p.clear();
						if(par3 == 0)
						{
							field_94071_C = 0;
						} else if(par3 == 1)
						{
							field_94071_C = 1;
						}
					}
				}
			}
		}
		field_94072_H = var5;
		field_94070_G = var6;
		field_94073_I = par3;
	}
	
	@Override protected void mouseClickMove(int par1, int par2, int par3, long par4)
	{
		Slot var6 = getSlotAtPosition(par1, par2);
		ItemStack var7 = mc.thePlayer.inventory.getItemStack();
		if(clickedSlot != null && mc.gameSettings.touchscreen)
		{
			if(par3 == 0 || par3 == 1)
			{
				if(draggedStack == null)
				{
					if(var6 != clickedSlot)
					{
						draggedStack = clickedSlot.getStack().copy();
					}
				} else if(draggedStack.stackSize > 1 && var6 != null && Container.func_94527_a(var6, draggedStack, false))
				{
					long var8 = Minecraft.getSystemTime();
					if(field_92033_y == var6)
					{
						if(var8 - field_92032_z > 500L)
						{
							handleMouseClick(clickedSlot, clickedSlot.slotNumber, 0, 0);
							handleMouseClick(var6, var6.slotNumber, 1, 0);
							handleMouseClick(clickedSlot, clickedSlot.slotNumber, 0, 0);
							field_92032_z = var8 + 750L;
							--draggedStack.stackSize;
						}
					} else
					{
						field_92033_y = var6;
						field_92032_z = var8;
					}
				}
			}
		} else if(field_94076_q && var6 != null && var7 != null && var7.stackSize > field_94077_p.size() && Container.func_94527_a(var6, var7, true) && var6.isItemValid(var7) && inventorySlots.canDragIntoSlot(var6))
		{
			field_94077_p.add(var6);
			func_94066_g();
		}
	}
	
	@Override protected void mouseMovedOrUp(int par1, int par2, int par3)
	{
		Slot var4 = getSlotAtPosition(par1, par2);
		int var5 = guiLeft;
		int var6 = guiTop;
		boolean var7 = par1 < var5 || par2 < var6 || par1 >= var5 + xSize || par2 >= var6 + ySize;
		int var8 = -1;
		if(var4 != null)
		{
			var8 = var4.slotNumber;
		}
		if(var7)
		{
			var8 = -999;
		}
		Slot var10;
		Iterator var11;
		if(field_94074_J && var4 != null && par3 == 0 && inventorySlots.func_94530_a((ItemStack) null, var4))
		{
			if(isShiftKeyDown())
			{
				if(var4 != null && var4.inventory != null && field_94075_K != null)
				{
					var11 = inventorySlots.inventorySlots.iterator();
					while(var11.hasNext())
					{
						var10 = (Slot) var11.next();
						if(var10 != null && var10.canTakeStack(mc.thePlayer) && var10.getHasStack() && var10.inventory == var4.inventory && Container.func_94527_a(var10, field_94075_K, true))
						{
							handleMouseClick(var10, var10.slotNumber, par3, 1);
						}
					}
				}
			} else
			{
				handleMouseClick(var4, var8, par3, 6);
			}
			field_94074_J = false;
			field_94070_G = 0L;
		} else
		{
			if(field_94076_q && field_94067_D != par3)
			{
				field_94076_q = false;
				field_94077_p.clear();
				field_94068_E = true;
				return;
			}
			if(field_94068_E)
			{
				field_94068_E = false;
				return;
			}
			boolean var9;
			if(clickedSlot != null && mc.gameSettings.touchscreen)
			{
				if(par3 == 0 || par3 == 1)
				{
					if(draggedStack == null && var4 != clickedSlot)
					{
						draggedStack = clickedSlot.getStack();
					}
					var9 = Container.func_94527_a(var4, draggedStack, false);
					if(var8 != -1 && draggedStack != null && var9)
					{
						handleMouseClick(clickedSlot, clickedSlot.slotNumber, par3, 0);
						handleMouseClick(var4, var8, 0, 0);
						if(mc.thePlayer.inventory.getItemStack() != null)
						{
							handleMouseClick(clickedSlot, clickedSlot.slotNumber, par3, 0);
							field_85049_r = par1 - var5;
							field_85048_s = par2 - var6;
							returningStackDestSlot = clickedSlot;
							returningStack = draggedStack;
							returningStackTime = Minecraft.getSystemTime();
						} else
						{
							returningStack = null;
						}
					} else if(draggedStack != null)
					{
						field_85049_r = par1 - var5;
						field_85048_s = par2 - var6;
						returningStackDestSlot = clickedSlot;
						returningStack = draggedStack;
						returningStackTime = Minecraft.getSystemTime();
					}
					draggedStack = null;
					clickedSlot = null;
				}
			} else if(field_94076_q && !field_94077_p.isEmpty())
			{
				handleMouseClick((Slot) null, -999, Container.func_94534_d(0, field_94071_C), 5);
				var11 = field_94077_p.iterator();
				while(var11.hasNext())
				{
					var10 = (Slot) var11.next();
					handleMouseClick(var10, var10.slotNumber, Container.func_94534_d(1, field_94071_C), 5);
				}
				handleMouseClick((Slot) null, -999, Container.func_94534_d(2, field_94071_C), 5);
			} else if(mc.thePlayer.inventory.getItemStack() != null)
			{
				if(par3 == mc.gameSettings.keyBindPickBlock.keyCode + 100)
				{
					handleMouseClick(var4, var8, par3, 3);
				} else
				{
					var9 = var8 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
					if(var9)
					{
						field_94075_K = var4 != null && var4.getHasStack() ? var4.getStack() : null;
					}
					handleMouseClick(var4, var8, par3, var9 ? 1 : 0);
				}
			}
		}
		if(mc.thePlayer.inventory.getItemStack() == null)
		{
			field_94070_G = 0L;
		}
		field_94076_q = false;
	}
	
	@Override public void onGuiClosed()
	{
		if(mc.thePlayer != null)
		{
			inventorySlots.onContainerClosed(mc.thePlayer);
		}
	}
	
	@Override public void updateScreen()
	{
		super.updateScreen();
		if(!mc.thePlayer.isEntityAlive() || mc.thePlayer.isDead)
		{
			mc.thePlayer.closeScreen();
		}
	}
}
