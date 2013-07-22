package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdCorridor extends ComponentStronghold
{
	private final int field_74993_a;
	
	public ComponentStrongholdCorridor(int p_i3841_1_, Random p_i3841_2_, StructureBoundingBox p_i3841_3_, int p_i3841_4_)
	{
		super(p_i3841_1_);
		coordBaseMode = p_i3841_4_;
		boundingBox = p_i3841_3_;
		field_74993_a = p_i3841_4_ != 2 && p_i3841_4_ != 0 ? p_i3841_3_.getXSize() : p_i3841_3_.getZSize();
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) return false;
		else
		{
			for(int var4 = 0; var4 < field_74993_a; ++var4)
			{
				placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 0, 0, var4, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 1, 0, var4, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 2, 0, var4, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 3, 0, var4, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 4, 0, var4, p_74875_3_);
				for(int var5 = 1; var5 <= 3; ++var5)
				{
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 0, var5, var4, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 1, var5, var4, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 2, var5, var4, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 3, var5, var4, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 4, var5, var4, p_74875_3_);
				}
				placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 0, 4, var4, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 1, 4, var4, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 2, 4, var4, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 3, 4, var4, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 4, 4, var4, p_74875_3_);
			}
			return true;
		}
	}
	
	public static StructureBoundingBox func_74992_a(List p_74992_0_, Random p_74992_1_, int p_74992_2_, int p_74992_3_, int p_74992_4_, int p_74992_5_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_74992_2_, p_74992_3_, p_74992_4_, -1, -1, 0, 5, 5, 4, p_74992_5_);
		StructureComponent var8 = StructureComponent.findIntersecting(p_74992_0_, var7);
		if(var8 == null) return null;
		else
		{
			if(var8.getBoundingBox().minY == var7.minY)
			{
				for(int var9 = 3; var9 >= 1; --var9)
				{
					var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_74992_2_, p_74992_3_, p_74992_4_, -1, -1, 0, 5, 5, var9 - 1, p_74992_5_);
					if(!var8.getBoundingBox().intersectsWith(var7)) return StructureBoundingBox.getComponentToAddBoundingBox(p_74992_2_, p_74992_3_, p_74992_4_, -1, -1, 0, 5, 5, var9, p_74992_5_);
				}
			}
			return null;
		}
	}
}
