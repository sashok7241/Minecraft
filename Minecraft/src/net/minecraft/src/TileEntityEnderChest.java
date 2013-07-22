package net.minecraft.src;

public class TileEntityEnderChest extends TileEntity
{
	public float lidAngle;
	public float prevLidAngle;
	public int numUsingPlayers;
	private int ticksSinceSync;
	
	public void closeChest()
	{
		--numUsingPlayers;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, Block.enderChest.blockID, 1, numUsingPlayers);
	}
	
	@Override public void invalidate()
	{
		updateContainingBlockInfo();
		super.invalidate();
	}
	
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : par1EntityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}
	
	public void openChest()
	{
		++numUsingPlayers;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, Block.enderChest.blockID, 1, numUsingPlayers);
	}
	
	@Override public boolean receiveClientEvent(int par1, int par2)
	{
		if(par1 == 1)
		{
			numUsingPlayers = par2;
			return true;
		} else return super.receiveClientEvent(par1, par2);
	}
	
	@Override public void updateEntity()
	{
		super.updateEntity();
		if(++ticksSinceSync % 20 * 4 == 0)
		{
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, Block.enderChest.blockID, 1, numUsingPlayers);
		}
		prevLidAngle = lidAngle;
		float var1 = 0.1F;
		double var4;
		if(numUsingPlayers > 0 && lidAngle == 0.0F)
		{
			double var2 = xCoord + 0.5D;
			var4 = zCoord + 0.5D;
			worldObj.playSoundEffect(var2, yCoord + 0.5D, var4, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}
		if(numUsingPlayers == 0 && lidAngle > 0.0F || numUsingPlayers > 0 && lidAngle < 1.0F)
		{
			float var8 = lidAngle;
			if(numUsingPlayers > 0)
			{
				lidAngle += var1;
			} else
			{
				lidAngle -= var1;
			}
			if(lidAngle > 1.0F)
			{
				lidAngle = 1.0F;
			}
			float var3 = 0.5F;
			if(lidAngle < var3 && var8 >= var3)
			{
				var4 = xCoord + 0.5D;
				double var6 = zCoord + 0.5D;
				worldObj.playSoundEffect(var4, yCoord + 0.5D, var6, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
			if(lidAngle < 0.0F)
			{
				lidAngle = 0.0F;
			}
		}
	}
}
