package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public abstract class EntityMinecart extends Entity
{
	private boolean isInReverse;
	private final IUpdatePlayerListBox field_82344_g;
	private String entityName;
	private static final int[][][] matrix = new int[][][] { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } }, { { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { 1, 0, 0 } } };
	private int turnProgress;
	private double minecartX;
	private double minecartY;
	private double minecartZ;
	private double minecartYaw;
	private double minecartPitch;
	private double velocityX;
	private double velocityY;
	private double velocityZ;
	
	public EntityMinecart(World par1World)
	{
		super(par1World);
		isInReverse = false;
		preventEntitySpawning = true;
		setSize(0.98F, 0.7F);
		yOffset = height / 2.0F;
		field_82344_g = par1World != null ? par1World.func_82735_a(this) : null;
	}
	
	public EntityMinecart(World par1World, double par2, double par4, double par6)
	{
		this(par1World);
		setPosition(par2, par4 + yOffset, par6);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = par2;
		prevPosY = par4;
		prevPosZ = par6;
	}
	
	protected void applyDrag()
	{
		if(riddenByEntity != null)
		{
			motionX *= 0.996999979019165D;
			motionY *= 0.0D;
			motionZ *= 0.996999979019165D;
		} else
		{
			motionX *= 0.9599999785423279D;
			motionY *= 0.0D;
			motionZ *= 0.9599999785423279D;
		}
	}
	
	@Override public void applyEntityCollision(Entity par1Entity)
	{
		if(!worldObj.isRemote)
		{
			if(par1Entity != riddenByEntity)
			{
				if(par1Entity instanceof EntityLiving && !(par1Entity instanceof EntityPlayer) && !(par1Entity instanceof EntityIronGolem) && getMinecartType() == 0 && motionX * motionX + motionZ * motionZ > 0.01D && riddenByEntity == null && par1Entity.ridingEntity == null)
				{
					par1Entity.mountEntity(this);
				}
				double var2 = par1Entity.posX - posX;
				double var4 = par1Entity.posZ - posZ;
				double var6 = var2 * var2 + var4 * var4;
				if(var6 >= 9.999999747378752E-5D)
				{
					var6 = MathHelper.sqrt_double(var6);
					var2 /= var6;
					var4 /= var6;
					double var8 = 1.0D / var6;
					if(var8 > 1.0D)
					{
						var8 = 1.0D;
					}
					var2 *= var8;
					var4 *= var8;
					var2 *= 0.10000000149011612D;
					var4 *= 0.10000000149011612D;
					var2 *= 1.0F - entityCollisionReduction;
					var4 *= 1.0F - entityCollisionReduction;
					var2 *= 0.5D;
					var4 *= 0.5D;
					if(par1Entity instanceof EntityMinecart)
					{
						double var10 = par1Entity.posX - posX;
						double var12 = par1Entity.posZ - posZ;
						Vec3 var14 = worldObj.getWorldVec3Pool().getVecFromPool(var10, 0.0D, var12).normalize();
						Vec3 var15 = worldObj.getWorldVec3Pool().getVecFromPool(MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F), 0.0D, MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F)).normalize();
						double var16 = Math.abs(var14.dotProduct(var15));
						if(var16 < 0.800000011920929D) return;
						double var18 = par1Entity.motionX + motionX;
						double var20 = par1Entity.motionZ + motionZ;
						if(((EntityMinecart) par1Entity).getMinecartType() == 2 && getMinecartType() != 2)
						{
							motionX *= 0.20000000298023224D;
							motionZ *= 0.20000000298023224D;
							addVelocity(par1Entity.motionX - var2, 0.0D, par1Entity.motionZ - var4);
							par1Entity.motionX *= 0.949999988079071D;
							par1Entity.motionZ *= 0.949999988079071D;
						} else if(((EntityMinecart) par1Entity).getMinecartType() != 2 && getMinecartType() == 2)
						{
							par1Entity.motionX *= 0.20000000298023224D;
							par1Entity.motionZ *= 0.20000000298023224D;
							par1Entity.addVelocity(motionX + var2, 0.0D, motionZ + var4);
							motionX *= 0.949999988079071D;
							motionZ *= 0.949999988079071D;
						} else
						{
							var18 /= 2.0D;
							var20 /= 2.0D;
							motionX *= 0.20000000298023224D;
							motionZ *= 0.20000000298023224D;
							addVelocity(var18 - var2, 0.0D, var20 - var4);
							par1Entity.motionX *= 0.20000000298023224D;
							par1Entity.motionZ *= 0.20000000298023224D;
							par1Entity.addVelocity(var18 + var2, 0.0D, var20 + var4);
						}
					} else
					{
						addVelocity(-var2, 0.0D, -var4);
						par1Entity.addVelocity(var2 / 4.0D, 0.0D, var4 / 4.0D);
					}
				}
			}
		}
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		if(!worldObj.isRemote && !isDead)
		{
			if(isEntityInvulnerable()) return false;
			else
			{
				setRollingDirection(-getRollingDirection());
				setRollingAmplitude(10);
				setBeenAttacked();
				setDamage(getDamage() + par2 * 10);
				boolean var3 = par1DamageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer) par1DamageSource.getEntity()).capabilities.isCreativeMode;
				if(var3 || getDamage() > 40)
				{
					if(riddenByEntity != null)
					{
						riddenByEntity.mountEntity(this);
					}
					if(var3 && !isInvNameLocalized())
					{
						setDead();
					} else
					{
						killMinecart(par1DamageSource);
					}
				}
				return true;
			}
		} else return true;
	}
	
	@Override public boolean canBeCollidedWith()
	{
		return !isDead;
	}
	
	@Override public boolean canBePushed()
	{
		return true;
	}
	
	@Override protected boolean canTriggerWalking()
	{
		return false;
	}
	
	@Override protected void entityInit()
	{
		dataWatcher.addObject(17, new Integer(0));
		dataWatcher.addObject(18, new Integer(1));
		dataWatcher.addObject(19, new Integer(0));
		dataWatcher.addObject(20, new Integer(0));
		dataWatcher.addObject(21, new Integer(6));
		dataWatcher.addObject(22, Byte.valueOf((byte) 0));
	}
	
	public Vec3 func_70489_a(double par1, double par3, double par5)
	{
		int var7 = MathHelper.floor_double(par1);
		int var8 = MathHelper.floor_double(par3);
		int var9 = MathHelper.floor_double(par5);
		if(BlockRailBase.isRailBlockAt(worldObj, var7, var8 - 1, var9))
		{
			--var8;
		}
		int var10 = worldObj.getBlockId(var7, var8, var9);
		if(BlockRailBase.isRailBlock(var10))
		{
			int var11 = worldObj.getBlockMetadata(var7, var8, var9);
			par3 = var8;
			if(((BlockRailBase) Block.blocksList[var10]).isPowered())
			{
				var11 &= 7;
			}
			if(var11 >= 2 && var11 <= 5)
			{
				par3 = var8 + 1;
			}
			int[][] var12 = matrix[var11];
			double var13 = 0.0D;
			double var15 = var7 + 0.5D + var12[0][0] * 0.5D;
			double var17 = var8 + 0.5D + var12[0][1] * 0.5D;
			double var19 = var9 + 0.5D + var12[0][2] * 0.5D;
			double var21 = var7 + 0.5D + var12[1][0] * 0.5D;
			double var23 = var8 + 0.5D + var12[1][1] * 0.5D;
			double var25 = var9 + 0.5D + var12[1][2] * 0.5D;
			double var27 = var21 - var15;
			double var29 = (var23 - var17) * 2.0D;
			double var31 = var25 - var19;
			if(var27 == 0.0D)
			{
				par1 = var7 + 0.5D;
				var13 = par5 - var9;
			} else if(var31 == 0.0D)
			{
				par5 = var9 + 0.5D;
				var13 = par1 - var7;
			} else
			{
				double var33 = par1 - var15;
				double var35 = par5 - var19;
				var13 = (var33 * var27 + var35 * var31) * 2.0D;
			}
			par1 = var15 + var27 * var13;
			par3 = var17 + var29 * var13;
			par5 = var19 + var31 * var13;
			if(var29 < 0.0D)
			{
				++par3;
			}
			if(var29 > 0.0D)
			{
				par3 += 0.5D;
			}
			return worldObj.getWorldVec3Pool().getVecFromPool(par1, par3, par5);
		} else return null;
	}
	
	public Vec3 func_70495_a(double par1, double par3, double par5, double par7)
	{
		int var9 = MathHelper.floor_double(par1);
		int var10 = MathHelper.floor_double(par3);
		int var11 = MathHelper.floor_double(par5);
		if(BlockRailBase.isRailBlockAt(worldObj, var9, var10 - 1, var11))
		{
			--var10;
		}
		int var12 = worldObj.getBlockId(var9, var10, var11);
		if(!BlockRailBase.isRailBlock(var12)) return null;
		else
		{
			int var13 = worldObj.getBlockMetadata(var9, var10, var11);
			if(((BlockRailBase) Block.blocksList[var12]).isPowered())
			{
				var13 &= 7;
			}
			par3 = var10;
			if(var13 >= 2 && var13 <= 5)
			{
				par3 = var10 + 1;
			}
			int[][] var14 = matrix[var13];
			double var15 = var14[1][0] - var14[0][0];
			double var17 = var14[1][2] - var14[0][2];
			double var19 = Math.sqrt(var15 * var15 + var17 * var17);
			var15 /= var19;
			var17 /= var19;
			par1 += var15 * par7;
			par5 += var17 * par7;
			if(var14[0][1] != 0 && MathHelper.floor_double(par1) - var9 == var14[0][0] && MathHelper.floor_double(par5) - var11 == var14[0][2])
			{
				par3 += var14[0][1];
			} else if(var14[1][1] != 0 && MathHelper.floor_double(par1) - var9 == var14[1][0] && MathHelper.floor_double(par5) - var11 == var14[1][2])
			{
				par3 += var14[1][1];
			}
			return func_70489_a(par1, par3, par5);
		}
	}
	
	protected void func_94088_b(double par1)
	{
		if(motionX < -par1)
		{
			motionX = -par1;
		}
		if(motionX > par1)
		{
			motionX = par1;
		}
		if(motionZ < -par1)
		{
			motionZ = -par1;
		}
		if(motionZ > par1)
		{
			motionZ = par1;
		}
		if(onGround)
		{
			motionX *= 0.5D;
			motionY *= 0.5D;
			motionZ *= 0.5D;
		}
		moveEntity(motionX, motionY, motionZ);
		if(!onGround)
		{
			motionX *= 0.949999988079071D;
			motionY *= 0.949999988079071D;
			motionZ *= 0.949999988079071D;
		}
	}
	
	public String func_95999_t()
	{
		return entityName;
	}
	
	@Override public AxisAlignedBB getBoundingBox()
	{
		return null;
	}
	
	@Override public AxisAlignedBB getCollisionBox(Entity par1Entity)
	{
		return par1Entity.canBePushed() ? par1Entity.boundingBox : null;
	}
	
	public int getDamage()
	{
		return dataWatcher.getWatchableObjectInt(19);
	}
	
	public Block getDefaultDisplayTile()
	{
		return null;
	}
	
	public int getDefaultDisplayTileData()
	{
		return 0;
	}
	
	public int getDefaultDisplayTileOffset()
	{
		return 6;
	}
	
	public Block getDisplayTile()
	{
		if(!hasDisplayTile()) return getDefaultDisplayTile();
		else
		{
			int var1 = getDataWatcher().getWatchableObjectInt(20) & 65535;
			return var1 > 0 && var1 < Block.blocksList.length ? Block.blocksList[var1] : null;
		}
	}
	
	public int getDisplayTileData()
	{
		return !hasDisplayTile() ? getDefaultDisplayTileData() : getDataWatcher().getWatchableObjectInt(20) >> 16;
	}
	
	public int getDisplayTileOffset()
	{
		return !hasDisplayTile() ? getDefaultDisplayTileOffset() : getDataWatcher().getWatchableObjectInt(21);
	}
	
	@Override public String getEntityName()
	{
		return entityName != null ? entityName : super.getEntityName();
	}
	
	public abstract int getMinecartType();
	
	@Override public double getMountedYOffset()
	{
		return height * 0.0D - 0.30000001192092896D;
	}
	
	public int getRollingAmplitude()
	{
		return dataWatcher.getWatchableObjectInt(17);
	}
	
	public int getRollingDirection()
	{
		return dataWatcher.getWatchableObjectInt(18);
	}
	
	@Override public float getShadowSize()
	{
		return 0.0F;
	}
	
	public boolean hasDisplayTile()
	{
		return getDataWatcher().getWatchableObjectByte(22) == 1;
	}
	
	public boolean isInvNameLocalized()
	{
		return entityName != null;
	}
	
	public void killMinecart(DamageSource par1DamageSource)
	{
		setDead();
		ItemStack var2 = new ItemStack(Item.minecartEmpty, 1);
		if(entityName != null)
		{
			var2.setItemName(entityName);
		}
		entityDropItem(var2, 0.0F);
	}
	
	public void onActivatorRailPass(int par1, int par2, int par3, boolean par4)
	{
	}
	
	@Override public void onUpdate()
	{
		if(field_82344_g != null)
		{
			field_82344_g.update();
		}
		if(getRollingAmplitude() > 0)
		{
			setRollingAmplitude(getRollingAmplitude() - 1);
		}
		if(getDamage() > 0)
		{
			setDamage(getDamage() - 1);
		}
		if(posY < -64.0D)
		{
			kill();
		}
		int var2;
		if(!worldObj.isRemote && worldObj instanceof WorldServer)
		{
			worldObj.theProfiler.startSection("portal");
			MinecraftServer var1 = ((WorldServer) worldObj).getMinecraftServer();
			var2 = getMaxInPortalTime();
			if(inPortal)
			{
				if(var1.getAllowNether())
				{
					if(ridingEntity == null && field_82153_h++ >= var2)
					{
						field_82153_h = var2;
						timeUntilPortal = getPortalCooldown();
						byte var3;
						if(worldObj.provider.dimensionId == -1)
						{
							var3 = 0;
						} else
						{
							var3 = -1;
						}
						travelToDimension(var3);
					}
					inPortal = false;
				}
			} else
			{
				if(field_82153_h > 0)
				{
					field_82153_h -= 4;
				}
				if(field_82153_h < 0)
				{
					field_82153_h = 0;
				}
			}
			if(timeUntilPortal > 0)
			{
				--timeUntilPortal;
			}
			worldObj.theProfiler.endSection();
		}
		if(worldObj.isRemote)
		{
			if(turnProgress > 0)
			{
				double var19 = posX + (minecartX - posX) / turnProgress;
				double var21 = posY + (minecartY - posY) / turnProgress;
				double var5 = posZ + (minecartZ - posZ) / turnProgress;
				double var7 = MathHelper.wrapAngleTo180_double(minecartYaw - rotationYaw);
				rotationYaw = (float) (rotationYaw + var7 / turnProgress);
				rotationPitch = (float) (rotationPitch + (minecartPitch - rotationPitch) / turnProgress);
				--turnProgress;
				setPosition(var19, var21, var5);
				setRotation(rotationYaw, rotationPitch);
			} else
			{
				setPosition(posX, posY, posZ);
				setRotation(rotationYaw, rotationPitch);
			}
		} else
		{
			prevPosX = posX;
			prevPosY = posY;
			prevPosZ = posZ;
			motionY -= 0.03999999910593033D;
			int var18 = MathHelper.floor_double(posX);
			var2 = MathHelper.floor_double(posY);
			int var20 = MathHelper.floor_double(posZ);
			if(BlockRailBase.isRailBlockAt(worldObj, var18, var2 - 1, var20))
			{
				--var2;
			}
			double var4 = 0.4D;
			double var6 = 0.0078125D;
			int var8 = worldObj.getBlockId(var18, var2, var20);
			if(BlockRailBase.isRailBlock(var8))
			{
				int var9 = worldObj.getBlockMetadata(var18, var2, var20);
				updateOnTrack(var18, var2, var20, var4, var6, var8, var9);
				if(var8 == Block.railActivator.blockID)
				{
					onActivatorRailPass(var18, var2, var20, (var9 & 8) != 0);
				}
			} else
			{
				func_94088_b(var4);
			}
			doBlockCollisions();
			rotationPitch = 0.0F;
			double var22 = prevPosX - posX;
			double var11 = prevPosZ - posZ;
			if(var22 * var22 + var11 * var11 > 0.001D)
			{
				rotationYaw = (float) (Math.atan2(var11, var22) * 180.0D / Math.PI);
				if(isInReverse)
				{
					rotationYaw += 180.0F;
				}
			}
			double var13 = MathHelper.wrapAngleTo180_float(rotationYaw - prevRotationYaw);
			if(var13 < -170.0D || var13 >= 170.0D)
			{
				rotationYaw += 180.0F;
				isInReverse = !isInReverse;
			}
			setRotation(rotationYaw, rotationPitch);
			List var15 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
			if(var15 != null && !var15.isEmpty())
			{
				for(int var16 = 0; var16 < var15.size(); ++var16)
				{
					Entity var17 = (Entity) var15.get(var16);
					if(var17 != riddenByEntity && var17.canBePushed() && var17 instanceof EntityMinecart)
					{
						var17.applyEntityCollision(this);
					}
				}
			}
			if(riddenByEntity != null && riddenByEntity.isDead)
			{
				if(riddenByEntity.ridingEntity == this)
				{
					riddenByEntity.ridingEntity = null;
				}
				riddenByEntity = null;
			}
		}
	}
	
	@Override public void performHurtAnimation()
	{
		setRollingDirection(-getRollingDirection());
		setRollingAmplitude(10);
		setDamage(getDamage() + getDamage() * 10);
	}
	
	@Override protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		if(par1NBTTagCompound.getBoolean("CustomDisplayTile"))
		{
			setDisplayTile(par1NBTTagCompound.getInteger("DisplayTile"));
			setDisplayTileData(par1NBTTagCompound.getInteger("DisplayData"));
			setDisplayTileOffset(par1NBTTagCompound.getInteger("DisplayOffset"));
		}
		if(par1NBTTagCompound.hasKey("CustomName") && par1NBTTagCompound.getString("CustomName").length() > 0)
		{
			entityName = par1NBTTagCompound.getString("CustomName");
		}
	}
	
	public void setDamage(int par1)
	{
		dataWatcher.updateObject(19, Integer.valueOf(par1));
	}
	
	@Override public void setDead()
	{
		super.setDead();
		if(field_82344_g != null)
		{
			field_82344_g.update();
		}
	}
	
	public void setDisplayTile(int par1)
	{
		getDataWatcher().updateObject(20, Integer.valueOf(par1 & 65535 | getDisplayTileData() << 16));
		setHasDisplayTile(true);
	}
	
	public void setDisplayTileData(int par1)
	{
		Block var2 = getDisplayTile();
		int var3 = var2 == null ? 0 : var2.blockID;
		getDataWatcher().updateObject(20, Integer.valueOf(var3 & 65535 | par1 << 16));
		setHasDisplayTile(true);
	}
	
	public void setDisplayTileOffset(int par1)
	{
		getDataWatcher().updateObject(21, Integer.valueOf(par1));
		setHasDisplayTile(true);
	}
	
	public void setHasDisplayTile(boolean par1)
	{
		getDataWatcher().updateObject(22, Byte.valueOf((byte) (par1 ? 1 : 0)));
	}
	
	public void setMinecartName(String par1Str)
	{
		entityName = par1Str;
	}
	
	@Override public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	{
		minecartX = par1;
		minecartY = par3;
		minecartZ = par5;
		minecartYaw = par7;
		minecartPitch = par8;
		turnProgress = par9 + 2;
		motionX = velocityX;
		motionY = velocityY;
		motionZ = velocityZ;
	}
	
	public void setRollingAmplitude(int par1)
	{
		dataWatcher.updateObject(17, Integer.valueOf(par1));
	}
	
	public void setRollingDirection(int par1)
	{
		dataWatcher.updateObject(18, Integer.valueOf(par1));
	}
	
	@Override public void setVelocity(double par1, double par3, double par5)
	{
		velocityX = motionX = par1;
		velocityY = motionY = par3;
		velocityZ = motionZ = par5;
	}
	
	protected void updateOnTrack(int par1, int par2, int par3, double par4, double par6, int par8, int par9)
	{
		fallDistance = 0.0F;
		Vec3 var10 = func_70489_a(posX, posY, posZ);
		posY = par2;
		boolean var11 = false;
		boolean var12 = false;
		if(par8 == Block.railPowered.blockID)
		{
			var11 = (par9 & 8) != 0;
			var12 = !var11;
		}
		if(((BlockRailBase) Block.blocksList[par8]).isPowered())
		{
			par9 &= 7;
		}
		if(par9 >= 2 && par9 <= 5)
		{
			posY = par2 + 1;
		}
		if(par9 == 2)
		{
			motionX -= par6;
		}
		if(par9 == 3)
		{
			motionX += par6;
		}
		if(par9 == 4)
		{
			motionZ += par6;
		}
		if(par9 == 5)
		{
			motionZ -= par6;
		}
		int[][] var13 = matrix[par9];
		double var14 = var13[1][0] - var13[0][0];
		double var16 = var13[1][2] - var13[0][2];
		double var18 = Math.sqrt(var14 * var14 + var16 * var16);
		double var20 = motionX * var14 + motionZ * var16;
		if(var20 < 0.0D)
		{
			var14 = -var14;
			var16 = -var16;
		}
		double var22 = Math.sqrt(motionX * motionX + motionZ * motionZ);
		if(var22 > 2.0D)
		{
			var22 = 2.0D;
		}
		motionX = var22 * var14 / var18;
		motionZ = var22 * var16 / var18;
		double var24;
		double var26;
		if(riddenByEntity != null)
		{
			var24 = riddenByEntity.motionX * riddenByEntity.motionX + riddenByEntity.motionZ * riddenByEntity.motionZ;
			var26 = motionX * motionX + motionZ * motionZ;
			if(var24 > 1.0E-4D && var26 < 0.01D)
			{
				motionX += riddenByEntity.motionX * 0.1D;
				motionZ += riddenByEntity.motionZ * 0.1D;
				var12 = false;
			}
		}
		if(var12)
		{
			var24 = Math.sqrt(motionX * motionX + motionZ * motionZ);
			if(var24 < 0.03D)
			{
				motionX *= 0.0D;
				motionY *= 0.0D;
				motionZ *= 0.0D;
			} else
			{
				motionX *= 0.5D;
				motionY *= 0.0D;
				motionZ *= 0.5D;
			}
		}
		var24 = 0.0D;
		var26 = par1 + 0.5D + var13[0][0] * 0.5D;
		double var28 = par3 + 0.5D + var13[0][2] * 0.5D;
		double var30 = par1 + 0.5D + var13[1][0] * 0.5D;
		double var32 = par3 + 0.5D + var13[1][2] * 0.5D;
		var14 = var30 - var26;
		var16 = var32 - var28;
		double var34;
		double var36;
		if(var14 == 0.0D)
		{
			posX = par1 + 0.5D;
			var24 = posZ - par3;
		} else if(var16 == 0.0D)
		{
			posZ = par3 + 0.5D;
			var24 = posX - par1;
		} else
		{
			var34 = posX - var26;
			var36 = posZ - var28;
			var24 = (var34 * var14 + var36 * var16) * 2.0D;
		}
		posX = var26 + var14 * var24;
		posZ = var28 + var16 * var24;
		setPosition(posX, posY + yOffset, posZ);
		var34 = motionX;
		var36 = motionZ;
		if(riddenByEntity != null)
		{
			var34 *= 0.75D;
			var36 *= 0.75D;
		}
		if(var34 < -par4)
		{
			var34 = -par4;
		}
		if(var34 > par4)
		{
			var34 = par4;
		}
		if(var36 < -par4)
		{
			var36 = -par4;
		}
		if(var36 > par4)
		{
			var36 = par4;
		}
		moveEntity(var34, 0.0D, var36);
		if(var13[0][1] != 0 && MathHelper.floor_double(posX) - par1 == var13[0][0] && MathHelper.floor_double(posZ) - par3 == var13[0][2])
		{
			setPosition(posX, posY + var13[0][1], posZ);
		} else if(var13[1][1] != 0 && MathHelper.floor_double(posX) - par1 == var13[1][0] && MathHelper.floor_double(posZ) - par3 == var13[1][2])
		{
			setPosition(posX, posY + var13[1][1], posZ);
		}
		applyDrag();
		Vec3 var38 = func_70489_a(posX, posY, posZ);
		if(var38 != null && var10 != null)
		{
			double var39 = (var10.yCoord - var38.yCoord) * 0.05D;
			var22 = Math.sqrt(motionX * motionX + motionZ * motionZ);
			if(var22 > 0.0D)
			{
				motionX = motionX / var22 * (var22 + var39);
				motionZ = motionZ / var22 * (var22 + var39);
			}
			setPosition(posX, var38.yCoord, posZ);
		}
		int var45 = MathHelper.floor_double(posX);
		int var40 = MathHelper.floor_double(posZ);
		if(var45 != par1 || var40 != par3)
		{
			var22 = Math.sqrt(motionX * motionX + motionZ * motionZ);
			motionX = var22 * (var45 - par1);
			motionZ = var22 * (var40 - par3);
		}
		if(var11)
		{
			double var41 = Math.sqrt(motionX * motionX + motionZ * motionZ);
			if(var41 > 0.01D)
			{
				double var43 = 0.06D;
				motionX += motionX / var41 * var43;
				motionZ += motionZ / var41 * var43;
			} else if(par9 == 1)
			{
				if(worldObj.isBlockNormalCube(par1 - 1, par2, par3))
				{
					motionX = 0.02D;
				} else if(worldObj.isBlockNormalCube(par1 + 1, par2, par3))
				{
					motionX = -0.02D;
				}
			} else if(par9 == 0)
			{
				if(worldObj.isBlockNormalCube(par1, par2, par3 - 1))
				{
					motionZ = 0.02D;
				} else if(worldObj.isBlockNormalCube(par1, par2, par3 + 1))
				{
					motionZ = -0.02D;
				}
			}
		}
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		if(hasDisplayTile())
		{
			par1NBTTagCompound.setBoolean("CustomDisplayTile", true);
			par1NBTTagCompound.setInteger("DisplayTile", getDisplayTile() == null ? 0 : getDisplayTile().blockID);
			par1NBTTagCompound.setInteger("DisplayData", getDisplayTileData());
			par1NBTTagCompound.setInteger("DisplayOffset", getDisplayTileOffset());
		}
		if(entityName != null && entityName.length() > 0)
		{
			par1NBTTagCompound.setString("CustomName", entityName);
		}
	}
	
	public static EntityMinecart createMinecart(World par0World, double par1, double par3, double par5, int par7)
	{
		switch(par7)
		{
			case 1:
				return new EntityMinecartChest(par0World, par1, par3, par5);
			case 2:
				return new EntityMinecartFurnace(par0World, par1, par3, par5);
			case 3:
				return new EntityMinecartTNT(par0World, par1, par3, par5);
			case 4:
				return new EntityMinecartMobSpawner(par0World, par1, par3, par5);
			case 5:
				return new EntityMinecartHopper(par0World, par1, par3, par5);
			default:
				return new EntityMinecartEmpty(par0World, par1, par3, par5);
		}
	}
}
