package net.minecraft.src;

public class MapCoord
{
	public byte iconSize;
	public byte centerX;
	public byte centerZ;
	public byte iconRotation;
	final MapData data;
	
	public MapCoord(MapData p_i3905_1_, byte p_i3905_2_, byte p_i3905_3_, byte p_i3905_4_, byte p_i3905_5_)
	{
		data = p_i3905_1_;
		iconSize = p_i3905_2_;
		centerX = p_i3905_3_;
		centerZ = p_i3905_4_;
		iconRotation = p_i3905_5_;
	}
}
