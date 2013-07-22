package net.minecraft.src;

class GameRuleValue
{
	private String valueString;
	private boolean valueBoolean;
	private int valueInteger;
	private double valueDouble;
	
	public GameRuleValue(String par1Str)
	{
		setValue(par1Str);
	}
	
	public boolean getGameRuleBooleanValue()
	{
		return valueBoolean;
	}
	
	public String getGameRuleStringValue()
	{
		return valueString;
	}
	
	public void setValue(String par1Str)
	{
		valueString = par1Str;
		valueBoolean = Boolean.parseBoolean(par1Str);
		try
		{
			valueInteger = Integer.parseInt(par1Str);
		} catch(NumberFormatException var4)
		{
			;
		}
		try
		{
			valueDouble = Double.parseDouble(par1Str);
		} catch(NumberFormatException var3)
		{
			;
		}
	}
}
