package net.minecraft.src;

public class EntityJumpHelper
{
	private EntityLiving entity;
	private boolean isJumping;
	
	public EntityJumpHelper(EntityLiving par1EntityLiving)
	{
		entity = par1EntityLiving;
	}
	
	public void doJump()
	{
		entity.setJumping(isJumping);
		isJumping = false;
	}
	
	public void setJumping()
	{
		isJumping = true;
	}
}
