package net.minecraft.src;

public class EntitySilverfish extends EntityMob
{
	private int allySummonCooldown;
	
	public EntitySilverfish(World p_i3554_1_)
	{
		super(p_i3554_1_);
		texture = "/mob/silverfish.png";
		setSize(0.3F, 0.7F);
		moveSpeed = 0.6F;
	}
	
	@Override protected void attackEntity(Entity p_70785_1_, float p_70785_2_)
	{
		if(attackTime <= 0 && p_70785_2_ < 1.2F && p_70785_1_.boundingBox.maxY > boundingBox.minY && p_70785_1_.boundingBox.minY < boundingBox.maxY)
		{
			attackTime = 20;
			attackEntityAsMob(p_70785_1_);
		}
	}
	
	@Override public boolean attackEntityFrom(DamageSource p_70097_1_, int p_70097_2_)
	{
		if(isEntityInvulnerable()) return false;
		else
		{
			if(allySummonCooldown <= 0 && (p_70097_1_ instanceof EntityDamageSource || p_70097_1_ == DamageSource.magic))
			{
				allySummonCooldown = 20;
			}
			return super.attackEntityFrom(p_70097_1_, p_70097_2_);
		}
	}
	
	@Override protected boolean canTriggerWalking()
	{
		return false;
	}
	
	@Override protected Entity findPlayerToAttack()
	{
		double var1 = 8.0D;
		return worldObj.getClosestVulnerablePlayerToEntity(this, var1);
	}
	
	@Override public int getAttackStrength(Entity p_82193_1_)
	{
		return 1;
	}
	
	@Override public float getBlockPathWeight(int p_70783_1_, int p_70783_2_, int p_70783_3_)
	{
		return worldObj.getBlockId(p_70783_1_, p_70783_2_ - 1, p_70783_3_) == Block.stone.blockID ? 10.0F : super.getBlockPathWeight(p_70783_1_, p_70783_2_, p_70783_3_);
	}
	
	@Override public boolean getCanSpawnHere()
	{
		if(super.getCanSpawnHere())
		{
			EntityPlayer var1 = worldObj.getClosestPlayerToEntity(this, 5.0D);
			return var1 == null;
		} else return false;
	}
	
	@Override public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.ARTHROPOD;
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.silverfish.kill";
	}
	
	@Override protected int getDropItemId()
	{
		return 0;
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.silverfish.hit";
	}
	
	@Override protected String getLivingSound()
	{
		return "mob.silverfish.say";
	}
	
	@Override public int getMaxHealth()
	{
		return 8;
	}
	
	@Override protected boolean isValidLightLevel()
	{
		return true;
	}
	
	@Override public void onUpdate()
	{
		renderYawOffset = rotationYaw;
		super.onUpdate();
	}
	
	@Override protected void playStepSound(int p_70036_1_, int p_70036_2_, int p_70036_3_, int p_70036_4_)
	{
		playSound("mob.silverfish.step", 0.15F, 1.0F);
	}
	
	@Override protected void updateEntityActionState()
	{
		super.updateEntityActionState();
		if(!worldObj.isRemote)
		{
			int var1;
			int var2;
			int var3;
			int var5;
			if(allySummonCooldown > 0)
			{
				--allySummonCooldown;
				if(allySummonCooldown == 0)
				{
					var1 = MathHelper.floor_double(posX);
					var2 = MathHelper.floor_double(posY);
					var3 = MathHelper.floor_double(posZ);
					boolean var4 = false;
					for(var5 = 0; !var4 && var5 <= 5 && var5 >= -5; var5 = var5 <= 0 ? 1 - var5 : 0 - var5)
					{
						for(int var6 = 0; !var4 && var6 <= 10 && var6 >= -10; var6 = var6 <= 0 ? 1 - var6 : 0 - var6)
						{
							for(int var7 = 0; !var4 && var7 <= 10 && var7 >= -10; var7 = var7 <= 0 ? 1 - var7 : 0 - var7)
							{
								int var8 = worldObj.getBlockId(var1 + var6, var2 + var5, var3 + var7);
								if(var8 == Block.silverfish.blockID)
								{
									worldObj.destroyBlock(var1 + var6, var2 + var5, var3 + var7, false);
									Block.silverfish.onBlockDestroyedByPlayer(worldObj, var1 + var6, var2 + var5, var3 + var7, 0);
									if(rand.nextBoolean())
									{
										var4 = true;
										break;
									}
								}
							}
						}
					}
				}
			}
			if(entityToAttack == null && !hasPath())
			{
				var1 = MathHelper.floor_double(posX);
				var2 = MathHelper.floor_double(posY + 0.5D);
				var3 = MathHelper.floor_double(posZ);
				int var9 = rand.nextInt(6);
				var5 = worldObj.getBlockId(var1 + Facing.offsetsXForSide[var9], var2 + Facing.offsetsYForSide[var9], var3 + Facing.offsetsZForSide[var9]);
				if(BlockSilverfish.getPosingIdByMetadata(var5))
				{
					worldObj.setBlock(var1 + Facing.offsetsXForSide[var9], var2 + Facing.offsetsYForSide[var9], var3 + Facing.offsetsZForSide[var9], Block.silverfish.blockID, BlockSilverfish.getMetadataForBlockType(var5), 3);
					spawnExplosionParticle();
					setDead();
				} else
				{
					updateWanderPath();
				}
			} else if(entityToAttack != null && !hasPath())
			{
				entityToAttack = null;
			}
		}
	}
}
