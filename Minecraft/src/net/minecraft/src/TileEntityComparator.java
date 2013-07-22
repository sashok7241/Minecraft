package net.minecraft.src;

public class TileEntityComparator extends TileEntity
{
	private int outputSignal = 0;
	
	public int getOutputSignal()
	{
		return outputSignal;
	}
	
	@Override public void readFromNBT(NBTTagCompound p_70307_1_)
	{
		super.readFromNBT(p_70307_1_);
		outputSignal = p_70307_1_.getInteger("OutputSignal");
	}
	
	public void setOutputSignal(int p_96099_1_)
	{
		outputSignal = p_96099_1_;
	}
	
	@Override public void writeToNBT(NBTTagCompound p_70310_1_)
	{
		super.writeToNBT(p_70310_1_);
		p_70310_1_.setInteger("OutputSignal", outputSignal);
	}
}
