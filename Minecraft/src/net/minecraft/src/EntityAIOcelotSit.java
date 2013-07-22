package net.minecraft.src;

public class EntityAIOcelotSit extends EntityAIBase
{
	private final EntityOcelot theOcelot;
	private final float field_75404_b;
	private int currentTick = 0;
	private int field_75402_d = 0;
	private int maxSittingTicks = 0;
	private int sitableBlockX = 0;
	private int sitableBlockY = 0;
	private int sitableBlockZ = 0;
	
	public EntityAIOcelotSit(EntityOcelot p_i3482_1_, float p_i3482_2_)
	{
		theOcelot = p_i3482_1_;
		field_75404_b = p_i3482_2_;
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
	
	private boolean isSittableBlock(World p_75398_1_, int p_75398_2_, int p_75398_3_, int p_75398_4_)
	{
		int var5 = p_75398_1_.getBlockId(p_75398_2_, p_75398_3_, p_75398_4_);
		int var6 = p_75398_1_.getBlockMetadata(p_75398_2_, p_75398_3_, p_75398_4_);
		if(var5 == Block.chest.blockID)
		{
			TileEntityChest var7 = (TileEntityChest) p_75398_1_.getBlockTileEntity(p_75398_2_, p_75398_3_, p_75398_4_);
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
