package net.minecraft.src;

class VillageAgressor
{
	public EntityLiving agressor;
	public int agressionTime;
	final Village villageObj;
	
	VillageAgressor(Village p_i3510_1_, EntityLiving p_i3510_2_, int p_i3510_3_)
	{
		villageObj = p_i3510_1_;
		agressor = p_i3510_2_;
		agressionTime = p_i3510_3_;
	}
}
