package chylex.hee.world.end.gen;
import java.util.EnumSet;
import java.util.Random;
import net.minecraft.init.Blocks;
import chylex.hee.init.BlockList;
import chylex.hee.render.environment.RenderEnvironmentSky.SkyTexture;
import chylex.hee.system.abstractions.BlockInfo;
import chylex.hee.system.abstractions.Meta;
import chylex.hee.system.util.MathUtil;
import chylex.hee.world.end.EndTerritory;
import chylex.hee.world.end.TerritoryEnvironment;
import chylex.hee.world.end.TerritoryGenerator;
import chylex.hee.world.feature.blobs.BlobPattern;
import chylex.hee.world.feature.blobs.GenerateBlobs;
import chylex.hee.world.feature.blobs.generators.BlobGenerator;
import chylex.hee.world.feature.blobs.generators.BlobGeneratorFromCenter;
import chylex.hee.world.feature.blobs.generators.BlobGeneratorSingle;
import chylex.hee.world.feature.blobs.populators.BlobPopulatorCover;
import chylex.hee.world.feature.noise.GenerateIslandNoise;
import chylex.hee.world.feature.ores.GenerateOres;
import chylex.hee.world.feature.ores.IOreGenerator;
import chylex.hee.world.feature.territory.GenerateHubTokenHolder;
import chylex.hee.world.feature.territory.GenerateHubVoidPortal;
import chylex.hee.world.structure.StructureWorld;
import chylex.hee.world.util.IRangeGenerator.RangeGenerator;
import chylex.hee.world.util.RandomAmount;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TerritoryTheHub extends TerritoryGenerator{
	private final GenerateIslandNoise island;
	private final GenerateOres endPowderOreMain;
	private final GenerateOres endPowderOreSurface;
	private final GenerateBlobs blobs;
	private final GenerateHubTokenHolder tokenHolders;
	private final GenerateHubVoidPortal voidPortal;
	
	public TerritoryTheHub(EndTerritory territory, EnumSet variations, StructureWorld world, Random rand){
		super(territory, variations, world, rand);
		
		this.island = new GenerateIslandNoise(Blocks.end_stone, rand);
		this.island.terrainSize = 64;
		this.island.noiseHeight = 20;
		this.island.peakSmoothness = 50D;
		this.island.sideSmoothness = 200D;
		this.island.surfaceHillScale = 92F;
		
		this.endPowderOreMain = new GenerateOres(Blocks.end_stone, BlockList.end_powder_ore);
		this.endPowderOreMain.setY(10, 50);
		this.endPowderOreMain.setChunkSize(24);
		this.endPowderOreMain.setAttemptsPerChunk(36);
		this.endPowderOreMain.setGeneratedPerChunk(8, 14);
		this.endPowderOreMain.setOresPerCluster(2, 6, RandomAmount.preferSmaller);
		this.endPowderOreMain.setOreGenerator(new IOreGenerator.AdjacentSpread(true));
		
		this.endPowderOreSurface = new GenerateOres(Blocks.end_stone, BlockList.end_powder_ore);
		this.endPowderOreSurface.setY(36, 50);
		this.endPowderOreSurface.setChunkSize(24);
		this.endPowderOreSurface.setAttemptsPerChunk(18);
		this.endPowderOreSurface.setGeneratedPerChunk(6, 9);
		this.endPowderOreSurface.setOresPerCluster(3, 9, RandomAmount.preferSmaller);
		this.endPowderOreSurface.setOreGenerator(new IOreGenerator.AdjacentSpread(true));
		
		this.blobs = new GenerateBlobs();
		this.blobs.setBlobWorldRad(12);
		this.blobs.setY(0, 76);
		this.blobs.setChunkSize(24);
		this.blobs.setGeneratedPerChunk(0, 5, RandomAmount.preferSmaller);
		this.blobs.setAttemptsPerChunk(7);
		this.blobs.setPredicate(GenerateBlobs.inDistanceFromCenter(this.blobs, 88D, 164D));
		
		this.blobs.addPattern(new BlobPattern(1)
			.addGenerators(new BlobGenerator[]{
				new BlobGeneratorSingle(3).setRadius(3.25D, 6D),
				new BlobGeneratorFromCenter(7).setAmount(new RangeGenerator(2, 4, RandomAmount.preferSmaller)).setRadiusFirst(3D, 4.5D).setRadiusOther(1.75D, 2.25D).setDistanceMp(0.5D, 0.85D)
			})
			.addPopulator(new BlobPopulatorCover(1).setBlock(BlockList.ravish_bell).setChance(0.1D))
			.setPopulatorAmount(random -> random.nextInt(4) == 0 ? 1 : 0)
		);
		
		this.tokenHolders = new GenerateHubTokenHolder();
		this.tokenHolders.setAttempts(15000);
		this.tokenHolders.setAmount(new RangeGenerator(3, 3, RandomAmount.linear));
		this.tokenHolders.setMinDistance(48D);
		
		this.voidPortal = new GenerateHubVoidPortal();
		this.voidPortal.setAttempts(50000);
	}
	
	@Override
	public void generate(){
		island.generate(world);
		
		int lowest = height;
		
		for(int x = -3; x <= 3; x++){
			for(int z = -3; z <= 3; z++){
				lowest = Math.min(lowest, world.getTopY(x, z, Blocks.end_stone));
			}
		}
		
		tokenHolders.generate(territory, world, rand);
		
		blobs.generateSplit(world, rand);
		endPowderOreMain.generateSplit(world, rand);
		endPowderOreSurface.generateSplit(world, rand);
		
		for(int x = -4; x <= 4; x++){
			for(int z = -4; z <= 4; z++){
				double dist = MathUtil.distance(x, z);
				if (Math.abs(x) <= 1 && Math.abs(z) <= 1)world.setAttentionWhore(x, lowest, z, new BlockInfo(Blocks.end_portal, Meta.endPortalActive));
				else if (dist <= 2.32D)world.setAttentionWhore(x, lowest, z, new BlockInfo(BlockList.end_portal_frame, Meta.endPortalFramePlain));
				else if (dist < 4D)world.setBlock(x, lowest, z, Blocks.end_stone);
				
				if (Math.abs(x) <= 2 && Math.abs(z) <= 2){
					world.setBlock(x, lowest-1, z, Blocks.end_stone); // sometimes End Powder Ore spawns under the portal and it makes particle effects
				}
				
				if (dist <= 3.61D){
					for(int y = lowest+1; y < height; y++){
						if (!world.isAir(x, y, z))world.setAir(x, y, z);
					}
				}
			}
		}
		
		voidPortal.generate(territory, world, rand);
	}
	
	public static class Environment extends TerritoryEnvironment{
		@Override
		@SideOnly(Side.CLIENT)
		public float getFogDensity(){
			return 0.005F+0.025F*getRenderDistanceMp();
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public int getSkyColor(){
			return (50<<16)|(50<<8)|50;
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public SkyTexture getSkyTexture(){
			return SkyTexture.BLUR;
		}
	}
}
