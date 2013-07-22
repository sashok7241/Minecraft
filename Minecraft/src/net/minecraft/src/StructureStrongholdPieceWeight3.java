package net.minecraft.src;

final class StructureStrongholdPieceWeight3 extends StructureStrongholdPieceWeight
{
	StructureStrongholdPieceWeight3(Class p_i3839_1_, int p_i3839_2_, int p_i3839_3_)
	{
		super(p_i3839_1_, p_i3839_2_, p_i3839_3_);
	}
	
	@Override public boolean canSpawnMoreStructuresOfType(int p_75189_1_)
	{
		return super.canSpawnMoreStructuresOfType(p_75189_1_) && p_75189_1_ > 5;
	}
}
