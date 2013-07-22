package net.minecraft.src;

import java.util.Random;

public class BlockRedstoneLight extends Block
{
	private final boolean powered;
	
	public BlockRedstoneLight(int par1, boolean par2)
	{
		super(par1, Material.redstoneLight);
		powered = par2;
		if(par2)
		{
			setLightValue(1.0F);
		}
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return Block.redstoneLampIdle.blockID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Block.redstoneLampIdle.blockID;
	}
	
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		if(!par1World.isRemote)
		{
			if(powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
			{
				par1World.scheduleBlockUpdate(par2, par3, par4, blockID, 4);
			} else if(!powered && par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
			{
				par1World.setBlock(par2, par3, par4, Block.redstoneLampActive.blockID, 0, 2);
			}
		}
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if(!par1World.isRemote)
		{
			if(powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
			{
				par1World.scheduleBlockUpdate(par2, par3, par4, blockID, 4);
			} else if(!powered && par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
			{
				par1World.setBlock(par2, par3, par4, Block.redstoneLampActive.blockID, 0, 2);
			}
		}
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(!par1World.isRemote && powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
		{
			par1World.setBlock(par2, par3, par4, Block.redstoneLampIdle.blockID, 0, 2);
		}
	}
}
