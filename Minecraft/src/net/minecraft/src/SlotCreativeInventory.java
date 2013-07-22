package net.minecraft.src;

class SlotCreativeInventory extends Slot
{
	private final Slot theSlot;
	final GuiContainerCreative theCreativeInventory;
	
	public SlotCreativeInventory(GuiContainerCreative p_i3088_1_, Slot p_i3088_2_, int p_i3088_3_)
	{
		super(p_i3088_2_.inventory, p_i3088_3_, 0, 0);
		theCreativeInventory = p_i3088_1_;
		theSlot = p_i3088_2_;
	}
	
	@Override public ItemStack decrStackSize(int p_75209_1_)
	{
		return theSlot.decrStackSize(p_75209_1_);
	}
	
	@Override public Icon getBackgroundIconIndex()
	{
		return theSlot.getBackgroundIconIndex();
	}
	
	@Override public boolean getHasStack()
	{
		return theSlot.getHasStack();
	}
	
	@Override public int getSlotStackLimit()
	{
		return theSlot.getSlotStackLimit();
	}
	
	@Override public ItemStack getStack()
	{
		return theSlot.getStack();
	}
	
	@Override public boolean isItemValid(ItemStack p_75214_1_)
	{
		return theSlot.isItemValid(p_75214_1_);
	}
	
	@Override public boolean isSlotInInventory(IInventory p_75217_1_, int p_75217_2_)
	{
		return theSlot.isSlotInInventory(p_75217_1_, p_75217_2_);
	}
	
	@Override public void onPickupFromSlot(EntityPlayer p_82870_1_, ItemStack p_82870_2_)
	{
		theSlot.onPickupFromSlot(p_82870_1_, p_82870_2_);
	}
	
	@Override public void onSlotChanged()
	{
		theSlot.onSlotChanged();
	}
	
	@Override public void putStack(ItemStack p_75215_1_)
	{
		theSlot.putStack(p_75215_1_);
	}
	
	static Slot func_75240_a(SlotCreativeInventory par0SlotCreativeInventory)
	{
		return par0SlotCreativeInventory.theSlot;
	}
}
