package net.minecraft.src;

public class TileEntityRecordPlayer extends TileEntity
{
	private ItemStack record;
	
	public ItemStack func_96097_a()
	{
		return record;
	}
	
	public void func_96098_a(ItemStack par1ItemStack)
	{
		record = par1ItemStack;
		onInventoryChanged();
	}
	
	@Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		if(par1NBTTagCompound.hasKey("RecordItem"))
		{
			func_96098_a(ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("RecordItem")));
		} else if(par1NBTTagCompound.getInteger("Record") > 0)
		{
			func_96098_a(new ItemStack(par1NBTTagCompound.getInteger("Record"), 1, 0));
		}
	}
	
	@Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		if(func_96097_a() != null)
		{
			par1NBTTagCompound.setCompoundTag("RecordItem", func_96097_a().writeToNBT(new NBTTagCompound()));
			par1NBTTagCompound.setInteger("Record", func_96097_a().itemID);
		}
	}
}
