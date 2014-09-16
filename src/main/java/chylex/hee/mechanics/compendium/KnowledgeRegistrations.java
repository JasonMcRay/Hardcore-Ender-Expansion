package chylex.hee.mechanics.compendium;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import chylex.hee.block.BlockCrossedDecoration;
import chylex.hee.block.BlockEndstoneTerrain;
import chylex.hee.block.BlockList;
import chylex.hee.entity.boss.EntityBossDragon;
import chylex.hee.entity.boss.EntityMiniBossEnderEye;
import chylex.hee.entity.mob.EntityMobAngryEnderman;
import chylex.hee.entity.mob.EntityMobBabyEnderman;
import chylex.hee.entity.mob.EntityMobEnderGuardian;
import chylex.hee.entity.mob.EntityMobFireGolem;
import chylex.hee.entity.mob.EntityMobHauntedMiner;
import chylex.hee.entity.mob.EntityMobInfestedBat;
import chylex.hee.entity.mob.EntityMobLouse;
import chylex.hee.entity.mob.EntityMobScorchingLens;
import chylex.hee.entity.mob.EntityMobVampiricBat;
import chylex.hee.item.ItemList;
import chylex.hee.mechanics.compendium.content.KnowledgeFragment;
import chylex.hee.mechanics.compendium.content.KnowledgeObject;
import chylex.hee.mechanics.compendium.content.KnowledgeObject.LinkedKnowledgeObject;
import chylex.hee.mechanics.compendium.content.fragments.KnowledgeFragmentCrafting;
import chylex.hee.mechanics.compendium.content.fragments.KnowledgeFragmentEnhancement;
import chylex.hee.mechanics.compendium.content.fragments.KnowledgeFragmentItemConversion;
import chylex.hee.mechanics.compendium.content.fragments.KnowledgeFragmentText;
import chylex.hee.mechanics.compendium.objects.IKnowledgeObjectInstance;
import chylex.hee.mechanics.compendium.objects.ObjectBlock;
import chylex.hee.mechanics.compendium.objects.ObjectDummy;
import chylex.hee.mechanics.compendium.objects.ObjectItem;
import chylex.hee.mechanics.compendium.objects.ObjectMob;
import chylex.hee.mechanics.enhancements.types.EnderPearlEnhancements;
import chylex.hee.mechanics.enhancements.types.TNTEnhancements;
import chylex.hee.mechanics.essence.EssenceType;
import chylex.hee.system.logging.Log;
import chylex.hee.system.logging.Stopwatch;

public final class KnowledgeRegistrations{
	public static final KnowledgeObject<? extends IKnowledgeObjectInstance<?>>
		HELP = new KnowledgeObject<>(new ObjectDummy("HalpPlz")),
		
		// ===
		
		STRONGHOLD = dummy("Stronghold",new ItemStack(Blocks.stonebrick),"Stronghold"),
		ADVENTURERS_DIARY = create(ItemList.adventurers_diary),
		ENDERMAN_HEAD = create(ItemList.enderman_head),
		MUSIC_DISKS = create(ItemList.music_disk),
		ALTAR_NEXUS = create(ItemList.altar_nexus),
		BASIC_ESSENCE_ALTAR = create(BlockList.essence_altar,EssenceType.INVALID.id),
		ENDERMAN = create(EntityEnderman.class),
		SILVERFISH = create(EntitySilverfish.class),
		
		ESSENCE = create(ItemList.essence),
		
		// ===
		
		DRAGON_LAIR = dummy("DragonLair",new ItemStack(Blocks.dragon_egg),"Dragon Lair"),
		END_STONE = create(Blocks.end_stone),
		FALLING_OBSIDIAN = create(BlockList.obsidian_falling),
		DRAGON_ESSENCE_ALTAR = create(BlockList.essence_altar,EssenceType.DRAGON.id),
		END_POWDER_ORE = create(BlockList.end_powder_ore),
		ENHANCED_BREWING_STAND = create(ItemList.enhanced_brewing_stand),
		ENHANCED_TNT = create(BlockList.enhanced_tnt),
		DRAGON_EGG = create(Blocks.dragon_egg),
		DRAGON_ESSENCE = link(ESSENCE,new ItemStack(ItemList.essence,EssenceType.DRAGON.getItemDamage()),"Dragon Essence"), // TODO localize
		END_POWDER = create(ItemList.end_powder),
		ENHANCED_ENDER_PEARL = create(ItemList.enhanced_ender_pearl),
		TEMPLE_CALLER = create(ItemList.temple_caller),
		ENDER_DRAGON = create(EntityBossDragon.class),
		ANGRY_ENDERMAN = create(EntityMobAngryEnderman.class),
		VAMPIRE_BAT = create(EntityMobVampiricBat.class),
		
		// ===
		
		ENDSTONE_BLOB = dummy("EndstoneBlob",new ItemStack(Blocks.end_stone),"Endstone Blob"),
		IGNEOUS_ROCK_ORE = create(BlockList.igneous_rock_ore),
		DEATH_FLOWER = create(BlockList.death_flower),
		ENDER_GOO = create(ItemList.bucket_ender_goo),
		IGNEOUS_ROCK = create(ItemList.igneous_rock),
		
		// ===
		
		DUNGEON_TOWER = dummy("DungeonTower",new ItemStack(BlockList.obsidian_special,1),"Dungeon Tower"),
		OBSIDIAN_STAIRS = create(BlockList.obsidian_stairs),
		OBSIDIAN_SMOOTH = create(BlockList.obsidian_special,0),
		OBSIDIAN_CHISELED = create(BlockList.obsidian_special,1),
		OBSIDIAN_PILLAR = create(BlockList.obsidian_special,2),
		OBSIDIAN_SMOOTH_GLOWING = create(BlockList.obsidian_special_glow,0),
		OBSIDIAN_CHISELED_GLOWING = create(BlockList.obsidian_special_glow,1),
		OBSIDIAN_PILLAR_GLOWING = create(BlockList.obsidian_special_glow,2),
		SPATIAL_DASH_GEM = create(ItemList.spatial_dash_gem),
		BIOME_COMPASS = create(ItemList.biome_compass),
		ENDER_EYE = create(EntityMiniBossEnderEye.class),
		ANGRY_ENDERMAN_LINKED = new LinkedKnowledgeObject<>(ANGRY_ENDERMAN),
		
		// ===
		
		METEOROID = dummy("Meteoroid",new ItemStack(BlockList.sphalerite),"Meteoroid"),
		SPHALERITE = create(BlockList.sphalerite,0),
		SPHALERITE_WITH_STARDUST = create(BlockList.sphalerite,1),
		STARDUST = create(ItemList.stardust),
		
		// ===
		
		INSTABILITY_ORB_ORE = create(BlockList.instability_orb_ore),
		STARDUST_ORE = create(BlockList.stardust_ore),
		INSTABILITY_ORB = create(ItemList.instability_orb),
		STARDUST_LINKED = link(STARDUST),
		
		// =>
		
		INFESTED_FOREST_BIOME = dummy("InfestedForest",new ItemStack(BlockList.end_terrain,1,BlockEndstoneTerrain.metaInfested),"Infested Forest Biome"),
		INFESTED_END_STONE = create(BlockList.end_terrain,0),
		INFESTED_GRASS = create(BlockList.crossed_decoration,BlockCrossedDecoration.dataInfestedGrass),
		INFESTED_TALL_GRASS = create(BlockList.crossed_decoration,BlockCrossedDecoration.dataInfestedTallgrass),
		INFESTED_FERN = create(BlockList.crossed_decoration,BlockCrossedDecoration.dataInfestedFern),
		THORNY_BUSH = create(BlockList.crossed_decoration,BlockCrossedDecoration.dataThornBush),
		
		SPOOKY_LOG = create(BlockList.spooky_log),
		SPOOKY_LEAVES = create(BlockList.spooky_leaves),
		DRY_SPLINTER = create(ItemList.dry_splinter),
		GHOST_AMULET = create(ItemList.ghost_amulet),
		ECTOPLASM = create(ItemList.ectoplasm),
		INFESTED_BAT = create(EntityMobInfestedBat.class),
		SILVERFISH_LINKED = link(SILVERFISH),
		
		RAVAGED_DUNGEON = dummy("RavagedDungeon",new ItemStack(BlockList.ravaged_brick),"Ravaged Dungeon"),
		RAVAGED_BRICK = create(BlockList.ravaged_brick),
		RAVAGED_BRICK_GLOWING = create(BlockList.ravaged_brick_glow),
		RAVAGED_BRICK_STAIRS = create(BlockList.ravaged_brick_stairs),
		RAVAGED_BRICK_SLAB = create(BlockList.ravaged_brick_slab),
		RAVAGED_BRICK_FENCE = create(BlockList.ravaged_brick_fence),
		CHARM_POUCH = create(ItemList.charm_pouch),
		RUNES = create(ItemList.rune),
		CHARMS = create(ItemList.charm),
		LOUSE = create(EntityMobLouse.class),
		
		// ===
		
		BURNING_MOUNTAINS_BIOME = dummy("BurningMountains",new ItemStack(BlockList.end_terrain,1,BlockEndstoneTerrain.metaBurned),"Burning Mountains Biome"),
		BURNED_END_STONE = create(BlockList.end_terrain,1),
		LILYFIRE = create(BlockList.crossed_decoration,BlockCrossedDecoration.dataLilyFire),
		
		IGNEOUS_ROCK_ORE_LINKED = link(IGNEOUS_ROCK_ORE),
		IGNEOUS_ROCK_LINKED = link(IGNEOUS_ROCK),
		CINDER = create(BlockList.cinder),
		FIERY_ESSENCE_ALTAR = create(BlockList.essence_altar,EssenceType.FIERY.id),
		FIERY_ESSENCE = link(ESSENCE,new ItemStack(ItemList.essence,EssenceType.FIERY.getItemDamage()),"Fiery Essence"), // TODO localize
		FIRE_GOLEM = create(EntityMobFireGolem.class),
		SCORCHING_LENS = create(EntityMobScorchingLens.class),
		
		FIRE_SHARD = create(ItemList.fire_shard),
		SCORCHING_PICKAXE = create(ItemList.scorching_pickaxe),
		HAUNTED_MINER = create(EntityMobHauntedMiner.class),
		
		// ===
		
		ENCHANTED_ISLAND_BIOME = dummy("EnchantedIsland",new ItemStack(BlockList.end_terrain,1,BlockEndstoneTerrain.metaEnchanted),"Enchanted Island Biome"),
		ENCHANTED_END_STONE = create(BlockList.end_terrain,2),
		
		FALLING_OBSIDIAN_LINKED = link(FALLING_OBSIDIAN),
		ENDERMAN_LINKED = link(ENDERMAN),
		BABY_ENDERMAN = create(EntityMobBabyEnderman.class),
		ENDER_GUARDIAN = create(EntityMobEnderGuardian.class);
	
	public static void initialize(){
		Stopwatch.time("KnowledgeRegistrations");
		
		/*
		 * General information
		 * ===================
		 * Numbers below are not set in stone, they will vary a bit around that value but keep the meaning.
		 * 
		 * Object price
		 * ============
		 *    5 | easily found objects without any feature mechanics
		 *    8 | easily found objects with some mechanics
		 *   10 | basic objects with mechanics
		 *   15 | regular objects with mechanics
		 *   20 | important objects with mechanics
		 *   30 | major objects
		 *   40 | phase objects (early)
		 *   75 | phase objects (mid)
		 *  100 | phase objects (late)
		 * 
		 * Fragment price
		 * ==============
		 *   1 | bulk data
		 *   2 | unimportant or generic information
		 *   3 | semi-important or additional information
		 *   5 | basic information about important objects
		 *   8 | key information
		 *  10 | very important information
		 *
		 * Object discovery reward
		 * =======================
		 * Most objects should give enough points to unlock some/all fragments.
		 * Uncommon and rare objects should give an additional bonus.
		 * Purely visual objects should also give a bonus for discovery.
		 * Objects which are made in a way they should be unlocked with points would only give small reward.
		 */
		
		HELP.setFragments(new KnowledgeFragment[]{
			new KnowledgeFragmentText(0).setContents("Welcome to the Ender Compendium, the source of all knowledge about the End!"),
			new KnowledgeFragmentText(1).setContents("The Compendium is divided into phases, clicking them reveals blocks, items and mobs you can find in that phase."),
			new KnowledgeFragmentText(2).setContents("In order to reveal information about these objects, first you have to either discover them, or spend a specified amount of Knowledge Points."),
			new KnowledgeFragmentText(3).setContents("Then you can spend your points on individual Knowledge Fragments."),
			new KnowledgeFragmentText(4).setContents("Note that discovering objects also unlocks some of their fragments and gives you points, whereas buying the object does neither."),
			new KnowledgeFragmentText(5).setContents("Knowledge Notes are items found in dungeons and traded by villagers. Using them gives you Knowledge Points, but destroys the item in the process."),
			new KnowledgeFragmentText(6).setContents("You can use right mouse button instead of the Back button for easier use of the Compendium.")
		});
		
		ESSENCE.setDiscoveryReward(12).setFragments(new KnowledgeFragment[]{
			new KnowledgeFragmentText(80).setContents("Essence is used to unleash power of altars.").setPrice(2).setUnlockOnDiscovery(),
			new KnowledgeFragmentText(81).setContents("Dragon Essence is gained by killing the Ender Dragon.").setPrice(2), // TODO cascade to Ender Dragon
			new KnowledgeFragmentText(82).setContents("Fiery Essence is dropped by Fire Golem and Scorching Lens, which can be found in a variation of Burning Mountains.").setPrice(2) // TODO cascade to the mobs
		});
		
		// ===
		
		KnowledgeCategories.OVERWORLD.addKnowledgeObjects(new KnowledgeObject[]{
			STRONGHOLD.setPos(0,1).setUnlockPrice(5).setFragments(new KnowledgeFragment[]{
				
			}),
			
			ADVENTURERS_DIARY.setPos(0,0).setUnlockPrice(5).setDiscoveryReward(12).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(10).setContents("Short story of an adventurer, split across 16 pages.").setPrice(2).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(11).setContents("Opening a new diary page unlocks next page of the story, and locks the item to only open that page for other players.").setPrice(2).setUnlockOnDiscovery()
			}),
			
			ENDERMAN_HEAD.setPos(1,0).setUnlockPrice(5).setDiscoveryReward(10).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(20).setContents("Rare drop from Endermen.").setPrice(2).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(21).setContents("Drop chance is 1 in 40 (2.5%), Looting enchantment increases the chance.").setPrice(2).setUnlockRequirements(20)
			}),
			
			MUSIC_DISKS.setPos(2,0).setUnlockPrice(5).setDiscoveryReward(15).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(30).setContents("Jukebox discs with various pieces of qwertygiy's music.").setPrice(2)
			}),
			
			ALTAR_NEXUS.setPos(3,0).setUnlockPrice(10).setDiscoveryReward(5).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(40).setContents("Core component of the Basic Essence Altar.").setPrice(8),
				new KnowledgeFragmentCrafting(41).setRecipeFromRegistry(new ItemStack(ItemList.altar_nexus)).setPrice(5).setUnlockRequirements(40),
				new KnowledgeFragmentCrafting(42).setRecipeFromRegistry(new ItemStack(BlockList.essence_altar)).setPrice(8).setUnlockCascade(50)
			}),
			
			BASIC_ESSENCE_ALTAR.setPos(4,0).setNonBuyable().setDiscoveryReward(20).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentCrafting(50).setRecipeFromRegistry(new ItemStack(BlockList.essence_altar)).setPrice(8).setUnlockCascade(42),
				new KnowledgeFragmentText(51).setContents("Basic altar is converted into a specific type of altar by giving it one Essence, and 8 blocks and items it requests.").setUnlockOnDiscovery().setPrice(5).setUnlockRequirements(50),
				new KnowledgeFragmentText(52).setContents("Transformed altars can be given 32 of Essence per right-click, or 1 while sneaking.").setPrice(2).setUnlockRequirements(51),
				new KnowledgeFragmentText(53).setContents("Altars have 4 sockets for precious blocks in the corners. Some of the blocks give an effect and some boost used effects, one of each is required minimum.").setPrice(6).setUnlockRequirements(51),
				new KnowledgeFragmentText(54).setContents("Redstone Block increases altar speed, Lapis Block improves range and Nether Quartz Block makes Essence usage lower.").setPrice(2).setUnlockRequirements(53),
				new KnowledgeFragmentText(55).setContents("Iron Block has effect boost 1, Gold Block 3, Diamond Block 7 and Emerald Block 10.").setPrice(2).setUnlockRequirements(53)
			}),
			
			ENDERMAN.setPos(5,0).setDiscoveryReward(15).setFragments(new KnowledgeFragment[]{
				// 60
			}),
			
			SILVERFISH.setPos(6,0).setDiscoveryReward(25).setFragments(new KnowledgeFragment[]{
				// 70
			})
		});
		
		// ===
		
		KnowledgeCategories.DRAGON_LAIR.addKnowledgeObjects(new KnowledgeObject[]{
			DRAGON_LAIR.setPos(0,0).setUnlockPrice(45).setFragments(new KnowledgeFragment[]{
				// 80
			}),
			
			END_STONE.setPos(1,0).setUnlockPrice(5).setFragments(new KnowledgeFragment[]{
				// 90
			}),
			
			FALLING_OBSIDIAN.setPos(2,0).setUnlockPrice(8).setDiscoveryReward(15).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(100).setContents("Special variation of Obsidian affected by gravity.").setPrice(2).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(101).setContents("It is primarily found in Dragon Lair and Enchanted Island Biome.").setPrice(2),
				new KnowledgeFragmentText(102).setContents("Falling on a weak block, such as torches or flowers, crushes the block.").setPrice(2).setUnlockRequirements(100),
				new KnowledgeFragmentText(103).setContents("Players and mobs hit by it are severely damaged, up to 30 hearts.").setPrice(2).setUnlockRequirements(100),
				new KnowledgeFragmentText(104).setContents("When broken, it loses its ability to fall.").setPrice(2).setUnlockRequirements(100)
			}),
			
			DRAGON_ESSENCE_ALTAR.setPos(3,0).setUnlockPrice(25).setDiscoveryReward(25).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(110).setContents("Dragon Essence Altar infuses blocks and items with Dragon Essence.").setPrice(5).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(111).setContents("Infusion requires pedestals, which are up to 8 blocks of the same type placed close around the altar").setPrice(8).setUnlockRequirements(110),
				new KnowledgeFragmentText(112).setContents("Using altar sockets to increase range allows up to 12 pedestals to be used.").setPrice(3).setUnlockRequirements(111),
				new KnowledgeFragmentText(113).setContents("Tools, weapons and armor will quickly repair, and slowly improve enchantments and gain new enchantments.").setPrice(5).setUnlockRequirements(110),
				new KnowledgeFragmentText(114).setContents("Some items will turn into different items when infused:").setPrice(4).setUnlockRequirements(110),
				new KnowledgeFragmentItemConversion(115).setItems(new ItemStack(Items.brewing_stand),new ItemStack(ItemList.enhanced_brewing_stand)).setPrice(2).setUnlockRequirements(114),
				new KnowledgeFragmentItemConversion(116).setItems(new ItemStack(Items.ender_eye),new ItemStack(ItemList.temple_caller)).setPrice(2).setUnlockRequirements(114).setUnlockCascade(180)
			}),
			
			END_POWDER_ORE.setPos(4,0).setUnlockPrice(20).setDiscoveryReward(18).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(120).setContents("Commonly found ore in the End.").setPrice(5).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(121).setContents("It spawns clusters spread out across a larger area than other ores.").setPrice(2).setUnlockOnDiscovery().setUnlockRequirements(120),
				new KnowledgeFragmentText(122).setContents("Mining with any pickaxe yields 1-3 End Powder and 2-4 experience.").setPrice(5).setUnlockRequirements(120),
				new KnowledgeFragmentText(123).setContents("Fortune enchantment has an effect on the amount of End Powder dropped.").setPrice(3).setUnlockRequirements(122)
			}),
			
			ENHANCED_BREWING_STAND.setPos(5,0).setUnlockPrice(18).setDiscoveryReward(20).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(130).setContents("A Brewing Stand infused with Dragon Essence.").setPrice(5),
				new KnowledgeFragmentText(131).setContents("The brewing speed depends on potion complexity, simple potions brew much faster than with regular Brewing Stand.").setPrice(2).setUnlockRequirements(130),
				new KnowledgeFragmentText(132).setContents("Using Glowstone, Redstone and Gunpowder requires End Powder, but the potions can go over the limits of basic Brewing Stand.").setPrice(5).setUnlockRequirements(130).setUnlockCascade(166),
				new KnowledgeFragmentText(133).setContents("It is required to brew special potions using Awkward Potion and an ingredient:").setPrice(4).setUnlockRequirements(130),
				new KnowledgeFragmentItemConversion(134).setItems(new ItemStack(ItemList.instability_orb),new ItemStack(ItemList.potion_of_instability)).setPrice(2).setUnlockRequirements(133),
				new KnowledgeFragmentItemConversion(135).setItems(new ItemStack(ItemList.silverfish_blood),new ItemStack(ItemList.infestation_remedy)).setPrice(2).setUnlockRequirements(133)
			}),
			
			ENHANCED_TNT.setPos(6,0).setUnlockPrice(12).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentEnhancement(140).setEnhancement(TNTEnhancements.NO_BLOCK_DAMAGE).setPrice(3),
				new KnowledgeFragmentEnhancement(141).setEnhancement(TNTEnhancements.NO_ENTITY_DAMAGE).setPrice(3),
				new KnowledgeFragmentEnhancement(142).setEnhancement(TNTEnhancements.EXTRA_POWER).setPrice(3),
				new KnowledgeFragmentEnhancement(143).setEnhancement(TNTEnhancements.TRAP).setPrice(3),
				new KnowledgeFragmentEnhancement(144).setEnhancement(TNTEnhancements.NOCLIP).setPrice(3),
				new KnowledgeFragmentEnhancement(145).setEnhancement(TNTEnhancements.FIRE).setPrice(3),
				new KnowledgeFragmentEnhancement(146).setEnhancement(TNTEnhancements.NO_FUSE).setPrice(3)
			}),
			
			DRAGON_EGG.setPos(7,0).setUnlockPrice(10).setDiscoveryReward(15).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(150).setContents("Dragon Egg is created on top of the End Portal after killing the Ender Dragon.").setPrice(5).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(151).setContents("It teleports into random directions when interacted with.").setPrice(2).setUnlockRequirements(150),
				new KnowledgeFragmentText(152).setContents("The egg can only be picked up by sneaking and hitting it with any sword.").setPrice(5).setUnlockRequirements(150)
			}),
			
			DRAGON_ESSENCE.setPos(8,0),
			
			END_POWDER.setPos(9,0).setUnlockPrice(20).setDiscoveryReward(22).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(160).setContents("Magical powder used to enhance items with special effects.").setPrice(2).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(161).setContents("Sneaking and using the End Powder opens enhancement screen.").setPrice(5).setUnlockOnDiscovery().setUnlockRequirements(160),
				new KnowledgeFragmentText(162).setContents("Placing a block or item into the top slot opens all possible enhancements.").setPrice(2).setUnlockRequirements(161),
				new KnowledgeFragmentText(163).setContents("Enhancing requires a specific amount of End Powder and ingredients of one type.").setPrice(2).setUnlockRequirements(162),
				new KnowledgeFragmentText(164).setContents("The ingredient is unlocked by trying. Using an incorrect ingredient may destroy random items in the interface, but may also reveal the correct ingredient.").setPrice(3).setUnlockRequirements(163),
				new KnowledgeFragmentText(165).setContents("Different enhancements can be stacked together.").setPrice(3).setUnlockRequirements(162),
				new KnowledgeFragmentText(166).setContents("End Powder applies enhancing effects to potions in Enhanced Brewing Stand.").setPrice(5).setUnlockRequirements(160).setUnlockCascade(132),
				new KnowledgeFragmentText(167).setContents("Applying End Powder to Death Flower partially reverts the effect of decaying.").setPrice(3) // TODO cascade
			}),
			
			ENHANCED_ENDER_PEARL.setPos(0,1).setUnlockPrice(12).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentEnhancement(170).setEnhancement(EnderPearlEnhancements.NO_FALL_DAMAGE).setPrice(3),
				new KnowledgeFragmentEnhancement(171).setEnhancement(EnderPearlEnhancements.NO_GRAVITY).setPrice(3),
				new KnowledgeFragmentEnhancement(172).setEnhancement(EnderPearlEnhancements.INCREASED_RANGE).setPrice(3),
				new KnowledgeFragmentEnhancement(173).setEnhancement(EnderPearlEnhancements.DOUBLE_SPEED).setPrice(3),
				new KnowledgeFragmentEnhancement(174).setEnhancement(EnderPearlEnhancements.EXPLOSIVE).setPrice(3),
				new KnowledgeFragmentEnhancement(175).setEnhancement(EnderPearlEnhancements.FREEZE).setPrice(3),
				new KnowledgeFragmentEnhancement(176).setEnhancement(EnderPearlEnhancements.RIDING).setPrice(3)
			}),
			
			TEMPLE_CALLER.setPos(1,1).setUnlockPrice(15).setDiscoveryReward(20).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(180).setContents("Temple Caller is an Eye of Ender infused with Dragon Essence.").setPrice(5).setUnlockCascade(116),
				new KnowledgeFragmentText(181).setContents("It can rarely be found in Overworld dungeons, Stronghold and Dungeon Tower.").setPrice(2),
				new KnowledgeFragmentText(182).setContents("Before using the Temple Caller, it has to be filled with Energy.").setPrice(7),
				new KnowledgeFragmentText(183).setContents("Using it in the End with Dragon Egg in the inventory teleports the player to a temple.").setPrice(4).setUnlockRequirements(182),
				new KnowledgeFragmentText(184).setContents("In the temple, there is a dark pedestal for the Dragon Egg. When the Egg is placed, it will use its power to destroy the End.").setPrice(4).setUnlockRequirements(183)
			}),
			
			ENDER_DRAGON.setPos(2,1).setUnlockPrice(20).setFragments(new KnowledgeFragment[]{
				// 190
			}),
			
			ANGRY_ENDERMAN.setPos(3,1).setUnlockPrice(10).setDiscoveryReward(5).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(200).setContents("Startled Enderman that will attack nearby playeres.").setPrice(2).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(201).setContents("They have less health and strength, but will not teleport away when damaged.").setPrice(3).setUnlockRequirements(200)
			}),
			
			VAMPIRE_BAT.setPos(4,1).setUnlockPrice(10).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(210).setContents("Special bat summoned by one of the Ender Dragon attacks.").setPrice(4).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(211).setContents("They instantly die when damaged.").setPrice(2).setUnlockRequirements(210),
				new KnowledgeFragmentText(212).setContents("The bats try to attack players. When they do, they damage the player, heal the Ender Dragon and die.").setPrice(3).setUnlockRequirements(210)
			})
		});
		
		// ===
		
		KnowledgeCategories.ENDSTONE_BLOBS.addKnowledgeObjects(new KnowledgeObject[]{
			ENDSTONE_BLOB.setPos(0,0).setUnlockPrice(40).setFragments(new KnowledgeFragment[]{
				// 220
			}),
			
			IGNEOUS_ROCK_ORE.setPos(1,0).setUnlockPrice(20).setDiscoveryReward(10).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(230).setContents("An ore rarely spawned in Endstone Blobs and commonly in Burning Moutains Biome.").setPrice(5).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(231).setContents("Iron Pickaxe or better can effectively mine the ore.").setPrice(2).setUnlockRequirements(230),
				new KnowledgeFragmentText(232).setContents("Always drops just one Igneous Rock, and 3-5 experience orbs.").setPrice(4).setUnlockRequirements(230)
			}),
			
			DEATH_FLOWER.setPos(2,0).setUnlockPrice(12).setFragments(new KnowledgeFragment[]{
				// 240
			}),
			
			ENDER_GOO.setPos(3,0).setUnlockPrice(10).setDiscoveryReward(12).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(250).setContents("Thick goo found across the End dimension.").setPrice(2).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(251).setContents("The goo heavily limits movement of anything touching it, and it causes Poison, Weakness and Mining Fatigue as well.").setPrice(5).setUnlockRequirements(250),
				new KnowledgeFragmentText(252).setContents("Creatures from the End are unaffected by its effects.").setPrice(2).setUnlockRequirements(251),
				new KnowledgeFragmentText(253).setContents("It aggresively fights water, especially in the End.").setPrice(2).setUnlockRequirements(250)
			}),
			
			IGNEOUS_ROCK.setPos(4,0).setUnlockPrice(12).setDiscoveryReward(15).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(260).setContents("Extremely hot rock dropped by the Igneous Rock Ore.").setPrice(2).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(261).setContents("When held, it has a chance of setting the holder on fire. The effect is strengthened in the Nether and partially suppressed in the End.").setPrice(2).setUnlockRequirements(260),
				new KnowledgeFragmentText(262).setContents("It is a very efficient fuel, roughly 3 times better than Blaze Rods.").setPrice(3).setUnlockRequirements(260),
				new KnowledgeFragmentText(263).setContents("When thrown on the ground, it spreads fire, smelts blocks, burns creatures and causes TNT to instantly explode. The rock also quickly dissolves.").setPrice(3).setUnlockRequirements(260)
			})
		});
		
		// ===
		
		KnowledgeCategories.DUNGEON_TOWER.addKnowledgeObjects(new KnowledgeObject[]{
			DUNGEON_TOWER.setPos(0,0).setUnlockPrice(55).setFragments(new KnowledgeFragment[]{
				// 270
			}),
			
			OBSIDIAN_STAIRS.setPos(1,0).setUnlockPrice(1).setDiscoveryReward(3).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(280).setContents("Very tough stairs made of Obsidian. Uncraftable.").setPrice(2).setUnlockOnDiscovery()
			}),
			
			OBSIDIAN_SMOOTH.setPos(2,0).setUnlockPrice(1).setDiscoveryReward(3).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(281).setContents("Obsidian with a smooth texture, it is easier to break. Uncraftable.").setPrice(1).setUnlockOnDiscovery()
			}),
			
			OBSIDIAN_CHISELED.setPos(3,0).setUnlockPrice(1).setDiscoveryReward(3).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(282).setContents("Obsidian with a chiseled texture, it is easier to break. Uncraftable.").setPrice(1).setUnlockOnDiscovery()
			}),
			
			OBSIDIAN_PILLAR.setPos(4,0).setUnlockPrice(1).setDiscoveryReward(3).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(283).setContents("Obsidian pillar, can be placed horizontally or vertically and it is easier to break. Uncraftable.").setPrice(1).setUnlockOnDiscovery()
			}),
			
			OBSIDIAN_SMOOTH_GLOWING.setPos(5,0).setUnlockPrice(1).setDiscoveryReward(3).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(284).setContents("Glowing Obsidian with a smooth texture, it is easier to break. Uncraftable.").setPrice(1).setUnlockOnDiscovery()
			}),
			
			OBSIDIAN_CHISELED_GLOWING.setPos(6,0).setUnlockPrice(1).setDiscoveryReward(3).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(285).setContents("Glowing Obsidian with a chiseled texture, it is easier to break. Uncraftable.").setPrice(1).setUnlockOnDiscovery()
			}),
			
			OBSIDIAN_PILLAR_GLOWING.setPos(7,0).setUnlockPrice(1).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(286).setContents("Glowing Obsidian pillar, can be placed horizontally or vertically and it is easier to break. Uncraftable.").setPrice(1).setUnlockOnDiscovery()
			}),
			
			SPATIAL_DASH_GEM.setPos(8,0).setUnlockPrice(25).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(290).setContents("Teleportation gem dropped by the Ender Eye.").setPrice(5).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(291).setContents("Using it creates a beam, which teleports the player to a block of mob it hits.").setPrice(2).setUnlockRequirements(290),
				new KnowledgeFragmentText(292).setContents("The beam can travel up to 75 blocks.").setPrice(3).setUnlockRequirements(291),
				new KnowledgeFragmentText(293).setContents("Unlike Ender Pearls, the beam will try to find suitable area to teleport the player to, even if it hits the side or the bottom of block.").setPrice(2).setUnlockRequirements(291),
				new KnowledgeFragmentText(294).setContents("If no suitable area is found, the player may be teleported into nearby blocks and start suffocating.").setPrice(2).setUnlockRequirements(293)
			}),
			
			BIOME_COMPASS.setPos(9,0).setUnlockPrice(25).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(300).setContents("Special compass that points at the nearest Biome Island.").setPrice(5).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(301).setContents("Holding it will show markers for all nearby islands in the dimension.").setPrice(5).setUnlockOnDiscovery().setUnlockRequirements(300),
				new KnowledgeFragmentText(302).setContents("Right-clicking switches between biomes.").setPrice(3).setUnlockRequirements(301),
				new KnowledgeFragmentCrafting(303).setRecipeFromRegistry(new ItemStack(ItemList.biome_compass)).setPrice(8).setUnlockRequirements(300)
			}),
			
			ENDER_EYE.setPos(0,1).setUnlockPrice(20).setFragments(new KnowledgeFragment[]{
				// 310
			}),
			
			ANGRY_ENDERMAN_LINKED.setPos(0,2)
		});
		
		// ===
		
		KnowledgeCategories.METEOROIDS.addKnowledgeObjects(new KnowledgeObject[]{
			METEOROID.setPos(0,0).setUnlockPrice(40).setFragments(new KnowledgeFragment[]{
				// 320
			}),
			
			SPHALERITE.setPos(1,0).setUnlockPrice(10).setDiscoveryReward(12).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(330).setContents("Rock of medium toughness found in Meteoroids.").setPrice(2).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(331).setContents("Stone Pickaxe or better is required to mine it quickly.").setPrice(3).setUnlockOnDiscovery().setUnlockRequirements(330)
			}),
			
			SPHALERITE_WITH_STARDUST.setPos(2,0).setUnlockPrice(10).setDiscoveryReward(10).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(340).setContents("Variation of Sphalerite, that has yellow marks on the surface.").setPrice(2).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(341).setContents("It drops 1-3 Stardust, but no experience unlike the ores.").setPrice(3).setUnlockRequirements(340),
				new KnowledgeFragmentText(342).setContents("Fortune enchantment has a small effect on the drops.").setPrice(2).setUnlockRequirements(341)
			}),
			
			STARDUST.setPos(3,0).setUnlockPrice(10).setDiscoveryReward(8).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(350).setContents("Dust dropped by Stardust Ore and Sphalerite with Stardust. It is used for crafting and as a decomposition catalyst in Tables.").setPrice(2).setUnlockOnDiscovery()
			})
		});
		
		// ===
		
		INSTABILITY_ORB_ORE.setPos(0,0).setUnlockPrice(15).setDiscoveryReward(14).setFragments(new KnowledgeFragment[]{
			new KnowledgeFragmentText(360).setContents("Uncommon ore found in tiny clusters in all Biome Islands.").setPrice(5).setUnlockOnDiscovery(),
			new KnowledgeFragmentText(361).setContents("It can be only mined with Diamond Pickaxe or better.").setPrice(2).setUnlockRequirements(360),
			new KnowledgeFragmentText(362).setContents("The ore has a 40% chance of dropping Instability Orb.").setPrice(2).setUnlockRequirements(360),
			new KnowledgeFragmentText(363).setContents("Each level of Fortune enchantment increases the drop chance by 4%.").setPrice(3).setUnlockRequirements(362)
		});
		
		STARDUST_ORE.setPos(1,0).setUnlockPrice(15).setDiscoveryReward(12).setFragments(new KnowledgeFragment[]{
			new KnowledgeFragmentText(370).setContents("Ore found commonly in Biome Islands.").setPrice(5).setUnlockOnDiscovery(),
			new KnowledgeFragmentText(371).setContents("Diamond Pickaxe or better is required to get any drops.").setPrice(2).setUnlockRequirements(370),
			new KnowledgeFragmentText(372).setContents("One ore drops 0-4 Stardust and 6-9 experience orbs.").setPrice(2).setUnlockRequirements(371),
			new KnowledgeFragmentText(373).setContents("Fortune enchantment does not affect the drops.").setPrice(3).setUnlockRequirements(372)
		});
		
		INSTABILITY_ORB.setPos(2,0).setUnlockPrice(15).setFragments(new KnowledgeFragment[]{
			new KnowledgeFragmentText(380).setContents("Very unstable material dropped by Instability Orb Ore.").setPrice(5).setUnlockOnDiscovery(),
			new KnowledgeFragmentText(381).setContents("It decomposes when thrown on the ground. When the process finishes, it can either explode, or turn into a random mob, block or item.").setPrice(5).setUnlockRequirements(380),
			new KnowledgeFragmentText(382).setContents("When a TNT explodes near one or more decomposing orbs, they will not explode once the decomposition is complete.").setPrice(3).setUnlockRequirements(381),
			new KnowledgeFragmentText(383).setContents("Brewing it in an Enhanced Brewing Stand creates Potion of Instability.").setPrice(3).setUnlockRequirements(380) // TODO cascade
		});
		
		STARDUST_LINKED.setPos(3,0);
		
		// ===
		
		KnowledgeCategories.BIOME_ISLAND_FOREST.addKnowledgeObjects(new KnowledgeObject[]{
			INSTABILITY_ORB_ORE, STARDUST_ORE, INSTABILITY_ORB, STARDUST_LINKED,
			
			INFESTED_FOREST_BIOME.setPos(0,0).setUnlockPrice(60).setFragments(new KnowledgeFragment[]{
				// 390
			}),
			
			INFESTED_END_STONE.setPos(1,0).setUnlockPrice(5).setDiscoveryReward(8).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(400).setContents("Variation of End Stone found in the Infested Forest Biome.").setPrice(2).setUnlockOnDiscovery()
			}),
			
			INFESTED_GRASS.setPos(2,0).setUnlockPrice(2).setDiscoveryReward(3).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(410).setContents("One of the plants commonly found in the Infested Forest Biome.").setPrice(2).setUnlockOnDiscovery()
			}),
			
			INFESTED_TALL_GRASS.setPos(3,0).setUnlockPrice(2).setDiscoveryReward(3).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(411).setContents("One of the plants commonly found in the Infested Forest Biome.").setPrice(2).setUnlockOnDiscovery()
			}),
			
			INFESTED_FERN.setPos(4,0).setUnlockPrice(2).setDiscoveryReward(3).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(412).setContents("One of the plants commonly found in the Infested Forest Biome.").setPrice(2).setUnlockOnDiscovery()
			}),
			
			THORNY_BUSH.setPos(5,0).setUnlockPrice(5).setDiscoveryReward(5).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(420).setContents("Dark plant found in the Infested Forest (Deep).").setPrice(2).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(421).setContents("On contact, it causes short Poison effect.").setPrice(3).setUnlockRequirements(420)
			}),
			
			// =
			
			SPOOKY_LOG.setPos(6,0).setUnlockPrice(15).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(430).setContents("Logs used for the trunk of Spooky Trees.").setPrice(5).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(431).setContents("Breaking one log also destroys all logs above.").setPrice(3).setUnlockRequirements(430),
				new KnowledgeFragmentText(432).setContents("Each log has a 1 in 8 (12.5%) chance of dropping a Dry Splinter.").setPrice(3).setUnlockRequirements(430).setUnlockCascade(451),
				new KnowledgeFragmentText(433).setContents("In the Deep variation of Infested Forest, some trees have faces.").setPrice(2).setUnlockRequirements(430),
				new KnowledgeFragmentText(434).setContents("When nobody looks at a face, it will move around the tree or move to another nearby tree.").setPrice(3).setUnlockRequirements(433),
				new KnowledgeFragmentText(435).setContents("If a log with a face is broken, it has a 1 in 4 (25%) chance of spawning a Forest Ghost.").setPrice(2).setUnlockRequirements(433),
				new KnowledgeFragmentText(436).setContents("Ghost Amulet will stop the Forest Ghost from spawning, and the face will have a 3 in 5 (60%) chance to drop Ectoplasm.").setPrice(8).setUnlockRequirements(435).setUnlockCascade(461),
				new KnowledgeFragmentCrafting(437).setRecipeFromRegistry(new ItemStack(BlockList.spooky_log)).setPrice(5).setUnlockRequirements(430).setUnlockCascade(452)
			}),
			
			SPOOKY_LEAVES.setPos(7,0).setUnlockPrice(10).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(440).setContents("Spooky Leaves are foliage of the Spooky Trees.").setPrice(4).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(441).setContents("If not connected to a Spooky Log, they leaves very quickly decay.").setPrice(3).setUnlockRequirements(440),
				new KnowledgeFragmentCrafting(442).setRecipeFromRegistry(new ItemStack(BlockList.spooky_leaves)).setPrice(5).setUnlockRequirements(440).setUnlockCascade(453)
			}),
			
			DRY_SPLINTER.setPos(8,0).setUnlockPrice(8).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(450).setContents("Crafting material dropped by Spooky Logs.").setPrice(2).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(451).setContents("Each Spooky Log has 1 in 8 (12.5%) chance of dropping a Dry Splinter.").setPrice(3).setUnlockRequirements(450).setUnlockCascade(432),
				new KnowledgeFragmentCrafting(452).setRecipeFromRegistry(new ItemStack(BlockList.spooky_log)).setPrice(5).setUnlockRequirements(450).setUnlockCascade(437),
				new KnowledgeFragmentCrafting(453).setRecipeFromRegistry(new ItemStack(BlockList.spooky_leaves)).setPrice(5).setUnlockRequirements(450).setUnlockCascade(442)
			}),
			
			GHOST_AMULET.setPos(9,0).setUnlockPrice(18).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(460).setContents("An amulet that banishes the Forest Ghost.").setPrice(5).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(461).setContents("When in the inventory, Forest Ghost will not spawn and the Spooky Log face will have 3 in 5 (60%) chance to drop Ectoplasm.").setPrice(8).setUnlockRequirements(460).setUnlockCascade(436),
				new KnowledgeFragmentText(462).setContents("In order to create it, one piece of End Powder, Emerald and String all have to be thrown into Ender Goo.").setPrice(8).setUnlockRequirements(460)
			}),
			
			ECTOPLASM.setPos(0,1).setUnlockPrice(25).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(470).setContents("Strange ethereal substance dropped by banished Forest Ghosts.").setPrice(5).setUnlockOnDiscovery()
			}),
			
			INFESTED_BAT.setPos(1,1).setUnlockPrice(8).setDiscoveryReward(10).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(480).setContents("Special type of bat that spawns in the Infested Forest Biome. Each bat varies in size.").setPrice(2).setUnlockOnDiscovery()
			}),
			
			SILVERFISH_LINKED.setPos(2,1),
			
			// =
			
			RAVAGED_DUNGEON.setPos(0,5).setUnlockPrice(25).setFragments(new KnowledgeFragment[]{
				// 490
			}),
			
			RAVAGED_BRICK.setPos(1,5).setUnlockPrice(2).setDiscoveryReward(8).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(500).setContents("Primary building block of the Ravaged Dungeon.").setPrice(2).setUnlockOnDiscovery(),
				new KnowledgeFragmentText(501).setContents("Some of the bricks are cracked or damaged.").setPrice(2).setUnlockOnDiscovery(),
				new KnowledgeFragmentCrafting(502).setRecipeFromRegistry(new ItemStack(BlockList.ravaged_brick_stairs,4)).setPrice(2).setUnlockRequirements(500).setUnlockCascade(513),
				new KnowledgeFragmentCrafting(503).setRecipeFromRegistry(new ItemStack(BlockList.ravaged_brick_slab,6)).setPrice(2).setUnlockRequirements(500).setUnlockCascade(515),
				new KnowledgeFragmentCrafting(505).setRecipeFromRegistry(new ItemStack(BlockList.ravaged_brick_fence,6)).setPrice(2).setUnlockRequirements(500).setUnlockCascade(517)
			}),
			
			RAVAGED_BRICK_GLOWING.setPos(2,5).setUnlockPrice(2).setDiscoveryReward(5).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(510).setContents("Glowing variation of the Ravaged Brick. It has the same level as Glowstone. Uncraftable.").setPrice(2).setUnlockOnDiscovery()
			}),
			
			RAVAGED_BRICK_STAIRS.setPos(3,5).setUnlockPrice(2).setDiscoveryReward(5).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(512).setContents("Stairs made of Ravaged Brick, slightly weaker than the full block.").setPrice(2).setUnlockOnDiscovery(),
				new KnowledgeFragmentCrafting(513).setRecipeFromRegistry(new ItemStack(BlockList.ravaged_brick_stairs,4)).setPrice(2).setUnlockRequirements(512).setUnlockCascade(502)
			}),
			
			RAVAGED_BRICK_SLAB.setPos(4,5).setUnlockPrice(2).setDiscoveryReward(5).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(514).setContents("Slab made of Ravaged Brick, weaker than the full block.").setPrice(2).setUnlockOnDiscovery(),
				new KnowledgeFragmentCrafting(515).setRecipeFromRegistry(new ItemStack(BlockList.ravaged_brick_slab,6)).setPrice(2).setUnlockRequirements(514).setUnlockCascade(503)
			}),
			
			RAVAGED_BRICK_FENCE.setPos(5,5).setUnlockPrice(2).setDiscoveryReward(5).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(516).setContents("Fence made of Ravaged Brick, weaker than the full block.").setPrice(2).setUnlockOnDiscovery(),
				new KnowledgeFragmentCrafting(517).setRecipeFromRegistry(new ItemStack(BlockList.ravaged_brick_fence,6)).setPrice(2).setUnlockRequirements(516).setUnlockCascade(504)
			}),
			
			CHARM_POUCH.setPos(6,5).setUnlockPrice(30).setFragments(new KnowledgeFragment[]{
				// 520
			}),
			
			RUNES.setPos(7,5).setUnlockPrice(20).setFragments(new KnowledgeFragment[]{
				// 530
			}),
			
			CHARMS.setPos(8,5).setUnlockPrice(35).setFragments(new KnowledgeFragment[]{
				// 540
			}),
			
			LOUSE.setPos(9,5).setUnlockPrice(15).setFragments(new KnowledgeFragment[]{
				// 550
			})
		});
		
		KnowledgeCategories.BIOME_ISLAND_MOUNTAINS.addKnowledgeObjects(new KnowledgeObject[]{
			INSTABILITY_ORB_ORE, STARDUST_ORE, INSTABILITY_ORB, STARDUST_LINKED,
			
			BURNING_MOUNTAINS_BIOME.setPos(0,0).setUnlockPrice(60).setFragments(new KnowledgeFragment[]{
				// 560
			}),
			
			BURNED_END_STONE.setPos(1,0).setUnlockPrice(5).setDiscoveryReward(8).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(570).setContents("Variation of End Stone found in the Burning Mountains Biome.").setPrice(2).setUnlockOnDiscovery()
			}),
			
			LILYFIRE.setPos(2,0).setUnlockPrice(4).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(580).setContents("Orange tulip found in the Burning Mountains (Scorching).").setPrice(2).setUnlockOnDiscovery(),
				new KnowledgeFragmentCrafting(581).setCustomRecipe(new ItemStack(Items.dye,2,14),new ItemStack[]{ new ItemStack(BlockList.crossed_decoration,1,BlockCrossedDecoration.dataLilyFire) }).setPrice(3).setUnlockRequirements(580)
			}),
			
			// =
			
			IGNEOUS_ROCK_ORE_LINKED.setPos(3,0),
			
			IGNEOUS_ROCK_LINKED.setPos(4,0),
			
			CINDER.setPos(5,0).setUnlockPrice(5).setFragments(new KnowledgeFragment[]{
				// 590
			}),
			
			FIERY_ESSENCE_ALTAR.setPos(6,0).setUnlockPrice(25).setFragments(new KnowledgeFragment[]{
				// 600
			}),
			
			FIERY_ESSENCE.setPos(7,0),
			
			FIRE_GOLEM.setPos(8,0).setUnlockPrice(20).setFragments(new KnowledgeFragment[]{
				// 610
			}),
			
			SCORCHING_LENS.setPos(9,0).setUnlockPrice(20).setFragments(new KnowledgeFragment[]{
				// 620
			}),
			
			// =
			
			FIRE_SHARD.setPos(0,1).setUnlockPrice(15).setFragments(new KnowledgeFragment[]{
				// 630
			}),
			
			SCORCHING_PICKAXE.setPos(1,1).setUnlockPrice(30).setFragments(new KnowledgeFragment[]{
				// 640
			}),
			
			HAUNTED_MINER.setPos(2,1).setUnlockPrice(20).setFragments(new KnowledgeFragment[]{
				// 650
			})
		});
		
		KnowledgeCategories.BIOME_ISLAND_ENCHISLAND.addKnowledgeObjects(new KnowledgeObject[]{
			INSTABILITY_ORB_ORE, STARDUST_ORE, INSTABILITY_ORB, STARDUST_LINKED,
			
			ENCHANTED_ISLAND_BIOME.setPos(0,0).setUnlockPrice(60).setFragments(new KnowledgeFragment[]{
				// 660
			}),
			
			ENCHANTED_END_STONE.setPos(1,0).setUnlockPrice(5).setDiscoveryReward(8).setFragments(new KnowledgeFragment[]{
				new KnowledgeFragmentText(670).setContents("Variation of End Stone found in the Enchanted Island Biome.").setPrice(2).setUnlockOnDiscovery()
			}),
			
			FALLING_OBSIDIAN_LINKED.setPos(2,0),
			
			ENDERMAN_LINKED.setPos(3,0),
			
			BABY_ENDERMAN.setPos(4,0).setUnlockPrice(20).setFragments(new KnowledgeFragment[]{
				// 680
			}),
			
			ENDER_GUARDIAN.setPos(5,0).setUnlockPrice(15).setFragments(new KnowledgeFragment[]{
				// 690
			})
		});
		
		Stopwatch.finish("KnowledgeRegistrations");
		
		if (Log.isDebugEnabled()){
			Stopwatch.time("KnowledgeRegistrations - Stats");
			
			int amtObjects = 0, amtFragments = 0, totalObjPrice = 0, totalFragPrice = 0, totalReward = 0;
			
			for(KnowledgeObject<?> obj:KnowledgeObject.getAllObjects()){
				if (obj.getDiscoveryReward() == 0 || obj.getUnlockPrice() == 0)throw new IllegalStateException("Knowledge Object "+obj.globalID+"/"+obj.getTooltip()+" has illegal reward or unlock price.");
				
				for(KnowledgeFragment fragment:obj.getFragments()){
					totalFragPrice += fragment.getPrice();
					if (fragment.getPrice() == 0)throw new IllegalStateException("Knowledge Fragment "+fragment.globalID+" has illegal unlock price.");
				}
				
				++amtObjects;
				amtFragments += obj.getFragments().size();
				totalObjPrice += obj.getUnlockPrice();
				totalReward += obj.getDiscoveryReward();
			}
			
			Log.debug("Knowledge Object amount: $0",amtObjects);
			Log.debug("Knowledge Fragment amount: $0",amtFragments);
			Log.debug("Total Object price: $0",totalObjPrice);
			Log.debug("Total Fragment price: $0",totalFragPrice);
			Log.debug("Total price: $0",totalObjPrice+totalFragPrice);
			Log.debug("Total discovery reward: $0",totalReward);
			
			Stopwatch.finish("KnowledgeRegistrations - Stats");
		}
	}
	
	public static KnowledgeObject<ObjectBlock> create(Block block){
		return new KnowledgeObject<ObjectBlock>(new ObjectBlock(block));
	}
	
	public static KnowledgeObject<ObjectBlock> create(Block block, int metadata){
		return new KnowledgeObject<ObjectBlock>(new ObjectBlock(block,metadata));
	}
	
	public static KnowledgeObject<ObjectItem> create(Item item){
		return new KnowledgeObject<ObjectItem>(new ObjectItem(item));
	}
	
	public static KnowledgeObject<ObjectMob> create(Class<? extends EntityLiving> mobClass){
		return new KnowledgeObject<ObjectMob>(new ObjectMob(mobClass));
	}
	
	public static KnowledgeObject<ObjectDummy> dummy(String identifier, ItemStack itemToRender, String tooltip){
		return new KnowledgeObject<ObjectDummy>(new ObjectDummy(identifier),itemToRender,tooltip);
	}
	
	public static KnowledgeObject<? extends IKnowledgeObjectInstance<?>> link(KnowledgeObject<? extends IKnowledgeObjectInstance<?>> object){
		return new LinkedKnowledgeObject<>(object);
	}
	
	public static KnowledgeObject<? extends IKnowledgeObjectInstance<?>> link(KnowledgeObject<? extends IKnowledgeObjectInstance<?>> object, ItemStack itemToRender, String tooltip){
		return new LinkedKnowledgeObject<>(object,itemToRender,tooltip);
	}
	
	private KnowledgeRegistrations(){}
}