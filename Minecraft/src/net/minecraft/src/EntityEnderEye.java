package net.minecraft.src;

public class EntityEnderEye extends Entity
{
	public int field_70226_a = 0;
	private double targetX;
	private double targetY;
	private double targetZ;
	private int despawnTimer;
	private boolean shatterOrDrop;
	
	public EntityEnderEye(World par1World)
	{
		super(par1World);
		setSize(0.25F, 0.25F);
	}
	
	public EntityEnderEye(World par1World, double par2, double par4, double par6)
	{
		super(par1World);
		despawnTimer = 0;
		setSize(0.25F, 0.25F);
		setPosition(par2, par4, par6);
		yOffset = 0.0F;
	}
	
	@Override public boolean canAttackWithItem()
	{
		return false;
	}
	
	@Override protected void entityInit()
	{
	}
	
	@Override public float getBrightness(float par1)
	{
		return 1.0F;
	}
	
	@Override public int getBrightnessForRender(float par1)
	{
		return 15728880;
	}
	
	@Override public float getShadowSize()
	{
		return 0.0F;
	}
	
	@Override public boolean isInRangeToRenderDist(double par1)
	{
		double var3 = boundingBox.getAverageEdgeLength() * 4.0D;
		var3 *= 64.0D;
		return par1 < var3 * var3;
	}
	
	public void moveTowards(double par1, int par3, double par4)
	{
		double var6 = par1 - posX;
		double var8 = par4 - posZ;
		float var10 = MathHelper.sqrt_double(var6 * var6 + var8 * var8);
		if(var10 > 12.0F)
		{
			targetX = posX + var6 / var10 * 12.0D;
			targetZ = posZ + var8 / var10 * 12.0D;
			targetY = posY + 8.0D;
		} else
		{
			targetX = par1;
			targetY = par3;
			targetZ = par4;
		}
		despawnTimer = 0;
		shatterOrDrop = rand.nextInt(5) > 0;
	}
	
	@Override public void onUpdate()
	{
		lastTickPosX = posX;
		lastTickPosY = posY;
		lastTickPosZ = posZ;
		super.onUpdate();
		posX += motionX;
		posY += motionY;
		posZ += motionZ;
		float var1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
		for(rotationPitch = (float) (Math.atan2(motionY, var1) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
		{
			;
		}
		while(rotationPitch - prevRotationPitch >= 180.0F)
		{
			prevRotationPitch += 360.0F;
		}
		while(rotationYaw - prevRotationYaw < -180.0F)
		{
			prevRotationYaw -= 360.0F;
		}
		while(rotationYaw - prevRotationYaw >= 180.0F)
		{
			prevRotationYaw += 360.0F;
		}
		rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
		rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
		if(!worldObj.isRemote)
		{
			double var2 = targetX - posX;
			double var4 = targetZ - posZ;
			float var6 = (float) Math.sqrt(var2 * var2 + var4 * var4);
			float var7 = (float) Math.atan2(var4, var2);
			double var8 = var1 + (var6 - var1) * 0.0025D;
			if(var6 < 1.0F)
			{
				var8 *= 0.8D;
				motionY *= 0.8D;
			}
			motionX = Math.cos(var7) * var8;
			motionZ = Math.sin(var7) * var8;
			if(posY < targetY)
			{
				motionY += (1.0D - motionY) * 0.014999999664723873D;
			} else
			{
				motionY += (-1.0D - motionY) * 0.014999999664723873D;
			}
		}
		float var10 = 0.25F;
		if(isInWater())
		{
			for(int var3 = 0; var3 < 4; ++var3)
			{
				worldObj.spawnParticle("bubble", posX - motionX * var10, posY - motionY * var10, posZ - motionZ * var10, motionX, motionY, motionZ);
			}
		} else
		{
			worldObj.spawnParticle("portal", posX - motionX * var10 + rand.nextDouble() * 0.6D - 0.3D, posY - motionY * var10 - 0.5D, posZ - motionZ * var10 + rand.nextDouble() * 0.6D - 0.3D, motionX, motionY, motionZ);
		}
		if(!worldObj.isRemote)
		{
			setPosition(posX, posY, posZ);
			++despawnTimer;
			if(despawnTimer > 80 && !worldObj.isRemote)
			{
				setDead();
				if(shatterOrDrop)
				{
					worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Item.eyeOfEnder)));
				} else
				{
					worldObj.playAuxSFX(2003, (int) Math.round(posX), (int) Math.round(posY), (int) Math.round(posZ), 0);
				}
			}
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
	}
	
	@Override public void setVelocity(double par1, double par3, double par5)
	{
		motionX = par1;
		motionY = par3;
		motionZ = par5;
		if(prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
		{
			float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float) (Math.atan2(par3, var7) * 180.0D / Math.PI);
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
	}
}
