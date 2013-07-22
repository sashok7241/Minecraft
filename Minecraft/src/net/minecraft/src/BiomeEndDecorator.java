package net.minecraft.src;

public class BiomeEndDecorator extends BiomeDecorator
{
	protected WorldGenerator spikeGen;
	
	public BiomeEndDecorator(BiomeGenBase par1BiomeGenBase)
	{
		super(par1BiomeGenBase);
		spikeGen = new WorldGenSpikes(Block.whiteStone.blockID);
	}
	
	@Override protected void decorate()
	{
		generateOres();
		if(randomGenerator.nextInt(5) == 0)
		{
			int var1 = chunk_X + randomGenerator.nextInt(16) + 8;
			int var2 = chunk_Z + randomGenerator.nextInt(16) + 8;
			int var3 = currentWorld.getTopSolidOrLiquidBlock(var1, var2);
			spikeGen.generate(currentWorld, randomGenerator, var1, var3, var2);
		}
		if(chunk_X == 0 && chunk_Z == 0)
		{
			EntityDragon var4 = new EntityDragon(currentWorld);
			var4.setLocationAndAngles(0.0D, 128.0D, 0.0D, randomGenerator.nextFloat() * 360.0F, 0.0F);
			currentWorld.spawnEntityInWorld(var4);
		}
	}
}
