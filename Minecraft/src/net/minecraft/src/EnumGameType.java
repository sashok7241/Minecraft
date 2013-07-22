package net.minecraft.src;

public enum EnumGameType
{
	NOT_SET(-1, ""), SURVIVAL(0, "survival"), CREATIVE(1, "creative"), ADVENTURE(2, "adventure");
	int id;
	String name;
	
	private EnumGameType(int p_i3734_3_, String p_i3734_4_)
	{
		id = p_i3734_3_;
		name = p_i3734_4_;
	}
	
	public void configurePlayerCapabilities(PlayerCapabilities p_77147_1_)
	{
		if(this == CREATIVE)
		{
			p_77147_1_.allowFlying = true;
			p_77147_1_.isCreativeMode = true;
			p_77147_1_.disableDamage = true;
		} else
		{
			p_77147_1_.allowFlying = false;
			p_77147_1_.isCreativeMode = false;
			p_77147_1_.disableDamage = false;
			p_77147_1_.isFlying = false;
		}
		p_77147_1_.allowEdit = !isAdventure();
	}
	
	public int getID()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean isAdventure()
	{
		return this == ADVENTURE;
	}
	
	public boolean isCreative()
	{
		return this == CREATIVE;
	}
	
	public boolean isSurvivalOrAdventure()
	{
		return this == SURVIVAL || this == ADVENTURE;
	}
	
	public static EnumGameType getByID(int p_77146_0_)
	{
		EnumGameType[] var1 = values();
		int var2 = var1.length;
		for(int var3 = 0; var3 < var2; ++var3)
		{
			EnumGameType var4 = var1[var3];
			if(var4.id == p_77146_0_) return var4;
		}
		return SURVIVAL;
	}
	
	public static EnumGameType getByName(String par0Str)
	{
		EnumGameType[] var1 = values();
		int var2 = var1.length;
		for(int var3 = 0; var3 < var2; ++var3)
		{
			EnumGameType var4 = var1[var3];
			if(var4.name.equals(par0Str)) return var4;
		}
		return SURVIVAL;
	}
}
