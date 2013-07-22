package net.minecraft.src;

public class EntityMinecartTNT extends EntityMinecart
{
	private int minecartTNTFuse = -1;
	
	public EntityMinecartTNT(World par1World)
	{
		super(par1World);
	}
	
	public EntityMinecartTNT(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6);
	}
	
	protected void explodeCart(double par1)
	{
		if(!worldObj.isRemote)
		{
			double var3 = Math.sqrt(par1);
			if(var3 > 5.0D)
			{
				var3 = 5.0D;
			}
			worldObj.createExplosion(this, posX, posY, posZ, (float) (4.0D + rand.nextDouble() * 1.5D * var3), true);
			setDead();
		}
	}
	
	@Override protected void fall(float par1)
	{
		if(par1 >= 3.0F)
		{
			float var2 = par1 / 10.0F;
			explodeCart(var2 * var2);
		}
		super.fall(par1);
	}
	
	@Override public float func_82146_a(Explosion par1Explosion, World par2World, int par3, int par4, int par5, Block par6Block)
	{
		return isIgnited() && (BlockRailBase.isRailBlock(par6Block.blockID) || BlockRailBase.isRailBlockAt(par2World, par3, par4 + 1, par5)) ? 0.0F : super.func_82146_a(par1Explosion, par2World, par3, par4, par5, par6Block);
	}
	
	public int func_94104_d()
	{
		return minecartTNTFuse;
	}
	
	@Override public boolean func_96091_a(Explosion par1Explosion, World par2World, int par3, int par4, int par5, int par6, float par7)
	{
		return isIgnited() && (BlockRailBase.isRailBlock(par6) || BlockRailBase.isRailBlockAt(par2World, par3, par4 + 1, par5)) ? false : super.func_96091_a(par1Explosion, par2World, par3, par4, par5, par6, par7);
	}
	
	@Override public Block getDefaultDisplayTile()
	{
		return Block.tnt;
	}
	
	@Override public int getMinecartType()
	{
		return 3;
	}
	
	@Override public void handleHealthUpdate(byte par1)
	{
		if(par1 == 10)
		{
			ignite();
		} else
		{
			super.handleHealthUpdate(par1);
		}
	}
	
	public void ignite()
	{
		minecartTNTFuse = 80;
		if(!worldObj.isRemote)
		{
			worldObj.setEntityState(this, (byte) 10);
			worldObj.playSoundAtEntity(this, "random.fuse", 1.0F, 1.0F);
		}
	}
	
	public boolean isIgnited()
	{
		return minecartTNTFuse > -1;
	}
	
	@Override public void killMinecart(DamageSource par1DamageSource)
	{
		super.killMinecart(par1DamageSource);
		double var2 = motionX * motionX + motionZ * motionZ;
		if(!par1DamageSource.isExplosion())
		{
			entityDropItem(new ItemStack(Block.tnt, 1), 0.0F);
		}
		if(par1DamageSource.isFireDamage() || par1DamageSource.isExplosion() || var2 >= 0.009999999776482582D)
		{
			explodeCart(var2);
		}
	}
	
	@Override public void onActivatorRailPass(int par1, int par2, int par3, boolean par4)
	{
		if(par4 && minecartTNTFuse < 0)
		{
			ignite();
		}
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		if(minecartTNTFuse > 0)
		{
			--minecartTNTFuse;
			worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
		} else if(minecartTNTFuse == 0)
		{
			explodeCart(motionX * motionX + motionZ * motionZ);
		}
		if(isCollidedHorizontally)
		{
			double var1 = motionX * motionX + motionZ * motionZ;
			if(var1 >= 0.009999999776482582D)
			{
				explodeCart(var1);
			}
		}
	}
	
	@Override protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		if(par1NBTTagCompound.hasKey("TNTFuse"))
		{
			minecartTNTFuse = par1NBTTagCompound.getInteger("TNTFuse");
		}
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("TNTFuse", minecartTNTFuse);
	}
}
