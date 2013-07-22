package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class GuiInventory extends InventoryEffectRenderer
{
	private float xSize_lo;
	private float ySize_lo;
	
	public GuiInventory(EntityPlayer par1EntityPlayer)
	{
		super(par1EntityPlayer.inventoryContainer);
		allowUserInput = true;
		par1EntityPlayer.addStat(AchievementList.openInventory, 1);
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.id == 0)
		{
			mc.displayGuiScreen(new GuiAchievements(mc.statFileWriter));
		}
		if(par1GuiButton.id == 1)
		{
			mc.displayGuiScreen(new GuiStats(this, mc.statFileWriter));
		}
	}
	
	@Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/gui/inventory.png");
		int var4 = guiLeft;
		int var5 = guiTop;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
		drawPlayerOnGui(mc, var4 + 51, var5 + 75, 30, var4 + 51 - xSize_lo, var5 + 75 - 50 - ySize_lo);
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		fontRenderer.drawString(StatCollector.translateToLocal("container.crafting"), 86, 16, 4210752);
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		xSize_lo = par1;
		ySize_lo = par2;
	}
	
	@Override public void initGui()
	{
		buttonList.clear();
		if(mc.playerController.isInCreativeMode())
		{
			mc.displayGuiScreen(new GuiContainerCreative(mc.thePlayer));
		} else
		{
			super.initGui();
		}
	}
	
	@Override public void updateScreen()
	{
		if(mc.playerController.isInCreativeMode())
		{
			mc.displayGuiScreen(new GuiContainerCreative(mc.thePlayer));
		}
	}
	
	public static void drawPlayerOnGui(Minecraft par0Minecraft, int par1, int par2, int par3, float par4, float par5)
	{
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) par1, (float) par2, 50.0F);
		GL11.glScalef((float) -par3, (float) par3, (float) par3);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float var6 = par0Minecraft.thePlayer.renderYawOffset;
		float var7 = par0Minecraft.thePlayer.rotationYaw;
		float var8 = par0Minecraft.thePlayer.rotationPitch;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float) Math.atan(par5 / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
		par0Minecraft.thePlayer.renderYawOffset = (float) Math.atan(par4 / 40.0F) * 20.0F;
		par0Minecraft.thePlayer.rotationYaw = (float) Math.atan(par4 / 40.0F) * 40.0F;
		par0Minecraft.thePlayer.rotationPitch = -((float) Math.atan(par5 / 40.0F)) * 20.0F;
		par0Minecraft.thePlayer.rotationYawHead = par0Minecraft.thePlayer.rotationYaw;
		GL11.glTranslatef(0.0F, par0Minecraft.thePlayer.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180.0F;
		RenderManager.instance.renderEntityWithPosYaw(par0Minecraft.thePlayer, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		par0Minecraft.thePlayer.renderYawOffset = var6;
		par0Minecraft.thePlayer.rotationYaw = var7;
		par0Minecraft.thePlayer.rotationPitch = var8;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
}
