package net.minecraft.src;

import java.util.Random;

public class BlockDispenser extends BlockContainer
{
	public static final IRegistry dispenseBehaviorRegistry = new RegistryDefaulted(new BehaviorDefaultDispenseItem());
	protected Random random = new Random();
	protected Icon furnaceTopIcon;
	protected Icon furnaceFrontIcon;
	protected Icon field_96473_e;
	
	protected BlockDispenser(int par1)
	{
		super(par1, Material.rock);
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		TileEntityDispenser var7 = (TileEntityDispenser) par1World.getBlockTileEntity(par2, par3, par4);
		if(var7 != null)
		{
			for(int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
			{
				ItemStack var9 = var7.getStackInSlot(var8);
				if(var9 != null)
				{
					float var10 = random.nextFloat() * 0.8F + 0.1F;
					float var11 = random.nextFloat() * 0.8F + 0.1F;
					float var12 = random.nextFloat() * 0.8F + 0.1F;
					while(var9.stackSize > 0)
					{
						int var13 = random.nextInt(21) + 10;
						if(var13 > var9.stackSize)
						{
							var13 = var9.stackSize;
						}
						var9.stackSize -= var13;
						EntityItem var14 = new EntityItem(par1World, par2 + var10, par3 + var11, par4 + var12, new ItemStack(var9.itemID, var13, var9.getItemDamage()));
						if(var9.hasTagCompound())
						{
							var14.getEntityItem().setTagCompound((NBTTagCompound) var9.getTagCompound().copy());
						}
						float var15 = 0.05F;
						var14.motionX = (float) random.nextGaussian() * var15;
						var14.motionY = (float) random.nextGaussian() * var15 + 0.2F;
						var14.motionZ = (float) random.nextGaussian() * var15;
						par1World.spawnEntityInWorld(var14);
					}
				}
			}
			par1World.func_96440_m(par2, par3, par4, par5);
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	
	@Override public TileEntity createNewTileEntity(World par1World)
	{
		return new TileEntityDispenser();
	}
	
	protected void dispense(World par1World, int par2, int par3, int par4)
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
				IBehaviorDispenseItem var9 = getBehaviorForItemStack(var8);
				if(var9 != IBehaviorDispenseItem.itemDispenseBehaviorProvider)
				{
					ItemStack var10 = var9.dispense(var5, var8);
					var6.setInventorySlotContents(var7, var10.stackSize == 0 ? null : var10);
				}
			}
		}
	}
	
	protected IBehaviorDispenseItem getBehaviorForItemStack(ItemStack par1ItemStack)
	{
		return (IBehaviorDispenseItem) dispenseBehaviorRegistry.func_82594_a(par1ItemStack.getItem());
	}
	
	@Override public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
	{
		return Container.calcRedstoneFromInventory((IInventory) par1World.getBlockTileEntity(par2, par3, par4));
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		int var3 = par2 & 7;
		return par1 == var3 ? var3 != 1 && var3 != 0 ? furnaceFrontIcon : field_96473_e : var3 != 1 && var3 != 0 ? par1 != 1 && par1 != 0 ? blockIcon : furnaceTopIcon : furnaceTopIcon;
	}
	
	@Override public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if(par1World.isRemote) return true;
		else
		{
			TileEntityDispenser var10 = (TileEntityDispenser) par1World.getBlockTileEntity(par2, par3, par4);
			if(var10 != null)
			{
				par5EntityPlayer.displayGUIDispenser(var10);
			}
			return true;
		}
	}
	
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		super.onBlockAdded(par1World, par2, par3, par4);
		setDispenserDefaultDirection(par1World, par2, par3, par4);
	}
	
	@Override public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		int var7 = BlockPistonBase.determineOrientation(par1World, par2, par3, par4, par5EntityLivingBase);
		par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
		if(par6ItemStack.hasDisplayName())
		{
			((TileEntityDispenser) par1World.getBlockTileEntity(par2, par3, par4)).setCustomName(par6ItemStack.getDisplayName());
		}
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		boolean var6 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);
		int var7 = par1World.getBlockMetadata(par2, par3, par4);
		boolean var8 = (var7 & 8) != 0;
		if(var6 && !var8)
		{
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 | 8, 4);
		} else if(!var6 && var8)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 & -9, 4);
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("furnace_side");
		furnaceTopIcon = par1IconRegister.registerIcon("furnace_top");
		furnaceFrontIcon = par1IconRegister.registerIcon(func_111023_E() + "_front_horizontal");
		field_96473_e = par1IconRegister.registerIcon(func_111023_E() + "_front_vertical");
	}
	
	private void setDispenserDefaultDirection(World par1World, int par2, int par3, int par4)
	{
		if(!par1World.isRemote)
		{
			int var5 = par1World.getBlockId(par2, par3, par4 - 1);
			int var6 = par1World.getBlockId(par2, par3, par4 + 1);
			int var7 = par1World.getBlockId(par2 - 1, par3, par4);
			int var8 = par1World.getBlockId(par2 + 1, par3, par4);
			byte var9 = 3;
			if(Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var6])
			{
				var9 = 3;
			}
			if(Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var5])
			{
				var9 = 2;
			}
			if(Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var8])
			{
				var9 = 5;
			}
			if(Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var7])
			{
				var9 = 4;
			}
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var9, 2);
		}
	}
	
	@Override public int tickRate(World par1World)
	{
		return 4;
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(!par1World.isRemote)
		{
			dispense(par1World, par2, par3, par4);
		}
	}
	
	public static EnumFacing getFacing(int par0)
	{
		return EnumFacing.getFront(par0 & 7);
	}
	
	public static IPosition getIPositionFromBlockSource(IBlockSource par0IBlockSource)
	{
		EnumFacing var1 = getFacing(par0IBlockSource.getBlockMetadata());
		double var2 = par0IBlockSource.getX() + 0.7D * var1.getFrontOffsetX();
		double var4 = par0IBlockSource.getY() + 0.7D * var1.getFrontOffsetY();
		double var6 = par0IBlockSource.getZ() + 0.7D * var1.getFrontOffsetZ();
		return new PositionImpl(var2, var4, var6);
	}
}
