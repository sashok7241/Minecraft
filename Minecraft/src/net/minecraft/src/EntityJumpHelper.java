package net.minecraft.src;

public class EntityJumpHelper
{
	private EntityLiving entity;
	private boolean isJumping = false;
	
	public EntityJumpHelper(EntityLiving p_i3454_1_)
	{
		entity = p_i3454_1_;
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
