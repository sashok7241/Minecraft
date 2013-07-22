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
	
	public EntityFallingSand(World p_i3536_1_)
	{
		super(p_i3536_1_);
		fallTime = 0;
		shouldDropItem = true;
		isBreakingAnvil = false;
		isAnvil = false;
		fallHurtMax = 40;
		fallHurtAmount = 2.0F;
		fallingBlockTileEntityData = null;
	}
	
	public EntityFallingSand(World p_i3537_1_, double p_i3537_2_, double p_i3537_4_, double p_i3537_6_, int p_i3537_8_)
	{
		this(p_i3537_1_, p_i3537_2_, p_i3537_4_, p_i3537_6_, p_i3537_8_, 0);
	}
	
	public EntityFallingSand(World p_i3538_1_, double p_i3538_2_, double p_i3538_4_, double p_i3538_6_, int p_i3538_8_, int p_i3538_9_)
	{
		super(p_i3538_1_);
		fallTime = 0;
		shouldDropItem = true;
		isBreakingAnvil = false;
		isAnvil = false;
		fallHurtMax = 40;
		fallHurtAmount = 2.0F;
		fallingBlockTileEntityData = null;
		blockID = p_i3538_8_;
		metadata = p_i3538_9_;
		preventEntitySpawning = true;
		setSize(0.98F, 0.98F);
		yOffset = height / 2.0F;
		setPosition(p_i3538_2_, p_i3538_4_, p_i3538_6_);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = p_i3538_2_;
		prevPosY = p_i3538_4_;
		prevPosZ = p_i3538_6_;
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
	
	@Override protected void fall(float p_70069_1_)
	{
		if(isAnvil)
		{
			int var2 = MathHelper.ceiling_float_int(p_70069_1_ - 1.0F);
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
	
	@Override public void func_85029_a(CrashReportCategory p_85029_1_)
	{
		super.func_85029_a(p_85029_1_);
		p_85029_1_.addCrashSection("Immitating block ID", Integer.valueOf(blockID));
		p_85029_1_.addCrashSection("Immitating block data", Integer.valueOf(metadata));
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
	
	@Override protected void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		if(p_70037_1_.hasKey("TileID"))
		{
			blockID = p_70037_1_.getInteger("TileID");
		} else
		{
			blockID = p_70037_1_.getByte("Tile") & 255;
		}
		metadata = p_70037_1_.getByte("Data") & 255;
		fallTime = p_70037_1_.getByte("Time") & 255;
		if(p_70037_1_.hasKey("HurtEntities"))
		{
			isAnvil = p_70037_1_.getBoolean("HurtEntities");
			fallHurtAmount = p_70037_1_.getFloat("FallHurtAmount");
			fallHurtMax = p_70037_1_.getInteger("FallHurtMax");
		} else if(blockID == Block.anvil.blockID)
		{
			isAnvil = true;
		}
		if(p_70037_1_.hasKey("DropItem"))
		{
			shouldDropItem = p_70037_1_.getBoolean("DropItem");
		}
		if(p_70037_1_.hasKey("TileEntityData"))
		{
			fallingBlockTileEntityData = p_70037_1_.getCompoundTag("TileEntityData");
		}
		if(blockID == 0)
		{
			blockID = Block.sand.blockID;
		}
	}
	
	public void setIsAnvil(boolean p_82154_1_)
	{
		isAnvil = p_82154_1_;
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		p_70014_1_.setByte("Tile", (byte) blockID);
		p_70014_1_.setInteger("TileID", blockID);
		p_70014_1_.setByte("Data", (byte) metadata);
		p_70014_1_.setByte("Time", (byte) fallTime);
		p_70014_1_.setBoolean("DropItem", shouldDropItem);
		p_70014_1_.setBoolean("HurtEntities", isAnvil);
		p_70014_1_.setFloat("FallHurtAmount", fallHurtAmount);
		p_70014_1_.setInteger("FallHurtMax", fallHurtMax);
		if(fallingBlockTileEntityData != null)
		{
			p_70014_1_.setCompoundTag("TileEntityData", fallingBlockTileEntityData);
		}
	}
}
