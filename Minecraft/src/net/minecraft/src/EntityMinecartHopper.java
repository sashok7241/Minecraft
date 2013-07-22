package net.minecraft.src;

import java.util.List;

public class EntityMinecartHopper extends EntityMinecartContainer implements Hopper
{
	private boolean isBlocked = true;
	private int transferTicker = -1;
	
	public EntityMinecartHopper(World p_i10053_1_)
	{
		super(p_i10053_1_);
	}
	
	public EntityMinecartHopper(World p_i10054_1_, double p_i10054_2_, double p_i10054_4_, double p_i10054_6_)
	{
		super(p_i10054_1_, p_i10054_2_, p_i10054_4_, p_i10054_6_);
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
	
	@Override public boolean interact(EntityPlayer p_70085_1_)
	{
		if(!worldObj.isRemote)
		{
			p_70085_1_.displayGUIHopperMinecart(this);
		}
		return true;
	}
	
	@Override public void killMinecart(DamageSource p_94095_1_)
	{
		super.killMinecart(p_94095_1_);
		dropItemWithOffset(Block.hopperBlock.blockID, 1, 0.0F);
	}
	
	@Override public void onActivatorRailPass(int p_96095_1_, int p_96095_2_, int p_96095_3_, boolean p_96095_4_)
	{
		boolean var5 = !p_96095_4_;
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
	
	@Override protected void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		transferTicker = p_70037_1_.getInteger("TransferCooldown");
	}
	
	public void setBlocked(boolean p_96110_1_)
	{
		isBlocked = p_96110_1_;
	}
	
	public void setTransferTicker(int p_98042_1_)
	{
		transferTicker = p_98042_1_;
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setInteger("TransferCooldown", transferTicker);
	}
}
