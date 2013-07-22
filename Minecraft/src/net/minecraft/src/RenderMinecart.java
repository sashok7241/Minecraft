package net.minecraft.src;


public class RenderMinecart extends Render
{
	protected ModelBase modelMinecart;
	protected final RenderBlocks field_94145_f;
	
	public RenderMinecart()
	{
		shadowSize = 0.5F;
		modelMinecart = new ModelMinecart();
		field_94145_f = new RenderBlocks();
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderTheMinecart((EntityMinecart) par1Entity, par2, par4, par6, par8, par9);
	}
	
	protected void renderBlockInMinecart(EntityMinecart par1EntityMinecart, float par2, Block par3Block, int par4)
	{
		float var5 = par1EntityMinecart.getBrightness(par2);
		GL11.glPushMatrix();
		field_94145_f.renderBlockAsItem(par3Block, par4, var5);
		GL11.glPopMatrix();
	}
	
	public void renderTheMinecart(EntityMinecart par1EntityMinecart, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		long var10 = par1EntityMinecart.entityId * 493286711L;
		var10 = var10 * var10 * 4392167121L + var10 * 98761L;
		float var12 = (((var10 >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float var13 = (((var10 >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float var14 = (((var10 >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		GL11.glTranslatef(var12, var13, var14);
		double var15 = par1EntityMinecart.lastTickPosX + (par1EntityMinecart.posX - par1EntityMinecart.lastTickPosX) * par9;
		double var17 = par1EntityMinecart.lastTickPosY + (par1EntityMinecart.posY - par1EntityMinecart.lastTickPosY) * par9;
		double var19 = par1EntityMinecart.lastTickPosZ + (par1EntityMinecart.posZ - par1EntityMinecart.lastTickPosZ) * par9;
		double var21 = 0.30000001192092896D;
		Vec3 var23 = par1EntityMinecart.func_70489_a(var15, var17, var19);
		float var24 = par1EntityMinecart.prevRotationPitch + (par1EntityMinecart.rotationPitch - par1EntityMinecart.prevRotationPitch) * par9;
		if(var23 != null)
		{
			Vec3 var25 = par1EntityMinecart.func_70495_a(var15, var17, var19, var21);
			Vec3 var26 = par1EntityMinecart.func_70495_a(var15, var17, var19, -var21);
			if(var25 == null)
			{
				var25 = var23;
			}
			if(var26 == null)
			{
				var26 = var23;
			}
			par2 += var23.xCoord - var15;
			par4 += (var25.yCoord + var26.yCoord) / 2.0D - var17;
			par6 += var23.zCoord - var19;
			Vec3 var27 = var26.addVector(-var25.xCoord, -var25.yCoord, -var25.zCoord);
			if(var27.lengthVector() != 0.0D)
			{
				var27 = var27.normalize();
				par8 = (float) (Math.atan2(var27.zCoord, var27.xCoord) * 180.0D / Math.PI);
				var24 = (float) (Math.atan(var27.yCoord) * 73.0D);
			}
		}
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		GL11.glRotatef(180.0F - par8, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-var24, 0.0F, 0.0F, 1.0F);
		float var31 = par1EntityMinecart.getRollingAmplitude() - par9;
		float var33 = par1EntityMinecart.getDamage() - par9;
		if(var33 < 0.0F)
		{
			var33 = 0.0F;
		}
		if(var31 > 0.0F)
		{
			GL11.glRotatef(MathHelper.sin(var31) * var31 * var33 / 10.0F * par1EntityMinecart.getRollingDirection(), 1.0F, 0.0F, 0.0F);
		}
		int var32 = par1EntityMinecart.getDisplayTileOffset();
		Block var28 = par1EntityMinecart.getDisplayTile();
		int var29 = par1EntityMinecart.getDisplayTileData();
		if(var28 != null)
		{
			GL11.glPushMatrix();
			loadTexture("/terrain.png");
			float var30 = 0.75F;
			GL11.glScalef(var30, var30, var30);
			GL11.glTranslatef(0.0F, var32 / 16.0F, 0.0F);
			renderBlockInMinecart(par1EntityMinecart, par9, var28, var29);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
		loadTexture("/item/cart.png");
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		modelMinecart.render(par1EntityMinecart, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}
}
