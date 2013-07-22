package net.minecraft.src;

public class ScoreObjective
{
	private final Scoreboard theScoreboard;
	private final String name;
	private final ScoreObjectiveCriteria objectiveCriteria;
	private String displayName;
	
	public ScoreObjective(Scoreboard p_i10061_1_, String p_i10061_2_, ScoreObjectiveCriteria p_i10061_3_)
	{
		theScoreboard = p_i10061_1_;
		name = p_i10061_2_;
		objectiveCriteria = p_i10061_3_;
		displayName = p_i10061_2_;
	}
	
	public ScoreObjectiveCriteria getCriteria()
	{
		return objectiveCriteria;
	}
	
	public String getDisplayName()
	{
		return displayName;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Scoreboard getScoreboard()
	{
		return theScoreboard;
	}
	
	public void setDisplayName(String p_96681_1_)
	{
		displayName = p_96681_1_;
		theScoreboard.func_96532_b(this);
	}
}
