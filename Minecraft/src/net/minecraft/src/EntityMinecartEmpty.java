package net.minecraft.src;

public class EntityMinecartEmpty extends EntityMinecart
{
	public EntityMinecartEmpty(World par1World)
	{
		super(par1World);
	}
	
	public EntityMinecartEmpty(World par1, double par2, double par4, double par6)
	{
		super(par1, par2, par4, par6);
	}
	
	@Override public int getMinecartType()
	{
		return 0;
	}
	
	@Override public boolean interact(EntityPlayer par1EntityPlayer)
	{
		if(riddenByEntity != null && riddenByEntity instanceof EntityPlayer && riddenByEntity != par1EntityPlayer) return true;
		else if(riddenByEntity != null && riddenByEntity != par1EntityPlayer) return false;
		else
		{
			if(!worldObj.isRemote)
			{
				par1EntityPlayer.mountEntity(this);
			}
			return true;
		}
	}
}
