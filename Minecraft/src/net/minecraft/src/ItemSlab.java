package net.minecraft.src;

public class ItemSlab extends ItemBlock
{
	private final boolean isFullBlock;
	private final BlockHalfSlab theHalfSlab;
	private final BlockHalfSlab doubleSlab;
	
	public ItemSlab(int p_i3689_1_, BlockHalfSlab p_i3689_2_, BlockHalfSlab p_i3689_3_, boolean p_i3689_4_)
	{
		super(p_i3689_1_);
		theHalfSlab = p_i3689_2_;
		doubleSlab = p_i3689_3_;
		isFullBlock = p_i3689_4_;
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Override public boolean canPlaceItemBlockOnSide(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer, ItemStack par7ItemStack)
	{
		int var8 = par2;
		int var9 = par3;
		int var10 = par4;
		int var11 = par1World.getBlockId(par2, par3, par4);
		int var12 = par1World.getBlockMetadata(par2, par3, par4);
		int var13 = var12 & 7;
		boolean var14 = (var12 & 8) != 0;
		if((par5 == 1 && !var14 || par5 == 0 && var14) && var11 == theHalfSlab.blockID && var13 == par7ItemStack.getItemDamage()) return true;
		else
		{
			if(par5 == 0)
			{
				--par3;
			}
			if(par5 == 1)
			{
				++par3;
			}
			if(par5 == 2)
			{
				--par4;
			}
			if(par5 == 3)
			{
				++par4;
			}
			if(par5 == 4)
			{
				--par2;
			}
			if(par5 == 5)
			{
				++par2;
			}
			var11 = par1World.getBlockId(par2, par3, par4);
			var12 = par1World.getBlockMetadata(par2, par3, par4);
			var13 = var12 & 7;
			var14 = (var12 & 8) != 0;
			return var11 == theHalfSlab.blockID && var13 == par7ItemStack.getItemDamage() ? true : super.canPlaceItemBlockOnSide(par1World, var8, var9, var10, par5, par6EntityPlayer, par7ItemStack);
		}
	}
	
	private boolean func_77888_a(ItemStack p_77888_1_, EntityPlayer p_77888_2_, World p_77888_3_, int p_77888_4_, int p_77888_5_, int p_77888_6_, int p_77888_7_)
	{
		if(p_77888_7_ == 0)
		{
			--p_77888_5_;
		}
		if(p_77888_7_ == 1)
		{
			++p_77888_5_;
		}
		if(p_77888_7_ == 2)
		{
			--p_77888_6_;
		}
		if(p_77888_7_ == 3)
		{
			++p_77888_6_;
		}
		if(p_77888_7_ == 4)
		{
			--p_77888_4_;
		}
		if(p_77888_7_ == 5)
		{
			++p_77888_4_;
		}
		int var8 = p_77888_3_.getBlockId(p_77888_4_, p_77888_5_, p_77888_6_);
		int var9 = p_77888_3_.getBlockMetadata(p_77888_4_, p_77888_5_, p_77888_6_);
		int var10 = var9 & 7;
		if(var8 == theHalfSlab.blockID && var10 == p_77888_1_.getItemDamage())
		{
			if(p_77888_3_.checkNoEntityCollision(doubleSlab.getCollisionBoundingBoxFromPool(p_77888_3_, p_77888_4_, p_77888_5_, p_77888_6_)) && p_77888_3_.setBlock(p_77888_4_, p_77888_5_, p_77888_6_, doubleSlab.blockID, var10, 3))
			{
				p_77888_3_.playSoundEffect(p_77888_4_ + 0.5F, p_77888_5_ + 0.5F, p_77888_6_ + 0.5F, doubleSlab.stepSound.getPlaceSound(), (doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F, doubleSlab.stepSound.getPitch() * 0.8F);
				--p_77888_1_.stackSize;
			}
			return true;
		} else return false;
	}
	
	@Override public Icon getIconFromDamage(int par1)
	{
		return Block.blocksList[itemID].getIcon(2, par1);
	}
	
	@Override public int getMetadata(int p_77647_1_)
	{
		return p_77647_1_;
	}
	
	@Override public String getUnlocalizedName(ItemStack p_77667_1_)
	{
		return theHalfSlab.getFullSlabName(p_77667_1_.getItemDamage());
	}
	
	@Override public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		if(isFullBlock) return super.onItemUse(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
		else if(p_77648_1_.stackSize == 0) return false;
		else if(!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) return false;
		else
		{
			int var11 = p_77648_3_.getBlockId(p_77648_4_, p_77648_5_, p_77648_6_);
			int var12 = p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_);
			int var13 = var12 & 7;
			boolean var14 = (var12 & 8) != 0;
			if((p_77648_7_ == 1 && !var14 || p_77648_7_ == 0 && var14) && var11 == theHalfSlab.blockID && var13 == p_77648_1_.getItemDamage())
			{
				if(p_77648_3_.checkNoEntityCollision(doubleSlab.getCollisionBoundingBoxFromPool(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_)) && p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, doubleSlab.blockID, var13, 3))
				{
					p_77648_3_.playSoundEffect(p_77648_4_ + 0.5F, p_77648_5_ + 0.5F, p_77648_6_ + 0.5F, doubleSlab.stepSound.getPlaceSound(), (doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F, doubleSlab.stepSound.getPitch() * 0.8F);
					--p_77648_1_.stackSize;
				}
				return true;
			} else return func_77888_a(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_) ? true : super.onItemUse(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
		}
	}
}
