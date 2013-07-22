package net.minecraft.src;

import java.util.List;

public abstract class EntityAnimal extends EntityAgeable implements IAnimals
{
	private int inLove;
	private int breeding = 0;
	
	public EntityAnimal(World p_i3514_1_)
	{
		super(p_i3514_1_);
	}
	
	@Override protected void attackEntity(Entity p_70785_1_, float p_70785_2_)
	{
		if(p_70785_1_ instanceof EntityPlayer)
		{
			if(p_70785_2_ < 3.0F)
			{
				double var3 = p_70785_1_.posX - posX;
				double var5 = p_70785_1_.posZ - posZ;
				rotationYaw = (float) (Math.atan2(var5, var3) * 180.0D / Math.PI) - 90.0F;
				hasAttacked = true;
			}
			EntityPlayer var7 = (EntityPlayer) p_70785_1_;
			if(var7.getCurrentEquippedItem() == null || !isBreedingItem(var7.getCurrentEquippedItem()))
			{
				entityToAttack = null;
			}
		} else if(p_70785_1_ instanceof EntityAnimal)
		{
			EntityAnimal var8 = (EntityAnimal) p_70785_1_;
			if(getGrowingAge() > 0 && var8.getGrowingAge() < 0)
			{
				if(p_70785_2_ < 2.5D)
				{
					hasAttacked = true;
				}
			} else if(inLove > 0 && var8.inLove > 0)
			{
				if(var8.entityToAttack == null)
				{
					var8.entityToAttack = this;
				}
				if(var8.entityToAttack == this && p_70785_2_ < 3.5D)
				{
					++var8.inLove;
					++inLove;
					++breeding;
					if(breeding % 4 == 0)
					{
						worldObj.spawnParticle("heart", posX + rand.nextFloat() * width * 2.0F - width, posY + 0.5D + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, 0.0D, 0.0D, 0.0D);
					}
					if(breeding == 60)
					{
						procreate((EntityAnimal) p_70785_1_);
					}
				} else
				{
					breeding = 0;
				}
			} else
			{
				breeding = 0;
				entityToAttack = null;
			}
		}
	}
	
	@Override public boolean attackEntityFrom(DamageSource p_70097_1_, int p_70097_2_)
	{
		if(isEntityInvulnerable()) return false;
		else
		{
			fleeingTick = 60;
			entityToAttack = null;
			inLove = 0;
			return super.attackEntityFrom(p_70097_1_, p_70097_2_);
		}
	}
	
	@Override protected boolean canDespawn()
	{
		return false;
	}
	
	public boolean canMateWith(EntityAnimal p_70878_1_)
	{
		return p_70878_1_ == this ? false : p_70878_1_.getClass() != this.getClass() ? false : isInLove() && p_70878_1_.isInLove();
	}
	
	@Override protected Entity findPlayerToAttack()
	{
		if(fleeingTick > 0) return null;
		else
		{
			float var1 = 8.0F;
			List var2;
			int var3;
			EntityAnimal var4;
			if(inLove > 0)
			{
				var2 = worldObj.getEntitiesWithinAABB(this.getClass(), boundingBox.expand(var1, var1, var1));
				for(var3 = 0; var3 < var2.size(); ++var3)
				{
					var4 = (EntityAnimal) var2.get(var3);
					if(var4 != this && var4.inLove > 0) return var4;
				}
			} else if(getGrowingAge() == 0)
			{
				var2 = worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(var1, var1, var1));
				for(var3 = 0; var3 < var2.size(); ++var3)
				{
					EntityPlayer var5 = (EntityPlayer) var2.get(var3);
					if(var5.getCurrentEquippedItem() != null && isBreedingItem(var5.getCurrentEquippedItem())) return var5;
				}
			} else if(getGrowingAge() > 0)
			{
				var2 = worldObj.getEntitiesWithinAABB(this.getClass(), boundingBox.expand(var1, var1, var1));
				for(var3 = 0; var3 < var2.size(); ++var3)
				{
					var4 = (EntityAnimal) var2.get(var3);
					if(var4 != this && var4.getGrowingAge() < 0) return var4;
				}
			}
			return null;
		}
	}
	
	@Override public float getBlockPathWeight(int p_70783_1_, int p_70783_2_, int p_70783_3_)
	{
		return worldObj.getBlockId(p_70783_1_, p_70783_2_ - 1, p_70783_3_) == Block.grass.blockID ? 10.0F : worldObj.getLightBrightness(p_70783_1_, p_70783_2_, p_70783_3_) - 0.5F;
	}
	
	@Override public boolean getCanSpawnHere()
	{
		int var1 = MathHelper.floor_double(posX);
		int var2 = MathHelper.floor_double(boundingBox.minY);
		int var3 = MathHelper.floor_double(posZ);
		return worldObj.getBlockId(var1, var2 - 1, var3) == Block.grass.blockID && worldObj.getFullBlockLightValue(var1, var2, var3) > 8 && super.getCanSpawnHere();
	}
	
	@Override protected int getExperiencePoints(EntityPlayer p_70693_1_)
	{
		return 1 + worldObj.rand.nextInt(3);
	}
	
	@Override public int getTalkInterval()
	{
		return 120;
	}
	
	@Override public boolean interact(EntityPlayer p_70085_1_)
	{
		ItemStack var2 = p_70085_1_.inventory.getCurrentItem();
		if(var2 != null && isBreedingItem(var2) && getGrowingAge() == 0 && inLove <= 0)
		{
			if(!p_70085_1_.capabilities.isCreativeMode)
			{
				--var2.stackSize;
				if(var2.stackSize <= 0)
				{
					p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, (ItemStack) null);
				}
			}
			inLove = 600;
			entityToAttack = null;
			for(int var3 = 0; var3 < 7; ++var3)
			{
				double var4 = rand.nextGaussian() * 0.02D;
				double var6 = rand.nextGaussian() * 0.02D;
				double var8 = rand.nextGaussian() * 0.02D;
				worldObj.spawnParticle("heart", posX + rand.nextFloat() * width * 2.0F - width, posY + 0.5D + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, var4, var6, var8);
			}
			return true;
		} else return super.interact(p_70085_1_);
	}
	
	public boolean isBreedingItem(ItemStack p_70877_1_)
	{
		return p_70877_1_.itemID == Item.wheat.itemID;
	}
	
	public boolean isInLove()
	{
		return inLove > 0;
	}
	
	@Override public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(getGrowingAge() != 0)
		{
			inLove = 0;
		}
		if(inLove > 0)
		{
			--inLove;
			String var1 = "heart";
			if(inLove % 10 == 0)
			{
				double var2 = rand.nextGaussian() * 0.02D;
				double var4 = rand.nextGaussian() * 0.02D;
				double var6 = rand.nextGaussian() * 0.02D;
				worldObj.spawnParticle(var1, posX + rand.nextFloat() * width * 2.0F - width, posY + 0.5D + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, var2, var4, var6);
			}
		} else
		{
			breeding = 0;
		}
	}
	
	private void procreate(EntityAnimal p_70876_1_)
	{
		EntityAgeable var2 = createChild(p_70876_1_);
		if(var2 != null)
		{
			setGrowingAge(6000);
			p_70876_1_.setGrowingAge(6000);
			inLove = 0;
			breeding = 0;
			entityToAttack = null;
			p_70876_1_.entityToAttack = null;
			p_70876_1_.breeding = 0;
			p_70876_1_.inLove = 0;
			var2.setGrowingAge(-24000);
			var2.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
			for(int var3 = 0; var3 < 7; ++var3)
			{
				double var4 = rand.nextGaussian() * 0.02D;
				double var6 = rand.nextGaussian() * 0.02D;
				double var8 = rand.nextGaussian() * 0.02D;
				worldObj.spawnParticle("heart", posX + rand.nextFloat() * width * 2.0F - width, posY + 0.5D + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, var4, var6, var8);
			}
			worldObj.spawnEntityInWorld(var2);
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		inLove = p_70037_1_.getInteger("InLove");
	}
	
	public void resetInLove()
	{
		inLove = 0;
	}
	
	@Override protected void updateAITick()
	{
		if(getGrowingAge() != 0)
		{
			inLove = 0;
		}
		super.updateAITick();
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setInteger("InLove", inLove);
	}
}
