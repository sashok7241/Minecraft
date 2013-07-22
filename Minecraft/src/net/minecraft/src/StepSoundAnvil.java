package net.minecraft.src;

final class StepSoundAnvil extends StepSound
{
	StepSoundAnvil(String p_i5107_1_, float p_i5107_2_, float p_i5107_3_)
	{
		super(p_i5107_1_, p_i5107_2_, p_i5107_3_);
	}
	
	@Override public String getBreakSound()
	{
		return "dig.stone";
	}
	
	@Override public String getPlaceSound()
	{
		return "random.anvil_land";
	}
}
