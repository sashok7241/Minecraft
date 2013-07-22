package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;

public class EntityFallingSand extends Entity
{
	public int blockID;
	public int metadata;
	public int fallTime;
	public boolean shouldDropItem;
	private boolean isBreakingAnvil;
	private boolean isAnvil;
	private int fallHurtMax;
	private float fallHurtAmount;
	public NBTTagCompound fallingBlockTileEntityData;
	
	public EntityFallingSand(World par1World)
	{
		super(par1World);
		shouldDropItem = true;
		fallHurtMax = 40;
		fallHurtAmount = 2.0F;
	}
	
	public EntityFallingSand(World par1World, double par2, double par4, double par6, int par8)
	{
		this(par1World, par2, par4, par6, par8, 0);
	}
	
	public EntityFallingSand(World par1World, double par2, double par4, double par6, int par8, int par9)
	{
		super(par1World);
		shouldDropItem = true;
		fallHurtMax = 40;
		fallHurtAmount = 2.0F;
		blockID = par8;
		metadata = par9;
		preventEntitySpawning = true;
		setSize(0.98F, 0.98F);
		yOffset = height / 2.0F;
		setPosition(par2, par4, par6);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = par2;
		prevPosY = par4;
		prevPosZ = par6;
	}
	
	@Override public boolean canBeCollidedWith()
	{
		return !isDead;
	}
	
	@Override public boolean canRenderOnFire()
	{
		return false;
	}
	
	@Override protected boolean canTriggerWalking()
	{
		return false;
	}
	
	@Override protected void entityInit()
	{
	}
	
	@Override protected void fall(float par1)
	{
		if(isAnvil)
		{
			int var2 = MathHelper.ceiling_float_int(par1 - 1.0F);
			if(var2 > 0)
			{
				ArrayList var3 = new ArrayList(worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox));
				DamageSource var4 = blockID == Block.anvil.blockID ? DamageSource.anvil : DamageSource.fallingBlock;
				Iterator var5 = var3.iterator();
				while(var5.hasNext())
				{
					Entity var6 = (Entity) var5.next();
					var6.attackEntityFrom(var4, Math.min(MathHelper.floor_float(var2 * fallHurtAmount), fallHurtMax));
				}
				if(blockID == Block.anvil.blockID && rand.nextFloat() < 0.05000000074505806D + var2 * 0.05D)
				{
					int var7 = metadata >> 2;
					int var8 = metadata & 3;
					++var7;
					if(var7 > 2)
					{
						isBreakingAnvil = true;
					} else
					{
						metadata = var8 | var7 << 2;
					}
				}
			}
		}
	}
	
	@Override public void func_85029_a(CrashReportCategory par1CrashReportCategory)
	{
		super.func_85029_a(par1CrashReportCategory);
		par1CrashReportCategory.addCrashSection("Immitating block ID", Integer.valueOf(blockID));
		par1CrashReportCategory.addCrashSection("Immitating block data", Integer.valueOf(metadata));
	}
	
	@Override public float getShadowSize()
	{
		return 0.0F;
	}
	
	public World getWorld()
	{
		return worldObj;
	}
	
	@Override public void onUpdate()
	{
		if(blockID == 0)
		{
			setDead();
		} else
		{
			prevPosX = posX;
			prevPosY = posY;
			prevPosZ = posZ;
			++fallTime;
			motionY -= 0.03999999910593033D;
			moveEntity(motionX, motionY, motionZ);
			motionX *= 0.9800000190734863D;
			motionY *= 0.9800000190734863D;
			motionZ *= 0.9800000190734863D;
			if(!worldObj.isRemote)
			{
				int var1 = MathHelper.floor_double(posX);
				int var2 = MathHelper.floor_double(posY);
				int var3 = MathHelper.floor_double(posZ);
				if(fallTime == 1)
				{
					if(worldObj.getBlockId(var1, var2, var3) != blockID)
					{
						setDead();
						return;
					}
					worldObj.setBlockToAir(var1, var2, var3);
				}
				if(onGround)
				{
					motionX *= 0.699999988079071D;
					motionZ *= 0.699999988079071D;
					motionY *= -0.5D;
					if(worldObj.getBlockId(var1, var2, var3) != Block.pistonMoving.blockID)
					{
						setDead();
						if(!isBreakingAnvil && worldObj.canPlaceEntityOnSide(blockID, var1, var2, var3, true, 1, (Entity) null, (ItemStack) null) && !BlockSand.canFallBelow(worldObj, var1, var2 - 1, var3) && worldObj.setBlock(var1, var2, var3, blockID, metadata, 3))
						{
							if(Block.blocksList[blockID] instanceof BlockSand)
							{
								((BlockSand) Block.blocksList[blockID]).onFinishFalling(worldObj, var1, var2, var3, metadata);
							}
							if(fallingBlockTileEntityData != null && Block.blocksList[blockID] instanceof ITileEntityProvider)
							{
								TileEntity var4 = worldObj.getBlockTileEntity(var1, var2, var3);
								if(var4 != null)
								{
									NBTTagCompound var5 = new NBTTagCompound();
									var4.writeToNBT(var5);
									Iterator var6 = fallingBlockTileEntityData.getTags().iterator();
									while(var6.hasNext())
									{
										NBTBase var7 = (NBTBase) var6.next();
										if(!var7.getName().equals("x") && !var7.getName().equals("y") && !var7.getName().equals("z"))
										{
											var5.setTag(var7.getName(), var7.copy());
										}
									}
									var4.readFromNBT(var5);
									var4.onInventoryChanged();
								}
							}
						} else if(shouldDropItem && !isBreakingAnvil)
						{
							entityDropItem(new ItemStack(blockID, 1, Block.blocksList[blockID].damageDropped(metadata)), 0.0F);
						}
					}
				} else if(fallTime > 100 && !worldObj.isRemote && (var2 < 1 || var2 > 256) || fallTime > 600)
				{
					if(shouldDropItem)
					{
						entityDropItem(new ItemStack(blockID, 1, Block.blocksList[blockID].damageDropped(metadata)), 0.0F);
					}
					setDead();
				}
			}
		}
	}
	
	@Override protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		if(par1NBTTagCompound.hasKey("TileID"))
		{
			blockID = par1NBTTagCompound.getInteger("TileID");
		} else
		{
			blockID = par1NBTTagCompound.getByte("Tile") & 255;
		}
		metadata = par1NBTTagCompound.getByte("Data") & 255;
		fallTime = par1NBTTagCompound.getByte("Time") & 255;
		if(par1NBTTagCompound.hasKey("HurtEntities"))
		{
			isAnvil = par1NBTTagCompound.getBoolean("HurtEntities");
			fallHurtAmount = par1NBTTagCompound.getFloat("FallHurtAmount");
			fallHurtMax = par1NBTTagCompound.getInteger("FallHurtMax");
		} else if(blockID == Block.anvil.blockID)
		{
			isAnvil = true;
		}
		if(par1NBTTagCompound.hasKey("DropItem"))
		{
			shouldDropItem = par1NBTTagCompound.getBoolean("DropItem");
		}
		if(par1NBTTagCompound.hasKey("TileEntityData"))
		{
			fallingBlockTileEntityData = par1NBTTagCompound.getCompoundTag("TileEntityData");
		}
		if(blockID == 0)
		{
			blockID = Block.sand.blockID;
		}
	}
	
	public void setIsAnvil(boolean par1)
	{
		isAnvil = par1;
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setByte("Tile", (byte) blockID);
		par1NBTTagCompound.setInteger("TileID", blockID);
		par1NBTTagCompound.setByte("Data", (byte) metadata);
		par1NBTTagCompound.setByte("Time", (byte) fallTime);
		par1NBTTagCompound.setBoolean("DropItem", shouldDropItem);
		par1NBTTagCompound.setBoolean("HurtEntities", isAnvil);
		par1NBTTagCompound.setFloat("FallHurtAmount", fallHurtAmount);
		par1NBTTagCompound.setInteger("FallHurtMax", fallHurtMax);
		if(fallingBlockTileEntityData != null)
		{
			par1NBTTagCompound.setCompoundTag("TileEntityData", fallingBlockTileEntityData);
		}
	}
}
