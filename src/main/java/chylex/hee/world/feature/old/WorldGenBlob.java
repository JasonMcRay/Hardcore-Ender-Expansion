package chylex.hee.world.feature.old;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import chylex.hee.game.commands.HeeDebugCommand.HeeTest;
import chylex.hee.system.collections.weight.WeightedList;
import chylex.hee.world.feature.old_blobs.BlobGenerator;
import chylex.hee.world.feature.old_blobs.BlobPattern;
import chylex.hee.world.feature.old_blobs.BlobPopulator;
import chylex.hee.world.feature.old_blobs.generators.BlobGeneratorSingle;
import chylex.hee.world.feature.old_blobs.populators.BlobPopulatorEndermanSpawner;
import chylex.hee.world.util.RandomAmount;

public class WorldGenBlob extends WorldGenerator{
	static{
		/*BlobType.COMMON.patterns.add(new BlobPattern[]{
			// basic random pattern
			new BlobPattern(1).addGenerators(new BlobGenerator[]{
				new BlobGeneratorFromCenter(10).amount(RandomAmount.preferSmaller,2,6).rad(2.5D,4.5D).dist(3.5D,6D),
				new BlobGeneratorSingle(9).rad(2D,5D),
				new BlobGeneratorRecursive(8).baseAmount(RandomAmount.linear,1,4).totalAmount(RandomAmount.preferSmaller,4,10).recursionAmount(RandomAmount.preferSmaller,1,5).recursionChance(0.1D,0.5D,0.8D,4).rad(2.6D,3.8D).distMp(0.9D,1.5D).cacheRecursionChance(),
				new BlobGeneratorFromCenter(7).amount(RandomAmount.aroundCenter,2,8).rad(2.2D,5D).dist(6D,6D).limitDist().unifySize(),
				new BlobGeneratorSingle(4).rad(4D,10D),
				new BlobGeneratorRecursive(4).baseAmount(RandomAmount.linear,1,3).totalAmount(RandomAmount.linear,5,9).recursionAmount(RandomAmount.linear,1,3).recursionChance(0.4D,0.8D,0.5D,3).rad(3D,5.5D).distMp(1D,1.7D),
				new BlobGeneratorFromCenter(3).amount(RandomAmount.linear,4,10).rad(2.4D,3D).dist(2D,6D),
				new BlobGeneratorChain(3).amount(RandomAmount.linear,3,6).rad(2.5D,4D).distMp(1.5D,2.5D)
			}).addPopulators(new BlobPopulator[]{
				new BlobPopulatorCave(4).rad(2.2D,2.8D).totalCaveAmount(RandomAmount.linear,2,5).fullCaveAmount(RandomAmount.preferSmaller,1,3).recursionChance(0.2D,0.8D,3).recursionRadMp(0.7D,0.9D).cacheRecursionChance(),
				new BlobPopulatorSpikes(4).block(Blocks.obsidian).amount(RandomAmount.aroundCenter,2,10).maxOffset(16),
				new BlobPopulatorLiquidFall(2).block(BlockList.ender_goo).amount(RandomAmount.preferSmaller,1,4).attempts(50,80).requireBlockBelow(),
				new BlobPopulatorLake(2).block(BlockList.ender_goo).rad(2D,3.5D),
				new BlobPopulatorOreCluster(4).block(BlockList.end_powder_ore).blockAmount(RandomAmount.linear,4,7).iterationAmount(RandomAmount.preferSmaller,1,4),
				new BlobPopulatorOreScattered(3).block(BlockList.igneous_rock_ore).blockAmount(RandomAmount.preferSmaller,1,4).attempts(5,10).visiblePlacementAttempts(10).knownBlockLocations(),
				new BlobPopulatorOreScattered(1).block(BlockList.end_powder_ore).blockAmount(RandomAmount.aroundCenter,3,8).attempts(8,15).visiblePlacementAttempts(5),
				new BlobPopulatorCover(1).block(Blocks.obsidian).replaceTopBlock(),
				new BlobPopulatorPlant(3).block(BlockList.death_flower).blockAmount(RandomAmount.linear,3,7).attempts(20,35).knownBlockLocations()
			}).setPopulatorAmountProvider(RandomAmount.preferSmaller,1,7)
		});
		
		BlobType.UNCOMMON.patterns.add(new BlobPattern[]{
			// tiny blob with ores
			new BlobPattern(7).addGenerators(new BlobGenerator[]{
				new BlobGeneratorSingle(4).rad(2.5D,3.5D),
				new BlobGeneratorSingle(1).rad(3.2D,5.2D)
			}).addPopulators(new BlobPopulator[]{
				new BlobPopulatorOreScattered(1).block(BlockList.end_powder_ore).blockAmount(RandomAmount.linear,10,20).attempts(40,40).visiblePlacementAttempts(15).knownBlockLocations()
			}).setPopulatorAmountProvider(RandomAmount.exact,1,1),
			
			// blob with a cut off part
			new BlobPattern(6).addGenerators(new BlobGenerator[]{
				new BlobGeneratorSingleCut(1).cutRadMp(0.2D,0.7D).cutDistMp(0.7D,1.5D).rad(3.5D,6D)
			}),
			
			// caterpillar
			new BlobPattern(4).addGenerators(new BlobGenerator[]{
				new BlobGeneratorChain(1).amount(RandomAmount.aroundCenter,4,9).rad(2.7D,3.6D).distMp(0.9D,1.1D).unifySize()
			}),
			
			// ender goo filled blob
			new BlobPattern(3).addGenerators(new BlobGenerator[]{
				new BlobGeneratorSingle(1).rad(3.8D,7D)
			}).addPopulators(new BlobPopulator[]{
				new BlobPopulatorFiller(1).block(BlockList.ender_goo),
				new BlobPopulatorLiquidFall(1).block(BlockList.ender_goo).amount(RandomAmount.linear,14,22).attempts(22,36)
			}).setPopulatorAmountProvider(RandomAmount.exact,2,2),
			
			// hollow goo covered blob with a chest inside
			new BlobPattern(2).addGenerators(new BlobGenerator[]{
				new BlobGeneratorSingle(1).rad(5D,7.5D)
			}).addPopulators(new BlobPopulator[]{
				new BlobPopulatorFiller(1).block(Blocks.air),
				/* TODO new BlobPopulatorChest(1).loot(new WeightedLootList(new LootItemStack[]{
					new LootItemStack(ItemList.end_powder).setAmount(1,5).setWeight(15),
					new LootItemStack(ItemList.knowledge_note).setWeight(10),
					new LootItemStack(Items.ender_pearl).setAmount(1,4).setWeight(9),
					new LootItemStack(Items.bucket).setWeight(7),
					new LootItemStack(ItemList.bucket_ender_goo).setWeight(5),
					new LootItemStack(ItemList.adventurers_diary).setWeight(5),
					new LootItemStack(ItemList.music_disk).setDamage(0,ItemMusicDisk.getRecordCount()-1).setWeight(5),
					new LootItemStack(Items.ender_eye).setWeight(4)
				}).addItemPostProcessor((is, rand) -> {
					if (is.getItem() == ItemList.knowledge_note)ItemKnowledgeNote.setRandomNote(is,rand,3);
					return is;
				}),IRandomAmount.preferSmaller,3,10).onlyInside(),*/
				/*new BlobPopulatorCover(1).block(BlockList.ender_goo)
			}).setPopulatorAmountProvider(RandomAmount.exact,3,3),
			
			// explosions from center
			new BlobPattern(2).addGenerators(new BlobGenerator[]{
				new BlobGeneratorFromCenter(1).amount(RandomAmount.linear,1,5).rad(2.6D,4.5D).dist(3.2D,5.5D)
			}).addPopulators(new BlobPopulator[]{
				new BlobPopulatorSpikes(1).block(Blocks.air).amount(RandomAmount.linear,25,42)
			}).setPopulatorAmountProvider(RandomAmount.exact,1,1)
		});
		
		/*BlobType.RARE.patterns.add(new BlobPattern[]{
			// transport beacon
			new BlobPattern(3).addGenerators(new BlobGenerator[]{
				new BlobGeneratorFromCenter(10).amount(RandomAmount.preferSmaller,3,6).rad(2.7D,4.5D).dist(3.2D,5D),
				new BlobGeneratorRecursive(8).baseAmount(RandomAmount.linear,1,3).totalAmount(RandomAmount.preferSmaller,4,8).recursionAmount(RandomAmount.preferSmaller,1,3).recursionChance(0.2D,0.45D,0.7D,3).rad(2.5D,4D).distMp(0.9D,1.5D),
				new BlobGeneratorRecursive(5).baseAmount(RandomAmount.linear,2,4).totalAmount(RandomAmount.preferSmaller,5,10).recursionAmount(RandomAmount.preferSmaller,1,4).recursionChance(0.1D,0.4D,0.7D,4).rad(2.5D,4D).distMp(1D,1.4D).cacheRecursionChance(),
				new BlobGeneratorSingle(4).rad(2.5D,5.2D),
				new BlobGeneratorChain(4).amount(RandomAmount.linear,2,5).rad(3D,4D).distMp(1.75D,2.5D),
				new BlobGeneratorFromCenter(3).amount(RandomAmount.preferSmaller,3,6).rad(2.7D,4.5D).dist(3.2D,5D).unifySize()
			}).addPopulators(new BlobPopulator[]{
				new BlobPopulatorTransportBeacon(1)
			}).setPopulatorAmountProvider(RandomAmount.exact,1,1),
			
			// blob filled with ores
			new BlobPattern(2).addGenerators(new BlobGenerator[]{
				new BlobGeneratorSingle(1).rad(2.9D,4.9D),
				new BlobGeneratorChain(1).amount(RandomAmount.aroundCenter,2,5).rad(2.4D,2.8D).distMp(1D,2D),
				new BlobGeneratorFromCenter(1).amount(RandomAmount.aroundCenter,3,6).rad(2.4D,3.1D).dist(1.1D,1.5D).limitDist()
			}).addPopulators(new BlobPopulator[]{
				new BlobPopulatorOreScattered(1).block(BlockList.end_powder_ore).blockAmount(RandomAmount.aroundCenter,25,34).attempts(41,54).visiblePlacementAttempts(3).knownBlockLocations(),
				new BlobPopulatorOreScattered(1).block(BlockList.igneous_rock_ore).blockAmount(RandomAmount.aroundCenter,13,23).attempts(24,31).knownBlockLocations(),
				new BlobPopulatorOreScattered(1).block(BlockList.endium_ore).blockAmount(RandomAmount.preferSmaller,4,8).attempts(8,13).visiblePlacementAttempts(4).knownBlockLocations()
			}).setPopulatorAmountProvider(RandomAmount.exact,3,3),
			
			// large blob with a spawner
			new BlobPattern(2).addGenerators(new BlobGenerator[]{
				new BlobGeneratorSingle(1).rad(6D,7.7D),
			}).addPopulators(new BlobPopulator[]{
				new BlobPopulatorEndermanSpawner(1).blockAmount(RandomAmount.aroundCenter,3,6).attempts(12,17).visiblePlacementAttempts(8).knownBlockLocations(),
				/* TODO new BlobPopulatorChest(1).loot(new WeightedLootList(new LootItemStack[]{
					new LootItemStack(ItemList.end_powder).setAmount(3,9).setWeight(10),
					new LootItemStack(ItemList.stardust).setAmount(3,7).setWeight(9),
					new LootItemStack(Items.ender_pearl).setAmount(2,5).setWeight(7),
					new LootItemStack(BlockList.obsidian_special).setAmount(3,10).setDamage(0,2).setWeight(6),
					new LootItemStack(ItemList.endium_ingot).setAmount(1,3).setWeight(5),
					new LootItemStack(Items.iron_ingot).setAmount(1,5).setWeight(5),
					new LootItemStack(Items.redstone).setAmount(2,6).setWeight(4),
					new LootItemStack(Items.gold_ingot).setAmount(1,5).setWeight(4),
					new LootItemStack(ItemList.igneous_rock).setAmount(1,2).setWeight(4),
					new LootItemStack(BlockList.obsidian_special_glow).setAmount(3,10).setDamage(0,2).setWeight(3),
					new LootItemStack(Items.diamond).setAmount(1,3).setWeight(3),
					new LootItemStack(Items.quartz).setAmount(2,7).setWeight(3),
					new LootItemStack(ItemList.music_disk).setDamage(0,ItemMusicDisk.getRecordCount()-1).setWeight(3),
					new LootItemStack(Items.gold_nugget).setAmount(2,8).setWeight(2)
				}),IRandomAmount.preferSmaller,6,10)*/
			/*}).setPopulatorAmountProvider(RandomAmount.exact,2,2)
		});*/
	}
	
	/* TODO private static final IDecoratorGenPass genSmootherPass = new IDecoratorGenPass(){
		private final byte[] airOffX = new byte[]{ -1, 1, 0, 0, 0, 0 },
							 airOffY = new byte[]{ 0, 0, 0, 0, -1, 1 },
							 airOffZ = new byte[]{ 0, 0, -1, 1, 0, 0 };
		
		@Override
		public void run(DecoratorFeatureGenerator gen, List<BlockPosM> blocks){
			for(BlockPosM loc:blocks){
				for(int a = 0, adjacentAir = 0; a < 6; a++){
					if (gen.getBlock(loc.x+airOffX[a],loc.y+airOffY[a],loc.z+airOffZ[a]) == Blocks.air && ++adjacentAir >= 4){
						gen.setBlock(loc.x,loc.y,loc.z,Blocks.air);
						break;
					}
				}
			}
		}
	};*/
	
	@Override
	public boolean generate(World world, Random rand, int x, int y, int z){
		/* TODO BlockPosM tmpPos = BlockPosM.tmp();
		
		if (tmpPos.set(x-7,y,z).getBlock(world) != Blocks.air ||
			tmpPos.set(x+7,y,z).getBlock(world) != Blocks.air ||
			tmpPos.set(x,y,z-7).getBlock(world) != Blocks.air ||
			tmpPos.set(x,y,z+7).getBlock(world) != Blocks.air ||
			tmpPos.set(x,y-7,z).getBlock(world) != Blocks.air ||
			tmpPos.set(x,y+7,z).getBlock(world) != Blocks.air ||
			tmpPos.set(x,y-15,z).getBlock(world) != Blocks.air ||
			tmpPos.set(x,y+15,z).getBlock(world) != Blocks.air)return false;
		
		DecoratorFeatureGenerator gen = new DecoratorFeatureGenerator();
		Pair<BlobGenerator,List<BlobPopulator>> pattern = getBlobType(rand,x,z).patterns.getRandomItem(rand).generatePattern(rand);
		
		pattern.getLeft().generate(gen,rand);
		gen.runPass(genSmootherPass);
		for(BlobPopulator populator:pattern.getRight())populator.generate(gen,rand);
		
		if (gen.getOutOfBoundsCounter() > 6)return false;
		
		gen.generate(world,rand,x,y,z);*/
		return true;
	}
	
	public static final HeeTest $debugTest = new HeeTest(){
		@Override
		public void run(String...args){
			WeightedList<BlobPattern> patterns = new WeightedList<>(new BlobPattern[]{
				new BlobPattern(1).addGenerators(new BlobGenerator[]{
					new BlobGeneratorSingle(1).rad(6D,7.7D),
				}).addPopulators(new BlobPopulator[]{
					new BlobPopulatorEndermanSpawner(1).blockAmount(RandomAmount.aroundCenter,3,6).attempts(12,17).visiblePlacementAttempts(8).knownBlockLocations(),
					/* TODO new BlobPopulatorChest(1).loot(new WeightedLootList(new LootItemStack[]{
						new LootItemStack(ItemList.end_powder).setAmount(3,9).setWeight(10),
						new LootItemStack(ItemList.stardust).setAmount(3,7).setWeight(9),
						new LootItemStack(Items.ender_pearl).setAmount(2,5).setWeight(7),
						new LootItemStack(BlockList.obsidian_special).setAmount(3,10).setDamage(0,2).setWeight(6),
						new LootItemStack(ItemList.endium_ingot).setAmount(1,3).setWeight(5),
						new LootItemStack(Items.iron_ingot).setAmount(1,5).setWeight(5),
						new LootItemStack(Items.redstone).setAmount(2,6).setWeight(4),
						new LootItemStack(Items.gold_ingot).setAmount(1,5).setWeight(4),
						new LootItemStack(ItemList.igneous_rock).setAmount(1,2).setWeight(4),
						new LootItemStack(BlockList.obsidian_special_glow).setAmount(3,10).setDamage(0,2).setWeight(3),
						new LootItemStack(Items.diamond).setAmount(1,3).setWeight(3),
						new LootItemStack(Items.quartz).setAmount(2,7).setWeight(3),
						new LootItemStack(ItemList.music_disk).setDamage(0,ItemMusicDisk.getRecordCount()-1).setWeight(3),
						new LootItemStack(Items.gold_nugget).setAmount(2,8).setWeight(2)
					}),IRandomAmount.preferSmaller,6,10)*/
				}).setPopulatorAmountProvider(RandomAmount.exact,2,2)
			});
			
			/* TODO DecoratorFeatureGenerator gen = new DecoratorFeatureGenerator();
			Pair<BlobGenerator,List<BlobPopulator>> pattern = patterns.getRandomItem(world.rand).generatePattern(world.rand);
			
			Stopwatch.time("WorldGenBlob - test blob generator");
			pattern.getLeft().generate(gen,world.rand);
			Stopwatch.finish("WorldGenBlob - test blob generator");
			
			Stopwatch.time("WorldGenBlob - test smoother pass");
			gen.runPass(genSmootherPass);
			Stopwatch.finish("WorldGenBlob - test smoother pass");
			
			Stopwatch.time("WorldGenBlob - test pattern generator");
			for(BlobPopulator populator:pattern.getRight())populator.generate(gen,world.rand);
			Stopwatch.finish("WorldGenBlob - test pattern generator");
			
			Stopwatch.time("WorldGenBlob - test generate");
			gen.generate(world,world.rand,(int)player.posX+10,(int)player.posY-5,(int)player.posZ);
			Stopwatch.finish("WorldGenBlob - test generate");*/
		}
	};
}
