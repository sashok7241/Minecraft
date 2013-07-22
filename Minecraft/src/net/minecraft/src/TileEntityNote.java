package net.minecraft.src;

public class TileEntityNote extends TileEntity
{
	public byte note;
	public boolean previousRedstoneState;
	
	public void changePitch()
	{
		note = (byte) ((note + 1) % 25);
		onInventoryChanged();
	}
	
	@Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		note = par1NBTTagCompound.getByte("note");
		if(note < 0)
		{
			note = 0;
		}
		if(note > 24)
		{
			note = 24;
		}
	}
	
	public void triggerNote(World par1World, int par2, int par3, int par4)
	{
		if(par1World.getBlockMaterial(par2, par3 + 1, par4) == Material.air)
		{
			Material var5 = par1World.getBlockMaterial(par2, par3 - 1, par4);
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
			par1World.addBlockEvent(par2, par3, par4, Block.music.blockID, var6, note);
		}
	}
	
	@Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setByte("note", note);
	}
}
