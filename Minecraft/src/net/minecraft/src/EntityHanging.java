package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public abstract class EntityHanging extends Entity
{
	private int tickCounter1;
	public int hangingDirection;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	
	public EntityHanging(World p_i5053_1_)
	{
		super(p_i5053_1_);
		tickCounter1 = 0;
		hangingDirection = 0;
		yOffset = 0.0F;
		setSize(0.5F, 0.5F);
	}
	
	public EntityHanging(World p_i5054_1_, int p_i5054_2_, int p_i5054_3_, int p_i5054_4_, int p_i5054_5_)
	{
		this(p_i5054_1_);
		xPosition = p_i5054_2_;
		yPosition = p_i5054_3_;
		zPosition = p_i5054_4_;
	}
	
	@Override public void addVelocity(double p_70024_1_, double p_70024_3_, double p_70024_5_)
	{
		if(!worldObj.isRemote && !isDead && p_70024_1_ * p_70024_1_ + p_70024_3_ * p_70024_3_ + p_70024_5_ * p_70024_5_ > 0.0D)
		{
			setDead();
			dropItemStack();
		}
	}
	
	@Override public boolean attackEntityFrom(DamageSource p_70097_1_, int p_70097_2_)
	{
		if(isEntityInvulnerable()) return false;
		else
		{
			if(!isDead && !worldObj.isRemote)
			{
				setDead();
				setBeenAttacked();
				EntityPlayer var3 = null;
				if(p_70097_1_.getEntity() instanceof EntityPlayer)
				{
					var3 = (EntityPlayer) p_70097_1_.getEntity();
				}
				if(var3 != null && var3.capabilities.isCreativeMode) return true;
				dropItemStack();
			}
			return true;
		}
	}
	
	@Override public boolean canBeCollidedWith()
	{
		return true;
	}
	
	public abstract void dropItemStack();
	
	@Override protected void entityInit()
	{
	}
	
	private float func_70517_b(int p_70517_1_)
	{
		return p_70517_1_ == 32 ? 0.5F : p_70517_1_ == 64 ? 0.5F : 0.0F;
	}
	
	public abstract int func_82329_d();
	
	public abstract int func_82330_g();
	
	@Override public boolean func_85031_j(Entity p_85031_1_)
	{
		return p_85031_1_ instanceof EntityPlayer ? attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) p_85031_1_), 0) : false;
	}
	
	@Override public void moveEntity(double p_70091_1_, double p_70091_3_, double p_70091_5_)
	{
		if(!worldObj.isRemote && !isDead && p_70091_1_ * p_70091_1_ + p_70091_3_ * p_70091_3_ + p_70091_5_ * p_70091_5_ > 0.0D)
		{
			setDead();
			dropItemStack();
		}
	}
	
	@Override public void onUpdate()
	{
		if(tickCounter1++ == 100 && !worldObj.isRemote)
		{
			tickCounter1 = 0;
			if(!isDead && !onValidSurface())
			{
				setDead();
				dropItemStack();
			}
		}
	}
	
	public boolean onValidSurface()
	{
		if(!worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty()) return false;
		else
		{
			int var1 = Math.max(1, func_82329_d() / 16);
			int var2 = Math.max(1, func_82330_g() / 16);
			int var3 = xPosition;
			int var4 = yPosition;
			int var5 = zPosition;
			if(hangingDirection == 2)
			{
				var3 = MathHelper.floor_double(posX - func_82329_d() / 32.0F);
			}
			if(hangingDirection == 1)
			{
				var5 = MathHelper.floor_double(posZ - func_82329_d() / 32.0F);
			}
			if(hangingDirection == 0)
			{
				var3 = MathHelper.floor_double(posX - func_82329_d() / 32.0F);
			}
			if(hangingDirection == 3)
			{
				var5 = MathHelper.floor_double(posZ - func_82329_d() / 32.0F);
			}
			var4 = MathHelper.floor_double(posY - func_82330_g() / 32.0F);
			for(int var6 = 0; var6 < var1; ++var6)
			{
				for(int var7 = 0; var7 < var2; ++var7)
				{
					Material var8;
					if(hangingDirection != 2 && hangingDirection != 0)
					{
						var8 = worldObj.getBlockMaterial(xPosition, var4 + var7, var5 + var6);
					} else
					{
						var8 = worldObj.getBlockMaterial(var3 + var6, var4 + var7, zPosition);
					}
					if(!var8.isSolid()) return false;
				}
			}
			List var9 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox);
			Iterator var10 = var9.iterator();
			Entity var11;
			do
			{
				if(!var10.hasNext()) return true;
				var11 = (Entity) var10.next();
			} while(!(var11 instanceof EntityHanging));
			return false;
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		if(p_70037_1_.hasKey("Direction"))
		{
			hangingDirection = p_70037_1_.getByte("Direction");
		} else
		{
			switch(p_70037_1_.getByte("Dir"))
			{
				case 0:
					hangingDirection = 2;
					break;
				case 1:
					hangingDirection = 1;
					break;
				case 2:
					hangingDirection = 0;
					break;
				case 3:
					hangingDirection = 3;
			}
		}
		xPosition = p_70037_1_.getInteger("TileX");
		yPosition = p_70037_1_.getInteger("TileY");
		zPosition = p_70037_1_.getInteger("TileZ");
		setDirection(hangingDirection);
	}
	
	public void setDirection(int p_82328_1_)
	{
		hangingDirection = p_82328_1_;
		prevRotationYaw = rotationYaw = p_82328_1_ * 90;
		float var2 = func_82329_d();
		float var3 = func_82330_g();
		float var4 = func_82329_d();
		if(p_82328_1_ != 2 && p_82328_1_ != 0)
		{
			var2 = 0.5F;
		} else
		{
			var4 = 0.5F;
			rotationYaw = prevRotationYaw = Direction.rotateOpposite[p_82328_1_] * 90;
		}
		var2 /= 32.0F;
		var3 /= 32.0F;
		var4 /= 32.0F;
		float var5 = xPosition + 0.5F;
		float var6 = yPosition + 0.5F;
		float var7 = zPosition + 0.5F;
		float var8 = 0.5625F;
		if(p_82328_1_ == 2)
		{
			var7 -= var8;
		}
		if(p_82328_1_ == 1)
		{
			var5 -= var8;
		}
		if(p_82328_1_ == 0)
		{
			var7 += var8;
		}
		if(p_82328_1_ == 3)
		{
			var5 += var8;
		}
		if(p_82328_1_ == 2)
		{
			var5 -= func_70517_b(func_82329_d());
		}
		if(p_82328_1_ == 1)
		{
			var7 += func_70517_b(func_82329_d());
		}
		if(p_82328_1_ == 0)
		{
			var5 += func_70517_b(func_82329_d());
		}
		if(p_82328_1_ == 3)
		{
			var7 -= func_70517_b(func_82329_d());
		}
		var6 += func_70517_b(func_82330_g());
		setPosition(var5, var6, var7);
		float var9 = -0.03125F;
		boundingBox.setBounds(var5 - var2 - var9, var6 - var3 - var9, var7 - var4 - var9, var5 + var2 + var9, var6 + var3 + var9, var7 + var4 + var9);
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		p_70014_1_.setByte("Direction", (byte) hangingDirection);
		p_70014_1_.setInteger("TileX", xPosition);
		p_70014_1_.setInteger("TileY", yPosition);
		p_70014_1_.setInteger("TileZ", zPosition);
		switch(hangingDirection)
		{
			case 0:
				p_70014_1_.setByte("Dir", (byte) 2);
				break;
			case 1:
				p_70014_1_.setByte("Dir", (byte) 1);
				break;
			case 2:
				p_70014_1_.setByte("Dir", (byte) 0);
				break;
			case 3:
				p_70014_1_.setByte("Dir", (byte) 3);
		}
	}
}
