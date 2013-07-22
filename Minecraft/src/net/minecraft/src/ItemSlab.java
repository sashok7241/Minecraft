package net.minecraft.src;

public class ItemSlab extends ItemBlock
{
	private final boolean isFullBlock;
	private final BlockHalfSlab theHalfSlab;
	private final BlockHalfSlab doubleSlab;
	
	public ItemSlab(int par1, BlockHalfSlab par2BlockHalfSlab, BlockHalfSlab par3BlockHalfSlab, boolean par4)
	{
		super(par1);
		theHalfSlab = par2BlockHalfSlab;
		doubleSlab = par3BlockHalfSlab;
		isFullBlock = par4;
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
	
	private boolean func_77888_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7)
	{
		if(par7 == 0)
		{
			--par5;
		}
		if(par7 == 1)
		{
			++par5;
		}
		if(par7 == 2)
		{
			--par6;
		}
		if(par7 == 3)
		{
			++par6;
		}
		if(par7 == 4)
		{
			--par4;
		}
		if(par7 == 5)
		{
			++par4;
		}
		int var8 = par3World.getBlockId(par4, par5, par6);
		int var9 = par3World.getBlockMetadata(par4, par5, par6);
		int var10 = var9 & 7;
		if(var8 == theHalfSlab.blockID && var10 == par1ItemStack.getItemDamage())
		{
			if(par3World.checkNoEntityCollision(doubleSlab.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6)) && par3World.setBlock(par4, par5, par6, doubleSlab.blockID, var10, 3))
			{
				par3World.playSoundEffect(par4 + 0.5F, par5 + 0.5F, par6 + 0.5F, doubleSlab.stepSound.getPlaceSound(), (doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F, doubleSlab.stepSound.getPitch() * 0.8F);
				--par1ItemStack.stackSize;
			}
			return true;
		} else return false;
	}
	
	@Override public Icon getIconFromDamage(int par1)
	{
		return Block.blocksList[itemID].getIcon(2, par1);
	}
	
	@Override public int getMetadata(int par1)
	{
		return par1;
	}
	
	@Override public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return theHalfSlab.getFullSlabName(par1ItemStack.getItemDamage());
	}
	
	@Override public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if(isFullBlock) return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
		else if(par1ItemStack.stackSize == 0) return false;
		else if(!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) return false;
		else
		{
			int var11 = par3World.getBlockId(par4, par5, par6);
			int var12 = par3World.getBlockMetadata(par4, par5, par6);
			int var13 = var12 & 7;
			boolean var14 = (var12 & 8) != 0;
			if((par7 == 1 && !var14 || par7 == 0 && var14) && var11 == theHalfSlab.blockID && var13 == par1ItemStack.getItemDamage())
			{
				if(par3World.checkNoEntityCollision(doubleSlab.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6)) && par3World.setBlock(par4, par5, par6, doubleSlab.blockID, var13, 3))
				{
					par3World.playSoundEffect(par4 + 0.5F, par5 + 0.5F, par6 + 0.5F, doubleSlab.stepSound.getPlaceSound(), (doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F, doubleSlab.stepSound.getPitch() * 0.8F);
					--par1ItemStack.stackSize;
				}
				return true;
			} else return func_77888_a(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7) ? true : super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
		}
	}
}
