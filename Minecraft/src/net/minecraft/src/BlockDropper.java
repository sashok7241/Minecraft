package net.minecraft.src;

public class BlockDropper extends BlockDispenser
{
	private final IBehaviorDispenseItem dropperDefaultBehaviour = new BehaviorDefaultDispenseItem();
	
	protected BlockDropper(int p_i10060_1_)
	{
		super(p_i10060_1_);
	}
	
	@Override public TileEntity createNewTileEntity(World p_72274_1_)
	{
		return new TileEntityDropper();
	}
	
	@Override protected void dispense(World p_82526_1_, int p_82526_2_, int p_82526_3_, int p_82526_4_)
	{
		BlockSourceImpl var5 = new BlockSourceImpl(p_82526_1_, p_82526_2_, p_82526_3_, p_82526_4_);
		TileEntityDispenser var6 = (TileEntityDispenser) var5.getBlockTileEntity();
		if(var6 != null)
		{
			int var7 = var6.getRandomStackFromInventory();
			if(var7 < 0)
			{
				p_82526_1_.playAuxSFX(1001, p_82526_2_, p_82526_3_, p_82526_4_, 0);
			} else
			{
				ItemStack var8 = var6.getStackInSlot(var7);
				int var9 = p_82526_1_.getBlockMetadata(p_82526_2_, p_82526_3_, p_82526_4_) & 7;
				IInventory var10 = TileEntityHopper.getInventoryAtLocation(p_82526_1_, p_82526_2_ + Facing.offsetsXForSide[var9], p_82526_3_ + Facing.offsetsYForSide[var9], p_82526_4_ + Facing.offsetsZForSide[var9]);
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
	
	@Override protected IBehaviorDispenseItem getBehaviorForItemStack(ItemStack p_96472_1_)
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
