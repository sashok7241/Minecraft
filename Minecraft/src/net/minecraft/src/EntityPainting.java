package net.minecraft.src;

import java.util.ArrayList;

public class EntityPainting extends EntityHanging
{
	public EnumArt art;
	
	public EntityPainting(World par1World)
	{
		super(par1World);
	}
	
	public EntityPainting(World par1World, int par2, int par3, int par4, int par5)
	{
		super(par1World, par2, par3, par4, par5);
		ArrayList var6 = new ArrayList();
		EnumArt[] var7 = EnumArt.values();
		int var8 = var7.length;
		for(int var9 = 0; var9 < var8; ++var9)
		{
			EnumArt var10 = var7[var9];
			art = var10;
			setDirection(par5);
			if(onValidSurface())
			{
				var6.add(var10);
			}
		}
		if(!var6.isEmpty())
		{
			art = (EnumArt) var6.get(rand.nextInt(var6.size()));
		}
		setDirection(par5);
	}
	
	public EntityPainting(World par1World, int par2, int par3, int par4, int par5, String par6Str)
	{
		this(par1World, par2, par3, par4, par5);
		EnumArt[] var7 = EnumArt.values();
		int var8 = var7.length;
		for(int var9 = 0; var9 < var8; ++var9)
		{
			EnumArt var10 = var7[var9];
			if(var10.title.equals(par6Str))
			{
				art = var10;
				break;
			}
		}
		setDirection(par5);
	}
	
	@Override public void dropItemStack()
	{
		entityDropItem(new ItemStack(Item.painting), 0.0F);
	}
	
	@Override public int func_82329_d()
	{
		return art.sizeX;
	}
	
	@Override public int func_82330_g()
	{
		return art.sizeY;
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		String var2 = par1NBTTagCompound.getString("Motive");
		EnumArt[] var3 = EnumArt.values();
		int var4 = var3.length;
		for(int var5 = 0; var5 < var4; ++var5)
		{
			EnumArt var6 = var3[var5];
			if(var6.title.equals(var2))
			{
				art = var6;
			}
		}
		if(art == null)
		{
			art = EnumArt.Kebab;
		}
		super.readEntityFromNBT(par1NBTTagCompound);
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setString("Motive", art.title);
		super.writeEntityToNBT(par1NBTTagCompound);
	}
}
