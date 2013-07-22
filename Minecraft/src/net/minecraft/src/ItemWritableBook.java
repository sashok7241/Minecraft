package net.minecraft.src;

public class ItemWritableBook extends Item
{
	public ItemWritableBook(int p_i3697_1_)
	{
		super(p_i3697_1_);
		setMaxStackSize(1);
	}
	
	@Override public boolean getShareTag()
	{
		return true;
	}
	
	@Override public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		p_77659_3_.displayGUIBook(p_77659_1_);
		return p_77659_1_;
	}
	
	public static boolean validBookTagPages(NBTTagCompound p_77829_0_)
	{
		if(p_77829_0_ == null) return false;
		else if(!p_77829_0_.hasKey("pages")) return false;
		else
		{
			NBTTagList var1 = (NBTTagList) p_77829_0_.getTag("pages");
			for(int var2 = 0; var2 < var1.tagCount(); ++var2)
			{
				NBTTagString var3 = (NBTTagString) var1.tagAt(var2);
				if(var3.data == null) return false;
				if(var3.data.length() > 256) return false;
			}
			return true;
		}
	}
}
