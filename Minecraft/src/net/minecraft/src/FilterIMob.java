package net.minecraft.src;

final class FilterIMob implements IEntitySelector
{
	@Override public boolean isEntityApplicable(Entity p_82704_1_)
	{
		return p_82704_1_ instanceof IMob;
	}
}
