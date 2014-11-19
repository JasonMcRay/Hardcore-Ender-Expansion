package chylex.hee.world.structure.island.biome.feature.island.laboratory;
import gnu.trove.map.hash.TByteByteHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.util.Direction;
import chylex.hee.system.logging.Log;
import chylex.hee.world.structure.island.ComponentIsland;
import chylex.hee.world.structure.util.pregen.LargeStructureWorld;

public final class LaboratoryPlan{
	final List<LaboratoryElement> elements = new ArrayList<>();
	final List<LaboratoryElement> roomElements = new ArrayList<>();
	private int score;
	
	public boolean generate(LargeStructureWorld world, Random rand){
		roomElements.clear();
		elements.clear();
		
		int x, z;
		
		for(int locAttempt = 0; locAttempt < 420; locAttempt++){
			x = (rand.nextInt(ComponentIsland.size)-ComponentIsland.halfSize)*3/4;
			z = (rand.nextInt(ComponentIsland.size)-ComponentIsland.halfSize)*3/4;
			LaboratoryElement element = trySpawnElement(world,LaboratoryElementType.SMALL_ROOM,x,z,0);
			if (element == null)continue;
			
			elements.add(element);
			roomElements.add(element);
			break;
		}
		
		if (roomElements.isEmpty())return false;
		
		int attempts = 100, dir;
		
		while(--attempts >= 0){
			if (roomElements.size() > 17+rand.nextInt(8))break;
			else if (roomElements.size() > 8+rand.nextInt(5))attempts -= 15;
			
			LaboratoryElement room = roomElements.get(rand.nextInt(roomElements.size()));
			if (room.connected[dir = rand.nextInt(4)])continue;
			
			int xx = room.x+room.type.halfSizeX+1, startY = room.y, prevY = room.y, zz = room.z+room.type.halfSizeZ+1, dist = 0;
			
			while(++dist < 32){
				LaboratoryElement hall = trySpawnElement(world,dir == 0 || dir == 2 ? LaboratoryElementType.HALL_Z : LaboratoryElementType.HALL_X,xx,zz,dir);
				if (hall == null || Math.abs(hall.y-prevY) > 2 || Math.abs(hall.y-startY) > 4 || !hasSpaceFor(hall))break;
			}
			
			if (dist <= 7)continue;
			
			boolean hasGenerated = false;
			
			for(int placeAttempt = 0; placeAttempt < dist+5; placeAttempt++){
				LaboratoryElementType type = rand.nextInt(4) == 0 ? LaboratoryElementType.LARGE_ROOM : LaboratoryElementType.SMALL_ROOM;
				LaboratoryElement newRoom = trySpawnElement(world,type,xx+dist*Direction.offsetX[dir],zz+dist*Direction.offsetZ[dir],dir);
				
				if (newRoom != null && Math.abs(newRoom.y-startY) <= 4){
					room.connected[dir] = true;
					newRoom.connected[Direction.rotateOpposite[dir]] = true;
					elements.add(newRoom);
					roomElements.add(newRoom);
					
					hasGenerated = true;
					dist -= Math.abs(Direction.offsetX[dir]*(newRoom.type.halfSizeX+1)+Direction.offsetZ[dir]*(newRoom.type.halfSizeZ+1));
					
					for(int path = 0; path < dist; path++){
						xx += Direction.offsetX[dir];
						zz += Direction.offsetZ[dir];
						elements.add(trySpawnElement(world,dir == 0 || dir == 2 ? LaboratoryElementType.HALL_Z : LaboratoryElementType.HALL_X,xx,zz,dir));
					}
					
					break;
				}
			}
			
			if (hasGenerated)attempts = Math.min(100,attempts+10);
		}
		
		if (elements.contains(null)){
			Log.error("Found null in LaboratoryPlan element list: $0",elements.toString());
			return false;
		}
		
		score = elements.size()+roomElements.size()*12;
		return true;
	}
	
	public LaboratoryPlan copy(){
		LaboratoryPlan copy = new LaboratoryPlan();
		copy.elements.addAll(elements);
		copy.roomElements.addAll(roomElements);
		copy.score = score;
		return copy;
	}
	
	public int getScore(){
		return score;
	}
	
	private boolean hasSpaceFor(LaboratoryElement testElement){
		int addDist = Math.max(testElement.type.halfSizeX,testElement.type.halfSizeZ)*2+3, max;
		
		for(LaboratoryElement element:elements){
			if (element == null)continue;
			
			max = Math.max(element.type.halfSizeX,element.type.halfSizeZ)*2+addDist;
			if (Math.abs(element.x-testElement.x) <= max && Math.abs(element.z-testElement.z) <= max)return false;
		}
		
		return true;
	}
	
	private static LaboratoryElement trySpawnElement(LargeStructureWorld world, LaboratoryElementType type, int x, int z, int dir){
		if (type.halfSizeX == -1 || type.halfSizeZ == -1)return null;
		
		TByteByteHashMap map = new TByteByteHashMap();
		int minY = -1, maxY = -1;
		
		for(int xx = x-type.halfSizeX, zz, yy; xx <= x+type.halfSizeX; xx++){
			for(zz = z-type.halfSizeZ; zz <= z+type.halfSizeZ; zz++){
				if ((yy = world.getHighestY(xx,zz)) == 0)return null;
				
				if (minY == -1)minY = maxY = yy;
				else{
					if (yy < minY)minY = yy;
					if (yy > maxY)maxY = yy;
				}
				
				map.adjustOrPutValue((byte)(yy-128),(byte)1,(byte)1);
			}
		}
		
		if (maxY-minY > 6)return null;
		
		int mostFrequentY = 0;
		byte mostFrequentYAmount = 0;
		
		for(byte y:map.keys()){
			byte amt = map.get(y);
			
			if (amt > mostFrequentYAmount){
				mostFrequentYAmount = amt;
				mostFrequentY = y+128;
			}
		}
		
		if (maxY-mostFrequentY > 2)return null;
		if ((float)mostFrequentYAmount/((type.halfSizeX*2+1)*(type.halfSizeZ*2+1)) < 0.25F)return null;
		
		return new LaboratoryElement(type,x,mostFrequentY,z);
	}
}