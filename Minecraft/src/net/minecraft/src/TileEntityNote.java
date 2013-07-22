package net.minecraft.src;

public class TileEntityNote extends TileEntity
{
	public byte note = 0;
	public boolean previousRedstoneState = false;
	
	public void changePitch()
	{
		note = (byte) ((note + 1) % 25);
		onInventoryChanged();
	}
	
	@Override public void readFromNBT(NBTTagCompound p_70307_1_)
	{
		super.readFromNBT(p_70307_1_);
		note = p_70307_1_.getByte("note");
		if(note < 0)
		{
			note = 0;
		}
		if(note > 24)
		{
			note = 24;
		}
	}
	
	public void triggerNote(World p_70414_1_, int p_70414_2_, int p_70414_3_, int p_70414_4_)
	{
		if(p_70414_1_.getBlockMaterial(p_70414_2_, p_70414_3_ + 1, p_70414_4_) == Material.air)
		{
			Material var5 = p_70414_1_.getBlockMaterial(p_70414_2_, p_70414_3_ - 1, p_70414_4_);
			byte var6 = 0;
			if(var5 == Material.rock)
			{
				var6 = 1;
			}
			if(var5 == Material.sand)
			{
				var6 = 2;
			}
			if(var5 == Material.glass)
			{
				var6 = 3;
			}
			if(var5 == Material.wood)
			{
				var6 = 4;
			}
			p_70414_1_.addBlockEvent(p_70414_2_, p_70414_3_, p_70414_4_, Block.music.blockID, var6, note);
		}
	}
	
	@Override public void writeToNBT(NBTTagCompound p_70310_1_)
	{
		super.writeToNBT(p_70310_1_);
		p_70310_1_.setByte("note", note);
	}
}
