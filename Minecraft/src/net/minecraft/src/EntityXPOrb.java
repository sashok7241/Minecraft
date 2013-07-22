package net.minecraft.src;

public class EntityXPOrb extends Entity
{
	public int xpColor;
	public int xpOrbAge = 0;
	public int field_70532_c;
	private int xpOrbHealth = 5;
	private int xpValue;
	private EntityPlayer closestPlayer;
	private int xpTargetColor;
	
	public EntityXPOrb(World par1World)
	{
		super(par1World);
		setSize(0.25F, 0.25F);
		yOffset = height / 2.0F;
	}
	
	public EntityXPOrb(World par1World, double par2, double par4, double par6, int par8)
	{
		super(par1World);
		setSize(0.5F, 0.5F);
		yOffset = height / 2.0F;
		setPosition(par2, par4, par6);
		rotationYaw = (float) (Math.random() * 360.0D);
		motionX = (float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F;
		motionY = (float) (Math.random() * 0.2D) * 2.0F;
		motionZ = (float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F;
		xpValue = par8;
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		if(isEntityInvulnerable()) return false;
		else
		{
			setBeenAttacked();
			xpOrbHealth -= par2;
			if(xpOrbHealth <= 0)
			{
				setDead();
			}
			return false;
		}
	}
	
	@Override public boolean canAttackWithItem()
	{
		return false;
	}
	
	@Override protected boolean canTriggerWalking()
	{
		return false;
	}
	
	@Override protected void dealFireDamage(int par1)
	{
		attackEntityFrom(DamageSource.inFire, par1);
	}
	
	@Override protected void entityInit()
	{
	}
	
	@Override public int getBrightnessForRender(float par1)
	{
		float var2 = 0.5F;
		if(var2 < 0.0F)
		{
			var2 = 0.0F;
		}
		if(var2 > 1.0F)
		{
			var2 = 1.0F;
		}
		int var3 = super.getBrightnessForRender(par1);
		int var4 = var3 & 255;
		int var5 = var3 >> 16 & 255;
		var4 += (int) (var2 * 15.0F * 16.0F);
		if(var4 > 240)
		{
			var4 = 240;
		}
		return var4 | var5 << 16;
	}
	
	public int getTextureByXP()
	{
		return xpValue >= 2477 ? 10 : xpValue >= 1237 ? 9 : xpValue >= 617 ? 8 : xpValue >= 307 ? 7 : xpValue >= 149 ? 6 : xpValue >= 73 ? 5 : xpValue >= 37 ? 4 : xpValue >= 17 ? 3 : xpValue >= 7 ? 2 : xpValue >= 3 ? 1 : 0;
	}
	
	public int getXpValue()
	{
		return xpValue;
	}
	
	@Override public boolean handleWaterMovement()
	{
		return worldObj.handleMaterialAcceleration(boundingBox, Material.water, this);
	}
	
	@Override public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
	{
		if(!worldObj.isRemote)
		{
			if(field_70532_c == 0 && par1EntityPlayer.xpCooldown == 0)
			{
				par1EntityPlayer.xpCooldown = 2;
				playSound("random.orb", 0.1F, 0.5F * ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.8F));
				par1EntityPlayer.onItemPickup(this, 1);
				par1EntityPlayer.addExperience(xpValue);
				setDead();
			}
		}
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		if(field_70532_c > 0)
		{
			--field_70532_c;
		}
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= 0.029999999329447746D;
		if(worldObj.getBlockMaterial(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) == Material.lava)
		{
			motionY = 0.20000000298023224D;
			motionX = (rand.nextFloat() - rand.nextFloat()) * 0.2F;
			motionZ = (rand.nextFloat() - rand.nextFloat()) * 0.2F;
			playSound("random.fizz", 0.4F, 2.0F + rand.nextFloat() * 0.4F);
		}
		pushOutOfBlocks(posX, (boundingBox.minY + boundingBox.maxY) / 2.0D, posZ);
		double var1 = 8.0D;
		if(xpTargetColor < xpColor - 20 + entityId % 100)
		{
			if(closestPlayer == null || closestPlayer.getDistanceSqToEntity(this) > var1 * var1)
			{
				closestPlayer = worldObj.getClosestPlayerToEntity(this, var1);
			}
			xpTargetColor = xpColor;
		}
		if(closestPlayer != null)
		{
			double var3 = (closestPlayer.posX - posX) / var1;
			double var5 = (closestPlayer.posY + closestPlayer.getEyeHeight() - posY) / var1;
			double var7 = (closestPlayer.posZ - posZ) / var1;
			double var9 = Math.sqrt(var3 * var3 + var5 * var5 + var7 * var7);
			double var11 = 1.0D - var9;
			if(var11 > 0.0D)
			{
				var11 *= var11;
				motionX += var3 / var9 * var11 * 0.1D;
				motionY += var5 / var9 * var11 * 0.1D;
				motionZ += var7 / var9 * var11 * 0.1D;
			}
		}
		moveEntity(motionX, motionY, motionZ);
		float var13 = 0.98F;
		if(onGround)
		{
			var13 = 0.58800006F;
			int var4 = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
			if(var4 > 0)
			{
				var13 = Block.blocksList[var4].slipperiness * 0.98F;
			}
		}
		motionX *= var13;
		motionY *= 0.9800000190734863D;
		motionZ *= var13;
		if(onGround)
		{
			motionY *= -0.8999999761581421D;
		}
		++xpColor;
		++xpOrbAge;
		if(xpOrbAge >= 6000)
		{
			setDead();
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		xpOrbHealth = par1NBTTagCompound.getShort("Health") & 255;
		xpOrbAge = par1NBTTagCompound.getShort("Age");
		xpValue = par1NBTTagCompound.getShort("Value");
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setShort("Health", (byte) xpOrbHealth);
		par1NBTTagCompound.setShort("Age", (short) xpOrbAge);
		par1NBTTagCompound.setShort("Value", (short) xpValue);
	}
	
	public static int getXPSplit(int par0)
	{
		return par0 >= 2477 ? 2477 : par0 >= 1237 ? 1237 : par0 >= 617 ? 617 : par0 >= 307 ? 307 : par0 >= 149 ? 149 : par0 >= 73 ? 73 : par0 >= 37 ? 37 : par0 >= 17 ? 17 : par0 >= 7 ? 7 : par0 >= 3 ? 3 : 1;
	}
}
