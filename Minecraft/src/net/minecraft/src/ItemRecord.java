package net.minecraft.src;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRecord extends Item
{
	private static final Map records = new HashMap();
	public final String recordName;
	
	protected ItemRecord(int p_i3677_1_, String p_i3677_2_)
	{
		super(p_i3677_1_);
		recordName = p_i3677_2_;
		maxStackSize = 1;
		setCreativeTab(CreativeTabs.tabMisc);
		records.put(p_i3677_2_, this);
	}
	
	@Override public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add(getRecordTitle());
	}
	
	@Override public Icon getIconFromDamage(int par1)
	{
		return itemIcon;
	}
	
	@Override public EnumRarity getRarity(ItemStack par1ItemStack)
	{
		return EnumRarity.rare;
	}
	
	public String getRecordTitle()
	{
		return "C418 - " + recordName;
	}
	
	@Override public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		if(p_77648_3_.getBlockId(p_77648_4_, p_77648_5_, p_77648_6_) == Block.jukebox.blockID && p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_) == 0)
		{
			if(p_77648_3_.isRemote) return true;
			else
			{
				((BlockJukeBox) Block.jukebox).insertRecord(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_1_);
				p_77648_3_.playAuxSFXAtEntity((EntityPlayer) null, 1005, p_77648_4_, p_77648_5_, p_77648_6_, itemID);
				--p_77648_1_.stackSize;
				return true;
			}
		} else return false;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		itemIcon = par1IconRegister.registerIcon("record_" + recordName);
	}
	
	public static ItemRecord getRecord(String par0Str)
	{
		return (ItemRecord) records.get(par0Str);
	}
}
