package net.minecraft.src;

import java.util.List;

import net.minecraft.client.Minecraft;

public class GuiRepair extends GuiContainer implements ICrafting
{
	private ContainerRepair repairContainer;
	private GuiTextField itemNameField;
	private InventoryPlayer field_82325_q;
	
	public GuiRepair(InventoryPlayer p_i5006_1_, World p_i5006_2_, int p_i5006_3_, int p_i5006_4_, int p_i5006_5_)
	{
		super(new ContainerRepair(p_i5006_1_, p_i5006_2_, p_i5006_3_, p_i5006_4_, p_i5006_5_, Minecraft.getMinecraft().thePlayer));
		field_82325_q = p_i5006_1_;
		repairContainer = (ContainerRepair) inventorySlots;
	}
	
	@Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/gui/repair.png");
		int var4 = (width - xSize) / 2;
		int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
		drawTexturedModalRect(var4 + 59, var5 + 20, 0, ySize + (repairContainer.getSlot(0).getHasStack() ? 0 : 16), 110, 16);
		if((repairContainer.getSlot(0).getHasStack() || repairContainer.getSlot(1).getHasStack()) && !repairContainer.getSlot(2).getHasStack())
		{
			drawTexturedModalRect(var4 + 99, var5 + 45, xSize, 0, 28, 21);
		}
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		GL11.glDisable(GL11.GL_LIGHTING);
		fontRenderer.drawString(StatCollector.translateToLocal("container.repair"), 60, 6, 4210752);
		if(repairContainer.maximumCost > 0)
		{
			int var3 = 8453920;
			boolean var4 = true;
			String var5 = StatCollector.translateToLocalFormatted("container.repair.cost", new Object[] { Integer.valueOf(repairContainer.maximumCost) });
			if(repairContainer.maximumCost >= 40 && !mc.thePlayer.capabilities.isCreativeMode)
			{
				var5 = StatCollector.translateToLocal("container.repair.expensive");
				var3 = 16736352;
			} else if(!repairContainer.getSlot(2).getHasStack())
			{
				var4 = false;
			} else if(!repairContainer.getSlot(2).canTakeStack(field_82325_q.player))
			{
				var3 = 16736352;
			}
			if(var4)
			{
				int var6 = -16777216 | (var3 & 16579836) >> 2 | var3 & -16777216;
				int var7 = xSize - 8 - fontRenderer.getStringWidth(var5);
				byte var8 = 67;
				if(fontRenderer.getUnicodeFlag())
				{
					drawRect(var7 - 3, var8 - 2, xSize - 7, var8 + 10, -16777216);
					drawRect(var7 - 2, var8 - 1, xSize - 8, var8 + 9, -12895429);
				} else
				{
					fontRenderer.drawString(var5, var7, var8 + 1, var6);
					fontRenderer.drawString(var5, var7 + 1, var8, var6);
					fontRenderer.drawString(var5, var7 + 1, var8 + 1, var6);
				}
				fontRenderer.drawString(var5, var7, var8, var3);
			}
		}
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		GL11.glDisable(GL11.GL_LIGHTING);
		itemNameField.drawTextBox();
	}
	
	@Override public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		int var1 = (width - xSize) / 2;
		int var2 = (height - ySize) / 2;
		itemNameField = new GuiTextField(fontRenderer, var1 + 62, var2 + 24, 103, 12);
		itemNameField.setTextColor(-1);
		itemNameField.setDisabledTextColour(-1);
		itemNameField.setEnableBackgroundDrawing(false);
		itemNameField.setMaxStringLength(30);
		inventorySlots.removeCraftingFromCrafters(this);
		inventorySlots.addCraftingToCrafters(this);
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		if(itemNameField.textboxKeyTyped(par1, par2))
		{
			repairContainer.updateItemName(itemNameField.getText());
			mc.thePlayer.sendQueue.addToSendQueue(new Packet250CustomPayload("MC|ItemName", itemNameField.getText().getBytes()));
		} else
		{
			super.keyTyped(par1, par2);
		}
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		itemNameField.mouseClicked(par1, par2, par3);
	}
	
	@Override public void onGuiClosed()
	{
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
		inventorySlots.removeCraftingFromCrafters(this);
	}
	
	@Override public void sendContainerAndContentsToPlayer(Container p_71110_1_, List p_71110_2_)
	{
		sendSlotContents(p_71110_1_, 0, p_71110_1_.getSlot(0).getStack());
	}
	
	@Override public void sendProgressBarUpdate(Container p_71112_1_, int p_71112_2_, int p_71112_3_)
	{
	}
	
	@Override public void sendSlotContents(Container p_71111_1_, int p_71111_2_, ItemStack p_71111_3_)
	{
		if(p_71111_2_ == 0)
		{
			itemNameField.setText(p_71111_3_ == null ? "" : p_71111_3_.getDisplayName());
			itemNameField.setEnabled(p_71111_3_ != null);
			if(p_71111_3_ != null)
			{
				repairContainer.updateItemName(itemNameField.getText());
				mc.thePlayer.sendQueue.addToSendQueue(new Packet250CustomPayload("MC|ItemName", itemNameField.getText().getBytes()));
			}
		}
	}
}
