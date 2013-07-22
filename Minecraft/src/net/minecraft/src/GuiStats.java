package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class GuiStats extends GuiScreen
{
	private static RenderItem renderItem = new RenderItem();
	protected GuiScreen parentGui;
	protected String statsTitle = "Select world";
	private GuiSlotStatsGeneral slotGeneral;
	private GuiSlotStatsItem slotItem;
	private GuiSlotStatsBlock slotBlock;
	private StatFileWriter statFileWriter;
	private GuiSlot selectedSlot;
	
	public GuiStats(GuiScreen par1GuiScreen, StatFileWriter par2StatFileWriter)
	{
		parentGui = par1GuiScreen;
		statFileWriter = par2StatFileWriter;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 0)
			{
				mc.displayGuiScreen(parentGui);
			} else if(par1GuiButton.id == 1)
			{
				selectedSlot = slotGeneral;
			} else if(par1GuiButton.id == 3)
			{
				selectedSlot = slotItem;
			} else if(par1GuiButton.id == 2)
			{
				selectedSlot = slotBlock;
			} else
			{
				selectedSlot.actionPerformed(par1GuiButton);
			}
		}
	}
	
	public void addHeaderButtons()
	{
		buttonList.add(new GuiButton(0, width / 2 + 4, height - 28, 150, 20, I18n.func_135053_a("gui.done")));
		buttonList.add(new GuiButton(1, width / 2 - 154, height - 52, 100, 20, I18n.func_135053_a("stat.generalButton")));
		GuiButton var1;
		buttonList.add(var1 = new GuiButton(2, width / 2 - 46, height - 52, 100, 20, I18n.func_135053_a("stat.blocksButton")));
		GuiButton var2;
		buttonList.add(var2 = new GuiButton(3, width / 2 + 62, height - 52, 100, 20, I18n.func_135053_a("stat.itemsButton")));
		if(slotBlock.getSize() == 0)
		{
			var1.enabled = false;
		}
		if(slotItem.getSize() == 0)
		{
			var2.enabled = false;
		}
	}
	
	private void drawButtonBackground(int par1, int par2)
	{
		this.drawSprite(par1, par2, 0, 0);
	}
	
	private void drawItemSprite(int par1, int par2, int par3)
	{
		drawButtonBackground(par1 + 1, par2 + 1);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.enableGUIStandardItemLighting();
		renderItem.renderItemIntoGUI(fontRenderer, mc.func_110434_K(), new ItemStack(par3, 1, 0), par1 + 2, par2 + 2);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		selectedSlot.drawScreen(par1, par2, par3);
		drawCenteredString(fontRenderer, statsTitle, width / 2, 20, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	
	private void drawSprite(int par1, int par2, int par3, int par4)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.func_110434_K().func_110577_a(field_110323_l);
		float var5 = 0.0078125F;
		float var6 = 0.0078125F;
		boolean var7 = true;
		boolean var8 = true;
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV(par1 + 0, par2 + 18, zLevel, (par3 + 0) * 0.0078125F, (par4 + 18) * 0.0078125F);
		var9.addVertexWithUV(par1 + 18, par2 + 18, zLevel, (par3 + 18) * 0.0078125F, (par4 + 18) * 0.0078125F);
		var9.addVertexWithUV(par1 + 18, par2 + 0, zLevel, (par3 + 18) * 0.0078125F, (par4 + 0) * 0.0078125F);
		var9.addVertexWithUV(par1 + 0, par2 + 0, zLevel, (par3 + 0) * 0.0078125F, (par4 + 0) * 0.0078125F);
		var9.draw();
	}
	
	@Override public void initGui()
	{
		statsTitle = I18n.func_135053_a("gui.stats");
		slotGeneral = new GuiSlotStatsGeneral(this);
		slotGeneral.registerScrollButtons(1, 1);
		slotItem = new GuiSlotStatsItem(this);
		slotItem.registerScrollButtons(1, 1);
		slotBlock = new GuiSlotStatsBlock(this);
		slotBlock.registerScrollButtons(1, 1);
		selectedSlot = slotGeneral;
		addHeaderButtons();
	}
	
	static void drawGradientRect(GuiStats par0GuiStats, int par1, int par2, int par3, int par4, int par5, int par6)
	{
		par0GuiStats.drawGradientRect(par1, par2, par3, par4, par5, par6);
	}
	
	static void drawGradientRect1(GuiStats par0GuiStats, int par1, int par2, int par3, int par4, int par5, int par6)
	{
		par0GuiStats.drawGradientRect(par1, par2, par3, par4, par5, par6);
	}
	
	static void drawItemSprite(GuiStats par0GuiStats, int par1, int par2, int par3)
	{
		par0GuiStats.drawItemSprite(par1, par2, par3);
	}
	
	static void drawSprite(GuiStats par0GuiStats, int par1, int par2, int par3, int par4)
	{
		par0GuiStats.drawSprite(par1, par2, par3, par4);
	}
	
	static FontRenderer getFontRenderer1(GuiStats par0GuiStats)
	{
		return par0GuiStats.fontRenderer;
	}
	
	static FontRenderer getFontRenderer10(GuiStats par0GuiStats)
	{
		return par0GuiStats.fontRenderer;
	}
	
	static FontRenderer getFontRenderer11(GuiStats par0GuiStats)
	{
		return par0GuiStats.fontRenderer;
	}
	
	static FontRenderer getFontRenderer2(GuiStats par0GuiStats)
	{
		return par0GuiStats.fontRenderer;
	}
	
	static FontRenderer getFontRenderer3(GuiStats par0GuiStats)
	{
		return par0GuiStats.fontRenderer;
	}
	
	static FontRenderer getFontRenderer4(GuiStats par0GuiStats)
	{
		return par0GuiStats.fontRenderer;
	}
	
	static FontRenderer getFontRenderer5(GuiStats par0GuiStats)
	{
		return par0GuiStats.fontRenderer;
	}
	
	static FontRenderer getFontRenderer6(GuiStats par0GuiStats)
	{
		return par0GuiStats.fontRenderer;
	}
	
	static FontRenderer getFontRenderer7(GuiStats par0GuiStats)
	{
		return par0GuiStats.fontRenderer;
	}
	
	static FontRenderer getFontRenderer8(GuiStats par0GuiStats)
	{
		return par0GuiStats.fontRenderer;
	}
	
	static FontRenderer getFontRenderer9(GuiStats par0GuiStats)
	{
		return par0GuiStats.fontRenderer;
	}
	
	static Minecraft getMinecraft(GuiStats par0GuiStats)
	{
		return par0GuiStats.mc;
	}
	
	static Minecraft getMinecraft1(GuiStats par0GuiStats)
	{
		return par0GuiStats.mc;
	}
	
	static Minecraft getMinecraft2(GuiStats par0GuiStats)
	{
		return par0GuiStats.mc;
	}
	
	static StatFileWriter getStatsFileWriter(GuiStats par0GuiStats)
	{
		return par0GuiStats.statFileWriter;
	}
}
