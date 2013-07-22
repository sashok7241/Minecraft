package net.minecraft.src;

import java.util.List;

public class EntityMinecartHopper extends EntityMinecartContainer implements Hopper
{
	private boolean isBlocked = true;
	private int transferTicker = -1;
	
	public EntityMinecartHopper(World par1World)
	{
		super(par1World);
	}
	
	public EntityMinecartHopper(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6);
	}
	
	public boolean canTransfer()
	{
		return transferTicker > 0;
	}
	
	public boolean func_96112_aD()
	{
		if(TileEntityHopper.suckItemsIntoHopper(this)) return true;
		else
		{
			List var1 = worldObj.selectEntitiesWithinAABB(EntityItem.class, boundingBox.expand(0.25D, 0.0D, 0.25D), IEntitySelector.selectAnything);
			if(var1.size() > 0)
			{
				TileEntityHopper.func_96114_a(this, (EntityItem) var1.get(0));
			}
			return false;
		}
	}
	
	public boolean getBlocked()
	{
		return isBlocked;
	}
	
	@Override public Block getDefaultDisplayTile()
	{
		return Block.hopperBlock;
	}
	
	@Override public int getDefaultDisplayTileOffset()
	{
		return 1;
	}
	
	@Override public int getMinecartType()
	{
		return 5;
	}
	
	@Override public int getSizeInventory()
	{
		return 5;
	}
	
	@Override public World getWorldObj()
	{
		return worldObj;
	}
	
	@Override public double getXPos()
	{
		return posX;
	}
	
	@Override public double getYPos()
	{
		return posY;
	}
	
	@Override public double getZPos()
	{
		return posZ;
	}
	
	@Override public boolean interact(EntityPlayer par1EntityPlayer)
	{
		if(!worldObj.isRemote)
		{
			par1EntityPlayer.displayGUIHopperMinecart(this);
		}
		return true;
	}
	
	@Override public void killMinecart(DamageSource par1DamageSource)
	{
		super.killMinecart(par1DamageSource);
		dropItemWithOffset(Block.hopperBlock.blockID, 1, 0.0F);
	}
	
	@Override public void onActivatorRailPass(int par1, int par2, int par3, boolean par4)
	{
		boolean var5 = !par4;
		if(var5 != getBlocked())
		{
			setBlocked(var5);
		}
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		if(!worldObj.isRemote && isEntityAlive() && getBlocked())
		{
			--transferTicker;
			if(!canTransfer())
			{
				setTransferTicker(0);
				if(func_96112_aD())
				{
					setTransferTicker(4);
					onInventoryChanged();
				}
			}
		}
	}
	
	@Override protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		transferTicker = par1NBTTagCompound.getInteger("TransferCooldown");
	}
	
	public void setBlocked(boolean par1)
	{
		isBlocked = par1;
	}
	
	public void setTransferTicker(int par1)
	{
		transferTicker = par1;
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("TransferCooldown", transferTicker);
	}
}
