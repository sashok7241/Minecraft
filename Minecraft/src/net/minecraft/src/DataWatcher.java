package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DataWatcher
{
	private boolean isBlank = true;
	private static final HashMap dataTypes = new HashMap();
	private final Map watchedObjects = new HashMap();
	private boolean objectChanged;
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public void addObject(int p_75682_1_, Object p_75682_2_)
	{
		Integer var3 = (Integer) dataTypes.get(p_75682_2_.getClass());
		if(var3 == null) throw new IllegalArgumentException("Unknown data type: " + p_75682_2_.getClass());
		else if(p_75682_1_ > 31) throw new IllegalArgumentException("Data value id is too big with " + p_75682_1_ + "! (Max is " + 31 + ")");
		else if(watchedObjects.containsKey(Integer.valueOf(p_75682_1_))) throw new IllegalArgumentException("Duplicate id value for " + p_75682_1_ + "!");
		else
		{
			WatchableObject var4 = new WatchableObject(var3.intValue(), p_75682_1_, p_75682_2_);
			lock.writeLock().lock();
			watchedObjects.put(Integer.valueOf(p_75682_1_), var4);
			lock.writeLock().unlock();
			isBlank = false;
		}
	}
	
	public void addObjectByDataType(int p_82709_1_, int p_82709_2_)
	{
		WatchableObject var3 = new WatchableObject(p_82709_2_, p_82709_1_, (Object) null);
		lock.writeLock().lock();
		watchedObjects.put(Integer.valueOf(p_82709_1_), var3);
		lock.writeLock().unlock();
		isBlank = false;
	}
	
	public List getAllWatched()
	{
		ArrayList var1 = null;
		lock.readLock().lock();
		WatchableObject var3;
		for(Iterator var2 = watchedObjects.values().iterator(); var2.hasNext(); var1.add(var3))
		{
			var3 = (WatchableObject) var2.next();
			if(var1 == null)
			{
				var1 = new ArrayList();
			}
		}
		lock.readLock().unlock();
		return var1;
	}
	
	public boolean getIsBlank()
	{
		return isBlank;
	}
	
	public byte getWatchableObjectByte(int p_75683_1_)
	{
		return ((Byte) getWatchedObject(p_75683_1_).getObject()).byteValue();
	}
	
	public int getWatchableObjectInt(int p_75679_1_)
	{
		return ((Integer) getWatchedObject(p_75679_1_).getObject()).intValue();
	}
	
	public ItemStack getWatchableObjectItemStack(int p_82710_1_)
	{
		return (ItemStack) getWatchedObject(p_82710_1_).getObject();
	}
	
	public short getWatchableObjectShort(int p_75693_1_)
	{
		return ((Short) getWatchedObject(p_75693_1_).getObject()).shortValue();
	}
	
	public String getWatchableObjectString(int p_75681_1_)
	{
		return (String) getWatchedObject(p_75681_1_).getObject();
	}
	
	private WatchableObject getWatchedObject(int p_75691_1_)
	{
		lock.readLock().lock();
		WatchableObject var2;
		try
		{
			var2 = (WatchableObject) watchedObjects.get(Integer.valueOf(p_75691_1_));
		} catch(Throwable var6)
		{
			CrashReport var4 = CrashReport.makeCrashReport(var6, "Getting synched entity data");
			CrashReportCategory var5 = var4.makeCategory("Synched entity data");
			var5.addCrashSection("Data ID", Integer.valueOf(p_75691_1_));
			throw new ReportedException(var4);
		}
		lock.readLock().unlock();
		return var2;
	}
	
	public boolean hasChanges()
	{
		return objectChanged;
	}
	
	public void setObjectWatched(int p_82708_1_)
	{
		WatchableObject.setWatchableObjectWatched(getWatchedObject(p_82708_1_), true);
		objectChanged = true;
	}
	
	public List unwatchAndReturnAllWatched()
	{
		ArrayList var1 = null;
		if(objectChanged)
		{
			lock.readLock().lock();
			Iterator var2 = watchedObjects.values().iterator();
			while(var2.hasNext())
			{
				WatchableObject var3 = (WatchableObject) var2.next();
				if(var3.isWatched())
				{
					var3.setWatched(false);
					if(var1 == null)
					{
						var1 = new ArrayList();
					}
					var1.add(var3);
				}
			}
			lock.readLock().unlock();
		}
		objectChanged = false;
		return var1;
	}
	
	public void updateObject(int p_75692_1_, Object p_75692_2_)
	{
		WatchableObject var3 = getWatchedObject(p_75692_1_);
		if(!p_75692_2_.equals(var3.getObject()))
		{
			var3.setObject(p_75692_2_);
			var3.setWatched(true);
			objectChanged = true;
		}
	}
	
	public void updateWatchedObjectsFromList(List par1List)
	{
		lock.writeLock().lock();
		Iterator var2 = par1List.iterator();
		while(var2.hasNext())
		{
			WatchableObject var3 = (WatchableObject) var2.next();
			WatchableObject var4 = (WatchableObject) watchedObjects.get(Integer.valueOf(var3.getDataValueId()));
			if(var4 != null)
			{
				var4.setObject(var3.getObject());
			}
		}
		lock.writeLock().unlock();
	}
	
	public void writeWatchableObjects(DataOutputStream p_75689_1_) throws IOException
	{
		lock.readLock().lock();
		Iterator var2 = watchedObjects.values().iterator();
		while(var2.hasNext())
		{
			WatchableObject var3 = (WatchableObject) var2.next();
			writeWatchableObject(p_75689_1_, var3);
		}
		lock.readLock().unlock();
		p_75689_1_.writeByte(127);
	}
	
	public static List readWatchableObjects(DataInputStream p_75686_0_) throws IOException
	{
		ArrayList var1 = null;
		for(byte var2 = p_75686_0_.readByte(); var2 != 127; var2 = p_75686_0_.readByte())
		{
			if(var1 == null)
			{
				var1 = new ArrayList();
			}
			int var3 = (var2 & 224) >> 5;
			int var4 = var2 & 31;
			WatchableObject var5 = null;
			switch(var3)
			{
				case 0:
					var5 = new WatchableObject(var3, var4, Byte.valueOf(p_75686_0_.readByte()));
					break;
				case 1:
					var5 = new WatchableObject(var3, var4, Short.valueOf(p_75686_0_.readShort()));
					break;
				case 2:
					var5 = new WatchableObject(var3, var4, Integer.valueOf(p_75686_0_.readInt()));
					break;
				case 3:
					var5 = new WatchableObject(var3, var4, Float.valueOf(p_75686_0_.readFloat()));
					break;
				case 4:
					var5 = new WatchableObject(var3, var4, Packet.readString(p_75686_0_, 64));
					break;
				case 5:
					var5 = new WatchableObject(var3, var4, Packet.readItemStack(p_75686_0_));
					break;
				case 6:
					int var6 = p_75686_0_.readInt();
					int var7 = p_75686_0_.readInt();
					int var8 = p_75686_0_.readInt();
					var5 = new WatchableObject(var3, var4, new ChunkCoordinates(var6, var7, var8));
			}
			var1.add(var5);
		}
		return var1;
	}
	
	public static void writeObjectsInListToStream(List p_75680_0_, DataOutputStream p_75680_1_) throws IOException
	{
		if(p_75680_0_ != null)
		{
			Iterator var2 = p_75680_0_.iterator();
			while(var2.hasNext())
			{
				WatchableObject var3 = (WatchableObject) var2.next();
				writeWatchableObject(p_75680_1_, var3);
			}
		}
		p_75680_1_.writeByte(127);
	}
	
	private static void writeWatchableObject(DataOutputStream p_75690_0_, WatchableObject p_75690_1_) throws IOException
	{
		int var2 = (p_75690_1_.getObjectType() << 5 | p_75690_1_.getDataValueId() & 31) & 255;
		p_75690_0_.writeByte(var2);
		switch(p_75690_1_.getObjectType())
		{
			case 0:
				p_75690_0_.writeByte(((Byte) p_75690_1_.getObject()).byteValue());
				break;
			case 1:
				p_75690_0_.writeShort(((Short) p_75690_1_.getObject()).shortValue());
				break;
			case 2:
				p_75690_0_.writeInt(((Integer) p_75690_1_.getObject()).intValue());
				break;
			case 3:
				p_75690_0_.writeFloat(((Float) p_75690_1_.getObject()).floatValue());
				break;
			case 4:
				Packet.writeString((String) p_75690_1_.getObject(), p_75690_0_);
				break;
			case 5:
				ItemStack var4 = (ItemStack) p_75690_1_.getObject();
				Packet.writeItemStack(var4, p_75690_0_);
				break;
			case 6:
				ChunkCoordinates var3 = (ChunkCoordinates) p_75690_1_.getObject();
				p_75690_0_.writeInt(var3.posX);
				p_75690_0_.writeInt(var3.posY);
				p_75690_0_.writeInt(var3.posZ);
		}
	}
	
	static
	{
		dataTypes.put(Byte.class, Integer.valueOf(0));
		dataTypes.put(Short.class, Integer.valueOf(1));
		dataTypes.put(Integer.class, Integer.valueOf(2));
		dataTypes.put(Float.class, Integer.valueOf(3));
		dataTypes.put(String.class, Integer.valueOf(4));
		dataTypes.put(ItemStack.class, Integer.valueOf(5));
		dataTypes.put(ChunkCoordinates.class, Integer.valueOf(6));
	}
}
