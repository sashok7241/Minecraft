package net.minecraft.src;

public class PlayerCapabilities
{
	public boolean disableDamage = false;
	public boolean isFlying = false;
	public boolean allowFlying = false;
	public boolean isCreativeMode = false;
	public boolean allowEdit = true;
	private float flySpeed = 0.05F;
	private float walkSpeed = 0.1F;
	
	public float getFlySpeed()
	{
		return flySpeed;
	}
	
	public float getWalkSpeed()
	{
		return walkSpeed;
	}
	
	public void readCapabilitiesFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		if(par1NBTTagCompound.hasKey("abilities"))
		{
			NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("abilities");
			disableDamage = var2.getBoolean("invulnerable");
			isFlying = var2.getBoolean("flying");
			allowFlying = var2.getBoolean("mayfly");
			isCreativeMode = var2.getBoolean("instabuild");
			if(var2.hasKey("flySpeed"))
			{
				flySpeed = var2.getFloat("flySpeed");
				walkSpeed = var2.getFloat("walkSpeed");
			}
			if(var2.hasKey("mayBuild"))
			{
				allowEdit = var2.getBoolean("mayBuild");
			}
		}
	}
	
	public void setFlySpeed(float par1)
	{
		flySpeed = par1;
	}
	
	public void setPlayerWalkSpeed(float par1)
	{
		walkSpeed = par1;
	}
	
	public void writeCapabilitiesToNBT(NBTTagCompound par1NBTTagCompound)
	{
		NBTTagCompound var2 = new NBTTagCompound();
		var2.setBoolean("invulnerable", disableDamage);
		var2.setBoolean("flying", isFlying);
		var2.setBoolean("mayfly", allowFlying);
		var2.setBoolean("instabuild", isCreativeMode);
		var2.setBoolean("mayBuild", allowEdit);
		var2.setFloat("flySpeed", flySpeed);
		var2.setFloat("walkSpeed", walkSpeed);
		par1NBTTagCompound.setTag("abilities", var2);
	}
}
