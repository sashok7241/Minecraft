package net.minecraft.src;

public class ItemDoor extends Item
{
	private Material doorMaterial;
	
	public ItemDoor(int p_i3644_1_, Material p_i3644_2_)
	{
		super(p_i3644_1_);
		doorMaterial = p_i3644_2_;
		maxStackSize = 1;
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		if(p_77648_7_ != 1) return false;
		else
		{
			++p_77648_5_;
			Block var11;
			if(doorMaterial == Material.wood)
			{
				var11 = Block.doorWood;
			} else
			{
				var11 = Block.doorIron;
			}
			if(p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_) && p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_ + 1, p_77648_6_, p_77648_7_, p_77648_1_))
			{
				if(!var11.canPlaceBlockAt(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_)) return false;
				else
				{
					int var12 = MathHelper.floor_double((p_77648_2_.rotationYaw + 180.0F) * 4.0F / 360.0F - 0.5D) & 3;
					placeDoorBlock(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, var12, var11);
					--p_77648_1_.stackSize;
					return true;
				}
			} else return false;
		}
	}
	
	public static void placeDoorBlock(World p_77869_0_, int p_77869_1_, int p_77869_2_, int p_77869_3_, int p_77869_4_, Block p_77869_5_)
	{
		byte var6 = 0;
		byte var7 = 0;
		if(p_77869_4_ == 0)
		{
			var7 = 1;
		}
		if(p_77869_4_ == 1)
		{
			var6 = -1;
		}
		if(p_77869_4_ == 2)
		{
			var7 = -1;
		}
		if(p_77869_4_ == 3)
		{
			var6 = 1;
		}
		int var8 = (p_77869_0_.isBlockNormalCube(p_77869_1_ - var6, p_77869_2_, p_77869_3_ - var7) ? 1 : 0) + (p_77869_0_.isBlockNormalCube(p_77869_1_ - var6, p_77869_2_ + 1, p_77869_3_ - var7) ? 1 : 0);
		int var9 = (p_77869_0_.isBlockNormalCube(p_77869_1_ + var6, p_77869_2_, p_77869_3_ + var7) ? 1 : 0) + (p_77869_0_.isBlockNormalCube(p_77869_1_ + var6, p_77869_2_ + 1, p_77869_3_ + var7) ? 1 : 0);
		boolean var10 = p_77869_0_.getBlockId(p_77869_1_ - var6, p_77869_2_, p_77869_3_ - var7) == p_77869_5_.blockID || p_77869_0_.getBlockId(p_77869_1_ - var6, p_77869_2_ + 1, p_77869_3_ - var7) == p_77869_5_.blockID;
		boolean var11 = p_77869_0_.getBlockId(p_77869_1_ + var6, p_77869_2_, p_77869_3_ + var7) == p_77869_5_.blockID || p_77869_0_.getBlockId(p_77869_1_ + var6, p_77869_2_ + 1, p_77869_3_ + var7) == p_77869_5_.blockID;
		boolean var12 = false;
		if(var10 && !var11)
		{
			var12 = true;
		} else if(var9 > var8)
		{
			var12 = true;
		}
		p_77869_0_.setBlock(p_77869_1_, p_77869_2_, p_77869_3_, p_77869_5_.blockID, p_77869_4_, 2);
		p_77869_0_.setBlock(p_77869_1_, p_77869_2_ + 1, p_77869_3_, p_77869_5_.blockID, 8 | (var12 ? 1 : 0), 2);
		p_77869_0_.notifyBlocksOfNeighborChange(p_77869_1_, p_77869_2_, p_77869_3_, p_77869_5_.blockID);
		p_77869_0_.notifyBlocksOfNeighborChange(p_77869_1_, p_77869_2_ + 1, p_77869_3_, p_77869_5_.blockID);
	}
}
