package net.minecraft.src;

public class EntitySnowman extends EntityGolem implements IRangedAttackMob
{
	public EntitySnowman(World p_i3522_1_)
	{
		super(p_i3522_1_);
		texture = "/mob/snowman.png";
		setSize(0.4F, 1.8F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(1, new EntityAIArrowAttack(this, 0.25F, 20, 10.0F));
		tasks.addTask(2, new EntityAIWander(this, 0.2F));
		tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(4, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 16.0F, 0, true, false, IMob.mobSelector));
	}
	
	@Override public void attackEntityWithRangedAttack(EntityLiving p_82196_1_, float p_82196_2_)
	{
		EntitySnowball var3 = new EntitySnowball(worldObj, this);
		double var4 = p_82196_1_.posX - posX;
		double var6 = p_82196_1_.posY + p_82196_1_.getEyeHeight() - 1.100000023841858D - var3.posY;
		double var8 = p_82196_1_.posZ - posZ;
		float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.2F;
		var3.setThrowableHeading(var4, var6 + var10, var8, 1.6F, 12.0F);
		playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
		worldObj.spawnEntityInWorld(var3);
	}
	
	@Override protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		int var3 = rand.nextInt(16);
		for(int var4 = 0; var4 < var3; ++var4)
		{
			dropItem(Item.snowball.itemID, 1);
		}
	}
	
	@Override protected int getDropItemId()
	{
		return Item.snowball.itemID;
	}
	
	@Override public int getMaxHealth()
	{
		return 4;
	}
	
	@Override public boolean isAIEnabled()
	{
		return true;
	}
	
	@Override public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(isWet())
		{
			attackEntityFrom(DamageSource.drown, 1);
		}
		int var1 = MathHelper.floor_double(posX);
		int var2 = MathHelper.floor_double(posZ);
		if(worldObj.getBiomeGenForCoords(var1, var2).getFloatTemperature() > 1.0F)
		{
			attackEntityFrom(DamageSource.onFire, 1);
		}
		for(var1 = 0; var1 < 4; ++var1)
		{
			var2 = MathHelper.floor_double(posX + (var1 % 2 * 2 - 1) * 0.25F);
			int var3 = MathHelper.floor_double(posY);
			int var4 = MathHelper.floor_double(posZ + (var1 / 2 % 2 * 2 - 1) * 0.25F);
			if(worldObj.getBlockId(var2, var3, var4) == 0 && worldObj.getBiomeGenForCoords(var2, var4).getFloatTemperature() < 0.8F && Block.snow.canPlaceBlockAt(worldObj, var2, var3, var4))
			{
				worldObj.setBlock(var2, var3, var4, Block.snow.blockID);
			}
		}
	}
}
