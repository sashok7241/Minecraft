package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet202PlayerAbilities extends Packet
{
	private boolean disableDamage = false;
	private boolean isFlying = false;
	private boolean allowFlying = false;
	private boolean isCreativeMode = false;
	private float flySpeed;
	private float walkSpeed;
	
	public Packet202PlayerAbilities()
	{
	}
	
	public Packet202PlayerAbilities(PlayerCapabilities p_i3336_1_)
	{
		setDisableDamage(p_i3336_1_.disableDamage);
		setFlying(p_i3336_1_.isFlying);
		setAllowFlying(p_i3336_1_.allowFlying);
		setCreativeMode(p_i3336_1_.isCreativeMode);
		setFlySpeed(p_i3336_1_.getFlySpeed());
		setWalkSpeed(p_i3336_1_.getWalkSpeed());
	}
	
	@Override public boolean containsSameEntityIDAs(Packet p_73268_1_)
	{
		return true;
	}
	
	public boolean getAllowFlying()
	{
		return allowFlying;
	}
	
	public boolean getDisableDamage()
	{
		return disableDamage;
	}
	
	public boolean getFlying()
	{
		return isFlying;
	}
	
	public float getFlySpeed()
	{
		return flySpeed;
	}
	
	@Override public int getPacketSize()
	{
		return 2;
	}
	
	public float getWalkSpeed()
	{
		return walkSpeed;
	}
	
	public boolean isCreativeMode()
	{
		return isCreativeMode;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handlePlayerAbilities(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		byte var2 = p_73267_1_.readByte();
		setDisableDamage((var2 & 1) > 0);
		setFlying((var2 & 2) > 0);
		setAllowFlying((var2 & 4) > 0);
		setCreativeMode((var2 & 8) > 0);
		setFlySpeed(p_73267_1_.readByte() / 255.0F);
		setWalkSpeed(p_73267_1_.readByte() / 255.0F);
	}
	
	public void setAllowFlying(boolean p_73354_1_)
	{
		allowFlying = p_73354_1_;
	}
	
	public void setCreativeMode(boolean p_73356_1_)
	{
		isCreativeMode = p_73356_1_;
	}
	
	public void setDisableDamage(boolean p_73353_1_)
	{
		disableDamage = p_73353_1_;
	}
	
	public void setFlying(boolean p_73349_1_)
	{
		isFlying = p_73349_1_;
	}
	
	public void setFlySpeed(float p_73351_1_)
	{
		flySpeed = p_73351_1_;
	}
	
	public void setWalkSpeed(float p_73355_1_)
	{
		walkSpeed = p_73355_1_;
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		byte var2 = 0;
		if(getDisableDamage())
		{
			var2 = (byte) (var2 | 1);
		}
		if(getFlying())
		{
			var2 = (byte) (var2 | 2);
		}
		if(getAllowFlying())
		{
			var2 = (byte) (var2 | 4);
		}
		if(isCreativeMode())
		{
			var2 = (byte) (var2 | 8);
		}
		p_73273_1_.writeByte(var2);
		p_73273_1_.writeByte((int) (flySpeed * 255.0F));
		p_73273_1_.writeByte((int) (walkSpeed * 255.0F));
	}
}
