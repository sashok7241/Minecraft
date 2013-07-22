package net.minecraft.src;


class GuiCreateFlatWorldListSlot extends GuiSlot
{
	public int field_82454_a;
	final GuiCreateFlatWorld createFlatWorldGui;
	
	public GuiCreateFlatWorldListSlot(GuiCreateFlatWorld p_i5005_1_)
	{
		super(p_i5005_1_.mc, p_i5005_1_.width, p_i5005_1_.height, 43, p_i5005_1_.height - 60, 24);
		createFlatWorldGui = p_i5005_1_;
		field_82454_a = -1;
	}
	
	@Override protected void drawBackground()
	{
	}
	
	@Override protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		FlatLayerInfo var6 = (FlatLayerInfo) GuiCreateFlatWorld.func_82271_a(createFlatWorldGui).getFlatLayers().get(GuiCreateFlatWorld.func_82271_a(createFlatWorldGui).getFlatLayers().size() - par1 - 1);
		ItemStack var7 = var6.getFillBlock() == 0 ? null : new ItemStack(var6.getFillBlock(), 1, var6.getFillBlockMeta());
		String var8 = var7 == null ? "Air" : Item.itemsList[var6.getFillBlock()].getItemStackDisplayName(var7);
		func_82452_a(par2, par3, var7);
		createFlatWorldGui.fontRenderer.drawString(var8, par2 + 18 + 5, par3 + 3, 16777215);
		String var9;
		if(par1 == 0)
		{
			var9 = StatCollector.translateToLocalFormatted("createWorld.customize.flat.layer.top", new Object[] { Integer.valueOf(var6.getLayerCount()) });
		} else if(par1 == GuiCreateFlatWorld.func_82271_a(createFlatWorldGui).getFlatLayers().size() - 1)
		{
			var9 = StatCollector.translateToLocalFormatted("createWorld.customize.flat.layer.bottom", new Object[] { Integer.valueOf(var6.getLayerCount()) });
		} else
		{
			var9 = StatCollector.translateToLocalFormatted("createWorld.customize.flat.layer", new Object[] { Integer.valueOf(var6.getLayerCount()) });
		}
		createFlatWorldGui.fontRenderer.drawString(var9, par2 + 2 + 213 - createFlatWorldGui.fontRenderer.getStringWidth(var9), par3 + 3, 16777215);
	}
	
	@Override protected void elementClicked(int par1, boolean par2)
	{
		field_82454_a = par1;
		createFlatWorldGui.func_82270_g();
	}
	
	private void func_82450_b(int par1, int par2, int par3, int par4)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		createFlatWorldGui.mc.renderEngine.bindTexture("/gui/slot.png");
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV(par1 + 0, par2 + 18, createFlatWorldGui.zLevel, (par3 + 0) * 0.0078125F, (par4 + 18) * 0.0078125F);
		var9.addVertexWithUV(par1 + 18, par2 + 18, createFlatWorldGui.zLevel, (par3 + 18) * 0.0078125F, (par4 + 18) * 0.0078125F);
		var9.addVertexWithUV(par1 + 18, par2 + 0, createFlatWorldGui.zLevel, (par3 + 18) * 0.0078125F, (par4 + 0) * 0.0078125F);
		var9.addVertexWithUV(par1 + 0, par2 + 0, createFlatWorldGui.zLevel, (par3 + 0) * 0.0078125F, (par4 + 0) * 0.0078125F);
		var9.draw();
	}
	
	private void func_82451_d(int par1, int par2)
	{
		func_82450_b(par1, par2, 0, 0);
	}
	
	private void func_82452_a(int par1, int par2, ItemStack par3ItemStack)
	{
		func_82451_d(par1 + 1, par2 + 1);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		if(par3ItemStack != null)
		{
			RenderHelper.enableGUIStandardItemLighting();
			GuiCreateFlatWorld.getRenderItem().renderItemIntoGUI(createFlatWorldGui.fontRenderer, createFlatWorldGui.mc.renderEngine, par3ItemStack, par1 + 2, par2 + 2);
			RenderHelper.disableStandardItemLighting();
		}
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}
	
	@Override protected int getScrollBarX()
	{
		return createFlatWorldGui.width - 70;
	}
	
	@Override protected int getSize()
	{
		return GuiCreateFlatWorld.func_82271_a(createFlatWorldGui).getFlatLayers().size();
	}
	
	@Override protected boolean isSelected(int par1)
	{
		return par1 == field_82454_a;
	}
}
