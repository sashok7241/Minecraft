package net.minecraft.src;

import java.util.List;

public abstract class EntityAnimal extends EntityAgeable implements IAnimals
{
	private int inLove;
	private int breeding = 0;
	
	public EntityAnimal(World par1World)
	{
		super(par1World);
	}
	
	@Override protected void attackEntity(Entity par1Entity, float par2)
	{
		if(par1Entity instanceof EntityPlayer)
		{
			if(par2 < 3.0F)
			{
				double var3 = par1Entity.posX - posX;
				double var5 = par1Entity.posZ - posZ;
				rotationYaw = (float) (Math.atan2(var5, var3) * 180.0D / Math.PI) - 90.0F;
				hasAttacked = true;
			}
			EntityPlayer var7 = (EntityPlayer) par1Entity;
			if(var7.getCurrentEquippedItem() == null || !isBreedingItem(var7.getCurrentEquippedItem()))
			{
				entityToAttack = null;
			}
		} else if(par1Entity instanceof EntityAnimal)
		{
			EntityAnimal var8 = (EntityAnimal) par1Entity;
			if(getGrowingAge() > 0 && var8.getGrowingAge() < 0)
			{
				if(par2 < 2.5D)
				{
					hasAttacked = true;
				}
			} else if(inLove > 0 && var8.inLove > 0)
			{
				if(var8.entityToAttack == null)
				{
					var8.entityToAttack = this;
				}
				if(var8.entityToAttack == this && par2 < 3.5D)
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
						procreate((EntityAnimal) par1Entity);
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
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		if(isEntityInvulnerable()) return false;
		else
		{
			fleeingTick = 60;
			entityToAttack = null;
			inLove = 0;
			return super.attackEntityFrom(par1DamageSource, par2);
		}
	}
	
	@Override protected boolean canDespawn()
	{
		return false;
	}
	
	public boolean canMateWith(EntityAnimal par1EntityAnimal)
	{
		return par1EntityAnimal == this ? false : par1EntityAnimal.getClass() != this.getClass() ? false : isInLove() && par1EntityAnimal.isInLove();
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
	
	@Override public float getBlockPathWeight(int par1, int par2, int par3)
	{
		return worldObj.getBlockId(par1, par2 - 1, par3) == Block.grass.blockID ? 10.0F : worldObj.getLightBrightness(par1, par2, par3) - 0.5F;
	}
	
	@Override public boolean getCanSpawnHere()
	{
		int var1 = MathHelper.floor_double(posX);
		int var2 = MathHelper.floor_double(boundingBox.minY);
		int var3 = MathHelper.floor_double(posZ);
		return worldObj.getBlockId(var1, var2 - 1, var3) == Block.grass.blockID && worldObj.getFullBlockLightValue(var1, var2, var3) > 8 && super.getCanSpawnHere();
	}
	
	@Override protected int getExperiencePoints(EntityPlayer par1EntityPlayer)
	{
		return 1 + worldObj.rand.nextInt(3);
	}
	
	@Override public int getTalkInterval()
	{
		return 120;
	}
	
	@Override public boolean interact(EntityPlayer par1EntityPlayer)
	{
		ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
		if(var2 != null && isBreedingItem(var2) && getGrowingAge() == 0 && inLove <= 0)
		{
			if(!par1EntityPlayer.capabilities.isCreativeMode)
			{
				--var2.stackSize;
				if(var2.stackSize <= 0)
				{
					par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack) null);
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
		} else return super.interact(par1EntityPlayer);
	}
	
	public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return par1ItemStack.itemID == Item.wheat.itemID;
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
	
	private void procreate(EntityAnimal par1EntityAnimal)
	{
		EntityAgeable var2 = createChild(par1EntityAnimal);
		if(var2 != null)
		{
			setGrowingAge(6000);
			par1EntityAnimal.setGrowingAge(6000);
			inLove = 0;
			breeding = 0;
			entityToAttack = null;
			par1EntityAnimal.entityToAttack = null;
			par1EntityAnimal.breeding = 0;
			par1EntityAnimal.inLove = 0;
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
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		inLove = par1NBTTagCompound.getInteger("InLove");
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
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("InLove", inLove);
	}
}
