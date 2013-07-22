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
	
	@Override public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
	{
		if(p_70298_1_ == 0 && payment != null)
		{
			if(p_70298_2_ >= payment.stackSize)
			{
				ItemStack var3 = payment;
				payment = null;
				return var3;
			} else
			{
				payment.stackSize -= p_70298_2_;
				return new ItemStack(payment.itemID, p_70298_2_, payment.getItemDamage());
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
	
	public void func_94047_a(String p_94047_1_)
	{
		field_94048_i = p_94047_1_;
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
	
	@Override public ItemStack getStackInSlot(int p_70301_1_)
	{
		return p_70301_1_ == 0 ? payment : null;
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int p_70304_1_)
	{
		if(p_70304_1_ == 0 && payment != null)
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
	
	@Override public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return p_94041_2_.itemID == Item.emerald.itemID || p_94041_2_.itemID == Item.diamond.itemID || p_94041_2_.itemID == Item.ingotGold.itemID || p_94041_2_.itemID == Item.ingotIron.itemID;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : p_70300_1_.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}
	
	@Override public void openChest()
	{
	}
	
	@Override public void readFromNBT(NBTTagCompound p_70307_1_)
	{
		super.readFromNBT(p_70307_1_);
		primaryEffect = p_70307_1_.getInteger("Primary");
		secondaryEffect = p_70307_1_.getInteger("Secondary");
		levels = p_70307_1_.getInteger("Levels");
	}
	
	@Override public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
	{
		if(p_70299_1_ == 0)
		{
			payment = p_70299_2_;
		}
	}
	
	public void setLevels(int par1)
	{
		levels = par1;
	}
	
	public void setPrimaryEffect(int p_82128_1_)
	{
		primaryEffect = 0;
		for(int var2 = 0; var2 < levels && var2 < 3; ++var2)
		{
			Potion[] var3 = effectsList[var2];
			int var4 = var3.length;
			for(int var5 = 0; var5 < var4; ++var5)
			{
				Potion var6 = var3[var5];
				if(var6.id == p_82128_1_)
				{
					primaryEffect = p_82128_1_;
					return;
				}
			}
		}
	}
	
	public void setSecondaryEffect(int p_82127_1_)
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
					if(var6.id == p_82127_1_)
					{
						secondaryEffect = p_82127_1_;
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
	
	@Override public void writeToNBT(NBTTagCompound p_70310_1_)
	{
		super.writeToNBT(p_70310_1_);
		p_70310_1_.setInteger("Primary", primaryEffect);
		p_70310_1_.setInteger("Secondary", secondaryEffect);
		p_70310_1_.setInteger("Levels", levels);
	}
}
