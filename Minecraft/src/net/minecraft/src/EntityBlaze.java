package net.minecraft.src;

public class EntityBlaze extends EntityMob
{
	private float heightOffset = 0.5F;
	private int heightOffsetUpdateTime;
	private int field_70846_g;
	
	public EntityBlaze(World par1World)
	{
		super(par1World);
		texture = "/mob/fire.png";
		isImmuneToFire = true;
		experienceValue = 10;
	}
	
	@Override protected void attackEntity(Entity par1Entity, float par2)
	{
		if(attackTime <= 0 && par2 < 2.0F && par1Entity.boundingBox.maxY > boundingBox.minY && par1Entity.boundingBox.minY < boundingBox.maxY)
		{
			attackTime = 20;
			attackEntityAsMob(par1Entity);
		} else if(par2 < 30.0F)
		{
			double var3 = par1Entity.posX - posX;
			double var5 = par1Entity.boundingBox.minY + par1Entity.height / 2.0F - (posY + height / 2.0F);
			double var7 = par1Entity.posZ - posZ;
			if(attackTime == 0)
			{
				++field_70846_g;
				if(field_70846_g == 1)
				{
					attackTime = 60;
					func_70844_e(true);
				} else if(field_70846_g <= 4)
				{
					attackTime = 6;
				} else
				{
					attackTime = 100;
					field_70846_g = 0;
					func_70844_e(false);
				}
				if(field_70846_g > 1)
				{
					float var9 = MathHelper.sqrt_float(par2) * 0.5F;
					worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1009, (int) posX, (int) posY, (int) posZ, 0);
					for(int var10 = 0; var10 < 1; ++var10)
					{
						EntitySmallFireball var11 = new EntitySmallFireball(worldObj, this, var3 + rand.nextGaussian() * var9, var5, var7 + rand.nextGaussian() * var9);
						var11.posY = posY + height / 2.0F + 0.5D;
						worldObj.spawnEntityInWorld(var11);
					}
				}
			}
			rotationYaw = (float) (Math.atan2(var7, var3) * 180.0D / Math.PI) - 90.0F;
			hasAttacked = true;
		}
	}
	
	@Override protected void dropFewItems(boolean par1, int par2)
	{
		if(par1)
		{
			int var3 = rand.nextInt(2 + par2);
			for(int var4 = 0; var4 < var3; ++var4)
			{
				dropItem(Item.blazeRod.itemID, 1);
			}
		}
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, new Byte((byte) 0));
	}
	
	@Override protected void fall(float par1)
	{
	}
	
	public void func_70844_e(boolean par1)
	{
		byte var2 = dataWatcher.getWatchableObjectByte(16);
		if(par1)
		{
			var2 = (byte) (var2 | 1);
		} else
		{
			var2 &= -2;
		}
		dataWatcher.updateObject(16, Byte.valueOf(var2));
	}
	
	public boolean func_70845_n()
	{
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}
	
	@Override public int getAttackStrength(Entity par1Entity)
	{
		return 6;
	}
	
	@Override public float getBrightness(float par1)
	{
		return 1.0F;
	}
	
	@Override public int getBrightnessForRender(float par1)
	{
		return 15728880;
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.blaze.death";
	}
	
	@Override protected int getDropItemId()
	{
		return Item.blazeRod.itemID;
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.blaze.hit";
	}
	
	@Override protected String getLivingSound()
	{
		return "mob.blaze.breathe";
	}
	
	@Override public int getMaxHealth()
	{
		return 20;
	}
	
	@Override public boolean isBurning()
	{
		return func_70845_n();
	}
	
	@Override protected boolean isValidLightLevel()
	{
		return true;
	}
	
	@Override public void onLivingUpdate()
	{
		if(!worldObj.isRemote)
		{
			if(isWet())
			{
				attackEntityFrom(DamageSource.drown, 1);
			}
			--heightOffsetUpdateTime;
			if(heightOffsetUpdateTime <= 0)
			{
				heightOffsetUpdateTime = 100;
				heightOffset = 0.5F + (float) rand.nextGaussian() * 3.0F;
			}
			if(getEntityToAttack() != null && getEntityToAttack().posY + getEntityToAttack().getEyeHeight() > posY + getEyeHeight() + heightOffset)
			{
				motionY += (0.30000001192092896D - motionY) * 0.30000001192092896D;
			}
		}
		if(rand.nextInt(24) == 0)
		{
			worldObj.playSoundEffect(posX + 0.5D, posY + 0.5D, posZ + 0.5D, "fire.fire", 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F);
		}
		if(!onGround && motionY < 0.0D)
		{
			motionY *= 0.6D;
		}
		for(int var1 = 0; var1 < 2; ++var1)
		{
			worldObj.spawnParticle("largesmoke", posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);
		}
		super.onLivingUpdate();
	}
}
