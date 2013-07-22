package net.minecraft.src;

import java.util.Random;

public class Particle
{
	private static Random rand = new Random();
	public double posX;
	public double posY;
	public double prevPosX;
	public double prevPosY;
	public double velocityX;
	public double velocityY;
	public double accelScale;
	public boolean isDead;
	public int timeTick;
	public int timeLimit;
	public double tintRed;
	public double tintGreen;
	public double tintBlue;
	public double tintAlpha;
	public double prevTintRed;
	public double prevTintGreen;
	public double prevTintBlue;
	public double prevTintAlpha;
	
	public void preUpdate()
	{
		prevTintRed = tintRed;
		prevTintGreen = tintGreen;
		prevTintBlue = tintBlue;
		prevTintAlpha = tintAlpha;
		prevPosX = posX;
		prevPosY = posY;
	}
	
	public void setDead()
	{
		isDead = true;
	}
	
	public void update(GuiParticle par1GuiParticle)
	{
		posX += velocityX;
		posY += velocityY;
		velocityX *= accelScale;
		velocityY *= accelScale;
		velocityY += 0.1D;
		if(++timeTick > timeLimit)
		{
			setDead();
		}
		tintAlpha = 2.0D - (double) timeTick / (double) timeLimit * 2.0D;
		if(tintAlpha > 1.0D)
		{
			tintAlpha = 1.0D;
		}
		tintAlpha *= tintAlpha;
		tintAlpha *= 0.5D;
	}
}
