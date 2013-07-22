package net.minecraft.src;

public class NibbleArray
{
	public final byte[] data;
	private final int depthBits;
	private final int depthBitsPlusFour;
	
	public NibbleArray(byte[] p_i3769_1_, int p_i3769_2_)
	{
		data = p_i3769_1_;
		depthBits = p_i3769_2_;
		depthBitsPlusFour = p_i3769_2_ + 4;
	}
	
	public NibbleArray(int p_i3768_1_, int p_i3768_2_)
	{
		data = new byte[p_i3768_1_ >> 1];
		depthBits = p_i3768_2_;
		depthBitsPlusFour = p_i3768_2_ + 4;
	}
	
	public int get(int p_76582_1_, int p_76582_2_, int p_76582_3_)
	{
		int var4 = p_76582_2_ << depthBitsPlusFour | p_76582_3_ << depthBits | p_76582_1_;
		int var5 = var4 >> 1;
		int var6 = var4 & 1;
		return var6 == 0 ? data[var5] & 15 : data[var5] >> 4 & 15;
	}
	
	public void set(int p_76581_1_, int p_76581_2_, int p_76581_3_, int p_76581_4_)
	{
		int var5 = p_76581_2_ << depthBitsPlusFour | p_76581_3_ << depthBits | p_76581_1_;
		int var6 = var5 >> 1;
		int var7 = var5 & 1;
		if(var7 == 0)
		{
			data[var6] = (byte) (data[var6] & 240 | p_76581_4_ & 15);
		} else
		{
			data[var6] = (byte) (data[var6] & 15 | (p_76581_4_ & 15) << 4);
		}
	}
}
