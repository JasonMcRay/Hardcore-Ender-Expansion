package chylex.hee.world.feature.stronghold.corridors;
import java.util.Random;
import chylex.hee.system.abstractions.facing.Facing4;
import chylex.hee.world.feature.stronghold.StrongholdPiece;
import chylex.hee.world.structure.StructureWorld;
import chylex.hee.world.structure.dungeon.StructureDungeonPieceInst;
import chylex.hee.world.util.Size;

public class StrongholdCorridorStraight extends StrongholdPiece{
	public static StrongholdCorridorStraight[] generateCorridors(int...lengths){
		StrongholdCorridorStraight[] corridors = new StrongholdCorridorStraight[lengths.length*2];
		
		for(int index = 0; index < lengths.length; index++){
			corridors[index*2] = new StrongholdCorridorStraight(false, lengths[index]);
			corridors[index*2+1] = new StrongholdCorridorStraight(true, lengths[index]);
		}
		
		return corridors;
	}
	
	private final boolean dirX;
	
	private StrongholdCorridorStraight(boolean dirX, int length){
		super(Type.CORRIDOR, new Size(dirX ? length : 5, 5, dirX ? 5 : length));
		
		if (dirX){
			addConnection(Facing4.EAST_POSX, length-1, 0, 2, withAnything);
			addConnection(Facing4.WEST_NEGX, 0, 0, 2, withAnything);
		}
		else{
			addConnection(Facing4.NORTH_NEGZ, 2, 0, 0, withAnything);
			addConnection(Facing4.SOUTH_POSZ, 2, 0, length-1, withAnything);
		}
		
		this.dirX = dirX;
	}

	@Override
	public void generate(StructureDungeonPieceInst inst, StructureWorld world, Random rand, final int x, final int y, final int z){
		placeCube(world, rand, placeStoneBrick, x, y, z, x+maxX, y, z+maxZ);
		placeCube(world, rand, placeStoneBrick, x, y+maxY, z, x+maxX, y+maxY, z+maxZ);
		
		if (dirX){
			placeLine(world, rand, placeStoneBrick, x, y+1, z, x+maxX, y+maxY-1, z);
			placeLine(world, rand, placeStoneBrick, x, y+1, z+maxZ, x+maxX, y+maxY-1, z+maxZ);
			if (inst.isConnectionFree(Facing4.EAST_POSX))placeLine(world, rand, placeStoneBrick, x+maxX, y+1, z+1, x+maxX, y+maxY-1, z+maxZ-1);
			if (inst.isConnectionFree(Facing4.WEST_NEGX))placeLine(world, rand, placeStoneBrick, x, y+1, z+1, x, y+maxY-1, z+maxZ-1);
		}
		else{
			placeLine(world, rand, placeStoneBrick, x, y+1, z, x, y+maxY-1, z+maxZ);
			placeLine(world, rand, placeStoneBrick, x+maxX, y+1, z, x+maxX, y+maxY-1, z+maxZ);
			if (inst.isConnectionFree(Facing4.NORTH_NEGZ))placeLine(world, rand, placeStoneBrick, x+1, y+1, z, x+maxX-1, y+maxY-1, z);
			if (inst.isConnectionFree(Facing4.SOUTH_POSZ))placeLine(world, rand, placeStoneBrick, x+1, y+1, z+maxZ, x+maxX-1, y+maxY-1, z+maxZ);
		}
	}
}
