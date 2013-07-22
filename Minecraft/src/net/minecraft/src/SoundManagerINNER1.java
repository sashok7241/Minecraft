package net.minecraft.src;


class SoundManagerINNER1 implements Runnable
{
	final SoundManager field_130090_a;
	
	SoundManagerINNER1(SoundManager par1SoundManager)
	{
		field_130090_a = par1SoundManager;
	}
	
	@Override public void run()
	{
		SoundManager.func_130080_a(field_130090_a, new SoundSystem());
		SoundManager.func_130082_a(field_130090_a, true);
	}
}
