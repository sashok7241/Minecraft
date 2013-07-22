package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockCauldron extends Block
{
	private Icon field_94378_a;
	private Icon cauldronTopIcon;
	private Icon cauldronBottomIcon;
	
	public BlockCauldron(int p_i3927_1_)
	{
		super(p_i3927_1_, Material.iron);
	}
	
	@Override public void addCollisionBoxesToList(World p_71871_1_, int p_71871_2_, int p_71871_3_, int p_71871_4_, AxisAlignedBB p_71871_5_, List p_71871_6_, Entity p_71871_7_)
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
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
		setBlockBoundsForItemRender();
	}
	
	@Override public void fillWithRain(World p_71892_1_, int p_71892_2_, int p_71892_3_, int p_71892_4_)
	{
		if(p_71892_1_.rand.nextInt(20) == 1)
		{
			int var5 = p_71892_1_.getBlockMetadata(p_71892_2_, p_71892_3_, p_71892_4_);
			if(var5 < 3)
			{
				p_71892_1_.setBlockMetadataWithNotify(p_71892_2_, p_71892_3_, p_71892_4_, var5 + 1, 2);
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
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
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
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		if(p_71903_1_.isRemote) return true;
		else
		{
			ItemStack var10 = p_71903_5_.inventory.getCurrentItem();
			if(var10 == null) return true;
			else
			{
				int var11 = p_71903_1_.getBlockMetadata(p_71903_2_, p_71903_3_, p_71903_4_);
				if(var10.itemID == Item.bucketWater.itemID)
				{
					if(var11 < 3)
					{
						if(!p_71903_5_.capabilities.isCreativeMode)
						{
							p_71903_5_.inventory.setInventorySlotContents(p_71903_5_.inventory.currentItem, new ItemStack(Item.bucketEmpty));
						}
						p_71903_1_.setBlockMetadataWithNotify(p_71903_2_, p_71903_3_, p_71903_4_, 3, 2);
					}
					return true;
				} else
				{
					if(var10.itemID == Item.glassBottle.itemID)
					{
						if(var11 > 0)
						{
							ItemStack var12 = new ItemStack(Item.potion, 1, 0);
							if(!p_71903_5_.inventory.addItemStackToInventory(var12))
							{
								p_71903_1_.spawnEntityInWorld(new EntityItem(p_71903_1_, p_71903_2_ + 0.5D, p_71903_3_ + 1.5D, p_71903_4_ + 0.5D, var12));
							} else if(p_71903_5_ instanceof EntityPlayerMP)
							{
								((EntityPlayerMP) p_71903_5_).sendContainerToPlayer(p_71903_5_.inventoryContainer);
							}
							--var10.stackSize;
							if(var10.stackSize <= 0)
							{
								p_71903_5_.inventory.setInventorySlotContents(p_71903_5_.inventory.currentItem, (ItemStack) null);
							}
							p_71903_1_.setBlockMetadataWithNotify(p_71903_2_, p_71903_3_, p_71903_4_, var11 - 1, 2);
						}
					} else if(var11 > 0 && var10.getItem() instanceof ItemArmor && ((ItemArmor) var10.getItem()).getArmorMaterial() == EnumArmorMaterial.CLOTH)
					{
						ItemArmor var13 = (ItemArmor) var10.getItem();
						var13.removeColor(var10);
						p_71903_1_.setBlockMetadataWithNotify(p_71903_2_, p_71903_3_, p_71903_4_, var11 - 1, 2);
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
