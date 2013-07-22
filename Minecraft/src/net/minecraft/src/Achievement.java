package net.minecraft.src;

public class Achievement extends StatBase
{
	public final int displayColumn;
	public final int displayRow;
	public final Achievement parentAchievement;
	private final String achievementDescription;
	private IStatStringFormat statStringFormatter;
	public final ItemStack theItemStack;
	private boolean isSpecial;
	
	public Achievement(int p_i3410_1_, String p_i3410_2_, int p_i3410_3_, int p_i3410_4_, Block p_i3410_5_, Achievement p_i3410_6_)
	{
		this(p_i3410_1_, p_i3410_2_, p_i3410_3_, p_i3410_4_, new ItemStack(p_i3410_5_), p_i3410_6_);
	}
	
	public Achievement(int p_i3409_1_, String p_i3409_2_, int p_i3409_3_, int p_i3409_4_, Item p_i3409_5_, Achievement p_i3409_6_)
	{
		this(p_i3409_1_, p_i3409_2_, p_i3409_3_, p_i3409_4_, new ItemStack(p_i3409_5_), p_i3409_6_);
	}
	
	public Achievement(int p_i3411_1_, String p_i3411_2_, int p_i3411_3_, int p_i3411_4_, ItemStack p_i3411_5_, Achievement p_i3411_6_)
	{
		super(5242880 + p_i3411_1_, "achievement." + p_i3411_2_);
		theItemStack = p_i3411_5_;
		achievementDescription = "achievement." + p_i3411_2_ + ".desc";
		displayColumn = p_i3411_3_;
		displayRow = p_i3411_4_;
		if(p_i3411_3_ < AchievementList.minDisplayColumn)
		{
			AchievementList.minDisplayColumn = p_i3411_3_;
		}
		if(p_i3411_4_ < AchievementList.minDisplayRow)
		{
			AchievementList.minDisplayRow = p_i3411_4_;
		}
		if(p_i3411_3_ > AchievementList.maxDisplayColumn)
		{
			AchievementList.maxDisplayColumn = p_i3411_3_;
		}
		if(p_i3411_4_ > AchievementList.maxDisplayRow)
		{
			AchievementList.maxDisplayRow = p_i3411_4_;
		}
		parentAchievement = p_i3411_6_;
	}
	
	public String getDescription()
	{
		return statStringFormatter != null ? statStringFormatter.formatString(StatCollector.translateToLocal(achievementDescription)) : StatCollector.translateToLocal(achievementDescription);
	}
	
	public boolean getSpecial()
	{
		return isSpecial;
	}
	
	@Override public StatBase initIndependentStat()
	{
		return setIndependent();
	}
	
	@Override public boolean isAchievement()
	{
		return true;
	}
	
	public Achievement registerAchievement()
	{
		super.registerStat();
		AchievementList.achievementList.add(this);
		return this;
	}
	
	@Override public StatBase registerStat()
	{
		return registerAchievement();
	}
	
	public Achievement setIndependent()
	{
		isIndependent = true;
		return this;
	}
	
	public Achievement setSpecial()
	{
		isSpecial = true;
		return this;
	}
	
	public Achievement setStatStringFormatter(IStatStringFormat par1IStatStringFormat)
	{
		statStringFormatter = par1IStatStringFormat;
		return this;
	}
}
