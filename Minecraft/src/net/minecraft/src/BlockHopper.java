package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockHopper extends BlockContainer
{
	private final Random field_94457_a = new Random();
	private Icon hopperIcon;
	private Icon hopperTopIcon;
	private Icon hopperInsideIcon;
	
	public BlockHopper(int p_i9064_1_)
	{
		super(p_i9064_1_, Material.iron);
		setCreativeTab(CreativeTabs.tabRedstone);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override public void addCollisionBoxesToList(World p_71871_1_, int p_71871_2_, int p_71871_3_, int p_71871_4_, AxisAlignedBB p_71871_5_, List p_71871_6_, Entity p_71871_7_)
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
		super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
		float var8 = 0.125F;
		setBlockBounds(0.0F, 0.0F, 0.0F, var8, 1.0F, 1.0F);
		super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var8);
		super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
		setBlockBounds(1.0F - var8, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
		setBlockBounds(0.0F, 0.0F, 1.0F - var8, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		TileEntityHopper var7 = (TileEntityHopper) p_71852_1_.getBlockTileEntity(p_71852_2_, p_71852_3_, p_71852_4_);
		if(var7 != null)
		{
			for(int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
			{
				ItemStack var9 = var7.getStackInSlot(var8);
				if(var9 != null)
				{
					float var10 = field_94457_a.nextFloat() * 0.8F + 0.1F;
					float var11 = field_94457_a.nextFloat() * 0.8F + 0.1F;
					float var12 = field_94457_a.nextFloat() * 0.8F + 0.1F;
					while(var9.stackSize > 0)
					{
						int var13 = field_94457_a.nextInt(21) + 10;
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
						var14.motionX = (float) field_94457_a.nextGaussian() * var15;
						var14.motionY = (float) field_94457_a.nextGaussian() * var15 + 0.2F;
						var14.motionZ = (float) field_94457_a.nextGaussian() * var15;
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
		return new TileEntityHopper();
	}
	
	@Override public int getComparatorInputOverride(World p_94328_1_, int p_94328_2_, int p_94328_3_, int p_94328_4_, int p_94328_5_)
	{
		return Container.calcRedstoneFromInventory(getHopperTile(p_94328_1_, p_94328_2_, p_94328_3_, p_94328_4_));
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 1 ? hopperTopIcon : hopperIcon;
	}
	
	@Override public String getItemIconName()
	{
		return "hopper";
	}
	
	@Override public int getRenderType()
	{
		return 38;
	}
	
	@Override public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		if(p_71903_1_.isRemote) return true;
		else
		{
			TileEntityHopper var10 = getHopperTile(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_);
			if(var10 != null)
			{
				p_71903_5_.displayGUIHopper(var10);
			}
			return true;
		}
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		super.onBlockAdded(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
		updateMetadata(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
	}
	
	@Override public int onBlockPlaced(World p_85104_1_, int p_85104_2_, int p_85104_3_, int p_85104_4_, int p_85104_5_, float p_85104_6_, float p_85104_7_, float p_85104_8_, int p_85104_9_)
	{
		int var10 = Facing.oppositeSide[p_85104_5_];
		if(var10 == 1)
		{
			var10 = 0;
		}
		return var10;
	}
	
	@Override public void onBlockPlacedBy(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
	{
		super.onBlockPlacedBy(p_71860_1_, p_71860_2_, p_71860_3_, p_71860_4_, p_71860_5_, p_71860_6_);
		if(p_71860_6_.hasDisplayName())
		{
			TileEntityHopper var7 = getHopperTile(p_71860_1_, p_71860_2_, p_71860_3_, p_71860_4_);
			var7.setInventoryName(p_71860_6_.getDisplayName());
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		updateMetadata(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_);
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		hopperIcon = par1IconRegister.registerIcon("hopper");
		hopperTopIcon = par1IconRegister.registerIcon("hopper_top");
		hopperInsideIcon = par1IconRegister.registerIcon("hopper_inside");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}
	
	private void updateMetadata(World p_96471_1_, int p_96471_2_, int p_96471_3_, int p_96471_4_)
	{
		int var5 = p_96471_1_.getBlockMetadata(p_96471_2_, p_96471_3_, p_96471_4_);
		int var6 = getDirectionFromMetadata(var5);
		boolean var7 = !p_96471_1_.isBlockIndirectlyGettingPowered(p_96471_2_, p_96471_3_, p_96471_4_);
		boolean var8 = getIsBlockNotPoweredFromMetadata(var5);
		if(var7 != var8)
		{
			p_96471_1_.setBlockMetadataWithNotify(p_96471_2_, p_96471_3_, p_96471_4_, var6 | (var7 ? 0 : 8), 4);
		}
	}
	
	public static int getDirectionFromMetadata(int p_94451_0_)
	{
		return p_94451_0_ & 7;
	}
	
	public static Icon getHopperIcon(String par0Str)
	{
		return par0Str == "hopper" ? Block.hopperBlock.hopperIcon : par0Str == "hopper_inside" ? Block.hopperBlock.hopperInsideIcon : null;
	}
	
	public static TileEntityHopper getHopperTile(IBlockAccess p_98213_0_, int p_98213_1_, int p_98213_2_, int p_98213_3_)
	{
		return (TileEntityHopper) p_98213_0_.getBlockTileEntity(p_98213_1_, p_98213_2_, p_98213_3_);
	}
	
	public static boolean getIsBlockNotPoweredFromMetadata(int p_94452_0_)
	{
		return (p_94452_0_ & 8) != 8;
	}
}
