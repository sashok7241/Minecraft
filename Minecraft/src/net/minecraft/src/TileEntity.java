package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.MinecraftServer;

public class TileEntity
{
	private static Map nameToClassMap = new HashMap();
	private static Map classToNameMap = new HashMap();
	protected World worldObj;
	public int xCoord;
	public int yCoord;
	public int zCoord;
	protected boolean tileEntityInvalid;
	public int blockMetadata = -1;
	public Block blockType;
	
	public void func_85027_a(CrashReportCategory par1CrashReportCategory)
	{
		par1CrashReportCategory.addCrashSectionCallable("Name", new CallableTileEntityName(this));
		CrashReportCategory.func_85068_a(par1CrashReportCategory, xCoord, yCoord, zCoord, getBlockType().blockID, getBlockMetadata());
		par1CrashReportCategory.addCrashSectionCallable("Actual block type", new CallableTileEntityID(this));
		par1CrashReportCategory.addCrashSectionCallable("Actual block data value", new CallableTileEntityData(this));
	}
	
	public int getBlockMetadata()
	{
		if(blockMetadata == -1)
		{
			blockMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		}
		return blockMetadata;
	}
	
	public Block getBlockType()
	{
		if(blockType == null)
		{
			blockType = Block.blocksList[worldObj.getBlockId(xCoord, yCoord, zCoord)];
		}
		return blockType;
	}
	
	public Packet getDescriptionPacket()
	{
		return null;
	}
	
	public double getDistanceFrom(double par1, double par3, double par5)
	{
		double var7 = xCoord + 0.5D - par1;
		double var9 = yCoord + 0.5D - par3;
		double var11 = zCoord + 0.5D - par5;
		return var7 * var7 + var9 * var9 + var11 * var11;
	}
	
	public double getMaxRenderDistanceSquared()
	{
		return 4096.0D;
	}
	
	public World getWorldObj()
	{
		return worldObj;
	}
	
	public boolean hasWorldObj()
	{
		return worldObj != null;
	}
	
	public void invalidate()
	{
		tileEntityInvalid = true;
	}
	
	public boolean isInvalid()
	{
		return tileEntityInvalid;
	}
	
	public void onInventoryChanged()
	{
		if(worldObj != null)
		{
			blockMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			worldObj.updateTileEntityChunkAndDoNothing(xCoord, yCoord, zCoord, this);
			if(getBlockType() != null)
			{
				worldObj.func_96440_m(xCoord, yCoord, zCoord, getBlockType().blockID);
			}
		}
	}
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		xCoord = par1NBTTagCompound.getInteger("x");
		yCoord = par1NBTTagCompound.getInteger("y");
		zCoord = par1NBTTagCompound.getInteger("z");
	}
	
	public boolean receiveClientEvent(int par1, int par2)
	{
		return false;
	}
	
	public void setWorldObj(World par1World)
	{
		worldObj = par1World;
	}
	
	public void updateContainingBlockInfo()
	{
		blockType = null;
		blockMetadata = -1;
	}
	
	public void updateEntity()
	{
	}
	
	public void validate()
	{
		tileEntityInvalid = false;
	}
	
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		String var2 = (String) classToNameMap.get(this.getClass());
		if(var2 == null) throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
		else
		{
			par1NBTTagCompound.setString("id", var2);
			par1NBTTagCompound.setInteger("x", xCoord);
			par1NBTTagCompound.setInteger("y", yCoord);
			par1NBTTagCompound.setInteger("z", zCoord);
		}
	}
	
	private static void addMapping(Class par0Class, String par1Str)
	{
		if(nameToClassMap.containsKey(par1Str)) throw new IllegalArgumentException("Duplicate id: " + par1Str);
		else
		{
			nameToClassMap.put(par1Str, par0Class);
			classToNameMap.put(par0Class, par1Str);
		}
	}
	
	public static TileEntity createAndLoadEntity(NBTTagCompound par0NBTTagCompound)
	{
		TileEntity var1 = null;
		try
		{
			Class var2 = (Class) nameToClassMap.get(par0NBTTagCompound.getString("id"));
			if(var2 != null)
			{
				var1 = (TileEntity) var2.newInstance();
			}
		} catch(Exception var3)
		{
			var3.printStackTrace();
		}
		if(var1 != null)
		{
			var1.readFromNBT(par0NBTTagCompound);
		} else
		{
			MinecraftServer.getServer().getLogAgent().logWarning("Skipping TileEntity with id " + par0NBTTagCompound.getString("id"));
		}
		return var1;
	}
	
	static Map getClassToNameMap()
	{
		return classToNameMap;
	}
	
	static
	{
		addMapping(TileEntityFurnace.class, "Furnace");
		addMapping(TileEntityChest.class, "Chest");
		addMapping(TileEntityEnderChest.class, "EnderChest");
		addMapping(TileEntityRecordPlayer.class, "RecordPlayer");
		addMapping(TileEntityDispenser.class, "Trap");
		addMapping(TileEntityDropper.class, "Dropper");
		addMapping(TileEntitySign.class, "Sign");
		addMapping(TileEntityMobSpawner.class, "MobSpawner");
		addMapping(TileEntityNote.class, "Music");
		addMapping(TileEntityPiston.class, "Piston");
		addMapping(TileEntityBrewingStand.class, "Cauldron");
		addMapping(TileEntityEnchantmentTable.class, "EnchantTable");
		addMapping(TileEntityEndPortal.class, "Airportal");
		addMapping(TileEntityCommandBlock.class, "Control");
		addMapping(TileEntityBeacon.class, "Beacon");
		addMapping(TileEntitySkull.class, "Skull");
		addMapping(TileEntityDaylightDetector.class, "DLDetector");
		addMapping(TileEntityHopper.class, "Hopper");
		addMapping(TileEntityComparator.class, "Comparator");
	}
}
