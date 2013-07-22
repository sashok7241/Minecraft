package net.minecraft.src;

import java.util.List;

public class ItemBlock extends Item
{
	private int blockID;
	private Icon field_94588_b;
	
	public ItemBlock(int p_i3690_1_)
	{
		super(p_i3690_1_);
		blockID = p_i3690_1_ + 256;
	}
	
	public boolean canPlaceItemBlockOnSide(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer, ItemStack par7ItemStack)
	{
		int var8 = par1World.getBlockId(par2, par3, par4);
		if(var8 == Block.snow.blockID)
		{
			par5 = 1;
		} else if(var8 != Block.vine.blockID && var8 != Block.tallGrass.blockID && var8 != Block.deadBush.blockID)
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
		}
		return par1World.canPlaceEntityOnSide(getBlockID(), par2, par3, par4, false, par5, (Entity) null, par7ItemStack);
	}
	
	public int getBlockID()
	{
		return blockID;
	}
	
	@Override public CreativeTabs getCreativeTab()
	{
		return Block.blocksList[blockID].getCreativeTabToDisplayOn();
	}
	
	@Override public Icon getIconFromDamage(int par1)
	{
		return field_94588_b != null ? field_94588_b : Block.blocksList[blockID].getBlockTextureFromSide(1);
	}
	
	@Override public int getSpriteNumber()
	{
		return Block.blocksList[blockID].getItemIconName() != null ? 1 : 0;
	}
	
	@Override public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		Block.blocksList[blockID].getSubBlocks(par1, par2CreativeTabs, par3List);
	}
	
	@Override public String getUnlocalizedName()
	{
		return Block.blocksList[blockID].getUnlocalizedName();
	}
	
	@Override public String getUnlocalizedName(ItemStack p_77667_1_)
	{
		return Block.blocksList[blockID].getUnlocalizedName();
	}
	
	@Override public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		int var11 = p_77648_3_.getBlockId(p_77648_4_, p_77648_5_, p_77648_6_);
		if(var11 == Block.snow.blockID && (p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_) & 7) < 1)
		{
			p_77648_7_ = 1;
		} else if(var11 != Block.vine.blockID && var11 != Block.tallGrass.blockID && var11 != Block.deadBush.blockID)
		{
			if(p_77648_7_ == 0)
			{
				--p_77648_5_;
			}
			if(p_77648_7_ == 1)
			{
				++p_77648_5_;
			}
			if(p_77648_7_ == 2)
			{
				--p_77648_6_;
			}
			if(p_77648_7_ == 3)
			{
				++p_77648_6_;
			}
			if(p_77648_7_ == 4)
			{
				--p_77648_4_;
			}
			if(p_77648_7_ == 5)
			{
				++p_77648_4_;
			}
		}
		if(p_77648_1_.stackSize == 0) return false;
		else if(!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) return false;
		else if(p_77648_5_ == 255 && Block.blocksList[blockID].blockMaterial.isSolid()) return false;
		else if(p_77648_3_.canPlaceEntityOnSide(blockID, p_77648_4_, p_77648_5_, p_77648_6_, false, p_77648_7_, p_77648_2_, p_77648_1_))
		{
			Block var12 = Block.blocksList[blockID];
			int var13 = getMetadata(p_77648_1_.getItemDamage());
			int var14 = Block.blocksList[blockID].onBlockPlaced(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, var13);
			if(p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, blockID, var14, 3))
			{
				if(p_77648_3_.getBlockId(p_77648_4_, p_77648_5_, p_77648_6_) == blockID)
				{
					Block.blocksList[blockID].onBlockPlacedBy(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_2_, p_77648_1_);
					Block.blocksList[blockID].onPostBlockPlaced(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, var14);
				}
				p_77648_3_.playSoundEffect(p_77648_4_ + 0.5F, p_77648_5_ + 0.5F, p_77648_6_ + 0.5F, var12.stepSound.getPlaceSound(), (var12.stepSound.getVolume() + 1.0F) / 2.0F, var12.stepSound.getPitch() * 0.8F);
				--p_77648_1_.stackSize;
			}
			return true;
		} else return false;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		String var2 = Block.blocksList[blockID].getItemIconName();
		if(var2 != null)
		{
			field_94588_b = par1IconRegister.registerIcon(var2);
		}
	}
}
