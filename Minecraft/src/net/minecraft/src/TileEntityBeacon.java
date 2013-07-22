package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class TileEntityBeacon extends TileEntity implements IInventory
{
	public static final Potion[][] effectsList = new Potion[][] { { Potion.moveSpeed, Potion.digSpeed }, { Potion.resistance, Potion.jump }, { Potion.damageBoost }, { Potion.regeneration } };
	private long field_82137_b;
	private float field_82138_c;
	private boolean isBeaconActive;
	private int levels = -1;
	private int primaryEffect;
	private int secondaryEffect;
	private ItemStack payment;
	private String field_94048_i;
	
	private void addEffectsToPlayers()
	{
		if(isBeaconActive && levels > 0 && !worldObj.isRemote && primaryEffect > 0)
		{
			double var1 = levels * 10 + 10;
			byte var3 = 0;
			if(levels >= 4 && primaryEffect == secondaryEffect)
			{
				var3 = 1;
			}
			AxisAlignedBB var4 = AxisAlignedBB.getAABBPool().getAABB(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(var1, var1, var1);
			var4.maxY = worldObj.getHeight();
			List var5 = worldObj.getEntitiesWithinAABB(EntityPlayer.class, var4);
			Iterator var6 = var5.iterator();
			EntityPlayer var7;
			while(var6.hasNext())
			{
				var7 = (EntityPlayer) var6.next();
				var7.addPotionEffect(new PotionEffect(primaryEffect, 180, var3, true));
			}
			if(levels >= 4 && primaryEffect != secondaryEffect && secondaryEffect > 0)
			{
				var6 = var5.iterator();
				while(var6.hasNext())
				{
					var7 = (EntityPlayer) var6.next();
					var7.addPotionEffect(new PotionEffect(secondaryEffect, 180, 0, true));
				}
			}
		}
	}
	
	@Override public void closeChest()
	{
	}
	
	@Override public ItemStack decrStackSize(int par1, int par2)
	{
		if(par1 == 0 && payment != null)
		{
			if(par2 >= payment.stackSize)
			{
				ItemStack var3 = payment;
				payment = null;
				return var3;
			} else
			{
				payment.stackSize -= par2;
				return new ItemStack(payment.itemID, par2, payment.getItemDamage());
			}
		} else return null;
	}
	
	public float func_82125_v_()
	{
		if(!isBeaconActive) return 0.0F;
		else
		{
			int var1 = (int) (worldObj.getTotalWorldTime() - field_82137_b);
			field_82137_b = worldObj.getTotalWorldTime();
			if(var1 > 1)
			{
				field_82138_c -= var1 / 40.0F;
				if(field_82138_c < 0.0F)
				{
					field_82138_c = 0.0F;
				}
			}
			field_82138_c += 0.025F;
			if(field_82138_c > 1.0F)
			{
				field_82138_c = 1.0F;
			}
			return field_82138_c;
		}
	}
	
	public void func_94047_a(String par1Str)
	{
		field_94048_i = par1Str;
	}
	
	@Override public Packet getDescriptionPacket()
	{
		NBTTagCompound var1 = new NBTTagCompound();
		writeToNBT(var1);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 3, var1);
	}
	
	@Override public int getInventoryStackLimit()
	{
		return 1;
	}
	
	@Override public String getInvName()
	{
		return isInvNameLocalized() ? field_94048_i : "container.beacon";
	}
	
	public int getLevels()
	{
		return levels;
	}
	
	@Override public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
	
	public int getPrimaryEffect()
	{
		return primaryEffect;
	}
	
	public int getSecondaryEffect()
	{
		return secondaryEffect;
	}
	
	@Override public int getSizeInventory()
	{
		return 1;
	}
	
	@Override public ItemStack getStackInSlot(int par1)
	{
		return par1 == 0 ? payment : null;
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int par1)
	{
		if(par1 == 0 && payment != null)
		{
			ItemStack var2 = payment;
			payment = null;
			return var2;
		} else return null;
	}
	
	@Override public boolean isInvNameLocalized()
	{
		return field_94048_i != null && field_94048_i.length() > 0;
	}
	
	@Override public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return par2ItemStack.itemID == Item.emerald.itemID || par2ItemStack.itemID == Item.diamond.itemID || par2ItemStack.itemID == Item.ingotGold.itemID || par2ItemStack.itemID == Item.ingotIron.itemID;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : par1EntityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}
	
	@Override public void openChest()
	{
	}
	
	@Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		primaryEffect = par1NBTTagCompound.getInteger("Primary");
		secondaryEffect = par1NBTTagCompound.getInteger("Secondary");
		levels = par1NBTTagCompound.getInteger("Levels");
	}
	
	@Override public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		if(par1 == 0)
		{
			payment = par2ItemStack;
		}
	}
	
	public void setLevels(int par1)
	{
		levels = par1;
	}
	
	public void setPrimaryEffect(int par1)
	{
		primaryEffect = 0;
		for(int var2 = 0; var2 < levels && var2 < 3; ++var2)
		{
			Potion[] var3 = effectsList[var2];
			int var4 = var3.length;
			for(int var5 = 0; var5 < var4; ++var5)
			{
				Potion var6 = var3[var5];
				if(var6.id == par1)
				{
					primaryEffect = par1;
					return;
				}
			}
		}
	}
	
	public void setSecondaryEffect(int par1)
	{
		secondaryEffect = 0;
		if(levels >= 4)
		{
			for(int var2 = 0; var2 < 4; ++var2)
			{
				Potion[] var3 = effectsList[var2];
				int var4 = var3.length;
				for(int var5 = 0; var5 < var4; ++var5)
				{
					Potion var6 = var3[var5];
					if(var6.id == par1)
					{
						secondaryEffect = par1;
						return;
					}
				}
			}
		}
	}
	
	@Override public void updateEntity()
	{
		if(worldObj.getTotalWorldTime() % 80L == 0L)
		{
			updateState();
			addEffectsToPlayers();
		}
	}
	
	private void updateState()
	{
		if(!worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord))
		{
			isBeaconActive = false;
			levels = 0;
		} else
		{
			isBeaconActive = true;
			levels = 0;
			for(int var1 = 1; var1 <= 4; levels = var1++)
			{
				int var2 = yCoord - var1;
				if(var2 < 0)
				{
					break;
				}
				boolean var3 = true;
				for(int var4 = xCoord - var1; var4 <= xCoord + var1 && var3; ++var4)
				{
					for(int var5 = zCoord - var1; var5 <= zCoord + var1; ++var5)
					{
						int var6 = worldObj.getBlockId(var4, var2, var5);
						if(var6 != Block.blockEmerald.blockID && var6 != Block.blockGold.blockID && var6 != Block.blockDiamond.blockID && var6 != Block.blockIron.blockID)
						{
							var3 = false;
							break;
						}
					}
				}
				if(!var3)
				{
					break;
				}
			}
			if(levels == 0)
			{
				isBeaconActive = false;
			}
		}
	}
	
	@Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("Primary", primaryEffect);
		par1NBTTagCompound.setInteger("Secondary", secondaryEffect);
		par1NBTTagCompound.setInteger("Levels", levels);
	}
}
