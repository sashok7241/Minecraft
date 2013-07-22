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
	
	public MapData(String par1Str)
	{
		super(par1Str);
	}
	
	private void func_82567_a(int par1, World par2World, String par3Str, double par4, double par6, double par8)
	{
		int var10 = 1 << scale;
		float var11 = (float) (par4 - xCenter) / var10;
		float var12 = (float) (par6 - zCenter) / var10;
		byte var13 = (byte) (int) (var11 * 2.0F + 0.5D);
		byte var14 = (byte) (int) (var12 * 2.0F + 0.5D);
		byte var16 = 63;
		byte var15;
		if(var11 >= -var16 && var12 >= -var16 && var11 <= var16 && var12 <= var16)
		{
			par8 += par8 < 0.0D ? -8.0D : 8.0D;
			var15 = (byte) (int) (par8 * 16.0D / 360.0D);
			if(dimension < 0)
			{
				int var17 = (int) (par2World.getWorldInfo().getWorldTime() / 10L);
				var15 = (byte) (var17 * var17 * 34187121 + var17 * 121 >> 15 & 15);
			}
		} else
		{
			if(Math.abs(var11) >= 320.0F || Math.abs(var12) >= 320.0F)
			{
				playersVisibleOnMap.remove(par3Str);
				return;
			}
			par1 = 6;
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
		playersVisibleOnMap.put(par3Str, new MapCoord(this, (byte) par1, var13, var14, var15));
	}
	
	public MapInfo func_82568_a(EntityPlayer par1EntityPlayer)
	{
		MapInfo var2 = (MapInfo) playersHashMap.get(par1EntityPlayer);
		if(var2 == null)
		{
			var2 = new MapInfo(this, par1EntityPlayer);
			playersHashMap.put(par1EntityPlayer, var2);
			playersArrayList.add(var2);
		}
		return var2;
	}
	
	public byte[] getUpdatePacketData(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		MapInfo var4 = (MapInfo) playersHashMap.get(par3EntityPlayer);
		return var4 == null ? null : var4.getPlayersOnMap(par1ItemStack);
	}
	
	@Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		dimension = par1NBTTagCompound.getByte("dimension");
		xCenter = par1NBTTagCompound.getInteger("xCenter");
		zCenter = par1NBTTagCompound.getInteger("zCenter");
		scale = par1NBTTagCompound.getByte("scale");
		if(scale < 0)
		{
			scale = 0;
		}
		if(scale > 4)
		{
			scale = 4;
		}
		short var2 = par1NBTTagCompound.getShort("width");
		short var3 = par1NBTTagCompound.getShort("height");
		if(var2 == 128 && var3 == 128)
		{
			colors = par1NBTTagCompound.getByteArray("colors");
		} else
		{
			byte[] var4 = par1NBTTagCompound.getByteArray("colors");
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
	
	public void setColumnDirty(int par1, int par2, int par3)
	{
		super.markDirty();
		for(int var4 = 0; var4 < playersArrayList.size(); ++var4)
		{
			MapInfo var5 = (MapInfo) playersArrayList.get(var4);
			if(var5.field_76209_b[par1] < 0 || var5.field_76209_b[par1] > par2)
			{
				var5.field_76209_b[par1] = par2;
			}
			if(var5.field_76210_c[par1] < 0 || var5.field_76210_c[par1] < par3)
			{
				var5.field_76210_c[par1] = par3;
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
	
	public void updateVisiblePlayers(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
	{
		if(!playersHashMap.containsKey(par1EntityPlayer))
		{
			MapInfo var3 = new MapInfo(this, par1EntityPlayer);
			playersHashMap.put(par1EntityPlayer, var3);
			playersArrayList.add(var3);
		}
		if(!par1EntityPlayer.inventory.hasItemStack(par2ItemStack))
		{
			playersVisibleOnMap.remove(par1EntityPlayer.getCommandSenderName());
		}
		for(int var5 = 0; var5 < playersArrayList.size(); ++var5)
		{
			MapInfo var4 = (MapInfo) playersArrayList.get(var5);
			if(!var4.entityplayerObj.isDead && (var4.entityplayerObj.inventory.hasItemStack(par2ItemStack) || par2ItemStack.isOnItemFrame()))
			{
				if(!par2ItemStack.isOnItemFrame() && var4.entityplayerObj.dimension == dimension)
				{
					func_82567_a(0, var4.entityplayerObj.worldObj, var4.entityplayerObj.getCommandSenderName(), var4.entityplayerObj.posX, var4.entityplayerObj.posZ, var4.entityplayerObj.rotationYaw);
				}
			} else
			{
				playersHashMap.remove(var4.entityplayerObj);
				playersArrayList.remove(var4);
			}
		}
		if(par2ItemStack.isOnItemFrame())
		{
			func_82567_a(1, par1EntityPlayer.worldObj, "frame-" + par2ItemStack.getItemFrame().entityId, par2ItemStack.getItemFrame().xPosition, par2ItemStack.getItemFrame().zPosition, par2ItemStack.getItemFrame().hangingDirection * 90);
		}
	}
	
	@Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setByte("dimension", dimension);
		par1NBTTagCompound.setInteger("xCenter", xCenter);
		par1NBTTagCompound.setInteger("zCenter", zCenter);
		par1NBTTagCompound.setByte("scale", scale);
		par1NBTTagCompound.setShort("width", (short) 128);
		par1NBTTagCompound.setShort("height", (short) 128);
		par1NBTTagCompound.setByteArray("colors", colors);
	}
}
