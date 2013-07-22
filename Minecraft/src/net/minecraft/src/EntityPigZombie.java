package net.minecraft.src;

import java.util.List;

public class EntityPigZombie extends EntityZombie
{
	private int angerLevel = 0;
	private int randomSoundDelay = 0;
	
	public EntityPigZombie(World p_i3553_1_)
	{
		super(p_i3553_1_);
		texture = "/mob/pigzombie.png";
		moveSpeed = 0.5F;
		isImmuneToFire = true;
	}
	
	@Override protected void addRandomArmor()
	{
		setCurrentItemOrArmor(0, new ItemStack(Item.swordGold));
	}
	
	@Override public boolean attackEntityFrom(DamageSource p_70097_1_, int p_70097_2_)
	{
		if(isEntityInvulnerable()) return false;
		else
		{
			Entity var3 = p_70097_1_.getEntity();
			if(var3 instanceof EntityPlayer)
			{
				List var4 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(32.0D, 32.0D, 32.0D));
				for(int var5 = 0; var5 < var4.size(); ++var5)
				{
					Entity var6 = (Entity) var4.get(var5);
					if(var6 instanceof EntityPigZombie)
					{
						EntityPigZombie var7 = (EntityPigZombie) var6;
						var7.becomeAngryAt(var3);
					}
				}
				becomeAngryAt(var3);
			}
			return super.attackEntityFrom(p_70097_1_, p_70097_2_);
		}
	}
	
	private void becomeAngryAt(Entity p_70835_1_)
	{
		entityToAttack = p_70835_1_;
		angerLevel = 400 + rand.nextInt(400);
		randomSoundDelay = rand.nextInt(40);
	}
	
	@Override protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		int var3 = rand.nextInt(2 + p_70628_2_);
		int var4;
		for(var4 = 0; var4 < var3; ++var4)
		{
			dropItem(Item.rottenFlesh.itemID, 1);
		}
		var3 = rand.nextInt(2 + p_70628_2_);
		for(var4 = 0; var4 < var3; ++var4)
		{
			dropItem(Item.goldNugget.itemID, 1);
		}
	}
	
	@Override protected void dropRareDrop(int p_70600_1_)
	{
		dropItem(Item.ingotGold.itemID, 1);
	}
	
	@Override protected Entity findPlayerToAttack()
	{
		return angerLevel == 0 ? null : super.findPlayerToAttack();
	}
	
	@Override public int getAttackStrength(Entity p_82193_1_)
	{
		ItemStack var2 = getHeldItem();
		int var3 = 5;
		if(var2 != null)
		{
			var3 += var2.getDamageVsEntity(this);
		}
		return var3;
	}
	
	@Override public boolean getCanSpawnHere()
	{
		return worldObj.difficultySetting > 0 && worldObj.checkNoEntityCollision(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty() && !worldObj.isAnyLiquid(boundingBox);
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.zombiepig.zpigdeath";
	}
	
	@Override protected int getDropItemId()
	{
		return Item.rottenFlesh.itemID;
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.zombiepig.zpighurt";
	}
	
	@Override protected String getLivingSound()
	{
		return "mob.zombiepig.zpig";
	}
	
	@Override public String getTexture()
	{
		return "/mob/pigzombie.png";
	}
	
	@Override public void initCreature()
	{
		super.initCreature();
		setVillager(false);
	}
	
	@Override public boolean interact(EntityPlayer p_70085_1_)
	{
		return false;
	}
	
	@Override protected boolean isAIEnabled()
	{
		return false;
	}
	
	@Override public void onUpdate()
	{
		moveSpeed = entityToAttack != null ? 0.95F : 0.5F;
		if(randomSoundDelay > 0 && --randomSoundDelay == 0)
		{
			playSound("mob.zombiepig.zpigangry", getSoundVolume() * 2.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
		}
		super.onUpdate();
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		angerLevel = p_70037_1_.getShort("Anger");
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setShort("Anger", (short) angerLevel);
	}
}
