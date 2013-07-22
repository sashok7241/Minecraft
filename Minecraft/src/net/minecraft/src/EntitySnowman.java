package net.minecraft.src;

public class EntitySnowman extends EntityGolem implements IRangedAttackMob
{
	public EntitySnowman(World par1World)
	{
		super(par1World);
		setSize(0.4F, 1.8F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(1, new EntityAIArrowAttack(this, 1.25D, 20, 10.0F));
		tasks.addTask(2, new EntityAIWander(this, 1.0D));
		tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(4, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, true, false, IMob.mobSelector));
	}
	
	@Override public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2)
	{
		EntitySnowball var3 = new EntitySnowball(worldObj, this);
		double var4 = par1EntityLivingBase.posX - posX;
		double var6 = par1EntityLivingBase.posY + par1EntityLivingBase.getEyeHeight() - 1.100000023841858D - var3.posY;
		double var8 = par1EntityLivingBase.posZ - posZ;
		float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.2F;
		var3.setThrowableHeading(var4, var6 + var10, var8, 1.6F, 12.0F);
		playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
		worldObj.spawnEntityInWorld(var3);
	}
	
	@Override protected void dropFewItems(boolean par1, int par2)
	{
		int var3 = rand.nextInt(16);
		for(int var4 = 0; var4 < var3; ++var4)
		{
			dropItem(Item.snowball.itemID, 1);
		}
	}
	
	@Override protected void func_110147_ax()
	{
		super.func_110147_ax();
		func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(4.0D);
		func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.20000000298023224D);
	}
	
	@Override protected int getDropItemId()
	{
		return Item.snowball.itemID;
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
			attackEntityFrom(DamageSource.drown, 1.0F);
		}
		int var1 = MathHelper.floor_double(posX);
		int var2 = MathHelper.floor_double(posZ);
		if(worldObj.getBiomeGenForCoords(var1, var2).getFloatTemperature() > 1.0F)
		{
			attackEntityFrom(DamageSource.onFire, 1.0F);
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
