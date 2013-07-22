package net.minecraft.src;

public class NibbleArrayReader
{
	public final byte[] data;
	private final int depthBits;
	private final int depthBitsPlusFour;
	
	public NibbleArrayReader(byte[] p_i3774_1_, int p_i3774_2_)
	{
		data = p_i3774_1_;
		depthBits = p_i3774_2_;
		depthBitsPlusFour = p_i3774_2_ + 4;
	}
	
	public int get(int p_76686_1_, int p_76686_2_, int p_76686_3_)
	{
		int var4 = p_76686_1_ << depthBitsPlusFour | p_76686_3_ << depthBits | p_76686_2_;
		int var5 = var4 >> 1;
		int var6 = var4 & 1;
		return var6 == 0 ? data[var5] & 15 : data[var5] >> 4 & 15;
	}
}
