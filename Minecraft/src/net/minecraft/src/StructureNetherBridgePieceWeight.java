package net.minecraft.src;

class StructureNetherBridgePieceWeight
{
	public Class weightClass;
	public final int field_78826_b;
	public int field_78827_c;
	public int field_78824_d;
	public boolean field_78825_e;
	
	public StructureNetherBridgePieceWeight(Class par1Class, int par2, int par3)
	{
		this(par1Class, par2, par3, false);
	}
	
	public StructureNetherBridgePieceWeight(Class par1Class, int par2, int par3, boolean par4)
	{
		weightClass = par1Class;
		field_78826_b = par2;
		field_78824_d = par3;
		field_78825_e = par4;
	}
	
	public boolean func_78822_a(int par1)
	{
		return field_78824_d == 0 || field_78827_c < field_78824_d;
	}
	
	public boolean func_78823_a()
	{
		return field_78824_d == 0 || field_78827_c < field_78824_d;
	}
}
