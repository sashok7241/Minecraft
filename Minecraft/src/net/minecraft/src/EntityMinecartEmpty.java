package net.minecraft.src;

public class EntityMinecartEmpty extends EntityMinecart
{
	public EntityMinecartEmpty(World p_i9005_1_)
	{
		super(p_i9005_1_);
	}
	
	public EntityMinecartEmpty(World p_i9006_1_, double p_i9006_2_, double p_i9006_4_, double p_i9006_6_)
	{
		super(p_i9006_1_, p_i9006_2_, p_i9006_4_, p_i9006_6_);
	}
	
	@Override public int getMinecartType()
	{
		return 0;
	}
	
	@Override public boolean interact(EntityPlayer p_70085_1_)
	{
		if(riddenByEntity != null && riddenByEntity instanceof EntityPlayer && riddenByEntity != p_70085_1_) return true;
		else if(riddenByEntity != null && riddenByEntity != p_70085_1_) return false;
		else
		{
			if(!worldObj.isRemote)
			{
				p_70085_1_.mountEntity(this);
			}
			return true;
		}
	}
}
