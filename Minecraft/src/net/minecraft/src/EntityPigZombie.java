package net.minecraft.src;

import java.util.List;

public class EntityPigZombie extends EntityZombie
{
	private int angerLevel = 0;
	private int randomSoundDelay = 0;
	
	public EntityPigZombie(World par1World)
	{
		super(par1World);
		texture = "/mob/pigzombie.png";
		moveSpeed = 0.5F;
		isImmuneToFire = true;
	}
	
	@Override protected void addRandomArmor()
	{
		setCurrentItemOrArmor(0, new ItemStack(Item.swordGold));
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		if(isEntityInvulnerable()) return false;
		else
		{
			Entity var3 = par1DamageSource.getEntity();
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
			return super.attackEntityFrom(par1DamageSource, par2);
		}
	}
	
	private void becomeAngryAt(Entity par1Entity)
	{
		entityToAttack = par1Entity;
		angerLevel = 400 + rand.nextInt(400);
		randomSoundDelay = rand.nextInt(40);
	}
	
	@Override protected void dropFewItems(boolean par1, int par2)
	{
		int var3 = rand.nextInt(2 + par2);
		int var4;
		for(var4 = 0; var4 < var3; ++var4)
		{
			dropItem(Item.rottenFlesh.itemID, 1);
		}
		var3 = rand.nextInt(2 + par2);
		for(var4 = 0; var4 < var3; ++var4)
		{
			dropItem(Item.goldNugget.itemID, 1);
		}
	}
	
	@Override protected void dropRareDrop(int par1)
	{
		dropItem(Item.ingotGold.itemID, 1);
	}
	
	@Override protected Entity findPlayerToAttack()
	{
		return angerLevel == 0 ? null : super.findPlayerToAttack();
	}
	
	@Override public int getAttackStrength(Entity par1Entity)
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
	
	@Override public boolean interact(EntityPlayer par1EntityPlayer)
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
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		angerLevel = par1NBTTagCompound.getShort("Anger");
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("Anger", (short) angerLevel);
	}
}
