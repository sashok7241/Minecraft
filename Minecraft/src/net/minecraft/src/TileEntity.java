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
	
	public void func_85027_a(CrashReportCategory p_85027_1_)
	{
		p_85027_1_.addCrashSectionCallable("Name", new CallableTileEntityName(this));
		CrashReportCategory.func_85068_a(p_85027_1_, xCoord, yCoord, zCoord, getBlockType().blockID, getBlockMetadata());
		p_85027_1_.addCrashSectionCallable("Actual block type", new CallableTileEntityID(this));
		p_85027_1_.addCrashSectionCallable("Actual block data value", new CallableTileEntityData(this));
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
	
	public void readFromNBT(NBTTagCompound p_70307_1_)
	{
		xCoord = p_70307_1_.getInteger("x");
		yCoord = p_70307_1_.getInteger("y");
		zCoord = p_70307_1_.getInteger("z");
	}
	
	public boolean receiveClientEvent(int p_70315_1_, int p_70315_2_)
	{
		return false;
	}
	
	public void setWorldObj(World p_70308_1_)
	{
		worldObj = p_70308_1_;
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
	
	public void writeToNBT(NBTTagCompound p_70310_1_)
	{
		String var2 = (String) classToNameMap.get(this.getClass());
		if(var2 == null) throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
		else
		{
			p_70310_1_.setString("id", var2);
			p_70310_1_.setInteger("x", xCoord);
			p_70310_1_.setInteger("y", yCoord);
			p_70310_1_.setInteger("z", zCoord);
		}
	}
	
	private static void addMapping(Class p_70306_0_, String p_70306_1_)
	{
		if(nameToClassMap.containsKey(p_70306_1_)) throw new IllegalArgumentException("Duplicate id: " + p_70306_1_);
		else
		{
			nameToClassMap.put(p_70306_1_, p_70306_0_);
			classToNameMap.put(p_70306_0_, p_70306_1_);
		}
	}
	
	public static TileEntity createAndLoadEntity(NBTTagCompound p_70317_0_)
	{
		TileEntity var1 = null;
		try
		{
			Class var2 = (Class) nameToClassMap.get(p_70317_0_.getString("id"));
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
			var1.readFromNBT(p_70317_0_);
		} else
		{
			MinecraftServer.getServer().getLogAgent().logWarning("Skipping TileEntity with id " + p_70317_0_.getString("id"));
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
