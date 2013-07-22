package net.minecraft.src;

public class AnvilConverterData
{
	public long lastUpdated;
	public boolean terrainPopulated;
	public byte[] heightmap;
	public NibbleArrayReader blockLight;
	public NibbleArrayReader skyLight;
	public NibbleArrayReader data;
	public byte[] blocks;
	public NBTTagList entities;
	public NBTTagList tileEntities;
	public NBTTagList tileTicks;
	public final int x;
	public final int z;
	
	public AnvilConverterData(int p_i3775_1_, int p_i3775_2_)
	{
		x = p_i3775_1_;
		z = p_i3775_2_;
	}
}
