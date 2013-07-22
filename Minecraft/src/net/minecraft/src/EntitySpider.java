package net.minecraft.src;

public class EntitySpider extends EntityMob
{
	public EntitySpider(World p_i3557_1_)
	{
		super(p_i3557_1_);
		texture = "/mob/spider.png";
		setSize(1.4F, 0.9F);
		moveSpeed = 0.8F;
	}
	
	@Override protected void attackEntity(Entity p_70785_1_, float p_70785_2_)
	{
		float var3 = getBrightness(1.0F);
		if(var3 > 0.5F && rand.nextInt(100) == 0)
		{
			entityToAttack = null;
		} else
		{
			if(p_70785_2_ > 2.0F && p_70785_2_ < 6.0F && rand.nextInt(10) == 0)
			{
				if(onGround)
				{
					double var4 = p_70785_1_.posX - posX;
					double var6 = p_70785_1_.posZ - posZ;
					float var8 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
					motionX = var4 / var8 * 0.5D * 0.800000011920929D + motionX * 0.20000000298023224D;
					motionZ = var6 / var8 * 0.5D * 0.800000011920929D + motionZ * 0.20000000298023224D;
					motionY = 0.4000000059604645D;
				}
			} else
			{
				super.attackEntity(p_70785_1_, p_70785_2_);
			}
		}
	}
	
	@Override protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		super.dropFewItems(p_70628_1_, p_70628_2_);
		if(p_70628_1_ && (rand.nextInt(3) == 0 || rand.nextInt(1 + p_70628_2_) > 0))
		{
			dropItem(Item.spiderEye.itemID, 1);
		}
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, new Byte((byte) 0));
	}
	
	@Override protected Entity findPlayerToAttack()
	{
		float var1 = getBrightness(1.0F);
		if(var1 < 0.5F)
		{
			double var2 = 16.0D;
			return worldObj.getClosestVulnerablePlayerToEntity(this, var2);
		} else return null;
	}
	
	@Override public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.ARTHROPOD;
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.spider.death";
	}
	
	@Override protected int getDropItemId()
	{
		return Item.silk.itemID;
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.spider.say";
	}
	
	@Override protected String getLivingSound()
	{
		return "mob.spider.say";
	}
	
	@Override public int getMaxHealth()
	{
		return 16;
	}
	
	@Override public double getMountedYOffset()
	{
		return height * 0.75D - 0.5D;
	}
	
	@Override public void initCreature()
	{
		if(worldObj.rand.nextInt(100) == 0)
		{
			EntitySkeleton var1 = new EntitySkeleton(worldObj);
			var1.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
			var1.initCreature();
			worldObj.spawnEntityInWorld(var1);
			var1.mountEntity(this);
		}
	}
	
	public boolean isBesideClimbableBlock()
	{
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}
	
	@Override public boolean isOnLadder()
	{
		return isBesideClimbableBlock();
	}
	
	@Override public boolean isPotionApplicable(PotionEffect p_70687_1_)
	{
		return p_70687_1_.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(p_70687_1_);
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		if(!worldObj.isRemote)
		{
			setBesideClimbableBlock(isCollidedHorizontally);
		}
	}
	
	@Override protected void playStepSound(int p_70036_1_, int p_70036_2_, int p_70036_3_, int p_70036_4_)
	{
		playSound("mob.spider.step", 0.15F, 1.0F);
	}
	
	public void setBesideClimbableBlock(boolean p_70839_1_)
	{
		byte var2 = dataWatcher.getWatchableObjectByte(16);
		if(p_70839_1_)
		{
			var2 = (byte) (var2 | 1);
		} else
		{
			var2 &= -2;
		}
		dataWatcher.updateObject(16, Byte.valueOf(var2));
	}
	
	@Override public void setInWeb()
	{
	}
	
	public float spiderScaleAmount()
	{
		return 1.0F;
	}
}
