package net.minecraft.src;

final class StepSoundStone extends StepSound
{
	StepSoundStone(String p_i4006_1_, float p_i4006_2_, float p_i4006_3_)
	{
		super(p_i4006_1_, p_i4006_2_, p_i4006_3_);
	}
	
	@Override public String getBreakSound()
	{
		return "random.glass";
	}
	
	@Override public String getPlaceSound()
	{
		return "step.stone";
	}
}
