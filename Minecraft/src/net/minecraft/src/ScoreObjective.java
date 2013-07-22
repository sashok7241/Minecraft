package net.minecraft.src;

public class ScoreObjective
{
	private final Scoreboard theScoreboard;
	private final String name;
	private final ScoreObjectiveCriteria objectiveCriteria;
	private String displayName;
	
	public ScoreObjective(Scoreboard par1Scoreboard, String par2Str, ScoreObjectiveCriteria par3ScoreObjectiveCriteria)
	{
		theScoreboard = par1Scoreboard;
		name = par2Str;
		objectiveCriteria = par3ScoreObjectiveCriteria;
		displayName = par2Str;
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
	
	public void setDisplayName(String par1Str)
	{
		displayName = par1Str;
		theScoreboard.func_96532_b(this);
	}
}
