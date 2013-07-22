package net.minecraft.src;

public class BlockDropper extends BlockDispenser
{
	private final IBehaviorDispenseItem dropperDefaultBehaviour = new BehaviorDefaultDispenseItem();
	
	protected BlockDropper(int par1)
	{
		super(par1);
	}
	
	@Override public TileEntity createNewTileEntity(World par1World)
	{
		return new TileEntityDropper();
	}
	
	@Override protected void dispense(World par1World, int par2, int par3, int par4)
	{
		BlockSourceImpl var5 = new BlockSourceImpl(par1World, par2, par3, par4);
		TileEntityDispenser var6 = (TileEntityDispenser) var5.getBlockTileEntity();
		if(var6 != null)
		{
			int var7 = var6.getRandomStackFromInventory();
			if(var7 < 0)
			{
				par1World.playAuxSFX(1001, par2, par3, par4, 0);
			} else
			{
				ItemStack var8 = var6.getStackInSlot(var7);
				int var9 = par1World.getBlockMetadata(par2, par3, par4) & 7;
				IInventory var10 = TileEntityHopper.getInventoryAtLocation(par1World, par2 + Facing.offsetsXForSide[var9], par3 + Facing.offsetsYForSide[var9], par4 + Facing.offsetsZForSide[var9]);
				ItemStack var11;
				if(var10 != null)
				{
					var11 = TileEntityHopper.insertStack(var10, var8.copy().splitStack(1), Facing.oppositeSide[var9]);
					if(var11 == null)
					{
						var11 = var8.copy();
						if(--var11.stackSize == 0)
						{
							var11 = null;
						}
					} else
					{
						var11 = var8.copy();
					}
				} else
				{
					var11 = dropperDefaultBehaviour.dispense(var5, var8);
					if(var11 != null && var11.stackSize == 0)
					{
						var11 = null;
					}
				}
				var6.setInventorySlotContents(var7, var11);
			}
		}
	}
	
	@Override protected IBehaviorDispenseItem getBehaviorForItemStack(ItemStack par1ItemStack)
	{
		return dropperDefaultBehaviour;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("furnace_side");
		furnaceTopIcon = par1IconRegister.registerIcon("furnace_top");
		furnaceFrontIcon = par1IconRegister.registerIcon("dropper_front");
		field_96473_e = par1IconRegister.registerIcon("dropper_front_vertical");
	}
}
