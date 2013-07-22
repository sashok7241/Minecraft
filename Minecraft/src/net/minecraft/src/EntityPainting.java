package net.minecraft.src;

import java.util.ArrayList;

public class EntityPainting extends EntityHanging
{
	public EnumArt art;
	
	public EntityPainting(World p_i3447_1_)
	{
		super(p_i3447_1_);
	}
	
	public EntityPainting(World p_i3448_1_, int p_i3448_2_, int p_i3448_3_, int p_i3448_4_, int p_i3448_5_)
	{
		super(p_i3448_1_, p_i3448_2_, p_i3448_3_, p_i3448_4_, p_i3448_5_);
		ArrayList var6 = new ArrayList();
		EnumArt[] var7 = EnumArt.values();
		int var8 = var7.length;
		for(int var9 = 0; var9 < var8; ++var9)
		{
			EnumArt var10 = var7[var9];
			art = var10;
			setDirection(p_i3448_5_);
			if(onValidSurface())
			{
				var6.add(var10);
			}
		}
		if(!var6.isEmpty())
		{
			art = (EnumArt) var6.get(rand.nextInt(var6.size()));
		}
		setDirection(p_i3448_5_);
	}
	
	public EntityPainting(World p_i3449_1_, int p_i3449_2_, int p_i3449_3_, int p_i3449_4_, int p_i3449_5_, String p_i3449_6_)
	{
		this(p_i3449_1_, p_i3449_2_, p_i3449_3_, p_i3449_4_, p_i3449_5_);
		EnumArt[] var7 = EnumArt.values();
		int var8 = var7.length;
		for(int var9 = 0; var9 < var8; ++var9)
		{
			EnumArt var10 = var7[var9];
			if(var10.title.equals(p_i3449_6_))
			{
				art = var10;
				break;
			}
		}
		setDirection(p_i3449_5_);
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
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		String var2 = p_70037_1_.getString("Motive");
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
		super.readEntityFromNBT(p_70037_1_);
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		p_70014_1_.setString("Motive", art.title);
		super.writeEntityToNBT(p_70014_1_);
	}
}
