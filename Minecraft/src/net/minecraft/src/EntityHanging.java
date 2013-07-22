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
	
	public EntityHanging(World par1World)
	{
		super(par1World);
		yOffset = 0.0F;
		setSize(0.5F, 0.5F);
	}
	
	public EntityHanging(World par1World, int par2, int par3, int par4, int par5)
	{
		this(par1World);
		xPosition = par2;
		yPosition = par3;
		zPosition = par4;
	}
	
	@Override public void addVelocity(double par1, double par3, double par5)
	{
		if(!worldObj.isRemote && !isDead && par1 * par1 + par3 * par3 + par5 * par5 > 0.0D)
		{
			setDead();
			func_110128_b((Entity) null);
		}
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(isEntityInvulnerable()) return false;
		else
		{
			if(!isDead && !worldObj.isRemote)
			{
				setDead();
				setBeenAttacked();
				func_110128_b(par1DamageSource.getEntity());
			}
			return true;
		}
	}
	
	@Override public boolean canBeCollidedWith()
	{
		return true;
	}
	
	@Override protected void entityInit()
	{
	}
	
	public abstract void func_110128_b(Entity var1);
	
	@Override protected boolean func_142008_O()
	{
		return false;
	}
	
	private float func_70517_b(int par1)
	{
		return par1 == 32 ? 0.5F : par1 == 64 ? 0.5F : 0.0F;
	}
	
	public abstract int func_82329_d();
	
	public abstract int func_82330_g();
	
	@Override public boolean func_85031_j(Entity par1Entity)
	{
		return par1Entity instanceof EntityPlayer ? attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) par1Entity), 0.0F) : false;
	}
	
	@Override public void moveEntity(double par1, double par3, double par5)
	{
		if(!worldObj.isRemote && !isDead && par1 * par1 + par3 * par3 + par5 * par5 > 0.0D)
		{
			setDead();
			func_110128_b((Entity) null);
		}
	}
	
	@Override public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		if(tickCounter1++ == 100 && !worldObj.isRemote)
		{
			tickCounter1 = 0;
			if(!isDead && !onValidSurface())
			{
				setDead();
				func_110128_b((Entity) null);
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
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		if(par1NBTTagCompound.hasKey("Direction"))
		{
			hangingDirection = par1NBTTagCompound.getByte("Direction");
		} else
		{
			switch(par1NBTTagCompound.getByte("Dir"))
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
		xPosition = par1NBTTagCompound.getInteger("TileX");
		yPosition = par1NBTTagCompound.getInteger("TileY");
		zPosition = par1NBTTagCompound.getInteger("TileZ");
		setDirection(hangingDirection);
	}
	
	public void setDirection(int par1)
	{
		hangingDirection = par1;
		prevRotationYaw = rotationYaw = par1 * 90;
		float var2 = func_82329_d();
		float var3 = func_82330_g();
		float var4 = func_82329_d();
		if(par1 != 2 && par1 != 0)
		{
			var2 = 0.5F;
		} else
		{
			var4 = 0.5F;
			rotationYaw = prevRotationYaw = Direction.rotateOpposite[par1] * 90;
		}
		var2 /= 32.0F;
		var3 /= 32.0F;
		var4 /= 32.0F;
		float var5 = xPosition + 0.5F;
		float var6 = yPosition + 0.5F;
		float var7 = zPosition + 0.5F;
		float var8 = 0.5625F;
		if(par1 == 2)
		{
			var7 -= var8;
		}
		if(par1 == 1)
		{
			var5 -= var8;
		}
		if(par1 == 0)
		{
			var7 += var8;
		}
		if(par1 == 3)
		{
			var5 += var8;
		}
		if(par1 == 2)
		{
			var5 -= func_70517_b(func_82329_d());
		}
		if(par1 == 1)
		{
			var7 += func_70517_b(func_82329_d());
		}
		if(par1 == 0)
		{
			var5 += func_70517_b(func_82329_d());
		}
		if(par1 == 3)
		{
			var7 -= func_70517_b(func_82329_d());
		}
		var6 += func_70517_b(func_82330_g());
		setPosition(var5, var6, var7);
		float var9 = -0.03125F;
		boundingBox.setBounds(var5 - var2 - var9, var6 - var3 - var9, var7 - var4 - var9, var5 + var2 + var9, var6 + var3 + var9, var7 + var4 + var9);
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setByte("Direction", (byte) hangingDirection);
		par1NBTTagCompound.setInteger("TileX", xPosition);
		par1NBTTagCompound.setInteger("TileY", yPosition);
		par1NBTTagCompound.setInteger("TileZ", zPosition);
		switch(hangingDirection)
		{
			case 0:
				par1NBTTagCompound.setByte("Dir", (byte) 2);
				break;
			case 1:
				par1NBTTagCompound.setByte("Dir", (byte) 1);
				break;
			case 2:
				par1NBTTagCompound.setByte("Dir", (byte) 0);
				break;
			case 3:
				par1NBTTagCompound.setByte("Dir", (byte) 3);
		}
	}
}
