package net.minecraft.src;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.server.MinecraftServer;

public abstract class Entity
{
	private static int nextEntityID = 0;
	public int entityId;
	public double renderDistanceWeight;
	public boolean preventEntitySpawning;
	public Entity riddenByEntity;
	public Entity ridingEntity;
	public boolean field_98038_p;
	public World worldObj;
	public double prevPosX;
	public double prevPosY;
	public double prevPosZ;
	public double posX;
	public double posY;
	public double posZ;
	public double motionX;
	public double motionY;
	public double motionZ;
	public float rotationYaw;
	public float rotationPitch;
	public float prevRotationYaw;
	public float prevRotationPitch;
	public final AxisAlignedBB boundingBox;
	public boolean onGround;
	public boolean isCollidedHorizontally;
	public boolean isCollidedVertically;
	public boolean isCollided;
	public boolean velocityChanged;
	protected boolean isInWeb;
	public boolean field_70135_K;
	public boolean isDead;
	public float yOffset;
	public float width;
	public float height;
	public float prevDistanceWalkedModified;
	public float distanceWalkedModified;
	public float distanceWalkedOnStepModified;
	public float fallDistance;
	private int nextStepDistance;
	public double lastTickPosX;
	public double lastTickPosY;
	public double lastTickPosZ;
	public float ySize;
	public float stepHeight;
	public boolean noClip;
	public float entityCollisionReduction;
	protected Random rand;
	public int ticksExisted;
	public int fireResistance;
	private int fire;
	protected boolean inWater;
	public int hurtResistantTime;
	private boolean firstUpdate;
	public String skinUrl;
	public String cloakUrl;
	protected boolean isImmuneToFire;
	protected DataWatcher dataWatcher;
	private double entityRiderPitchDelta;
	private double entityRiderYawDelta;
	public boolean addedToChunk;
	public int chunkCoordX;
	public int chunkCoordY;
	public int chunkCoordZ;
	public int serverPosX;
	public int serverPosY;
	public int serverPosZ;
	public boolean ignoreFrustumCheck;
	public boolean isAirBorne;
	public int timeUntilPortal;
	protected boolean inPortal;
	protected int field_82153_h;
	public int dimension;
	protected int teleportDirection;
	private boolean invulnerable;
	private UUID entityUniqueID;
	public EnumEntitySize myEntitySize;
	
	public Entity(World par1World)
	{
		entityId = nextEntityID++;
		renderDistanceWeight = 1.0D;
		preventEntitySpawning = false;
		boundingBox = AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		onGround = false;
		isCollided = false;
		velocityChanged = false;
		field_70135_K = true;
		isDead = false;
		yOffset = 0.0F;
		width = 0.6F;
		height = 1.8F;
		prevDistanceWalkedModified = 0.0F;
		distanceWalkedModified = 0.0F;
		distanceWalkedOnStepModified = 0.0F;
		fallDistance = 0.0F;
		nextStepDistance = 1;
		ySize = 0.0F;
		stepHeight = 0.0F;
		noClip = false;
		entityCollisionReduction = 0.0F;
		rand = new Random();
		ticksExisted = 0;
		fireResistance = 1;
		fire = 0;
		inWater = false;
		hurtResistantTime = 0;
		firstUpdate = true;
		isImmuneToFire = false;
		dataWatcher = new DataWatcher();
		addedToChunk = false;
		teleportDirection = 0;
		invulnerable = false;
		entityUniqueID = UUID.randomUUID();
		myEntitySize = EnumEntitySize.SIZE_2;
		worldObj = par1World;
		setPosition(0.0D, 0.0D, 0.0D);
		if(par1World != null)
		{
			dimension = par1World.provider.dimensionId;
		}
		dataWatcher.addObject(0, Byte.valueOf((byte) 0));
		dataWatcher.addObject(1, Short.valueOf((short) 300));
		entityInit();
	}
	
	public boolean addEntityID(NBTTagCompound par1NBTTagCompound)
	{
		String var2 = getEntityString();
		if(!isDead && var2 != null && riddenByEntity == null)
		{
			par1NBTTagCompound.setString("id", var2);
			writeToNBT(par1NBTTagCompound);
			return true;
		} else return false;
	}
	
	public boolean addNotRiddenEntityID(NBTTagCompound par1NBTTagCompound)
	{
		String var2 = getEntityString();
		if(!isDead && var2 != null)
		{
			par1NBTTagCompound.setString("id", var2);
			writeToNBT(par1NBTTagCompound);
			return true;
		} else return false;
	}
	
	public void addToPlayerScore(Entity par1Entity, int par2)
	{
	}
	
	public void addVelocity(double par1, double par3, double par5)
	{
		motionX += par1;
		motionY += par3;
		motionZ += par5;
		isAirBorne = true;
	}
	
	public void applyEntityCollision(Entity par1Entity)
	{
		if(par1Entity.riddenByEntity != this && par1Entity.ridingEntity != this)
		{
			double var2 = par1Entity.posX - posX;
			double var4 = par1Entity.posZ - posZ;
			double var6 = MathHelper.abs_max(var2, var4);
			if(var6 >= 0.009999999776482582D)
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
				var2 *= 0.05000000074505806D;
				var4 *= 0.05000000074505806D;
				var2 *= 1.0F - entityCollisionReduction;
				var4 *= 1.0F - entityCollisionReduction;
				addVelocity(-var2, 0.0D, -var4);
				par1Entity.addVelocity(var2, 0.0D, var4);
			}
		}
	}
	
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		if(isEntityInvulnerable()) return false;
		else
		{
			setBeenAttacked();
			return false;
		}
	}
	
	public boolean canAttackWithItem()
	{
		return true;
	}
	
	public boolean canBeCollidedWith()
	{
		return false;
	}
	
	public boolean canBePushed()
	{
		return false;
	}
	
	public boolean canRenderOnFire()
	{
		return isBurning();
	}
	
	protected boolean canTriggerWalking()
	{
		return true;
	}
	
	public void copyDataFrom(Entity par1Entity, boolean par2)
	{
		NBTTagCompound var3 = new NBTTagCompound();
		par1Entity.writeToNBT(var3);
		readFromNBT(var3);
		timeUntilPortal = par1Entity.timeUntilPortal;
		teleportDirection = par1Entity.teleportDirection;
	}
	
	public void copyLocationAndAnglesFrom(Entity par1Entity)
	{
		setLocationAndAngles(par1Entity.posX, par1Entity.posY, par1Entity.posZ, par1Entity.rotationYaw, par1Entity.rotationPitch);
	}
	
	protected void dealFireDamage(int par1)
	{
		if(!isImmuneToFire)
		{
			attackEntityFrom(DamageSource.inFire, par1);
		}
	}
	
	protected void doBlockCollisions()
	{
		int var1 = MathHelper.floor_double(boundingBox.minX + 0.001D);
		int var2 = MathHelper.floor_double(boundingBox.minY + 0.001D);
		int var3 = MathHelper.floor_double(boundingBox.minZ + 0.001D);
		int var4 = MathHelper.floor_double(boundingBox.maxX - 0.001D);
		int var5 = MathHelper.floor_double(boundingBox.maxY - 0.001D);
		int var6 = MathHelper.floor_double(boundingBox.maxZ - 0.001D);
		if(worldObj.checkChunksExist(var1, var2, var3, var4, var5, var6))
		{
			for(int var7 = var1; var7 <= var4; ++var7)
			{
				for(int var8 = var2; var8 <= var5; ++var8)
				{
					for(int var9 = var3; var9 <= var6; ++var9)
					{
						int var10 = worldObj.getBlockId(var7, var8, var9);
						if(var10 > 0)
						{
							Block.blocksList[var10].onEntityCollidedWithBlock(worldObj, var7, var8, var9, this);
						}
					}
				}
			}
		}
	}
	
	public boolean doesEntityNotTriggerPressurePlate()
	{
		return false;
	}
	
	public EntityItem dropItem(int par1, int par2)
	{
		return dropItemWithOffset(par1, par2, 0.0F);
	}
	
	public EntityItem dropItemWithOffset(int par1, int par2, float par3)
	{
		return entityDropItem(new ItemStack(par1, par2, 0), par3);
	}
	
	public EntityItem entityDropItem(ItemStack par1ItemStack, float par2)
	{
		EntityItem var3 = new EntityItem(worldObj, posX, posY + par2, posZ, par1ItemStack);
		var3.delayBeforeCanPickup = 10;
		worldObj.spawnEntityInWorld(var3);
		return var3;
	}
	
	protected abstract void entityInit();
	
	@Override public boolean equals(Object par1Obj)
	{
		return par1Obj instanceof Entity ? ((Entity) par1Obj).entityId == entityId : false;
	}
	
	public void extinguish()
	{
		fire = 0;
	}
	
	protected void fall(float par1)
	{
		if(riddenByEntity != null)
		{
			riddenByEntity.fall(par1);
		}
	}
	
	public int func_82143_as()
	{
		return 3;
	}
	
	public float func_82146_a(Explosion par1Explosion, World par2World, int par3, int par4, int par5, Block par6Block)
	{
		return par6Block.getExplosionResistance(this);
	}
	
	public void func_85029_a(CrashReportCategory par1CrashReportCategory)
	{
		par1CrashReportCategory.addCrashSectionCallable("Entity Type", new CallableEntityType(this));
		par1CrashReportCategory.addCrashSection("Entity ID", Integer.valueOf(entityId));
		par1CrashReportCategory.addCrashSectionCallable("Entity Name", new CallableEntityName(this));
		par1CrashReportCategory.addCrashSection("Entity\'s Exact location", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(posX), Double.valueOf(posY), Double.valueOf(posZ) }));
		par1CrashReportCategory.addCrashSection("Entity\'s Block location", CrashReportCategory.getLocationInfo(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)));
		par1CrashReportCategory.addCrashSection("Entity\'s Momentum", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(motionX), Double.valueOf(motionY), Double.valueOf(motionZ) }));
	}
	
	public boolean func_85031_j(Entity par1Entity)
	{
		return false;
	}
	
	public boolean func_96091_a(Explosion par1Explosion, World par2World, int par3, int par4, int par5, int par6, float par7)
	{
		return true;
	}
	
	public boolean func_96092_aw()
	{
		return true;
	}
	
	public boolean func_98034_c(EntityPlayer par1EntityPlayer)
	{
		return isInvisible();
	}
	
	public int getAir()
	{
		return dataWatcher.getWatchableObjectShort(1);
	}
	
	public AxisAlignedBB getBoundingBox()
	{
		return null;
	}
	
	public float getBrightness(float par1)
	{
		int var2 = MathHelper.floor_double(posX);
		int var3 = MathHelper.floor_double(posZ);
		if(worldObj.blockExists(var2, 0, var3))
		{
			double var4 = (boundingBox.maxY - boundingBox.minY) * 0.66D;
			int var6 = MathHelper.floor_double(posY - yOffset + var4);
			return worldObj.getLightBrightness(var2, var6, var3);
		} else return 0.0F;
	}
	
	public int getBrightnessForRender(float par1)
	{
		int var2 = MathHelper.floor_double(posX);
		int var3 = MathHelper.floor_double(posZ);
		if(worldObj.blockExists(var2, 0, var3))
		{
			double var4 = (boundingBox.maxY - boundingBox.minY) * 0.66D;
			int var6 = MathHelper.floor_double(posY - yOffset + var4);
			return worldObj.getLightBrightnessForSkyBlocks(var2, var6, var3, 0);
		} else return 0;
	}
	
	public float getCollisionBorderSize()
	{
		return 0.1F;
	}
	
	public AxisAlignedBB getCollisionBox(Entity par1Entity)
	{
		return null;
	}
	
	public DataWatcher getDataWatcher()
	{
		return dataWatcher;
	}
	
	public double getDistance(double par1, double par3, double par5)
	{
		double var7 = posX - par1;
		double var9 = posY - par3;
		double var11 = posZ - par5;
		return MathHelper.sqrt_double(var7 * var7 + var9 * var9 + var11 * var11);
	}
	
	public double getDistanceSq(double par1, double par3, double par5)
	{
		double var7 = posX - par1;
		double var9 = posY - par3;
		double var11 = posZ - par5;
		return var7 * var7 + var9 * var9 + var11 * var11;
	}
	
	public double getDistanceSqToEntity(Entity par1Entity)
	{
		double var2 = posX - par1Entity.posX;
		double var4 = posY - par1Entity.posY;
		double var6 = posZ - par1Entity.posZ;
		return var2 * var2 + var4 * var4 + var6 * var6;
	}
	
	public float getDistanceToEntity(Entity par1Entity)
	{
		float var2 = (float) (posX - par1Entity.posX);
		float var3 = (float) (posY - par1Entity.posY);
		float var4 = (float) (posZ - par1Entity.posZ);
		return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
	}
	
	public String getEntityName()
	{
		String var1 = EntityList.getEntityString(this);
		if(var1 == null)
		{
			var1 = "generic";
		}
		return StatCollector.translateToLocal("entity." + var1 + ".name");
	}
	
	protected final String getEntityString()
	{
		return EntityList.getEntityString(this);
	}
	
	public float getEyeHeight()
	{
		return 0.0F;
	}
	
	protected boolean getFlag(int par1)
	{
		return (dataWatcher.getWatchableObjectByte(0) & 1 << par1) != 0;
	}
	
	public ItemStack[] getLastActiveItems()
	{
		return null;
	}
	
	public Vec3 getLookVec()
	{
		return null;
	}
	
	public int getMaxInPortalTime()
	{
		return 0;
	}
	
	public double getMountedYOffset()
	{
		return height * 0.75D;
	}
	
	public Entity[] getParts()
	{
		return null;
	}
	
	public int getPortalCooldown()
	{
		return 900;
	}
	
	public float getRotationYawHead()
	{
		return 0.0F;
	}
	
	public float getShadowSize()
	{
		return height / 2.0F;
	}
	
	public int getTeleportDirection()
	{
		return teleportDirection;
	}
	
	public String getTexture()
	{
		return null;
	}
	
	public String getTranslatedEntityName()
	{
		return getEntityName();
	}
	
	public double getYOffset()
	{
		return yOffset;
	}
	
	public void handleHealthUpdate(byte par1)
	{
	}
	
	public boolean handleLavaMovement()
	{
		return worldObj.isMaterialInBB(boundingBox.expand(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.lava);
	}
	
	public boolean handleWaterMovement()
	{
		if(worldObj.handleMaterialAcceleration(boundingBox.expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D), Material.water, this))
		{
			if(!inWater && !firstUpdate)
			{
				float var1 = MathHelper.sqrt_double(motionX * motionX * 0.20000000298023224D + motionY * motionY + motionZ * motionZ * 0.20000000298023224D) * 0.2F;
				if(var1 > 1.0F)
				{
					var1 = 1.0F;
				}
				playSound("liquid.splash", var1, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
				float var2 = MathHelper.floor_double(boundingBox.minY);
				int var3;
				float var4;
				float var5;
				for(var3 = 0; var3 < 1.0F + width * 20.0F; ++var3)
				{
					var4 = (rand.nextFloat() * 2.0F - 1.0F) * width;
					var5 = (rand.nextFloat() * 2.0F - 1.0F) * width;
					worldObj.spawnParticle("bubble", posX + var4, var2 + 1.0F, posZ + var5, motionX, motionY - rand.nextFloat() * 0.2F, motionZ);
				}
				for(var3 = 0; var3 < 1.0F + width * 20.0F; ++var3)
				{
					var4 = (rand.nextFloat() * 2.0F - 1.0F) * width;
					var5 = (rand.nextFloat() * 2.0F - 1.0F) * width;
					worldObj.spawnParticle("splash", posX + var4, var2 + 1.0F, posZ + var5, motionX, motionY, motionZ);
				}
			}
			fallDistance = 0.0F;
			inWater = true;
			fire = 0;
		} else
		{
			inWater = false;
		}
		return inWater;
	}
	
	@Override public int hashCode()
	{
		return entityId;
	}
	
	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		return false;
	}
	
	public boolean isBurning()
	{
		return fire > 0 || getFlag(0);
	}
	
	public boolean isEating()
	{
		return getFlag(4);
	}
	
	public boolean isEntityAlive()
	{
		return !isDead;
	}
	
	public boolean isEntityEqual(Entity par1Entity)
	{
		return this == par1Entity;
	}
	
	public boolean isEntityInsideOpaqueBlock()
	{
		for(int var1 = 0; var1 < 8; ++var1)
		{
			float var2 = ((var1 >> 0) % 2 - 0.5F) * width * 0.8F;
			float var3 = ((var1 >> 1) % 2 - 0.5F) * 0.1F;
			float var4 = ((var1 >> 2) % 2 - 0.5F) * width * 0.8F;
			int var5 = MathHelper.floor_double(posX + var2);
			int var6 = MathHelper.floor_double(posY + getEyeHeight() + var3);
			int var7 = MathHelper.floor_double(posZ + var4);
			if(worldObj.isBlockNormalCube(var5, var6, var7)) return true;
		}
		return false;
	}
	
	public boolean isEntityInvulnerable()
	{
		return invulnerable;
	}
	
	public final boolean isImmuneToFire()
	{
		return isImmuneToFire;
	}
	
	public boolean isInRangeToRenderDist(double par1)
	{
		double var3 = boundingBox.getAverageEdgeLength();
		var3 *= 64.0D * renderDistanceWeight;
		return par1 < var3 * var3;
	}
	
	public boolean isInRangeToRenderVec3D(Vec3 par1Vec3)
	{
		double var2 = posX - par1Vec3.xCoord;
		double var4 = posY - par1Vec3.yCoord;
		double var6 = posZ - par1Vec3.zCoord;
		double var8 = var2 * var2 + var4 * var4 + var6 * var6;
		return isInRangeToRenderDist(var8);
	}
	
	public boolean isInsideOfMaterial(Material par1Material)
	{
		double var2 = posY + getEyeHeight();
		int var4 = MathHelper.floor_double(posX);
		int var5 = MathHelper.floor_float(MathHelper.floor_double(var2));
		int var6 = MathHelper.floor_double(posZ);
		int var7 = worldObj.getBlockId(var4, var5, var6);
		if(var7 != 0 && Block.blocksList[var7].blockMaterial == par1Material)
		{
			float var8 = BlockFluid.getFluidHeightPercent(worldObj.getBlockMetadata(var4, var5, var6)) - 0.11111111F;
			float var9 = var5 + 1 - var8;
			return var2 < var9;
		} else return false;
	}
	
	public boolean isInvisible()
	{
		return getFlag(5);
	}
	
	public boolean isInWater()
	{
		return inWater;
	}
	
	public boolean isOffsetPositionInLiquid(double par1, double par3, double par5)
	{
		AxisAlignedBB var7 = boundingBox.getOffsetBoundingBox(par1, par3, par5);
		List var8 = worldObj.getCollidingBoundingBoxes(this, var7);
		return !var8.isEmpty() ? false : !worldObj.isAnyLiquid(var7);
	}
	
	public boolean isRiding()
	{
		return ridingEntity != null || getFlag(2);
	}
	
	public boolean isSneaking()
	{
		return getFlag(1);
	}
	
	public boolean isSprinting()
	{
		return getFlag(3);
	}
	
	public boolean isWet()
	{
		return inWater || worldObj.canLightningStrikeAt(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) || worldObj.canLightningStrikeAt(MathHelper.floor_double(posX), MathHelper.floor_double(posY + height), MathHelper.floor_double(posZ));
	}
	
	protected void kill()
	{
		setDead();
	}
	
	public void mountEntity(Entity par1Entity)
	{
		entityRiderPitchDelta = 0.0D;
		entityRiderYawDelta = 0.0D;
		if(par1Entity == null)
		{
			if(ridingEntity != null)
			{
				setLocationAndAngles(ridingEntity.posX, ridingEntity.boundingBox.minY + ridingEntity.height, ridingEntity.posZ, rotationYaw, rotationPitch);
				ridingEntity.riddenByEntity = null;
			}
			ridingEntity = null;
		} else
		{
			if(ridingEntity != null)
			{
				ridingEntity.riddenByEntity = null;
			}
			ridingEntity = par1Entity;
			par1Entity.riddenByEntity = this;
		}
	}
	
	public void moveEntity(double par1, double par3, double par5)
	{
		if(noClip)
		{
			boundingBox.offset(par1, par3, par5);
			posX = (boundingBox.minX + boundingBox.maxX) / 2.0D;
			posY = boundingBox.minY + yOffset - ySize;
			posZ = (boundingBox.minZ + boundingBox.maxZ) / 2.0D;
		} else
		{
			worldObj.theProfiler.startSection("move");
			ySize *= 0.4F;
			double var7 = posX;
			double var9 = posY;
			double var11 = posZ;
			if(isInWeb)
			{
				isInWeb = false;
				par1 *= 0.25D;
				par3 *= 0.05000000074505806D;
				par5 *= 0.25D;
				motionX = 0.0D;
				motionY = 0.0D;
				motionZ = 0.0D;
			}
			double var13 = par1;
			double var15 = par3;
			double var17 = par5;
			AxisAlignedBB var19 = boundingBox.copy();
			boolean var20 = onGround && isSneaking() && this instanceof EntityPlayer;
			if(var20)
			{
				double var21;
				for(var21 = 0.05D; par1 != 0.0D && worldObj.getCollidingBoundingBoxes(this, boundingBox.getOffsetBoundingBox(par1, -1.0D, 0.0D)).isEmpty(); var13 = par1)
				{
					if(par1 < var21 && par1 >= -var21)
					{
						par1 = 0.0D;
					} else if(par1 > 0.0D)
					{
						par1 -= var21;
					} else
					{
						par1 += var21;
					}
				}
				for(; par5 != 0.0D && worldObj.getCollidingBoundingBoxes(this, boundingBox.getOffsetBoundingBox(0.0D, -1.0D, par5)).isEmpty(); var17 = par5)
				{
					if(par5 < var21 && par5 >= -var21)
					{
						par5 = 0.0D;
					} else if(par5 > 0.0D)
					{
						par5 -= var21;
					} else
					{
						par5 += var21;
					}
				}
				while(par1 != 0.0D && par5 != 0.0D && worldObj.getCollidingBoundingBoxes(this, boundingBox.getOffsetBoundingBox(par1, -1.0D, par5)).isEmpty())
				{
					if(par1 < var21 && par1 >= -var21)
					{
						par1 = 0.0D;
					} else if(par1 > 0.0D)
					{
						par1 -= var21;
					} else
					{
						par1 += var21;
					}
					if(par5 < var21 && par5 >= -var21)
					{
						par5 = 0.0D;
					} else if(par5 > 0.0D)
					{
						par5 -= var21;
					} else
					{
						par5 += var21;
					}
					var13 = par1;
					var17 = par5;
				}
			}
			List var35 = worldObj.getCollidingBoundingBoxes(this, boundingBox.addCoord(par1, par3, par5));
			for(int var22 = 0; var22 < var35.size(); ++var22)
			{
				par3 = ((AxisAlignedBB) var35.get(var22)).calculateYOffset(boundingBox, par3);
			}
			boundingBox.offset(0.0D, par3, 0.0D);
			if(!field_70135_K && var15 != par3)
			{
				par5 = 0.0D;
				par3 = 0.0D;
				par1 = 0.0D;
			}
			boolean var34 = onGround || var15 != par3 && var15 < 0.0D;
			int var23;
			for(var23 = 0; var23 < var35.size(); ++var23)
			{
				par1 = ((AxisAlignedBB) var35.get(var23)).calculateXOffset(boundingBox, par1);
			}
			boundingBox.offset(par1, 0.0D, 0.0D);
			if(!field_70135_K && var13 != par1)
			{
				par5 = 0.0D;
				par3 = 0.0D;
				par1 = 0.0D;
			}
			for(var23 = 0; var23 < var35.size(); ++var23)
			{
				par5 = ((AxisAlignedBB) var35.get(var23)).calculateZOffset(boundingBox, par5);
			}
			boundingBox.offset(0.0D, 0.0D, par5);
			if(!field_70135_K && var17 != par5)
			{
				par5 = 0.0D;
				par3 = 0.0D;
				par1 = 0.0D;
			}
			double var25;
			double var27;
			int var30;
			double var36;
			if(stepHeight > 0.0F && var34 && (var20 || ySize < 0.05F) && (var13 != par1 || var17 != par5))
			{
				var36 = par1;
				var25 = par3;
				var27 = par5;
				par1 = var13;
				par3 = stepHeight;
				par5 = var17;
				AxisAlignedBB var29 = boundingBox.copy();
				boundingBox.setBB(var19);
				var35 = worldObj.getCollidingBoundingBoxes(this, boundingBox.addCoord(var13, par3, var17));
				for(var30 = 0; var30 < var35.size(); ++var30)
				{
					par3 = ((AxisAlignedBB) var35.get(var30)).calculateYOffset(boundingBox, par3);
				}
				boundingBox.offset(0.0D, par3, 0.0D);
				if(!field_70135_K && var15 != par3)
				{
					par5 = 0.0D;
					par3 = 0.0D;
					par1 = 0.0D;
				}
				for(var30 = 0; var30 < var35.size(); ++var30)
				{
					par1 = ((AxisAlignedBB) var35.get(var30)).calculateXOffset(boundingBox, par1);
				}
				boundingBox.offset(par1, 0.0D, 0.0D);
				if(!field_70135_K && var13 != par1)
				{
					par5 = 0.0D;
					par3 = 0.0D;
					par1 = 0.0D;
				}
				for(var30 = 0; var30 < var35.size(); ++var30)
				{
					par5 = ((AxisAlignedBB) var35.get(var30)).calculateZOffset(boundingBox, par5);
				}
				boundingBox.offset(0.0D, 0.0D, par5);
				if(!field_70135_K && var17 != par5)
				{
					par5 = 0.0D;
					par3 = 0.0D;
					par1 = 0.0D;
				}
				if(!field_70135_K && var15 != par3)
				{
					par5 = 0.0D;
					par3 = 0.0D;
					par1 = 0.0D;
				} else
				{
					par3 = -stepHeight;
					for(var30 = 0; var30 < var35.size(); ++var30)
					{
						par3 = ((AxisAlignedBB) var35.get(var30)).calculateYOffset(boundingBox, par3);
					}
					boundingBox.offset(0.0D, par3, 0.0D);
				}
				if(var36 * var36 + var27 * var27 >= par1 * par1 + par5 * par5)
				{
					par1 = var36;
					par3 = var25;
					par5 = var27;
					boundingBox.setBB(var29);
				}
			}
			worldObj.theProfiler.endSection();
			worldObj.theProfiler.startSection("rest");
			posX = (boundingBox.minX + boundingBox.maxX) / 2.0D;
			posY = boundingBox.minY + yOffset - ySize;
			posZ = (boundingBox.minZ + boundingBox.maxZ) / 2.0D;
			isCollidedHorizontally = var13 != par1 || var17 != par5;
			isCollidedVertically = var15 != par3;
			onGround = var15 != par3 && var15 < 0.0D;
			isCollided = isCollidedHorizontally || isCollidedVertically;
			updateFallState(par3, onGround);
			if(var13 != par1)
			{
				motionX = 0.0D;
			}
			if(var15 != par3)
			{
				motionY = 0.0D;
			}
			if(var17 != par5)
			{
				motionZ = 0.0D;
			}
			var36 = posX - var7;
			var25 = posY - var9;
			var27 = posZ - var11;
			if(canTriggerWalking() && !var20 && ridingEntity == null)
			{
				int var37 = MathHelper.floor_double(posX);
				var30 = MathHelper.floor_double(posY - 0.20000000298023224D - yOffset);
				int var31 = MathHelper.floor_double(posZ);
				int var32 = worldObj.getBlockId(var37, var30, var31);
				if(var32 == 0)
				{
					int var33 = worldObj.blockGetRenderType(var37, var30 - 1, var31);
					if(var33 == 11 || var33 == 32 || var33 == 21)
					{
						var32 = worldObj.getBlockId(var37, var30 - 1, var31);
					}
				}
				if(var32 != Block.ladder.blockID)
				{
					var25 = 0.0D;
				}
				distanceWalkedModified = (float) (distanceWalkedModified + MathHelper.sqrt_double(var36 * var36 + var27 * var27) * 0.6D);
				distanceWalkedOnStepModified = (float) (distanceWalkedOnStepModified + MathHelper.sqrt_double(var36 * var36 + var25 * var25 + var27 * var27) * 0.6D);
				if(distanceWalkedOnStepModified > nextStepDistance && var32 > 0)
				{
					nextStepDistance = (int) distanceWalkedOnStepModified + 1;
					if(isInWater())
					{
						float var39 = MathHelper.sqrt_double(motionX * motionX * 0.20000000298023224D + motionY * motionY + motionZ * motionZ * 0.20000000298023224D) * 0.35F;
						if(var39 > 1.0F)
						{
							var39 = 1.0F;
						}
						playSound("liquid.swim", var39, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
					}
					playStepSound(var37, var30, var31, var32);
					Block.blocksList[var32].onEntityWalking(worldObj, var37, var30, var31, this);
				}
			}
			doBlockCollisions();
			boolean var38 = isWet();
			if(worldObj.isBoundingBoxBurning(boundingBox.contract(0.001D, 0.001D, 0.001D)))
			{
				dealFireDamage(1);
				if(!var38)
				{
					++fire;
					if(fire == 0)
					{
						setFire(8);
					}
				}
			} else if(fire <= 0)
			{
				fire = -fireResistance;
			}
			if(var38 && fire > 0)
			{
				playSound("random.fizz", 0.7F, 1.6F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
				fire = -fireResistance;
			}
			worldObj.theProfiler.endSection();
		}
	}
	
	public void moveFlying(float par1, float par2, float par3)
	{
		float var4 = par1 * par1 + par2 * par2;
		if(var4 >= 1.0E-4F)
		{
			var4 = MathHelper.sqrt_float(var4);
			if(var4 < 1.0F)
			{
				var4 = 1.0F;
			}
			var4 = par3 / var4;
			par1 *= var4;
			par2 *= var4;
			float var5 = MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F);
			float var6 = MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F);
			motionX += par1 * var6 - par2 * var5;
			motionZ += par2 * var6 + par1 * var5;
		}
	}
	
	protected NBTTagList newDoubleNBTList(double ... par1ArrayOfDouble)
	{
		NBTTagList var2 = new NBTTagList();
		double[] var3 = par1ArrayOfDouble;
		int var4 = par1ArrayOfDouble.length;
		for(int var5 = 0; var5 < var4; ++var5)
		{
			double var6 = var3[var5];
			var2.appendTag(new NBTTagDouble((String) null, var6));
		}
		return var2;
	}
	
	protected NBTTagList newFloatNBTList(float ... par1ArrayOfFloat)
	{
		NBTTagList var2 = new NBTTagList();
		float[] var3 = par1ArrayOfFloat;
		int var4 = par1ArrayOfFloat.length;
		for(int var5 = 0; var5 < var4; ++var5)
		{
			float var6 = var3[var5];
			var2.appendTag(new NBTTagFloat((String) null, var6));
		}
		return var2;
	}
	
	public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
	{
	}
	
	public void onEntityUpdate()
	{
		worldObj.theProfiler.startSection("entityBaseTick");
		if(ridingEntity != null && ridingEntity.isDead)
		{
			ridingEntity = null;
		}
		prevDistanceWalkedModified = distanceWalkedModified;
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		prevRotationPitch = rotationPitch;
		prevRotationYaw = rotationYaw;
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
		if(isSprinting() && !isInWater())
		{
			int var5 = MathHelper.floor_double(posX);
			var2 = MathHelper.floor_double(posY - 0.20000000298023224D - yOffset);
			int var6 = MathHelper.floor_double(posZ);
			int var4 = worldObj.getBlockId(var5, var2, var6);
			if(var4 > 0)
			{
				worldObj.spawnParticle("tilecrack_" + var4 + "_" + worldObj.getBlockMetadata(var5, var2, var6), posX + (rand.nextFloat() - 0.5D) * width, boundingBox.minY + 0.1D, posZ + (rand.nextFloat() - 0.5D) * width, -motionX * 4.0D, 1.5D, -motionZ * 4.0D);
			}
		}
		handleWaterMovement();
		if(worldObj.isRemote)
		{
			fire = 0;
		} else if(fire > 0)
		{
			if(isImmuneToFire)
			{
				fire -= 4;
				if(fire < 0)
				{
					fire = 0;
				}
			} else
			{
				if(fire % 20 == 0)
				{
					attackEntityFrom(DamageSource.onFire, 1);
				}
				--fire;
			}
		}
		if(handleLavaMovement())
		{
			setOnFireFromLava();
			fallDistance *= 0.5F;
		}
		if(posY < -64.0D)
		{
			kill();
		}
		if(!worldObj.isRemote)
		{
			setFlag(0, fire > 0);
			setFlag(2, ridingEntity != null);
		}
		firstUpdate = false;
		worldObj.theProfiler.endSection();
	}
	
	public void onKillEntity(EntityLiving par1EntityLiving)
	{
	}
	
	public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt)
	{
		dealFireDamage(5);
		++fire;
		if(fire == 0)
		{
			setFire(8);
		}
	}
	
	public void onUpdate()
	{
		onEntityUpdate();
	}
	
	public void performHurtAnimation()
	{
	}
	
	public void playSound(String par1Str, float par2, float par3)
	{
		worldObj.playSoundAtEntity(this, par1Str, par2, par3);
	}
	
	protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		StepSound var5 = Block.blocksList[par4].stepSound;
		if(worldObj.getBlockId(par1, par2 + 1, par3) == Block.snow.blockID)
		{
			var5 = Block.snow.stepSound;
			playSound(var5.getStepSound(), var5.getVolume() * 0.15F, var5.getPitch());
		} else if(!Block.blocksList[par4].blockMaterial.isLiquid())
		{
			playSound(var5.getStepSound(), var5.getVolume() * 0.15F, var5.getPitch());
		}
	}
	
	protected void preparePlayerToSpawn()
	{
		if(worldObj != null)
		{
			while(posY > 0.0D)
			{
				setPosition(posX, posY, posZ);
				if(worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty())
				{
					break;
				}
				++posY;
			}
			motionX = motionY = motionZ = 0.0D;
			rotationPitch = 0.0F;
		}
	}
	
	protected boolean pushOutOfBlocks(double par1, double par3, double par5)
	{
		int var7 = MathHelper.floor_double(par1);
		int var8 = MathHelper.floor_double(par3);
		int var9 = MathHelper.floor_double(par5);
		double var10 = par1 - var7;
		double var12 = par3 - var8;
		double var14 = par5 - var9;
		List var16 = worldObj.getCollidingBlockBounds(boundingBox);
		if(var16.isEmpty() && !worldObj.isBlockFullCube(var7, var8, var9)) return false;
		else
		{
			boolean var17 = !worldObj.isBlockFullCube(var7 - 1, var8, var9);
			boolean var18 = !worldObj.isBlockFullCube(var7 + 1, var8, var9);
			boolean var19 = !worldObj.isBlockFullCube(var7, var8 - 1, var9);
			boolean var20 = !worldObj.isBlockFullCube(var7, var8 + 1, var9);
			boolean var21 = !worldObj.isBlockFullCube(var7, var8, var9 - 1);
			boolean var22 = !worldObj.isBlockFullCube(var7, var8, var9 + 1);
			byte var23 = 3;
			double var24 = 9999.0D;
			if(var17 && var10 < var24)
			{
				var24 = var10;
				var23 = 0;
			}
			if(var18 && 1.0D - var10 < var24)
			{
				var24 = 1.0D - var10;
				var23 = 1;
			}
			if(var20 && 1.0D - var12 < var24)
			{
				var24 = 1.0D - var12;
				var23 = 3;
			}
			if(var21 && var14 < var24)
			{
				var24 = var14;
				var23 = 4;
			}
			if(var22 && 1.0D - var14 < var24)
			{
				var24 = 1.0D - var14;
				var23 = 5;
			}
			float var26 = rand.nextFloat() * 0.2F + 0.1F;
			if(var23 == 0)
			{
				motionX = -var26;
			}
			if(var23 == 1)
			{
				motionX = var26;
			}
			if(var23 == 2)
			{
				motionY = -var26;
			}
			if(var23 == 3)
			{
				motionY = var26;
			}
			if(var23 == 4)
			{
				motionZ = -var26;
			}
			if(var23 == 5)
			{
				motionZ = var26;
			}
			return true;
		}
	}
	
	protected abstract void readEntityFromNBT(NBTTagCompound var1);
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		try
		{
			NBTTagList var2 = par1NBTTagCompound.getTagList("Pos");
			NBTTagList var6 = par1NBTTagCompound.getTagList("Motion");
			NBTTagList var7 = par1NBTTagCompound.getTagList("Rotation");
			motionX = ((NBTTagDouble) var6.tagAt(0)).data;
			motionY = ((NBTTagDouble) var6.tagAt(1)).data;
			motionZ = ((NBTTagDouble) var6.tagAt(2)).data;
			if(Math.abs(motionX) > 10.0D)
			{
				motionX = 0.0D;
			}
			if(Math.abs(motionY) > 10.0D)
			{
				motionY = 0.0D;
			}
			if(Math.abs(motionZ) > 10.0D)
			{
				motionZ = 0.0D;
			}
			prevPosX = lastTickPosX = posX = ((NBTTagDouble) var2.tagAt(0)).data;
			prevPosY = lastTickPosY = posY = ((NBTTagDouble) var2.tagAt(1)).data;
			prevPosZ = lastTickPosZ = posZ = ((NBTTagDouble) var2.tagAt(2)).data;
			prevRotationYaw = rotationYaw = ((NBTTagFloat) var7.tagAt(0)).data;
			prevRotationPitch = rotationPitch = ((NBTTagFloat) var7.tagAt(1)).data;
			fallDistance = par1NBTTagCompound.getFloat("FallDistance");
			fire = par1NBTTagCompound.getShort("Fire");
			setAir(par1NBTTagCompound.getShort("Air"));
			onGround = par1NBTTagCompound.getBoolean("OnGround");
			dimension = par1NBTTagCompound.getInteger("Dimension");
			invulnerable = par1NBTTagCompound.getBoolean("Invulnerable");
			timeUntilPortal = par1NBTTagCompound.getInteger("PortalCooldown");
			if(par1NBTTagCompound.hasKey("UUIDMost") && par1NBTTagCompound.hasKey("UUIDLeast"))
			{
				entityUniqueID = new UUID(par1NBTTagCompound.getLong("UUIDMost"), par1NBTTagCompound.getLong("UUIDLeast"));
			}
			setPosition(posX, posY, posZ);
			setRotation(rotationYaw, rotationPitch);
			readEntityFromNBT(par1NBTTagCompound);
		} catch(Throwable var5)
		{
			CrashReport var3 = CrashReport.makeCrashReport(var5, "Loading entity NBT");
			CrashReportCategory var4 = var3.makeCategory("Entity being loaded");
			func_85029_a(var4);
			throw new ReportedException(var3);
		}
	}
	
	public void setAir(int par1)
	{
		dataWatcher.updateObject(1, Short.valueOf((short) par1));
	}
	
	public void setAngles(float par1, float par2)
	{
		float var3 = rotationPitch;
		float var4 = rotationYaw;
		rotationYaw = (float) (rotationYaw + par1 * 0.15D);
		rotationPitch = (float) (rotationPitch - par2 * 0.15D);
		if(rotationPitch < -90.0F)
		{
			rotationPitch = -90.0F;
		}
		if(rotationPitch > 90.0F)
		{
			rotationPitch = 90.0F;
		}
		prevRotationPitch += rotationPitch - var3;
		prevRotationYaw += rotationYaw - var4;
	}
	
	protected void setBeenAttacked()
	{
		velocityChanged = true;
	}
	
	public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack)
	{
	}
	
	public void setDead()
	{
		isDead = true;
	}
	
	public void setEating(boolean par1)
	{
		setFlag(4, par1);
	}
	
	public void setFire(int par1)
	{
		int var2 = par1 * 20;
		var2 = EnchantmentProtection.func_92093_a(this, var2);
		if(fire < var2)
		{
			fire = var2;
		}
	}
	
	protected void setFlag(int par1, boolean par2)
	{
		byte var3 = dataWatcher.getWatchableObjectByte(0);
		if(par2)
		{
			dataWatcher.updateObject(0, Byte.valueOf((byte) (var3 | 1 << par1)));
		} else
		{
			dataWatcher.updateObject(0, Byte.valueOf((byte) (var3 & ~(1 << par1))));
		}
	}
	
	public void setInPortal()
	{
		if(timeUntilPortal > 0)
		{
			timeUntilPortal = getPortalCooldown();
		} else
		{
			double var1 = prevPosX - posX;
			double var3 = prevPosZ - posZ;
			if(!worldObj.isRemote && !inPortal)
			{
				teleportDirection = Direction.getMovementDirection(var1, var3);
			}
			inPortal = true;
		}
	}
	
	public void setInvisible(boolean par1)
	{
		setFlag(5, par1);
	}
	
	public void setInWeb()
	{
		isInWeb = true;
		fallDistance = 0.0F;
	}
	
	public void setLocationAndAngles(double par1, double par3, double par5, float par7, float par8)
	{
		lastTickPosX = prevPosX = posX = par1;
		lastTickPosY = prevPosY = posY = par3 + yOffset;
		lastTickPosZ = prevPosZ = posZ = par5;
		rotationYaw = par7;
		rotationPitch = par8;
		setPosition(posX, posY, posZ);
	}
	
	protected void setOnFireFromLava()
	{
		if(!isImmuneToFire)
		{
			attackEntityFrom(DamageSource.lava, 4);
			setFire(15);
		}
	}
	
	public void setPosition(double par1, double par3, double par5)
	{
		posX = par1;
		posY = par3;
		posZ = par5;
		float var7 = width / 2.0F;
		float var8 = height;
		boundingBox.setBounds(par1 - var7, par3 - yOffset + ySize, par5 - var7, par1 + var7, par3 - yOffset + ySize + var8, par5 + var7);
	}
	
	public void setPositionAndRotation(double par1, double par3, double par5, float par7, float par8)
	{
		prevPosX = posX = par1;
		prevPosY = posY = par3;
		prevPosZ = posZ = par5;
		prevRotationYaw = rotationYaw = par7;
		prevRotationPitch = rotationPitch = par8;
		ySize = 0.0F;
		double var9 = prevRotationYaw - par7;
		if(var9 < -180.0D)
		{
			prevRotationYaw += 360.0F;
		}
		if(var9 >= 180.0D)
		{
			prevRotationYaw -= 360.0F;
		}
		setPosition(posX, posY, posZ);
		setRotation(par7, par8);
	}
	
	public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	{
		setPosition(par1, par3, par5);
		setRotation(par7, par8);
		List var10 = worldObj.getCollidingBoundingBoxes(this, boundingBox.contract(0.03125D, 0.0D, 0.03125D));
		if(!var10.isEmpty())
		{
			double var11 = 0.0D;
			for(int var13 = 0; var13 < var10.size(); ++var13)
			{
				AxisAlignedBB var14 = (AxisAlignedBB) var10.get(var13);
				if(var14.maxY > var11)
				{
					var11 = var14.maxY;
				}
			}
			par3 += var11 - boundingBox.minY;
			setPosition(par1, par3, par5);
		}
	}
	
	protected void setRotation(float par1, float par2)
	{
		rotationYaw = par1 % 360.0F;
		rotationPitch = par2 % 360.0F;
	}
	
	public void setRotationYawHead(float par1)
	{
	}
	
	protected void setSize(float par1, float par2)
	{
		if(par1 != width || par2 != height)
		{
			width = par1;
			height = par2;
			boundingBox.maxX = boundingBox.minX + width;
			boundingBox.maxZ = boundingBox.minZ + width;
			boundingBox.maxY = boundingBox.minY + height;
		}
		float var3 = par1 % 2.0F;
		if(var3 < 0.375D)
		{
			myEntitySize = EnumEntitySize.SIZE_1;
		} else if(var3 < 0.75D)
		{
			myEntitySize = EnumEntitySize.SIZE_2;
		} else if(var3 < 1.0D)
		{
			myEntitySize = EnumEntitySize.SIZE_3;
		} else if(var3 < 1.375D)
		{
			myEntitySize = EnumEntitySize.SIZE_4;
		} else if(var3 < 1.75D)
		{
			myEntitySize = EnumEntitySize.SIZE_5;
		} else
		{
			myEntitySize = EnumEntitySize.SIZE_6;
		}
	}
	
	public void setSneaking(boolean par1)
	{
		setFlag(1, par1);
	}
	
	public void setSprinting(boolean par1)
	{
		setFlag(3, par1);
	}
	
	public void setVelocity(double par1, double par3, double par5)
	{
		motionX = par1;
		motionY = par3;
		motionZ = par5;
	}
	
	public void setWorld(World par1World)
	{
		worldObj = par1World;
	}
	
	@Override public String toString()
	{
		return String.format("%s[\'%s\'/%d, l=\'%s\', x=%.2f, y=%.2f, z=%.2f]", new Object[] { this.getClass().getSimpleName(), getEntityName(), Integer.valueOf(entityId), worldObj == null ? "~NULL~" : worldObj.getWorldInfo().getWorldName(), Double.valueOf(posX), Double.valueOf(posY), Double.valueOf(posZ) });
	}
	
	public void travelToDimension(int par1)
	{
		if(!worldObj.isRemote && !isDead)
		{
			worldObj.theProfiler.startSection("changeDimension");
			MinecraftServer var2 = MinecraftServer.getServer();
			int var3 = dimension;
			WorldServer var4 = var2.worldServerForDimension(var3);
			WorldServer var5 = var2.worldServerForDimension(par1);
			dimension = par1;
			worldObj.removeEntity(this);
			isDead = false;
			worldObj.theProfiler.startSection("reposition");
			var2.getConfigurationManager().transferEntityToWorld(this, var3, var4, var5);
			worldObj.theProfiler.endStartSection("reloading");
			Entity var6 = EntityList.createEntityByName(EntityList.getEntityString(this), var5);
			if(var6 != null)
			{
				var6.copyDataFrom(this, true);
				var5.spawnEntityInWorld(var6);
			}
			isDead = true;
			worldObj.theProfiler.endSection();
			var4.resetUpdateEntityTick();
			var5.resetUpdateEntityTick();
			worldObj.theProfiler.endSection();
		}
	}
	
	public void unmountEntity(Entity par1Entity)
	{
		double var3 = posX;
		double var5 = posY;
		double var7 = posZ;
		if(par1Entity != null)
		{
			var3 = par1Entity.posX;
			var5 = par1Entity.boundingBox.minY + par1Entity.height;
			var7 = par1Entity.posZ;
		}
		for(double var9 = -1.5D; var9 < 2.0D; ++var9)
		{
			for(double var11 = -1.5D; var11 < 2.0D; ++var11)
			{
				if(var9 != 0.0D || var11 != 0.0D)
				{
					int var13 = (int) (posX + var9);
					int var14 = (int) (posZ + var11);
					AxisAlignedBB var2 = boundingBox.getOffsetBoundingBox(var9, 1.0D, var11);
					if(worldObj.getCollidingBlockBounds(var2).isEmpty())
					{
						if(worldObj.doesBlockHaveSolidTopSurface(var13, (int) posY, var14))
						{
							setLocationAndAngles(posX + var9, posY + 1.0D, posZ + var11, rotationYaw, rotationPitch);
							return;
						}
						if(worldObj.doesBlockHaveSolidTopSurface(var13, (int) posY - 1, var14) || worldObj.getBlockMaterial(var13, (int) posY - 1, var14) == Material.water)
						{
							var3 = posX + var9;
							var5 = posY + 1.0D;
							var7 = posZ + var11;
						}
					}
				}
			}
		}
		setLocationAndAngles(var3, var5, var7, rotationYaw, rotationPitch);
	}
	
	public void updateCloak()
	{
	}
	
	protected void updateFallState(double par1, boolean par3)
	{
		if(par3)
		{
			if(fallDistance > 0.0F)
			{
				fall(fallDistance);
				fallDistance = 0.0F;
			}
		} else if(par1 < 0.0D)
		{
			fallDistance = (float) (fallDistance - par1);
		}
	}
	
	public void updateRidden()
	{
		if(ridingEntity.isDead)
		{
			ridingEntity = null;
		} else
		{
			motionX = 0.0D;
			motionY = 0.0D;
			motionZ = 0.0D;
			onUpdate();
			if(ridingEntity != null)
			{
				ridingEntity.updateRiderPosition();
				entityRiderYawDelta += ridingEntity.rotationYaw - ridingEntity.prevRotationYaw;
				for(entityRiderPitchDelta += ridingEntity.rotationPitch - ridingEntity.prevRotationPitch; entityRiderYawDelta >= 180.0D; entityRiderYawDelta -= 360.0D)
				{
					;
				}
				while(entityRiderYawDelta < -180.0D)
				{
					entityRiderYawDelta += 360.0D;
				}
				while(entityRiderPitchDelta >= 180.0D)
				{
					entityRiderPitchDelta -= 360.0D;
				}
				while(entityRiderPitchDelta < -180.0D)
				{
					entityRiderPitchDelta += 360.0D;
				}
				double var1 = entityRiderYawDelta * 0.5D;
				double var3 = entityRiderPitchDelta * 0.5D;
				float var5 = 10.0F;
				if(var1 > var5)
				{
					var1 = var5;
				}
				if(var1 < -var5)
				{
					var1 = -var5;
				}
				if(var3 > var5)
				{
					var3 = var5;
				}
				if(var3 < -var5)
				{
					var3 = -var5;
				}
				entityRiderYawDelta -= var1;
				entityRiderPitchDelta -= var3;
				rotationYaw = (float) (rotationYaw + var1);
				rotationPitch = (float) (rotationPitch + var3);
			}
		}
	}
	
	public void updateRiderPosition()
	{
		if(riddenByEntity != null)
		{
			if(!(riddenByEntity instanceof EntityPlayer) || !((EntityPlayer) riddenByEntity).func_71066_bF())
			{
				riddenByEntity.lastTickPosX = lastTickPosX;
				riddenByEntity.lastTickPosY = lastTickPosY + getMountedYOffset() + riddenByEntity.getYOffset();
				riddenByEntity.lastTickPosZ = lastTickPosZ;
			}
			riddenByEntity.setPosition(posX, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ);
		}
	}
	
	protected abstract void writeEntityToNBT(NBTTagCompound var1);
	
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		try
		{
			par1NBTTagCompound.setTag("Pos", newDoubleNBTList(new double[] { posX, posY + ySize, posZ }));
			par1NBTTagCompound.setTag("Motion", newDoubleNBTList(new double[] { motionX, motionY, motionZ }));
			par1NBTTagCompound.setTag("Rotation", newFloatNBTList(new float[] { rotationYaw, rotationPitch }));
			par1NBTTagCompound.setFloat("FallDistance", fallDistance);
			par1NBTTagCompound.setShort("Fire", (short) fire);
			par1NBTTagCompound.setShort("Air", (short) getAir());
			par1NBTTagCompound.setBoolean("OnGround", onGround);
			par1NBTTagCompound.setInteger("Dimension", dimension);
			par1NBTTagCompound.setBoolean("Invulnerable", invulnerable);
			par1NBTTagCompound.setInteger("PortalCooldown", timeUntilPortal);
			par1NBTTagCompound.setLong("UUIDMost", entityUniqueID.getMostSignificantBits());
			par1NBTTagCompound.setLong("UUIDLeast", entityUniqueID.getLeastSignificantBits());
			writeEntityToNBT(par1NBTTagCompound);
			if(ridingEntity != null)
			{
				NBTTagCompound var2 = new NBTTagCompound("Riding");
				if(ridingEntity.addNotRiddenEntityID(var2))
				{
					par1NBTTagCompound.setTag("Riding", var2);
				}
			}
		} catch(Throwable var5)
		{
			CrashReport var3 = CrashReport.makeCrashReport(var5, "Saving entity NBT");
			CrashReportCategory var4 = var3.makeCategory("Entity being saved");
			func_85029_a(var4);
			throw new ReportedException(var3);
		}
	}
}
