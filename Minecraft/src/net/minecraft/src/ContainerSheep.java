package net.minecraft.src;

class ContainerSheep extends Container
{
	final EntitySheep field_90034_a;
	
	ContainerSheep(EntitySheep p_i7007_1_)
	{
		field_90034_a = p_i7007_1_;
	}
	
	@Override public boolean canInteractWith(EntityPlayer p_75145_1_)
	{
		return false;
	}
}
