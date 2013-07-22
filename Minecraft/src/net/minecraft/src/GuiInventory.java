package net.minecraft.src;


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
		mc.func_110434_K().func_110577_a(field_110408_a);
		int var4 = guiLeft;
		int var5 = guiTop;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
		func_110423_a(var4 + 51, var5 + 75, 30, var4 + 51 - xSize_lo, var5 + 75 - 50 - ySize_lo, mc.thePlayer);
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		fontRenderer.drawString(I18n.func_135053_a("container.crafting"), 86, 16, 4210752);
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
	
	public static void func_110423_a(int par0, int par1, int par2, float par3, float par4, EntityLivingBase par5EntityLivingBase)
	{
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) par0, (float) par1, 50.0F);
		GL11.glScalef((float) -par2, (float) par2, (float) par2);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float var6 = par5EntityLivingBase.renderYawOffset;
		float var7 = par5EntityLivingBase.rotationYaw;
		float var8 = par5EntityLivingBase.rotationPitch;
		float var9 = par5EntityLivingBase.prevRotationYawHead;
		float var10 = par5EntityLivingBase.rotationYawHead;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float) Math.atan(par4 / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
		par5EntityLivingBase.renderYawOffset = (float) Math.atan(par3 / 40.0F) * 20.0F;
		par5EntityLivingBase.rotationYaw = (float) Math.atan(par3 / 40.0F) * 40.0F;
		par5EntityLivingBase.rotationPitch = -((float) Math.atan(par4 / 40.0F)) * 20.0F;
		par5EntityLivingBase.rotationYawHead = par5EntityLivingBase.rotationYaw;
		par5EntityLivingBase.prevRotationYawHead = par5EntityLivingBase.rotationYaw;
		GL11.glTranslatef(0.0F, par5EntityLivingBase.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180.0F;
		RenderManager.instance.renderEntityWithPosYaw(par5EntityLivingBase, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		par5EntityLivingBase.renderYawOffset = var6;
		par5EntityLivingBase.rotationYaw = var7;
		par5EntityLivingBase.rotationPitch = var8;
		par5EntityLivingBase.prevRotationYawHead = var9;
		par5EntityLivingBase.rotationYawHead = var10;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
}
