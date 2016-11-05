package chylex.hee.world.feature.stronghold;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import chylex.hee.init.BlockList;
import chylex.hee.system.abstractions.BlockInfo;
import chylex.hee.system.abstractions.Meta;
import chylex.hee.system.abstractions.facing.Facing4;
import chylex.hee.system.util.MathUtil;
import chylex.hee.world.feature.WorldGenStronghold;
import chylex.hee.world.loot.WeightedLootTable;
import chylex.hee.world.structure.dungeon.StructureDungeonPiece;
import chylex.hee.world.structure.dungeon.generators.DungeonGeneratorSpreading.ISpreadingGeneratorPieceType;
import chylex.hee.world.structure.util.IBlockPicker;
import chylex.hee.world.structure.util.IStructureTileEntity;
import chylex.hee.world.util.RandomAmount;
import chylex.hee.world.util.Size;

public abstract class StrongholdPiece extends StructureDungeonPiece{
	protected enum Type implements ISpreadingGeneratorPieceType{
		CORRIDOR, DOOR, ROOM, DEADEND;
		
		@Override
		public boolean isRoom(){
			return this == ROOM || this == DEADEND; // the spreading generator cannot create deadends, so make a couple of them intentionally
		}
		
		@Override
		public boolean isDoor(){
			return this == DOOR;
		}
	}
	
	protected static final IConnectWith fromRoom = type -> type == Type.CORRIDOR || type == Type.DOOR;
	protected static final IConnectWith fromDeadEnd = type -> type == Type.CORRIDOR;
	protected static final IConnectWith fromDoor = type -> true;
	protected static final IConnectWith withAnything = type -> true;
	
	private static final BlockInfo[] blocksStoneBrick = new BlockInfo[]{
		new BlockInfo(Blocks.stonebrick, Meta.stoneBrickPlain),
		new BlockInfo(Blocks.stonebrick, Meta.stoneBrickMossy),
		new BlockInfo(Blocks.stonebrick, Meta.stoneBrickCracked),
		new BlockInfo(Blocks.monster_egg, Meta.silverfishPlain),
		new BlockInfo(Blocks.monster_egg, Meta.silverfishMossy),
		new BlockInfo(Blocks.monster_egg, Meta.silverfishCracked)
	};
	
	protected static final IBlockPicker placeStoneBrick = rand -> {
		int chance = rand.nextInt(100);
		
		if (chance < 45)return blocksStoneBrick[0];
		else if (chance < 75)return blocksStoneBrick[1];
		else if (chance < 95)return blocksStoneBrick[2];
		else{
			chance = rand.nextInt(100);
			
			if (chance < 47)return blocksStoneBrick[3];
			else if (chance < 79)return blocksStoneBrick[4];
			else return blocksStoneBrick[5];
		}
	};
	
	protected static final IBlockPicker placeStoneBrickPlain = blocksStoneBrick[0];
	protected static final IBlockPicker placeStoneBrickChiseled = new BlockInfo(Blocks.stonebrick, Meta.stoneBrickChiseled);
	
	protected static final IBlockPicker placeStoneBrickStairs(Facing4 ascendsTowards, boolean flip){
		return new BlockInfo(Blocks.stone_brick_stairs, Meta.getStairs(ascendsTowards, flip));
	}
	
	private static final void generateLoot(WeightedLootTable lootTable, int items, int cobwebs, TileEntityChest chest, Random rand){
		while(items-- > 0){
			chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), lootTable.generateWeighted(null, rand));
		}
		
		for(int slot; cobwebs > 0; cobwebs--){
			slot = rand.nextInt(chest.getSizeInventory());
			if (chest.getStackInSlot(slot) == null)chest.setInventorySlotContents(slot, new ItemStack(rand.nextInt(3) == 0 ? BlockList.ancient_web : Blocks.web));
		}
	}
	
	protected static final IStructureTileEntity generateLootGeneral = (tile, rand) -> {
		generateLoot(WorldGenStronghold.lootGeneral, RandomAmount.aroundCenter.generate(rand, 3, 10), rand.nextInt(7), (TileEntityChest)tile, rand);
	};
	
	protected static final IStructureTileEntity generateLootLibraryMain = (tile, rand) -> {
		generateLoot(WorldGenStronghold.lootLibrary, RandomAmount.linear.generate(rand, 9, 11), 2+rand.nextInt(2), (TileEntityChest)tile, rand);
	};
	
	protected static final IStructureTileEntity generateLootLibrarySecondary = (tile, rand) -> {
		generateLoot(WorldGenStronghold.lootLibrary, RandomAmount.aroundCenter.generate(rand, 4, 6), 4+rand.nextInt(3), (TileEntityChest)tile, rand);
	};
	
	public StrongholdPiece(Type type, Size size){
		super(type, size);
	}
	
	/**
	 * Determines the multiplier of calculated weight. Using the default settings:<br>
	 * - corridors with several connections get higher increase than rooms, to encourage interesting paths<br>
	 * - rooms have higher importance than corridors, but have a gentler curve with increasing connections
	 */
	@Override
	public final int calculateInstWeight(int availableConnections){
		return MathUtil.ceil(Math.pow(availableConnections, getWeightFactor())*getWeightMultiplier());
	}
	
	protected float getWeightFactor(){
		return type == Type.ROOM ? 1.5F : type == Type.CORRIDOR ? 2.5F : 1F;
	}
	
	protected float getWeightMultiplier(){
		return type == Type.ROOM ? 2F : type == Type.CORRIDOR ? 1F : 0F;
	}
}
