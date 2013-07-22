package net.minecraft.src;

public class EntitySquid extends EntityWaterMob
{
	public float squidPitch;
	public float prevSquidPitch;
	public float squidYaw;
	public float prevSquidYaw;
	public float field_70867_h;
	public float field_70868_i;
	public float tentacleAngle;
	public float prevTentacleAngle;
	private float randomMotionSpeed;
	private float field_70864_bA;
	private float field_70871_bB;
	private float randomMotionVecX;
	private float randomMotionVecY;
	private float randomMotionVecZ;
	
	public EntitySquid(World par1World)
	{
		super(par1World);
		setSize(0.95F, 0.95F);
		field_70864_bA = 1.0F / (rand.nextFloat() + 1.0F) * 0.2F;
	}
	
	@Override protected boolean canTriggerWalking()
	{
		return false;
	}
	
	@Override protected void dropFewItems(boolean par1, int par2)
	{
		int var3 = rand.nextInt(3 + par2) + 1;
		for(int var4 = 0; var4 < var3; ++var4)
		{
			entityDropItem(new ItemStack(Item.dyePowder, 1, 0), 0.0F);
		}
	}
	
	@Override protected void func_110147_ax()
	{
		super.func_110147_ax();
		func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0D);
	}
	
	@Override public boolean getCanSpawnHere()
	{
		return posY > 45.0D && posY < 63.0D && super.getCanSpawnHere();
	}
	
	@Override protected String getDeathSound()
	{
		return null;
	}
	
	@Override protected int getDropItemId()
	{
		return 0;
	}
	
	@Override protected String getHurtSound()
	{
		return null;
	}
	
	@Override protected String getLivingSound()
	{
		return null;
	}
	
	@Override protected float getSoundVolume()
	{
		return 0.4F;
	}
	
	@Override public boolean isInWater()
	{
		return worldObj.handleMaterialAcceleration(boundingBox.expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, this);
	}
	
	@Override public void moveEntityWithHeading(float par1, float par2)
	{
		moveEntity(motionX, motionY, motionZ);
	}
	
	@Override public void onLivingUpdate()
	{
		super.onLivingUpdate();
		prevSquidPitch = squidPitch;
		prevSquidYaw = squidYaw;
		field_70868_i = field_70867_h;
		prevTentacleAngle = tentacleAngle;
		field_70867_h += field_70864_bA;
		if(field_70867_h > (float) Math.PI * 2F)
		{
			field_70867_h -= (float) Math.PI * 2F;
			if(rand.nextInt(10) == 0)
			{
				field_70864_bA = 1.0F / (rand.nextFloat() + 1.0F) * 0.2F;
			}
		}
		if(isInWater())
		{
			float var1;
			if(field_70867_h < (float) Math.PI)
			{
				var1 = field_70867_h / (float) Math.PI;
				tentacleAngle = MathHelper.sin(var1 * var1 * (float) Math.PI) * (float) Math.PI * 0.25F;
				if(var1 > 0.75D)
				{
					randomMotionSpeed = 1.0F;
					field_70871_bB = 1.0F;
				} else
				{
					field_70871_bB *= 0.8F;
				}
			} else
			{
				tentacleAngle = 0.0F;
				randomMotionSpeed *= 0.9F;
				field_70871_bB *= 0.99F;
			}
			if(!worldObj.isRemote)
			{
				motionX = randomMotionVecX * randomMotionSpeed;
				motionY = randomMotionVecY * randomMotionSpeed;
				motionZ = randomMotionVecZ * randomMotionSpeed;
			}
			var1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			renderYawOffset += (-((float) Math.atan2(motionX, motionZ)) * 180.0F / (float) Math.PI - renderYawOffset) * 0.1F;
			rotationYaw = renderYawOffset;
			squidYaw += (float) Math.PI * field_70871_bB * 1.5F;
			squidPitch += (-((float) Math.atan2(var1, motionY)) * 180.0F / (float) Math.PI - squidPitch) * 0.1F;
		} else
		{
			tentacleAngle = MathHelper.abs(MathHelper.sin(field_70867_h)) * (float) Math.PI * 0.25F;
			if(!worldObj.isRemote)
			{
				motionX = 0.0D;
				motionY -= 0.08D;
				motionY *= 0.9800000190734863D;
				motionZ = 0.0D;
			}
			squidPitch = (float) (squidPitch + (-90.0F - squidPitch) * 0.02D);
		}
	}
	
	@Override protected void updateEntityActionState()
	{
		++entityAge;
		if(entityAge > 100)
		{
			randomMotionVecX = randomMotionVecY = randomMotionVecZ = 0.0F;
		} else if(rand.nextInt(50) == 0 || !inWater || randomMotionVecX == 0.0F && randomMotionVecY == 0.0F && randomMotionVecZ == 0.0F)
		{
			float var1 = rand.nextFloat() * (float) Math.PI * 2.0F;
			randomMotionVecX = MathHelper.cos(var1) * 0.2F;
			randomMotionVecY = -0.1F + rand.nextFloat() * 0.2F;
			randomMotionVecZ = MathHelper.sin(var1) * 0.2F;
		}
		despawnEntity();
	}
}
