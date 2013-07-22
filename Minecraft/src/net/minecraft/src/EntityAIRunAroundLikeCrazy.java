package net.minecraft.src;

public class EntityAIRunAroundLikeCrazy extends EntityAIBase
{
	private EntityHorse field_111180_a;
	private double field_111178_b;
	private double field_111179_c;
	private double field_111176_d;
	private double field_111177_e;
	
	public EntityAIRunAroundLikeCrazy(EntityHorse par1EntityHorse, double par2)
	{
		field_111180_a = par1EntityHorse;
		field_111178_b = par2;
		setMutexBits(1);
	}
	
	@Override public boolean continueExecuting()
	{
		return !field_111180_a.getNavigator().noPath() && field_111180_a.riddenByEntity != null;
	}
	
	@Override public boolean shouldExecute()
	{
		if(!field_111180_a.func_110248_bS() && field_111180_a.riddenByEntity != null)
		{
			Vec3 var1 = RandomPositionGenerator.findRandomTarget(field_111180_a, 5, 4);
			if(var1 == null) return false;
			else
			{
				field_111179_c = var1.xCoord;
				field_111176_d = var1.yCoord;
				field_111177_e = var1.zCoord;
				return true;
			}
		} else return false;
	}
	
	@Override public void startExecuting()
	{
		field_111180_a.getNavigator().tryMoveToXYZ(field_111179_c, field_111176_d, field_111177_e, field_111178_b);
	}
	
	@Override public void updateTask()
	{
		if(field_111180_a.getRNG().nextInt(50) == 0)
		{
			if(field_111180_a.riddenByEntity instanceof EntityPlayer)
			{
				int var1 = field_111180_a.func_110252_cg();
				int var2 = field_111180_a.func_110218_cm();
				if(var2 > 0 && field_111180_a.getRNG().nextInt(var2) < var1)
				{
					field_111180_a.func_110263_g((EntityPlayer) field_111180_a.riddenByEntity);
					field_111180_a.worldObj.setEntityState(field_111180_a, (byte) 7);
					return;
				}
				field_111180_a.func_110198_t(5);
			}
			field_111180_a.riddenByEntity.mountEntity((Entity) null);
			field_111180_a.riddenByEntity = null;
			field_111180_a.func_110231_cz();
			field_111180_a.worldObj.setEntityState(field_111180_a, (byte) 6);
		}
	}
}
