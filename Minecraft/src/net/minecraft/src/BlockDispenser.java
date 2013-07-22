package net.minecraft.src;

import java.util.Random;

public class BlockDispenser extends BlockContainer
{
	public static final IRegistry dispenseBehaviorRegistry = new RegistryDefaulted(new BehaviorDefaultDispenseItem());
	protected Random random = new Random();
	protected Icon furnaceTopIcon;
	protected Icon furnaceFrontIcon;
	protected Icon field_96473_e;
	
	protected BlockDispenser(int p_i3938_1_)
	{
		super(p_i3938_1_, Material.rock);
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		TileEntityDispenser var7 = (TileEntityDispenser) p_71852_1_.getBlockTileEntity(p_71852_2_, p_71852_3_, p_71852_4_);
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
						EntityItem var14 = new EntityItem(p_71852_1_, p_71852_2_ + var10, p_71852_3_ + var11, p_71852_4_ + var12, new ItemStack(var9.itemID, var13, var9.getItemDamage()));
						if(var9.hasTagCompound())
						{
							var14.getEntityItem().setTagCompound((NBTTagCompound) var9.getTagCompound().copy());
						}
						float var15 = 0.05F;
						var14.motionX = (float) random.nextGaussian() * var15;
						var14.motionY = (float) random.nextGaussian() * var15 + 0.2F;
						var14.motionZ = (float) random.nextGaussian() * var15;
						p_71852_1_.spawnEntityInWorld(var14);
					}
				}
			}
			p_71852_1_.func_96440_m(p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_);
		}
		super.breakBlock(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
	}
	
	@Override public TileEntity createNewTileEntity(World p_72274_1_)
	{
		return new TileEntityDispenser();
	}
	
	protected void dispense(World p_82526_1_, int p_82526_2_, int p_82526_3_, int p_82526_4_)
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
				IBehaviorDispenseItem var9 = getBehaviorForItemStack(var8);
				if(var9 != IBehaviorDispenseItem.itemDispenseBehaviorProvider)
				{
					ItemStack var10 = var9.dispense(var5, var8);
					var6.setInventorySlotContents(var7, var10.stackSize == 0 ? null : var10);
				}
			}
		}
	}
	
	protected IBehaviorDispenseItem getBehaviorForItemStack(ItemStack p_96472_1_)
	{
		return (IBehaviorDispenseItem) dispenseBehaviorRegistry.func_82594_a(p_96472_1_.getItem());
	}
	
	@Override public int getComparatorInputOverride(World p_94328_1_, int p_94328_2_, int p_94328_3_, int p_94328_4_, int p_94328_5_)
	{
		return Container.calcRedstoneFromInventory((IInventory) p_94328_1_.getBlockTileEntity(p_94328_2_, p_94328_3_, p_94328_4_));
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
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		if(p_71903_1_.isRemote) return true;
		else
		{
			TileEntityDispenser var10 = (TileEntityDispenser) p_71903_1_.getBlockTileEntity(p_71903_2_, p_71903_3_, p_71903_4_);
			if(var10 != null)
			{
				p_71903_5_.displayGUIDispenser(var10);
			}
			return true;
		}
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		super.onBlockAdded(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
		setDispenserDefaultDirection(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
	}
	
	@Override public void onBlockPlacedBy(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
	{
		int var7 = BlockPistonBase.determineOrientation(p_71860_1_, p_71860_2_, p_71860_3_, p_71860_4_, p_71860_5_);
		p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, var7, 2);
		if(p_71860_6_.hasDisplayName())
		{
			((TileEntityDispenser) p_71860_1_.getBlockTileEntity(p_71860_2_, p_71860_3_, p_71860_4_)).setCustomName(p_71860_6_.getDisplayName());
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		boolean var6 = p_71863_1_.isBlockIndirectlyGettingPowered(p_71863_2_, p_71863_3_, p_71863_4_) || p_71863_1_.isBlockIndirectlyGettingPowered(p_71863_2_, p_71863_3_ + 1, p_71863_4_);
		int var7 = p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_);
		boolean var8 = (var7 & 8) != 0;
		if(var6 && !var8)
		{
			p_71863_1_.scheduleBlockUpdate(p_71863_2_, p_71863_3_, p_71863_4_, blockID, tickRate(p_71863_1_));
			p_71863_1_.setBlockMetadataWithNotify(p_71863_2_, p_71863_3_, p_71863_4_, var7 | 8, 4);
		} else if(!var6 && var8)
		{
			p_71863_1_.setBlockMetadataWithNotify(p_71863_2_, p_71863_3_, p_71863_4_, var7 & -9, 4);
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("furnace_side");
		furnaceTopIcon = par1IconRegister.registerIcon("furnace_top");
		furnaceFrontIcon = par1IconRegister.registerIcon("dispenser_front");
		field_96473_e = par1IconRegister.registerIcon("dispenser_front_vertical");
	}
	
	private void setDispenserDefaultDirection(World p_72280_1_, int p_72280_2_, int p_72280_3_, int p_72280_4_)
	{
		if(!p_72280_1_.isRemote)
		{
			int var5 = p_72280_1_.getBlockId(p_72280_2_, p_72280_3_, p_72280_4_ - 1);
			int var6 = p_72280_1_.getBlockId(p_72280_2_, p_72280_3_, p_72280_4_ + 1);
			int var7 = p_72280_1_.getBlockId(p_72280_2_ - 1, p_72280_3_, p_72280_4_);
			int var8 = p_72280_1_.getBlockId(p_72280_2_ + 1, p_72280_3_, p_72280_4_);
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
			p_72280_1_.setBlockMetadataWithNotify(p_72280_2_, p_72280_3_, p_72280_4_, var9, 2);
		}
	}
	
	@Override public int tickRate(World p_71859_1_)
	{
		return 4;
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(!p_71847_1_.isRemote)
		{
			dispense(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
		}
	}
	
	public static EnumFacing getFacing(int p_100009_0_)
	{
		return EnumFacing.getFront(p_100009_0_ & 7);
	}
	
	public static IPosition getIPositionFromBlockSource(IBlockSource p_82525_0_)
	{
		EnumFacing var1 = getFacing(p_82525_0_.getBlockMetadata());
		double var2 = p_82525_0_.getX() + 0.7D * var1.getFrontOffsetX();
		double var4 = p_82525_0_.getY() + 0.7D * var1.getFrontOffsetY();
		double var6 = p_82525_0_.getZ() + 0.7D * var1.getFrontOffsetZ();
		return new PositionImpl(var2, var4, var6);
	}
}
