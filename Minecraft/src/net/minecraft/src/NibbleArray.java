package net.minecraft.src;

public class NibbleArray
{
	public final byte[] data;
	private final int depthBits;
	private final int depthBitsPlusFour;
	
	public NibbleArray(byte[] par1ArrayOfByte, int par2)
	{
		data = par1ArrayOfByte;
		depthBits = par2;
		depthBitsPlusFour = par2 + 4;
	}
	
	public NibbleArray(int par1, int par2)
	{
		data = new byte[par1 >> 1];
		depthBits = par2;
		depthBitsPlusFour = par2 + 4;
	}
	
	public int get(int par1, int par2, int par3)
	{
		int var4 = par2 << depthBitsPlusFour | par3 << depthBits | par1;
		int var5 = var4 >> 1;
		int var6 = var4 & 1;
		return var6 == 0 ? data[var5] & 15 : data[var5] >> 4 & 15;
	}
	
	public void set(int par1, int par2, int par3, int par4)
	{
		int var5 = par2 << depthBitsPlusFour | par3 << depthBits | par1;
		int var6 = var5 >> 1;
		int var7 = var5 & 1;
		if(var7 == 0)
		{
			data[var6] = (byte) (data[var6] & 240 | par4 & 15);
		} else
		{
			data[var6] = (byte) (data[var6] & 15 | (par4 & 15) << 4);
		}
	}
}
