package net.minecraft.src;


public class RenderPlayer extends RenderLiving
{
	private ModelBiped modelBipedMain;
	private ModelBiped modelArmorChestplate;
	private ModelBiped modelArmor;
	private static final String[] armorFilenamePrefix = new String[] { "cloth", "chain", "iron", "diamond", "gold" };
	
	public RenderPlayer()
	{
		super(new ModelBiped(0.0F), 0.5F);
		modelBipedMain = (ModelBiped) mainModel;
		modelArmorChestplate = new ModelBiped(1.0F);
		modelArmor = new ModelBiped(0.5F);
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderPlayer((EntityPlayer) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderPlayer((EntityPlayer) par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	@Override protected void func_82408_c(EntityLiving par1EntityLivingBase, int par2, float par3)
	{
		func_82439_b((EntityPlayer) par1EntityLivingBase, par2, par3);
	}
	
	protected void func_82439_b(EntityPlayer par1EntityLivingBase, int par2, float par3)
	{
		ItemStack var4 = par1EntityLivingBase.inventory.armorItemInSlot(3 - par2);
		if(var4 != null)
		{
			Item var5 = var4.getItem();
			if(var5 instanceof ItemArmor)
			{
				ItemArmor var6 = (ItemArmor) var5;
				loadTexture("/armor/" + armorFilenamePrefix[var6.renderIndex] + "_" + (par2 == 2 ? 2 : 1) + "_b.png");
				float var7 = 1.0F;
				GL11.glColor3f(var7, var7, var7);
			}
		}
	}
	
	@Override protected void func_96449_a(EntityLiving par1EntityLivingBase, double par2, double par4, double par6, String par8Str, float par9, double par10)
	{
		func_96450_a((EntityPlayer) par1EntityLivingBase, par2, par4, par6, par8Str, par9, par10);
	}
	
	protected void func_96450_a(EntityPlayer par1AbstractClientPlayer, double par2, double par4, double par6, String par8Str, float par9, double par10)
	{
		if(par10 < 100.0D)
		{
			Scoreboard var12 = par1AbstractClientPlayer.getWorldScoreboard();
			ScoreObjective var13 = var12.func_96539_a(2);
			if(var13 != null)
			{
				Score var14 = var12.func_96529_a(par1AbstractClientPlayer.getEntityName(), var13);
				if(par1AbstractClientPlayer.isPlayerSleeping())
				{
					renderLivingLabel(par1AbstractClientPlayer, var14.getScorePoints() + " " + var13.getDisplayName(), par2, par4 - 1.5D, par6, 64);
				} else
				{
					renderLivingLabel(par1AbstractClientPlayer, var14.getScorePoints() + " " + var13.getDisplayName(), par2, par4, par6, 64);
				}
				par4 += getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * par9;
			}
		}
		super.func_96449_a(par1AbstractClientPlayer, par2, par4, par6, par8Str, par9, par10);
	}
	
	@Override protected void func_98190_a(EntityLiving p_98190_1_)
	{
		func_98191_a((EntityPlayer) p_98190_1_);
	}
	
	protected void func_98191_a(EntityPlayer p_98191_1_)
	{
		loadDownloadableImageTexture(p_98191_1_.skinUrl, p_98191_1_.getTexture());
	}
	
	@Override protected void preRenderCallback(EntityLiving par1EntityLivingBase, float par2)
	{
		renderPlayerScale((EntityPlayer) par1EntityLivingBase, par2);
	}
	
	@Override protected void renderEquippedItems(EntityLiving par1EntityLivingBase, float par2)
	{
		renderSpecials((EntityPlayer) par1EntityLivingBase, par2);
	}
	
	public void renderFirstPersonArm(EntityPlayer par1EntityPlayer)
	{
		float var2 = 1.0F;
		GL11.glColor3f(var2, var2, var2);
		modelBipedMain.onGround = 0.0F;
		modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, par1EntityPlayer);
		modelBipedMain.bipedRightArm.render(0.0625F);
	}
	
	@Override protected void renderLivingAt(EntityLiving par1EntityLivingBase, double par2, double par4, double par6)
	{
		renderPlayerSleep((EntityPlayer) par1EntityLivingBase, par2, par4, par6);
	}
	
	public void renderPlayer(EntityPlayer par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
	{
		float var10 = 1.0F;
		GL11.glColor3f(var10, var10, var10);
		ItemStack var11 = par1EntityLivingBase.inventory.getCurrentItem();
		modelArmorChestplate.heldItemRight = modelArmor.heldItemRight = modelBipedMain.heldItemRight = var11 != null ? 1 : 0;
		if(var11 != null && par1EntityLivingBase.getItemInUseCount() > 0)
		{
			EnumAction var12 = var11.getItemUseAction();
			if(var12 == EnumAction.block)
			{
				modelArmorChestplate.heldItemRight = modelArmor.heldItemRight = modelBipedMain.heldItemRight = 3;
			} else if(var12 == EnumAction.bow)
			{
				modelArmorChestplate.aimedBow = modelArmor.aimedBow = modelBipedMain.aimedBow = true;
			}
		}
		modelArmorChestplate.isSneak = modelArmor.isSneak = modelBipedMain.isSneak = par1EntityLivingBase.isSneaking();
		double var14 = par4 - par1EntityLivingBase.yOffset;
		if(par1EntityLivingBase.isSneaking() && !(par1EntityLivingBase instanceof EntityPlayerSP))
		{
			var14 -= 0.125D;
		}
		super.doRenderLiving(par1EntityLivingBase, par2, var14, par6, par8, par9);
		modelArmorChestplate.aimedBow = modelArmor.aimedBow = modelBipedMain.aimedBow = false;
		modelArmorChestplate.isSneak = modelArmor.isSneak = modelBipedMain.isSneak = false;
		modelArmorChestplate.heldItemRight = modelArmor.heldItemRight = modelBipedMain.heldItemRight = 0;
	}
	
	protected void renderPlayerScale(EntityPlayer par1AbstractClientPlayer, float par2)
	{
		float var3 = 0.9375F;
		GL11.glScalef(var3, var3, var3);
	}
	
	protected void renderPlayerSleep(EntityPlayer par1AbstractClientPlayer, double par2, double par4, double par6)
	{
		if(par1AbstractClientPlayer.isEntityAlive() && par1AbstractClientPlayer.isPlayerSleeping())
		{
			super.renderLivingAt(par1AbstractClientPlayer, par2 + par1AbstractClientPlayer.field_71079_bU, par4 + par1AbstractClientPlayer.field_71082_cx, par6 + par1AbstractClientPlayer.field_71089_bV);
		} else
		{
			super.renderLivingAt(par1AbstractClientPlayer, par2, par4, par6);
		}
	}
	
	protected void renderSpecials(EntityPlayer par1AbstractClientPlayer, float par2)
	{
		float var3 = 1.0F;
		GL11.glColor3f(var3, var3, var3);
		super.renderEquippedItems(par1AbstractClientPlayer, par2);
		super.renderArrowsStuckInEntity(par1AbstractClientPlayer, par2);
		ItemStack var4 = par1AbstractClientPlayer.inventory.armorItemInSlot(3);
		if(var4 != null)
		{
			GL11.glPushMatrix();
			modelBipedMain.bipedHead.postRender(0.0625F);
			float var5;
			if(var4.getItem().itemID < 256)
			{
				if(RenderBlocks.renderItemIn3d(Block.blocksList[var4.itemID].getRenderType()))
				{
					var5 = 0.625F;
					GL11.glTranslatef(0.0F, -0.25F, 0.0F);
					GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(var5, -var5, -var5);
				}
				renderManager.itemRenderer.renderItem(par1AbstractClientPlayer, var4, 0);
			} else if(var4.getItem().itemID == Item.skull.itemID)
			{
				var5 = 1.0625F;
				GL11.glScalef(var5, -var5, -var5);
				String var6 = "";
				if(var4.hasTagCompound() && var4.getTagCompound().hasKey("SkullOwner"))
				{
					var6 = var4.getTagCompound().getString("SkullOwner");
				}
				TileEntitySkullRenderer.skullRenderer.func_82393_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, var4.getItemDamage(), var6);
			}
			GL11.glPopMatrix();
		}
		float var7;
		float var8;
		if(par1AbstractClientPlayer.username.equals("deadmau5") && loadDownloadableImageTexture(par1AbstractClientPlayer.skinUrl, (String) null))
		{
			for(int var20 = 0; var20 < 2; ++var20)
			{
				float var25 = par1AbstractClientPlayer.prevRotationYaw + (par1AbstractClientPlayer.rotationYaw - par1AbstractClientPlayer.prevRotationYaw) * par2 - (par1AbstractClientPlayer.prevRenderYawOffset + (par1AbstractClientPlayer.renderYawOffset - par1AbstractClientPlayer.prevRenderYawOffset) * par2);
				var7 = par1AbstractClientPlayer.prevRotationPitch + (par1AbstractClientPlayer.rotationPitch - par1AbstractClientPlayer.prevRotationPitch) * par2;
				GL11.glPushMatrix();
				GL11.glRotatef(var25, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(var7, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0.375F * (var20 * 2 - 1), 0.0F, 0.0F);
				GL11.glTranslatef(0.0F, -0.375F, 0.0F);
				GL11.glRotatef(-var7, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-var25, 0.0F, 1.0F, 0.0F);
				var8 = 1.3333334F;
				GL11.glScalef(var8, var8, var8);
				modelBipedMain.renderEars(0.0625F);
				GL11.glPopMatrix();
			}
		}
		float var11;
		if(loadDownloadableImageTexture(par1AbstractClientPlayer.cloakUrl, (String) null) && !par1AbstractClientPlayer.isInvisible() && !par1AbstractClientPlayer.getHideCape())
		{
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 0.0F, 0.125F);
			double var22 = par1AbstractClientPlayer.field_71091_bM + (par1AbstractClientPlayer.field_71094_bP - par1AbstractClientPlayer.field_71091_bM) * par2 - (par1AbstractClientPlayer.prevPosX + (par1AbstractClientPlayer.posX - par1AbstractClientPlayer.prevPosX) * par2);
			double var24 = par1AbstractClientPlayer.field_71096_bN + (par1AbstractClientPlayer.field_71095_bQ - par1AbstractClientPlayer.field_71096_bN) * par2 - (par1AbstractClientPlayer.prevPosY + (par1AbstractClientPlayer.posY - par1AbstractClientPlayer.prevPosY) * par2);
			double var9 = par1AbstractClientPlayer.field_71097_bO + (par1AbstractClientPlayer.field_71085_bR - par1AbstractClientPlayer.field_71097_bO) * par2 - (par1AbstractClientPlayer.prevPosZ + (par1AbstractClientPlayer.posZ - par1AbstractClientPlayer.prevPosZ) * par2);
			var11 = par1AbstractClientPlayer.prevRenderYawOffset + (par1AbstractClientPlayer.renderYawOffset - par1AbstractClientPlayer.prevRenderYawOffset) * par2;
			double var12 = MathHelper.sin(var11 * (float) Math.PI / 180.0F);
			double var14 = -MathHelper.cos(var11 * (float) Math.PI / 180.0F);
			float var16 = (float) var24 * 10.0F;
			if(var16 < -6.0F)
			{
				var16 = -6.0F;
			}
			if(var16 > 32.0F)
			{
				var16 = 32.0F;
			}
			float var17 = (float) (var22 * var12 + var9 * var14) * 100.0F;
			float var18 = (float) (var22 * var14 - var9 * var12) * 100.0F;
			if(var17 < 0.0F)
			{
				var17 = 0.0F;
			}
			float var19 = par1AbstractClientPlayer.prevCameraYaw + (par1AbstractClientPlayer.cameraYaw - par1AbstractClientPlayer.prevCameraYaw) * par2;
			var16 += MathHelper.sin((par1AbstractClientPlayer.prevDistanceWalkedModified + (par1AbstractClientPlayer.distanceWalkedModified - par1AbstractClientPlayer.prevDistanceWalkedModified) * par2) * 6.0F) * 32.0F * var19;
			if(par1AbstractClientPlayer.isSneaking())
			{
				var16 += 25.0F;
			}
			GL11.glRotatef(6.0F + var17 / 2.0F + var16, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(var18 / 2.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-var18 / 2.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			modelBipedMain.renderCloak(0.0625F);
			GL11.glPopMatrix();
		}
		ItemStack var21 = par1AbstractClientPlayer.inventory.getCurrentItem();
		if(var21 != null)
		{
			GL11.glPushMatrix();
			modelBipedMain.bipedRightArm.postRender(0.0625F);
			GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
			if(par1AbstractClientPlayer.fishEntity != null)
			{
				var21 = new ItemStack(Item.stick);
			}
			EnumAction var23 = null;
			if(par1AbstractClientPlayer.getItemInUseCount() > 0)
			{
				var23 = var21.getItemUseAction();
			}
			if(var21.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var21.itemID].getRenderType()))
			{
				var7 = 0.5F;
				GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
				var7 *= 0.75F;
				GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(-var7, -var7, var7);
			} else if(var21.itemID == Item.bow.itemID)
			{
				var7 = 0.625F;
				GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
				GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(var7, -var7, var7);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else if(Item.itemsList[var21.itemID].isFull3D())
			{
				var7 = 0.625F;
				if(Item.itemsList[var21.itemID].shouldRotateAroundWhenRendering())
				{
					GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(0.0F, -0.125F, 0.0F);
				}
				if(par1AbstractClientPlayer.getItemInUseCount() > 0 && var23 == EnumAction.block)
				{
					GL11.glTranslatef(0.05F, 0.0F, -0.1F);
					GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
				}
				GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
				GL11.glScalef(var7, -var7, var7);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else
			{
				var7 = 0.375F;
				GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
				GL11.glScalef(var7, var7, var7);
				GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
			}
			float var10;
			int var27;
			float var28;
			if(var21.getItem().requiresMultipleRenderPasses())
			{
				for(var27 = 0; var27 <= 1; ++var27)
				{
					int var26 = var21.getItem().getColorFromItemStack(var21, var27);
					var28 = (var26 >> 16 & 255) / 255.0F;
					var10 = (var26 >> 8 & 255) / 255.0F;
					var11 = (var26 & 255) / 255.0F;
					GL11.glColor4f(var28, var10, var11, 1.0F);
					renderManager.itemRenderer.renderItem(par1AbstractClientPlayer, var21, var27);
				}
			} else
			{
				var27 = var21.getItem().getColorFromItemStack(var21, 0);
				var8 = (var27 >> 16 & 255) / 255.0F;
				var28 = (var27 >> 8 & 255) / 255.0F;
				var10 = (var27 & 255) / 255.0F;
				GL11.glColor4f(var8, var28, var10, 1.0F);
				renderManager.itemRenderer.renderItem(par1AbstractClientPlayer, var21, 0);
			}
			GL11.glPopMatrix();
		}
	}
	
	@Override protected void rotateCorpse(EntityLiving par1EntityLivingBase, float par2, float par3, float par4)
	{
		rotatePlayer((EntityPlayer) par1EntityLivingBase, par2, par3, par4);
	}
	
	protected void rotatePlayer(EntityPlayer par1AbstractClientPlayer, float par2, float par3, float par4)
	{
		if(par1AbstractClientPlayer.isEntityAlive() && par1AbstractClientPlayer.isPlayerSleeping())
		{
			GL11.glRotatef(par1AbstractClientPlayer.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(getDeathMaxRotation(par1AbstractClientPlayer), 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
		} else
		{
			super.rotateCorpse(par1AbstractClientPlayer, par2, par3, par4);
		}
	}
	
	protected int setArmorModel(EntityPlayer par1AbstractClientPlayer, int par2, float par3)
	{
		ItemStack var4 = par1AbstractClientPlayer.inventory.armorItemInSlot(3 - par2);
		if(var4 != null)
		{
			Item var5 = var4.getItem();
			if(var5 instanceof ItemArmor)
			{
				ItemArmor var6 = (ItemArmor) var5;
				loadTexture("/armor/" + armorFilenamePrefix[var6.renderIndex] + "_" + (par2 == 2 ? 2 : 1) + ".png");
				ModelBiped var7 = par2 == 2 ? modelArmor : modelArmorChestplate;
				var7.bipedHead.showModel = par2 == 0;
				var7.bipedHeadwear.showModel = par2 == 0;
				var7.bipedBody.showModel = par2 == 1 || par2 == 2;
				var7.bipedRightArm.showModel = par2 == 1;
				var7.bipedLeftArm.showModel = par2 == 1;
				var7.bipedRightLeg.showModel = par2 == 2 || par2 == 3;
				var7.bipedLeftLeg.showModel = par2 == 2 || par2 == 3;
				setRenderPassModel(var7);
				if(var7 != null)
				{
					var7.onGround = mainModel.onGround;
				}
				if(var7 != null)
				{
					var7.isRiding = mainModel.isRiding;
				}
				if(var7 != null)
				{
					var7.isChild = mainModel.isChild;
				}
				float var8 = 1.0F;
				if(var6.getArmorMaterial() == EnumArmorMaterial.CLOTH)
				{
					int var9 = var6.getColor(var4);
					float var10 = (var9 >> 16 & 255) / 255.0F;
					float var11 = (var9 >> 8 & 255) / 255.0F;
					float var12 = (var9 & 255) / 255.0F;
					GL11.glColor3f(var8 * var10, var8 * var11, var8 * var12);
					if(var4.isItemEnchanted()) return 31;
					return 16;
				}
				GL11.glColor3f(var8, var8, var8);
				if(var4.isItemEnchanted()) return 15;
				return 1;
			}
		}
		return -1;
	}
	
	@Override protected int shouldRenderPass(EntityLiving par1EntityLivingBase, int par2, float par3)
	{
		return setArmorModel((EntityPlayer) par1EntityLivingBase, par2, par3);
	}
}
