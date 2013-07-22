package net.minecraft.src;

import java.util.Iterator;

public class MapInfo
{
	public final EntityPlayer entityplayerObj;
	public int[] field_76209_b;
	public int[] field_76210_c;
	private int currentRandomNumber;
	private int ticksUntilPlayerLocationMapUpdate;
	private byte[] lastPlayerLocationOnMap;
	public int field_82569_d;
	private boolean field_82570_i;
	final MapData mapDataObj;
	
	public MapInfo(MapData p_i3904_1_, EntityPlayer p_i3904_2_)
	{
		mapDataObj = p_i3904_1_;
		field_76209_b = new int[128];
		field_76210_c = new int[128];
		currentRandomNumber = 0;
		ticksUntilPlayerLocationMapUpdate = 0;
		field_82570_i = false;
		entityplayerObj = p_i3904_2_;
		for(int var3 = 0; var3 < field_76209_b.length; ++var3)
		{
			field_76209_b[var3] = 0;
			field_76210_c[var3] = 127;
		}
	}
	
	public byte[] getPlayersOnMap(ItemStack p_76204_1_)
	{
		byte[] var2;
		if(!field_82570_i)
		{
			var2 = new byte[] { (byte) 2, mapDataObj.scale };
			field_82570_i = true;
			return var2;
		} else
		{
			int var3;
			int var10;
			if(--ticksUntilPlayerLocationMapUpdate < 0)
			{
				ticksUntilPlayerLocationMapUpdate = 4;
				var2 = new byte[mapDataObj.playersVisibleOnMap.size() * 3 + 1];
				var2[0] = 1;
				var3 = 0;
				for(Iterator var4 = mapDataObj.playersVisibleOnMap.values().iterator(); var4.hasNext(); ++var3)
				{
					MapCoord var5 = (MapCoord) var4.next();
					var2[var3 * 3 + 1] = (byte) (var5.iconSize << 4 | var5.iconRotation & 15);
					var2[var3 * 3 + 2] = var5.centerX;
					var2[var3 * 3 + 3] = var5.centerZ;
				}
				boolean var9 = !p_76204_1_.isOnItemFrame();
				if(lastPlayerLocationOnMap != null && lastPlayerLocationOnMap.length == var2.length)
				{
					for(var10 = 0; var10 < var2.length; ++var10)
					{
						if(var2[var10] != lastPlayerLocationOnMap[var10])
						{
							var9 = false;
							break;
						}
					}
				} else
				{
					var9 = false;
				}
				if(!var9)
				{
					lastPlayerLocationOnMap = var2;
					return var2;
				}
			}
			for(int var8 = 0; var8 < 1; ++var8)
			{
				var3 = currentRandomNumber++ * 11 % 128;
				if(field_76209_b[var3] >= 0)
				{
					int var11 = field_76210_c[var3] - field_76209_b[var3] + 1;
					var10 = field_76209_b[var3];
					byte[] var6 = new byte[var11 + 3];
					var6[0] = 0;
					var6[1] = (byte) var3;
					var6[2] = (byte) var10;
					for(int var7 = 0; var7 < var6.length - 3; ++var7)
					{
						var6[var7 + 3] = mapDataObj.colors[(var7 + var10) * 128 + var3];
					}
					field_76210_c[var3] = -1;
					field_76209_b[var3] = -1;
					return var6;
				}
			}
			return null;
		}
	}
}
