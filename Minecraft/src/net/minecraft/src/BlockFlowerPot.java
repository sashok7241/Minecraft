package net.minecraft.src;

import java.util.Random;

public class BlockFlowerPot extends Block
{
	public BlockFlowerPot(int p_i5103_1_)
	{
		super(p_i5103_1_, Material.circuits);
		setBlockBoundsForItemRender();
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return super.canPlaceBlockAt(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_) && p_71930_1_.doesBlockHaveSolidTopSurface(p_71930_2_, p_71930_3_ - 1, p_71930_4_);
	}
	
	@Override public void dropBlockAsItemWithChance(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
	{
		super.dropBlockAsItemWithChance(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, p_71914_5_, p_71914_6_, p_71914_7_);
		if(p_71914_5_ > 0)
		{
			ItemStack var8 = getPlantForMeta(p_71914_5_);
			if(var8 != null)
			{
				dropBlockAsItem_do(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, var8);
			}
		}
	}
	
	@Override public int getDamageValue(World p_71873_1_, int p_71873_2_, int p_71873_3_, int p_71873_4_)
	{
		ItemStack var5 = getPlantForMeta(p_71873_1_.getBlockMetadata(p_71873_2_, p_71873_3_, p_71873_4_));
		return var5 == null ? Item.flowerPot.itemID : var5.getItemDamage();
	}
	
	@Override public int getRenderType()
	{
		return 33;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Item.flowerPot.itemID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		ItemStack var5 = getPlantForMeta(par1World.getBlockMetadata(par2, par3, par4));
		return var5 == null ? Item.flowerPot.itemID : var5.itemID;
	}
	
	@Override public boolean isFlowerPot()
	{
		return true;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		ItemStack var10 = p_71903_5_.inventory.getCurrentItem();
		if(var10 == null) return false;
		else if(p_71903_1_.getBlockMetadata(p_71903_2_, p_71903_3_, p_71903_4_) != 0) return false;
		else
		{
			int var11 = getMetaForPlant(var10);
			if(var11 > 0)
			{
				p_71903_1_.setBlockMetadataWithNotify(p_71903_2_, p_71903_3_, p_71903_4_, var11, 2);
				if(!p_71903_5_.capabilities.isCreativeMode && --var10.stackSize <= 0)
				{
					p_71903_5_.inventory.setInventorySlotContents(p_71903_5_.inventory.currentItem, (ItemStack) null);
				}
				return true;
			} else return false;
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		if(!p_71863_1_.doesBlockHaveSolidTopSurface(p_71863_2_, p_71863_3_ - 1, p_71863_4_))
		{
			dropBlockAsItem(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_), 0);
			p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
		}
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		float var1 = 0.375F;
		float var2 = var1 / 2.0F;
		setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var1, 0.5F + var2);
	}
	
	public static int getMetaForPlant(ItemStack p_82530_0_)
	{
		int var1 = p_82530_0_.getItem().itemID;
		if(var1 == Block.plantRed.blockID) return 1;
		else if(var1 == Block.plantYellow.blockID) return 2;
		else if(var1 == Block.cactus.blockID) return 9;
		else if(var1 == Block.mushroomBrown.blockID) return 8;
		else if(var1 == Block.mushroomRed.blockID) return 7;
		else if(var1 == Block.deadBush.blockID) return 10;
		else
		{
			if(var1 == Block.sapling.blockID)
			{
				switch(p_82530_0_.getItemDamage())
				{
					case 0:
						return 3;
					case 1:
						return 4;
					case 2:
						return 5;
					case 3:
						return 6;
				}
			}
			if(var1 == Block.tallGrass.blockID)
			{
				switch(p_82530_0_.getItemDamage())
				{
					case 2:
						return 11;
				}
			}
			return 0;
		}
	}
	
	public static ItemStack getPlantForMeta(int p_82531_0_)
	{
		switch(p_82531_0_)
		{
			case 1:
				return new ItemStack(Block.plantRed);
			case 2:
				return new ItemStack(Block.plantYellow);
			case 3:
				return new ItemStack(Block.sapling, 1, 0);
			case 4:
				return new ItemStack(Block.sapling, 1, 1);
			case 5:
				return new ItemStack(Block.sapling, 1, 2);
			case 6:
				return new ItemStack(Block.sapling, 1, 3);
			case 7:
				return new ItemStack(Block.mushroomRed);
			case 8:
				return new ItemStack(Block.mushroomBrown);
			case 9:
				return new ItemStack(Block.cactus);
			case 10:
				return new ItemStack(Block.deadBush);
			case 11:
				return new ItemStack(Block.tallGrass, 1, 2);
			default:
				return null;
		}
	}
}
