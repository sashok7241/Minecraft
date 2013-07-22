package net.minecraft.src;

public class EntityAIOcelotSit extends EntityAIBase
{
	private final EntityOcelot theOcelot;
	private final double field_75404_b;
	private int currentTick;
	private int field_75402_d;
	private int maxSittingTicks;
	private int sitableBlockX;
	private int sitableBlockY;
	private int sitableBlockZ;
	
	public EntityAIOcelotSit(EntityOcelot par1EntityOcelot, double par2)
	{
		theOcelot = par1EntityOcelot;
		field_75404_b = par2;
		setMutexBits(5);
	}
	
	@Override public boolean continueExecuting()
	{
		return currentTick <= maxSittingTicks && field_75402_d <= 60 && isSittableBlock(theOcelot.worldObj, sitableBlockX, sitableBlockY, sitableBlockZ);
	}
	
	private boolean getNearbySitableBlockDistance()
	{
		int var1 = (int) theOcelot.posY;
		double var2 = 2.147483647E9D;
		for(int var4 = (int) theOcelot.posX - 8; var4 < theOcelot.posX + 8.0D; ++var4)
		{
			for(int var5 = (int) theOcelot.posZ - 8; var5 < theOcelot.posZ + 8.0D; ++var5)
			{
				if(isSittableBlock(theOcelot.worldObj, var4, var1, var5) && theOcelot.worldObj.isAirBlock(var4, var1 + 1, var5))
				{
					double var6 = theOcelot.getDistanceSq(var4, var1, var5);
					if(var6 < var2)
					{
						sitableBlockX = var4;
						sitableBlockY = var1;
						sitableBlockZ = var5;
						var2 = var6;
					}
				}
			}
		}
		return var2 < 2.147483647E9D;
	}
	
	private boolean isSittableBlock(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockId(par2, par3, par4);
		int var6 = par1World.getBlockMetadata(par2, par3, par4);
		if(var5 == Block.chest.blockID)
		{
			TileEntityChest var7 = (TileEntityChest) par1World.getBlockTileEntity(par2, par3, par4);
			if(var7.numUsingPlayers < 1) return true;
		} else
		{
			if(var5 == Block.furnaceBurning.blockID) return true;
			if(var5 == Block.bed.blockID && !BlockBed.isBlockHeadOfBed(var6)) return true;
		}
		return false;
	}
	
	@Override public void resetTask()
	{
		theOcelot.setSitting(false);
	}
	
	@Override public boolean shouldExecute()
	{
		return theOcelot.isTamed() && !theOcelot.isSitting() && theOcelot.getRNG().nextDouble() <= 0.006500000134110451D && getNearbySitableBlockDistance();
	}
	
	@Override public void startExecuting()
	{
		theOcelot.getNavigator().tryMoveToXYZ(sitableBlockX + 0.5D, sitableBlockY + 1, sitableBlockZ + 0.5D, field_75404_b);
		currentTick = 0;
		field_75402_d = 0;
		maxSittingTicks = theOcelot.getRNG().nextInt(theOcelot.getRNG().nextInt(1200) + 1200) + 1200;
		theOcelot.func_70907_r().setSitting(false);
	}
	
	@Override public void updateTask()
	{
		++currentTick;
		theOcelot.func_70907_r().setSitting(false);
		if(theOcelot.getDistanceSq(sitableBlockX, sitableBlockY + 1, sitableBlockZ) > 1.0D)
		{
			theOcelot.setSitting(false);
			theOcelot.getNavigator().tryMoveToXYZ(sitableBlockX + 0.5D, sitableBlockY + 1, sitableBlockZ + 0.5D, field_75404_b);
			++field_75402_d;
		} else if(!theOcelot.isSitting())
		{
			theOcelot.setSitting(true);
		} else
		{
			--field_75402_d;
		}
	}
}
