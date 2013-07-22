package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

abstract class ComponentNetherBridgePiece extends StructureComponent
{
	protected ComponentNetherBridgePiece(int p_i3826_1_)
	{
		super(p_i3826_1_);
	}
	
	private ComponentNetherBridgePiece getNextComponent(ComponentNetherBridgeStartPiece p_74959_1_, List p_74959_2_, List p_74959_3_, Random p_74959_4_, int p_74959_5_, int p_74959_6_, int p_74959_7_, int p_74959_8_, int p_74959_9_)
	{
		int var10 = getTotalWeight(p_74959_2_);
		boolean var11 = var10 > 0 && p_74959_9_ <= 30;
		int var12 = 0;
		while(var12 < 5 && var11)
		{
			++var12;
			int var13 = p_74959_4_.nextInt(var10);
			Iterator var14 = p_74959_2_.iterator();
			while(var14.hasNext())
			{
				StructureNetherBridgePieceWeight var15 = (StructureNetherBridgePieceWeight) var14.next();
				var13 -= var15.field_78826_b;
				if(var13 < 0)
				{
					if(!var15.func_78822_a(p_74959_9_) || var15 == p_74959_1_.theNetherBridgePieceWeight && !var15.field_78825_e)
					{
						break;
					}
					ComponentNetherBridgePiece var16 = StructureNetherBridgePieces.createNextComponent(var15, p_74959_3_, p_74959_4_, p_74959_5_, p_74959_6_, p_74959_7_, p_74959_8_, p_74959_9_);
					if(var16 != null)
					{
						++var15.field_78827_c;
						p_74959_1_.theNetherBridgePieceWeight = var15;
						if(!var15.func_78823_a())
						{
							p_74959_2_.remove(var15);
						}
						return var16;
					}
				}
			}
		}
		return ComponentNetherBridgeEnd.func_74971_a(p_74959_3_, p_74959_4_, p_74959_5_, p_74959_6_, p_74959_7_, p_74959_8_, p_74959_9_);
	}
	
	private StructureComponent getNextComponent(ComponentNetherBridgeStartPiece p_74962_1_, List p_74962_2_, Random p_74962_3_, int p_74962_4_, int p_74962_5_, int p_74962_6_, int p_74962_7_, int p_74962_8_, boolean p_74962_9_)
	{
		if(Math.abs(p_74962_4_ - p_74962_1_.getBoundingBox().minX) <= 112 && Math.abs(p_74962_6_ - p_74962_1_.getBoundingBox().minZ) <= 112)
		{
			List var10 = p_74962_1_.primaryWeights;
			if(p_74962_9_)
			{
				var10 = p_74962_1_.secondaryWeights;
			}
			ComponentNetherBridgePiece var11 = this.getNextComponent(p_74962_1_, var10, p_74962_2_, p_74962_3_, p_74962_4_, p_74962_5_, p_74962_6_, p_74962_7_, p_74962_8_ + 1);
			if(var11 != null)
			{
				p_74962_2_.add(var11);
				p_74962_1_.field_74967_d.add(var11);
			}
			return var11;
		} else return ComponentNetherBridgeEnd.func_74971_a(p_74962_2_, p_74962_3_, p_74962_4_, p_74962_5_, p_74962_6_, p_74962_7_, p_74962_8_);
	}
	
	protected StructureComponent getNextComponentNormal(ComponentNetherBridgeStartPiece p_74963_1_, List p_74963_2_, Random p_74963_3_, int p_74963_4_, int p_74963_5_, boolean p_74963_6_)
	{
		switch(coordBaseMode)
		{
			case 0:
				return this.getNextComponent(p_74963_1_, p_74963_2_, p_74963_3_, boundingBox.minX + p_74963_4_, boundingBox.minY + p_74963_5_, boundingBox.maxZ + 1, coordBaseMode, getComponentType(), p_74963_6_);
			case 1:
				return this.getNextComponent(p_74963_1_, p_74963_2_, p_74963_3_, boundingBox.minX - 1, boundingBox.minY + p_74963_5_, boundingBox.minZ + p_74963_4_, coordBaseMode, getComponentType(), p_74963_6_);
			case 2:
				return this.getNextComponent(p_74963_1_, p_74963_2_, p_74963_3_, boundingBox.minX + p_74963_4_, boundingBox.minY + p_74963_5_, boundingBox.minZ - 1, coordBaseMode, getComponentType(), p_74963_6_);
			case 3:
				return this.getNextComponent(p_74963_1_, p_74963_2_, p_74963_3_, boundingBox.maxX + 1, boundingBox.minY + p_74963_5_, boundingBox.minZ + p_74963_4_, coordBaseMode, getComponentType(), p_74963_6_);
			default:
				return null;
		}
	}
	
	protected StructureComponent getNextComponentX(ComponentNetherBridgeStartPiece p_74961_1_, List p_74961_2_, Random p_74961_3_, int p_74961_4_, int p_74961_5_, boolean p_74961_6_)
	{
		switch(coordBaseMode)
		{
			case 0:
				return this.getNextComponent(p_74961_1_, p_74961_2_, p_74961_3_, boundingBox.minX - 1, boundingBox.minY + p_74961_4_, boundingBox.minZ + p_74961_5_, 1, getComponentType(), p_74961_6_);
			case 1:
				return this.getNextComponent(p_74961_1_, p_74961_2_, p_74961_3_, boundingBox.minX + p_74961_5_, boundingBox.minY + p_74961_4_, boundingBox.minZ - 1, 2, getComponentType(), p_74961_6_);
			case 2:
				return this.getNextComponent(p_74961_1_, p_74961_2_, p_74961_3_, boundingBox.minX - 1, boundingBox.minY + p_74961_4_, boundingBox.minZ + p_74961_5_, 1, getComponentType(), p_74961_6_);
			case 3:
				return this.getNextComponent(p_74961_1_, p_74961_2_, p_74961_3_, boundingBox.minX + p_74961_5_, boundingBox.minY + p_74961_4_, boundingBox.minZ - 1, 2, getComponentType(), p_74961_6_);
			default:
				return null;
		}
	}
	
	protected StructureComponent getNextComponentZ(ComponentNetherBridgeStartPiece p_74965_1_, List p_74965_2_, Random p_74965_3_, int p_74965_4_, int p_74965_5_, boolean p_74965_6_)
	{
		switch(coordBaseMode)
		{
			case 0:
				return this.getNextComponent(p_74965_1_, p_74965_2_, p_74965_3_, boundingBox.maxX + 1, boundingBox.minY + p_74965_4_, boundingBox.minZ + p_74965_5_, 3, getComponentType(), p_74965_6_);
			case 1:
				return this.getNextComponent(p_74965_1_, p_74965_2_, p_74965_3_, boundingBox.minX + p_74965_5_, boundingBox.minY + p_74965_4_, boundingBox.maxZ + 1, 0, getComponentType(), p_74965_6_);
			case 2:
				return this.getNextComponent(p_74965_1_, p_74965_2_, p_74965_3_, boundingBox.maxX + 1, boundingBox.minY + p_74965_4_, boundingBox.minZ + p_74965_5_, 3, getComponentType(), p_74965_6_);
			case 3:
				return this.getNextComponent(p_74965_1_, p_74965_2_, p_74965_3_, boundingBox.minX + p_74965_5_, boundingBox.minY + p_74965_4_, boundingBox.maxZ + 1, 0, getComponentType(), p_74965_6_);
			default:
				return null;
		}
	}
	
	private int getTotalWeight(List p_74960_1_)
	{
		boolean var2 = false;
		int var3 = 0;
		StructureNetherBridgePieceWeight var5;
		for(Iterator var4 = p_74960_1_.iterator(); var4.hasNext(); var3 += var5.field_78826_b)
		{
			var5 = (StructureNetherBridgePieceWeight) var4.next();
			if(var5.field_78824_d > 0 && var5.field_78827_c < var5.field_78824_d)
			{
				var2 = true;
			}
		}
		return var2 ? var3 : -1;
	}
	
	protected static boolean isAboveGround(StructureBoundingBox p_74964_0_)
	{
		return p_74964_0_ != null && p_74964_0_.minY > 10;
	}
}
