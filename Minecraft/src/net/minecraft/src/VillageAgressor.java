package net.minecraft.src;

class VillageAgressor
{
	public EntityLivingBase agressor;
	public int agressionTime;
	final Village villageObj;
	
	VillageAgressor(Village par1Village, EntityLivingBase par2EntityLivingBase, int par3)
	{
		villageObj = par1Village;
		agressor = par2EntityLivingBase;
		agressionTime = par3;
	}
}
