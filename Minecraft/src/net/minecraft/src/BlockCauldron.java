package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockCauldron extends Block
{
	private Icon field_94378_a;
	private Icon cauldronTopIcon;
	private Icon cauldronBottomIcon;
	
	public BlockCauldron(int par1)
	{
		super(par1, Material.iron);
	}
	
	@Override public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
		super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		float var8 = 0.125F;
		setBlockBounds(0.0F, 0.0F, 0.0F, var8, 1.0F, 1.0F);
		super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var8);
		super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		setBlockBounds(1.0F - var8, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		setBlockBounds(0.0F, 0.0F, 1.0F - var8, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		setBlockBoundsForItemRender();
	}
	
	@Override public void fillWithRain(World par1World, int par2, int par3, int par4)
	{
		if(par1World.rand.nextInt(20) == 1)
		{
			int var5 = par1World.getBlockMetadata(par2, par3, par4);
			if(var5 < 3)
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, var5 + 1, 2);
			}
		}
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 1 ? cauldronTopIcon : par1 == 0 ? cauldronBottomIcon : blockIcon;
	}
	
	@Override public int getRenderType()
	{
		return 24;
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return Item.cauldron.itemID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Item.cauldron.itemID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if(par1World.isRemote) return true;
		else
		{
			ItemStack var10 = par5EntityPlayer.inventory.getCurrentItem();
			if(var10 == null) return true;
			else
			{
				int var11 = par1World.getBlockMetadata(par2, par3, par4);
				if(var10.itemID == Item.bucketWater.itemID)
				{
					if(var11 < 3)
					{
						if(!par5EntityPlayer.capabilities.isCreativeMode)
						{
							par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(Item.bucketEmpty));
						}
						par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
					}
					return true;
				} else
				{
					if(var10.itemID == Item.glassBottle.itemID)
					{
						if(var11 > 0)
						{
							ItemStack var12 = new ItemStack(Item.potion, 1, 0);
							if(!par5EntityPlayer.inventory.addItemStackToInventory(var12))
							{
								par1World.spawnEntityInWorld(new EntityItem(par1World, par2 + 0.5D, par3 + 1.5D, par4 + 0.5D, var12));
							} else if(par5EntityPlayer instanceof EntityPlayerMP)
							{
								((EntityPlayerMP) par5EntityPlayer).sendContainerToPlayer(par5EntityPlayer.inventoryContainer);
							}
							--var10.stackSize;
							if(var10.stackSize <= 0)
							{
								par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, (ItemStack) null);
							}
							par1World.setBlockMetadataWithNotify(par2, par3, par4, var11 - 1, 2);
						}
					} else if(var11 > 0 && var10.getItem() instanceof ItemArmor && ((ItemArmor) var10.getItem()).getArmorMaterial() == EnumArmorMaterial.CLOTH)
					{
						ItemArmor var13 = (ItemArmor) var10.getItem();
						var13.removeColor(var10);
						par1World.setBlockMetadataWithNotify(par2, par3, par4, var11 - 1, 2);
						return true;
					}
					return true;
				}
			}
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		field_94378_a = par1IconRegister.registerIcon("cauldron_inner");
		cauldronTopIcon = par1IconRegister.registerIcon("cauldron_top");
		cauldronBottomIcon = par1IconRegister.registerIcon("cauldron_bottom");
		blockIcon = par1IconRegister.registerIcon("cauldron_side");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public static Icon func_94375_b(String par0Str)
	{
		return par0Str == "cauldron_inner" ? Block.cauldron.field_94378_a : par0Str == "cauldron_bottom" ? Block.cauldron.cauldronBottomIcon : null;
	}
}
