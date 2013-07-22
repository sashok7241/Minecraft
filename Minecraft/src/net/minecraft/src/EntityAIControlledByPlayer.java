package net.minecraft.src;

public class EntityAIControlledByPlayer extends EntityAIBase
{
	private final EntityLiving thisEntity;
	private final float maxSpeed;
	private float currentSpeed = 0.0F;
	private boolean speedBoosted = false;
	private int speedBoostTime = 0;
	private int maxSpeedBoostTime = 0;
	
	public EntityAIControlledByPlayer(EntityLiving p_i5058_1_, float p_i5058_2_)
	{
		thisEntity = p_i5058_1_;
		maxSpeed = p_i5058_2_;
		setMutexBits(7);
	}
	
	public void boostSpeed()
	{
		speedBoosted = true;
		speedBoostTime = 0;
		maxSpeedBoostTime = thisEntity.getRNG().nextInt(841) + 140;
	}
	
	private boolean func_98216_b(int p_98216_1_)
	{
		return Block.blocksList[p_98216_1_] != null && (Block.blocksList[p_98216_1_].getRenderType() == 10 || Block.blocksList[p_98216_1_] instanceof BlockHalfSlab);
	}
	
	public boolean isControlledByPlayer()
	{
		return !isSpeedBoosted() && currentSpeed > maxSpeed * 0.3F;
	}
	
	public boolean isSpeedBoosted()
	{
		return speedBoosted;
	}
	
	@Override public void resetTask()
	{
		speedBoosted = false;
		currentSpeed = 0.0F;
	}
	
	@Override public boolean shouldExecute()
	{
		return thisEntity.isEntityAlive() && thisEntity.riddenByEntity != null && thisEntity.riddenByEntity instanceof EntityPlayer && (speedBoosted || thisEntity.canBeSteered());
	}
	
	@Override public void startExecuting()
	{
		currentSpeed = 0.0F;
	}
	
	@Override public void updateTask()
	{
		EntityPlayer var1 = (EntityPlayer) thisEntity.riddenByEntity;
		EntityCreature var2 = (EntityCreature) thisEntity;
		float var3 = MathHelper.wrapAngleTo180_float(var1.rotationYaw - thisEntity.rotationYaw) * 0.5F;
		if(var3 > 5.0F)
		{
			var3 = 5.0F;
		}
		if(var3 < -5.0F)
		{
			var3 = -5.0F;
		}
		thisEntity.rotationYaw = MathHelper.wrapAngleTo180_float(thisEntity.rotationYaw + var3);
		if(currentSpeed < maxSpeed)
		{
			currentSpeed += (maxSpeed - currentSpeed) * 0.01F;
		}
		if(currentSpeed > maxSpeed)
		{
			currentSpeed = maxSpeed;
		}
		int var4 = MathHelper.floor_double(thisEntity.posX);
		int var5 = MathHelper.floor_double(thisEntity.posY);
		int var6 = MathHelper.floor_double(thisEntity.posZ);
		float var7 = currentSpeed;
		if(speedBoosted)
		{
			if(speedBoostTime++ > maxSpeedBoostTime)
			{
				speedBoosted = false;
			}
			var7 += var7 * 1.15F * MathHelper.sin((float) speedBoostTime / (float) maxSpeedBoostTime * (float) Math.PI);
		}
		float var8 = 0.91F;
		if(thisEntity.onGround)
		{
			var8 = 0.54600006F;
			int var9 = thisEntity.worldObj.getBlockId(MathHelper.floor_float(var4), MathHelper.floor_float(var5) - 1, MathHelper.floor_float(var6));
			if(var9 > 0)
			{
				var8 = Block.blocksList[var9].slipperiness * 0.91F;
			}
		}
		float var23 = 0.16277136F / (var8 * var8 * var8);
		float var10 = MathHelper.sin(var2.rotationYaw * (float) Math.PI / 180.0F);
		float var11 = MathHelper.cos(var2.rotationYaw * (float) Math.PI / 180.0F);
		float var12 = var2.getAIMoveSpeed() * var23;
		float var13 = Math.max(var7, 1.0F);
		var13 = var12 / var13;
		float var14 = var7 * var13;
		float var15 = -(var14 * var10);
		float var16 = var14 * var11;
		if(MathHelper.abs(var15) > MathHelper.abs(var16))
		{
			if(var15 < 0.0F)
			{
				var15 -= thisEntity.width / 2.0F;
			}
			if(var15 > 0.0F)
			{
				var15 += thisEntity.width / 2.0F;
			}
			var16 = 0.0F;
		} else
		{
			var15 = 0.0F;
			if(var16 < 0.0F)
			{
				var16 -= thisEntity.width / 2.0F;
			}
			if(var16 > 0.0F)
			{
				var16 += thisEntity.width / 2.0F;
			}
		}
		int var17 = MathHelper.floor_double(thisEntity.posX + var15);
		int var18 = MathHelper.floor_double(thisEntity.posZ + var16);
		PathPoint var19 = new PathPoint(MathHelper.floor_float(thisEntity.width + 1.0F), MathHelper.floor_float(thisEntity.height + var1.height + 1.0F), MathHelper.floor_float(thisEntity.width + 1.0F));
		if(var4 != var17 || var6 != var18)
		{
			int var20 = thisEntity.worldObj.getBlockId(var4, var5, var6);
			int var21 = thisEntity.worldObj.getBlockId(var4, var5 - 1, var6);
			boolean var22 = func_98216_b(var20) || Block.blocksList[var20] == null && func_98216_b(var21);
			if(!var22 && PathFinder.func_82565_a(thisEntity, var17, var5, var18, var19, false, false, true) == 0 && PathFinder.func_82565_a(thisEntity, var4, var5 + 1, var6, var19, false, false, true) == 1 && PathFinder.func_82565_a(thisEntity, var17, var5 + 1, var18, var19, false, false, true) == 1)
			{
				var2.getJumpHelper().setJumping();
			}
		}
		if(!var1.capabilities.isCreativeMode && currentSpeed >= maxSpeed * 0.5F && thisEntity.getRNG().nextFloat() < 0.006F && !speedBoosted)
		{
			ItemStack var24 = var1.getHeldItem();
			if(var24 != null && var24.itemID == Item.carrotOnAStick.itemID)
			{
				var24.damageItem(1, var1);
				if(var24.stackSize == 0)
				{
					ItemStack var25 = new ItemStack(Item.fishingRod);
					var25.setTagCompound(var24.stackTagCompound);
					var1.inventory.mainInventory[var1.inventory.currentItem] = var25;
				}
			}
		}
		thisEntity.moveEntityWithHeading(0.0F, var7);
	}
}
