package chylex.hee.world.structure.dungeon.generators;
import gnu.trove.impl.Constants;
import gnu.trove.map.hash.TObjectIntHashMap;
import java.util.Random;
import chylex.hee.system.abstractions.Pos;
import chylex.hee.system.collections.CollectionUtil;
import chylex.hee.system.collections.weight.WeightedList;
import chylex.hee.world.structure.StructureWorld;
import chylex.hee.world.structure.dungeon.StructureDungeon;
import chylex.hee.world.structure.dungeon.StructureDungeonGenerator;
import chylex.hee.world.structure.dungeon.StructureDungeonPiece;
import chylex.hee.world.structure.dungeon.StructureDungeonPiece.Connection;
import chylex.hee.world.structure.dungeon.StructureDungeonPieceArray;
import chylex.hee.world.structure.dungeon.StructureDungeonPieceInst;

/**
 * Generates a dungeon by choosing a random existing piece and then trying to attach another random piece to it until it has enough pieces or runs out.
 * Piece range is used to limit the total amount of pieces in the dungeon.
 */
public class DungeonGeneratorAttaching extends StructureDungeonGenerator{
	protected final WeightedList<StructureDungeonPieceArray> available;
	protected final TObjectIntHashMap<StructureDungeonPieceArray> generatedCount;
	
	private int cycleAttempts = 1000;
	private int placeAttempts = 20;
	
	public DungeonGeneratorAttaching(StructureDungeon<?> dungeon){
		super(dungeon);
		this.available = new WeightedList<>(dungeon.pieces);
		this.generatedCount = new TObjectIntHashMap<>(dungeon.pieces.size(), Constants.DEFAULT_LOAD_FACTOR, 0);
	}
	
	/**
	 * Generates the dungeon.
	 */
	@Override
	public boolean generate(StructureWorld world, Random rand){
		int targetAmount = dungeon.getPieceAmountRange().random(rand);
		
		generateStartPiece(rand);
		
		if (generated.size() < targetAmount && generated.getTotalWeight() > 0){
			for(int cycleAttempt = 0; cycleAttempt < cycleAttempts; cycleAttempt++){
				StructureDungeonPieceArray nextArray = selectNextPiece(rand);
				if (nextArray == null)break;
				
				for(StructureDungeonPiece nextPiece:CollectionUtil.shuffled(nextArray.toList(), rand)){
					if (tryGeneratePiece(nextPiece, rand)){
						if (generated.size() >= targetAmount)cycleAttempt = Integer.MAX_VALUE-1;
						break;
					}
				}
			}
		}
		
		if (!dungeon.getPieceAmountRange().in(generated.size()))return false;
		if (!dungeon.pieces.stream().allMatch(array -> array.amount.in(generatedCount.get(array))))return false;
		
		for(StructureDungeonPieceInst pieceInst:generated){
			pieceInst.clearArea(world, rand);
			pieceInst.generatePiece(world, rand);
		}
		
		return true;
	}
	
	/**
	 * Returns a random piece out of the available piece list, or null if there are no pieces available.
	 */
	@Override
	protected StructureDungeonPieceArray selectNextPiece(Random rand){
		return available.getRandomItem(rand);
	}
	
	/**
	 * Adds a new piece to the structure and updates all collections.
	 */
	@Override
	protected StructureDungeonPieceInst addPiece(StructureDungeonPiece piece, Pos position){
		StructureDungeonPieceInst inst = super.addPiece(piece, position);
		StructureDungeonPieceArray parentArray = piece.getParentArray();
		if (generatedCount.adjustOrPutValue(parentArray, 1, 1) >= parentArray.amount.max)available.remove(parentArray);
		return inst;
	}
	
	/**
	 * Tries to attach a piece to a random existing one.
	 */
	private boolean tryGeneratePiece(StructureDungeonPiece piece, Random rand){
		Connection sourceConnection = piece.getRandomConnection(rand);
		
		for(int placeAttempt = 0; placeAttempt < placeAttempts; placeAttempt++){
			StructureDungeonPieceInst target = generated.getRandomItem(rand);
			if (target == null)break;
			
			for(Connection targetConnection:CollectionUtil.shuffled(target.findConnections(sourceConnection.facing, piece.type), rand)){
				if (tryConnectPieces(piece, sourceConnection, target, targetConnection))return true;
			}
		}
		
		return false;
	}
}
