package chylex.hee.world.biome;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeEndDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.OreGenEvent;
import chylex.hee.entity.boss.EntityBossDragon;
import chylex.hee.system.logging.Stopwatch;
import chylex.hee.system.savedata.WorldDataHandler;
import chylex.hee.system.savedata.types.DragonSavefile;
import chylex.hee.system.util.MathUtil;
import chylex.hee.world.feature.WorldGenBlob;
import chylex.hee.world.feature.WorldGenEndPowderOre;
import chylex.hee.world.feature.WorldGenEndiumOre;
import chylex.hee.world.feature.WorldGenEnergyCluster;
import chylex.hee.world.feature.WorldGenMeteoroid;
import chylex.hee.world.feature.WorldGenObsidianSpike;

public class BiomeDecoratorHardcoreEnd extends BiomeEndDecorator{
	private final WorldGenBlob blobGen;
	private final WorldGenMeteoroid meteoroidGen;
	private final WorldGenEndPowderOre endPowderOreGen;
	private final WorldGenEndiumOre endiumOreGen;
	private final WorldGenEnergyCluster clusterGen;
	
	public BiomeDecoratorHardcoreEnd(){
		spikeGen = new WorldGenObsidianSpike();
		blobGen = new WorldGenBlob();
		meteoroidGen = new WorldGenMeteoroid();
		endPowderOreGen = new WorldGenEndPowderOre();
		endiumOreGen = new WorldGenEndiumOre();
		clusterGen = new WorldGenEnergyCluster();
	}
	
	@Override
	protected void genDecorations(BiomeGenBase biome){
		if (currentWorld.provider.dimensionId != 1){
			super.genDecorations(biome);
			return;
		}
		
		generateOres();

		double distFromCenter = Math.sqrt(MathUtil.square(chunk_X>>4)+MathUtil.square(chunk_Z>>4))*16D;
		
		if (distFromCenter < 120D && randomGenerator.nextInt(5) == 0){
			Stopwatch.timeAverage("WorldGenObsidianSpike",4);
			int xx = randX(), zz = randZ();
			spikeGen.generate(currentWorld,randomGenerator,xx,currentWorld.getTopSolidOrLiquidBlock(xx,zz),zz);
			Stopwatch.finish("WorldGenObsidianSpike");
		}
		
		if (distFromCenter > 102D && Math.abs(randomGenerator.nextGaussian()) < 0.285D){
			Stopwatch.timeAverage("WorldGenBlob",64);
			blobGen.generate(currentWorld,randomGenerator,chunk_X+16,32+randomGenerator.nextInt(60),chunk_Z+16);
			Stopwatch.finish("WorldGenBlob");
		}
		
		if (distFromCenter > 320D && randomGenerator.nextDouble()*randomGenerator.nextDouble() > 0.8D){
			Stopwatch.timeAverage("WorldGenEnergyCluster",64);
			
			for(int a = 0; a < randomGenerator.nextInt(2+randomGenerator.nextInt(2+randomGenerator.nextInt(2))); a++){
				clusterGen.generate(currentWorld,randomGenerator,chunk_X+8,0,chunk_Z+8);
			}
			
			Stopwatch.finish("WorldGenEnergyCluster");
		}
		
		if (distFromCenter > 500D && randomGenerator.nextInt(1+randomGenerator.nextInt(3)+MathUtil.floor(Math.max((11500D-distFromCenter)/1000D,0))) <= randomGenerator.nextInt(4)){
			Stopwatch.timeAverage("WorldGenEndiumOre",64);
			
			for(int attempt = 0, max = 1+randomGenerator.nextInt(1+randomGenerator.nextInt(1+Math.min(10,MathUtil.floor((Math.max(0,distFromCenter-1700D))/2300D)))); attempt < 440; attempt++){
				if (endiumOreGen.generate(currentWorld,randomGenerator,randX(),10+randomGenerator.nextInt(100),randZ()) && --max <= 0)break;
			}
			
			Stopwatch.finish("WorldGenEndiumOre");
		}
		
		if (distFromCenter > 1280D && randomGenerator.nextFloat()*randomGenerator.nextFloat() > 0.666F && randomGenerator.nextFloat() < 0.1F+(distFromCenter/15000D)){
			Stopwatch.timeAverage("WorldGenMeteoroid",64);
			
			for(int attempt = 0; attempt < randomGenerator.nextInt(3); attempt++){
				meteoroidGen.generate(currentWorld,randomGenerator,randX(),8+randomGenerator.nextInt(112),randZ());
			}

			Stopwatch.finish("WorldGenMeteoroid");
		}
		
		Stopwatch.timeAverage("WorldGenEndPowderOre",64);
		
		for(int attempt = 0, placed = 0, xx, yy, zz; attempt < 22 && placed < 4+randomGenerator.nextInt(5); attempt++){
			xx = randX();
			yy = 35+randomGenerator.nextInt(92);
			zz = randZ();
			
			if (currentWorld.getBlock(xx,yy,zz) == Blocks.end_stone && endPowderOreGen.generate(currentWorld,randomGenerator,xx,yy,zz)){
				++placed;
			}
		}
		
		Stopwatch.finish("WorldGenEndPowderOre");

		if (chunk_X == 0 && chunk_Z == 0){
			EntityBossDragon dragon = new EntityBossDragon(currentWorld);
			dragon.setLocationAndAngles(0D,128D,0D,randomGenerator.nextFloat()*360F,0F);
			currentWorld.spawnEntityInWorld(dragon);
			WorldDataHandler.<DragonSavefile>get(DragonSavefile.class).setDragonExists();
		}
	}

	@Override
	protected void generateOres(){
		MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(currentWorld,randomGenerator,chunk_X,chunk_Z));
		MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(currentWorld,randomGenerator,chunk_X,chunk_Z));
	}
	
	private int randX(){
		return chunk_X+randomGenerator.nextInt(16)+8;
	}
	
	private int randZ(){
		return chunk_Z+randomGenerator.nextInt(16)+8;
	}
}
