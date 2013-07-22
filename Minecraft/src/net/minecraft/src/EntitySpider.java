package net.minecraft.src;

public class EntitySpider extends EntityMob
{
	public EntitySpider(World par1World)
	{
		super(par1World);
		setSize(1.4F, 0.9F);
	}
	
	@Override protected void attackEntity(Entity par1Entity, float par2)
	{
		float var3 = getBrightness(1.0F);
		if(var3 > 0.5F && rand.nextInt(100) == 0)
		{
			entityToAttack = null;
		} else
		{
			if(par2 > 2.0F && par2 < 6.0F && rand.nextInt(10) == 0)
			{
				if(onGround)
				{
					double var4 = par1Entity.posX - posX;
					double var6 = par1Entity.posZ - posZ;
					float var8 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
					motionX = var4 / var8 * 0.5D * 0.800000011920929D + motionX * 0.20000000298023224D;
					motionZ = var6 / var8 * 0.5D * 0.800000011920929D + motionZ * 0.20000000298023224D;
					motionY = 0.4000000059604645D;
				}
			} else
			{
				super.attackEntity(par1Entity, par2);
			}
		}
	}
	
	@Override protected void dropFewItems(boolean par1, int par2)
	{
		super.dropFewItems(par1, par2);
		if(par1 && (rand.nextInt(3) == 0 || rand.nextInt(1 + par2) > 0))
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
	
	@Override protected void func_110147_ax()
	{
		super.func_110147_ax();
		func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(16.0D);
		func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.800000011920929D);
	}
	
	@Override public EntityLivingData func_110161_a(EntityLivingData par1EntityLivingData)
	{
		Object par1EntityLivingData1 = super.func_110161_a(par1EntityLivingData);
		if(worldObj.rand.nextInt(100) == 0)
		{
			EntitySkeleton var2 = new EntitySkeleton(worldObj);
			var2.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
			var2.func_110161_a((EntityLivingData) null);
			worldObj.spawnEntityInWorld(var2);
			var2.mountEntity(this);
		}
		if(par1EntityLivingData1 == null)
		{
			par1EntityLivingData1 = new SpiderEffectsGroupData();
			if(worldObj.difficultySetting > 2 && worldObj.rand.nextFloat() < 0.1F * worldObj.func_110746_b(posX, posY, posZ))
			{
				((SpiderEffectsGroupData) par1EntityLivingData1).func_111104_a(worldObj.rand);
			}
		}
		if(par1EntityLivingData1 instanceof SpiderEffectsGroupData)
		{
			int var4 = ((SpiderEffectsGroupData) par1EntityLivingData1).field_111105_a;
			if(var4 > 0 && Potion.potionTypes[var4] != null)
			{
				addPotionEffect(new PotionEffect(var4, Integer.MAX_VALUE));
			}
		}
		return (EntityLivingData) par1EntityLivingData1;
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
	
	public boolean isBesideClimbableBlock()
	{
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}
	
	@Override public boolean isOnLadder()
	{
		return isBesideClimbableBlock();
	}
	
	@Override public boolean isPotionApplicable(PotionEffect par1PotionEffect)
	{
		return par1PotionEffect.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(par1PotionEffect);
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		if(!worldObj.isRemote)
		{
			setBesideClimbableBlock(isCollidedHorizontally);
		}
	}
	
	@Override protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		playSound("mob.spider.step", 0.15F, 1.0F);
	}
	
	public void setBesideClimbableBlock(boolean par1)
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
	
	@Override public void setInWeb()
	{
	}
}
