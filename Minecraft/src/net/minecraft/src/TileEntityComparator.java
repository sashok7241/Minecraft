package net.minecraft.src;

public class TileEntityComparator extends TileEntity
{
	private int outputSignal = 0;
	
	public int getOutputSignal()
	{
		return outputSignal;
	}
	
	@Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		outputSignal = par1NBTTagCompound.getInteger("OutputSignal");
	}
	
	public void setOutputSignal(int par1)
	{
		outputSignal = par1;
	}
	
	@Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("OutputSignal", outputSignal);
	}
}
