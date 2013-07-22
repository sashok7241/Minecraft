package net.minecraft.src;

public class WeightedRandomMinecart extends WeightedRandomItem
{
	public final NBTTagCompound field_98222_b;
	public final String minecartName;
	final MobSpawnerBaseLogic field_98221_d;
	
	public WeightedRandomMinecart(MobSpawnerBaseLogic p_i11041_1_, NBTTagCompound p_i11041_2_)
	{
		super(p_i11041_2_.getInteger("Weight"));
		field_98221_d = p_i11041_1_;
		NBTTagCompound var3 = p_i11041_2_.getCompoundTag("Properties");
		String var4 = p_i11041_2_.getString("Type");
		if(var4.equals("Minecart"))
		{
			if(var3 != null)
			{
				switch(var3.getInteger("Type"))
				{
					case 0:
						var4 = "MinecartRideable";
						break;
					case 1:
						var4 = "MinecartChest";
						break;
					case 2:
						var4 = "MinecartFurnace";
				}
			} else
			{
				var4 = "MinecartRideable";
			}
		}
		field_98222_b = var3;
		minecartName = var4;
	}
	
	public WeightedRandomMinecart(MobSpawnerBaseLogic p_i11042_1_, NBTTagCompound p_i11042_2_, String p_i11042_3_)
	{
		super(1);
		field_98221_d = p_i11042_1_;
		if(p_i11042_3_.equals("Minecart"))
		{
			if(p_i11042_2_ != null)
			{
				switch(p_i11042_2_.getInteger("Type"))
				{
					case 0:
						p_i11042_3_ = "MinecartRideable";
						break;
					case 1:
						p_i11042_3_ = "MinecartChest";
						break;
					case 2:
						p_i11042_3_ = "MinecartFurnace";
				}
			} else
			{
				p_i11042_3_ = "MinecartRideable";
			}
		}
		field_98222_b = p_i11042_2_;
		minecartName = p_i11042_3_;
	}
	
	public NBTTagCompound func_98220_a()
	{
		NBTTagCompound var1 = new NBTTagCompound();
		var1.setCompoundTag("Properties", field_98222_b);
		var1.setString("Type", minecartName);
		var1.setInteger("Weight", itemWeight);
		return var1;
	}
}
