package net.minecraft.src;

import java.util.Date;

class GuiWorldSlot extends GuiSlot
{
	final GuiSelectWorld parentWorldGui;
	
	public GuiWorldSlot(GuiSelectWorld par1GuiSelectWorld)
	{
		super(par1GuiSelectWorld.mc, par1GuiSelectWorld.width, par1GuiSelectWorld.height, 32, par1GuiSelectWorld.height - 64, 36);
		parentWorldGui = par1GuiSelectWorld;
	}
	
	@Override protected void drawBackground()
	{
		parentWorldGui.drawDefaultBackground();
	}
	
	@Override protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		SaveFormatComparator var6 = (SaveFormatComparator) GuiSelectWorld.getSize(parentWorldGui).get(par1);
		String var7 = var6.getDisplayName();
		if(var7 == null || MathHelper.stringNullOrLengthZero(var7))
		{
			var7 = GuiSelectWorld.func_82313_g(parentWorldGui) + " " + (par1 + 1);
		}
		String var8 = var6.getFileName();
		var8 = var8 + " (" + GuiSelectWorld.func_82315_h(parentWorldGui).format(new Date(var6.getLastTimePlayed()));
		var8 = var8 + ")";
		String var9 = "";
		if(var6.requiresConversion())
		{
			var9 = GuiSelectWorld.func_82311_i(parentWorldGui) + " " + var9;
		} else
		{
			var9 = GuiSelectWorld.func_82314_j(parentWorldGui)[var6.getEnumGameType().getID()];
			if(var6.isHardcoreModeEnabled())
			{
				var9 = EnumChatFormatting.DARK_RED + I18n.func_135053_a("gameMode.hardcore") + EnumChatFormatting.RESET;
			}
			if(var6.getCheatsEnabled())
			{
				var9 = var9 + ", " + I18n.func_135053_a("selectWorld.cheats");
			}
		}
		parentWorldGui.drawString(parentWorldGui.fontRenderer, var7, par2 + 2, par3 + 1, 16777215);
		parentWorldGui.drawString(parentWorldGui.fontRenderer, var8, par2 + 2, par3 + 12, 8421504);
		parentWorldGui.drawString(parentWorldGui.fontRenderer, var9, par2 + 2, par3 + 12 + 10, 8421504);
	}
	
	@Override protected void elementClicked(int par1, boolean par2)
	{
		GuiSelectWorld.onElementSelected(parentWorldGui, par1);
		boolean var3 = GuiSelectWorld.getSelectedWorld(parentWorldGui) >= 0 && GuiSelectWorld.getSelectedWorld(parentWorldGui) < getSize();
		GuiSelectWorld.getSelectButton(parentWorldGui).enabled = var3;
		GuiSelectWorld.getRenameButton(parentWorldGui).enabled = var3;
		GuiSelectWorld.getDeleteButton(parentWorldGui).enabled = var3;
		GuiSelectWorld.func_82312_f(parentWorldGui).enabled = var3;
		if(par2 && var3)
		{
			parentWorldGui.selectWorld(par1);
		}
	}
	
	@Override protected int getContentHeight()
	{
		return GuiSelectWorld.getSize(parentWorldGui).size() * 36;
	}
	
	@Override protected int getSize()
	{
		return GuiSelectWorld.getSize(parentWorldGui).size();
	}
	
	@Override protected boolean isSelected(int par1)
	{
		return par1 == GuiSelectWorld.getSelectedWorld(parentWorldGui);
	}
}
