package net.minecraft.src;

class SlotCreativeInventory extends Slot
{
	private final Slot theSlot;
	final GuiContainerCreative theCreativeInventory;
	
	public SlotCreativeInventory(GuiContainerCreative par1GuiContainerCreative, Slot par2Slot, int par3)
	{
		super(par2Slot.inventory, par3, 0, 0);
		theCreativeInventory = par1GuiContainerCreative;
		theSlot = par2Slot;
	}
	
	@Override public ItemStack decrStackSize(int par1)
	{
		return theSlot.decrStackSize(par1);
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
	
	@Override public boolean isItemValid(ItemStack par1ItemStack)
	{
		return theSlot.isItemValid(par1ItemStack);
	}
	
	@Override public boolean isSlotInInventory(IInventory par1IInventory, int par2)
	{
		return theSlot.isSlotInInventory(par1IInventory, par2);
	}
	
	@Override public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
	{
		theSlot.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
	}
	
	@Override public void onSlotChanged()
	{
		theSlot.onSlotChanged();
	}
	
	@Override public void putStack(ItemStack par1ItemStack)
	{
		theSlot.putStack(par1ItemStack);
	}
	
	static Slot func_75240_a(SlotCreativeInventory par0SlotCreativeInventory)
	{
		return par0SlotCreativeInventory.theSlot;
	}
}
