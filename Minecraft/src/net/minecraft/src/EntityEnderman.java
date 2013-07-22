package net.minecraft.src;

public class EntityEnderman extends EntityMob
{
	private static boolean[] carriableBlocks = new boolean[256];
	private int teleportDelay = 0;
	private int field_70826_g = 0;
	private boolean field_104003_g;
	
	public EntityEnderman(World par1World)
	{
		super(par1World);
		texture = "/mob/enderman.png";
		moveSpeed = 0.2F;
		setSize(0.6F, 2.9F);
		stepHeight = 1.0F;
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		if(isEntityInvulnerable()) return false;
		else
		{
			setScreaming(true);
			if(par1DamageSource instanceof EntityDamageSource && par1DamageSource.getEntity() instanceof EntityPlayer)
			{
				field_104003_g = true;
			}
			if(par1DamageSource instanceof EntityDamageSourceIndirect)
			{
				field_104003_g = false;
				for(int var3 = 0; var3 < 64; ++var3)
				{
					if(teleportRandomly()) return true;
				}
				return false;
			} else return super.attackEntityFrom(par1DamageSource, par2);
		}
	}
	
	@Override protected void dropFewItems(boolean par1, int par2)
	{
		int var3 = getDropItemId();
		if(var3 > 0)
		{
			int var4 = rand.nextInt(2 + par2);
			for(int var5 = 0; var5 < var4; ++var5)
			{
				dropItem(var3, 1);
			}
		}
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, new Byte((byte) 0));
		dataWatcher.addObject(17, new Byte((byte) 0));
		dataWatcher.addObject(18, new Byte((byte) 0));
	}
	
	@Override protected Entity findPlayerToAttack()
	{
		EntityPlayer var1 = worldObj.getClosestVulnerablePlayerToEntity(this, 64.0D);
		if(var1 != null)
		{
			if(shouldAttackPlayer(var1))
			{
				field_104003_g = true;
				if(field_70826_g == 0)
				{
					worldObj.playSoundAtEntity(var1, "mob.endermen.stare", 1.0F, 1.0F);
				}
				if(field_70826_g++ == 5)
				{
					field_70826_g = 0;
					setScreaming(true);
					return var1;
				}
			} else
			{
				field_70826_g = 0;
			}
		}
		return null;
	}
	
	@Override public int getAttackStrength(Entity par1Entity)
	{
		return 7;
	}
	
	public int getCarried()
	{
		return dataWatcher.getWatchableObjectByte(16);
	}
	
	public int getCarryingData()
	{
		return dataWatcher.getWatchableObjectByte(17);
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.endermen.death";
	}
	
	@Override protected int getDropItemId()
	{
		return Item.enderPearl.itemID;
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.endermen.hit";
	}
	
	@Override protected String getLivingSound()
	{
		return isScreaming() ? "mob.endermen.scream" : "mob.endermen.idle";
	}
	
	@Override public int getMaxHealth()
	{
		return 40;
	}
	
	public boolean isScreaming()
	{
		return dataWatcher.getWatchableObjectByte(18) > 0;
	}
	
	@Override public void onLivingUpdate()
	{
		if(isWet())
		{
			attackEntityFrom(DamageSource.drown, 1);
		}
		moveSpeed = entityToAttack != null ? 6.5F : 0.3F;
		int var1;
		if(!worldObj.isRemote && worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
		{
			int var2;
			int var3;
			int var4;
			if(getCarried() == 0)
			{
				if(rand.nextInt(20) == 0)
				{
					var1 = MathHelper.floor_double(posX - 2.0D + rand.nextDouble() * 4.0D);
					var2 = MathHelper.floor_double(posY + rand.nextDouble() * 3.0D);
					var3 = MathHelper.floor_double(posZ - 2.0D + rand.nextDouble() * 4.0D);
					var4 = worldObj.getBlockId(var1, var2, var3);
					if(carriableBlocks[var4])
					{
						setCarried(worldObj.getBlockId(var1, var2, var3));
						setCarryingData(worldObj.getBlockMetadata(var1, var2, var3));
						worldObj.setBlock(var1, var2, var3, 0);
					}
				}
			} else if(rand.nextInt(2000) == 0)
			{
				var1 = MathHelper.floor_double(posX - 1.0D + rand.nextDouble() * 2.0D);
				var2 = MathHelper.floor_double(posY + rand.nextDouble() * 2.0D);
				var3 = MathHelper.floor_double(posZ - 1.0D + rand.nextDouble() * 2.0D);
				var4 = worldObj.getBlockId(var1, var2, var3);
				int var5 = worldObj.getBlockId(var1, var2 - 1, var3);
				if(var4 == 0 && var5 > 0 && Block.blocksList[var5].renderAsNormalBlock())
				{
					worldObj.setBlock(var1, var2, var3, getCarried(), getCarryingData(), 3);
					setCarried(0);
				}
			}
		}
		for(var1 = 0; var1 < 2; ++var1)
		{
			worldObj.spawnParticle("portal", posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height - 0.25D, posZ + (rand.nextDouble() - 0.5D) * width, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D);
		}
		if(worldObj.isDaytime() && !worldObj.isRemote)
		{
			float var6 = getBrightness(1.0F);
			if(var6 > 0.5F && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) && rand.nextFloat() * 30.0F < (var6 - 0.4F) * 2.0F)
			{
				entityToAttack = null;
				setScreaming(false);
				field_104003_g = false;
				teleportRandomly();
			}
		}
		if(isWet() || isBurning())
		{
			entityToAttack = null;
			setScreaming(false);
			field_104003_g = false;
			teleportRandomly();
		}
		if(isScreaming() && !field_104003_g && rand.nextInt(100) == 0)
		{
			setScreaming(false);
		}
		isJumping = false;
		if(entityToAttack != null)
		{
			faceEntity(entityToAttack, 100.0F, 100.0F);
		}
		if(!worldObj.isRemote && isEntityAlive())
		{
			if(entityToAttack != null)
			{
				if(entityToAttack instanceof EntityPlayer && shouldAttackPlayer((EntityPlayer) entityToAttack))
				{
					moveStrafing = moveForward = 0.0F;
					moveSpeed = 0.0F;
					if(entityToAttack.getDistanceSqToEntity(this) < 16.0D)
					{
						teleportRandomly();
					}
					teleportDelay = 0;
				} else if(entityToAttack.getDistanceSqToEntity(this) > 256.0D && teleportDelay++ >= 30 && teleportToEntity(entityToAttack))
				{
					teleportDelay = 0;
				}
			} else
			{
				setScreaming(false);
				teleportDelay = 0;
			}
		}
		super.onLivingUpdate();
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		setCarried(par1NBTTagCompound.getShort("carried"));
		setCarryingData(par1NBTTagCompound.getShort("carriedData"));
	}
	
	public void setCarried(int par1)
	{
		dataWatcher.updateObject(16, Byte.valueOf((byte) (par1 & 255)));
	}
	
	public void setCarryingData(int par1)
	{
		dataWatcher.updateObject(17, Byte.valueOf((byte) (par1 & 255)));
	}
	
	public void setScreaming(boolean par1)
	{
		dataWatcher.updateObject(18, Byte.valueOf((byte) (par1 ? 1 : 0)));
	}
	
	private boolean shouldAttackPlayer(EntityPlayer par1EntityPlayer)
	{
		ItemStack var2 = par1EntityPlayer.inventory.armorInventory[3];
		if(var2 != null && var2.itemID == Block.pumpkin.blockID) return false;
		else
		{
			Vec3 var3 = par1EntityPlayer.getLook(1.0F).normalize();
			Vec3 var4 = worldObj.getWorldVec3Pool().getVecFromPool(posX - par1EntityPlayer.posX, boundingBox.minY + height / 2.0F - (par1EntityPlayer.posY + par1EntityPlayer.getEyeHeight()), posZ - par1EntityPlayer.posZ);
			double var5 = var4.lengthVector();
			var4 = var4.normalize();
			double var7 = var3.dotProduct(var4);
			return var7 > 1.0D - 0.025D / var5 ? par1EntityPlayer.canEntityBeSeen(this) : false;
		}
	}
	
	protected boolean teleportRandomly()
	{
		double var1 = posX + (rand.nextDouble() - 0.5D) * 64.0D;
		double var3 = posY + (rand.nextInt(64) - 32);
		double var5 = posZ + (rand.nextDouble() - 0.5D) * 64.0D;
		return teleportTo(var1, var3, var5);
	}
	
	protected boolean teleportTo(double par1, double par3, double par5)
	{
		double var7 = posX;
		double var9 = posY;
		double var11 = posZ;
		posX = par1;
		posY = par3;
		posZ = par5;
		boolean var13 = false;
		int var14 = MathHelper.floor_double(posX);
		int var15 = MathHelper.floor_double(posY);
		int var16 = MathHelper.floor_double(posZ);
		int var18;
		if(worldObj.blockExists(var14, var15, var16))
		{
			boolean var17 = false;
			while(!var17 && var15 > 0)
			{
				var18 = worldObj.getBlockId(var14, var15 - 1, var16);
				if(var18 != 0 && Block.blocksList[var18].blockMaterial.blocksMovement())
				{
					var17 = true;
				} else
				{
					--posY;
					--var15;
				}
			}
			if(var17)
			{
				setPosition(posX, posY, posZ);
				if(worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty() && !worldObj.isAnyLiquid(boundingBox))
				{
					var13 = true;
				}
			}
		}
		if(!var13)
		{
			setPosition(var7, var9, var11);
			return false;
		} else
		{
			short var30 = 128;
			for(var18 = 0; var18 < var30; ++var18)
			{
				double var19 = var18 / (var30 - 1.0D);
				float var21 = (rand.nextFloat() - 0.5F) * 0.2F;
				float var22 = (rand.nextFloat() - 0.5F) * 0.2F;
				float var23 = (rand.nextFloat() - 0.5F) * 0.2F;
				double var24 = var7 + (posX - var7) * var19 + (rand.nextDouble() - 0.5D) * width * 2.0D;
				double var26 = var9 + (posY - var9) * var19 + rand.nextDouble() * height;
				double var28 = var11 + (posZ - var11) * var19 + (rand.nextDouble() - 0.5D) * width * 2.0D;
				worldObj.spawnParticle("portal", var24, var26, var28, var21, var22, var23);
			}
			worldObj.playSoundEffect(var7, var9, var11, "mob.endermen.portal", 1.0F, 1.0F);
			playSound("mob.endermen.portal", 1.0F, 1.0F);
			return true;
		}
	}
	
	protected boolean teleportToEntity(Entity par1Entity)
	{
		Vec3 var2 = worldObj.getWorldVec3Pool().getVecFromPool(posX - par1Entity.posX, boundingBox.minY + height / 2.0F - par1Entity.posY + par1Entity.getEyeHeight(), posZ - par1Entity.posZ);
		var2 = var2.normalize();
		double var3 = 16.0D;
		double var5 = posX + (rand.nextDouble() - 0.5D) * 8.0D - var2.xCoord * var3;
		double var7 = posY + (rand.nextInt(16) - 8) - var2.yCoord * var3;
		double var9 = posZ + (rand.nextDouble() - 0.5D) * 8.0D - var2.zCoord * var3;
		return teleportTo(var5, var7, var9);
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("carried", (short) getCarried());
		par1NBTTagCompound.setShort("carriedData", (short) getCarryingData());
	}
	
	static
	{
		carriableBlocks[Block.grass.blockID] = true;
		carriableBlocks[Block.dirt.blockID] = true;
		carriableBlocks[Block.sand.blockID] = true;
		carriableBlocks[Block.gravel.blockID] = true;
		carriableBlocks[Block.plantYellow.blockID] = true;
		carriableBlocks[Block.plantRed.blockID] = true;
		carriableBlocks[Block.mushroomBrown.blockID] = true;
		carriableBlocks[Block.mushroomRed.blockID] = true;
		carriableBlocks[Block.tnt.blockID] = true;
		carriableBlocks[Block.cactus.blockID] = true;
		carriableBlocks[Block.blockClay.blockID] = true;
		carriableBlocks[Block.pumpkin.blockID] = true;
		carriableBlocks[Block.melon.blockID] = true;
		carriableBlocks[Block.mycelium.blockID] = true;
	}
}
