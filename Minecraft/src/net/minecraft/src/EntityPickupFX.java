package net.minecraft.src;


public class EntityPickupFX extends EntityFX
{
	private Entity entityToPickUp;
	private Entity entityPickingUp;
	private int age;
	private int maxAge;
	private float yOffs;
	
	public EntityPickupFX(World par1World, Entity par2Entity, Entity par3Entity, float par4)
	{
		super(par1World, par2Entity.posX, par2Entity.posY, par2Entity.posZ, par2Entity.motionX, par2Entity.motionY, par2Entity.motionZ);
		entityToPickUp = par2Entity;
		entityPickingUp = par3Entity;
		maxAge = 3;
		yOffs = par4;
	}
	
	@Override public int getFXLayer()
	{
		return 3;
	}
	
	@Override public void onUpdate()
	{
		++age;
		if(age == maxAge)
		{
			setDead();
		}
	}
	
	@Override public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		float var8 = (age + par2) / maxAge;
		var8 *= var8;
		double var9 = entityToPickUp.posX;
		double var11 = entityToPickUp.posY;
		double var13 = entityToPickUp.posZ;
		double var15 = entityPickingUp.lastTickPosX + (entityPickingUp.posX - entityPickingUp.lastTickPosX) * par2;
		double var17 = entityPickingUp.lastTickPosY + (entityPickingUp.posY - entityPickingUp.lastTickPosY) * par2 + yOffs;
		double var19 = entityPickingUp.lastTickPosZ + (entityPickingUp.posZ - entityPickingUp.lastTickPosZ) * par2;
		double var21 = var9 + (var15 - var9) * var8;
		double var23 = var11 + (var17 - var11) * var8;
		double var25 = var13 + (var19 - var13) * var8;
		int var27 = MathHelper.floor_double(var21);
		int var28 = MathHelper.floor_double(var23 + yOffset / 2.0F);
		int var29 = MathHelper.floor_double(var25);
		int var30 = getBrightnessForRender(par2);
		int var31 = var30 % 65536;
		int var32 = var30 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var31 / 1.0F, var32 / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		var21 -= interpPosX;
		var23 -= interpPosY;
		var25 -= interpPosZ;
		RenderManager.instance.renderEntityWithPosYaw(entityToPickUp, (float) var21, (float) var23, (float) var25, entityToPickUp.rotationYaw, par2);
	}
}
