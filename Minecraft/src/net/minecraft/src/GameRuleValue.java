package net.minecraft.src;

class GameRuleValue
{
	private String valueString;
	private boolean valueBoolean;
	private int valueInteger;
	private double valueDouble;
	
	public GameRuleValue(String p_i5089_1_)
	{
		setValue(p_i5089_1_);
	}
	
	public boolean getGameRuleBooleanValue()
	{
		return valueBoolean;
	}
	
	public String getGameRuleStringValue()
	{
		return valueString;
	}
	
	public void setValue(String p_82757_1_)
	{
		valueString = p_82757_1_;
		valueBoolean = Boolean.parseBoolean(p_82757_1_);
		try
		{
			valueInteger = Integer.parseInt(p_82757_1_);
		} catch(NumberFormatException var4)
		{
			;
		}
		try
		{
			valueDouble = Double.parseDouble(p_82757_1_);
		} catch(NumberFormatException var3)
		{
			;
		}
	}
}
