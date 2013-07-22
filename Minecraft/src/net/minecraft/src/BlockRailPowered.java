package net.minecraft.src;

public class BlockRailPowered extends BlockRailBase
{
	protected Icon theIcon;
	
	protected BlockRailPowered(int p_i9014_1_)
	{
		super(p_i9014_1_, true);
	}
	
	@Override protected void func_94358_a(World p_94358_1_, int p_94358_2_, int p_94358_3_, int p_94358_4_, int p_94358_5_, int p_94358_6_, int p_94358_7_)
	{
		boolean var8 = p_94358_1_.isBlockIndirectlyGettingPowered(p_94358_2_, p_94358_3_, p_94358_4_);
		var8 = var8 || func_94360_a(p_94358_1_, p_94358_2_, p_94358_3_, p_94358_4_, p_94358_5_, true, 0) || func_94360_a(p_94358_1_, p_94358_2_, p_94358_3_, p_94358_4_, p_94358_5_, false, 0);
		boolean var9 = false;
		if(var8 && (p_94358_5_ & 8) == 0)
		{
			p_94358_1_.setBlockMetadataWithNotify(p_94358_2_, p_94358_3_, p_94358_4_, p_94358_6_ | 8, 3);
			var9 = true;
		} else if(!var8 && (p_94358_5_ & 8) != 0)
		{
			p_94358_1_.setBlockMetadataWithNotify(p_94358_2_, p_94358_3_, p_94358_4_, p_94358_6_, 3);
			var9 = true;
		}
		if(var9)
		{
			p_94358_1_.notifyBlocksOfNeighborChange(p_94358_2_, p_94358_3_ - 1, p_94358_4_, blockID);
			if(p_94358_6_ == 2 || p_94358_6_ == 3 || p_94358_6_ == 4 || p_94358_6_ == 5)
			{
				p_94358_1_.notifyBlocksOfNeighborChange(p_94358_2_, p_94358_3_ + 1, p_94358_4_, blockID);
			}
		}
	}
	
	protected boolean func_94360_a(World p_94360_1_, int p_94360_2_, int p_94360_3_, int p_94360_4_, int p_94360_5_, boolean p_94360_6_, int p_94360_7_)
	{
		if(p_94360_7_ >= 8) return false;
		else
		{
			int var8 = p_94360_5_ & 7;
			boolean var9 = true;
			switch(var8)
			{
				case 0:
					if(p_94360_6_)
					{
						++p_94360_4_;
					} else
					{
						--p_94360_4_;
					}
					break;
				case 1:
					if(p_94360_6_)
					{
						--p_94360_2_;
					} else
					{
						++p_94360_2_;
					}
					break;
				case 2:
					if(p_94360_6_)
					{
						--p_94360_2_;
					} else
					{
						++p_94360_2_;
						++p_94360_3_;
						var9 = false;
					}
					var8 = 1;
					break;
				case 3:
					if(p_94360_6_)
					{
						--p_94360_2_;
						++p_94360_3_;
						var9 = false;
					} else
					{
						++p_94360_2_;
					}
					var8 = 1;
					break;
				case 4:
					if(p_94360_6_)
					{
						++p_94360_4_;
					} else
					{
						--p_94360_4_;
						++p_94360_3_;
						var9 = false;
					}
					var8 = 0;
					break;
				case 5:
					if(p_94360_6_)
					{
						++p_94360_4_;
						++p_94360_3_;
						var9 = false;
					} else
					{
						--p_94360_4_;
					}
					var8 = 0;
			}
			return func_94361_a(p_94360_1_, p_94360_2_, p_94360_3_, p_94360_4_, p_94360_6_, p_94360_7_, var8) ? true : var9 && func_94361_a(p_94360_1_, p_94360_2_, p_94360_3_ - 1, p_94360_4_, p_94360_6_, p_94360_7_, var8);
		}
	}
	
	protected boolean func_94361_a(World p_94361_1_, int p_94361_2_, int p_94361_3_, int p_94361_4_, boolean p_94361_5_, int p_94361_6_, int p_94361_7_)
	{
		int var8 = p_94361_1_.getBlockId(p_94361_2_, p_94361_3_, p_94361_4_);
		if(var8 == blockID)
		{
			int var9 = p_94361_1_.getBlockMetadata(p_94361_2_, p_94361_3_, p_94361_4_);
			int var10 = var9 & 7;
			if(p_94361_7_ == 1 && (var10 == 0 || var10 == 4 || var10 == 5)) return false;
			if(p_94361_7_ == 0 && (var10 == 1 || var10 == 2 || var10 == 3)) return false;
			if((var9 & 8) != 0)
			{
				if(p_94361_1_.isBlockIndirectlyGettingPowered(p_94361_2_, p_94361_3_, p_94361_4_)) return true;
				return func_94360_a(p_94361_1_, p_94361_2_, p_94361_3_, p_94361_4_, var9, p_94361_5_, p_94361_6_ + 1);
			}
		}
		return false;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return (par2 & 8) == 0 ? blockIcon : theIcon;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		super.registerIcons(par1IconRegister);
		theIcon = par1IconRegister.registerIcon(getUnlocalizedName2() + "_powered");
	}
}
