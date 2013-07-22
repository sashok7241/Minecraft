package net.minecraft.src;

import java.util.Collection;
import java.util.Iterator;

public abstract class InventoryEffectRenderer extends GuiContainer
{
	private boolean field_74222_o;
	
	public InventoryEffectRenderer(Container par1Container)
	{
		super(par1Container);
	}
	
	private void displayDebuffEffects()
	{
		int var1 = guiLeft - 124;
		int var2 = guiTop;
		boolean var3 = true;
		Collection var4 = mc.thePlayer.getActivePotionEffects();
		if(!var4.isEmpty())
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			int var5 = 33;
			if(var4.size() > 5)
			{
				var5 = 132 / (var4.size() - 1);
			}
			for(Iterator var6 = mc.thePlayer.getActivePotionEffects().iterator(); var6.hasNext(); var2 += var5)
			{
				PotionEffect var7 = (PotionEffect) var6.next();
				Potion var8 = Potion.potionTypes[var7.getPotionID()];
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.func_110434_K().func_110577_a(field_110408_a);
				drawTexturedModalRect(var1, var2, 0, 166, 140, 32);
				if(var8.hasStatusIcon())
				{
					int var9 = var8.getStatusIconIndex();
					drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var9 % 8 * 18, 198 + var9 / 8 * 18, 18, 18);
				}
				String var11 = I18n.func_135053_a(var8.getName());
				if(var7.getAmplifier() == 1)
				{
					var11 = var11 + " II";
				} else if(var7.getAmplifier() == 2)
				{
					var11 = var11 + " III";
				} else if(var7.getAmplifier() == 3)
				{
					var11 = var11 + " IV";
				}
				fontRenderer.drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6, 16777215);
				String var10 = Potion.getDurationString(var7);
				fontRenderer.drawStringWithShadow(var10, var1 + 10 + 18, var2 + 6 + 10, 8355711);
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		if(field_74222_o)
		{
			displayDebuffEffects();
		}
	}
	
	@Override public void initGui()
	{
		super.initGui();
		if(!mc.thePlayer.getActivePotionEffects().isEmpty())
		{
			guiLeft = 160 + (width - xSize - 200) / 2;
			field_74222_o = true;
		}
	}
}
