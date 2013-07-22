package net.minecraft.src;

import java.util.List;

class GuiTexturePackSlot extends GuiSlot
{
	final GuiTexturePacks parentTexturePackGui;
	
	public GuiTexturePackSlot(GuiTexturePacks p_i3026_1_)
	{
		super(GuiTexturePacks.func_73950_a(p_i3026_1_), p_i3026_1_.width, p_i3026_1_.height, 32, p_i3026_1_.height - 55 + 4, 36);
		parentTexturePackGui = p_i3026_1_;
	}
	
	@Override protected void drawBackground()
	{
		parentTexturePackGui.drawDefaultBackground();
	}
	
	@Override protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		ITexturePack var6 = (ITexturePack) GuiTexturePacks.func_96143_l(parentTexturePackGui).texturePackList.availableTexturePacks().get(par1);
		var6.bindThumbnailTexture(GuiTexturePacks.func_96142_m(parentTexturePackGui).renderEngine);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		par5Tessellator.startDrawingQuads();
		par5Tessellator.setColorOpaque_I(16777215);
		par5Tessellator.addVertexWithUV(par2, par3 + par4, 0.0D, 0.0D, 1.0D);
		par5Tessellator.addVertexWithUV(par2 + 32, par3 + par4, 0.0D, 1.0D, 1.0D);
		par5Tessellator.addVertexWithUV(par2 + 32, par3, 0.0D, 1.0D, 0.0D);
		par5Tessellator.addVertexWithUV(par2, par3, 0.0D, 0.0D, 0.0D);
		par5Tessellator.draw();
		String var7 = var6.getTexturePackFileName();
		if(!var6.isCompatible())
		{
			var7 = EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("texturePack.incompatible") + " - " + var7;
		}
		if(var7.length() > 32)
		{
			var7 = var7.substring(0, 32).trim() + "...";
		}
		parentTexturePackGui.drawString(GuiTexturePacks.func_73954_n(parentTexturePackGui), var7, par2 + 32 + 2, par3 + 1, 16777215);
		parentTexturePackGui.drawString(GuiTexturePacks.func_96145_o(parentTexturePackGui), var6.getFirstDescriptionLine(), par2 + 32 + 2, par3 + 12, 8421504);
		parentTexturePackGui.drawString(GuiTexturePacks.func_96144_p(parentTexturePackGui), var6.getSecondDescriptionLine(), par2 + 32 + 2, par3 + 12 + 10, 8421504);
	}
	
	@Override protected void elementClicked(int par1, boolean par2)
	{
		List var3 = GuiTexturePacks.func_73958_c(parentTexturePackGui).texturePackList.availableTexturePacks();
		try
		{
			GuiTexturePacks.func_73951_d(parentTexturePackGui).texturePackList.setTexturePack((ITexturePack) var3.get(par1));
			GuiTexturePacks.func_73952_e(parentTexturePackGui).renderEngine.refreshTextures();
			GuiTexturePacks.func_73962_f(parentTexturePackGui).renderGlobal.loadRenderers();
		} catch(Exception var5)
		{
			GuiTexturePacks.func_73959_g(parentTexturePackGui).texturePackList.setTexturePack((ITexturePack) var3.get(0));
			GuiTexturePacks.func_73957_h(parentTexturePackGui).renderEngine.refreshTextures();
			GuiTexturePacks.func_73956_i(parentTexturePackGui).renderGlobal.loadRenderers();
		}
	}
	
	@Override protected int getContentHeight()
	{
		return getSize() * 36;
	}
	
	@Override protected int getSize()
	{
		return GuiTexturePacks.func_73955_b(parentTexturePackGui).texturePackList.availableTexturePacks().size();
	}
	
	@Override protected boolean isSelected(int par1)
	{
		List var2 = GuiTexturePacks.func_73953_j(parentTexturePackGui).texturePackList.availableTexturePacks();
		return GuiTexturePacks.func_73961_k(parentTexturePackGui).texturePackList.getSelectedTexturePack() == var2.get(par1);
	}
}
