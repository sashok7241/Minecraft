package net.minecraft.src;

final class StepSoundSand extends StepSound
{
	StepSoundSand(String p_i4007_1_, float p_i4007_2_, float p_i4007_3_)
	{
		super(p_i4007_1_, p_i4007_2_, p_i4007_3_);
	}
	
	@Override public String getBreakSound()
	{
		return "dig.wood";
	}
}
