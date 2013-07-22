package net.minecraft.src;

public class TileEntityRecordPlayer extends TileEntity
{
	private ItemStack record;
	
	public ItemStack func_96097_a()
	{
		return record;
	}
	
	public void func_96098_a(ItemStack p_96098_1_)
	{
		record = p_96098_1_;
		onInventoryChanged();
	}
	
	@Override public void readFromNBT(NBTTagCompound p_70307_1_)
	{
		super.readFromNBT(p_70307_1_);
		if(p_70307_1_.hasKey("RecordItem"))
		{
			func_96098_a(ItemStack.loadItemStackFromNBT(p_70307_1_.getCompoundTag("RecordItem")));
		} else if(p_70307_1_.getInteger("Record") > 0)
		{
			func_96098_a(new ItemStack(p_70307_1_.getInteger("Record"), 1, 0));
		}
	}
	
	@Override public void writeToNBT(NBTTagCompound p_70310_1_)
	{
		super.writeToNBT(p_70310_1_);
		if(func_96097_a() != null)
		{
			p_70310_1_.setCompoundTag("RecordItem", func_96097_a().writeToNBT(new NBTTagCompound()));
			p_70310_1_.setInteger("Record", func_96097_a().itemID);
		}
	}
}
