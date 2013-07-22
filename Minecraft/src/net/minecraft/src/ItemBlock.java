package net.minecraft.src;

import java.util.List;

public class ItemBlock extends Item
{
	private int blockID;
	private Icon field_94588_b;
	
	public ItemBlock(int par1)
	{
		super(par1);
		blockID = par1 + 256;
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
	
	@Override public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return Block.blocksList[blockID].getUnlocalizedName();
	}
	
	@Override public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		int var11 = par3World.getBlockId(par4, par5, par6);
		if(var11 == Block.snow.blockID && (par3World.getBlockMetadata(par4, par5, par6) & 7) < 1)
		{
			par7 = 1;
		} else if(var11 != Block.vine.blockID && var11 != Block.tallGrass.blockID && var11 != Block.deadBush.blockID)
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
		}
		if(par1ItemStack.stackSize == 0) return false;
		else if(!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) return false;
		else if(par5 == 255 && Block.blocksList[blockID].blockMaterial.isSolid()) return false;
		else if(par3World.canPlaceEntityOnSide(blockID, par4, par5, par6, false, par7, par2EntityPlayer, par1ItemStack))
		{
			Block var12 = Block.blocksList[blockID];
			int var13 = getMetadata(par1ItemStack.getItemDamage());
			int var14 = Block.blocksList[blockID].onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, var13);
			if(par3World.setBlock(par4, par5, par6, blockID, var14, 3))
			{
				if(par3World.getBlockId(par4, par5, par6) == blockID)
				{
					Block.blocksList[blockID].onBlockPlacedBy(par3World, par4, par5, par6, par2EntityPlayer, par1ItemStack);
					Block.blocksList[blockID].onPostBlockPlaced(par3World, par4, par5, par6, var14);
				}
				par3World.playSoundEffect(par4 + 0.5F, par5 + 0.5F, par6 + 0.5F, var12.stepSound.getPlaceSound(), (var12.stepSound.getVolume() + 1.0F) / 2.0F, var12.stepSound.getPitch() * 0.8F);
				--par1ItemStack.stackSize;
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
