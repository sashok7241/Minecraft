package net.minecraft.src;

import java.util.List;

public class ItemSkull extends Item
{
	private static final String[] skullTypes = new String[] { "skeleton", "wither", "zombie", "char", "creeper" };
	public static final String[] field_94587_a = new String[] { "skull_skeleton", "skull_wither", "skull_zombie", "skull_char", "skull_creeper" };
	private Icon[] field_94586_c;
	
	public ItemSkull(int p_i5088_1_)
	{
		super(p_i5088_1_);
		setCreativeTab(CreativeTabs.tabDecorations);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Override public Icon getIconFromDamage(int par1)
	{
		if(par1 < 0 || par1 >= skullTypes.length)
		{
			par1 = 0;
		}
		return field_94586_c[par1];
	}
	
	@Override public String getItemDisplayName(ItemStack p_77628_1_)
	{
		return p_77628_1_.getItemDamage() == 3 && p_77628_1_.hasTagCompound() && p_77628_1_.getTagCompound().hasKey("SkullOwner") ? StatCollector.translateToLocalFormatted("item.skull.player.name", new Object[] { p_77628_1_.getTagCompound().getString("SkullOwner") }) : super.getItemDisplayName(p_77628_1_);
	}
	
	@Override public int getMetadata(int p_77647_1_)
	{
		return p_77647_1_;
	}
	
	@Override public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int var4 = 0; var4 < skullTypes.length; ++var4)
		{
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
	
	@Override public String getUnlocalizedName(ItemStack p_77667_1_)
	{
		int var2 = p_77667_1_.getItemDamage();
		if(var2 < 0 || var2 >= skullTypes.length)
		{
			var2 = 0;
		}
		return super.getUnlocalizedName() + "." + skullTypes[var2];
	}
	
	@Override public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		if(p_77648_7_ == 0) return false;
		else if(!p_77648_3_.getBlockMaterial(p_77648_4_, p_77648_5_, p_77648_6_).isSolid()) return false;
		else
		{
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
			if(!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) return false;
			else if(!Block.skull.canPlaceBlockAt(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_)) return false;
			else
			{
				p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, Block.skull.blockID, p_77648_7_, 2);
				int var11 = 0;
				if(p_77648_7_ == 1)
				{
					var11 = MathHelper.floor_double(p_77648_2_.rotationYaw * 16.0F / 360.0F + 0.5D) & 15;
				}
				TileEntity var12 = p_77648_3_.getBlockTileEntity(p_77648_4_, p_77648_5_, p_77648_6_);
				if(var12 != null && var12 instanceof TileEntitySkull)
				{
					String var13 = "";
					if(p_77648_1_.hasTagCompound() && p_77648_1_.getTagCompound().hasKey("SkullOwner"))
					{
						var13 = p_77648_1_.getTagCompound().getString("SkullOwner");
					}
					((TileEntitySkull) var12).setSkullType(p_77648_1_.getItemDamage(), var13);
					((TileEntitySkull) var12).setSkullRotation(var11);
					((BlockSkull) Block.skull).makeWither(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, (TileEntitySkull) var12);
				}
				--p_77648_1_.stackSize;
				return true;
			}
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		field_94586_c = new Icon[field_94587_a.length];
		for(int var2 = 0; var2 < field_94587_a.length; ++var2)
		{
			field_94586_c[var2] = par1IconRegister.registerIcon(field_94587_a[var2]);
		}
	}
}
