package net.minecraft.src;

import java.util.Random;

public class GuiEnchantment extends GuiContainer
{
	private static ModelBook bookModel = new ModelBook();
	private Random rand = new Random();
	private ContainerEnchantment containerEnchantment;
	public int field_74214_o;
	public float field_74213_p;
	public float field_74212_q;
	public float field_74211_r;
	public float field_74210_s;
	public float field_74209_t;
	public float field_74208_u;
	ItemStack theItemStack;
	private String field_94079_C;
	
	public GuiEnchantment(InventoryPlayer p_i9000_1_, World p_i9000_2_, int p_i9000_3_, int p_i9000_4_, int p_i9000_5_, String p_i9000_6_)
	{
		super(new ContainerEnchantment(p_i9000_1_, p_i9000_2_, p_i9000_3_, p_i9000_4_, p_i9000_5_));
		containerEnchantment = (ContainerEnchantment) inventorySlots;
		field_94079_C = p_i9000_6_;
	}
	
	@Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/gui/enchant.png");
		int var4 = (width - xSize) / 2;
		int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
		GL11.glPushMatrix();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		ScaledResolution var6 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		GL11.glViewport((var6.getScaledWidth() - 320) / 2 * var6.getScaleFactor(), (var6.getScaledHeight() - 240) / 2 * var6.getScaleFactor(), 320 * var6.getScaleFactor(), 240 * var6.getScaleFactor());
		GL11.glTranslatef(-0.34F, 0.23F, 0.0F);
		GLU.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
		float var7 = 1.0F;
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		RenderHelper.enableStandardItemLighting();
		GL11.glTranslatef(0.0F, 3.3F, -16.0F);
		GL11.glScalef(var7, var7, var7);
		float var8 = 5.0F;
		GL11.glScalef(var8, var8, var8);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		mc.renderEngine.bindTexture("/item/book.png");
		GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
		float var9 = field_74208_u + (field_74209_t - field_74208_u) * par1;
		GL11.glTranslatef((1.0F - var9) * 0.2F, (1.0F - var9) * 0.1F, (1.0F - var9) * 0.25F);
		GL11.glRotatef(-(1.0F - var9) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		float var10 = field_74212_q + (field_74213_p - field_74212_q) * par1 + 0.25F;
		float var11 = field_74212_q + (field_74213_p - field_74212_q) * par1 + 0.75F;
		var10 = (var10 - MathHelper.truncateDoubleToInt(var10)) * 1.6F - 0.3F;
		var11 = (var11 - MathHelper.truncateDoubleToInt(var11)) * 1.6F - 0.3F;
		if(var10 < 0.0F)
		{
			var10 = 0.0F;
		}
		if(var11 < 0.0F)
		{
			var11 = 0.0F;
		}
		if(var10 > 1.0F)
		{
			var10 = 1.0F;
		}
		if(var11 > 1.0F)
		{
			var11 = 1.0F;
		}
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		bookModel.render((Entity) null, 0.0F, var10, var11, var9, 0.0F, 0.0625F);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/gui/enchant.png");
		EnchantmentNameParts.instance.setRandSeed(containerEnchantment.nameSeed);
		for(int var12 = 0; var12 < 3; ++var12)
		{
			String var13 = EnchantmentNameParts.instance.generateRandomEnchantName();
			zLevel = 0.0F;
			mc.renderEngine.bindTexture("/gui/enchant.png");
			int var14 = containerEnchantment.enchantLevels[var12];
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			if(var14 == 0)
			{
				drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 185, 108, 19);
			} else
			{
				String var15 = "" + var14;
				FontRenderer var16 = mc.standardGalacticFontRenderer;
				int var17 = 6839882;
				if(mc.thePlayer.experienceLevel < var14 && !mc.thePlayer.capabilities.isCreativeMode)
				{
					drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 185, 108, 19);
					var16.drawSplitString(var13, var4 + 62, var5 + 16 + 19 * var12, 104, (var17 & 16711422) >> 1);
					var16 = mc.fontRenderer;
					var17 = 4226832;
					var16.drawStringWithShadow(var15, var4 + 62 + 104 - var16.getStringWidth(var15), var5 + 16 + 19 * var12 + 7, var17);
				} else
				{
					int var18 = par2 - (var4 + 60);
					int var19 = par3 - (var5 + 14 + 19 * var12);
					if(var18 >= 0 && var19 >= 0 && var18 < 108 && var19 < 19)
					{
						drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 204, 108, 19);
						var17 = 16777088;
					} else
					{
						drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 166, 108, 19);
					}
					var16.drawSplitString(var13, var4 + 62, var5 + 16 + 19 * var12, 104, var17);
					var16 = mc.fontRenderer;
					var17 = 8453920;
					var16.drawStringWithShadow(var15, var4 + 62 + 104 - var16.getStringWidth(var15), var5 + 16 + 19 * var12 + 7, var17);
				}
			}
		}
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		fontRenderer.drawString(field_94079_C == null ? StatCollector.translateToLocal("container.enchant") : field_94079_C, 12, 5, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}
	
	public void func_74205_h()
	{
		ItemStack var1 = inventorySlots.getSlot(0).getStack();
		if(!ItemStack.areItemStacksEqual(var1, theItemStack))
		{
			theItemStack = var1;
			do
			{
				field_74211_r += rand.nextInt(4) - rand.nextInt(4);
			} while(field_74213_p <= field_74211_r + 1.0F && field_74213_p >= field_74211_r - 1.0F);
		}
		++field_74214_o;
		field_74212_q = field_74213_p;
		field_74208_u = field_74209_t;
		boolean var2 = false;
		for(int var3 = 0; var3 < 3; ++var3)
		{
			if(containerEnchantment.enchantLevels[var3] != 0)
			{
				var2 = true;
			}
		}
		if(var2)
		{
			field_74209_t += 0.2F;
		} else
		{
			field_74209_t -= 0.2F;
		}
		if(field_74209_t < 0.0F)
		{
			field_74209_t = 0.0F;
		}
		if(field_74209_t > 1.0F)
		{
			field_74209_t = 1.0F;
		}
		float var5 = (field_74211_r - field_74213_p) * 0.4F;
		float var4 = 0.2F;
		if(var5 < -var4)
		{
			var5 = -var4;
		}
		if(var5 > var4)
		{
			var5 = var4;
		}
		field_74210_s += (var5 - field_74210_s) * 0.9F;
		field_74213_p += field_74210_s;
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		int var4 = (width - xSize) / 2;
		int var5 = (height - ySize) / 2;
		for(int var6 = 0; var6 < 3; ++var6)
		{
			int var7 = par1 - (var4 + 60);
			int var8 = par2 - (var5 + 14 + 19 * var6);
			if(var7 >= 0 && var8 >= 0 && var7 < 108 && var8 < 19 && containerEnchantment.enchantItem(mc.thePlayer, var6))
			{
				mc.playerController.sendEnchantPacket(containerEnchantment.windowId, var6);
			}
		}
	}
	
	@Override public void updateScreen()
	{
		super.updateScreen();
		func_74205_h();
	}
}
