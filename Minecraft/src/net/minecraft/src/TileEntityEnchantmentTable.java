package net.minecraft.src;

import java.util.Random;

public class TileEntityEnchantmentTable extends TileEntity
{
	public int tickCount;
	public float pageFlip;
	public float pageFlipPrev;
	public float field_70373_d;
	public float field_70374_e;
	public float bookSpread;
	public float bookSpreadPrev;
	public float bookRotation2;
	public float bookRotationPrev;
	public float bookRotation;
	private static Random rand = new Random();
	private String field_94136_s;
	
	public String func_94133_a()
	{
		return func_94135_b() ? field_94136_s : "container.enchant";
	}
	
	public void func_94134_a(String par1Str)
	{
		field_94136_s = par1Str;
	}
	
	public boolean func_94135_b()
	{
		return field_94136_s != null && field_94136_s.length() > 0;
	}
	
	@Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		if(par1NBTTagCompound.hasKey("CustomName"))
		{
			field_94136_s = par1NBTTagCompound.getString("CustomName");
		}
	}
	
	@Override public void updateEntity()
	{
		super.updateEntity();
		bookSpreadPrev = bookSpread;
		bookRotationPrev = bookRotation2;
		EntityPlayer var1 = worldObj.getClosestPlayer(xCoord + 0.5F, yCoord + 0.5F, zCoord + 0.5F, 3.0D);
		if(var1 != null)
		{
			double var2 = var1.posX - (xCoord + 0.5F);
			double var4 = var1.posZ - (zCoord + 0.5F);
			bookRotation = (float) Math.atan2(var4, var2);
			bookSpread += 0.1F;
			if(bookSpread < 0.5F || rand.nextInt(40) == 0)
			{
				float var6 = field_70373_d;
				do
				{
					field_70373_d += rand.nextInt(4) - rand.nextInt(4);
				} while(var6 == field_70373_d);
			}
		} else
		{
			bookRotation += 0.02F;
			bookSpread -= 0.1F;
		}
		while(bookRotation2 >= (float) Math.PI)
		{
			bookRotation2 -= (float) Math.PI * 2F;
		}
		while(bookRotation2 < -(float) Math.PI)
		{
			bookRotation2 += (float) Math.PI * 2F;
		}
		while(bookRotation >= (float) Math.PI)
		{
			bookRotation -= (float) Math.PI * 2F;
		}
		while(bookRotation < -(float) Math.PI)
		{
			bookRotation += (float) Math.PI * 2F;
		}
		float var7;
		for(var7 = bookRotation - bookRotation2; var7 >= (float) Math.PI; var7 -= (float) Math.PI * 2F)
		{
			;
		}
		while(var7 < -(float) Math.PI)
		{
			var7 += (float) Math.PI * 2F;
		}
		bookRotation2 += var7 * 0.4F;
		if(bookSpread < 0.0F)
		{
			bookSpread = 0.0F;
		}
		if(bookSpread > 1.0F)
		{
			bookSpread = 1.0F;
		}
		++tickCount;
		pageFlipPrev = pageFlip;
		float var3 = (field_70373_d - pageFlip) * 0.4F;
		float var8 = 0.2F;
		if(var3 < -var8)
		{
			var3 = -var8;
		}
		if(var3 > var8)
		{
			var3 = var8;
		}
		field_70374_e += (var3 - field_70374_e) * 0.9F;
		pageFlip += field_70374_e;
	}
	
	@Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		if(func_94135_b())
		{
			par1NBTTagCompound.setString("CustomName", field_94136_s);
		}
	}
}
