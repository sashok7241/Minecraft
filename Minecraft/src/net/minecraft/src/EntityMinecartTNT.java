package net.minecraft.src;

public class EntityMinecartTNT extends EntityMinecart
{
	private int minecartTNTFuse = -1;
	
	public EntityMinecartTNT(World p_i9007_1_)
	{
		super(p_i9007_1_);
	}
	
	public EntityMinecartTNT(World p_i9008_1_, double p_i9008_2_, double p_i9008_4_, double p_i9008_6_)
	{
		super(p_i9008_1_, p_i9008_2_, p_i9008_4_, p_i9008_6_);
	}
	
	protected void explodeCart(double p_94103_1_)
	{
		if(!worldObj.isRemote)
		{
			double var3 = Math.sqrt(p_94103_1_);
			if(var3 > 5.0D)
			{
				var3 = 5.0D;
			}
			worldObj.createExplosion(this, posX, posY, posZ, (float) (4.0D + rand.nextDouble() * 1.5D * var3), true);
			setDead();
		}
	}
	
	@Override protected void fall(float p_70069_1_)
	{
		if(p_70069_1_ >= 3.0F)
		{
			float var2 = p_70069_1_ / 10.0F;
			explodeCart(var2 * var2);
		}
		super.fall(p_70069_1_);
	}
	
	@Override public float func_82146_a(Explosion p_82146_1_, World p_82146_2_, int p_82146_3_, int p_82146_4_, int p_82146_5_, Block p_82146_6_)
	{
		return isIgnited() && (BlockRailBase.isRailBlock(p_82146_6_.blockID) || BlockRailBase.isRailBlockAt(p_82146_2_, p_82146_3_, p_82146_4_ + 1, p_82146_5_)) ? 0.0F : super.func_82146_a(p_82146_1_, p_82146_2_, p_82146_3_, p_82146_4_, p_82146_5_, p_82146_6_);
	}
	
	public int func_94104_d()
	{
		return minecartTNTFuse;
	}
	
	@Override public boolean func_96091_a(Explosion p_96091_1_, World p_96091_2_, int p_96091_3_, int p_96091_4_, int p_96091_5_, int p_96091_6_, float p_96091_7_)
	{
		return isIgnited() && (BlockRailBase.isRailBlock(p_96091_6_) || BlockRailBase.isRailBlockAt(p_96091_2_, p_96091_3_, p_96091_4_ + 1, p_96091_5_)) ? false : super.func_96091_a(p_96091_1_, p_96091_2_, p_96091_3_, p_96091_4_, p_96091_5_, p_96091_6_, p_96091_7_);
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
	
	@Override public void killMinecart(DamageSource p_94095_1_)
	{
		super.killMinecart(p_94095_1_);
		double var2 = motionX * motionX + motionZ * motionZ;
		if(!p_94095_1_.isExplosion())
		{
			entityDropItem(new ItemStack(Block.tnt, 1), 0.0F);
		}
		if(p_94095_1_.isFireDamage() || p_94095_1_.isExplosion() || var2 >= 0.009999999776482582D)
		{
			explodeCart(var2);
		}
	}
	
	@Override public void onActivatorRailPass(int p_96095_1_, int p_96095_2_, int p_96095_3_, boolean p_96095_4_)
	{
		if(p_96095_4_ && minecartTNTFuse < 0)
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
	
	@Override protected void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		if(p_70037_1_.hasKey("TNTFuse"))
		{
			minecartTNTFuse = p_70037_1_.getInteger("TNTFuse");
		}
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setInteger("TNTFuse", minecartTNTFuse);
	}
}
