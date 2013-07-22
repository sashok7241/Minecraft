package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapData extends WorldSavedData
{
	public int xCenter;
	public int zCenter;
	public byte dimension;
	public byte scale;
	public byte[] colors = new byte[16384];
	public List playersArrayList = new ArrayList();
	private Map playersHashMap = new HashMap();
	public Map playersVisibleOnMap = new LinkedHashMap();
	
	public MapData(String p_i3906_1_)
	{
		super(p_i3906_1_);
	}
	
	private void func_82567_a(int p_82567_1_, World p_82567_2_, String p_82567_3_, double p_82567_4_, double p_82567_6_, double p_82567_8_)
	{
		int var10 = 1 << scale;
		float var11 = (float) (p_82567_4_ - xCenter) / var10;
		float var12 = (float) (p_82567_6_ - zCenter) / var10;
		byte var13 = (byte) (int) (var11 * 2.0F + 0.5D);
		byte var14 = (byte) (int) (var12 * 2.0F + 0.5D);
		byte var16 = 63;
		byte var15;
		if(var11 >= -var16 && var12 >= -var16 && var11 <= var16 && var12 <= var16)
		{
			p_82567_8_ += p_82567_8_ < 0.0D ? -8.0D : 8.0D;
			var15 = (byte) (int) (p_82567_8_ * 16.0D / 360.0D);
			if(dimension < 0)
			{
				int var17 = (int) (p_82567_2_.getWorldInfo().getWorldTime() / 10L);
				var15 = (byte) (var17 * var17 * 34187121 + var17 * 121 >> 15 & 15);
			}
		} else
		{
			if(Math.abs(var11) >= 320.0F || Math.abs(var12) >= 320.0F)
			{
				playersVisibleOnMap.remove(p_82567_3_);
				return;
			}
			p_82567_1_ = 6;
			var15 = 0;
			if(var11 <= -var16)
			{
				var13 = (byte) (int) (var16 * 2 + 2.5D);
			}
			if(var12 <= -var16)
			{
				var14 = (byte) (int) (var16 * 2 + 2.5D);
			}
			if(var11 >= var16)
			{
				var13 = (byte) (var16 * 2 + 1);
			}
			if(var12 >= var16)
			{
				var14 = (byte) (var16 * 2 + 1);
			}
		}
		playersVisibleOnMap.put(p_82567_3_, new MapCoord(this, (byte) p_82567_1_, var13, var14, var15));
	}
	
	public MapInfo func_82568_a(EntityPlayer p_82568_1_)
	{
		MapInfo var2 = (MapInfo) playersHashMap.get(p_82568_1_);
		if(var2 == null)
		{
			var2 = new MapInfo(this, p_82568_1_);
			playersHashMap.put(p_82568_1_, var2);
			playersArrayList.add(var2);
		}
		return var2;
	}
	
	public byte[] getUpdatePacketData(ItemStack p_76193_1_, World p_76193_2_, EntityPlayer p_76193_3_)
	{
		MapInfo var4 = (MapInfo) playersHashMap.get(p_76193_3_);
		return var4 == null ? null : var4.getPlayersOnMap(p_76193_1_);
	}
	
	@Override public void readFromNBT(NBTTagCompound p_76184_1_)
	{
		dimension = p_76184_1_.getByte("dimension");
		xCenter = p_76184_1_.getInteger("xCenter");
		zCenter = p_76184_1_.getInteger("zCenter");
		scale = p_76184_1_.getByte("scale");
		if(scale < 0)
		{
			scale = 0;
		}
		if(scale > 4)
		{
			scale = 4;
		}
		short var2 = p_76184_1_.getShort("width");
		short var3 = p_76184_1_.getShort("height");
		if(var2 == 128 && var3 == 128)
		{
			colors = p_76184_1_.getByteArray("colors");
		} else
		{
			byte[] var4 = p_76184_1_.getByteArray("colors");
			colors = new byte[16384];
			int var5 = (128 - var2) / 2;
			int var6 = (128 - var3) / 2;
			for(int var7 = 0; var7 < var3; ++var7)
			{
				int var8 = var7 + var6;
				if(var8 >= 0 || var8 < 128)
				{
					for(int var9 = 0; var9 < var2; ++var9)
					{
						int var10 = var9 + var5;
						if(var10 >= 0 || var10 < 128)
						{
							colors[var10 + var8 * 128] = var4[var9 + var7 * var2];
						}
					}
				}
			}
		}
	}
	
	public void setColumnDirty(int p_76194_1_, int p_76194_2_, int p_76194_3_)
	{
		super.markDirty();
		for(int var4 = 0; var4 < playersArrayList.size(); ++var4)
		{
			MapInfo var5 = (MapInfo) playersArrayList.get(var4);
			if(var5.field_76209_b[p_76194_1_] < 0 || var5.field_76209_b[p_76194_1_] > p_76194_2_)
			{
				var5.field_76209_b[p_76194_1_] = p_76194_2_;
			}
			if(var5.field_76210_c[p_76194_1_] < 0 || var5.field_76210_c[p_76194_1_] < p_76194_3_)
			{
				var5.field_76210_c[p_76194_1_] = p_76194_3_;
			}
		}
	}
	
	public void updateMPMapData(byte[] par1ArrayOfByte)
	{
		int var2;
		if(par1ArrayOfByte[0] == 0)
		{
			var2 = par1ArrayOfByte[1] & 255;
			int var3 = par1ArrayOfByte[2] & 255;
			for(int var4 = 0; var4 < par1ArrayOfByte.length - 3; ++var4)
			{
				colors[(var4 + var3) * 128 + var2] = par1ArrayOfByte[var4 + 3];
			}
			markDirty();
		} else if(par1ArrayOfByte[0] == 1)
		{
			playersVisibleOnMap.clear();
			for(var2 = 0; var2 < (par1ArrayOfByte.length - 1) / 3; ++var2)
			{
				byte var7 = (byte) (par1ArrayOfByte[var2 * 3 + 1] >> 4);
				byte var8 = par1ArrayOfByte[var2 * 3 + 2];
				byte var5 = par1ArrayOfByte[var2 * 3 + 3];
				byte var6 = (byte) (par1ArrayOfByte[var2 * 3 + 1] & 15);
				playersVisibleOnMap.put("icon-" + var2, new MapCoord(this, var7, var8, var5, var6));
			}
		} else if(par1ArrayOfByte[0] == 2)
		{
			scale = par1ArrayOfByte[1];
		}
	}
	
	public void updateVisiblePlayers(EntityPlayer p_76191_1_, ItemStack p_76191_2_)
	{
		if(!playersHashMap.containsKey(p_76191_1_))
		{
			MapInfo var3 = new MapInfo(this, p_76191_1_);
			playersHashMap.put(p_76191_1_, var3);
			playersArrayList.add(var3);
		}
		if(!p_76191_1_.inventory.hasItemStack(p_76191_2_))
		{
			playersVisibleOnMap.remove(p_76191_1_.getCommandSenderName());
		}
		for(int var5 = 0; var5 < playersArrayList.size(); ++var5)
		{
			MapInfo var4 = (MapInfo) playersArrayList.get(var5);
			if(!var4.entityplayerObj.isDead && (var4.entityplayerObj.inventory.hasItemStack(p_76191_2_) || p_76191_2_.isOnItemFrame()))
			{
				if(!p_76191_2_.isOnItemFrame() && var4.entityplayerObj.dimension == dimension)
				{
					func_82567_a(0, var4.entityplayerObj.worldObj, var4.entityplayerObj.getCommandSenderName(), var4.entityplayerObj.posX, var4.entityplayerObj.posZ, var4.entityplayerObj.rotationYaw);
				}
			} else
			{
				playersHashMap.remove(var4.entityplayerObj);
				playersArrayList.remove(var4);
			}
		}
		if(p_76191_2_.isOnItemFrame())
		{
			func_82567_a(1, p_76191_1_.worldObj, "frame-" + p_76191_2_.getItemFrame().entityId, p_76191_2_.getItemFrame().xPosition, p_76191_2_.getItemFrame().zPosition, p_76191_2_.getItemFrame().hangingDirection * 90);
		}
	}
	
	@Override public void writeToNBT(NBTTagCompound p_76187_1_)
	{
		p_76187_1_.setByte("dimension", dimension);
		p_76187_1_.setInteger("xCenter", xCenter);
		p_76187_1_.setInteger("zCenter", zCenter);
		p_76187_1_.setByte("scale", scale);
		p_76187_1_.setShort("width", (short) 128);
		p_76187_1_.setShort("height", (short) 128);
		p_76187_1_.setByteArray("colors", colors);
	}
}
