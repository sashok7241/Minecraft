package net.minecraft.src;


class GuiFlatPresetsListSlot extends GuiSlot
{
	public int field_82459_a;
	final GuiFlatPresets flatPresetsGui;
	
	public GuiFlatPresetsListSlot(GuiFlatPresets p_i5002_1_)
	{
		super(p_i5002_1_.mc, p_i5002_1_.width, p_i5002_1_.height, 80, p_i5002_1_.height - 37, 24);
		flatPresetsGui = p_i5002_1_;
		field_82459_a = -1;
	}
	
	@Override protected void drawBackground()
	{
	}
	
	@Override protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		GuiFlatPresetsItem var6 = (GuiFlatPresetsItem) GuiFlatPresets.getPresets().get(par1);
		func_82457_a(par2, par3, var6.iconId);
		flatPresetsGui.fontRenderer.drawString(var6.presetName, par2 + 18 + 5, par3 + 6, 16777215);
	}
	
	@Override protected void elementClicked(int par1, boolean par2)
	{
		field_82459_a = par1;
		flatPresetsGui.func_82296_g();
		GuiFlatPresets.func_82298_b(flatPresetsGui).setText(((GuiFlatPresetsItem) GuiFlatPresets.getPresets().get(GuiFlatPresets.func_82292_a(flatPresetsGui).field_82459_a)).presetData);
	}
	
	private void func_82455_b(int par1, int par2, int par3, int par4)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		flatPresetsGui.mc.renderEngine.bindTexture("/gui/slot.png");
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV(par1 + 0, par2 + 18, flatPresetsGui.zLevel, (par3 + 0) * 0.0078125F, (par4 + 18) * 0.0078125F);
		var9.addVertexWithUV(par1 + 18, par2 + 18, flatPresetsGui.zLevel, (par3 + 18) * 0.0078125F, (par4 + 18) * 0.0078125F);
		var9.addVertexWithUV(par1 + 18, par2 + 0, flatPresetsGui.zLevel, (par3 + 18) * 0.0078125F, (par4 + 0) * 0.0078125F);
		var9.addVertexWithUV(par1 + 0, par2 + 0, flatPresetsGui.zLevel, (par3 + 0) * 0.0078125F, (par4 + 0) * 0.0078125F);
		var9.draw();
	}
	
	private void func_82456_d(int par1, int par2)
	{
		func_82455_b(par1, par2, 0, 0);
	}
	
	private void func_82457_a(int par1, int par2, int par3)
	{
		func_82456_d(par1 + 1, par2 + 1);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.enableGUIStandardItemLighting();
		GuiFlatPresets.getPresetIconRenderer().renderItemIntoGUI(flatPresetsGui.fontRenderer, flatPresetsGui.mc.renderEngine, new ItemStack(par3, 1, 0), par1 + 2, par2 + 2);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}
	
	@Override protected int getSize()
	{
		return GuiFlatPresets.getPresets().size();
	}
	
	@Override protected boolean isSelected(int par1)
	{
		return par1 == field_82459_a;
	}
}
