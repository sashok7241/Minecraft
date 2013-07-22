package net.minecraft.src;

import java.io.IOException;
import java.util.List;

class GuiScreenTemporaryResourcePackSelectSelectionList extends GuiSlot
{
	private final ResourcePackRepository field_110511_b;
	private ResourceLocation field_110513_h;
	final GuiScreenTemporaryResourcePackSelect field_110512_a;
	
	public GuiScreenTemporaryResourcePackSelectSelectionList(GuiScreenTemporaryResourcePackSelect par1GuiScreenTemporaryResourcePackSelect, ResourcePackRepository par2ResourcePackRepository)
	{
		super(GuiScreenTemporaryResourcePackSelect.func_110344_a(par1GuiScreenTemporaryResourcePackSelect), par1GuiScreenTemporaryResourcePackSelect.width, par1GuiScreenTemporaryResourcePackSelect.height, 32, par1GuiScreenTemporaryResourcePackSelect.height - 55 + 4, 36);
		field_110512_a = par1GuiScreenTemporaryResourcePackSelect;
		field_110511_b = par2ResourcePackRepository;
		par2ResourcePackRepository.func_110611_a();
	}
	
	@Override protected void drawBackground()
	{
		field_110512_a.drawDefaultBackground();
	}
	
	@Override protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		TextureManager var6 = GuiScreenTemporaryResourcePackSelect.func_110340_f(field_110512_a).func_110434_K();
		if(par1 == 0)
		{
			try
			{
				ResourcePack var12 = field_110511_b.field_110620_b;
				PackMetadataSection var13 = (PackMetadataSection) var12.func_135058_a(field_110511_b.field_110621_c, "pack");
				if(field_110513_h == null)
				{
					field_110513_h = var6.func_110578_a("texturepackicon", new DynamicTexture(var12.func_110586_a()));
				}
				var6.func_110577_a(field_110513_h);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				par5Tessellator.startDrawingQuads();
				par5Tessellator.setColorOpaque_I(16777215);
				par5Tessellator.addVertexWithUV(par2, par3 + par4, 0.0D, 0.0D, 1.0D);
				par5Tessellator.addVertexWithUV(par2 + 32, par3 + par4, 0.0D, 1.0D, 1.0D);
				par5Tessellator.addVertexWithUV(par2 + 32, par3, 0.0D, 1.0D, 0.0D);
				par5Tessellator.addVertexWithUV(par2, par3, 0.0D, 0.0D, 0.0D);
				par5Tessellator.draw();
				field_110512_a.drawString(GuiScreenTemporaryResourcePackSelect.func_130017_g(field_110512_a), "Default", par2 + 32 + 2, par3 + 1, 16777215);
				field_110512_a.drawString(GuiScreenTemporaryResourcePackSelect.func_130016_h(field_110512_a), var13.func_110461_a(), par2 + 32 + 2, par3 + 12 + 10, 8421504);
			} catch(IOException var11)
			{
				;
			}
		} else
		{
			ResourcePackRepositoryEntry var7 = (ResourcePackRepositoryEntry) field_110511_b.func_110609_b().get(par1 - 1);
			var7.func_110518_a(var6);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			par5Tessellator.startDrawingQuads();
			par5Tessellator.setColorOpaque_I(16777215);
			par5Tessellator.addVertexWithUV(par2, par3 + par4, 0.0D, 0.0D, 1.0D);
			par5Tessellator.addVertexWithUV(par2 + 32, par3 + par4, 0.0D, 1.0D, 1.0D);
			par5Tessellator.addVertexWithUV(par2 + 32, par3, 0.0D, 1.0D, 0.0D);
			par5Tessellator.addVertexWithUV(par2, par3, 0.0D, 0.0D, 0.0D);
			par5Tessellator.draw();
			String var8 = var7.func_110515_d();
			if(var8.length() > 32)
			{
				var8 = var8.substring(0, 32).trim() + "...";
			}
			field_110512_a.drawString(GuiScreenTemporaryResourcePackSelect.func_110337_i(field_110512_a), var8, par2 + 32 + 2, par3 + 1, 16777215);
			List var9 = GuiScreenTemporaryResourcePackSelect.func_110335_j(field_110512_a).listFormattedStringToWidth(var7.func_110519_e(), 183);
			for(int var10 = 0; var10 < 2 && var10 < var9.size(); ++var10)
			{
				field_110512_a.drawString(GuiScreenTemporaryResourcePackSelect.func_110338_k(field_110512_a), (String) var9.get(var10), par2 + 32 + 2, par3 + 12 + 10 * var10, 8421504);
			}
		}
	}
	
	@Override protected void elementClicked(int par1, boolean par2)
	{
		List var3 = field_110511_b.func_110609_b();
		try
		{
			if(par1 == 0) throw new RuntimeException("This is so horrible ;D");
			field_110511_b.func_110615_a(new ResourcePackRepositoryEntry[] { (ResourcePackRepositoryEntry) var3.get(par1 - 1) });
			GuiScreenTemporaryResourcePackSelect.func_110341_b(field_110512_a).func_110436_a();
		} catch(Exception var5)
		{
			field_110511_b.func_110615_a(new ResourcePackRepositoryEntry[0]);
			GuiScreenTemporaryResourcePackSelect.func_110339_c(field_110512_a).func_110436_a();
		}
		GuiScreenTemporaryResourcePackSelect.func_110345_d(field_110512_a).gameSettings.skin = field_110511_b.func_110610_d();
		GuiScreenTemporaryResourcePackSelect.func_110334_e(field_110512_a).gameSettings.saveOptions();
	}
	
	@Override protected int getContentHeight()
	{
		return getSize() * 36;
	}
	
	@Override protected int getSize()
	{
		return 1 + field_110511_b.func_110609_b().size();
	}
	
	@Override protected boolean isSelected(int par1)
	{
		List var2 = field_110511_b.func_110613_c();
		return par1 == 0 ? var2.isEmpty() : var2.contains(field_110511_b.func_110609_b().get(par1 - 1));
	}
	
	static ResourcePackRepository func_110510_a(GuiScreenTemporaryResourcePackSelectSelectionList par0GuiScreenTemporaryResourcePackSelectSelectionList)
	{
		return par0GuiScreenTemporaryResourcePackSelectSelectionList.field_110511_b;
	}
}
