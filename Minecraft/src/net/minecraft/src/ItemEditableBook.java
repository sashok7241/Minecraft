package net.minecraft.src;

import java.util.List;

public class ItemEditableBook extends Item
{
	public ItemEditableBook(int p_i3698_1_)
	{
		super(p_i3698_1_);
		setMaxStackSize(1);
	}
	
	@Override public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if(par1ItemStack.hasTagCompound())
		{
			NBTTagCompound var5 = par1ItemStack.getTagCompound();
			NBTTagString var6 = (NBTTagString) var5.getTag("author");
			if(var6 != null)
			{
				par3List.add(EnumChatFormatting.GRAY + String.format(StatCollector.translateToLocalFormatted("book.byAuthor", new Object[] { var6.data }), new Object[0]));
			}
		}
	}
	
	@Override public String getItemDisplayName(ItemStack p_77628_1_)
	{
		if(p_77628_1_.hasTagCompound())
		{
			NBTTagCompound var2 = p_77628_1_.getTagCompound();
			NBTTagString var3 = (NBTTagString) var2.getTag("title");
			if(var3 != null) return var3.toString();
		}
		return super.getItemDisplayName(p_77628_1_);
	}
	
	@Override public boolean getShareTag()
	{
		return true;
	}
	
	@Override public boolean hasEffect(ItemStack par1ItemStack)
	{
		return true;
	}
	
	@Override public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		p_77659_3_.displayGUIBook(p_77659_1_);
		return p_77659_1_;
	}
	
	public static boolean validBookTagContents(NBTTagCompound p_77828_0_)
	{
		if(!ItemWritableBook.validBookTagPages(p_77828_0_)) return false;
		else if(!p_77828_0_.hasKey("title")) return false;
		else
		{
			String var1 = p_77828_0_.getString("title");
			return var1 != null && var1.length() <= 16 ? p_77828_0_.hasKey("author") : false;
		}
	}
}
